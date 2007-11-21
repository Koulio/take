/**
 * 
 */
package nz.org.take.r2ml;

import javax.xml.bind.JAXBElement;

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
			BinaryArithmeticFunction f = BinaryArithmeticFunction.getInstance(name, args[0].getType(), args[1].getType());
			term.setFunction(f);

		} else {
			throw new R2MLException("DatatypeFuntionTerms are supported only with 2 or more arguments ().", R2MLException.FEATURE_NOT_SUPPORTED);
		}

		
		
		return term;
	}
	
	private BinaryArithmeticFunction buildBinaryArithmeticFunction(Term arg1, Term arg2) {
		return null;
	}

}
