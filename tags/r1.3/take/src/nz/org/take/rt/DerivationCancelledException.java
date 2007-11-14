/**
 * Copyright 2007 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */

package nz.org.take.rt;

/**
 * Exceptions that is thrown to signal that the derivation has been cancelled.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class DerivationCancelledException extends DerivationException {

	public DerivationCancelledException() {
		super();
	}

	public DerivationCancelledException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public DerivationCancelledException(String arg0) {
		super(arg0);
	}

	public DerivationCancelledException(Throwable arg0) {
		super(arg0);
	}

}
