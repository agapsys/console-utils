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

public class CellPropertiesBuilder {

	private Integer       wrapLength;
	private CellAlignment alignment;
	private ConsoleColor  fgColor;
	private ConsoleColor  bgColor;
	
	public CellPropertiesBuilder setWrapLength(int wrapLength) {
		if (this.wrapLength != null)
			throw new IllegalStateException("Wrap length already set");
		
		if (wrapLength < 1)
			throw new IllegalArgumentException("Invalid wrap length: " + wrapLength);
		
		this.wrapLength = wrapLength;
		return this;
	}
	
	public CellPropertiesBuilder setCellAligment(CellAlignment alignment) {
		if (this.alignment != null)
			throw new IllegalStateException("Cell aligment already set");
		
		if (alignment == null)
			throw new IllegalArgumentException("Alignment cannot be null");
		
		this.alignment = alignment;
		return this;
	}
	
	public CellPropertiesBuilder setFgColor(ConsoleColor fgColor) {
		if (this.fgColor != null)
			throw new IllegalStateException("Foreground color already set");
		
		if (fgColor == null)
			throw new IllegalArgumentException("Color cannot be null");
		
		this.fgColor = fgColor;
		return this;
	}
	
	public CellPropertiesBuilder setBgColor(ConsoleColor bgColor) {
		if (this.bgColor != null)
			throw new IllegalStateException("Background color already set");
		
		if (bgColor == null)
			throw new IllegalArgumentException("Color cannot be null");
		
		this.bgColor = bgColor;
		return this;
	}
	
	public CellProperties build() {
		return new CellProperties(wrapLength, alignment, fgColor, bgColor);
	}

}
