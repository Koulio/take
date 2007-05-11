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
 * Predicate backed by a Java method returning a boolean.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class JPredicate extends AbstractPredicate {

	private java.lang.reflect.Method method = null;
	
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((method == null) ? 0 : method.hashCode());
		result = PRIME * result + (negated ? 1231 : 1237);
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
		final JPredicate other = (JPredicate) obj;
		if (method == null) {
			if (other.method != null)
				return false;
		} else if (!method.equals(other.method))
			return false;
		if (negated != other.negated)
			return false;
		return true;
	}

	public String getName() {
		return method==null?null:method.getName();
	}

	public Class[] getSlotTypes() {
		Class[] types = new Class[method.getParameterTypes().length+1];
		types[0]=method.getDeclaringClass();
		for (int i=1;i<types.length;i++)
			types[i]=method.getParameterTypes()[i-1];
		return types;
	}
	public java.lang.reflect.Method getMethod() {
		return method;
	}
	public void setMethod(java.lang.reflect.Method method) {
		this.method = method;
	}

}
