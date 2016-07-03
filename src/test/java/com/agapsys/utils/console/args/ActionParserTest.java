/*
 * Copyright 2015 Leandro José Britto de Oliveira.
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

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Leandro Oliveira (ljbo.82@gmail.com)
 */
public class ActionParserTest {
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
	private final ActionParser commandParser = ActionParser.getInstance();
	
	@Test
	public void no_args() {
		Action action;
		String cmdStr;
		Throwable error;
		
		// ---------------------------------------------------------------------
		error = null;
		action   = null;
		
		try {	
			cmdStr = "";
			action = commandParser.getAction(__getArgs(cmdStr));
		} catch (ParsingException ex) {
			error = ex;
		}
		
		Assert.assertNull(error);
		
		Assert.assertNotNull(action);
		Assert.assertEquals(null, action.getName());
		Assert.assertEquals(0, action.getArgs().size());
		
		Assert.assertEquals(0, action.getSubActions().size());
	}
	
	@Test
	public void empty_action() {
		String cmdStr;
		Throwable error;
		
		// ---------------------------------------------------------------------
		error = null;
		
		try {	
			cmdStr = "\" \" \"a\" \"b\"";
			commandParser.getAction(__getArgs(cmdStr));
		} catch (ParsingException ex) {
			error = ex;
		}
		
		Assert.assertNotNull(error);
		Assert.assertTrue(error.getMessage().toLowerCase().contains("empty action name"));
	}
	
	@Test
	public void simple_action_without_subActions() {
		Action action;
		String cmdStr;
		Throwable error;
		
		// ---------------------------------------------------------------------
		error = null;
		action   = null;
		
		try {	
			cmdStr = "action aArg1 aArg2";
			action = commandParser.getAction(__getArgs(cmdStr));
		} catch (ParsingException ex) {
			error = ex;
		}
		
		Assert.assertNull(error);
		
		Assert.assertNotNull(action);
		Assert.assertEquals("action", action.getName());
		
		Assert.assertEquals(2, action.getArgs().size());
		Assert.assertEquals("aArg1", action.getArgs().get(0));
		Assert.assertEquals("aArg2", action.getArgs().get(1));
		
		Assert.assertEquals(0, action.getSubActions().size());
	}
	
	@Test
	public void action_without_name_and_simple_subActions() {
		Action action;
		String cmdStr;
		Throwable error;
		
		// ---------------------------------------------------------------------
		error = null;
		action   = null;
		
		try {	
			cmdStr = "-s sArg1 sArg2 sArg3 --long-option1 lArg1 lArg2 lArg3";
			action = commandParser.getAction(__getArgs(cmdStr));
		} catch (ParsingException ex) {
			error = ex;
		}
		
		Assert.assertNull(error);
		
		Assert.assertNotNull(action);
		Assert.assertEquals(null, action.getName());
		Assert.assertEquals(0, action.getArgs().size());
		
		Assert.assertEquals(2, action.getSubActions().size());
		
		Assert.assertNotNull(action.getSubActions().get("s"));
		Assert.assertEquals(3, action.getSubActions().get("s").size());
		Assert.assertEquals("sArg1", action.getSubActions().get("s").get(0));
		Assert.assertEquals("sArg2", action.getSubActions().get("s").get(1));
		Assert.assertEquals("sArg3", action.getSubActions().get("s").get(2));
		
		Assert.assertEquals(3, action.getSubActions().get("long-option1").size());
		Assert.assertEquals("lArg1", action.getSubActions().get("long-option1").get(0));
		Assert.assertEquals("lArg2", action.getSubActions().get("long-option1").get(1));
		Assert.assertEquals("lArg3", action.getSubActions().get("long-option1").get(2));
	}
	
	@Test
	public void passing_args_to_short_sequence() {
		String cmdStr;
		Throwable error;
		
		// ---------------------------------------------------------------------
		error = null;
		
		try {	
			cmdStr = "-abcd sSeqArg1 sSeqArg2 --long-option1 lArg1 lArg2 lArg3";
			commandParser.getAction(__getArgs(cmdStr));
		} catch (ParsingException ex) {
			error = ex;
		}
		
		Assert.assertNotNull(error);
		Assert.assertEquals("Short option sequence \"-abcd\" does not allow args", error.getMessage());
		// ---------------------------------------------------------------------
		error = null;
		
		try {	
			cmdStr = "--long-option1 lArg1 lArg2 lArg3 -abcd sSeqArg1 sSeqArg2";
			commandParser.getAction(__getArgs(cmdStr));
		} catch (ParsingException ex) {
			error = ex;
		}
		
		Assert.assertNotNull(error);
		Assert.assertEquals("Short option sequence \"-abcd\" does not allow args", error.getMessage());
	}
	
