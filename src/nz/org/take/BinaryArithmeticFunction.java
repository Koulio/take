/*
 * Copyright (C) 2006 <A href="http://www-ist.massey.ac.nz/JBDietrich" target="_top">Jens Dietrich</a>
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