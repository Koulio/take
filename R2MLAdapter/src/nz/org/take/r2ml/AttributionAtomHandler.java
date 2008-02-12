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

import nz.org.take.Fact;
import nz.org.take.SimplePredicate;
import nz.org.take.Term;
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

		fact.setTerms(new Term[] { domain, range });

		String[] slotNames = driver.getNameMapper().getSlotNames(
				atom.getAttributeID());

		//if (!MappingContext.get().isInsideCondition()) {
		//	SimplePredicate predicate = new SimplePredicate();
		//	predicate.setName(attributeName);
		//	predicate.setNegated(R2MLUtil.isNegated(atom));
		//	predicate.setSlotNames(driver.getNameMapper().getSlotNames(atom.getAttributeID()));
		//	predicate.setSlotTypes(new Class[] {domain.getType(), range.getType()});
		//	fact.setPredicate(predicate);
		//} else {
			fact.setPredicate(buildPredicate(attributeName, domain, range,
					R2MLUtil.isNegated(atom), slotNames));
		//}

		fact.setId(attributeName);

		return fact;
	}

}
