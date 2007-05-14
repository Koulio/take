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
 * Constant terms. Basically those are just aribtrary (wrapped) objects.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class Constant extends Variable {
	
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
	public boolean isProxy() {
		return this.object==null && this.ref!=null;
	}

}
