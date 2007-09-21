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
