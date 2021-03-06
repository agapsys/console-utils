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

import java.util.List;
import java.util.Map;

/**
 * Represents a command-line action
 */
public interface Action {
    /**
     * Returns action name.
     *
     * @return action name. If action has no name, returns null.
     */
    public String getName();

    /**
     * Return action arguments.
     *
     * @return action arguments.
     */
    public List<String> getArgs();

    /**
     * Return sub-actions associated with this instance.
     *
     * @return sub-actions associated with this instance.
     */
    public Map<String, List<String>> getSubActions();
}
