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

import nz.org.take.Fact;
import nz.org.take.Term;
import nz.org.take.r2ml.util.R2MLUtil;
import de.tu_cottbus.r2ml.ReferencePropertyAtom;

/**
 * @author Bastian Schenke (bastian.schenke@googlemail.com)
 */
class ReferencePropertyAtomHandler extends AbstractPropertyHandler {

	/**
	 * Map a ReferencePropertyAtom to a Fact. 
	 * 
	 * @param obj
	 *            a ReferencePropertyAtom
	 * @return the Fact
	 * 
	 * @see nz.org.take.r2ml.XmlTypeHandler#importObject(java.lang.Object,
	 *      nz.org.take.r2ml.R2MLDriver)
	 */
	public Object importObject(Object obj)
			throws R2MLException {
		R2MLDriver driver = R2MLDriver.get();
		
		ReferencePropertyAtom atom = (ReferencePropertyAtom)obj;
		Fact fact = R2MLUtil.newFact();
		String referenceName = atom.getReferencePropertyID().getLocalPart();
		// domain
		XmlTypeHandler subjectHandler = driver.getHandlerByXmlType(atom
				.getSubject().getClass());
		Term domain = (Term) subjectHandler.importObject(atom.getSubject());
		// range
		XmlTypeHandler valueHandler = driver.getHandlerByXmlType(atom.getObject().getObjectTerm().getValue().getClass());
		Term range = (Term) valueHandler.importObject(atom.getObject().getObjectTerm().getValue());

		fact.setTerms(new Term[] { domain, range });

		String[] slotNames = driver.getNameMapper().getSlotNames(
				atom.getReferencePropertyID());
		
		fact.setPredicate(buildPredicate(referenceName, domain, range,
				atom.isIsNegated() == null ? false : atom.isIsNegated(), slotNames));

		fact.setId(referenceName);

		return fact;
	}

}
