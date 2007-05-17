package nz.org.take.r2ml;

import nz.org.take.Constant;
import de.tu_cottbus.r2ml.PlainLiteral;

/**
 * @author Bastian Schenke (bastian.schenke@googlemail.com)
 *
 */
public class PlainLiteralHandler implements XmlTypeHandler {

	/**
	 * Maps a PlainLiteral to a Constant.
	 * 
	 * TODO implement protected String languageTag;
	 * TODO implement protected String typeCategory;
	 * 
	 * @param obj
	 *            a PlainLiteral
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
		PlainLiteral pl = (PlainLiteral)obj;
		constant.setObject(pl.getLexicalValue());
		constant.setType(String.class);
		constant.addAnnotation(PLAIN_LITERAL_LANGUAGE_TAG, pl.getLanguageTag());
		return constant;
	}

}
