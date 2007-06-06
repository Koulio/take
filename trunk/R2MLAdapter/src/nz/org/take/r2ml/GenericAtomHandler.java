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

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBElement;


import nz.org.take.SimplePredicate;

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
	 * @param driver
	 *            the used R2MLDriver
	 * @return Fact representing the GenericAtom
	 * 
	 * @see nz.org.take.r2ml.XmlTypeHandler#importObject(java.lang.Object,
	 *      nz.org.take.r2ml.R2MLDriver)
	 */
	public Object importObject(Object obj, MappingContext context,
			R2MLDriver driver) throws R2MLException {
		GenericAtom genAtom = (GenericAtom) obj;
		List<JAXBElement<? extends de.tu_cottbus.r2ml.Term>> r2mlArgs = genAtom
				.getArguments().getTerm();
		nz.org.take.Fact takeFact = new nz.org.take.Fact();
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
						.importObject(r2mlTerm, context, driver);
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
