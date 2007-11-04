/**
 * Copyright 2007 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */


package nz.org.take.script;

import java.util.ArrayList;
import java.util.List;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;

/**
 * Take script engine factory implementation.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class TakeScriptEngineFactory implements ScriptEngineFactory {

	private List<String> extensions = null;
	private List<String> names = null;
	public String getEngineName() {
		return "take default implementation";
	}

	public String getEngineVersion() {
		return "0.1";
	}

	public List<String> getExtensions() {
		if (extensions==null) {
			List l = new ArrayList<String>();
			l.add("take");
			extensions = java.util.Collections.unmodifiableList(l);
		}
		return extensions;
	}

	public String getLanguageName() {
		return "take";
	}

	public String getLanguageVersion() {
		return "0.1";
	}

	public String getMethodCallSyntax(String obj, String m, String... args) {
		return null;
	}

	public List<String> getMimeTypes() {
		return null;
	}

	public List<String> getNames() {
		if (names==null) {
			List l = new ArrayList<String>();
			l.add("take");
			names = java.util.Collections.unmodifiableList(l);
		}
		return names;
	}

	public String getOutputStatement(String toDisplay) {
		return null;
	}

	public Object getParameter(String key) {
		return null;
	}

	public String getProgram(String... statements) {
		return null;
	}

	public ScriptEngine getScriptEngine() {
		return null;
	}

}
