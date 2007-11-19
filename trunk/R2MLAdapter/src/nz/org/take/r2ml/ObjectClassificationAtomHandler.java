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
import java.util.List;

import nz.org.take.Fact;
import nz.org.take.JPredicate;
import nz.org.take.Predicate;
import nz.org.take.Prerequisite;
import nz.org.take.PropertyPredicate;
import nz.org.take.Term;
import nz.org.take.Variable;
import nz.org.take.r2ml.util.R2MLUtil;
import de.tu_cottbus.r2ml.ObjectClassificationAtom;
import de.tu_cottbus.r2ml.ObjectTerm;
import de.tu_cottbus.r2ml.ObjectVariable;

/**
 * @author Bastian Schenke (bastian.schenke@googlemail.com)
 */
class ObjectClassificationAtomHandler extends AbstractPropertyHandler {

	/**
	 * Maps an <code>ObjectClassificationAtom</code> to a take <code>JPredicate</code>.
	 * 
	 * <p>
	 * In R2ML <code>ObjectClassificationAtoms</code> are sometimes used for
	 * variabledeclaration. In that case an <code>ObjectClassificationAtom</code>
	 * contains an <code>ObjectVariable</code> wihtout a <code>classID</code>
	 * attribute as <code>ObjectTerm</code>. This <code>classID</code>
	 * attribute is then set to the value of the <code>classID</code> attribute
	 * of the <code>ObjectClassificationAtom</code>.
	 * </p>
	 * 
	 * @param obj hopefully an ObjectClassificationAtom
	 *            
	 * @return a take Fact with a JPredicate
	 * 
	 * @see nz.org.take.r2ml.XmlTypeHandler#importObject(java.lang.Object,
	 *      nz.org.take.r2ml.R2MLDriver)
	 */
	public Object importObject(Object obj)
			throws R2MLException {
		ObjectClassificationAtom atom = (ObjectClassificationAtom)obj;
		MappingContext context = MappingContext.get();
		
		ObjectTerm term = atom.getObjectTerm().getValue();
		if (term instanceof ObjectVariable && ((ObjectVariable)term).getClassID() == null) {
			((ObjectVariable)term).setClassID(atom.getClassID());
		}
		
		XmlTypeHandler handler = R2MLDriver.get().getHandlerByXmlType(term.getClass());
		// takeTerm
		Term tTerm = (Term) handler.importObject(term);
		
		String localName = atom.getClassID().getLocalPart();
		// build Predicate for classificationcheck
		Predicate p = context.getPredicate(localName); 
		if (p == null) {
			JPredicate jp = new JPredicate();
			StringBuffer name = new StringBuffer(localName.substring(0, 1).toLowerCase());
			name.append(localName.substring(1));
			PropertyDescriptor prop = AbstractPropertyHandler.buildProperty(name.toString(), R2MLDriver.get().getDatatypeMapper().getType(atom.getClassID()));
			jp.setMethod(prop.getReadMethod());
			System.out.println(name.toString() + " : " + tTerm.toString());
			//jp.setMethod(R2MLUtil.getMethod(name.toString(), new Term[]{ tTerm }));
			jp.setNegated(R2MLUtil.isNegated(atom));
			jp.setSlotNames(R2MLDriver.get().getNameMapper().getSlotNames(atom.getClassID()));
			context.addPredicate(jp);
			p = jp;
		}
		Fact fact = new Fact();
		fact.setId(p.getName());
		fact.setPredicate(p);
		fact.setTerms(new Term[]{ tTerm });
		return fact;
	}

}
