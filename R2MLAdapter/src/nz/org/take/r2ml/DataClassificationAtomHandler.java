package nz.org.take.r2ml;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import de.tu_cottbus.r2ml.DataClassificationAtom;
import de.tu_cottbus.r2ml.DataTerm;

/**
 * @author Bastian Schenke (bastian.schenke@googlemail.com)
 * TODO implement this Handler
 */
public class DataClassificationAtomHandler implements XmlTypeHandler {

	/**
	 * Map a 
	 * 
	 * TODO implement protected JAXBElement<? extends DataTerm> dataTerm;
	 * TODO implement protected QName datatypeID;
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
		DataClassificationAtom atom = (DataClassificationAtom)obj;
		return null;
	}

}
