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

public class TableDefinitionBuilder extends LockableObject {
	// CLASS SCOPE =============================================================
	public static enum ColumnAlignment {
		LEFT,
		RIGHT
	}
	
	public static class ColumnDefinition {
		public final int width;
		public final ColumnAlignment alignment;
		
		public ColumnDefinition(int width, ColumnAlignment alignment) {
			if (width < 1)
				throw new IllegalArgumentException(String.format("Invalid width: %d", width));
		
			if (alignment == null)
				throw new IllegalArgumentException("Alignment cannot be null");
			
			this.width = width;
			this.alignment = alignment;
		}
	}
	// =========================================================================

	// INSTANCE SCOPE ==========================================================
	private final List<ColumnDefinition> colDefs = new LinkedList<>();
	private final TableBuilder tableBuilder;
	
	TableDefinitionBuilder(TableBuilder tableBuilder) {
		this.tableBuilder = tableBuilder;
	}
	
	public TableDefinitionBuilder col(int width, ColumnAlignment alignment) {
		throwIfLocked();
		colDefs.add(new ColumnDefinition(width, alignment));
		return this;
	}
	
	public TableBuilder end() {
		setLocked(true);
		return tableBuilder;
	}
	
	public List<ColumnDefinition> getColumnDefinitions() {
		if (!isLocked())
			throw new IllegalStateException("Definition is not complete");
		
		return colDefs;
	}
	// =========================================================================
}
