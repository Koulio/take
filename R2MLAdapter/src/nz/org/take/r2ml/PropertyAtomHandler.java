package nz.org.take.r2ml;

import javax.xml.namespace.QName;

import de.tu_cottbus.r2ml.PropertyAtom;
import de.tu_cottbus.r2ml.Subject;
import de.tu_cottbus.r2ml.Value;

/**
 * @author Bastian Schenke (bastian.schenke@googlemail.com)
 * TODO implement this Handler
 */
public class PropertyAtomHandler implements XmlTypeHandler {

	/**
	 * Map a 
	 * 
	 * TODO implement protected Subject subject;
	 * TODO implement protected Value value;
	 * TODO implement protected QName propertyID;
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
		PropertyAtom atom = (PropertyAtom)obj;
		return null;
	}

}
