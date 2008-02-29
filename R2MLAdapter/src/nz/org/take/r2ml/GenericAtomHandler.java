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
import java.util.List;

import javax.xml.bind.JAXBElement;


import nz.org.take.Fact;
import nz.org.take.SimplePredicate;
import nz.org.take.r2ml.util.R2MLUtil;

import de.tu_cottbus.r2ml.GenericAtom;

/**
 * @author Bastian Schenke (bastian.schenke@googlemail.com)
 * 
 */
class GenericAtomHandler implements XmlTypeHandler {

	/**
	 * Maps a GenericAtom to a Fact.
	 * 
	 * TODO implement protected String predicateType;
	 * 
	 * @param obj
	 *            a GenericAtom containing a Atom
	 * @return Fact representing the GenericAtom
	 * 
	 * @see nz.org.take.r2ml.XmlTypeHandler#importObject(java.lang.Object,
	 *      nz.org.take.r2ml.R2MLDriver)
	 */
	public Object importObject(Object obj) throws R2MLException {
		GenericAtom genAtom = (GenericAtom) obj;
		R2MLDriver driver = R2MLDriver.get();
		MappingContext context = MappingContext.get();
		List<JAXBElement<? extends de.tu_cottbus.r2ml.Term>> r2mlArgs = genAtom
				.getArguments().getTerm();
		Fact takeFact = R2MLUtil.newFact();
		if (context.getPredicate(genAtom.getPredicateID().toString()) == null) {
			SimplePredicate takePredicate = new SimplePredicate();
			takePredicate.setName(genAtom.getPredicateID().toString());
			List<nz.org.take.Term> takeTerms = new ArrayList<nz.org.take.Term>();
			takePredicate.setSlotNames(driver.getNameMapper()
					.getSlotNames(genAtom.getPredicateID()));
			List<Class> takeSlotTypes = new ArrayList<Class>();
			for (JAXBElement<? extends de.tu_cottbus.r2ml.Term> argElem : r2mlArgs) {
				de.tu_cottbus.r2ml.Term r2mlTerm = argElem.getValue();
				XmlTypeHandler handler = driver.getHandlerByXmlType(r2mlTerm
						.getClass());
				nz.org.take.Term takeArg = (nz.org.take.Term) handler
						.importObject(r2mlTerm);
				takeTerms.add(takeArg);
				takeSlotTypes.add(takeArg.getType());
			}
			takePredicate.setSlotTypes(takeSlotTypes.toArray(new Class[0]));
			if (genAtom.isIsNegated() != null)
				takePredicate.setNegated(genAtom.isIsNegated());
			takeFact.setPredicate(takePredicate);
			takeFact.setTerms(takeTerms.toArray(new nz.org.take.Term[0]));
		} else {
			takeFact.setPredicate(context.getPredicate(genAtom.getPredicateID().toString()));
		}
		return takeFact;
	}
}
