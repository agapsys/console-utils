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

import java.util.LinkedList;
import java.util.List;

public class RowBuilder extends LockableObject {
	// CLASS SCOPE =============================================================
	public static class Column {
		public final ConsoleColor fgColor;
		public final String value;
		
		public Column(ConsoleColor fgColor, String value, Object...args) {
			if (fgColor == null)
				throw new IllegalArgumentException("Color cannot be null");
			
			if (value == null)
				throw new IllegalArgumentException("Value cannot be null");
			
			this.fgColor = fgColor;
			
			if (args.length > 0)
				value = String.format(value, args);
			this.value = value;
		}
	}
	// =========================================================================
	
	// INSTANCE SCOPE ==========================================================
	private final List<Column> cols = new LinkedList<>();
	private final TableBuilder tableBuilder;
	
	RowBuilder(TableBuilder tableBuilder) {
		this.tableBuilder = tableBuilder;
	}
	
	public RowBuilder col(ConsoleColor fgColor, String value, Object...args) {
		throwIfLocked();
		Column column = new Column(fgColor, value, args);
		cols.add(column);
		return this;
	}
	
	public RowBuilder col(String value, Object...args) {
		return col(ConsoleColor.DEFAULT, value, args);
	}
	
	public TableBuilder end() {
		setLocked(true);
		return tableBuilder;
	}
	
	public List<Column> getColumns() {
		if (!isLocked())
			throw new IllegalStateException("Definition is not complete");
		
		return cols;
	}
	// =========================================================================
}
