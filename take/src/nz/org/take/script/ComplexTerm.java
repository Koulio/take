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
 * Complex term.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class ComplexTerm extends TermContainer implements Term {
	private String function = null;

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}
	
	public String toString() {
		StringBuffer b = new StringBuffer();
		b.append(function);
		b.append('(');
		boolean f = true;
		for (Term t:terms) {			
			if (f)
				f=false;
			else
				b.append(',');
			b.append(t);
		}
		b.append(')');
		return b.toString();
	}
	
}
