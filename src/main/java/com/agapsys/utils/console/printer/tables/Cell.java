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

import com.agapsys.utils.console.printer.ConsolePrinter;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;

class Cell {
	private final CellProperties cellProperties;
	private final String         value;
	
	private String wrappedString = null;

	
	public Cell(CellProperties props, String value, Object...valueArgs) {
		if (props == null)
			throw new IllegalArgumentException("Cell properties cannot be null");
		
		this.cellProperties = props;

		if (value == null)
			throw new IllegalArgumentException("Value cannot be null");

		if (valueArgs.length > 0)
			value = String.format(value, valueArgs);

		this.value = value;
	}

	
	public CellProperties getCellProperties() {
		return cellProperties;
	}
	
	public String getValue() {
		return value;
	}

	public String getWrappedValue() {
		if (wrappedString == null)
			wrappedString = WordUtils.wrap(value, cellProperties.getWrapLength(), "\n", true);
		
		return wrappedString;
	}
	
	public int getLineCount() {
		return StringUtils.countMatches(getWrappedValue(), "\n") + 1;
	}
	
	public String getDisplayString() {
		String displayString = getValue();
		StringBuilder sb = new StringBuilder();
		
			int delta = cellProperties.getWrapLength() - displayString.length();

			String padding;
			if (delta > 0) {
				padding = StringUtils.repeat(" ", delta);
			} else {
				padding = "";
			}

			if (cellProperties.getCellAlignment() == CellAlignment.LEFT) {
				displayString = displayString + padding;
			} else {
				displayString = padding + displayString;
			}

			displayString = ConsolePrinter.toString(cellProperties.getFgColor(), cellProperties.getBgColor(), displayString);
			sb.append(displayString);
		
		return sb.toString();
	}
	
	@Override
	public String toString() {
		return value;
	}
}
