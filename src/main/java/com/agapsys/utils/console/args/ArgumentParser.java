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

public class ArgumentParser {
	// CLASS SCOPE =============================================================
	/*
	public static final String SHORT_NAME_PREFIX = "-";
	public static final String LONG_NAME_PREFIX = "--";
	
	private static class DefaultArgument implements Argument {
		private final ArgumentDefinition definition;
		private final List<String> params;
		
		public DefaultArgument(ArgumentDefinition definition, List<String> params) {
			this.definition = definition;
			this.params = params;
		}
		
		@Override
		public ArgumentDefinition getDefinition() {
			return definition;
		}

		@Override
		public List<String> getParameters() {
			return params;
		}
	}
	
	public static interface OnErrorListener {
		public void on
	}
	// =========================================================================

	// INSTANCE SCOPE ==========================================================
	private final List<ArgumentDefinition> argDefinitions;
	
	ArgumentParser(List<ArgumentDefinition> argDefinitions) {
		this.argDefinitions = argDefinitions;
	}
	
	public List<ArgumentDefinition> getArgDefinitions() {
		return argDefinitions;
	}
	*/
	
	/**
	 * Returns the arguments passed to the application.
	 * @param appParams application parameters
	 * @return the list of arguments passed to application. associated with this argument. If there is no parameters returns an empty list.
	 * @throws ArgumentParserException if there was an error while processing
	 */
	/*
	public List<Argument> getArguments(String[] appParams) throws ArgumentParserException {
		List<Argument> argList = new LinkedList<>();
		
		List<String> argParams = new LinkedList<>();
		int i = 0;
		
		while (i < appParams.length) {
			String appParam = appParams[i];
			
			String argName = null;
			
			if (appParam.startsWith(LONG_NAME_PREFIX)) {
				argName = appParam.substring(LONG_NAME_PREFIX.length());
			} else if (appParam.startsWith(SHORT_NAME_PREFIX)) {
				argName = appParam.substring(SHORT_NAME_PREFIX.length());
			} else {
				if (i == 0)
					throw new ArgumentException(app, argName)
				argParams.add(appParam);
			}
			
			if (argName != null) {
				
			}
		}
	}*/
	
	// =========================================================================
}
