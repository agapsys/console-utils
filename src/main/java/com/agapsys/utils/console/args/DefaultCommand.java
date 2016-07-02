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

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Leandro Oliveira (ljbo.82@gmail.com)
 */
public class DefaultCommand implements Command {
	private static final String LONG_OPT_PREFIX  = "--";
	private static final String SHORT_OPT_PREFIX = "-";
	
	private final String cmdName;
	private final List<String> cmdArgs;
	private final Map<String, List<String>> cmdOptions;
	
	private String[] getOptionNames(String cmdLineArg) {
		if (cmdLineArg.startsWith(SHORT_OPT_PREFIX)) {
			String name = cmdLineArg.substring(SHORT_OPT_PREFIX.length());
			String[] names = new String[name.length()];
			int i = 0;
			for (char chr : name.toCharArray()) {
				names[i] = "" + chr;
				i++;
			}
			return names;
		} else if (cmdLineArg.startsWith(LONG_OPT_PREFIX)) {
			return new String[] { cmdLineArg.substring(LONG_OPT_PREFIX.length()) };
		} else {
			return new String[] { cmdLineArg };
		}
	}
	
	public DefaultCommand(String[] cmdLineArgs) throws ParsingException {
		String cmdName                       = null;
		List<String> cmdArgs                 = new LinkedList<>();
		Map<String, List<String>> cmdOptions = new LinkedHashMap<>();
		
		String currentOptionName       = null;
		List<String> currentOptionArgs = new LinkedList<>();
		
		String shortSequence = null;
		
		final List<String> emptyList = Collections.unmodifiableList(new LinkedList<String>());
		
		for (int i = 0; i < cmdLineArgs.length; i++) {
			String cmdLineArg = cmdLineArgs[i].trim();
			
			if (i == 0) {
				if (!(cmdLineArg.startsWith(SHORT_OPT_PREFIX) || cmdLineArg.startsWith(LONG_OPT_PREFIX)))
					cmdName = cmdLineArg;
			} else {
				
				//TODO consider "-c arg1 arg2 -d arg1 arg2 -c arg3 arg4" ("-c" must be concatenated and dupplicate args should be ignored!)
				if (cmdLineArg.startsWith(SHORT_OPT_PREFIX) || cmdLineArg.startsWith(LONG_OPT_PREFIX)) {
					if (currentOptionName != null) { // <-- closes current option
						cmdOptions.put(currentOptionName, Collections.unmodifiableList(currentOptionArgs));
						cmdOptions = new LinkedHashMap<>();
						shortSequence = null;
					}
					
					String[] optionNames = getOptionNames(cmdLineArg);
					currentOptionName = optionNames[0];

					if (cmdLineArg.startsWith(SHORT_OPT_PREFIX)) {
						
						shortSequence = optionNames.length > 1 ? cmdLineArg : null;
						
						if (shortSequence != null) {
							for (String optionName : optionNames) {
								cmdOptions.put(optionName, emptyList);
							}
						}
					}
				} else {
					if (currentOptionName == null) {
						cmdArgs.add(cmdLineArg);
					} else {
						if (shortSequence == null) {
							currentOptionArgs.add(cmdLineArg);
						} else {
							throw new ParsingException("Short option sequence '%s' does not allow args", shortSequence);
						}
					}
				}
			}
		}
		
		if (currentOptionName != null) { // <-- closes current option
			cmdOptions.put(currentOptionName, Collections.unmodifiableList(currentOptionArgs));
			currentOptionName = null;
			cmdOptions = new LinkedHashMap<>();
			shortSequence = null;
		}
		
		this.cmdName = cmdName;
		this.cmdArgs = Collections.unmodifiableList(cmdArgs);
		this.cmdOptions = Collections.unmodifiableMap(cmdOptions);
	}
	
	@Override
	public String getName() {
		return cmdName;
	}

	@Override
	public List<String> getArgs() {
		return cmdArgs;
	}

	@Override
	public Map<String, List<String>> getOptions() {
		return cmdOptions;
	}
	
}
