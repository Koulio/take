/**
 * Copyright 2008 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */

package nz.org.take.nscript;

import nz.org.take.TakeException;

/**
 * Represents parser exceptions.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class ScriptException extends TakeException {

	private int line = -1;
	
	public ScriptException() {
	}

	public ScriptException(String arg0) {
		super(arg0);
	}

	public ScriptException(Throwable arg0) {
		super(arg0);
	}

	public ScriptException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
	
	public ScriptException(int line) {
		this();
		this.line = line;
	}

	public ScriptException(String arg0,int line) {
		super(arg0);
		this.line = line;
	}

	public ScriptException(Throwable arg0,int line) {
		super(arg0);
		this.line = line;
	}

	public ScriptException(String arg0, Throwable arg1,int line) {
		super(arg0, arg1);
		this.line = line;
	}
	

	public int getLine() {
		return line;
	}

}
