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

package com.agapsys.utils.console;

import java.util.List;

public abstract class HelpArgumentDefinition extends ArgumentDefinition {
	// CLASS SCOPE =============================================================
	private static final String DEFAULT_DESCRIPTION = "Shows help instructions";
	// =========================================================================

	// INSTANCE SCOPE ==========================================================
	public HelpArgumentDefinition() {
		super('h', "help");
	}
	
	@Override
	public String getDescription() {
		return DEFAULT_DESCRIPTION;
	}
	
	/** @return help title. */
	protected abstract String getTitle();
	
	/** @return usage info. */
	protected abstract String getUsageInfo();
		
	public void displayHelp(ArgumentParser parser, List<String> params) {
//		String
//		
//		String title = getTitle();
//		if (title == null)
//			title = "";
//		
//		String usage = getUsage();
//		if (usage)
//		
	}
	// =========================================================================



}