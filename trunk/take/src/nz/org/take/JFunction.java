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

import java.lang.reflect.Modifier;

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
		return method.getReturnType();
	}
	
	public String toString() {
		return method.getName();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((method == null) ? 0 : method.hashCode());
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
		final JFunction other = (JFunction) obj;
		if (method == null) {
			if (other.method != null)
				return false;
		} else if (!method.equals(other.method))
			return false;
		return true;
	}
	
	public boolean isStatic() {
		return method!=null && Modifier.isStatic(method.getModifiers());
	}

}
