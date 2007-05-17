package nz.org.take.r2ml;

import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;


public class DefaultSlotNameGenerator implements SlotNameGenerator {

	Map<QName, String[]> slotNames = new HashMap<QName, String[]>();

	public DefaultSlotNameGenerator() {
	}

	public String[] getSlotNames(QName predicateID) {
		return slotNames.get(predicateID);
	}

	public void setSlotName(QName key, String[] value) {
		slotNames.put(key, value);
	}

	public void setSlotNames(Map<QName, String[]> names) {
		slotNames = names;
	}

}
