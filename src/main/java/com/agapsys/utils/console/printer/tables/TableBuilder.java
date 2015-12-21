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

public class TableBuilder {
	// CLASS SCOPE =============================================================
	// =========================================================================

	// =========================================================================	
	
	private final List<String> rows = new LinkedList<>();
	
	private TableDefaults tableDefaults = null;
	
	public TableBuilder setDefaults(TableDefaults defaults) {
		if (this.tableDefaults != null)
			throw new IllegalStateException("Defaults already set");
		
		if (defaults == null)
			throw new IllegalArgumentException("Defaults cannot be null");
		
		this.tableDefaults = defaults;
		return this;
	}
	
	public TableDefaults getDefaults() {
		return tableDefaults;
	}
	
	public TableBuilder addRow(String row) {
		if (row == null)
			throw new IllegalArgumentException("Row cannot be null");
		
		rows.add(row);
		return this;
	}
		
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for (String row : rows) {
			sb.append(row).append("\n");
		}
		
		return sb.toString();
	}
	// =========================================================================
}
