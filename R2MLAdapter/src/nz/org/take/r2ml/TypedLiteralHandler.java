package nz.org.take.r2ml;

import de.tu_cottbus.r2ml.TypedLiteral;
import nz.org.take.Constant;

/**
 * TODO implement protected String typeCategory;
 * 
 * @author Bastian Schenke (bastian.schenke@googlemail.com)
 *
 */
public class TypedLiteralHandler implements XmlTypeHandler {

	/**
	 * Maps a TypedLiteral to a Constant.
	 * 
	 * @param obj
	 *            a TypedLiteral
	 * @param driver
	 *            the used R2MLDriver
	 * @return Constant representing the literal
	 * 
	 * @see nz.org.take.r2ml.XmlTypeHandler#importObject(java.lang.Object,
	 *      nz.org.take.r2ml.R2MLDriver)
	 */
	public Object importObject(Object obj, MappingContext context, R2MLDriver driver)
			throws R2MLException {
		Constant constant = new Constant();
		TypedLiteral tl = (TypedLiteral)obj;
		constant.setType(driver.getDatatypeMapper().getType(tl.getDatatypeID()));
		constant.setObject(tl.getLexicalValue());
		return constant;
	}

}
