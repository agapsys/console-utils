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

public class CellProperties {
	
	private static Object applyObject(Object defaultObj, Object custom) {
		if (custom != null)
			return custom;
		
		return defaultObj;
	}
	
	static CellProperties apply(CellProperties defaults, CellProperties custom) {
		if (!defaults.isFulfilled())
			throw new IllegalArgumentException("Defaults must be fulfilled");
		
		int wrapLength          = (int)           applyObject(defaults.wrapLength, custom.wrapLength);
		CellAlignment alignment = (CellAlignment) applyObject(defaults.alignment,  custom.alignment);
		ConsoleColor fgColor    = (ConsoleColor)  applyObject(defaults.fgColor,    custom.fgColor);
		ConsoleColor bgColor    = (ConsoleColor)  applyObject(defaults.bgColor,    custom.bgColor);
		
		return new CellProperties(wrapLength, alignment, fgColor, bgColor);
		
	}

	private final Integer       wrapLength;
	private final CellAlignment alignment;
	private final ConsoleColor  fgColor;
	private final ConsoleColor  bgColor;

	public CellProperties() {
		this(null, null, null, null);
	}
	
	public CellProperties(Integer wrapLength, CellAlignment alignment, ConsoleColor fgColor, ConsoleColor bgColor) {
		if (wrapLength != null && wrapLength < 1)
			throw new IllegalArgumentException(String.format("Invalid wrap length: %d", wrapLength));

		this.wrapLength = wrapLength;
		this.alignment = alignment;
		this.fgColor = fgColor;
		this.bgColor = bgColor;
	}

	public Integer getWrapLength() {
		return wrapLength;
	}

	public CellAlignment getCellAlignment() {
		return alignment;
	}

	public ConsoleColor getFgColor() {
		return fgColor;
	}

	public ConsoleColor getBgColor() {
		return bgColor;
	}

	boolean isFulfilled() {
		return wrapLength != null && alignment != null && fgColor != null && bgColor != null;
	}
	
	@Override
	public String toString() {
		return String.format("[wrapLength: %d, alignment: %s, fgColor: %s, bgColor: %s]", wrapLength, alignment, fgColor, bgColor);
	}
}
