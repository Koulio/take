/**
 * Copyright 2009 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */

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
	private Class<?> type = null;
	private Map<String, Class> typeInfo = null;
	private List<String> inputSlots;
	
	
	public MVEL2CompiledExpression(String expression,Map<String, Class> typeInfo) throws ExpressionException {
		super();
		
		// we need to compile the expression twice - first to compute the input slots
		ParserContext ctx = new ParserContext();
		try {
			ce = new ExpressionCompiler(expression).compile(ctx);
			inputSlots = new ArrayList<String>();
			inputSlots.addAll(ctx.getInputs().keySet());
		}
		catch (Exception x) {
			throw new ExpressionException("Cannot parse expression "+expression,x);
		}
		
		// second run to compute the return type - if we set the inputs first,
		// ctx.getInputs() will return all inputs we added with addInput
		// it is however important that we get only those actually used in the expression!!
		ctx = new ParserContext();
		ctx.setStrongTyping(true); 
		for (Map.Entry<String,Class> mapping:typeInfo.entrySet()) {
			ctx.addInput(mapping.getKey(),mapping.getValue());
		}
		try {
			ce = new ExpressionCompiler(expression).compile(ctx);
			type = ce.getKnownEgressType();
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
	public Class<?> getType() {
		return type;
	}

}
