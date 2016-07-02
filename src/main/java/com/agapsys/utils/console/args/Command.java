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

import java.util.List;
import java.util.Map;

/**
 * Represents a command-line command
 * @author Leandro Oliveira (ljbo.82@gmail.com)
 */
public interface Command {
	/**
	 * Return command name.
	 * 
	 * @return command name.
	 */
	public String getName();
	
	/**
	 * Return command arguments.
	 * 
	 * @return command arguments.
	 */
	public List<String> getArgs();
	
	/**
	 * Return command options.
	 * 
	 * @return command options.
	 */
	public Map<String, List<String>> getOptions();
}
