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

/**
 * @author Leandro Oliveira (leandro@agapsys.com)
 */
public class Option {
	private final OptionDefinition definition;
	private final OptionParser parser;
	private final List<String> params;
	
	public Option(OptionParser parser, OptionDefinition definition, List<String> params) {
		if (definition == null)
			throw new IllegalArgumentException("Definition cannot be null");
		
		this.definition = definition;
		
		if (parser == null)
			throw new IllegalArgumentException("Parser cannot be null");
		
		this.parser = parser;
		
		if (params == null)
			throw new IllegalArgumentException("Params cannot be null");
		
		this.params = params;
	}
	
	public OptionParser getParser() {
		return parser;
	}
	
	public OptionDefinition getDefinition() {
		return definition;
	}

	public List<String> getParams() {
		return params;
	}
	
	public void exec() throws ParsingException {
		definition.exec(parser, params);
	}
}
