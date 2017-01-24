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

import java.util.regex.Pattern;

/**
 * Builds toString sequences for formatted console output.
 */
public class FormatEscapeBuilder {
    private ConsoleColor bgColor = null;
    private ConsoleColor fgColor = null;

    private Boolean bold       = null;
    private Boolean dim        = null;
    private Boolean underlined = null;
    private Boolean blink      = null;
    private Boolean reverse    = null;
    private Boolean hidden     = null;

    public FormatEscapeBuilder setBgColor(ConsoleColor bgColor) {
        if (bgColor == null)
            throw new IllegalArgumentException("bgColor == null");

        if (this.bgColor != null)
            throw new IllegalStateException("bgColor is already set");

        this.bgColor = bgColor;
        return this;
    }

    public FormatEscapeBuilder setFgColor(ConsoleColor fgColor) {
        if (fgColor == null)
            throw new IllegalArgumentException("fgColor == null");

        if (this.fgColor != null)
            throw new IllegalStateException("fgColor is already set");

        this.fgColor = fgColor;
        return this;
    }


    public FormatEscapeBuilder setBold() {
        return setBold(true);
    }

    public FormatEscapeBuilder setBold(boolean enabled) {
        if (this.bold != null)
            throw new IllegalStateException("property already set");

        this.bold = enabled;
        return this;
    }


    public FormatEscapeBuilder setDim() {
        return setDim(true);
    }

    public FormatEscapeBuilder setDim(boolean enabled) {
        if (this.dim != null)
            throw new IllegalStateException("property already set");

        this.dim = enabled;
        return this;
    }


    public FormatEscapeBuilder setUnderlined() {
        return setUnderlined(true);
    }

    public FormatEscapeBuilder setUnderlined(boolean enabled) {
        if (this.underlined != null)
            throw new IllegalStateException("property already set");

        this.underlined = enabled;
        return this;
    }


    public FormatEscapeBuilder setBlink() {
        return setBlink(true);
    }

    public FormatEscapeBuilder setBlink(boolean enabled) {
        if (this.blink != null)
            throw new IllegalStateException("property already set");

        this.blink = enabled;
        return this;
    }


    public FormatEscapeBuilder setReverse() {
        return setReverse(true);
    }

    public FormatEscapeBuilder setReverse(boolean enabled) {
        if (this.reverse != null)
            throw new IllegalStateException("property already set");

        this.reverse = enabled;
        return this;
    }


    public FormatEscapeBuilder setHidden() {
        return setHidden(true);
    }

    public FormatEscapeBuilder setHidden(boolean enabled) {
        if (this.hidden != null)
            throw new IllegalStateException("property already set");

        this.hidden = enabled;
        return this;
    }


    public String toString(String msg, Object...msgArgs) {
        if (msg == null)
            throw new IllegalArgumentException("message cannot be null");

        if (msg.isEmpty())
            return msg;

        if (msgArgs.length > 0)
            msg = String.format(msg, msgArgs);

        String attrCodes = String.format("%s%s%s%s%s%s%s",
            bgColor != null ? bgColor.getBgCode() + ";" : "",
            fgColor != null ? fgColor.getFgCode() + ";" : "",
            bold != null ? (bold ? ConsoleFormat.BOLD.getSetCode() : ConsoleFormat.BOLD.getResetCode()) + ";" : "",
            dim != null ? (dim ? ConsoleFormat.DIM.getSetCode() : ConsoleFormat.DIM.getResetCode()) + ";" : "",
            underlined != null ? (underlined ? ConsoleFormat.UNDERLINED.getSetCode() : ConsoleFormat.UNDERLINED.getResetCode()) + ";" : "",
            blink != null ? (blink ? ConsoleFormat.BLINK.getSetCode() : ConsoleFormat.BLINK.getResetCode()) + ";" : "",
            reverse != null ? (reverse ? ConsoleFormat.REVERSE.getSetCode() : ConsoleFormat.REVERSE.getResetCode()) + ";" : "",
            hidden != null ? (hidden ? ConsoleFormat.HIDDEN.getSetCode() : ConsoleFormat.HIDDEN.getResetCode()) + ";" : ""
        );

        if (!attrCodes.isEmpty()) {
            attrCodes = attrCodes.substring(0, attrCodes.length() - 1);
            String[] lines = msg.split(Pattern.quote("\n"));
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < lines.length; i++) {
                if (i > 0) sb.append("\n");
                sb.append(String.format("\033[%sm%s\033[0m", attrCodes, lines[i]));
            }
            return sb.toString();
        } else {
            return msg;
        }
    }
}
