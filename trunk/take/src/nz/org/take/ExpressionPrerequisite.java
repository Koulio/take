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

import java.util.Map;



/**
 * A body expression is a prerequisite of a rule that can be evaluated
 * through an expression language interpreter such as MVEL or OGNL.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */
public class ExpressionPrerequisite extends Expression implements Prerequisite {

	public ExpressionPrerequisite(String expression, String expressionLanguage, Map<String, Class> typeInfo) throws ExpressionException{
		super(expression, expressionLanguage, typeInfo);
		Class type = getType();
		if (type!=Boolean.class && type!=Boolean.TYPE) {
			throw new ExpressionException ("Expressions used as prerequisites should retun a boolean, but " + expression + " returns a value of type " + type);
		} 
	}
	public void accept(KnowledgeBaseVisitor visitor) {
		visitor.visit(this);
		visitor.endVisit(this);
	}
	
	public String toString() {
		StringBuffer b = new StringBuffer();
		b.append(this.expression);
		return b.toString();
	}
	
}
