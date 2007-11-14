/**
 * Copyright 2007 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */

package nz.org.take;

import java.util.Arrays;

/**
 * Complex terms are composed of functions and other terms.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class ComplexTerm extends AbstractAnnotatable implements Term {
	
	private Function function = null;
	private Term[] terms = null;
	public Function getFunction() {
		return function;
	}
	public void setFunction(Function function) {
		this.function = function;
	}
	public Term[] getTerms() {
		return terms;
	}
	public void setTerms(Term[] terms) {
		this.terms = terms;
	}
	public Class getType() {
		return function==null?null:function.getReturnType();
	}
	public String toString() {
		StringBuffer b = new StringBuffer();
		boolean f = true;
		b.append(function);
		b.append('(');
		for (Term t:terms) {
			if (f)
				f = false;
			else 
				b.append(',');
			b.append(t);
		}
		b.append(')');
		return b.toString();
	}
	public void accept(KnowledgeBaseVisitor visitor) {
		if (visitor.visit(this)) {
			for (Term t:terms)
				t.accept(visitor);
		}		
		visitor.endVisit(this);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((function == null) ? 0 : function.hashCode());
		result = prime * result + Arrays.hashCode(terms);
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ComplexTerm other = (ComplexTerm) obj;
		if (function == null) {
			if (other.function != null)
				return false;
		} else if (!function.equals(other.function))
			return false;
		if (!Arrays.equals(terms, other.terms))
			return false;
		return true;
	}

}
