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
