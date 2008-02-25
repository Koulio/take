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

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Collection;

import nz.org.take.Fact;
import nz.org.take.Predicate;
import nz.org.take.PropertyPredicate;
import nz.org.take.SimplePredicate;
import nz.org.take.Term;
import nz.org.take.r2ml.util.R2MLUtil;
import de.tu_cottbus.r2ml.AttributionAtom;
import de.tu_cottbus.r2ml.PropertyAtom;

/**
 * @author Bastian Schenke (bastian.schenke@googlemail.com)
 * 
 */
class PropertyAtomHandler implements XmlTypeHandler {

	/**
	 * Map a PropertyAtom to a Fact.
	 * 
	 * @param obj
	 *            a PropertyAtom
	 * @return a Fact
	 * 
	 * @see nz.org.take.r2ml.XmlTypeHandler#importObject(java.lang.Object,
	 *      nz.org.take.r2ml.R2MLDriver)
	 */
	public Object importObject(Object obj) throws R2MLException {
		PropertyAtom atom = (PropertyAtom) obj;
		R2MLDriver driver = R2MLDriver.get();
		if (driver.getPropertyMode() == R2MLDriver.INFER_PROPERTIES_MODE)
			throw new R2MLException("PropertyAtoms are not supported while using the infer-properties-mode!");
		MappingContext context = MappingContext.get();

		Fact fact = R2MLUtil.newFact();
		String propertyName = atom.getPropertyID().getLocalPart();
		// domain
		XmlTypeHandler subjectHandler = driver.getHandlerByXmlType(atom
				.getSubject().getClass());
		Term domain = (Term) subjectHandler.importObject(atom.getSubject());
		// range
		XmlTypeHandler valueHandler = driver.getHandlerByXmlType(atom
				.getValue().getTerm().getValue().getClass());
		Term range = (Term) valueHandler.importObject(atom.getValue().getTerm()
				.getValue());

		fact.setTerms(new Term[] { domain, range });

		String[] slotNames = driver.getNameMapper().getSlotNames(
				atom.getPropertyID());

		// Predicate predicate = ;
		fact.setPredicate(buildPredicate(propertyName, domain, range, atom
				.isIsNegated() == null ? false : atom.isIsNegated(), slotNames,
				context));

		fact.setId(propertyName);

		return fact;
	}

	private Predicate buildPredicate(String propertyName, Term domain,
			Term range, boolean negated, String[] slotNames,
			MappingContext context) throws R2MLException {
		// if predicate already exists
		if (context.getPredicate(propertyName) != null) {
			return context.getPredicate(propertyName);
		}
		PropertyDescriptor property = buildProperty(propertyName, domain
				.getType());
		Predicate returnPredicate = null;
		// if attribute is beanproperty use it
		if (property != null) {
			PropertyPredicate propPredicate = new PropertyPredicate();
			propPredicate.setNegated(negated);
			propPredicate.setOwnerType(domain.getType());
			propPredicate.setProperty(property);
			propPredicate.setSlotNames(slotNames);
			// this method does some initialization stuff (setOne2One)
			// is this really neccessary here?
			propPredicate.getSlotTypes();
			context.addPredicate(propPredicate);
			returnPredicate = propPredicate;
		} else {
			SimplePredicate simplePredicate = new SimplePredicate();
			simplePredicate.setName(propertyName);
			simplePredicate.setNegated(negated);
			simplePredicate.setSlotTypes(new Class[] { domain.getType(),
					range.getType() });
			simplePredicate.setSlotNames(slotNames);
			context.addPredicate(simplePredicate);
			returnPredicate = simplePredicate;
		}
		return returnPredicate;
	}

	private PropertyDescriptor buildProperty(String name, Class clazz) {
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
			PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : properties) {
				if (name.equals(property.getName())
						&& property.getReadMethod() != null) {
					return property;
				}
			}
		} catch (Exception e) {
		}
		// no property found or exception occured
		return null;
	}
}
