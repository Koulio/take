package nz.org.take.mvel2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.mvel2.ParserContext;
import org.mvel2.compiler.ExpressionCompiler;

import nz.org.take.ExpressionException;
import nz.org.take.ExpressionLanguage.CompiledExpression;

/**
 * MVEL2 compiled expression wrapper.
 * @author jens dietrich
 */

class MVEL2CompiledExpression implements CompiledExpression {

	private org.mvel2.compiler.CompiledExpression ce = null;
	private Class type = null;
	private Map<String, Class> typeInfo = null;
	private List<String> inputSlots;
	
	
	public MVEL2CompiledExpression(String expression,Map<String, Class> typeInfo) throws ExpressionException {
		super();
		
		ParserContext ctx = new ParserContext();
		ctx.setStrongTyping(true); 
		for (Map.Entry<String,Class> mapping:typeInfo.entrySet()) {
			ctx.addInput(mapping.getKey(),mapping.getValue());
		}
		try {
			ce = new ExpressionCompiler(expression).compile(ctx);
			type = ce.getKnownEgressType();
			inputSlots = new ArrayList<String>();
			// FIXME - we need to filter those in order to register only slots 
			// actually used in the expression - it seems to be tricky to do this in MVEL
			// as the MVEL AST API does not make this easy
			// plan b: check whether names are in expression (substring) - but this is dodgy
			// would work better if we could have a special variable syntax such as $x or ?x
			inputSlots.addAll(ctx.getInputs().keySet());
		}
		catch (Exception x) {
			throw new ExpressionException("Cannot parse expression "+expression,x);
		}
		
	}

	@Override
	public List<String> getInputSlots() {
		return inputSlots;
	}

	@Override
	public Class getType() {
		return type;
	}

}
