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

import nz.org.take.compiler.CompilerException;

/**
 * Constant terms. Basically those are just aribtrary (wrapped) objects.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class Constant extends AbstractAnnotatable implements Term {
	
	private Object object = null;
	// the type can be overridden, by default the type of the object is used
	private Class type = null;
	// this is a string that is used to proxy the object
	private String ref = null;
	
	public Object getObject() {
		return object;
	}
	public void setObject(Object o) {
		checkTypeConsistency(o,type);
		this.object = o;
	}
	public Class getType() {
		if  (type==null) {
			if (object==null)
				return null;
			else
				return object.getClass();
		}
		return type;
	}
	public void setType(Class t) {
		checkTypeConsistency(object,t);
		this.type = t;
	}
	private void checkTypeConsistency(Object o,Class t) {
		if (o!=null && t!=null && !t.isAssignableFrom(o.getClass()))
			throw new IllegalArgumentException("Object "+ o + " and type " + t + " are inconsistent, the type must be a supertype of the object type");
	}
	public String getRef() {
		return ref;
	}
	public void setRef(String ref) {
		this.ref = ref;
	}
	/**
	 * Whether this is just a placeholder to access the object or a real object. 
	 * @return
	 */
	public boolean isProxy() {
		return this.object==null && this.ref!=null;
	}
	/**
	 * Whether this is a literal, in particular a string.
	 * @return
	 */
	public boolean isLiteral() {
		Class type = getType();
		if (this.object==null) return false; // proxy
		else return (type==String.class)  || type.isPrimitive() || java.lang.Number.class.isAssignableFrom(type);
	}
	public String toString() {
		StringBuffer b = new StringBuffer();
		if (isProxy())
			b.append('[');
		b.append(isProxy()?ref:object);
		if (isProxy())
			b.append(']');
		return b.toString();
	}
	public void accept(KnowledgeBaseVisitor visitor) {
		visitor.visit(this);
		visitor.endVisit(this);
	}
	// return a Java literal prepresenting thsi object, or null if this isn't possible
	public String getLiteral() {
		if (object!=null) {
			if (object instanceof String)
				return "\"" + object + "\"";
			else if (object instanceof Number || object instanceof Boolean || object.getClass().isPrimitive())
				return String.valueOf(object);
		}
		return null;
	}
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((object == null) ? 0 : object.hashCode());
		result = PRIME * result + ((ref == null) ? 0 : ref.hashCode());
		result = PRIME * result + ((type == null) ? 0 : type.hashCode());
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
		final Constant other = (Constant) obj;
		if (object == null) {
			if (other.object != null)
				return false;
		} else if (!object.equals(other.object))
			return false;
		if (ref == null) {
			if (other.ref != null)
				return false;
		} else if (!ref.equals(other.ref))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
}
