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
 * Functions representing binary operations on numbers.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class BinaryArithmeticFunction implements Function {
	
	private String name = null;
	private Class returnType = null;
	private Class[] paramTypes = null;
	
	public static BinaryArithmeticFunction getInstance(String name,Class type1,Class type2) {
		Class returnType = getReturnType(type1,type2);
		return new BinaryArithmeticFunction(name,returnType,type1,type2);
	}
	
	private static Class getPrimitive(Class type) {
		if (type.isPrimitive())
			return type;
		else if (type==Integer.class)
			return Integer.TYPE;
		else if (type==Character.class)
			return Character.TYPE;
		else if (type==Short.class)
			return Short.TYPE;
		else if (type==Long.class)
			return Long.TYPE;
		else if (type==Float.class)
			return Float.TYPE;
		else if (type==Double.class)
			return Double.TYPE;
		else if (type==Byte.class)
			return Byte.TYPE;
		else throw new IllegalArgumentException(""+type+" is not a numeric type");
	}
	
	private static boolean isFloatingPoint(Class type) {
		return type==Float.TYPE || type==Double.TYPE;
	}
	
	private static Class getReturnType(Class t1,Class t2) {
		t1 = getPrimitive(t1);
		t2 = getPrimitive(t2);
		if (isFloatingPoint(t1) || isFloatingPoint(t2))
			return Double.class;
		else if (t1==Long.TYPE || t2==Long.TYPE)
			return Long.class;
		else return Integer.class;
	}
	
	
	public BinaryArithmeticFunction() {
		super();
	}
	public BinaryArithmeticFunction(String name, Class returnType,Class paramType1,Class paramType2) {
		super();
		this.name = name;
		this.returnType = returnType;
		this.paramTypes = new Class[]{paramType1,paramType2};
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Class getReturnType() {
		return returnType;
	}
	public void setReturnType(Class returnType) {
		this.returnType = returnType;
	}
	public Class[] getParamTypes() {
		return paramTypes;
	}
	public void setParamTypes(Class[] paramTypes) {
		this.paramTypes = paramTypes;
	}

	@Override
	public String toString() {
		return name==null?super.toString():name;
	}
	
}