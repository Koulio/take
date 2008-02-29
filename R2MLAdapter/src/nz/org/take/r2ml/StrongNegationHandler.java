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

import nz.org.take.AbstractPredicate;
import nz.org.take.KnowledgeElement;
import de.tu_cottbus.r2ml.LogicalFormula;
import de.tu_cottbus.r2ml.StrongNegation;

class StrongNegationHandler implements XmlTypeHandler {

	public Object importObject(Object obj)
			throws R2MLException {
		R2MLDriver driver = R2MLDriver.get();
		MappingContext context = MappingContext.get();
		StrongNegation neg = (StrongNegation) obj;
		LogicalFormula argument = neg.getLogicalFormula().getValue();
		XmlTypeHandler handler = driver
				.getHandlerByXmlType(argument.getClass());
		KnowledgeElement ke = null;
		Object toNeg = null;
		try {
			toNeg = handler.importObject(argument);
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
		return ke;
	}
}