/*
 * Copyright (C) 2007 <A href="http://www-ist.massey.ac.nz/JBDietrich" target="_top">Jens Dietrich</a>
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
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
