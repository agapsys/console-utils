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

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author Leandro Oliveira (leandro@agapsys.com)
 */
public class OptionParserBuilder {
	// CLASS SCOPE =============================================================
	private static final String SHORT_NAME_PATTERN = "^[a-zA-Z]$";
	private static final String LONG_NAME_PATTERN = "^[a-zA-Z][a-zA-Z0-9\\-\\_]{2,}$";
	// =========================================================================

	// INSTANCE SCOPE ==========================================================
	private final Map<String, OptionDefinition> optionDefinitionMap = new LinkedHashMap<>();

	public OptionParserBuilder addOptionDefinition(Class<? extends OptionDefinition> optionDefinitionClass) {
		if (optionDefinitionClass == null)
			throw new IllegalArgumentException("Option definition class cannot be null");

		OptionDefinition optionDefinition;
		try {
			optionDefinition = optionDefinitionClass.newInstance();
		} catch (InstantiationException | IllegalAccessException ex) {
			throw new RuntimeException(ex);
		}

		String shortName = optionDefinition.getShortName();
		String longName = optionDefinition.getLongName();

		if (shortName == null && longName == null)
			throw new IllegalArgumentException("Option definition does not define neither a short name nor a long name");

		
		if (shortName != null) {
			if (optionDefinitionMap.containsKey(shortName))
				throw new IllegalArgumentException(String.format("An option definition with the same short name is already registered: %s", shortName));

			if (!Pattern.matches(SHORT_NAME_PATTERN, shortName))
				throw new IllegalArgumentException(String.format("Invalid short name: %s", shortName));

			optionDefinitionMap.put(shortName, optionDefinition);
		}

		
		if (longName != null) {
			if (optionDefinitionMap.containsKey(longName))
				throw new IllegalArgumentException(String.format("An option definition with the same long name is already registered: %s", longName));

			if (!Pattern.matches(LONG_NAME_PATTERN, longName))
				throw new IllegalArgumentException(String.format("Invalid long name: %s", longName));

			optionDefinitionMap.put(longName, optionDefinition);
		}

		return this;
	}

	public OptionParser build() {
		return new DefaultOptionParser(optionDefinitionMap);
	}
	// =========================================================================
}
