package nz.org.take.r2ml;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;

import nz.org.take.PropertyPredicate;
import nz.org.take.Variable;
import de.tu_cottbus.r2ml.AttributionAtom;
import de.tu_cottbus.r2ml.Subject;

/**
 * @author Bastian Schenke (bastian.schenke@googlemail.com)
 * TODO implement this Handler
 */
public class AttributionAtomHandler implements XmlTypeHandler {

	/**
	 * Map a 
	 * 
	 * TODO implement protected Subject subject;
	 * TODO implement protected DataValue dataValue;
	 * TODO implement protected QName attributeID;
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
		AttributionAtom atom = (AttributionAtom)obj;
		PropertyPredicate prop = new PropertyPredicate();
		prop.setNegated(atom.isIsNegated()==null?false:atom.isIsNegated());
		prop.setOne2One(true);
		XmlTypeHandler handler = driver.getHandlerByXmlType(atom.getSubject().getClass());
		context.enter(this);
		Variable subject = (Variable)handler.importObject(atom.getSubject(), context, driver);
		prop.setOwnerType(subject.getType());
		prop.setSlotNames(driver.getSloNameGenerator().getSlotNames(atom.getAttributeID()));
		try {
			PropertyDescriptor pd = new PropertyDescriptor(atom.getAttributeID().getLocalPart(), ((Variable)subject).getType());
			prop.setProperty(pd);
		} catch (IntrospectionException e) {
			throw new R2MLException("", R2MLException.GENERIC_ERROR);
		}
		context.leave(this);

		return null;
	}

}
