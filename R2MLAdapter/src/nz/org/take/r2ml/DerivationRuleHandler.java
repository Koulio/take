package nz.org.take.r2ml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.tu_cottbus.r2ml.Atom;
import nz.org.take.DerivationRule;
import nz.org.take.Fact;
import nz.org.take.Prerequisite;

/**
 * @author Bastian Schenke (bastian.schenke@googlemail.com)
 * 
 */
public class DerivationRuleHandler implements XmlTypeHandler {

	/**
	 * Map a de.tu_cottbus.r2ml.DerivationRule to a List of
	 * nz.ac.take.DerivationRule. If the body of the Rule contains a Disjunction
	 * it is splitted into two or more separated rules (take does not supprt
	 * disjunctions in the body).
	 * 
	 * FIXME disjunction in conditions of r2ml rules means more than one rule in
	 * take
	 * 
	 * @param obj
	 *            a r2ml.DerivationRule object
	 * @param driver
	 *            the used R2MLDriver
	 * @return nz.ac.take.DerivationRule
	 * 
	 * @see nz.org.take.r2ml.XmlTypeHandler#importObject(java.lang.Object,
	 *      nz.org.take.r2ml.R2MLDriver)
	 */
	public Object importObject(Object obj, MappingContext context, R2MLDriver driver)
			throws R2MLException {

		// R2ML DerivationRule
		de.tu_cottbus.r2ml.DerivationRule xDRule = (de.tu_cottbus.r2ml.DerivationRule) obj;
		
		// take DerivationRule
		DerivationRule dRule = new DerivationRule();

		// Documentation
		Map<String, String> documentation = extractDocumentation(xDRule, context, driver);
		dRule.addAnnotations(documentation);

		try {
			xDRule = (de.tu_cottbus.r2ml.DerivationRule) obj;

			// copy RuleID
			dRule.setId(xDRule.getRuleID());

			// Conclusion/Head
			Fact head = extractHead(xDRule, context, driver);
			dRule.setHead(head);

			// Condition/Body
			List<Prerequisite> body = extractBody(xDRule, context, driver);
			dRule.setBody(body);

		} catch (Exception e) {
			throw new R2MLException("Unable to map Derivationrule ("
					+ xDRule.getRuleID() + ")", e);
		}

		return dRule;
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
					.getDocumentation(), context, driver);
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
		Atom genAtom = rule.getConclusion().getAtom().getValue();
		XmlTypeHandler handler = driver.getHandlerByXmlType(genAtom.getClass());
		head = (Fact) handler.importObject(genAtom, context, driver);
		return head;
	}

	@SuppressWarnings("unchecked")
	private List<Prerequisite> extractBody(
			de.tu_cottbus.r2ml.DerivationRule rule, MappingContext context, R2MLDriver driver)
			throws R2MLException {
		List<Prerequisite> body = new ArrayList<Prerequisite>();
		XmlTypeHandler handler = driver.getHandlerByXmlType(rule
				.getConditions().getClass());
		try {
			body = (List<Prerequisite>) handler.importObject(rule
					.getConditions(), context, driver);
		} catch (NullPointerException e) {

		}
		return body;
	}

}
