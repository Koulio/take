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

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;

import nz.org.take.Fact;
import nz.org.take.PropertyPredicate;
import nz.org.take.Term;
import de.tu_cottbus.r2ml.AttributionAtom;

/**
 * @author Bastian Schenke (bastian.schenke@googlemail.com)
 * 
 */
class AttributionAtomHandler implements XmlTypeHandler {

	/**
	 * Map an AttributionAtom to a PropertyPredicate.
	 * 
	 * @param obj
	 *            an AttributionAtom
	 * @param driver
	 *            the used R2MLDriver
	 * @return a PropertyPredicate
	 * 
	 * @see nz.org.take.r2ml.XmlTypeHandler#importObject(java.lang.Object,
	 *      nz.org.take.r2ml.R2MLDriver)
	 */
	public Object importObject(Object obj, MappingContext context,
			R2MLDriver driver) throws R2MLException {
		AttributionAtom atom = (AttributionAtom) obj;
		context.enter(this);
		
		Fact fact = new Fact();
		String attributeName = atom.getAttributeID().getLocalPart();
		fact.setId(attributeName);
		// domain
		XmlTypeHandler subjectHandler = driver.getHandlerByXmlType(atom.getSubject()
				.getClass());
		Term domain = (Term) subjectHandler.importObject(atom.getSubject(), context,
				driver);
		// range
		XmlTypeHandler valueHandler = driver.getHandlerByXmlType(atom.getDataValue().getDataTerm().getValue().getClass());
		Term range = (Term) valueHandler.importObject(atom.getDataValue().getDataTerm().getValue(), context, driver);
		
		fact.setTerms( new Term[]{ domain, range } );
		
		// build Predicate
		PropertyPredicate prop = new PropertyPredicate();
		// set negation
		prop.setNegated((atom.isIsNegated()==null)?false:atom.isIsNegated());
		// Attribution is everytime one2one
		// TODO check typeCategory-attribute of the datavalue-dataterm
		prop.setOne2One(true);
		// look up slotnames
		prop.setSlotNames(driver.getNameMapper().getSlotNames(atom.getAttributeID()));
		prop.setOwnerType(domain.getType());
		
		// create PropertyDescriptor with datavalue as range
		PropertyDescriptor pd = null;
		try {
			pd = new PropertyDescriptor(attributeName, domain.getType());
			prop.setProperty(pd);
		} catch (IntrospectionException e) {
			throw new R2MLException(
					"Unable to instanciate PropertyDescriptor for AttributeAtom \""
							+ attributeName + "\" on \""
							+ domain.getType().getSimpleName() + "\"",
					R2MLException.GENERIC_ERROR);
		} finally {
			context.leave(this);
		}
		fact.setPredicate(prop);
		return fact;
	}

}
