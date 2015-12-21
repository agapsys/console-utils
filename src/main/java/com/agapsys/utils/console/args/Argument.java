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
 * Represents an argument passed to application
 * @author Leandro Oliveira (leandro@agapsys.com)
 */
public interface Argument {
	/**
	 * Returns argument definition.
	 * @return argument definition.
	 */
	public ArgumentDefinition getDefinition();
	
	/**
	 * Returns the parameters passed to this argument.
	 * @return parameters passed to this argument. If there is no parameters, returns an empty list.
	 */
	public List<String> getParameters();
	
	/** 
	 * Returns the parser associated with the argument.
	 * @return associated parser.
	 */
	public ArgumentParser getParser();
}
