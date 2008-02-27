/**
 * 
 */
package nz.org.take.strelka.filter;

import java.util.Iterator;

import javax.xml.namespace.QName;

import org.apache.commons.jxpath.JXPathContext;

import de.tu_cottbus.r2ml.DatatypeFunctionTerm;
import de.tu_cottbus.r2ml.DatatypePredicateAtom;
import de.tu_cottbus.r2ml.RuleBase;
import nz.org.take.r2ml.R2MLException;
import nz.org.take.r2ml.util.DataPredicates;
import nz.org.take.r2ml.util.RuleBaseFilter;

/**
 * @author Bastian Schenke (bastian.schenke(at)googlemail.com)
 * 
 */
public class CleanupSwrlbNsFilter implements RuleBaseFilter {

	private static final String SWRLB_NS = DataPredicates.NS_SWRLB;///"http://www.w3.org/2003/11/swrlb";

	/**
	 * 
	 */
	public CleanupSwrlbNsFilter() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nz.org.take.strelka.RuleBaseFilter#getName()
	 */
	public String getName() {
		return "CleanupSwrlbNsFilter";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nz.org.take.strelka.RuleBaseFilter#repair(de.tu_cottbus.r2ml.RuleBase)
	 */
	public void repair(RuleBase ruleBase) throws R2MLException {

		JXPathContext context = JXPathContext.newContext(ruleBase);
		Iterator stuff2replace = context
				.iterate("//*[datatypePredicateID][starts-with(datatypePredicateID/localPart, 'swrlb:')]");
		while (stuff2replace.hasNext()) {
			Object obj = stuff2replace.next();
			System.out.println("element to repair "
					+ obj.getClass().getSimpleName());
			if (obj instanceof DatatypePredicateAtom) {
				DatatypePredicateAtom element = (DatatypePredicateAtom) obj;
				element.setDatatypePredicateID(new QName(SWRLB_NS, element
						.getDatatypePredicateID().getLocalPart().substring(6),
						"swrlb"));
			} else {
				throw new R2MLException(
						"Unable to delete wrong swrlb namespace usage.");
			}
		} // while

	} // repair()
}
