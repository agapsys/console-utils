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
import java.util.LinkedList;
import java.util.List;

/**
 * @author Leandro Oliveira (leandro@agapsys.com)
 */
public class HelpOptionDefinition implements OptionDefinition {
	// CLASS SCOPE =============================================================
	private static final String DEFAULT_SHORT_NAME         = "h";
	private static final String DEFAULT_LONG_NAME          = "--help";
	private static final String DEFAULT_DESCRIPTION        = "Shows usage instructions";
	private static final String DEFAULT_PARAMS_DESCRIPTION = "[option]";
	private static final int    DEFAULT_TABLE_WIDTH        = 80;
	// =========================================================================

	// INSTANCE SCOPE ==========================================================
	@Override
	public String getShortName() {
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
	
	
	private String __getOptionShortName(OptionDefinition optionDefinition) {
		if (optionDefinition.getShortName() != null)
			return String.format("%s%s", DefaultOptionParser.SHORT_OPTION_PREFIX, optionDefinition.getShortName());
		
		return "";
	}
	
	private String __getOptionLongName(OptionDefinition optionDefinition) {
		if (optionDefinition.getLongName()!= null)
			return String.format("%s%s", DefaultOptionParser.LONG_OPTION_PREFIX, optionDefinition.getLongName());
		
		return "";
	}
	
	private String __getOptionName(OptionDefinition optionDefinition) {
		String shortNameDescription = __getOptionShortName(optionDefinition);
		String longNameDescription  = __getOptionLongName(optionDefinition);
		
		String nameDescription;
		
		if (!shortNameDescription.isEmpty() && !longNameDescription.isEmpty()) {
			nameDescription = String.format("%s, %s", shortNameDescription, longNameDescription);
		} else if (!shortNameDescription.isEmpty()) {
			nameDescription = shortNameDescription;
		} else {
			nameDescription = longNameDescription;
		}
		
		String paramDescription = optionDefinition.getParamDescription();
		
		if (paramDescription != null) {
			paramDescription = paramDescription.trim();
		} else {
			paramDescription = "";
		}
		
		return String.format("%s%s",
			nameDescription, 
			paramDescription.isEmpty() ? "" : " " + paramDescription
		);	
	}
	
	
	protected String getGlobalHelpString(OptionParser parser) {
		final int CELL_VERTICAL_SPACING = 1;
		
		int firstColLength = 0;
		List<String> firstColList = new LinkedList<>();
		
		for (OptionDefinition optionDefinition : parser.getOptionDefinitions()) {
			String firstCol = __getOptionName(optionDefinition);
			firstColList.add(firstCol);
			
			if (firstCol.length() > firstColLength)
				firstColLength = firstCol.length();
		}
		
		TableBuilder tb = new TableBuilder()
			.addCol(firstColLength)
			.addCol(CELL_VERTICAL_SPACING)
			.addCol(DEFAULT_TABLE_WIDTH - firstColLength - CELL_VERTICAL_SPACING);
		
		for (int i = 0; i < firstColList.size(); i++) {
			OptionDefinition optionDefinition = parser.getOptionDefinitions().get(i);
			String shortDescription = optionDefinition.getShortDescription();
			
			if (shortDescription == null || shortDescription.trim().isEmpty())
				throw new RuntimeException(String.format("Option '%s' does not have a short description", __getOptionName(optionDefinition)));
			
			tb.addRow()
				.addCell(firstColList.get(i))
				.addCell()
				.addCell(shortDescription)
			.endRow();
		}
		
		return tb.toString();
	}
	
	protected String getDetailHelpString(OptionParser parser, OptionDefinition optionDefinition) {
		final int IDENT_WIDTH = 4;
		
		String firstLine = __getOptionName(optionDefinition);
		StringBuilder sb = new StringBuilder(firstLine).append("\n\n");
		
		String longDescription = optionDefinition.getLongDescription();
		if (longDescription == null || longDescription.trim().isEmpty())
				throw new RuntimeException(String.format("Option '%s' does not have a long description", __getOptionName(optionDefinition)));
		
		longDescription = new TableBuilder().addCol(IDENT_WIDTH).addCol(DEFAULT_TABLE_WIDTH - IDENT_WIDTH).addRow().addCell().addCell(longDescription).endRow().toString();
		sb.append(longDescription);
		return sb.toString();
	}
	
	protected String getHelpString(OptionParser parser, List<String> params) throws ParsingException {
		if (params.size() > 1)
			throw new ParsingException("Unexpected parameter: %s", params.get(1));
		
		if (params.isEmpty())
			return getGlobalHelpString(parser);
		
		return getDetailHelpString(parser, parser.getOptionDefinition(params.get(0)));
	}

	
	@Override
	public String getShortDescription() {
		return DEFAULT_DESCRIPTION;
	}

	@Override
	public String getLongDescription() {
		return DEFAULT_DESCRIPTION;
	}
	
	@Override
	public String getParamDescription() {
		return DEFAULT_PARAMS_DESCRIPTION;
	}
	// =========================================================================

}
