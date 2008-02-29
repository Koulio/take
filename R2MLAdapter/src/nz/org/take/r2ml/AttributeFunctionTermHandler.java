/*
 * Copyright 2007 Bastian Schenke Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */
package nz.org.take.r2ml;

import java.beans.PropertyDescriptor;

import nz.org.take.ComplexTerm;
import nz.org.take.JFunction;
import nz.org.take.Term;
import nz.org.take.Variable;
import nz.org.take.r2ml.util.R2MLUtil;
import de.tu_cottbus.r2ml.AttributeFunctionTerm;

/**
 * @author schenke
 * 
 */
public class AttributeFunctionTermHandler implements XmlTypeHandler {

	/**
	 * @param obj
	 *            an AttributionFunctionTerm
	 * @return a ComplexTerm representing the accessor of the Property
	 * @see nz.org.take.r2ml.XmlTypeHandler#importObject(java.lang.Object)
	 */
	public Object importObject(Object obj) throws R2MLException {
		// AttributionFunctionTermHandler should be replaced before compiling the knowledge base
		R2MLDriver driver = R2MLDriver.get();

		if (driver.getPropertyMode() == R2MLDriver.INFER_PROPERTIES_MODE)
			throw new R2MLException("AttributionFunctionTerm not replaced although INFER_PROPERTIES_MODE is enabled.");
		AttributeFunctionTerm term = (AttributeFunctionTerm) obj;

		Term takeTerm = null;

				// building objectTerm
		Term contextArgument = null;
		XmlTypeHandler contextHandler = driver.getHandlerByXmlType(term
				.getContextArgument().getObjectTerm().getDeclaredType());
		contextArgument = (Term) contextHandler.importObject(term
				.getContextArgument().getObjectTerm().getValue());

		String localName = term.getAttributeID().getLocalPart();
		// building method
		PropertyDescriptor prop = R2MLUtil.buildProperty(localName,
				contextArgument.getType());
		if (prop == null) {
			// takeTerm = buildRuleForDerivedAttributes(localName,
			// contextArgument);
			throw new R2MLException("The domain class \""
					+ contextArgument.getType().getSimpleName()
					+ "\" does not support the property \"" + localName + "\".");
		} else {
			ComplexTerm tt = new ComplexTerm();
			JFunction jFunction = new JFunction();
			jFunction.setMethod(prop.getReadMethod());
			tt.setFunction(jFunction);
			tt.setTerms(new Term[] { contextArgument });
			
			takeTerm = tt;
		}

		return takeTerm;
	}

	// private Variable buildRuleForDerivedAttributes(String localName, Term
	// contextArgument) throws R2MLException {
	// Class type = R2MLDriver.get().lookUpType(localName, contextArgument);
	// Variable var = new Variable();
	// var.setName(localName);
	// var.setType(type);
	// buildRuleToResolvVariable(contextArgument);
	// return var;
	//
	// }

	private void buildRuleToResolvVariable(Term contextArgument)
			throws R2MLException {
		// TODO Auto-generated method stub
		throw new R2MLException("alles scheisse");
	}

}
