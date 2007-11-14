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
		// TODO caching 
		Class returnType = null;
		if (type1==Integer.TYPE || type1==Integer.class||type2==Integer.TYPE || type2==Integer.class) 
			returnType=Integer.class;
		if (type1==Double.TYPE || type1==Double.class||type2==Double.TYPE || type2==Double.class) 
			returnType=Integer.class;	
		if (returnType==null)
			throw new IllegalArgumentException();
		return new BinaryArithmeticFunction(name,returnType,type1,type2);
		
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