/**
 * Copyright 2008 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */
package nz.org.take.nscript;

import de.odysseus.el.tree.ExpressionNode;
import de.odysseus.el.tree.Node;
import de.odysseus.el.tree.Tree;
import de.odysseus.el.tree.TreeBuilder;
import de.odysseus.el.tree.impl.Builder;
import de.odysseus.el.tree.impl.ast.AstBinary;
import de.odysseus.el.tree.impl.ast.AstNode;
import de.odysseus.el.tree.impl.ast.AstBinary.Operator;
import nz.org.take.Predicate;
import nz.org.take.Prerequisite;
import nz.org.take.Term;

/**
 * JUEL based parser for JSP EL expressions.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class JSPELParser {
	private TreeBuilder builder = new Builder();
	public Prerequisite parseCondition(String s) throws ScriptException {
		s = "${" + s + "}"; // EL parser expects this
		Tree tree = builder.build(s);
		ExpressionNode root = tree.getRoot();
		if (root.getCardinality()==1) {
			Node child = root.getChild(0);
			if (child instanceof AstBinary) {
				AstBinary binN = (AstBinary)child;
				Operator op = binN.getOperator();
				Term t1 = this.parseTerm(binN.getChild(0)); // left
				Term t2 = this.parseTerm(binN.getChild(1)); // right
				Predicate p = getPredicate(op,t1.getType(),t2.getType());
				Prerequisite prereq = new Prerequisite();
				prereq.setPredicate(p);
				prereq.setTerms(new Term[]{t1,t2});
				return prereq;
			}
		}
		// TODO handle other cases
		throw new ScriptException ("Unsupported EL expression: " + s );
	} 
	private Predicate getPredicate(Operator op, Class type, Class type2) {
		// TODO 
		return null;
	}
	public Term parseTerm(String s) throws ScriptException {
		s = "${" + s + "}"; // EL parser expects this
		Tree tree = builder.build(s);
		return parseTerm(tree.getRoot());
	}
	public Term parseTerm(Node n) throws ScriptException {
		throw new ScriptException ("Not yet implemented");
	}

}
