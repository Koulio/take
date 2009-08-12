/**
 * Copyright 2009 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */

package nz.org.take;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import nz.org.take.compiler.util.TmpVarGenerator;


/**
 * A body expression is a prerequisite of a rule that can be evaluated
 * through an expression language interpreter such as MVEL or OGNL.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public abstract class Expression  {
	protected ExpressionLanguage xLanguage = null;
	protected String expression = null;
	protected ExpressionLanguage.CompiledExpression compiledExpression = null;
	protected Collection<Variable> variables = new HashSet<Variable>();
	
	public Expression(String expression,String language, Map<String,Class<?>> typeInfo) throws ExpressionException  {
		super();
		this.expression = expression;
		try {
			Class<ExpressionLanguage> LANG = (Class<ExpressionLanguage>) Class.forName(language);
			xLanguage = LANG.newInstance();
			compiledExpression = xLanguage.compile(expression,typeInfo);
			for (String slot:compiledExpression.getInputSlots()) {
				Variable var = new Variable();
				var.setName(slot);
				var.setType(typeInfo.get(slot));
				variables.add(var);
			}
		} catch (InstantiationException e) {
			throw new ExpressionException("Cannot instantiate expression language "+language,e);
		} catch (IllegalAccessException e) {
			throw new ExpressionException("Cannot access expression language "+language,e);
		} catch (ClassNotFoundException e) {
			throw new ExpressionException("Cannot find expression language class "+language,e);
		}
	}
	
	public ExpressionLanguage getLanguage() {
		return xLanguage;
	}

	public String getExpression() {
		return expression;
	}


	/**
	 * Get the input slots used in the expression. This will be delegated to the
	 * expression language. Example: for p.getName()==\"Mr. Foo\" , the result would be ["p"].
	 */
	public List<String> getInputSlots () {
		return compiledExpression.getInputSlots();
	}
	/**
	 * Get the type resulting from evaluating the expression.
	 * @return
	 */
	public Class<?> getType() {
		return compiledExpression.getType();
	}
	
	public Collection<Variable> getVariables() {
		return variables;
	}
	/**
	 * Generate the code that can be used to define a field representing this expression.
	 * @param out
	 * @param expressionField
	 * @param args
	 */
	public void generateDefinitionCode(PrintWriter out,String expressionField){
		this.xLanguage.generateDefinitionCode(out,this,expressionField);
	}
	/**
	 * Generate the code that can be used to invoke this expression, and assign the result to
	 * a variable named target.
	 * @param out
	 * @param target the result will be assigned to this variable
	 * @param expressionClass the class where compiled expressions are defined as static fields
	 * @param expressionField the static field name where the compiled expression is stored 
	 * @param varNameGenerator
	 * @param args
	 */
	public void generateInvocationCode(PrintWriter out,String target,String expressionClass,String expressionField, TmpVarGenerator varNameGenerator,List<String> args){
		this.xLanguage.generateInvocationCode(out,this,target,expressionClass,expressionField,varNameGenerator,args);
	}
}
