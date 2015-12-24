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

public class TableBuilder {
	// CLASS SCOPE =============================================================
	// =========================================================================

	// INSTANCE SCOPE ==========================================================	
	private final List<CellProperties> columns = new LinkedList<>();
	private final List<RowBuilder> rows = new LinkedList<>();
	
	private RowBuilder currentRow = null;
	private boolean lockedColumns = false;
	
	public TableBuilder addCol(int wrapLength) {
		return addCol(wrapLength, CellAlignment.LEFT);
	}
	
	public TableBuilder addCol(int wrapLength, CellAlignment alignment) {
		if (lockedColumns)
			throw new IllegalStateException("Columns are already set");
		
		if (alignment == null)
			throw new IllegalArgumentException("Alignment cannot be null");
		
		columns.add(new CellProperties(wrapLength, alignment, ConsoleColor.DEFAULT, ConsoleColor.DEFAULT));
		return this;
	}
	
	private RowBuilder __createRow(boolean emptyRow) {
		if (columns.isEmpty())
			throw new IllegalStateException("Pending column definitions");
		
		if (currentRow != null)
			throw new IllegalStateException("There is an open row");
		
		lockedColumns = true;
		return new RowBuilder(this, emptyRow);
	}
	
	public TableBuilder addRow(RowBuilder row) {
		if (currentRow != null)
			throw new IllegalStateException("There is an open row");
		
		rows.add(row);
		return this;
	}
	
	public RowBuilder addRow() {
		currentRow = __createRow(false);
		return currentRow;
	}
	
	public TableBuilder addEmptyRow() {
		rows.add(__createRow(true));
		return this;
	}
	
	List<CellProperties> _getColumns() {
		return columns;
	}
	
	List<RowBuilder> _getRows() {
		return rows;
	}
	
	void _lockColumns() {
		this.lockedColumns = true;
	}
	
	void _clearCurrentRow() {
		this.currentRow = null;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < rows.size(); i++) {
			RowBuilder row = rows.get(i);
			sb.append(row._getRowString());
			if (i < rows.size() - 1)
				sb.append("\n");
		}
		
		return sb.toString();
	}
	// =========================================================================
}
