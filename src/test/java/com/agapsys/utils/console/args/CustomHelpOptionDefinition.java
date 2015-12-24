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

import com.agapsys.utils.console.printer.ConsoleColor;
import com.agapsys.utils.console.printer.ConsolePrinter;
import java.util.List;

public class CustomHelpOptionDefinition extends HelpOptionDefinition {


	// CLASS SCOPE =============================================================
	private static final String APP_NAME = "Test application\nv.0.1.0";
	// =========================================================================

	// INSTANCE SCOPE ==========================================================
	@Override
	protected String getGlobalHelpString(OptionParser parser) {
		return String.format("%s\n\nUsage:\n\n%s", APP_NAME, super.getGlobalHelpString(parser));
	}
	
	@Override
	protected String getDetailedHelpString(OptionParser parser, OptionDefinition optionDefinition) {
		return String.format("%s\n\n%s", APP_NAME, super.getDetailedHelpString(parser, optionDefinition));
	}
	
	@Override
	public void exec(OptionParser parser, List<String> params) throws ParsingException {
		super.exec(parser, params);
		ConsolePrinter.println(ConsoleColor.YELLOW, "Custom execution!");
	}
	// =========================================================================

	
}
