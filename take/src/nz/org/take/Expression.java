package nz.org.take;

import java.util.List;
import java.util.Map;


/**
 * A body expression is a prerequisite of a rule that can be evaluated
 * through an expression language interpreter such as MVEL or OGNL.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public abstract class Expression  {
	protected ExpressionLanguage language = null;
	protected String expression = null;
	protected Class type = null;
	
	public Expression(String expression,Map<String,Class> typeInfo) {
		super();
		this.expression = expression;
		this.type = this.computeType(typeInfo) ;
	}
	
	public ExpressionLanguage getLanguage() {
		return language;
	}
	public void setLanguage(ExpressionLanguage language) {
		this.language = language;
	}
	public String getExpression() {
		return expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}

	// TODO
	/**
	 * Get the input slots used in the expression. This will be delegated to the
	 * expression language. Example: for p.getName()==\"Mr. Foo\" , the result would be ["p"].
	 */
	public List<String> getInputSlots () {
		return null;
	}
	/**
	 * Computes the type from a type map generated from var and ref declarations, and 
	 * info about literals. 
	 * @param typeInfo - each slot (returned by getInputSlots) should be a key in this map,
	 * otherwise this will throw an IllegalArgumentException .
	 * @return
	 */
	public Class computeType(Map<String,Class> typeInfo) {
		return Object.class;
	}
	public Class getType() {
		return type;
	}
}