	@Test
	public void action_without_name_and_repetitive_subActions() {
		Action action;
		String cmdStr;
		Throwable error;
		
		// ---------------------------------------------------------------------
		error  = null;
		action = null;
		
		try {	
			cmdStr = "-s sArg1 sArg2 sArg3 -s sArg1 sArg2 sArg4 sArg5 --long-option1  lArg1 lArg2 lArg3 --long-option1 lArg1 lArg2 lArg4 lArg5 --long-option2";
			action = commandParser.getAction(__getArgs(cmdStr));
		} catch (ParsingException ex) {
			error = ex;
		}
		
		Assert.assertNull(error);
		
		Assert.assertNotNull(action);
		Assert.assertEquals(null, action.getName());
		Assert.assertEquals(0, action.getArgs().size());
		
		Assert.assertEquals(3, action.getSubActions().size());
		Assert.assertNotNull(action.getSubActions().get("s"));
		Assert.assertNotNull(action.getSubActions().get("long-option1"));
		Assert.assertNotNull(action.getSubActions().get("long-option2"));
		
		Assert.assertEquals(5, action.getSubActions().get("s").size());
		Assert.assertEquals("sArg1", action.getSubActions().get("s").get(0));
		Assert.assertEquals("sArg2", action.getSubActions().get("s").get(1));
		Assert.assertEquals("sArg3", action.getSubActions().get("s").get(2));
		Assert.assertEquals("sArg4", action.getSubActions().get("s").get(3));
		Assert.assertEquals("sArg5", action.getSubActions().get("s").get(4));
		
		Assert.assertEquals(5, action.getSubActions().get("long-option1").size());
		Assert.assertEquals("lArg1", action.getSubActions().get("long-option1").get(0));
		Assert.assertEquals("lArg2", action.getSubActions().get("long-option1").get(1));
		Assert.assertEquals("lArg3", action.getSubActions().get("long-option1").get(2));
		Assert.assertEquals("lArg4", action.getSubActions().get("long-option1").get(3));
		Assert.assertEquals("lArg5", action.getSubActions().get("long-option1").get(4));
		
		Assert.assertEquals(0, action.getSubActions().get("long-option2").size());
		// ---------------------------------------------------------------------
		error  = null;
		action = null;
		
		try {	
			cmdStr = "-s sArg1 sArg2 sArg3 -s sArg1 sArg2 sArg4 sArg5 --long-option1  lArg1 lArg2 lArg3 --long-option1 lArg1 lArg2 lArg4 lArg5";
			action = commandParser.getAction(__getArgs(cmdStr));
		} catch (ParsingException ex) {
			error = ex;
		}
		
		Assert.assertNull(error);
		
		Assert.assertNotNull(action);
		Assert.assertEquals(null, action.getName());
		Assert.assertEquals(0, action.getArgs().size());
		
		Assert.assertEquals(2, action.getSubActions().size());
		Assert.assertNotNull(action.getSubActions().get("s"));
		Assert.assertNotNull(action.getSubActions().get("long-option1"));
		
		Assert.assertEquals(5, action.getSubActions().get("s").size());
		Assert.assertEquals("sArg1", action.getSubActions().get("s").get(0));
		Assert.assertEquals("sArg2", action.getSubActions().get("s").get(1));
		Assert.assertEquals("sArg3", action.getSubActions().get("s").get(2));
		Assert.assertEquals("sArg4", action.getSubActions().get("s").get(3));
		Assert.assertEquals("sArg5", action.getSubActions().get("s").get(4));
		
		Assert.assertEquals(5, action.getSubActions().get("long-option1").size());
		Assert.assertEquals("lArg1", action.getSubActions().get("long-option1").get(0));
		Assert.assertEquals("lArg2", action.getSubActions().get("long-option1").get(1));
		Assert.assertEquals("lArg3", action.getSubActions().get("long-option1").get(2));
		Assert.assertEquals("lArg4", action.getSubActions().get("long-option1").get(3));
		Assert.assertEquals("lArg5", action.getSubActions().get("long-option1").get(4));
	}
	
