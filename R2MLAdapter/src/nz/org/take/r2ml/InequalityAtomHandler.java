package nz.org.take.r2ml;

import java.util.List;

import javax.xml.bind.JAXBElement;

import de.tu_cottbus.r2ml.InequalityAtom;
import de.tu_cottbus.r2ml.ObjectTerm;

/**
 * @author Bastian Schenke (bastian.schenke@googlemail.com)
 * TODO implement this Handler
 */
public class InequalityAtomHandler implements XmlTypeHandler {

	/**
	 * Map a 
	 * 
	 * TODO implement protected List<JAXBElement<? extends ObjectTerm>> objectTerm;
	 * TODO implement protected Boolean isNegated;
	 * 
	 * @param obj
	 *            a 
	 * @param driver
	 *            the used R2MLDriver
	 * @return a 
	 * 
	 * @see nz.org.take.r2ml.XmlTypeHandler#importObject(java.lang.Object,
	 *      nz.org.take.r2ml.R2MLDriver)
	 */
	public Object importObject(Object obj, MappingContext context, R2MLDriver driver)
			throws R2MLException {
		InequalityAtom atom = (InequalityAtom)obj;
		return null;
	}

}
