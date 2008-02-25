package nz.org.take.r2ml;

import java.beans.PropertyDescriptor;

import nz.org.take.ComplexTerm;
import nz.org.take.JFunction;
import nz.org.take.PropertyPredicate;
import nz.org.take.Term;
import nz.org.take.r2ml.util.R2MLUtil;
import de.tu_cottbus.r2ml.ReferencePropertyFunctionTerm;

public class ReferencePropertyFunctionTermHandler implements XmlTypeHandler {

	public Object importObject(Object obj) throws R2MLException {
		R2MLDriver driver = R2MLDriver.get();
		
		if (driver.getPropertyMode() == R2MLDriver.INFER_PROPERTIES_MODE)
			throw new R2MLException("ReferencePropertyFunctionTerm not replaced although INFER_PROPERTIES_MODE is enabled.");
		ReferencePropertyFunctionTerm term = (ReferencePropertyFunctionTerm) obj;
		
		ComplexTerm takeTerm = new ComplexTerm();
		
		// building objectTerm
		XmlTypeHandler contextHandler = driver.getHandlerByXmlType(term.getContextArgument().getObjectTerm().getDeclaredType());
		Term contextArgument = (Term) contextHandler.importObject(term.getContextArgument().getObjectTerm().getValue());
		takeTerm.setTerms(new Term[] {contextArgument});
		
		// building method
		PropertyDescriptor prop = R2MLUtil.buildProperty(term.getReferencePropertyID().getLocalPart(), contextArgument.getType());
		JFunction jFunction = new JFunction();
		jFunction.setMethod(prop.getReadMethod());
		
		takeTerm.setFunction(jFunction);
		
		return takeTerm;
	}

}
