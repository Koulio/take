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

import java.util.ArrayList;
import java.util.List;

import de.tu_cottbus.r2ml.DerivationRuleSet;
import de.tu_cottbus.r2ml.Term;
import nz.org.take.DerivationRule;
import nz.org.take.KnowledgeElement;

/**
 * @author Bastian Schenke (bastian.schenke@googlemail.com)
 * 
 */
class DerivationRuleSetHandler implements XmlTypeHandler {

	/**
	 * Maps a DerivationRuleSet to a List containing KnowledgeElements
	 * where each rule is transformed into one KnowledgeElement.
	 * 
	 * @param obj
	 *            a DerivationRuleSet
	 * @return List<KnowledgeElement> containing all rules of the
	 *         DerivationRuleSet obj
	 * @see nz.org.take.r2ml.XmlTypeHandler#importObject(java.lang.Object,
	 *      nz.org.take.r2ml.R2MLDriver)
	 */
	public Object importObject(Object obj)
			throws R2MLException {
		
		R2MLDriver driver = R2MLDriver.get();
		MappingContext.get().enter(this);
		
		List<KnowledgeElement> ret = new ArrayList<KnowledgeElement>();

		DerivationRuleSet dRuleSet = (DerivationRuleSet)obj;

		for (Term term : dRuleSet.getGenericVariableOrObjectVariableOrDataVariable()) {
			XmlTypeHandler handler = driver.getHandlerByXmlType(term.getClass());
			// the handler is responsible for registering the variable to the context
			handler.importObject(term);
		}
		XmlTypeHandler dRuleHandler = driver.getHandlerByXmlType(de.tu_cottbus.r2ml.DerivationRule.class);
		// mapping the ruleSetId to annotations of each rule
		String id = dRuleSet.getRuleSetID();
		for (de.tu_cottbus.r2ml.DerivationRule derivationRule : dRuleSet.getDerivationRule()) {
			try {
				for (Object ruleObj : (List) dRuleHandler.importObject(derivationRule)) {
					DerivationRule rule = (DerivationRule) ruleObj;
					if (id != null)
						rule.addAnnotation(RULE_SET_ID_KEY, id);
					ret.add(rule);
				} // for
			} catch (R2MLException e) {
				driver.logger.info(e.getMessage());
				driver.logger.warn("Error while importing DerivationRuleSet "
						+ id + "." + e.getLocalizedMessage());
				e.printStackTrace();
			} catch (ClassCastException cce) {
				MappingContext.get().cleanUpToHandler(this);
				throw new R2MLException("Error while processing DerivationRuleSet " + id + "", cce, R2MLException.CLASS_CAST_ERROR);
			} // try-catch
		}
		MappingContext.get().leave(this);
		return ret;
	}


}
