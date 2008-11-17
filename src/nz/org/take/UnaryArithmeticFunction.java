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
 * Functions representing unary operations on numbers.
 * Right now only minus (-) is supported!!
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class UnaryArithmeticFunction implements Function {
	
	private String name = null;
	private Class returnType = null;
	private Class[] paramTypes = null;
	
	private static UnaryArithmeticFunction getInstance(String name,Class type) {
		Class returnType = PrimitiveTypeUtils.getUnaryOperationReturnType(type);
		return new UnaryArithmeticFunction(name,returnType,type);
	}
	
	public static UnaryArithmeticFunction getMINUS(Class type) {
		return getInstance("-",type);
	}
	
	public UnaryArithmeticFunction() {
		super();
	}
	public UnaryArithmeticFunction(String name, Class returnType,Class paramType) {
		super();
		this.name = name;
		this.returnType = returnType;
		this.paramTypes = new Class[]{paramType};
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