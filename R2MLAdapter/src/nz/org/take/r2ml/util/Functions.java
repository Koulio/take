/**
 * 
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
		return arithmeticFunctionNames.get(datatypeFunctionID.getLocalPart());
	}

}
