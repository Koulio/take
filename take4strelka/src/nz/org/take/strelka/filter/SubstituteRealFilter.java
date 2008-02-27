/**
 * 
 */
package nz.org.take.strelka.filter;

import java.util.Iterator;

import javax.xml.namespace.QName;

import org.apache.commons.jxpath.JXPathContext;

import de.tu_cottbus.r2ml.RuleBase;
import de.tu_cottbus.r2ml.TypedLiteral;
import nz.org.take.r2ml.R2MLException;

/**
 * @author Bastian Schenke (bastian.schenke(at)googlemail.com)
 * 
 */
public class SubstituteRealFilter implements nz.org.take.r2ml.util.RuleBaseFilter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nz.org.take.strelka.RuleBaseFilter#repair(de.tu_cottbus.r2ml.RuleBase)
	 */
	public void repair(RuleBase ruleBase) throws R2MLException {
		JXPathContext context = JXPathContext.newContext(ruleBase);

		// here is an error in the return value of the QName.getLocalPart() method.
		// it should return only the localpart, but does returns also the namespace-prefix
		Iterator stuff2replace = context
			.iterate("//*[datatypeID/localPart='xs:real']");
//		Iterator stuff2replace = context
//			.iterate("//*[datatypeID/localPart='real']");

		while (stuff2replace.hasNext()) {
			Object obj = stuff2replace.next();
			if (obj instanceof TypedLiteral) {
				TypedLiteral element = (TypedLiteral) obj;
				element.setDatatypeID(new QName(
						javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI, "double",
						"xs"));
			} else {
				throw new R2MLException(
						"Unable to replace xs:real occurence. Modify SubstituteRealFilter to handle "
								+ obj.getClass().getCanonicalName());
			}
		}

	}

	public String getName() {
		return this.getClass().getSimpleName();
	}

}
