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

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import nz.org.take.Expression;
import nz.org.take.ExpressionException;
import nz.org.take.ExpressionLanguage;
import nz.org.take.compiler.util.TmpVarGenerator;
/**
 * MVEL2 expression language implementation.
 * @author jens dietrich
 */
public class MVEL2ExpressionLanguage implements ExpressionLanguage {

	@Override
	public CompiledExpression compile(String definition,Map<String, Class<?>> typeInfo) throws ExpressionException {

		return new MVEL2CompiledExpression(definition,typeInfo);
	}

	@Override
	public void generateDefinitionCode(PrintWriter out, Expression expression,String varName) {
		
		// compile expression
		out.print("static Object ");
		out.print(varName);
		out.print(" = ");
		out.print("org.mvel2.MVEL.compileExpression(\"");
		out.print(expression.getExpression());
		out.println("\");");

	}
	
	@Override
	public void generateInvocationCode(PrintWriter out, Expression expression,String target,String expressionClass,String expressionField, TmpVarGenerator varNameGenerator, List<String> args) {
		if (args.size()!=expression.getInputSlots().size()) {
			throw new IllegalArgumentException("Number of arguments does not match number of input slots in " + expression);
		}

		String argVar = varNameGenerator.nextTmpVar("args");
		out.print("java.util.Map ");
		out.print(argVar);
		out.print(" = new java.util.HashMap(");
		out.print(args.size());
		out.println(");");
		
		// bind variables
		for (int i=0;i<expression.getInputSlots().size();i++) {
			out.print(argVar);
			out.print(".put(\"");
			out.print(expression.getInputSlots().get(i));
			out.print("\",");
			out.print(args.get(i));
			out.println(");");
		}

		// invoke expression
		out.print(target);
		out.print(" = (Boolean)org.mvel2.MVEL.executeExpression(");
		out.print(expressionClass);
		out.print('.');
		out.print(expressionField);
		out.print(',');
		out.print(argVar);
		out.println(");");
	}



}
