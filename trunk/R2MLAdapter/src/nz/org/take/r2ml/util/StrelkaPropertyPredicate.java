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

	@Override
	public String getName() {
		return "p_" + super.getName();
	}
	
	

}
