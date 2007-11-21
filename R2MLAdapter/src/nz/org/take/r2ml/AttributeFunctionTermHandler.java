package nz.org.take.r2ml;

import java.beans.PropertyDescriptor;

import nz.org.take.ComplexTerm;
import nz.org.take.JFunction;
import nz.org.take.Term;
import nz.org.take.r2ml.util.R2MLUtil;
import de.tu_cottbus.r2ml.AttributeFunctionTerm;

/**
 * @author schenke
 *
 */
public class AttributeFunctionTermHandler implements XmlTypeHandler {

	/**
	 * @param obj an AttributionFunctionTerm
	 * @return a ComplexTerm representing the accessor of the Property
	 * @see nz.org.take.r2ml.XmlTypeHandler#importObject(java.lang.Object)
	 */
	public Object importObject(Object obj) throws R2MLException {
		AttributeFunctionTerm term = (AttributeFunctionTerm) obj;

		ComplexTerm takeTerm = new ComplexTerm();
		
		R2MLDriver driver = R2MLDriver.get();
		
		// building objectTerm
		Term contextArgument = null;
		XmlTypeHandler contextHandler = driver.getHandlerByXmlType(term.getContextArgument().getObjectTerm().getDeclaredType());
		contextArgument = (Term) contextHandler.importObject(term.getContextArgument().getObjectTerm().getValue());
		takeTerm.setTerms(new Term[] {contextArgument});
		
		String localName = term.getAttributeID().getLocalPart();
		// building method
		PropertyDescriptor prop = R2MLUtil.buildProperty(localName, contextArgument.getType());
		if (prop == null) {
			throw new R2MLException("The domain class \"" + contextArgument.getType().getSimpleName() + "\" does not support the property \"" + localName + "\".");
		}
		JFunction jFunction = new JFunction();
		jFunction.setMethod(prop.getReadMethod());
		takeTerm.setFunction(jFunction);
		
		return takeTerm;
	}

}
