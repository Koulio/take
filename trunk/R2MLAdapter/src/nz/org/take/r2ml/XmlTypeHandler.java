package nz.org.take.r2ml;

import nz.org.take.Variable;

import org.apache.log4j.Logger;

import de.tu_cottbus.r2ml.PlainLiteral;
import de.tu_cottbus.r2ml.RuleSet;

/**
 * @author Bastian Schenke (bastian.schenke@googlemail.com)
 *
 */
public interface XmlTypeHandler {
	
	/**
	 *	Annotation key for the namespace of a QName identifying a variable.
	 */
	public static final String VARIABLE_QUALIFIED_NAME_NS = Variable.class.getCanonicalName() + ".qname.ns";
	/**
	 *	Annotation key for the local part of a QName identifying a variable. 
	 */
	public static final String VARIABLE_QUALIFIED_NAME_LP = Variable.class.getCanonicalName() + ".qname.lp";
	public static final String PLAIN_LITERAL_LANGUAGE_TAG = PlainLiteral.class.getCanonicalName() + ".languageTag";
	public static final String RULE_SET_ID = RuleSet.class.getCanonicalName() + ".ruleSetId";
	
	/**
	 * @param obj
	 * @param driver
	 * @return
	 * @throws R2MLException
	 */
	Object importObject(Object obj, MappingContext context, R2MLDriver driver) throws R2MLException;
	
}
