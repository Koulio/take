package nz.org.take;

import java.util.Map;


/**
 * A body expression is a prerequisite of a rule that can be evaluated
 * through an expression language interpreter such as MVEL or OGNL.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */
public class ExpressionPrerequisite extends Expression implements Prerequisite {

	public ExpressionPrerequisite(String expression, Map<String, Class> typeInfo) {
		super(expression, typeInfo);
	}
	
	public void accept(KnowledgeBaseVisitor visitor) {
		visitor.visit(this);
		visitor.endVisit(this);
	}
	
	public Class getType() {
		return Boolean.class;
	}
	
	public void check (Map<String,Class> typeInfo) {
		Class type = this.computeType(typeInfo);
		if (type!=Boolean.class && type!=Boolean.TYPE) {
			throw new IllegalStateException ();
		} 
	}
	
}
