/**
 * 
 */
package nz.org.take.r2ml.util;

import nz.org.take.PropertyPredicate;

/**
 * 
 * 
 * @author Bastian Schenke (bastian.schenke(at)googlemail.com)
 *
 */
public class StrelkaPropertyPredicate extends PropertyPredicate {

	/**
	 * 
	 */
	public StrelkaPropertyPredicate() {
		super();
	}
	
	

	/* (non-Javadoc)
	 * @see nz.org.take.PropertyPredicate#getSlotTypes()
	 */
	@Override
	public Class[] getSlotTypes() {
		// TODO Auto-generated method stub
		return super.getSlotTypes();
	}



	@Override
	public String getName() {
		return "p_" + super.getName();
	}
	
	

}
