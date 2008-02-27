package nz.org.take.strelka.filter;

import java.util.Iterator;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;

import org.apache.commons.jxpath.JXPathContext;

import de.tu_cottbus.r2ml.DatatypePredicateAtom;
import de.tu_cottbus.r2ml.RuleBase;
import nz.org.take.r2ml.R2MLException;
import nz.org.take.r2ml.util.RuleBaseFilter;

public class CleanupXsNsFilter implements RuleBaseFilter {

	private static final String XS_NS = XMLConstants.W3C_XML_SCHEMA_NS_URI;
	
	public String getName() {
		// TODO Auto-generated method stub
		return "CleanupXsNsFilter";
	}

	public void repair(RuleBase ruleBase) throws R2MLException {
		if (true)
			throw new RuntimeException("dont use this RuleBaseFilter, it simply doesnt do its job...");
		JXPathContext context = JXPathContext.newContext(ruleBase);
		Iterator stuff2replace = context
				.iterate("//*[datatypePredicateID][starts-with(datatypePredicateID/localPart, 'xs')]");
		while (stuff2replace.hasNext()) {
			Object obj = stuff2replace.next();
			System.out.println("element to repair "
					+ obj.getClass().getSimpleName());
			if (obj instanceof DatatypePredicateAtom) {
				DatatypePredicateAtom element = (DatatypePredicateAtom) obj;
				element.setDatatypePredicateID(new QName(XS_NS, element
						.getDatatypePredicateID().getLocalPart().substring(3),
						"xs"));
			} else {
				throw new R2MLException(
						"Unable to delete wrong xs namespace usage.");
			}
		} // while
	}

}
