package nz.org.take;


/**
 * A body expression is a prerequisite of a rule that can be evaluated
 * through an expression language interpreter such as MVEL or OGNL.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class BodyExpression implements Prerequisite {
	private ExpressionLanguage language = null;
	private String expression = null;
	
	public BodyExpression() {
		super();
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
	public void accept(KnowledgeBaseVisitor visitor) {
		visitor.visit(this);
		visitor.endVisit(this);
	}
}
