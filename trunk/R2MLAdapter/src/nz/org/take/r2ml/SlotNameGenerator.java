package nz.org.take.r2ml;

import java.util.Map;

import javax.xml.namespace.QName;

/**
 * Managment class for names associated with slots in predicates.
 * 
 * 
 * @author Bastian Schenke (bastian.schenke@googlemail.com)
 *
 */
public interface SlotNameGenerator {

	public String[] getSlotNames(QName predicateID);

	public void setSlotName(QName predicateID, String[] slotNames);
	
	public void setSlotNames(Map<QName, String[]> names);

}
