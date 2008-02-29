/*
 * Copyright 2007 Bastian Schenke Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */
package nz.org.take.r2ml.util;

import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import nz.org.take.r2ml.R2MLException;

/**
 * @author Bastian Schenke (bastian.schenke(at)googlemail.com)
 *
 */
public class Functions {
	
	private static Map<String, String> arithmeticFunctionNames = new HashMap<String, String>();

	static {
		arithmeticFunctionNames.put("numeric-add", "+");
		arithmeticFunctionNames.put("numeric-substract", "-");
		arithmeticFunctionNames.put("numeric-subtract", "-");
		arithmeticFunctionNames.put("numeric-multiply", "*");
		arithmeticFunctionNames.put("numeric-divide", "/");
		arithmeticFunctionNames.put("numeric-integer-divide", "/");
		arithmeticFunctionNames.put("numeric-mod", "%");
	}

	public static String getArithmeticFunctionName(QName datatypeFunctionID) throws R2MLException {
//		if (!"op".equals(datatypeFunctionID.getPrefix())) {
//			throw new R2MLException("");
//		}
		if (datatypeFunctionID == null) {
			throw new R2MLException("Undefined datatype-function-id.");
		}
		String func =arithmeticFunctionNames.get(datatypeFunctionID.getLocalPart());
		if (func == null)
			throw new R2MLException("Unable to resolve function symbol " + datatypeFunctionID.getLocalPart());
		return func;
	}

}
