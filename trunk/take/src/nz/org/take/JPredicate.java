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
	
	public String toString() {
		return (negated?"!":"") + method.getName();
	}
	

}
