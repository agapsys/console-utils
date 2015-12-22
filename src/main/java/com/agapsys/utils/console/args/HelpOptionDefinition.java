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

import com.agapsys.utils.console.printer.tables.TableBuilder;
import java.util.List;

/**
 * @author Leandro Oliveira (leandro@agapsys.com)
 */
public class HelpOptionDefinition implements OptionDefinition {
	// CLASS SCOPE =============================================================
	private static final Character DEFAULT_SHORT_NAME  = 'h';
	private static final String    DEFAULT_LONG_NAME   = "--help";
	private static final String    DEFAULT_DESCRIPTION = "Shows usage instructions";
	private static final int       DEFAULT_TABLE_WIDTH = 80;
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
	
	protected String getGlobalHelpString(OptionParser parser) {
		int firstColLength = 0;
		final String FIRST_COL_FORMAT = "%s%s, %s%s";
		for (OptionDefinition optionDefinition : parser.getOptionDefinitions()) {
			String firstCol = String.format(FIRST_COL_FORMAT, DefaultOptionParser.SHORT_OPTION_PREFIX, optionDefinition.getShortName(), DefaultOptionParser.LONG_OPTION_PREFIX, optionDefinition.getLongName());
			if (firstCol.length() > firstColLength)
				firstColLength = firstCol.length();
		}
		
		TableBuilder tb = new TableBuilder()
			.addCol(firstColLength)
			.addCol(1)
			.addCol(DEFAULT_TABLE_WIDTH - firstColLength - 1);
		
		for (OptionDefinition optionDefinition : parser.getOptionDefinitions()) {
			tb.addRow()
				.addCell(FIRST_COL_FORMAT, optionDefinition.getShortName(), optionDefinition.getLongName())
				.addCell(optionDefinition.getShortDescription())
			.endRow();
		}
		
		return tb.toString();
	}
	
	protected String getDetailedHelpString(OptionParser parser, OptionDefinition optionDefinition) {
		final String FIRST_LINE_FORMAT = "%s%s, %s%s";
		String firstLine = String.format(FIRST_LINE_FORMAT, DefaultOptionParser.SHORT_OPTION_PREFIX, optionDefinition.getShortName(), DefaultOptionParser.LONG_OPTION_PREFIX, optionDefinition.getLongName());

		
		sb.append(firstLine).append("\n\t").append(sb)
	}
	
	/**
	 * Returns the help string to be printed to console
	 * @param parser associated argument parser
	 * @param params help parameters
	 * @return the help string to be printed to console
	 */
	protected String getHelpString(OptionParser parser, List<String> params) throws ParsingException {
		if (params.size() > 1)
			throw new ParsingException("Unknown parameter: %s", params.get(1));
		
		if (params.size() == 0)
		
		
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
