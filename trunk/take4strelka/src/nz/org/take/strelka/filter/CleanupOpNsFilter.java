package nz.org.take.strelka.filter;

import java.util.Iterator;

import javax.xml.namespace.QName;

import org.apache.commons.jxpath.JXPathContext;

import de.tu_cottbus.r2ml.DatatypeFunctionTerm;
import de.tu_cottbus.r2ml.RuleBase;
import nz.org.take.r2ml.R2MLException;
import nz.org.take.r2ml.util.RuleBaseFilter;

public class CleanupOpNsFilter implements RuleBaseFilter {

	public String getName() {
		return "CleanupOpNsFilter";
	}

	public void repair(RuleBase ruleBase) throws R2MLException {

		JXPathContext context = JXPathContext.newContext(ruleBase);
//System.out.println("tryit");
		Iterator stuff2replace = context
			.iterate("//*[datatypeFunctionID][starts-with(datatypeFunctionID/localPart, 'op:')]");
//System.out.println("got it");
		//assert(stuff2replace.hasNext());
		while (stuff2replace.hasNext()) {
			Object obj = stuff2replace.next();
			System.out.println("element to repair " + obj.getClass().getSimpleName());
			if (obj instanceof DatatypeFunctionTerm) {
				DatatypeFunctionTerm element = (DatatypeFunctionTerm) obj;
				element.setDatatypeFunctionID(new QName(
						"http://www.xpath2operations.org/op#", element.getDatatypeFunctionID().getLocalPart().substring(3),
						"op"));
				} else {
				throw new R2MLException(
						"Unable to delete wrong op namespace usage.");
			}
		}

	}

}
