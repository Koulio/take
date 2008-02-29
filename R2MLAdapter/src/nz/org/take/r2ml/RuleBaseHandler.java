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

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBElement;

import org.apache.commons.jxpath.JXPathContext;

import nz.org.take.DefaultKnowledgeBase;
import nz.org.take.DerivationRule;
import nz.org.take.KnowledgeBase;
import nz.org.take.KnowledgeElement;
import nz.org.take.r2ml.util.R2MLConstants;

import de.tu_cottbus.r2ml.ObjectClassificationAtom;
import de.tu_cottbus.r2ml.ObjectVariable;
import de.tu_cottbus.r2ml.RuleBase;
import de.tu_cottbus.r2ml.RuleSet;

/**
 * @author Bastian Schenke (bastian.schenke@googlemail.com)
 * 
 */
class RuleBaseHandler implements XmlTypeHandler {
	
	KnowledgeBase kb = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nz.org.take.r2ml.XmlTypeHandler#importObject(java.lang.Object,
	 *      nz.org.take.r2ml.R2MLDriver)
	 */
	@SuppressWarnings("unchecked")
	public Object importObject(Object obj)
			throws R2MLException {
		kb = new DefaultKnowledgeBase();
		R2MLDriver driver = R2MLDriver.get();
		MappingContext context = MappingContext.get();
		RuleBase ruleBase = (RuleBase) obj;
		context.enter(this);
		kb = new DefaultKnowledgeBase();
		kb.addAnnotation(R2MLConstants.ANNOTATION_KEY_AUTHOR, System.getProperty("user.name"));
		kb.addAnnotation(R2MLConstants.ANNOTATION_KEY_CREATOR, driver.ID);
		kb.addAnnotation(R2MLConstants.ANNOTATION_KEY_DATE, new Date().toString());

		for (JAXBElement<? extends RuleSet> ruleSet : ruleBase.getRuleSet()) {
			XmlTypeHandler ruleSetHandler = null;
			ruleSetHandler = driver.getHandlerByXmlType(ruleSet.getValue()
					.getClass());
			Collection<KnowledgeElement> rules = null;
			rules = (List<KnowledgeElement>) ruleSetHandler.importObject(
					ruleSet.getValue());
			for (KnowledgeElement rule : rules) {
				kb.add(rule);
			}
		}
//		KnowledgeBase retKB = kb;
//		kb=null;
		context.leave(this);

		return kb;
	}
	
	void addRuleToKB(DerivationRule rule) {
		kb.add(rule);
	}

}
