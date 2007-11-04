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

/**
 * Exceptions that occur when a not supported or not yet implemented feature is encountered.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class FeatureNotSupportedException extends ScriptException {

	public FeatureNotSupportedException() {
	}

	public FeatureNotSupportedException(String arg0) {
		super(arg0);
	}

	public FeatureNotSupportedException(Throwable arg0) {
		super(arg0);
	}

	public FeatureNotSupportedException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
