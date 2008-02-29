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

import nz.org.take.Fact;
import nz.org.take.Prerequisite;
import nz.org.take.r2ml.util.R2MLUtil;

import de.tu_cottbus.r2ml.Condition;
import de.tu_cottbus.r2ml.QfAndOrNafNegFormula;
import de.tu_cottbus.r2ml.QfDisjunction;

class ConditionHandler implements XmlTypeHandler {

	/**
	 * Maps a Condition to a List of Lists of Prerequisites.
	 * 
	 * The "inner" lists represent disjuncts of the original condition and the
	 * elements are suppossed to be conjuncted Prerequisites. This is neccessary
	 * hence take doesnt support Disjunctions in rule bodies. Each disjunct is
	 * represented by a single take rule with the same head.
	 * 
	 * @param obj
	 *            a Condition
	 * @return a List of Lists that contain Prerequisites
	 * 
	 * @see nz.org.take.r2ml.XmlTypeHandler#importObject(java.lang.Object,
	 *      nz.org.take.r2ml.R2MLDriver)
	 */
	@SuppressWarnings("unchecked")
	public Object importObject(Object obj) throws R2MLException {
		R2MLDriver driver = R2MLDriver.get();
		Condition condition = (Condition) obj;
		List<List<Prerequisite>> bodies = new ArrayList<List<Prerequisite>>();
		// normalize condition into DNF
		List<JAXBElement<? extends QfAndOrNafNegFormula>> formula = driver
				.getNormalizer().normalize(condition).getQfAndOrNafNegFormula();
		MappingContext.get().enter(this);
		List<Prerequisite> body = null;
		for (JAXBElement<? extends QfAndOrNafNegFormula> item : formula) {
			XmlTypeHandler handler = driver.getHandlerByXmlType(item.getValue()
					.getClass());
			// disjunctions occur only as single toplevel elements, see
			// Normalizer.normalize()
			if (item.getValue() instanceof QfDisjunction) {
				bodies = (List<List<Prerequisite>>) handler.importObject(item
						.getValue());
				break;
			} // if
			if (body == null) {
				body = new ArrayList<Prerequisite>();
				bodies.add(body);
			} // if
			if (R2MLUtil.returnsListOfPrerequisites(item.getValue())) {
				body.addAll((List<? extends Prerequisite>) handler
						.importObject(item.getValue()));
			} else if (R2MLUtil.returnsListOfFacts(item.getValue())) {
				for (Fact fact : (List<Fact>) handler.importObject(item
						.getValue())) {
					body.add(R2MLUtil.factAsPrerequisite(fact));
				} // for
			} else
				// if (R2MLUtil.returnsFact(item.getValue())) {
				body.add((Prerequisite) handler
						.importObject(item.getValue()));
			// } else {
			// driver.logger.warn("Condition dont know the return type of " +
			// item.getName());
			// throw new RuntimeException("Condition dont know the return type
			// of " + item.getName());
			// }
		} // for
		MappingContext.get().leave(this);
		return bodies;
	} // importObject()

}
