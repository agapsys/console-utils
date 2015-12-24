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

package com.agapsys.utils.console.printer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class ConsolePrinterTest {
	private PrintStream printStream;
	private ByteArrayOutputStream outContent;
	private PrintStream originalStream = null;
	
	@Before
	public void before() {
		if (originalStream == null) {
			originalStream = System.out;
		}
		
		outContent = new ByteArrayOutputStream();
		printStream = new PrintStream(outContent);
		System.setOut(printStream);
	}
	
	@After
	public void after() {
		try {
			outContent.flush();
			outContent.close();
			printStream.close();
			System.setOut(originalStream);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	private void __clearOutput() {
		after();
		before();
	}
	
	@Test
	public void testToString() {
		String result;
		String expected;
		// ---------------------------------------------------------------------
		result = ConsolePrinter.toString("Hello world!");
		expected = "Hello world!";
		assertEquals(expected, result);
		// ---------------------------------------------------------------------
		
		// ---------------------------------------------------------------------
		result = ConsolePrinter.toString("Hello %s", "world!");
		expected = "Hello world!";
		assertEquals(expected, result);
		// ---------------------------------------------------------------------
		
		// ---------------------------------------------------------------------
		result = ConsolePrinter.toString(ConsoleColor.YELLOW, "Hello %s", "world!");
		expected = String.format("\033[%dmHello world!\033[0m", ConsoleColor.YELLOW.getFgCode());
		assertEquals(expected, result);
		// ---------------------------------------------------------------------
		
		// ---------------------------------------------------------------------
		result = ConsolePrinter.toString(ConsoleColor.YELLOW, ConsoleColor.GREEN, "Hello %s", "world!");
		expected = String.format("\033[%d;%dmHello world!\033[0m", ConsoleColor.GREEN.getBgCode(), ConsoleColor.YELLOW.getFgCode());
		assertEquals(expected, result);
		// ---------------------------------------------------------------------
		
		// ---------------------------------------------------------------------
		result = ConsolePrinter.toString(ConsoleColor.YELLOW, ConsoleColor.GREEN, "Hello\n%s", "world!");
		expected = String.format("\033[%d;%dmHello\033[0m\n" + "\033[%d;%dmworld!\033[0m",
			ConsoleColor.GREEN.getBgCode(), ConsoleColor.YELLOW.getFgCode(),
			ConsoleColor.GREEN.getBgCode(), ConsoleColor.YELLOW.getFgCode()
		);
		assertEquals(expected, result);
		// ---------------------------------------------------------------------
	}
	
	@Test
	public void testPrintln() {
		// ---------------------------------------------------------------------
		ConsolePrinter.println("OUT: abc");
		ConsolePrinter.println("OUT: def");
		assertEquals("OUT: abc\nOUT: def\n", outContent.toString());
		__clearOutput();
		// ---------------------------------------------------------------------
		
		// ---------------------------------------------------------------------
		ConsolePrinter.println("OUT: %s", "abc");
		assertEquals("OUT: abc\n", outContent.toString());
		__clearOutput();
		// ---------------------------------------------------------------------
		
		// ---------------------------------------------------------------------
		ConsolePrinter.println(ConsoleColor.YELLOW, "OUT: %s", "abc");
		String expected = String.format("\033[%dmOUT: abc\033[0m\n", ConsoleColor.YELLOW.getFgCode());
		assertEquals(expected, outContent.toString());
		__clearOutput();
		// ---------------------------------------------------------------------
	}
	
	@Test
	public void testPrint() {
		// ---------------------------------------------------------------------
		ConsolePrinter.print("OUT: abc");
		ConsolePrinter.print("OUT: def");
		assertEquals("OUT: abcOUT: def", outContent.toString());
		__clearOutput();
		// ---------------------------------------------------------------------
		
		// ---------------------------------------------------------------------
		ConsolePrinter.print("OUT: %s", "abc");
		assertEquals("OUT: abc", outContent.toString());
		__clearOutput();
		// ---------------------------------------------------------------------
		
		// ---------------------------------------------------------------------
		ConsolePrinter.print(ConsoleColor.YELLOW, "OUT: %s", "abc");
		String expected = String.format("\033[%dmOUT: abc\033[0m", ConsoleColor.YELLOW.getFgCode());
		assertEquals(expected, outContent.toString());
		__clearOutput();
		// ---------------------------------------------------------------------
	}
}
