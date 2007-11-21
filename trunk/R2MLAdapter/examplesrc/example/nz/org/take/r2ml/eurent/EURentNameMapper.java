/**
 * 
 */
package example.nz.org.take.r2ml.eurent;

import javax.xml.namespace.QName;

import nz.org.take.r2ml.reference.DefaultNameMapper;

/**
 * @author schenke
 *
 */
public class EURentNameMapper extends DefaultNameMapper {

	/**
	 * 
	 */
	public EURentNameMapper() {
		super();
		addSlotNames(new QName("", "availableAt"), new String[] { "rentalCar", "branch" } );
		addSlotNames(new QName("", "storedAt")   , new String[] { "rentalCar", "branch" } );
		addSlotNames(new QName("", "assignedTo") , new String[] { "rentalCar", "rental" } );
	}

}
