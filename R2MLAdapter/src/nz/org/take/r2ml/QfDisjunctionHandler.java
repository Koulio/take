/*
 * Copyright 2007 Bastian Schenke Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */
package nz.org.take.r2ml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.JAXBElement;

import de.tu_cottbus.r2ml.Atom;
import de.tu_cottbus.r2ml.QfAndOrNafNegFormula;
import de.tu_cottbus.r2ml.QfDisjunction;

import nz.org.take.Fact;
import nz.org.take.Prerequisite;
import nz.org.take.r2ml.util.R2MLUtil;

/**
 * Map a quantifier free disjunction from R2ML (QfDisjunction) to a list of take
 * prerequisite lists (List<List<Prerequisite>>).
 * 
 * @param obj
 *            a QfDisjunction object
 * @param driver
 *            the used R2MLDriver
 * @return a list of take prerequisites
 * 
 * @see nz.org.take.r2ml.XmlTypeHandler#importObject(java.lang.Object,
 *      nz.org.take.r2ml.R2MLDriver)
 */
public class QfDisjunctionHandler implements XmlTypeHandler {

	public Object importObject(Object obj) throws R2MLException {

		QfDisjunction formula = (QfDisjunction) obj;
		List<List<Prerequisite>> bodies = new ArrayList<List<Prerequisite>>();

		for (JAXBElement<? extends QfAndOrNafNegFormula> disjunct : formula
				.getQfAndOrNafNegFormula()) {
			XmlTypeHandler handler = R2MLDriver.get().getHandlerByXmlType(disjunct
					.getValue().getClass());
			Prerequisite object = (Prerequisite) handler.importObject(disjunct.getValue());
			if (object instanceof List) {
				bodies.add((List) object);
			} else {
				List<Prerequisite> list = new ArrayList<Prerequisite>();
				list.add(object);
				bodies.add(list);
			}
		} // for
		return bodies;
	}

}
