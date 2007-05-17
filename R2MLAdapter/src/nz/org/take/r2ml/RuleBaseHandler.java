package nz.org.take.r2ml;

import java.util.Collection;
import java.util.List;

import javax.xml.bind.JAXBElement;

import nz.org.take.DefaultKnowledgeBase;
import nz.org.take.KnowledgeBase;
import nz.org.take.KnowledgeElement;

import de.tu_cottbus.r2ml.RuleBase;
import de.tu_cottbus.r2ml.RuleSet;

/**
 * @author Bastian Schenke (bastian.schenke@googlemail.com)
 * 
 */
public class RuleBaseHandler implements XmlTypeHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nz.org.take.r2ml.XmlTypeHandler#importObject(java.lang.Object,
	 *      nz.org.take.r2ml.R2MLDriver)
	 */
	@SuppressWarnings("unchecked")
	public Object importObject(Object obj, MappingContext context, R2MLDriver driver)
			throws R2MLException {
		RuleBase ruleBase = (RuleBase) obj;
		context.enter(this);
		KnowledgeBase kb = new DefaultKnowledgeBase();
		for (JAXBElement<? extends RuleSet> ruleSet : ruleBase.getRuleSet()) {
			XmlTypeHandler ruleSetHandler = null;
			ruleSetHandler = driver.getHandlerByXmlType(ruleSet.getValue()
					.getClass());
			Collection<KnowledgeElement> rules = null;
			rules = (List<KnowledgeElement>) ruleSetHandler.importObject(
					ruleSet.getValue(), context, driver);
			for (KnowledgeElement rule : rules) {
				kb.add(rule);
			}
		}
		context.leave(this);

		return kb;
	}

}