	@Test
	public void full_args() {
		Action action;
		String cmdStr;
		Throwable error;
		
		// ---------------------------------------------------------------------
		error  = null;
		action = null;
		
		try {	
			cmdStr = "action aArg1 aArg2 -s sArg1 sArg2 sArg3 -s sArg1 sArg2 sArg4 sArg5  -e -abc --long-option1  lArg1 lArg2 lArg3 --long-option1 lArg1 lArg2 lArg4 lArg5 --long-option2";
			action = commandParser.getAction(__getArgs(cmdStr));
		} catch (ParsingException ex) {
			error = ex;
		}
		
		Assert.assertNull(error);
		
		Assert.assertNotNull(action);
		Assert.assertEquals("action", action.getName());
		Assert.assertEquals(2, action.getArgs().size());
		Assert.assertEquals("aArg1", action.getArgs().get(0));
		Assert.assertEquals("aArg2", action.getArgs().get(1));
		
		Assert.assertEquals(7, action.getSubActions().size());
		Assert.assertNotNull(action.getSubActions().get("s"));
		Assert.assertNotNull(action.getSubActions().get("e"));
		Assert.assertNotNull(action.getSubActions().get("a"));
		Assert.assertNotNull(action.getSubActions().get("b"));
		Assert.assertNotNull(action.getSubActions().get("c"));
		Assert.assertNotNull(action.getSubActions().get("long-option1"));
		Assert.assertNotNull(action.getSubActions().get("long-option2"));
		
		Assert.assertEquals(5, action.getSubActions().get("s").size());
		Assert.assertEquals("sArg1", action.getSubActions().get("s").get(0));
		Assert.assertEquals("sArg2", action.getSubActions().get("s").get(1));
		Assert.assertEquals("sArg3", action.getSubActions().get("s").get(2));
		Assert.assertEquals("sArg4", action.getSubActions().get("s").get(3));
		Assert.assertEquals("sArg5", action.getSubActions().get("s").get(4));
		
		Assert.assertEquals(5, action.getSubActions().get("long-option1").size());
		Assert.assertEquals("lArg1", action.getSubActions().get("long-option1").get(0));
		Assert.assertEquals("lArg2", action.getSubActions().get("long-option1").get(1));
		Assert.assertEquals("lArg3", action.getSubActions().get("long-option1").get(2));
		Assert.assertEquals("lArg4", action.getSubActions().get("long-option1").get(3));
		Assert.assertEquals("lArg5", action.getSubActions().get("long-option1").get(4));
		
		Assert.assertEquals(0, action.getSubActions().get("long-option2").size());
		Assert.assertEquals(0, action.getSubActions().get("e").size());
		Assert.assertEquals(0, action.getSubActions().get("a").size());
		Assert.assertEquals(0, action.getSubActions().get("b").size());
		Assert.assertEquals(0, action.getSubActions().get("c").size());

		// ---------------------------------------------------------------------
		error  = null;
		action = null;
		
		try {	
			cmdStr = "action aArg1 aArg2 -s sArg1 sArg2 sArg3 -s sArg1 sArg2 sArg4 sArg5  -e -abc --long-option1  lArg1 lArg2 lArg3 --long-option1 lArg1 lArg2 lArg4 lArg5";
			action = commandParser.getAction(__getArgs(cmdStr));
		} catch (ParsingException ex) {
			error = ex;
		}
		
		Assert.assertNull(error);
		
		Assert.assertNotNull(action);
		Assert.assertEquals("action", action.getName());
		Assert.assertEquals(2, action.getArgs().size());
		Assert.assertEquals("aArg1", action.getArgs().get(0));
		Assert.assertEquals("aArg2", action.getArgs().get(1));
		
		Assert.assertEquals(6, action.getSubActions().size());
		Assert.assertNotNull(action.getSubActions().get("s"));
		Assert.assertNotNull(action.getSubActions().get("e"));
		Assert.assertNotNull(action.getSubActions().get("a"));
		Assert.assertNotNull(action.getSubActions().get("b"));
		Assert.assertNotNull(action.getSubActions().get("c"));
		Assert.assertNotNull(action.getSubActions().get("long-option1"));
		
		Assert.assertEquals(5, action.getSubActions().get("s").size());
		Assert.assertEquals("sArg1", action.getSubActions().get("s").get(0));
		Assert.assertEquals("sArg2", action.getSubActions().get("s").get(1));
		Assert.assertEquals("sArg3", action.getSubActions().get("s").get(2));
		Assert.assertEquals("sArg4", action.getSubActions().get("s").get(3));
		Assert.assertEquals("sArg5", action.getSubActions().get("s").get(4));
		
		Assert.assertEquals(5, action.getSubActions().get("long-option1").size());
		Assert.assertEquals("lArg1", action.getSubActions().get("long-option1").get(0));
		Assert.assertEquals("lArg2", action.getSubActions().get("long-option1").get(1));
		Assert.assertEquals("lArg3", action.getSubActions().get("long-option1").get(2));
		Assert.assertEquals("lArg4", action.getSubActions().get("long-option1").get(3));
		Assert.assertEquals("lArg5", action.getSubActions().get("long-option1").get(4));
		
		Assert.assertEquals(0, action.getSubActions().get("e").size());
		Assert.assertEquals(0, action.getSubActions().get("a").size());
		Assert.assertEquals(0, action.getSubActions().get("b").size());
		Assert.assertEquals(0, action.getSubActions().get("c").size());
	}
	
