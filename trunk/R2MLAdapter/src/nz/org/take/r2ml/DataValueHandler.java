package nz.org.take.r2ml;

/**
 * @author Bastian Schenke (bastian.schenke@googlemail.com)
 *
 */
public class DataValueHandler implements XmlTypeHandler {

	/**
	 * Map a DataValue to a Fact.
	 * 
	 * The "inner" lists represent disjuncts of the original condition and there
	 * elements are suppossed to be conjuncted Prerequisites. This is neccessary
	 * hence take doesnt support Disjunctions in rule bodies. Each disjunct is
	 * represented by a single take rule with the same head.
	 * 
	 * @param obj
	 *            a Condition
	 * @param driver
	 *            the used R2MLDriver
	 * @return a List of Lists that contain Prerequisites
	 * 
	 * @see nz.org.take.r2ml.XmlTypeHandler#importObject(java.lang.Object,
	 *      nz.org.take.r2ml.R2MLDriver)
	 */
	public Object importObject(Object obj, MappingContext context,
			R2MLDriver driver) throws R2MLException {

		//DataValue
		return null;
	}

}
