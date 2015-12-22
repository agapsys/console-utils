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

import com.agapsys.utils.console.args.OptionDefinition;

/**
 * @author Leandro Oliveira (leandro@agapsys.com)
 */
public class CopyOption implements OptionDefinition {

	@Override
	public Character getShortName() {
		return 'c';
	}

	@Override
	public String getLongName() {
		return "copy";
	}

	@Override
	public boolean isUnique() {
		return false;
	}

	@Override
	public String getShortDescription() {
		return null;
	}

	@Override
	public String getLongDescription() {
		return null;
	}

}
