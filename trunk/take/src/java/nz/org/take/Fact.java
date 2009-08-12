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
 * Represents standalone facts and conditions that are part of a derivation rule.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */
public class Fact extends AbstractAnnotatable implements Clause {
	
	private Predicate predicate = null;
	private String id = null;
	private Term[] terms = null;
	
	public Predicate getPredicate() {
		return predicate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((predicate == null) ? 0 : predicate.hashCode());
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
		final Fact other = (Fact) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (predicate == null) {
			if (other.predicate != null)
				return false;
		} else if (!predicate.equals(other.predicate))
			return false;
		if (!Arrays.equals(terms, other.terms))
			return false;
		return true;
	}

	public void setPredicate(Predicate predicate) {
		this.predicate = predicate;
	}

	public Term[] getTerms() {
		return terms;
	}

	public void setTerms(Term[] terms) {
		this.terms = terms;
	}
	
	public String getName() {
		return id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String toString() {
		StringBuffer b = new StringBuffer();
		boolean f = true;
		b.append(predicate);
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

}
