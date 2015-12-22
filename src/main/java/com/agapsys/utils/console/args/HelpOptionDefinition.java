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
public class HelpOptionDefinition implements OptionDefinition {
	// CLASS SCOPE =============================================================
	private static final Character DEFAULT_SHORT_NAME  = 'h';
	private static final String    DEFAULT_LONG_NAME   = "--help";
	private static final String    DEFAULT_DESCRIPTION = "Shows usage instructions";
	// =========================================================================

	// INSTANCE SCOPE ==========================================================
	@Override
	public Character getShortName() {
		return DEFAULT_SHORT_NAME;
	}

	@Override
	public String getLongName() {
		return DEFAULT_LONG_NAME;
	}

	@Override
	public boolean isUnique() {
		return true;
	}

	protected int getOptionColWrapLength() {
		// TODO return the maximum length of all registered option definitions
		// TODO lazy check to avoid calculation on each call
		throw new UnsupportedOperationException();
	}

	protected int getDescriptionColWrapLength() {
		// TODO return 80 - getOptionColWrapLength()
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns the help string to be printed to console
	 * @param parser associated argument parser
	 * @param params help parameters
	 * @return the help string to be printed to console
	 */
	protected String getHelpString(OptionParser parser, List<String> params) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getShortDescription() {
		return DEFAULT_DESCRIPTION;
	}

	@Override
	public String getLongDescription() {
		return DEFAULT_DESCRIPTION;
	}
	// =========================================================================
}
