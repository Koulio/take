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

import javax.xml.bind.JAXBElement;

import org.apache.log4j.Logger;

import nz.org.take.BinaryArithmeticFunction;
import nz.org.take.ComplexTerm;
import nz.org.take.Term;
import nz.org.take.r2ml.util.Functions;
import de.tu_cottbus.r2ml.DataTerm;
import de.tu_cottbus.r2ml.DatatypeFunctionTerm;

/**
 * @author Bastian Schenke (bastian.schenke(at)googlemail.com)
 *
 */
public class DatatypeFunctionTermHandler implements XmlTypeHandler {
	
	/**
	 * 
	 * @param obj a DatatypeFunctionTerm
	 * @return a 
	 * @see nz.org.take.r2ml.XmlTypeHandler#importObject(java.lang.Object)
	 */
	public Object importObject(Object obj) throws R2MLException {
		Logger logger = R2MLDriver.get().logger;
		DatatypeFunctionTerm r2mlTerm = (DatatypeFunctionTerm) obj;
		R2MLDriver driver = R2MLDriver.get();
		ComplexTerm term = new ComplexTerm();
		Term[] args = new Term[r2mlTerm.getDataArguments().getDataTerm().size()];
		
		int i = 0;
		for (JAXBElement<? extends DataTerm> argElement : r2mlTerm.getDataArguments().getDataTerm()) {
			DataTerm dTerm = argElement.getValue();
			XmlTypeHandler handler = driver.getHandlerByXmlType(dTerm.getClass());
			args[i++] = (Term) handler.importObject(dTerm);
		}
		term.setTerms(args);

		// set function here
		if (r2mlTerm.getDataArguments().getDataTerm().size() == 2) {
			
			String name = Functions.getArithmeticFunctionName(r2mlTerm.getDatatypeFunctionID());
			
			if (logger.isDebugEnabled())
				logger.debug("function symbol for " + r2mlTerm.getDatatypeFunctionID().getLocalPart() +" is " + name);
			

			BinaryArithmeticFunction f = BinaryArithmeticFunction.getInstance(name, args[0].getType(), args[1].getType());
			if (f == null || f.getName() == null || f.getName().isEmpty()) {
					throw new R2MLException("Unknown function-type: " + r2mlTerm.getDatatypeFunctionID() + "!");
			}
			term.setFunction(f);
			
			
		} else {
			throw new R2MLException("DatatypeFuntionTerms are supported only with 2 or more arguments ().", R2MLException.FEATURE_NOT_SUPPORTED);
		}

		driver.logger.debug("BinaryArithmeticFunction created: " + term.getFunction().toString());

		
		return term;
	}
	
	private BinaryArithmeticFunction buildBinaryArithmeticFunction(Term arg1, Term arg2) {
		return null;
	}

}
