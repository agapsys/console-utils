/*
 * Copyright 2015 Agapsys Tecnologia Ltda-ME.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.agapsys.utils.console;

import com.agapsys.utils.console.RowBuilder.Column;
import com.agapsys.utils.console.TableDefinitionBuilder.ColumnAlignment;
import com.agapsys.utils.console.TableDefinitionBuilder.ColumnDefinition;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;

public class TableBuilder {
	
	public static void main(String...args) {
		TableBuilder tb = new TableBuilder();
		tb.definition()
			.col(2,  ColumnAlignment.LEFT)
			.col(11, ColumnAlignment.LEFT)
			.col(30, ColumnAlignment.LEFT)
			.col(2,  ColumnAlignment.LEFT)
		.end()
		.row()
			.col(ConsoleColor.CYAN, "id")
			.col(ConsoleColor.CYAN, " date")
			.col(ConsoleColor.CYAN, " Name")
			.col(ConsoleColor.CYAN, " G")
		.end()
		.row()
			.col("--")
			.col("+----------")
			.col("+-----------------------------")
			.col("+-")
		.end()
		.row()
			.col("1")
			.col(" abcd-ef-gh ")
			.col(" aaaaa bbbbbb cccccc dd eeeeeeee f gggg hhhh i jjjjj kk lll mm nnnn")
			.col(ConsoleColor.YELLOW, " f")
		.end()
		.row()
			.col("-+")
			.col("-----------")
			.col("------------------------------")
			.col(ConsoleColor.YELLOW, " f")
		.end()
		//.row().end() // test this
		.row()
			.col("2")
			.col(" ijkl-mn-op ")
			.col(" ooooooo pppp qqqqqq rr ssssssss")
			.col(ConsoleColor.YELLOW, " m")
		.end()
		//.row().end() // test this
		.row()
			.col("3")
			.col(" qrst-uv-wx ")
			.col(" tttttt uuuuuu vv wwwwwwww")
			.col(ConsoleColor.YELLOW, " m")
		.end();
		System.out.println(tb.toString());
	}

	public TableDefinitionBuilder tdb = null;
	public final List<RowBuilder> rows = new LinkedList<>();

	public TableDefinitionBuilder definition() {
		if (tdb != null) {
			throw new IllegalStateException("Table definition is already set");
		}

		tdb = new TableDefinitionBuilder(this);
		return tdb;
	}

	public RowBuilder row() {
		RowBuilder rb = new RowBuilder(this);
		rows.add(rb);
		return rb;
	}

	private String getCell(ConsoleColor fgColor, ColumnAlignment alignment, int width, String value) {
		String baseStr = String.format("%" + (alignment == ColumnAlignment.LEFT ? "-" : "") + width + "s", value);
		return Console.getOutputString(fgColor, baseStr);
	}
	
	@Override
	public String toString() {
		if (tdb == null)
			throw new IllegalStateException("Table definition is not set");
		
		int colNum = tdb.getColumnDefinitions().size();
		int rowNum = 0;
		
		for (RowBuilder row : rows) {
			rowNum++;
			for (int j = 0; j < row.getColumns().size(); j++) {
				Column col = row.getColumns().get(j);
				ColumnDefinition colDef = tdb.getColumnDefinitions().get(j);
				
				rowNum += StringUtils.countMatches(WordUtils.wrap(col.value, colDef.width, "\n", true), "\n");
			}
		}
		
		String[][] strArray = new String[rowNum][colNum];
		
		int i = 0;
		for (RowBuilder row : rows) {
			int k = 0;
			int maxK = 0;
			for (int j = 0; j < colNum; j++) {
				ColumnDefinition  colDef = tdb.getColumnDefinitions().get(j);
				Column col = row.getColumns().get(j);
				String[] cRows = WordUtils.wrap(col.value, colDef.width).split("\n");
				k = 0;
				for (String cRow : cRows) {
					String cRowStr = WordUtils.wrap(cRow, colDef.width);
					strArray[i + k][j] = getCell(col.fgColor, colDef.alignment, colDef.width, cRowStr);
					k++;
				}
				if (k > maxK)
					maxK = k;
			}
			i += maxK;
		}
		
		StringBuilder sb = new StringBuilder();
		for (i = 0; i < rowNum; i++) {
			StringBuilder rowFormat = new StringBuilder();
			
			for (ColumnDefinition colDef : tdb.getColumnDefinitions()) {
				rowFormat.append("%").append(colDef.alignment == ColumnAlignment.LEFT ? "-" : "").append(colDef.width).append("s");
			}
			
			List<String> colValues = new LinkedList<>();
			for (int j = 0; j < colNum; j++) {
				String colValue = strArray[i][j];
				if (colValue == null)
					colValue = "";
				
				colValues.add(colValue);
			}
			
			sb.append(String.format(rowFormat.toString(), colValues.toArray()));
			if (i < rowNum - 1)
				sb.append("\n");
		}
		
		return sb.toString();
	}
}
