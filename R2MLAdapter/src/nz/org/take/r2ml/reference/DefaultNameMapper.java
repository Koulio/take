/*
 * Copyright 2007 Bastian Schenke Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */
package nz.org.take.r2ml.reference;

import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import nz.org.take.r2ml.NameMapper;
import nz.org.take.r2ml.XmlTypeHandler;
import nz.org.take.r2ml.util.DataPredicates;


public class DefaultNameMapper implements NameMapper {
	
	private Map<QName, String[]> slotNames = new HashMap<QName, String[]>();
	private Map<QName, String> attrPredNames = new HashMap<QName, String>();

	public DefaultNameMapper() {
		addSlotNames(new QName(DataPredicates.NS_SWRLB, "equal"), new String[]{"equal1", "equal2"});
		addSlotNames(new QName(DataPredicates.NS_SWRLB, "notEqual"), new String[]{"unEqual1", "unEqual2"});
		addSlotNames(new QName(DataPredicates.NS_SWRLB, "greaterThan"), new String[]{"high", "low"});
		addSlotNames(new QName(DataPredicates.NS_SWRLB, "lessThan"), new String[]{"low", "high"});
	}

	public String[] getSlotNames(QName predicateID) {
		String[] names = slotNames.get(predicateID); 
		return names;
	}

	public void addSlotNames(QName predicateName, String[] names) {
		this.slotNames.put(predicateName, names);
	}
	
	protected void addSlotNames(String predicateName, String[] names) {
		addSlotNames(new QName("", predicateName), names);
	}

	public void addSlotNames(Map<QName, String[]> names) {
		slotNames.putAll(names);
	}
	
}
