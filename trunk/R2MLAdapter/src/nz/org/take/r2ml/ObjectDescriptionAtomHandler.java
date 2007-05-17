package nz.org.take.r2ml;

import java.util.List;

import javax.xml.namespace.QName;

import de.tu_cottbus.r2ml.ObjectDescriptionAtom;
import de.tu_cottbus.r2ml.Slot;
import de.tu_cottbus.r2ml.Subject;

/**
 * @author Bastian Schenke (bastian.schenke@googlemail.com)
 * TODO implement this Handler
 */
public class ObjectDescriptionAtomHandler implements XmlTypeHandler {

	/**
	 * Map a 
	 * 
	 * TODO implement protected Subject subject;
	 * TODO implement protected List<Slot> objectSlotOrDataSlot;
	 * TODO implement protected QName baseType;
	 * TODO implement protected QName classID;
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
		ObjectDescriptionAtom atom = (ObjectDescriptionAtom)obj;
		return null;
	}

}
