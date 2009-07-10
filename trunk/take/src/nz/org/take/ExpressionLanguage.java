package nz.org.take;

import java.util.List;
import java.util.Map;

public interface ExpressionLanguage {
	interface CompiledExpression {
		/**
		 * Get the input slots used in the expression. This will be delegated to the
		 * expression language. Example: for p.getName()==\"Mr. Foo\" , the result would be ["p"].
		 */
		public List<String> getInputSlots ();
		/**
		 * Get the type of evaluating this expression.
		 * @return a class
		 */
		public Class getType() ;
	};
	CompiledExpression compile(String definition,Map<String,Class> typeInfo) throws ExpressionException;

}
