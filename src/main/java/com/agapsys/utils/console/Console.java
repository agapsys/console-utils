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
	/** 
	 * Prints a message in system console.
	 * @param msg message to be printed.
	 */
	public static void print(String msg) {
		System.out.print(msg);
	}
	
	/**
	 * Prints a formatted message in system console.
	 * @param format format string
	 * @param args parameters to be added to final string
	 * @see String#format(String, Object...)
	 */
	public static void printf(String format, Object...args) {
		print(String.format(format, args));
	}
	// -------------------------------------------------------------------------
	
	/** 
	 * Prints a message in system console. 
	 * A line break will be appended at the end of the message.
	 * @param msg message to be printed.
	 */
	public static void println(String msg) {
		System.out.println(msg);
	}
	
	/**
	 * Prints a formatted message in system console. 
	 * A line break will be appended at the end of the message.
	 * @param format format string
	 * @param args parameters to be added to final string
	 * @see String#format(String, Object...)
	 */
	public static void printlnf(String format, Object...args) {
		println(String.format(format, args));
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
