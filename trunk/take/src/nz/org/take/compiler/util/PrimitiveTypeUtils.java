/**
 * Copyright 2008 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */

package nz.org.take.compiler.util;

/**
 * Useful utilities to deal with primitive types.
 * Not that we use the same approach as JSP EL here: all numeric types are converted to
 * either long or double.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */
public class PrimitiveTypeUtils {
	public static Class getDefaultIntegerType() {
		return Long.TYPE;
	}
	public static boolean isNumericType(Class type) {
		return (type.isPrimitive() && type!=Boolean.TYPE) || 
			Byte.class==type ||
			Double.class==type ||
			Float.class==type ||
			Integer.class==type ||
			Long.class==type ||
			Short.class==type ||
			Character.class==type;
	}
	public static Class getType(Class clazz) {
		Class t = null;
		if (clazz.isPrimitive()) {
			t = clazz;
		}
		if (clazz==Boolean.class)
			return Boolean.TYPE;		
	 	else if (clazz==Character.class)
	 		t = Character.TYPE;		
	 	else if (clazz==Byte.class)
	 		t = Byte.TYPE;		
	 	else if (clazz==Short.class)
	 		t = Short.TYPE;		
		else if (clazz==Integer.class)
			t = Integer.TYPE;		
		else if (clazz==Long.class)
			t = Long.TYPE;		
		else if (clazz==Float.class)
			t =  Float.TYPE;		
		else if (clazz==Double.class)
			t =  Double.TYPE;
		
		
		if (t==null) {
			return null; // reference type
		}
		else {
			// use only long, double and boolean
			// this will ensure that Widening Primitive Conversion is performed
			// see also http://java.sun.com/docs/books/jls/second_edition/html/conversions.doc.html
			if (t==Double.TYPE || t==Float.TYPE)
				return Double.TYPE;
			else 
				return Long.TYPE;
		}

	}
}
