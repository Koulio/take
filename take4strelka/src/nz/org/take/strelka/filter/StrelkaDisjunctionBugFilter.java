/**
 * 
 */
package nz.org.take.strelka.filter;

import java.util.List;
import java.util.ListIterator;

import javax.xml.bind.JAXBElement;

import de.tu_cottbus.r2ml.Atom;
import de.tu_cottbus.r2ml.DerivationRule;
import de.tu_cottbus.r2ml.DerivationRuleSet;
import de.tu_cottbus.r2ml.ObjectClassificationAtom;
import de.tu_cottbus.r2ml.PropertyAtom;
import de.tu_cottbus.r2ml.QfAndOrNafNegFormula;
import de.tu_cottbus.r2ml.QfDisjunction;
import de.tu_cottbus.r2ml.RuleBase;
import de.tu_cottbus.r2ml.RuleSet;

/**
 * @author Bastian Schenke (bastian.schenke(at)googlemail.com)
 * 
 */
public class StrelkaDisjunctionBugFilter implements
		nz.org.take.r2ml.util.RuleBaseFilter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nz.org.take.strelka.RuleBaseFilter#repair(de.tu_cottbus.r2ml.RuleBase)
	 */
	public void repair(RuleBase ruleBase) {
		for (JAXBElement<? extends RuleSet> item : ruleBase.getRuleSet())
			if (item.getDeclaredType().equals(DerivationRuleSet.class))
				repairDRS((DerivationRuleSet) item.getValue());
	}

	private void repairDRS(DerivationRuleSet ruleSet) {

		for (DerivationRule rule : ruleSet.getDerivationRule()) {
			fixCondition(rule.getConditions().getQfAndOrNafNegFormula());
		}

	}

	@SuppressWarnings("unchecked")
	private void fixCondition(
			List<JAXBElement<? extends QfAndOrNafNegFormula>> conditions) {
		for (ListIterator<JAXBElement<? extends QfAndOrNafNegFormula>> it = conditions
				.listIterator(); it.hasNext();) {
			JAXBElement<QfAndOrNafNegFormula> formulaElement = (JAXBElement) (it
					.next());
			if (formulaElement.getValue() instanceof QfDisjunction) {
				List<JAXBElement<? extends QfAndOrNafNegFormula>> qfAndOrNafNegFormula = ((QfDisjunction) (formulaElement.getValue()))
											.getQfAndOrNafNegFormula();
				// only one argument in qf disjunction ?
				if ((qfAndOrNafNegFormula.size() == 1)) {
					it.set(qfAndOrNafNegFormula.get(0));
				}
//				else if ((qfAndOrNafNegFormula.size() == 2)) {
//					// negated ClassificationAtom and negated Atom
//					if (!qfAndOrNafNegFormula.get(0).getDeclaredType().equals(ObjectClassificationAtom.class))
//						if ( !( (ObjectClassificationAtom)(qfAndOrNafNegFormula.get(0).getValue()) ).isIsNegated())
//							continue;
//					if (!Atom.class.isAssignableFrom(qfAndOrNafNegFormula.get(1).getDeclaredType()))
//						if ( !( (ObjectClassificationAtom)(qfAndOrNafNegFormula.get(0).getValue()) ).isIsNegated())
//							continue;
//					for (JAXBElement<? extends QfAndOrNafNegFormula> e: qfAndOrNafNegFormula) {
//						e.getValue();
//					}
//				}
			}
		}

	}

	public String getName() {
		return this.getClass().getSimpleName();
	}

}
