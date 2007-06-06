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

import nz.org.take.AbstractPredicate;
import nz.org.take.KnowledgeElement;
import de.tu_cottbus.r2ml.LogicalFormula;
import de.tu_cottbus.r2ml.StrongNegation;

class StrongNegationHandler implements XmlTypeHandler {

	public Object importObject(Object obj, MappingContext context, R2MLDriver driver)
			throws R2MLException {
		context.enter(this);
		StrongNegation neg = (StrongNegation) obj;
		LogicalFormula argument = neg.getLogicalFormula().getValue();
		XmlTypeHandler handler = driver
				.getHandlerByXmlType(argument.getClass());
		KnowledgeElement ke = null;
		Object toNeg = null;
		try {
			toNeg = handler.importObject(argument, context, driver);
			ke = (KnowledgeElement) toNeg;
		} catch (ClassCastException e) {
			throw new R2MLException(
					"Only elements that are mapped to a KnowledgeElement could be negated (actual type is '"
							+ toNeg.getClass().getSimpleName() + "').");
		}
		if (ke.getPredicate() instanceof AbstractPredicate) {
			((AbstractPredicate) ke.getPredicate()).setNegated(true);
		} else
			throw new R2MLException(
					"Negation of Predicates that do not extend AbstractPredicate are not supported.",
					R2MLException.FEATURE_NOT_SUPPORTED);
		context.leave(this);
		return ke;
	}
}