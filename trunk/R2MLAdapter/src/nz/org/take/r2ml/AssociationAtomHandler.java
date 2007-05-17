package nz.org.take.r2ml;

import javax.xml.namespace.QName;

import de.tu_cottbus.r2ml.AssociationAtom;
import de.tu_cottbus.r2ml.DataArguments;
import de.tu_cottbus.r2ml.ObjectArguments;

/**
 * @author Bastian Schenke (bastian.schenke@googlemail.com)
 * TODO implement this Handler
 */
public class AssociationAtomHandler implements
		XmlTypeHandler {

	/**
	 * Map a 
	 * 
	 * TODO implement protected DataArguments dataArguments;
	 * TODO implement protected ObjectArguments objectArguments;
	 * TODO implement protected QName associationPredicateID;
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
		AssociationAtom atom = (AssociationAtom)obj;
		return null;
	}

}
