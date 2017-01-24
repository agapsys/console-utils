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

/**
 * ConsolePrinter utilities.
 */
public class ConsolePrinter {
    // CLASS SCOPE =============================================================
    public static String toString(String str, Object...strArgs) {
        if (strArgs.length > 0)
            str = String.format(str, strArgs);

        return str;
    }

    public static String toString(ConsoleColor fgColor, ConsoleColor bgColor, String message, Object...args) {
        return new FormatEscapeBuilder().setFgColor(fgColor).setBgColor(bgColor).toString(ConsolePrinter.toString(message, args));
    }

    public static String toString(ConsoleColor fgColor, String message, Object...args) {
        return new FormatEscapeBuilder().setFgColor(fgColor).toString(ConsolePrinter.toString(message, args));
    }


    public static void print(ConsoleColor fgColor, ConsoleColor bgColor, String message, Object...args) {
        System.out.print(new FormatEscapeBuilder().setFgColor(fgColor).setBgColor(bgColor).toString(ConsolePrinter.toString(message, args)));
    }

    public static void print(ConsoleColor fgColor, String message, Object...args) {
        System.out.print(new FormatEscapeBuilder().setFgColor(fgColor).toString(ConsolePrinter.toString(message, args)));
    }

    public static void print(String message, Object...args) {
        System.out.print(ConsolePrinter.toString(message, args));
    }


    public static void println(ConsoleColor fgColor, ConsoleColor bgColor, String message, Object...args) {
        System.out.println(new FormatEscapeBuilder().setFgColor(fgColor).setBgColor(bgColor).toString(toString(message, args)));
    }

    public static void println(ConsoleColor fgColor, String message, Object...args) {
        System.out.println(new FormatEscapeBuilder().setFgColor(fgColor).toString(toString(message, args)));
    }

    public static void println(String message, Object...args) {
        System.out.println(toString(message, args));
    }
    // =========================================================================

    // INSTANCE SCOPE ==========================================================
    private ConsolePrinter() {}
    // =========================================================================
}