	@Test
	public void test_unmodifiable() {
		Action action;
		String cmdStr;
		Throwable error;
		
		// ---------------------------------------------------------------------
		error  = null;
		action = null;
		
		try {	
			cmdStr = "action aArg1 aArg2 -s sArg1 sArg2 sArg3 -s sArg1 sArg2 sArg4 sArg5  -e -abc --long-option1  lArg1 lArg2 lArg3 --long-option1 lArg1 lArg2 lArg4 lArg5 --long-option2";
			action = commandParser.getAction(__getArgs(cmdStr));
		} catch (ParsingException ex) {
			error = ex;
		}
		Assert.assertNull(error);
		// ---------------------------------------------------------------------
		error = null;
		try {
			action.getArgs().clear();
		} catch (UnsupportedOperationException ex) {
			error = ex;
		}
		Assert.assertNotNull(error);
		// ---------------------------------------------------------------------
		error = null;
		try {
			action.getSubActions().clear();
		} catch (UnsupportedOperationException ex) {
			error = ex;
		}
		Assert.assertNotNull(error);
		// ---------------------------------------------------------------------
		error = null;
		try {
			action.getSubActions().get("s").clear();
		} catch (UnsupportedOperationException ex) {
			error = ex;
		}
		Assert.assertNotNull(error);
		// ---------------------------------------------------------------------
		error = null;
		try {
			action.getSubActions().get("e").clear();
		} catch (UnsupportedOperationException ex) {
			error = ex;
		}
		Assert.assertNotNull(error);
		// ---------------------------------------------------------------------
		error = null;
		try {
			action.getSubActions().get("a").clear();
		} catch (UnsupportedOperationException ex) {
			error = ex;
		}
		Assert.assertNotNull(error);
		// ---------------------------------------------------------------------
		error = null;
		try {
			action.getSubActions().get("b").clear();
		} catch (UnsupportedOperationException ex) {
			error = ex;
		}
		Assert.assertNotNull(error);
		// ---------------------------------------------------------------------
		error = null;
		try {
			action.getSubActions().get("c").clear();
		} catch (UnsupportedOperationException ex) {
			error = ex;
		}
		Assert.assertNotNull(error);
		// ---------------------------------------------------------------------
		error = null;
		try {
			action.getSubActions().get("long-option1").clear();
		} catch (UnsupportedOperationException ex) {
			error = ex;
		}
		Assert.assertNotNull(error);
		// ---------------------------------------------------------------------
		error = null;
		try {
			action.getSubActions().get("long-option2").clear();
		} catch (UnsupportedOperationException ex) {
			error = ex;
		}
		Assert.assertNotNull(error);
		// ---------------------------------------------------------------------
	}
}
