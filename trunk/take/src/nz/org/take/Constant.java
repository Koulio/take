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
 * Constant terms. Basically those are just arbitrary (wrapped) objects.
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
		else return (type==String.class)  || type.isPrimitive() || java.lang.Number.class.isAssignableFrom(type) || type == Boolean.class;
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
	// return a Java literal prepresenting this object, or null if this isn't possible
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
