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

package com.agapsys.utils.console.args;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Represents an argument definition.
 * @author Leandro Oliveira (leandro@agapsys.com)
 */
public abstract class ArgumentDefinition {
	// CLASS SCOPE =============================================================
	private static final String SHORT_NAME_PATTERN = "[a-zA-Z]";
	private static final String LONG_NAME_PATTERN = "^[a-z]+[a-zA-Z\\-0-9]{1,}";
	// =========================================================================

	// INSTANCE SCOPE ==========================================================
	private final Character    shortName;
	private final String       longName;
	
	/**
	 * Creates an argument definition
	 * @param shortName      associated argument short name
	 * @param longName       associated argument long name
	 */
	public ArgumentDefinition(Character shortName, String longName) {
		if (shortName == null && longName == null)
			throw new IllegalArgumentException("Both short and long names are null");
		
		if (shortName != null && !Pattern.matches(SHORT_NAME_PATTERN, shortName.toString()))
			throw new IllegalArgumentException(String.format("Invalid short name: %s", shortName));
		
		if (longName != null && Pattern.matches(LONG_NAME_PATTERN, longName))
			throw new IllegalArgumentException(String.format("Invalid long name: %s", longName));
		
		this.shortName = shortName;
		this.longName = longName;
	}
	
	/**
	 * Return associated argument short name.
	 * @return associated argument short name. If associated argument does not have a short name, returns <code>null</code>.
	 */
	public final Character getShortName() {
		return shortName;
	}
	
	/**
	 * Return associated argument long name
	 * @return associated argument long name. If associated argument does not have a long name, returns <code>null</code>.
	 */
	public final String getLongName() {
		return longName;
	}
	
	/**
	 * Returns a list with argument default parameters for associated argument.
	 * @return list with default parameters for associated argument. Default implementation returns <code>null</code> (no default parameters)
	 */
	public List<String> getDefaultParams() {
		return null;
	}
		
	/**
	 * Returns the description associated with this argument definition.
	 * @return argument description.
	 */
	public abstract String getDescription();
	// =========================================================================
}
