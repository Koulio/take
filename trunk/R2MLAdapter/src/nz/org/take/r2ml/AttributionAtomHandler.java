/*
 * Copyright (C) 2007 Bastian Schenke (bastian.schenke(at)gmail.com) and 
 * <a href="http://www-ist.massey.ac.nz/JBDietrich" target="_top">Jens Dietrich</a>
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
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
			Predicate jp = buildJPredciate(domain, range, atom);
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

	private Predicate buildJPredciate(Term domain, Term range,
			AttributionAtom atom) {

		String localName = atom.getAttributeID().getLocalPart();
		MappingContext context = MappingContext.get();
		Predicate p = context.getPredicate(localName);
		if (p == null) {
			StringBuffer name = new StringBuffer(localName.substring(0, 1)
					.toLowerCase());
			name.append(localName.substring(1));
			PropertyDescriptor prop = AbstractPropertyHandler.buildProperty(
					name.toString(), domain.getType());
			if (prop != null) {
				JPredicate jp = new JPredicate();
				jp.setMethod(prop.getReadMethod());
				jp.setNegated(R2MLUtil.isNegated(atom));
				// jp.setSlotNames(R2MLDriver.get().getNameMapper().getSlotNames(atom.getAttributeID()));
				context.addPredicate(jp);
				p = jp;
			} else {
				SimplePredicate sp = new SimplePredicate();
				sp.setName(name.toString());
				sp.setNegated(R2MLUtil.isNegated(atom));
				sp.setSlotTypes(new Class[] { domain.getType() });

				p = sp;
			}
		}
		return p;
	}

}
