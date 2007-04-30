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
 * Function backed by a Java method.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class JFunction implements Function {

	private java.lang.reflect.Method method = null;
	public String getName() {
		return method==null?null:method.getName();
	}

	public java.lang.reflect.Method getMethod() {
		return method;
	}
	public void setMethod(java.lang.reflect.Method method) {
		this.method = method;
	}

	public Class[] getParamTypes() {
		return method.getParameterTypes();
	}

	public Class getReturnType() {
		return method.getDeclaringClass();
	}

}
