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
 * Console text formats.
 * 
 * For details, check <a href="http://misc.flogisoft.com/bash/tip_colors_and_formatting">this link</a>
 */
public enum ConsoleFormat {
    BOLD       (1, 21),
    DIM        (2, 22),
    UNDERLINED (4, 23),
    BLINK      (5, 25),
    REVERSE    (7, 27),
    HIDDEN     (8, 28);

    private final int setCode;
    private final int resetCode;

    private ConsoleFormat(int setCode, int resetCode) {
        this.setCode = setCode;
        this.resetCode = resetCode;
    }

    public int getSetCode() {
        return setCode;
    }

    public int getResetCode() {
            return resetCode;
        }

    @Override
    public String toString() {
        return name();
    }
}
