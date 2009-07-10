package nz.org.take.mvel2;

import java.util.Map;

import nz.org.take.ExpressionException;
import nz.org.take.ExpressionLanguage;
/**
 * MVEL2 expression language implementation.
 * @author jens dietrich
 */
public class MVEL2ExpressionLanguage implements ExpressionLanguage {

	@Override
	public CompiledExpression compile(String definition,Map<String, Class> typeInfo) throws ExpressionException {

		return new MVEL2CompiledExpression(definition,typeInfo);
	}

}
