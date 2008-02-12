/**
 * 
 */
package nz.org.take.r2ml.util;

import nz.org.take.r2ml.R2MLException;
import de.tu_cottbus.r2ml.RuleBase;

/**
 * RepairFilters are responsible to repair errors in R2ML rule bases. This
 * interface is neccessary hence strelka is a very academic project with lots
 * of bugs and errors.
 * 
 * @author schenke
 *
 */
public interface RuleBaseFilter {
	
	public String getName();

	public void repair(RuleBase ruleBase) throws R2MLException;
	
}
