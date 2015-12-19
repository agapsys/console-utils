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

import com.agapsys.utils.console.TableDefinitionBuilder.ColumnDefinition;
import java.util.LinkedList;
import java.util.List;

public class TableBuilder {

	// CLASS SCOPE =============================================================
	private static String wrap(String srcText, int width, boolean recursive) {
		if (srcText.equals("\n"))
			return srcText;
					
		width = width - 1;
		String[] paragraphs = srcText.split("\n");
		
		for (int i = 0; i < paragraphs.length; i++) {
			paragraphs[i] = paragraphs[i].trim();
		}
		
		StringBuilder totalText = new StringBuilder();
		
		// Quebra as linhas dentro do parágrafo:
		for (int i = 0; i < paragraphs.length; i++) {
			StringBuilder sb = new StringBuilder(paragraphs[i]);

			int j = 0;
			while (j + width < sb.length() && (j = sb.lastIndexOf(" ", j + width)) != -1) {
			    sb.replace(j, j + 1, "\n");
			}

			// trim lines
			String[] lines = sb.toString().split("\n");
			StringBuilder sbFinal = new StringBuilder();
			for (int k = 0; k < lines.length; k++) {
				lines[k] = lines[k].trim();
				sbFinal.append(lines[k]);
				if (k < (lines.length - 1))
					sbFinal.append("\n");
			}
			
			if (i < (paragraphs.length - 1))
				sbFinal.append("\n");
			
			totalText.append(sbFinal.toString());
		}
		
		return totalText.toString();
	}
	
	public static String wrap(String str, int width) {
		return wrap(str, width, false);
	}
	
	public static void main(String...args) {
		Console.println(ConsoleColor.MAGENTA, wrap("Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.", 50));
		System.out.println("");
		Console.println(ConsoleColor.MAGENTA, wrap("Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and\n\n limitations under the License.", 50));
	}
	// =========================================================================
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

	public void println() {
		if (tdb == null) {
			throw new IllegalStateException("Table definition is not set");
		}

		List<ColumnDefinition> colDefs = tdb.getColumnDefinitions();
//		for (RowBuilder row : rows) {
//			int i = 0;
//			for ()
//		}
	}
}
