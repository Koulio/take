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
public class DataPredicates {
	
	public static final String NS_SWRLB = "http://www.w3.org/2003/11/swrlb";
	private static Map<QName, String> dataPredicateNames = new HashMap<QName, String>();

	static {
		dataPredicateNames.put(new QName(NS_SWRLB, "equal"), "==");
		dataPredicateNames.put(new QName(NS_SWRLB, "notEqual"), "!=");
		dataPredicateNames.put(new QName(NS_SWRLB, "lessThan"), "<");
		dataPredicateNames.put(new QName(NS_SWRLB, "lessThanOrEqual"), "<=");
		dataPredicateNames.put(new QName(NS_SWRLB, "greaterThan"), ">");
		dataPredicateNames.put(new QName(NS_SWRLB, "greaterThanOrEqual"), ">=");
	}

	public static String getComparisonSymbol(QName datatypePredicateID) throws R2MLException {
		if (datatypePredicateID == null) {
			throw new R2MLException("undefined predicateID");
		}
		return dataPredicateNames.get(datatypePredicateID);
	}

}
