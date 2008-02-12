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

import nz.org.take.Comparison;
import nz.org.take.Fact;
import nz.org.take.TakeException;
import nz.org.take.Term;
import nz.org.take.r2ml.util.DataPredicates;
import nz.org.take.r2ml.util.R2MLUtil;
import de.tu_cottbus.r2ml.DatatypePredicateAtom;

/**
 * @author Bastian Schenke (bastian.schenke@googlemail.com)
 * TODO implement this Handler
 */
class DatatypePredicateAtomHandler implements XmlTypeHandler {

	/**
	 * Map a 
	 * 
	 * TODO implement protected DataArguments dataArguments;
	 * TODO implement protected QName datatypePredicateID;
	 * TODO implement protected Boolean isNegated;
	 * 
	 * @param obj
	 *            a 
	 * @return a 
	 * 
	 * @see nz.org.take.r2ml.XmlTypeHandler#importObject(java.lang.Object,
	 *      nz.org.take.r2ml.R2MLDriver)
	 */
	public Object importObject(Object obj)
			throws R2MLException {
		DatatypePredicateAtom atom = (DatatypePredicateAtom)obj;
		
		if (atom.getDataArguments().getDataTerm().size() != 2) {
			throw new R2MLException("DatatypePredicates are supported only for exactly two arguments.");
		}
		
		String symbol = DataPredicates.getComparisonSymbol(atom.getDatatypePredicateID());
		
		Term arg0 = null;
		Term arg1 = null;
		
		XmlTypeHandler h0 = R2MLDriver.get().getHandlerByXmlType(atom.getDataArguments().getDataTerm().get(0).getDeclaredType());
		XmlTypeHandler h1 = R2MLDriver.get().getHandlerByXmlType(atom.getDataArguments().getDataTerm().get(1).getDeclaredType());
		arg0 = (Term) h0.importObject(atom.getDataArguments().getDataTerm().get(0).getValue());
		arg1 = (Term) h1.importObject(atom.getDataArguments().getDataTerm().get(1).getValue());
		
		Fact fact = R2MLUtil.newFact();
		Comparison c = null;
		try {
			c = new Comparison(symbol);
		} catch (TakeException e) {
			throw new R2MLException("Unable to create comparison.", e);
		}		
		c.setNegated(R2MLUtil.isNegated(atom));
		c.setTypes(new Class[] {arg0.getType(), arg1.getType()});
		
		fact.setId(c.getName());
		fact.setPredicate(c);
		fact.setTerms(new Term[] {arg0, arg1});
		return fact;
	}

}
