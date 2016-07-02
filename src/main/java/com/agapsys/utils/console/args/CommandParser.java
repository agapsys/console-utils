/*
 * Copyright 2016 Agapsys Tecnologia Ltda-ME.
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

/**
 * Represents a command-line command parser.
 * 
 * @author Leandro Oliveira (ljbo.82@gmail.com)
 */
public class CommandParser {
	// STATIC SCOPE ============================================================
	private static CommandParser singleton;
	
	public static CommandParser getInstance() {
		synchronized(CommandParser.class) {
			if (singleton == null) singleton = new CommandParser();
			
			return singleton;
		}
	}
	// =========================================================================
	
	// INSTANCE SCOPE ==========================================================
	protected CommandParser() {}
	
	public Command getCommand(String[] args) throws ParsingException {
		return new DefaultCommand(args);
	}
	// =========================================================================
}
