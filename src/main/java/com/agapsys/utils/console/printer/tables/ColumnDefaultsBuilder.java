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
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ColumnDefaultsBuilder {
	private final List<CellProperties> cellPropList = new LinkedList<>();

	public ColumnDefaultsBuilder addColumn(int wrapLength, CellAlignment alignment, ConsoleColor fgColor, ConsoleColor bgColor) {
		cellPropList.add(new CellProperties(wrapLength, alignment, fgColor, bgColor));
		return this;
	}
	
	public ColumnDefaultsBuilder addColumn(int wrapLength) {
		return addColumn(wrapLength, CellAlignment.LEFT, ConsoleColor.DEFAULT, ConsoleColor.DEFAULT);
	}
	
	public ColumnDefaultsBuilder addColumn(int wrapLength, ConsoleColor fgColor) {
		return addColumn(wrapLength, CellAlignment.LEFT, fgColor, ConsoleColor.DEFAULT);
	}

	public ColumnDefaults getTableDefaults() {
		return new ColumnDefaults() {

			@Override
			public List<CellProperties> getColumnDefinitions() {
				return Collections.unmodifiableList(cellPropList);
			}
		};
	}
}
