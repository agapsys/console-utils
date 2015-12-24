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

/**
 * @author Leandro Oliveira (leandro@agapsys.com)
 */
public class Test {
	private static String[] getArgs(String cmd) {
		return cmd.split(" ");
	}

	public static void main(String...args) throws ParsingException {
		//String cmd = "-c /tmp/file1 /tmp/file2 --copy /tmp/file3 /tmp/file4 --extract-files /tmp/filex1 /tmp/filex2 /tmp/filex3 /tmp/filex4"; // OK
		//String cmd = "-c /tmp/file1 /tmp/file2 --c /tmp/file3 /tmp/file4 --extract-files /tmp/filex1 /tmp/filex2 /tmp/filex3 /tmp/filex4"; // OK
		//String cmd = "-c /tmp/file1 /tmp/file2 --copy /tmp/file3 /tmp/file4 --extract-files /tmp/filex1 /tmp/filex2 /tmp/filex3 /tmp/filex4 --extract-files aaaa"; // duplicate extract-files
		//String cmd = "/tmp/file1 /tmp/file2 --c /tmp/file3 /tmp/file4 --extract-files /tmp/filex1 /tmp/filex2 /tmp/filex3 /tmp/filex4"; // Missing option
		//String cmd = "-l /tmp/file1 /tmp/file2 --copy /tmp/file3 /tmp/file4 --extract-files /tmp/filex1 /tmp/filex2 /tmp/filex3 /tmp/filex4"; // unknown option -l
		//String cmd = "-c /tmp/file1 /tmp/file2 --l /tmp/file3 /tmp/file4 --extract-files /tmp/filex1 /tmp/filex2 /tmp/filex3 /tmp/filex4"; // Invalid name for long option --l
		//String cmd = "-c /tmp/file1 /tmp/file2 -l /tmp/file3 /tmp/file4 --extract-files /tmp/filex1 /tmp/filex2 /tmp/filex3 /tmp/filex4"; //  unknown option -l
		String cmd = "-c /tmp/file1 /tmp/file2 -c /tmp/file3 /tmp/file4 --unknown-option /tmp/filex1 /tmp/filex2 /tmp/filex3 /tmp/filex4"; //  unknown option --unknown-option

//		OptionParser parser = new OptionParserBuilder()
//			.addOptionDefinition(CopyOption.class)
//			.addOptionDefinition(ExtractOption.class)
//			//.addOptionDefinition(HelpOptionDefinition.class)
//			.build();
//
//		List<Option> options = parser.getOptions(getArgs(cmd));
//		int a = 0;
	}
}
