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
import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Leandro Oliveira (leandro@agapsys.com)
 */
public class OptionParserTest {
	// CLASS SCOPE =============================================================
	private static String[] __getArgs(String cmd) {
		cmd = cmd.trim();
		
		if (cmd.isEmpty())
			return new String[] {};
		
		String[] args = cmd.split("\\s+(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
		
		for (int i = 0; i < args.length; i++) {
			args[i] = StringUtils.strip(args[i], " \"");
		}
		
		return args;
	}
	// =========================================================================
	
	// INSTANCE SCOPE ==========================================================
	private final OptionParser parser;
	
	public OptionParserTest() {		
		parser = new OptionParserBuilder()
			.addOptionDefinition(CopyOption.class)
			.addOptionDefinition(ExtractOption.class)
			.addOptionDefinition(CustomHelpOptionDefinition.class)
			.build();
	}
	
	@Test
	public void okOptions() throws ParsingException {
		String cmd;
		List<Option> options;
		ParsingException err;
		Option option;
		
		// ---------------------------------------------------------------------
		err = null;
		options = null;
		
		try {	
			cmd = "-c /tmp/file1 /tmp/file2 --copy /tmp/file3 --extract-files /tmp/filex1 /tmp/filex2 /tmp/filex3 /tmp/filex4";
			options = parser.getOptions(__getArgs(cmd));
		} catch (ParsingException ex) {
			err = ex;
		}
		
		Assert.assertNull(err);
		Assert.assertNotNull(options);
		Assert.assertEquals(3, options.size());
		
		option = options.get(0);
		Assert.assertTrue(option.getDefinition() instanceof CopyOption);
		Assert.assertEquals(2, option.getParams().size());
		Assert.assertEquals("/tmp/file1", option.getParams().get(0));
		Assert.assertEquals("/tmp/file2", option.getParams().get(1));
		
		option = options.get(1);
		Assert.assertTrue(option.getDefinition() instanceof CopyOption);
		Assert.assertEquals(1, option.getParams().size());
		Assert.assertEquals("/tmp/file3", option.getParams().get(0));
		
		option = options.get(2);
		Assert.assertTrue(option.getDefinition() instanceof ExtractOption);
		Assert.assertEquals(4, option.getParams().size());
		Assert.assertEquals("/tmp/filex1", option.getParams().get(0));
		Assert.assertEquals("/tmp/filex2", option.getParams().get(1));
		Assert.assertEquals("/tmp/filex3", option.getParams().get(2));
		Assert.assertEquals("/tmp/filex4", option.getParams().get(3));
		// ---------------------------------------------------------------------
		
		// ---------------------------------------------------------------------
		err = null;
		options = null;
		
		try {	
			cmd = "-c /tmp/file1 /tmp/file2 -c /tmp/file3 --extract-files /tmp/filex1 /tmp/filex2 /tmp/filex3 /tmp/filex4";
			options = parser.getOptions(__getArgs(cmd));
		} catch (ParsingException ex) {
			err = ex;
		}
		
		Assert.assertNull(err);
		Assert.assertNotNull(options);
		Assert.assertEquals(3, options.size());
		
		option = options.get(0);
		Assert.assertTrue(option.getDefinition() instanceof CopyOption);
		Assert.assertEquals(2, option.getParams().size());
		Assert.assertEquals("/tmp/file1", option.getParams().get(0));
		Assert.assertEquals("/tmp/file2", option.getParams().get(1));
		
		option = options.get(1);
		Assert.assertTrue(option.getDefinition() instanceof CopyOption);
		Assert.assertEquals(1, option.getParams().size());
		Assert.assertEquals("/tmp/file3", option.getParams().get(0));
		
		option = options.get(2);
		Assert.assertTrue(option.getDefinition() instanceof ExtractOption);
		Assert.assertEquals(4, option.getParams().size());
		Assert.assertEquals("/tmp/filex1", option.getParams().get(0));
		Assert.assertEquals("/tmp/filex2", option.getParams().get(1));
		Assert.assertEquals("/tmp/filex3", option.getParams().get(2));
		Assert.assertEquals("/tmp/filex4", option.getParams().get(3));
		// ---------------------------------------------------------------------
		
		// ---------------------------------------------------------------------
		cmd = "-h \"abcd efg\"";
		ConsolePrinter.println(ConsoleColor.MAGENTA, cmd);
		options = parser.getOptions(__getArgs(cmd));
		Assert.assertEquals(1, options.size());
		Assert.assertEquals(1, options.get(0).getParams().size());
		Assert.assertEquals("abcd efg", options.get(0).getParams().get(0));
		// ---------------------------------------------------------------------
	}
	
	@Test
	public void duplicateOption() {
		String cmd;
		ParsingException err;
		
		// ---------------------------------------------------------------------
		err = null;
		
		try {	
			cmd = "-c /tmp/file1 /tmp/file2 --copy /tmp/file3 /tmp/file4 --extract-files /tmp/filex1 /tmp/filex2 /tmp/filex3 /tmp/filex4 --extract-files aaaa";
			parser.getOptions(__getArgs(cmd));
		} catch (ParsingException ex) {
			err = ex;
		}
		
		Assert.assertNotNull(err);
		Assert.assertEquals("Option already set: --extract-files", err.getMessage());
		// ---------------------------------------------------------------------
	}
	
	@Test
	public void missingOption() {
		String cmd;
		ParsingException err;
		
		// ---------------------------------------------------------------------
		err = null;
		
		try {	
			cmd = "/tmp/file1 /tmp/file2 --c /tmp/file3 /tmp/file4 --extract-files /tmp/filex1 /tmp/filex2 /tmp/filex3 /tmp/filex4";
			parser.getOptions(__getArgs(cmd));
		} catch (ParsingException ex) {
			err = ex;
		}
		
		Assert.assertNotNull(err);
		Assert.assertEquals("Missing option for parameter: /tmp/file1", err.getMessage());
		// ---------------------------------------------------------------------
	}
	
	@Test
	public void unknownOption() {
		String cmd;
		ParsingException err;
		
		// ---------------------------------------------------------------------
		err = null;
		
		try {	
			cmd = "-l /tmp/file1 /tmp/file2 --copy /tmp/file3 /tmp/file4 --extract-files /tmp/filex1 /tmp/filex2 /tmp/filex3 /tmp/filex4";
			parser.getOptions(__getArgs(cmd));
		} catch (ParsingException ex) {
			err = ex;
		}
		
		Assert.assertNotNull(err);
		Assert.assertEquals("Unknown option: -l", err.getMessage());
		// ---------------------------------------------------------------------
		
		// ---------------------------------------------------------------------
		err = null;
		
		try {	
			cmd = "-c /tmp/file1 /tmp/file2 -l /tmp/file3 /tmp/file4 --extract-files /tmp/filex1 /tmp/filex2 /tmp/filex3 /tmp/filex4";
			parser.getOptions(__getArgs(cmd));
		} catch (ParsingException ex) {
			err = ex;
		}
		
		Assert.assertNotNull(err);
		Assert.assertEquals("Unknown option: -l", err.getMessage());
		// ---------------------------------------------------------------------
		
		// ---------------------------------------------------------------------
		err = null;
		
		try {	
			cmd = "-c /tmp/file1 /tmp/file2 -c /tmp/file3 /tmp/file4 --unknown-option /tmp/filex1 /tmp/filex2 /tmp/filex3 /tmp/filex4";
			parser.getOptions(__getArgs(cmd));
		} catch (ParsingException ex) {
			err = ex;
		}
		
		Assert.assertNotNull(err);
		Assert.assertEquals("Unknown option: --unknown-option", err.getMessage());
		// ---------------------------------------------------------------------
	}
	
	@Test
	public void invalidOption() {
		String cmd;
		ParsingException err;
		
		// ---------------------------------------------------------------------
		err = null;
		
		try {	
			cmd = "-c /tmp/file1 /tmp/file2 --l /tmp/file3 /tmp/file4 --extract-files /tmp/filex1 /tmp/filex2 /tmp/filex3 /tmp/filex4";
			parser.getOptions(__getArgs(cmd));
		} catch (ParsingException ex) {
			err = ex;
		}
		
		Assert.assertNotNull(err);
		Assert.assertEquals("Invalid option: --l", err.getMessage());
		// ---------------------------------------------------------------------
		
	}
	
	@Test
	public void multipleShortOptionsOK() {
		String cmd;
		List<Option> options;
		ParsingException err;
		Option option;
		
		// ---------------------------------------------------------------------
		err = null;
		options = null;
		
		try {	
			cmd = "-cx /tmp/filex1 /tmp/filex2 /tmp/filex3 /tmp/filex4";
			options = parser.getOptions(__getArgs(cmd));
		} catch (ParsingException ex) {
			err = ex;
		}
		
		Assert.assertNull(err);
		Assert.assertNotNull(options);
		Assert.assertEquals(2, options.size());
		
		option = options.get(0);
		Assert.assertTrue(option.getDefinition() instanceof CopyOption);
		Assert.assertEquals(0, option.getParams().size());
		
		option = options.get(1);
		Assert.assertTrue(option.getDefinition() instanceof ExtractOption);
		Assert.assertEquals(4, option.getParams().size());
		Assert.assertEquals("/tmp/filex1", option.getParams().get(0));
		Assert.assertEquals("/tmp/filex2", option.getParams().get(1));
		Assert.assertEquals("/tmp/filex3", option.getParams().get(2));
		Assert.assertEquals("/tmp/filex4", option.getParams().get(3));
		// ---------------------------------------------------------------------
	}
	
	@Test
	public void multipleShortOptionsError() {
		String cmd;
		ParsingException err;
		
		err = null;
		
		try {	
			cmd = "-ccx /tmp/filex1 /tmp/filex2 /tmp/filex3 /tmp/filex4";
			parser.getOptions(__getArgs(cmd));
		} catch (ParsingException ex) {
			err = ex;
		}
			
		Assert.assertNotNull(err);
		Assert.assertEquals("Invalid short option sequence: -ccx", err.getMessage());
	}
	
	@Test
	public void testNoOptions() throws ParsingException {
		String cmd = "";
		List<Option> options = parser.getOptions(__getArgs(cmd));
		Assert.assertEquals(0, options.size());
	}
	
	@Test
	public void testHelpOption() throws ParsingException {		
		String cmd;
		List<Option> options;
		Option helpOption;
		
		// ---------------------------------------------------------------------
		cmd = "--help";
		ConsolePrinter.println(ConsoleColor.MAGENTA, cmd);
		options = parser.getOptions(__getArgs(cmd));
		helpOption = OptionParser.getOptionsByClass(options, CustomHelpOptionDefinition.class).get(0);
		Assert.assertEquals(0, helpOption.getParams().size());
		helpOption.exec();
		// ---------------------------------------------------------------------
		
		// ---------------------------------------------------------------------
		cmd = "-h";
		ConsolePrinter.println(ConsoleColor.MAGENTA, cmd);
		options = parser.getOptions(__getArgs(cmd));
		helpOption = OptionParser.getOptionsByClass(options, CustomHelpOptionDefinition.class).get(0);
		Assert.assertEquals(0, helpOption.getParams().size());
		helpOption.exec();
		// ---------------------------------------------------------------------
		
		// ---------------------------------------------------------------------
		cmd = "--help c";
		ConsolePrinter.println(ConsoleColor.MAGENTA, cmd);
		options = parser.getOptions(__getArgs(cmd));
		helpOption = OptionParser.getOptionsByClass(options, CustomHelpOptionDefinition.class).get(0);
		Assert.assertEquals(1, helpOption.getParams().size());
		helpOption.exec();
		// ---------------------------------------------------------------------
		
		// ---------------------------------------------------------------------
		cmd = "--help copy";
		ConsolePrinter.println(ConsoleColor.MAGENTA, cmd);
		options = parser.getOptions(__getArgs(cmd));
		helpOption = OptionParser.getOptionsByClass(options, CustomHelpOptionDefinition.class).get(0);
		Assert.assertEquals(1, helpOption.getParams().size());
		helpOption.exec();
		// ---------------------------------------------------------------------
		
		// ---------------------------------------------------------------------
		cmd = "-h c";
		ConsolePrinter.println(ConsoleColor.MAGENTA, cmd);
		options = parser.getOptions(__getArgs(cmd));
		helpOption = OptionParser.getOptionsByClass(options, CustomHelpOptionDefinition.class).get(0);
		Assert.assertEquals(1, helpOption.getParams().size());
		helpOption.exec();
		// ---------------------------------------------------------------------
		
		// ---------------------------------------------------------------------
		cmd = "-h copy";
		ConsolePrinter.println(ConsoleColor.MAGENTA, cmd);
		options = parser.getOptions(__getArgs(cmd));
		helpOption = OptionParser.getOptionsByClass(options, CustomHelpOptionDefinition.class).get(0);
		Assert.assertEquals(1, helpOption.getParams().size());
		helpOption.exec();
		// ---------------------------------------------------------------------
		
		// ---------------------------------------------------------------------
		cmd = "--help x";
		ConsolePrinter.println(ConsoleColor.MAGENTA, cmd);
		options = parser.getOptions(__getArgs(cmd));
		helpOption = OptionParser.getOptionsByClass(options, CustomHelpOptionDefinition.class).get(0);
		Assert.assertEquals(1, helpOption.getParams().size());
		helpOption.exec();
		// ---------------------------------------------------------------------
		
		// ---------------------------------------------------------------------
		cmd = "--help extract-files";
		ConsolePrinter.println(ConsoleColor.MAGENTA, cmd);
		options = parser.getOptions(__getArgs(cmd));
		helpOption = OptionParser.getOptionsByClass(options, CustomHelpOptionDefinition.class).get(0);
		Assert.assertEquals(1, helpOption.getParams().size());
		helpOption.exec();
		// ---------------------------------------------------------------------
		
		// ---------------------------------------------------------------------
		cmd = "-h x";
		ConsolePrinter.println(ConsoleColor.MAGENTA, cmd);
		options = parser.getOptions(__getArgs(cmd));
		helpOption = OptionParser.getOptionsByClass(options, CustomHelpOptionDefinition.class).get(0);
		Assert.assertEquals(1, helpOption.getParams().size());
		helpOption.exec();
		// ---------------------------------------------------------------------
		
		// ---------------------------------------------------------------------
		cmd = "-h extract-files";
		ConsolePrinter.println(ConsoleColor.MAGENTA, cmd);
		options = parser.getOptions(__getArgs(cmd));
		helpOption = OptionParser.getOptionsByClass(options, CustomHelpOptionDefinition.class).get(0);
		Assert.assertEquals(1, helpOption.getParams().size());
		helpOption.exec();
		// ---------------------------------------------------------------------
	}
}
