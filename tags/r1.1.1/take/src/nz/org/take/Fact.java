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

package nz.org.take;

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

	public void setPredicate(Predicate predicate) {
		this.predicate = predicate;
	}

	public Term[] getTerms() {
		return terms;
	}

	public void setTerms(Term[] terms) {
		this.terms = terms;
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
