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

import java.util.Arrays;

/**
 * Simple explicit predicate. The semantics of this predicate is given by explicit facts and rules.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class SimplePredicate extends AbstractPredicate {

	private String name;
	private Class[] slotTypes;
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((name == null) ? 0 : name.hashCode());
		result = PRIME * result + (negated ? 1231 : 1237);
		result = PRIME * result + Arrays.hashCode(slotTypes);
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
		final SimplePredicate other = (SimplePredicate) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (negated != other.negated)
			return false;
		if (!Arrays.equals(slotTypes, other.slotTypes))
			return false;
		return true;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Class[] getSlotTypes() {
		return slotTypes;
	}
	public void setSlotTypes(Class[] slotTypes) {
		this.slotTypes = slotTypes;
	}

	
	public String toString() {
		// use UML syntax for derivated properties
		return "/"+name;
	}
	/**
	 * Get a copy of this predicate.
	 * @return
	 */
	public SimplePredicate copy() {
		SimplePredicate copy = new SimplePredicate();
		copy.slotTypes = this.getSlotTypes();
		copy.slotNames = this.getSlotNames();
		copy.name = this.getName();
		// copy.annotations = this.getAnnotations();
		copy.negated = this.isNegated();
		return copy;
	}
	
}
