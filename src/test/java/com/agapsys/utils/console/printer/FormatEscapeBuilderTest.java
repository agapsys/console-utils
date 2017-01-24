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

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class FormatEscapeBuilderTest {
    @Test
    public void emptyAttrs() {
        String msg = new FormatEscapeBuilder().toString("Hello world");
        assertEquals("Hello world", msg);
    }
    
    @Test
    public void fgColorTest() {
        String msg = "Hello world";
        msg = new FormatEscapeBuilder().setFgColor(ConsoleColor.CYAN).toString(msg);
        String expected = String.format("\033[%dmHello world\033[0m", ConsoleColor.CYAN.getFgCode());
        assertEquals(expected, msg);
    }
    
    @Test
    public void bgColorTest() {
        String msg = "Hello world";
        msg = new FormatEscapeBuilder().setBgColor(ConsoleColor.CYAN).toString(msg);
        String expected = String.format("\033[%dmHello world\033[0m", ConsoleColor.CYAN.getBgCode());
        assertEquals(expected, msg);
    }
    
    @Test
    public void fgBgColorTest() {
        // Fg, Bg
        String msg = new FormatEscapeBuilder().setFgColor(ConsoleColor.CYAN).setBgColor(ConsoleColor.CYAN).toString("Hello world");
        String expected = String.format("\033[%d;%dmHello world\033[0m", ConsoleColor.CYAN.getBgCode(), ConsoleColor.CYAN.getFgCode());
        assertEquals(expected, msg);
        
        // Bg, Fg
        msg = new FormatEscapeBuilder().setBgColor(ConsoleColor.CYAN).setFgColor(ConsoleColor.CYAN).toString("Hello world");
        expected = String.format("\033[%d;%dmHello world\033[0m", ConsoleColor.CYAN.getBgCode(), ConsoleColor.CYAN.getFgCode());
        assertEquals(expected, msg);
    }
    
    @Test
    public void boldTest() {
        // bold == true
        String msg = new FormatEscapeBuilder().setBold(true).toString("Hello world");
        String expected = String.format("\033[%dmHello world\033[0m", ConsoleFormat.BOLD.getSetCode());
        assertEquals(expected, msg);
        
        // bold == true
        msg = new FormatEscapeBuilder().setBold().toString("Hello world");
        expected = String.format("\033[%dmHello world\033[0m", ConsoleFormat.BOLD.getSetCode());
        assertEquals(expected, msg);
        
        // bold == false
        msg = new FormatEscapeBuilder().setBold(false).toString("Hello world");
        expected = String.format("\033[%dmHello world\033[0m", ConsoleFormat.BOLD.getResetCode());
        assertEquals(expected, msg);
    }
    
    @Test
    public void boldUnderlined() {
        // bold == true, underlined == true
        String msg = new FormatEscapeBuilder().setBold(true).setUnderlined(true).toString("Hello world");
        String expected = String.format("\033[%d;%dmHello world\033[0m", ConsoleFormat.BOLD.getSetCode(), ConsoleFormat.UNDERLINED.getSetCode());
        assertEquals(expected, msg);
        
        // bold == true, underlined == true
        msg = new FormatEscapeBuilder().setBold().setUnderlined().toString("Hello world");
        expected = String.format("\033[%d;%dmHello world\033[0m", ConsoleFormat.BOLD.getSetCode(), ConsoleFormat.UNDERLINED.getSetCode());
        assertEquals(expected, msg);
        
        
        // bold == true, underlined == false
        msg = new FormatEscapeBuilder().setBold(true).setUnderlined(false).toString("Hello world");
        expected = String.format("\033[%d;%dmHello world\033[0m", ConsoleFormat.BOLD.getSetCode(), ConsoleFormat.UNDERLINED.getResetCode());
        assertEquals(expected, msg);
        
        // bold == true, underlined == false
        msg = new FormatEscapeBuilder().setBold().setUnderlined(false).toString("Hello world");
        expected = String.format("\033[%d;%dmHello world\033[0m", ConsoleFormat.BOLD.getSetCode(), ConsoleFormat.UNDERLINED.getResetCode());
        assertEquals(expected, msg);
        
        // bold == false, underlined == false
        msg = new FormatEscapeBuilder().setBold(false).setUnderlined(false).toString("Hello world");
        expected = String.format("\033[%d;%dmHello world\033[0m", ConsoleFormat.BOLD.getResetCode(), ConsoleFormat.UNDERLINED.getResetCode());
        assertEquals(expected, msg);
    }
    
    @Test
    public void fgBgBoldUnderline() {
        String msg = new FormatEscapeBuilder().setBgColor(ConsoleColor.RED).setFgColor(ConsoleColor.YELLOW).setBold().setUnderlined().toString("Hello world");
        String expected = String.format("\033[%d;%d;%d;%dmHello world\033[0m",
            ConsoleColor.RED.getBgCode(),
            ConsoleColor.YELLOW.getFgCode(),
            ConsoleFormat.BOLD.getSetCode(), 
            ConsoleFormat.UNDERLINED.getSetCode()
        );
        
        assertEquals(expected, msg);
    }
    
    @Test
    public void fgBgBoldUnderlineWithNewLine() {
        String msg = new FormatEscapeBuilder().setBgColor(ConsoleColor.RED).setFgColor(ConsoleColor.YELLOW).setBold().setUnderlined().toString("Hello\n%s", "world!");
        
        String lineFormat = String.format("\033[%d;%d;%d;%dm%%s\033[0m",
            ConsoleColor.RED.getBgCode(),
            ConsoleColor.YELLOW.getFgCode(),
            ConsoleFormat.BOLD.getSetCode(), 
            ConsoleFormat.UNDERLINED.getSetCode()
        );
                
        String expected = String.format("%s\n%s",
            String.format(lineFormat, "Hello"),
            String.format(lineFormat, "world!")
        );
        
        assertEquals(expected, msg);
        
        String colorString = ConsolePrinter.toString(ConsoleColor.CYAN, "aaaaa0\nbbbbbbbbbbbb\nccccccccccccccccc");
        StringBuilder sb = new StringBuilder();
        sb.append("\033[36maaaaa0\033[0m\n");
        sb.append("\033[36mbbbbbbbbbbbb\033[0m\n");
        sb.append("\033[36mccccccccccccccccc\033[0m");
        
        assertEquals(sb.toString(), colorString);
        ConsolePrinter.println(sb.toString());
    }
}
