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

import com.agapsys.utils.console.printer.tables.CellAlignment;
import com.agapsys.utils.console.printer.tables.RowBuilder;
import com.agapsys.utils.console.printer.tables.TableBuilder;
import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;

public class TableTest {
    @Test
    public void printTable() {
        TableBuilder tb = new TableBuilder()
            .addCol(2)
            .addCol(1)
            .addCol(11)
            .addCol(1)
            .addCol(40)
            .addCol(1)
            .addCol(3);
        
        RowBuilder lineBorder = new RowBuilder(tb)
            .addCell("--")
            .addCell("+")
            .addCell("-----------")
            .addCell("+")
            .addCell(StringUtils.repeat("-", 40))
            .addCell("+")
            .addCell("---");
        
        tb.addRow()
            .addCell(ConsoleColor.CYAN, "ID")
            .addCell()
            .addCell(ConsoleColor.CYAN, "DATE")
            .addCell()
            .addCell(ConsoleColor.CYAN, "NAME")
            .addCell()
            .addCell(ConsoleColor.CYAN, "G")
        .endRow()
        .addRow(lineBorder)
        .addRow()
            .addCell("1")
            .addCell()
            .addCell("abcd-ef-gh ")
            .addCell()
            .addCell("aaaaa bbbbbb cccccc dd eeeeeeee f gggg hhhh i jjjjj kk lll mm nnnn")
            .addCell()
            .addCell(CellAlignment.RIGHT, ConsoleColor.WHITE, ConsoleColor.RED, "f")
        .endRow()
        .addRow(lineBorder)
        .addRow()
            .addCell("2")
            .addCell()
            .addCell("ijkl-mn-op ")
            .addCell()
            .addCell("ooooooo pppp qqqqqq rr ssssssss")
            .addCell()
            .addCell(ConsoleColor.LIGHT_YELLOW, "m")
        .endRow()
        .addRow(lineBorder)
        .addRow()
            .addCell("3")
            .addCell()
            .addCell("qrst-uv-wx ")
            .addCell()
            .addCell("tttttt uuuuuu vv wwwwwwww")
            .addCell()
            .addCell(ConsoleColor.RED, "m")
        .endRow();
        
        StringBuilder expected = new StringBuilder();
        expected.append("\033[49;36mID\033[0m\033[49;39m \033[0m\033[49;36mDATE       \033[0m\033[49;39m \033[0m\033[49;36mNAME                                    \033[0m\033[49;39m \033[0m\033[49;36mG  \033[0m\n");
        expected.append("\033[49;39m--\033[0m\033[49;39m+\033[0m\033[49;39m-----------\033[0m\033[49;39m+\033[0m\033[49;39m----------------------------------------\033[0m\033[49;39m+\033[0m\033[49;39m---\033[0m\n");
        expected.append("\033[49;39m1 \033[0m\033[49;39m \033[0m\033[49;39mabcd-ef-gh \033[0m\033[49;39m \033[0m\033[49;39maaaaa bbbbbb cccccc dd eeeeeeee f gggg  \033[0m\033[49;39m \033[0m\033[41;97m  f\033[0m\n");
        expected.append("\033[49;39m  \033[0m\033[49;39m \033[0m\033[49;39m           \033[0m\033[49;39m \033[0m\033[49;39mhhhh i jjjjj kk lll mm nnnn             \033[0m\033[49;39m \033[0m\033[49;39m   \033[0m\n");
        expected.append("\033[49;39m--\033[0m\033[49;39m+\033[0m\033[49;39m-----------\033[0m\033[49;39m+\033[0m\033[49;39m----------------------------------------\033[0m\033[49;39m+\033[0m\033[49;39m---\033[0m\n");
        expected.append("\033[49;39m2 \033[0m\033[49;39m \033[0m\033[49;39mijkl-mn-op \033[0m\033[49;39m \033[0m\033[49;39mooooooo pppp qqqqqq rr ssssssss         \033[0m\033[49;39m \033[0m\033[49;93mm  \033[0m\n");
        expected.append("\033[49;39m--\033[0m\033[49;39m+\033[0m\033[49;39m-----------\033[0m\033[49;39m+\033[0m\033[49;39m----------------------------------------\033[0m\033[49;39m+\033[0m\033[49;39m---\033[0m\n");
        expected.append("\033[49;39m3 \033[0m\033[49;39m \033[0m\033[49;39mqrst-uv-wx \033[0m\033[49;39m \033[0m\033[49;39mtttttt uuuuuu vv wwwwwwww               \033[0m\033[49;39m \033[0m\033[49;31mm  \033[0m");
        
        Assert.assertEquals(expected.toString(), tb.toString());
        ConsolePrinter.println(tb.toString());
    }
}
