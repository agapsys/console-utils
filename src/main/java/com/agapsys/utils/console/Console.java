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

import java.util.Scanner;

/**
 * Console utilities
 * @author Leandro Oliveira (leandro@agapsys.com)
 */
public class Console {
	// CLASS SCOPE =============================================================
	public static String getOutputString(ConsoleColor fgColor, String message, Object...args) {
		if (args.length > 0)
			message = String.format(message, args);
		
		return new FormatEscapeBuilder().setFgColor(fgColor).escape(message);
	}
	
	public static void print(ConsoleColor fgColor, String message, Object...args) {
		System.out.print(getOutputString(fgColor, message, args));
	}
	
	public static void print(String message, Object...args) {
		print(ConsoleColor.DEFAULT, message, args);
	}
	
	public static void println(ConsoleColor fgColor, String message, Object...args) {
		System.out.println(getOutputString(fgColor, message, args));
	}
	
	public static void println(String message, Object...args) {
		println(ConsoleColor.DEFAULT, message, args);
	}
	
	private static Scanner scanner = null;	
	public static Scanner getScanner() {
		if (scanner == null)
			scanner = new Scanner(System.in);
		
		return scanner;
	}
	// =========================================================================

	// INSTANCE SCOPE ==========================================================
	private Console() {}
	// =========================================================================
}
