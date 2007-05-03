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

import java.lang.reflect.TypeVariable;

public class PropertyPredicate extends AbstractPredicate {
	private java.beans.PropertyDescriptor property = null;
	private Class[] types = null;
	private Class ownerType = null;
	private boolean isOne2One = true; // false => One2Many
	

	public String getName() {
		return property.getName();
	}

	public Class[] getSlotTypes() {
		if (types==null && property!=null) {
			types = new Class[2];
			types[0] = ownerType;
			Class clazz = property.getPropertyType();
			if (java.util.Collection.class.isAssignableFrom(clazz)) {
				isOne2One = false;
				TypeVariable[] genTypes = clazz.getTypeParameters();
				if (genTypes.length==0)
					types[1] = Object.class;
			}
			else if (clazz.isArray()) {
				types[1] = clazz.getComponentType();
				isOne2One = false;
			}
			else {
				
			}
		} 
		return types;
	}
}
