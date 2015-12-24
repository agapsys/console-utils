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

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Leandro Oliveira (leandro@agapsys.com)
 */
class DefaultOptionParser extends OptionParser {
	// CLASS SCOPE =============================================================
	static final String SHORT_OPTION_PREFIX = "-";
	static final String LONG_OPTION_PREFIX  = "--";

	private static class DefaultOption implements Option {
		private final OptionDefinition optionDefinition;
		private final List<String> params;

		public DefaultOption(OptionDefinition optionDefinition, List<String> params) {
			this.optionDefinition = optionDefinition;
			this.params = params;
		}

		@Override
		public OptionDefinition getOptionDefinition() {
			return optionDefinition;
		}

		@Override
		public List<String> getParams() {
			return params;
		}
	}
	// =========================================================================

	// INSTANCE SCOPE ==========================================================
	private final Map<String, OptionDefinition> optionDefinitionMap;
	private List<OptionDefinition> optionDefinitions = null;

	public DefaultOptionParser(Map<String, OptionDefinition> optionDefinitionMap) {
		this.optionDefinitionMap = optionDefinitionMap;
	}

	private void __checkDuplicateOption(List<Option> options, String optionName) throws ParsingException {
		OptionDefinition optionDefinition = getOptionDefinition(optionName);

		String optionPrefix = optionName.length() == 1 ? SHORT_OPTION_PREFIX : LONG_OPTION_PREFIX;

		for (Option option : options) {
			if (option.getOptionDefinition() == optionDefinition && optionDefinition.isUnique())
				throw new ParsingException("Option already set: %s%s", optionPrefix, optionName);
		}
	}

	@Override
	public List<Option> getOptions(String[] args) throws ParsingException {
		List<String> currentParamList = new LinkedList<>();
		OptionDefinition currentOptionDefinition = null;

		List<Option> options = new LinkedList<>();

		for (String arg : args) {
			if (arg.startsWith(LONG_OPTION_PREFIX) || arg.startsWith(SHORT_OPTION_PREFIX)) {
				if (currentOptionDefinition != null) {
					options.add(new DefaultOption(currentOptionDefinition, currentParamList));
				}

				String optionName;
				if (arg.startsWith(LONG_OPTION_PREFIX)) {
					optionName = arg.substring(LONG_OPTION_PREFIX.length());
					if (optionName.length() == 1)
						throw new ParsingException("Invalid option: %s", arg);
					
					currentOptionDefinition = getOptionDefinition(optionName);

					if (currentOptionDefinition == null)
						throw new ParsingException("Unknown option: %s%s", LONG_OPTION_PREFIX, optionName);

					__checkDuplicateOption(options, optionName);

				} else {
					char[] chars = arg.substring(SHORT_OPTION_PREFIX.length()).toCharArray();
					Set<Character> charSet = new LinkedHashSet<>();
					
					for (int i = 0; i < chars.length; i++) {
						optionName = "" + chars[i];
						
						if (!charSet.add(chars[i]))
							throw new ParsingException("Invalid short option sequence: %s", arg);
						
						currentOptionDefinition = getOptionDefinition(optionName);

						if (currentOptionDefinition == null)
							throw new ParsingException("Unknown option: %s%s", SHORT_OPTION_PREFIX, chars[i]);

						__checkDuplicateOption(options, optionName);

						if (i < chars.length - 1)
							options.add(new DefaultOption(currentOptionDefinition, new LinkedList<String>()));
					}
				}

				currentParamList = new LinkedList<>();

			} else {
				if (currentOptionDefinition == null)
					throw new ParsingException("Missing option for parameter: %s", arg);

				currentParamList.add(arg);
			}
		}

		options.add(new DefaultOption(currentOptionDefinition, currentParamList));

		return options;
	}

	@Override
	public List<OptionDefinition> getOptionDefinitions() {
		if (optionDefinitions == null) {
			optionDefinitions = new LinkedList<>();
			for (Map.Entry<String, OptionDefinition> entry : optionDefinitionMap.entrySet()) {
				OptionDefinition optionDefinition = entry.getValue();
				if (!optionDefinitions.contains(optionDefinition))
					optionDefinitions.add(optionDefinition);
			}
			optionDefinitions = Collections.unmodifiableList(optionDefinitions);
		}

		return optionDefinitions;
	}

	@Override
	public OptionDefinition getOptionDefinition(String name) {
		if (name == null)
			throw new IllegalArgumentException("name cannot be null");

		return optionDefinitionMap.get(name);
	}
	// =========================================================================
}
