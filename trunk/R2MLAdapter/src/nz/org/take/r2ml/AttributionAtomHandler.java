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

import java.beans.PropertyDescriptor;

import nz.org.take.Fact;
import nz.org.take.JPredicate;
import nz.org.take.Predicate;
import nz.org.take.SimplePredicate;
import nz.org.take.Term;
import nz.org.take.Variable;
import nz.org.take.r2ml.util.R2MLUtil;
import de.tu_cottbus.r2ml.AttributionAtom;

/**
 * @author Bastian Schenke (bastian.schenke@googlemail.com)
 * 
 */
class AttributionAtomHandler extends AbstractPropertyHandler {

	/**
	 * Map an AttributionAtom to a PropertyPredicate.
	 * 
	 * @param obj
	 *            an AttributionAtom
	 * @return a PropertyPredicate
	 * 
	 * @see nz.org.take.r2ml.XmlTypeHandler#importObject(java.lang.Object,
	 *      nz.org.take.r2ml.R2MLDriver)
	 */
	public Object importObject(Object obj) throws R2MLException {
		AttributionAtom atom = (AttributionAtom) obj;

		Fact fact = R2MLUtil.newFact();
		String attributeName = atom.getAttributeID().getLocalPart();
		R2MLDriver driver = R2MLDriver.get();
		// domain
		XmlTypeHandler subjectHandler = driver.getHandlerByXmlType(atom
				.getSubject().getClass());
		Term domain = (Term) subjectHandler.importObject(atom.getSubject());
		// range
		XmlTypeHandler valueHandler = driver.getHandlerByXmlType(atom
				.getDataValue().getDataTerm().getValue().getClass());
		Term range = (Term) valueHandler.importObject(atom.getDataValue()
				.getDataTerm().getValue());

		String[] slotNames = driver.getNameMapper().getSlotNames(
				atom.getAttributeID());

		// boolean properties are special see also PropertyPredicates.getTypes()
		if (range.getType() == Boolean.class || range.getType() == Boolean.TYPE) {
			Predicate jp = buildJPredciate(domain, atom.getAttributeID().getLocalPart(), R2MLUtil.isNegated(atom), slotNames);
			fact.setPredicate(jp);
			fact.setTerms(new Term[] { domain });
			fact.setId(attributeName);
			return fact;
		} else {
			fact.setTerms(new Term[] { domain, range });

			// if (!MappingContext.get().isInsideCondition()) {
			// SimplePredicate predicate = new SimplePredicate();
			// predicate.setName(attributeName);
			// predicate.setNegated(R2MLUtil.isNegated(atom));
			// predicate.setSlotNames(driver.getNameMapper().getSlotNames(atom.getAttributeID()));
			// predicate.setSlotTypes(new Class[] {domain.getType(),
			// range.getType()});
			// fact.setPredicate(predicate);
			// } else {
			fact.setPredicate(buildPredicate(attributeName, domain, range,
					R2MLUtil.isNegated(atom), slotNames));
			// }

			fact.setId(attributeName);

			return fact;
		}

	}

	static Predicate buildJPredciate(Term domain, String localName, boolean isNegated, String[] slotNames) {

		//String localName = atom.getAttributeID().getLocalPart();
		MappingContext context = MappingContext.get();
		Predicate p = context.getPredicate(localName);
		if (p == null) {
			StringBuffer name = new StringBuffer(localName.substring(0, 1)
					.toLowerCase());
			name.append(localName.substring(1));
			PropertyDescriptor prop = AbstractPropertyHandler.buildProperty(
					name.toString(), domain.getType());
			// TODO build a mappingrule from JPredicte to simplePredicate and use only the simplePredicate in rules
			if (prop != null) {
				JPredicate jp = new JPredicate();
				jp.setMethod(prop.getReadMethod());
				jp.setNegated(isNegated);
				jp.setSlotNames(slotNames);
				context.addPredicate(jp);
				p = jp;
			} else {
				SimplePredicate sp = new SimplePredicate();
				sp.setName(name.toString());
				sp.setNegated(isNegated);
				sp.setSlotNames(slotNames);
				sp.setSlotTypes(new Class[] { domain.getType() });

				p = sp;
			}
		}
		return p;
	}

}
