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
