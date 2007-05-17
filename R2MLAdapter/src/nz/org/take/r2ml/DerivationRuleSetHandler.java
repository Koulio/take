package nz.org.take.r2ml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import de.tu_cottbus.r2ml.DerivationRuleSet;
import de.tu_cottbus.r2ml.Term;
import nz.org.take.Clause;
import nz.org.take.KnowledgeElement;
import nz.org.take.Variable;

/**
 * @author Bastian Schenke (bastian.schenke@googlemail.com)
 * 
 */
public class DerivationRuleSetHandler implements XmlTypeHandler {

	/**
	 * Maps a DerivationRuleSet to a List containing KnowledgeElements
	 * where each rule is transformed into one KnowledgeElement.
	 * 
	 * TODO implement protected Vocabulary vocabulary;
	 * TODO implement protected List<Term> genericVariableOrObjectVariableOrDataVariable; (Map them to Facts in the KnowledgeBase!!!)
	 * TODO implement protected String externalVocabularyID;
	 * TODO implement protected String externalVocabularyLanguage;
	 * 
	 * @param obj
	 *            a DerivationRuleSet
	 * @param driver
	 *            the used R2MLDriver
	 * @return List<KnowledgeElement> containing all rules of the
	 *         DerivationRuleSet obj
	 * @see nz.org.take.r2ml.XmlTypeHandler#importObject(java.lang.Object,
	 *      nz.org.take.r2ml.R2MLDriver)
	 */
	public Object importObject(Object obj, MappingContext context, R2MLDriver driver)
			throws R2MLException {
		
		List<KnowledgeElement> ret = new ArrayList<KnowledgeElement>();

		DerivationRuleSet dRuleSet = (DerivationRuleSet)obj;

		for (Term term : dRuleSet.getGenericVariableOrObjectVariableOrDataVariable()) {
			XmlTypeHandler handler = driver.getHandlerByXmlType(term.getClass());
			// the handler is responsible for registering the variable to the context
			handler.importObject(term, context, driver);
		}
		XmlTypeHandler dRuleHandler = driver.getHandlerByXmlType(de.tu_cottbus.r2ml.DerivationRule.class);
		// mapping the ruleSetId to annotations of each rule
		String id = dRuleSet.getRuleSetID();
		context.enter(this);
		for (de.tu_cottbus.r2ml.DerivationRule derivationRule : dRuleSet.getDerivationRule()) {
			try {
				Clause rule = (Clause) dRuleHandler.importObject(derivationRule, context, driver);
				if (id != null) {
					rule.addAnnotation(RULE_SET_ID, id);
				}
				ret.add(rule);
			} catch (R2MLException e) {
				driver.logger.error("Error while importing DerivationRuleSet " + id + ".", e);
				
			}
		}
		context.leave(this);

		return ret;
	}


}
