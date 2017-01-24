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
* Console colors.
* 
* For details, check <a href="http://misc.flogisoft.com/bash/tip_colors_and_formatting">this link</a>
*/
public enum ConsoleColor {
   DEFAULT       (39,  49),
   BLACK         (30,  40),
   RED           (31,  41),
   GREEN         (32,  42),
   YELLOW        (33,  43),
   BLUE          (34,  44),
   MAGENTA       (35,  45),
   CYAN          (36,  46),
   LIGHT_GREY    (37,  47),
   DARK_GREY     (90, 100),
   LIGHT_RED     (91, 101),
   LIGHT_GREEN   (92, 102),
   LIGHT_YELLOW  (93, 103),
   LIGHT_BLUE    (94, 104),
   LIGHT_MAGENTA (95, 105),
   LIGHT_CYAN    (96, 106),
   WHITE         (97, 107);

   private final int fgCode;
   private final int bgCode;

   public int getFgCode() {
       return fgCode;
   }

   public int getBgCode() {
       return bgCode;
   }

   private ConsoleColor(int fgCode, int bgCode){
       this.fgCode = fgCode;
       this.bgCode = bgCode;
   }

   @Override
   public String toString() {
       return name();
   }
}
