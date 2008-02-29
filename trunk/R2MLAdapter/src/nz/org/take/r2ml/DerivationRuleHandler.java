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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.tu_cottbus.r2ml.Atom;
import de.tu_cottbus.r2ml.Conclusion;
import nz.org.take.DerivationRule;
import nz.org.take.Fact;
import nz.org.take.Prerequisite;

/**
 * @author Bastian Schenke (bastian.schenke@googlemail.com)
 * 
 */
class DerivationRuleHandler implements XmlTypeHandler {

	/**
	 * Map a de.tu_cottbus.r2ml.DerivationRule to a List of
	 * nz.ac.take.DerivationRule. If the body of the Rule contains a Disjunction
	 * it is splitted into two or more separated rules (take does not supprt
	 * disjunctions in the body).
	 * 
	 * @param obj
	 *            a r2ml.DerivationRule object
	 * @return List<nz.ac.take.DerivationRule> one rule for each disjunct in the condition
	 * 
	 * @see nz.org.take.r2ml.XmlTypeHandler#importObject(java.lang.Object,
	 *      nz.org.take.r2ml.R2MLDriver)
	 */
	public List<DerivationRule> importObject(Object obj)
			throws R2MLException {
		R2MLDriver driver = R2MLDriver.get();
		MappingContext context = MappingContext.get();

		
		// R2ML DerivationRule
		de.tu_cottbus.r2ml.DerivationRule xDRule = (de.tu_cottbus.r2ml.DerivationRule) obj;

		context.enter(this, xDRule.getRuleID());
		
		try {
			
			if (driver.logger.isDebugEnabled())
				driver.logger.debug("mapping rule " + xDRule.getRuleID()!=null?xDRule.getRuleID():xDRule.toString());
			// take DerivationRule
			List<DerivationRule> dRules = new ArrayList<DerivationRule>();

			// Documentation
			Map<String, String> documentation = extractDocumentation(xDRule, context, driver);
			Fact conclusion = extractHead(xDRule, context, driver);
			
			try {
				int i = 0;
				List<List<Prerequisite>> body = extractBody(xDRule, context, driver);
				for (List<Prerequisite> condition : body) {
					DerivationRule dRule = new DerivationRule();
					// Condition
					dRule.setBody(condition);
					// Conclusion
					dRule.setHead(conclusion);
					// set RuleID
					
					if (xDRule.getRuleID() != null) {
						if (body.size() > 1)
							dRule.setId(xDRule.getRuleID() + '_' + i++);
						else
							dRule.setId(xDRule.getRuleID());
					} else {
						if (body.size() > 1)
							dRule.setId(dRule.getHead().getPredicate().getName() + "_" + i++);
						else
							dRule.setId(dRule.getHead().getPredicate().getName());
					}
					//System.out.println(xDRule.getRuleID());
					//System.out.println(dRule.getId());
					// add documentation
					dRule.addAnnotations(documentation);
					dRules.add(dRule);
				} // for
			} catch (R2MLException e) {
				context.cleanUpToHandler(this);
				throw new R2MLException("Error in rule \"" + xDRule.getRuleID() + "\": " + e.getMessage(), e);
			} // try catch

			context.leave(this, xDRule.getRuleID());
//			System.out.println("leaving rule " + xDRule.getRuleID());
			return dRules;
		} catch (RuntimeException e) {
			R2MLDriver.get().logger.warn("Exception occured while handling rule " + xDRule.getRuleID());
			e.printStackTrace();
			MappingContext.get().leave(this, xDRule.getRuleID());
			return new ArrayList<DerivationRule>();
		}
	}

	@SuppressWarnings("unchecked")
	private Map<String, String> extractDocumentation(
			de.tu_cottbus.r2ml.DerivationRule rule, MappingContext context, R2MLDriver driver)
			throws R2MLException {

		Map<String, String> docAnnotations = null;
		XmlTypeHandler handler = null;
		try {
			handler = driver.getHandlerByXmlType(rule.getDocumentation()
					.getClass());
			docAnnotations = (Map<String, String>) handler.importObject(rule
					.getDocumentation());
		} catch (NullPointerException e) {
			driver.logger.info("No documentation element for rule "
					+ (rule.getRuleID() != null ? rule.getRuleID() : rule
							.toString()));
			docAnnotations = new HashMap<String, String>();
		}
		return docAnnotations;
	}

	private Fact extractHead(de.tu_cottbus.r2ml.DerivationRule rule, MappingContext context,
			R2MLDriver driver) throws R2MLException {
		Fact head = null;
		
		Conclusion conclusion = rule.getConclusion();
		XmlTypeHandler handler = driver.getHandlerByXmlType(conclusion.getClass());
		head = (Fact) handler.importObject(conclusion);
		return head;
	}

	@SuppressWarnings("unchecked")
	private List<List<Prerequisite>> extractBody(
			de.tu_cottbus.r2ml.DerivationRule rule, MappingContext context, R2MLDriver driver)
			throws R2MLException {
		List<List<Prerequisite>> ruleBodies = null;
		XmlTypeHandler handler = driver.getHandlerByXmlType(rule.getConditions().getClass());
		ruleBodies = (List<List<Prerequisite>>) handler.importObject(rule.getConditions());
		return ruleBodies;
	}

}
