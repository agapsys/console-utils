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

package com.agapsys.utils.console.printer.tables;

import com.agapsys.utils.console.printer.ConsoleColor;
import com.agapsys.utils.console.printer.ConsolePrinter;
import org.apache.commons.lang.StringUtils;

public class TableTest {
	// CLASS SCOPE =============================================================
	private static void printTable() {
		TableBuilder tb = new TableBuilder()
			.addCol(2)
			.addCol(1)
			.addCol(11)
			.addCol(1)
			.addCol(40)
			.addCol(1)
			.addCol(3);
		
		RowBuilder borderBuilder = new RowBuilder(tb)
			.addCell("--")
			.addCell("+")
			.addCell("-----------")
			.addCell("+")
			.addCell(StringUtils.repeat("-", 40))
			.addCell("+")
			.addCell("---");
		
		tb.addRow()
			.addCell(ConsoleColor.CYAN, "ID")
			.addCell()
			.addCell(ConsoleColor.CYAN, "DATE")
			.addCell()
			.addCell(ConsoleColor.CYAN, "NAME")
			.addCell()
			.addCell(ConsoleColor.CYAN, "G")
		.endRow()
		.addRow(borderBuilder)
		.addRow()
			.addCell("1")
			.addCell()
			.addCell("abcd-ef-gh ")
			.addCell()
			.addCell("aaaaa bbbbbb cccccc dd eeeeeeee f gggg hhhh i jjjjj kk lll mm nnnn")
			.addCell()
			.addCell(CellAlignment.RIGHT, ConsoleColor.WHITE, ConsoleColor.RED, "f")
		.endRow()
		.addRow(borderBuilder)
		.addRow()
			.addCell("2")
			.addCell()
			.addCell("ijkl-mn-op ")
			.addCell()
			.addCell("ooooooo pppp qqqqqq rr ssssssss")
			.addCell()
			.addCell(ConsoleColor.YELLOW, "m")
		.endRow()
		.addRow(borderBuilder)
		.addRow()
			.addCell("3")
			.addCell()
			.addCell("qrst-uv-wx ")
			.addCell()
			.addCell("tttttt uuuuuu vv wwwwwwww")
			.addCell()
			.addCell(ConsoleColor.RED, "m")
		.endRow();
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
