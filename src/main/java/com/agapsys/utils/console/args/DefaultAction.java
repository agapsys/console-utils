/*
 * Copyright 2016 Agapsys Tecnologia Ltda-ME.
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

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DefaultAction implements Action {
    // STATIC SCOPE ============================================================
    private static final String LONG_SUB_ACTION_PREFIX  = "--";
    private static final String SHORT_SUB_ACTION_PREFIX = "-";

    private static final List<String> EMPTY_LIST = Collections.unmodifiableList(new LinkedList<String>());
    // =========================================================================

    // INSTANCE SCOPE ==========================================================
    private final String actionName;
    private final List<String> actionArgs;
    private final Map<String, List<String>> subActions;

    private String[] getNames(String cmdLineArg) {
        if (cmdLineArg.startsWith(SHORT_SUB_ACTION_PREFIX) && !cmdLineArg.startsWith(LONG_SUB_ACTION_PREFIX)) {
            String name = cmdLineArg.substring(SHORT_SUB_ACTION_PREFIX.length());
            String[] names = new String[name.length()];
            int i = 0;
            for (char chr : name.toCharArray()) {
                names[i] = ("" + chr).trim();
                i++;
            }
            return names;
        } else if (cmdLineArg.startsWith(LONG_SUB_ACTION_PREFIX)) {
            return new String[] { cmdLineArg.substring(LONG_SUB_ACTION_PREFIX.length()).trim() };
        } else {
            return new String[] { cmdLineArg.trim() };
        }
    }

    public DefaultAction(String[] cmdLineArgs) throws ParsingException {

        String actionName                       = null;
        List<String> actionArgs                 = new LinkedList<>();
        Map<String, List<String>> subActions    = new LinkedHashMap<>();

        String currentActionName       = null;
        List<String> currentActionArgs = new LinkedList<>();

        String shortSequence = null;

        for (int i = 0; i < cmdLineArgs.length; i++) {
            String cmdLineArg = cmdLineArgs[i];

            if (i == 0 && !(cmdLineArg.startsWith(SHORT_SUB_ACTION_PREFIX) || cmdLineArg.startsWith(LONG_SUB_ACTION_PREFIX))) {
                actionName = cmdLineArg.trim();
                if (actionName.isEmpty()) throw new ParsingException("Empty action name");
            } else {
                if (cmdLineArg.startsWith(SHORT_SUB_ACTION_PREFIX) || cmdLineArg.startsWith(LONG_SUB_ACTION_PREFIX)) {
                    if (currentActionName != null) { // <-- closes current option
                        List<String> args = subActions.get(currentActionName);

                        if (args != null) {
                            for (String arg : currentActionArgs) {
                                if (!args.contains(arg)) {
                                    args.add(arg);
                                }
                            }
                        } else {
                            subActions.put(currentActionName, currentActionArgs);
                        }
                        currentActionArgs = new LinkedList<>();
                        shortSequence = null;
                    }

                    String[] names = getNames(cmdLineArg);
                    currentActionName = names[0];

                    if (cmdLineArg.startsWith(SHORT_SUB_ACTION_PREFIX) && !cmdLineArg.startsWith(LONG_SUB_ACTION_PREFIX)) {
                        shortSequence = names.length > 1 ? cmdLineArg : null;

                        if (shortSequence != null) {
                            for (String name : names) {
                                subActions.put(name, EMPTY_LIST);
                            }
                        }
                    }
                } else {
                    if (currentActionName == null) {
                        actionArgs.add(cmdLineArg);
                    } else {
                        if (shortSequence == null) {
                            currentActionArgs.add(cmdLineArg);
                        } else {
                            throw new ParsingException("Short option sequence \"%s\" does not allow args", shortSequence);
                        }
                    }
                }
            }
        }

        if (currentActionName != null) { // <-- closes current option
            List<String> args = subActions.get(currentActionName);

            if (args != null) {
                for (String arg : currentActionArgs) {
                    if (!args.contains(arg)) {
                        args.add(arg);
                    }
                }
            } else {
                subActions.put(currentActionName, currentActionArgs);
            }
        }

        for (Map.Entry<String, List<String>> entry : subActions.entrySet()) {
            entry.setValue(Collections.unmodifiableList(entry.getValue()));
        }


        this.actionName = actionName;
        this.actionArgs = Collections.unmodifiableList(actionArgs);
        this.subActions = Collections.unmodifiableMap(subActions);
    }

    @Override
    public String getName() {
        return actionName;
    }

    @Override
    public List<String> getArgs() {
        return actionArgs;
    }

    @Override
    public Map<String, List<String>> getSubActions() {
        return subActions;
    }
}
