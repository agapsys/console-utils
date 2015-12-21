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

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class RowBuilder {
	private final List<Cell> cells = new LinkedList<>();
	
	public RowBuilder addCell(CellProperties cellProps, String value, Object...args) {
		if (!cellProps.isFulfilled())
			throw new IllegalArgumentException("cellProps must be fulfilled");
		
		Cell cell = new Cell(cellProps, value, args);
		cells.add(cell);
		return this;
	}
	
	private String getRowString() {
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
			
			String[] cellLines = cell.getDisplayString().split(Pattern.quote("\n"));

			for (int i = 0; i < cellLines.length; i++) {
				cellArray[i][j] = new Cell(cell.getCellProperties(), cell.getValue());
			}
		}
		
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < lineCount; i++) {
			StringBuilder sbLine = new StringBuilder();
			
			for (int j = 0; j < cells.size(); j++) {
				Cell cell = cellArray[i][j];
				
				sbLine.append(cell.getDisplayString());
			}
			
			sb.append(sbLine.toString());
			
			if (i < lineCount - 1)
				sb.append("\n");
		}
		
		return sb.toString();		
	}
	
	protected List<Cell> getCells() {
		return cells;
	}
	
	@Override
	public String toString() {
		return getRowString();
	}
	// =========================================================================
}
