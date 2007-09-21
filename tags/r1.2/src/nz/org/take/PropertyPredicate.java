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
 * Predicates based on Java properties as defined in the JavaBeans spec.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

public class PropertyPredicate extends AbstractPredicate {
	private java.beans.PropertyDescriptor property = null;
	private Class[] types = null;
	private Class ownerType = null;
	private boolean isOne2One = true; // false => One2Many
	
	public String getName() {
		return property.getName();
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + (isOne2One ? 1231 : 1237);
		result = PRIME * result + (negated ? 1231 : 1237);
		result = PRIME * result + ((ownerType == null) ? 0 : ownerType.hashCode());
		result = PRIME * result + ((property == null) ? 0 : property.hashCode());
		result = PRIME * result + Arrays.hashCode(types);
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
		final PropertyPredicate other = (PropertyPredicate) obj;
		if (isOne2One != other.isOne2One)
			return false;
		if (negated != other.negated)
			return false;
		if (ownerType == null) {
			if (other.ownerType != null)
				return false;
		} else if (!ownerType.equals(other.ownerType))
			return false;
		if (property == null) {
			if (other.property != null)
				return false;
		} else if (!property.equals(other.property))
			return false;
		if (!Arrays.equals(types, other.types))
			return false;
		return true;
	}

	public Class[] getSlotTypes() {
		if (types==null && property!=null) {
			Class clazz = property.getPropertyType();
			// boolean properties are special !
			if (clazz==Boolean.class || clazz==Boolean.TYPE) {
				types = new Class[1];
				types[0] = ownerType;
				return types;
			}
			types = new Class[2];
			types[0] = ownerType;			
			
			if (java.util.Collection.class.isAssignableFrom(clazz)) {
				isOne2One = false;
				// check for generics
				Method m = property.getReadMethod();
				Type gtype = m.getGenericReturnType();
				if (gtype instanceof ParameterizedType) {
					ParameterizedType ptype = (ParameterizedType)gtype;
					Type otype = ptype.getOwnerType();
					Type rtype = ptype.getRawType();
					Type[] atypes = ptype.getActualTypeArguments();
					// TODO check whether this is really necessary
					if (atypes.length==0)
						types[1] = Object.class;
					types[1] = (Class)atypes[0];
				}
				else 
					types[1] = Object.class;

			}
			else if (clazz.isArray()) {
				types[1] = clazz.getComponentType();
				isOne2One = false;
			}
			else {
				types[1] = clazz;
			}
		} 
		return types;
	}

	public boolean isOne2One() {
		return isOne2One;
	}

	public void setOne2One(boolean isOne2One) {
		this.isOne2One = isOne2One;
	}

	public java.beans.PropertyDescriptor getProperty() {
		return property;
	}

	public void setProperty(java.beans.PropertyDescriptor property) {
		this.property = property;
	}

	public Class getOwnerType() {
		return ownerType;
	}

	public void setOwnerType(Class ownerType) {
		this.ownerType = ownerType;
	}
	public String toString() {
		return property.getName();
	}
}
