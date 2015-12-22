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
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public final class RowBuilder {
	// CLASS SCOPE =============================================================
	// =========================================================================

	// INSTANCE SCOPE ==========================================================
	private final List<Cell> cells = new LinkedList<>();
	private final TableBuilder tableBuiler;
	
	private boolean locked = false;
	
	RowBuilder(TableBuilder tableBuilder, boolean empty) {
		if (tableBuilder == null)
			throw new IllegalArgumentException("Table builder cannot be nulll");
		
		List<CellProperties> columns = tableBuilder._getColumns();
		this.tableBuiler = tableBuilder;
		
		if (empty) {
			locked = true;
			for (CellProperties props : columns) {
				addCell();
			}
		}
	}
	
	public RowBuilder(TableBuilder tableBuilder) {
		this(tableBuilder, false);
		
		if (tableBuilder._getColumns().isEmpty())
			throw new IllegalArgumentException("Table builder does not have column definitions");
		
		tableBuilder._lockColumns();
	}
	
	private CellProperties __getDefaultCellProps(int index) {
		String errMsg = "There is no default properties for column index " + index;
		List<CellProperties> columns = tableBuiler._getColumns();
		
		if (index > columns.size() - 1)
			throw new IllegalStateException(errMsg);
		
		return columns.get(index);
	}
	
	private RowBuilder __addCell(boolean allowNull, CellAlignment alignment, ConsoleColor fgColor, ConsoleColor bgColor, String value, Object...valueArgs) {
		if (alignment == null && !allowNull)
			throw new IllegalArgumentException("Alignment cannot be null");
		
		if (fgColor == null && !allowNull)
			throw new IllegalArgumentException("Foreground color cannot be null");
		
		if (bgColor == null && !allowNull)
			throw new IllegalArgumentException("Background color cannot be null");
		
		if (locked)
			throw new IllegalStateException("Row is fully defined");
		
		CellProperties column = __getDefaultCellProps(cells.size());
		CellProperties tmpProperties = new CellProperties(null, alignment, fgColor, bgColor);
		
		CellProperties effectiveProperties = CellProperties._apply(column, tmpProperties);
		
		cells.add(new Cell(effectiveProperties, value, valueArgs));
		return this;
	}
	
	public RowBuilder addCell(CellAlignment alignment, ConsoleColor fgColor, ConsoleColor bgColor, String value, Object...valueArgs) {
		return __addCell(false, alignment, fgColor, bgColor, value, valueArgs);
	}
	
	public RowBuilder addCell(ConsoleColor fgColor, ConsoleColor bgColor, String value, Object...args) {
		return __addCell(true, CellAlignment.LEFT, fgColor, bgColor, value, args);
	}
	
	public RowBuilder addCell(ConsoleColor fgColor, String value, Object...args) {
		return addCell(fgColor, ConsoleColor.DEFAULT, value, args);
	}
	
	public RowBuilder addCell(String value, Object...args) {
		return addCell(ConsoleColor.DEFAULT, value, args);
	}
	
	public RowBuilder addCell() {
		return addCell("");
	}
	
	public TableBuilder endRow() {
		locked = true;
		tableBuiler._getRows().add(this);
		tableBuiler._clearCurrentRow();
		return tableBuiler;
	}
	
	String _getRowString() {
		if (cells.isEmpty())
			return "";
				
		int lineCount = 1;
		for (Cell cell : cells) {
			int tmpLineCount = cell.getLineCount();
			if (tmpLineCount > lineCount)
				lineCount = tmpLineCount;
		}
		
		Cell[][] cellArray = new Cell[lineCount][cells.size()];
		
		for (int j = 0; j < cells.size(); j++) {
			Cell cell = cells.get(j);
			
			String[] cellLines = cell.getWrappedValue().split(Pattern.quote("\n"));

			for (int i = 0; i < cellLines.length; i++) {
				cellArray[i][j] = new Cell(cell.getCellProperties(), cellLines[i]);
			}
		}
		
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < lineCount; i++) {
			StringBuilder sbLine = new StringBuilder();
			
			for (int j = 0; j < cells.size(); j++) {
				Cell cell = cellArray[i][j];
				if (cell == null)
					cell = new Cell(__getDefaultCellProps(j), "");
				
				sbLine.append(cell.getDisplayString());
			}
			
			sb.append(sbLine.toString());
			
			if (i < lineCount - 1)
				sb.append("\n");
		}
		
		return sb.toString();		
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		
		boolean firstCell = true;
		for (Cell cell : cells) {
			
			if (!firstCell) {
				sb.append(", ");
			} else {
				firstCell = false;
			}
			
			sb.append("\"").append(cell.toString()).append("\"");
		}
		
		sb.append("]");
		return sb.toString();
	}
	// =========================================================================
}
