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
 * Condition.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class Condition extends TermContainer {
	
	private String predicate = null;
	private boolean isNegated = false;
	private boolean isPrimitiveComparison = false;
	public boolean isNegated() {
		return isNegated;
	}

	public void setNegated(boolean isNegated) {
		this.isNegated = isNegated;
	}

	public String getPredicate() {
		return predicate;
	}

	public void setPredicate(String predicate) {
		this.predicate = predicate;
	}
	public String toString() {
		StringBuffer b = new StringBuffer();
		if (this.isNegated())
			b.append("not_");
		b.append(predicate);
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

	public boolean isPrimitiveComparison() {
		return isPrimitiveComparison;
	}

	public void setPrimitiveComparison(boolean isPrimitiveComparison) {
		this.isPrimitiveComparison = isPrimitiveComparison;
	}
	
}
