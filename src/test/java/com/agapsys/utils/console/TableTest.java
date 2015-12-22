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

import com.agapsys.utils.console.printer.ConsoleColor;
import com.agapsys.utils.console.printer.ConsolePrinter;
import com.agapsys.utils.console.printer.tables.CellProperties;
import com.agapsys.utils.console.printer.tables.CellPropertiesBuilder;
import com.agapsys.utils.console.printer.tables.RowBuilder;
import com.agapsys.utils.console.printer.tables.TableBuilder;
import com.agapsys.utils.console.printer.tables.ColumnDefaultsBuilder;

public class TableTest {
	// CLASS SCOPE =============================================================
	private static void printTable() {
		TableBuilder tb = new TableBuilder();
		
		tb.setDefaults(new ColumnDefaultsBuilder()
				.addColumn(2)
				.addColumn(1)
				.addColumn(11)
				.addColumn(1)
				.addColumn(30)
				.addColumn(1)
				.addColumn(2)
				.getTableDefaults()
		);
		
		RowBuilder rowBorderBuilder = new RowBuilder(tb)
			.addCell()
			.addCell()
			.addCell()
			.addCell()
			.addCell()
			.addCell()
			.addCell();
		
		
		CellProperties cellProps = new CellPropertiesBuilder().setFgColor(ConsoleColor.CYAN).build();
		tb.addRow(
			new RowBuilder(tb)
				.addCell(cellProps, "ID")
				.addCell()
				.addCell(cellProps, "DATE")
				.addCell()
				.addCell(cellProps, "NAME")
				.addCell()
				.addCell(cellProps, "G")
				.getRowString()
		);
		tb.addRow(rowBorderBuilder.getRowString());
		tb.addRow(
			new RowBuilder(tb)
				.addCell("1")
				.addCell()
				.addCell("abcd-ef-gh ")
				.addCell()
				.addCell("aaaaa bbbbbb cccccc dd eeeeeeee f gggg hhhh i jjjjj kk lll mm nnnn")
				.addCell()
				.addCell(new CellPropertiesBuilder().setFgColor(ConsoleColor.YELLOW).build(), "f")
				.getRowString()
		);
		tb.addRow(rowBorderBuilder.getRowString());
		tb.addRow(
			new RowBuilder(tb)
			.addCell("2")
			.addCell()
			.addCell("ijkl-mn-op ")
			.addCell()
			.addCell("ooooooo pppp qqqqqq rr ssssssss")
			.addCell()
				.addCell(new CellPropertiesBuilder().setFgColor(ConsoleColor.YELLOW).build(), "m")
			.getRowString()
		);
		
		/*
		.addRowBuilder()
		.end()
		.addRowBuilder()
			
		.end()
		.addRowBuilder()
			.cell("3")
			.cell()
			.cell("qrst-uv-wx ")
			.cell()
			.cell("tttttt uuuuuu vv wwwwwwww")
			.cell()
			.cell(ConsoleColor.RED, " m")
		.end();
		*/
		System.out.println(tb.toString());
	}
	
	public static void testColorString() {
		String colorString = ConsolePrinter.getColorString(ConsoleColor.CYAN, "aaaaa0\nbbbbbbbbbbbb\nccccccccccccccccc");
		System.out.println(colorString);
	}
	
	public static void main(String...args) {
		testColorString();
		System.out.println();
		printTable();
	}
	// =========================================================================

	// INSTANCE SCOPE ==========================================================

	// =========================================================================
}
