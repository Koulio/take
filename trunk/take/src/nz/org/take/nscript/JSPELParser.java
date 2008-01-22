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

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import de.odysseus.el.tree.ExpressionNode;
import de.odysseus.el.tree.Node;
import de.odysseus.el.tree.Tree;
import de.odysseus.el.tree.TreeBuilder;
import de.odysseus.el.tree.impl.Builder;
import de.odysseus.el.tree.impl.ast.AstBinary;
import de.odysseus.el.tree.impl.ast.AstDot;
import de.odysseus.el.tree.impl.ast.AstEval;
import de.odysseus.el.tree.impl.ast.AstIdentifier;
import de.odysseus.el.tree.impl.ast.AstNode;
import de.odysseus.el.tree.impl.ast.AstNumber;
import de.odysseus.el.tree.impl.ast.AstString;
import de.odysseus.el.tree.impl.ast.AstUnary;
import de.odysseus.el.tree.impl.ast.AstBinary.Operator;
import nz.org.take.Comparison;
import nz.org.take.ComplexTerm;
import nz.org.take.Constant;
import nz.org.take.JFunction;
import nz.org.take.JPredicate;
import nz.org.take.Predicate;
import nz.org.take.Prerequisite;
import nz.org.take.TakeException;
import nz.org.take.Term;
import nz.org.take.Variable;

/**
 * JUEL based parser for JSP EL expressions.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class JSPELParser {
	
	private Map<String,Variable> variables = new HashMap<String,Variable>();
	private Map<String,Constant> constants = new HashMap<String,Constant>();
	
	private TreeBuilder builder = new Builder();
	
	public JSPELParser(Map<String, Variable> variables,
			Map<String, Constant> constants) {
		super();
		this.variables = variables;
		this.constants = constants;
	}
	
	public JSPELParser() {
		super();
	}
	
	public Prerequisite parseCondition(String s,int line) throws ScriptException {
		s = "${" + s + "}"; // EL parser expects this
		Tree tree = builder.build(s);
		ExpressionNode root = tree.getRoot();
		if (root.getCardinality()==1) {
			Node child = root.getChild(0);
			if (child instanceof AstBinary) {
				AstBinary binN = (AstBinary)child;
				Operator op = binN.getOperator();
				Term t1 = this.parseTerm(binN.getChild(0),line); // left
				Term t2 = this.parseTerm(binN.getChild(1),line); // right
				Predicate p = getPredicate(op,t1.getType(),t2.getType(),line);
				Prerequisite prereq = new Prerequisite();
				prereq.setPredicate(p);
				prereq.setTerms(new Term[]{t1,t2});
				return prereq;
			}
		}
		// TODO handle other cases
		throw new ScriptException ("Unsupported EL expression: " + s );
	} 
	private Predicate getPredicate(Operator op, Class type1, Class type2,int line) throws ScriptException  {
		if (op == AstBinary.EQ && isNumeric(type1) &&  isNumeric(type2)) {
			try {
				return new Comparison("==");
			} catch (TakeException e) {
				error(line,e.getMessage());
			}
		} 
		else if (op == AstBinary.EQ && !isNumeric(type1) && !isNumeric(type2)) {
			try {
				Method m = type1.getMethod("equals",new Class[]{Object.class});
				JPredicate p = new JPredicate();
				p.setMethod(m);
				return p;
			} catch (Exception e) {
				// nothing todo - always exists
			}
		} 
		this.error(line,"Cannot build predicate for operation ",op.toString());
		return null;
	}
	
	private boolean isNumeric(Class type) {
		return (type.isPrimitive() && type!=Boolean.TYPE) || 
			Byte.class==type ||
			Double.class==type ||
			Float.class==type ||
			Integer.class==type ||
			Long.class==type ||
			Short.class==type ||
			Character.class==type;
	}
	public Term parseTerm(String s,int line) throws ScriptException {
		s = "${" + s + "}"; // EL parser expects this
		Tree tree = builder.build(s);
		return parseTerm(tree.getRoot(),line);
	}
	public Term parseTerm(Node n,int line) throws ScriptException {
		boolean MINUS = false;
		
		if (n instanceof AstEval) {
			n = n.getChild(0);
		}
		
		if (n instanceof AstUnary && ((AstUnary)n).getOperator()==AstUnary.NEG) {
			MINUS=true;
			n = n.getChild(0);
		} 
		
		
		if (n instanceof AstNumber) {
			AstNumber _n = (AstNumber)n;
			Number o = (Number)_n.eval(null,null);
			Constant c = new Constant();
			if (MINUS) {
				if (o instanceof Double) {
					o = new Double(-o.doubleValue());
				}
				else if (o instanceof Long) {
					o = new Long(-o.longValue());
				}
			}
			c.setObject(o);
			return c;
		}
		
		else if (n instanceof AstString) {
			AstString _n = (AstString)n;
			String o = (String)_n.eval(null,null);
			Constant c = new Constant();
			c.setObject(o);
			return c;
		}
		
		else if (n instanceof AstDot) {
			AstDot d = (AstDot)n;
			// TODO hack: remove ". " prefix - will have to modify JUEL to access property name directly
			String p = d.toString().substring(2);
			AstNode c = d.getChild(0); // head
			Term t = this.parseTerm(c,line);
			// verify property
			PropertyDescriptor property = findProperty(t.getType(),p,line);
			JFunction f = new JFunction();
			f.setMethod(property.getReadMethod());
			ComplexTerm ct = new ComplexTerm();
			ct.setFunction(f);
			ct.setTerms(new Term[]{t});
			return ct;
		}
		
		else if (n instanceof AstIdentifier) {
			// TODO hack: remove toString and  modify JUEL to access name property directly
			String identifier = n.toString();
			// try to find a variable 
			Term t = this.variables.get(identifier);
			if (t!=null) 
				return t;
			// try to find a constant 
			t = this.constants.get(identifier);
			if (t!=null) 
				return t;	
			else 
				this.error(line,"Identifier ",identifier,"has not yet been declared - use var or ref to declare it");
		}
		
		throw new ScriptException("Cannot parse EL expression: " + n);
	}
	private PropertyDescriptor findProperty(Class type, String p, int line) throws ScriptException {
		PropertyDescriptor[] properties = null;
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(type);
			properties = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property:properties) {
				if (property.getName().equals(p)) {
					return property;
				}
			}
		}
		catch (IntrospectionException x) {}
		this.error(line,"introspection exception "," cannot find property ",p,"in class ",type.getName());
		return null;
	}
	public Map<String, Variable> getVariables() {
		return variables;
	}
	public void setVariables(Map<String, Variable> variables) {
		this.variables = variables;
	}
	public Map<String, Constant> getConstants() {
		return constants;
	}
	public void setConstants(Map<String, Constant> constants) {
		this.constants = constants;
	}

	private void error(int no,String... description) throws ScriptException{
		StringBuffer buf = new StringBuffer();
		buf.append("Parser exception at line ");
		buf.append(no);
		buf.append(' ');
		for (String t:description)
			buf.append(t);
		throw new ScriptException(buf.toString(),no);
	}
}
