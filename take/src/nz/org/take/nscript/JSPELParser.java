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
import javax.el.ELException;
import de.odysseus.el.tree.ExpressionNode;
import de.odysseus.el.tree.Node;
import de.odysseus.el.tree.Tree;
import de.odysseus.el.tree.TreeBuilder;
import de.odysseus.el.tree.impl.Builder;
import de.odysseus.el.tree.impl.ast.AstBinary;
import de.odysseus.el.tree.impl.ast.AstDot;
import de.odysseus.el.tree.impl.ast.AstEval;
import de.odysseus.el.tree.impl.ast.AstFunction;
import de.odysseus.el.tree.impl.ast.AstIdentifier;
import de.odysseus.el.tree.impl.ast.AstNested;
import de.odysseus.el.tree.impl.ast.AstNode;
import de.odysseus.el.tree.impl.ast.AstNumber;
import de.odysseus.el.tree.impl.ast.AstString;
import de.odysseus.el.tree.impl.ast.AstUnary;
import de.odysseus.el.tree.impl.ast.AstBinary.Operator;
import nz.org.take.AggregationFunction;
import nz.org.take.BinaryArithmeticFunction;
import nz.org.take.Comparison;
import nz.org.take.ComplexTerm;
import nz.org.take.Constant;
import nz.org.take.Function;
import nz.org.take.JPredicate;
import nz.org.take.Predicate;
import nz.org.take.Prerequisite;
import nz.org.take.TakeException;
import nz.org.take.Term;
import nz.org.take.Variable;
import nz.org.take.compiler.util.PrimitiveTypeUtils;

/**
 * JUEL based parser for JSP EL expressions.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class JSPELParser extends ParserSupport {
	
	private Map<String,Variable> variables = new HashMap<String,Variable>();
	private Map<String,Constant> constants = new HashMap<String,Constant>();
	private Map<String,AggregationFunction> aggregations = new HashMap<String,AggregationFunction>();
	
	private TreeBuilder builder = new Builder();
	
	public JSPELParser(Map<String, Variable> variables,
			Map<String, Constant> constants,
			Map<String,AggregationFunction> aggregations) {
		super();
		this.variables = variables;
		this.constants = constants;
		this.aggregations = aggregations;
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
			// expressions like 42<person.age
			if (child instanceof AstBinary) {
				AstBinary binN = (AstBinary)child;
				Operator op = binN.getOperator();
				
				if (op == AstBinary.AND) 
					this.error(line,"The logical operator AND (&&) is not allowed here, use multiple conditions in rule body instead");
				else if (op == AstBinary.OR) 
					this.error(line,"The logical operator OR (||) not allowed here, use multiple rules instead");
				
				Term t1 = this.parseTerm(binN.getChild(0),line); // left
				Term t2 = this.parseTerm(binN.getChild(1),line); // right
				Predicate p = getPredicate(op,t1.getType(),t2.getType(),line);
				Prerequisite prereq = new Prerequisite();
				prereq.setPredicate(p);
				prereq.setTerms(new Term[]{t1,t2});
				return prereq;
			}
			// expression like person.male or person.isMale
			else if (child instanceof AstDot) {
				AstDot d = (AstDot)child;
				// TODO hack: remove ". " prefix - will have to modify JUEL to access property name directly
				String p = d.toString().substring(2);
				AstNode c = d.getChild(0); // head
				Term t = this.parseTerm(c,line);
				Method m = null;
				// verify property
				PropertyDescriptor property = null;
				try {
					property = findProperty(t.getType(),p,line);
				}
				catch (ScriptException x) {
					// ignore - try to find a matching getter
				}
				if (property!=null) {
					if (property.getPropertyType()!=Boolean.TYPE) {
						this.error(line,"Only boolean properties can be used as predicates, but ",property.getName()," in ",t.getType().getName()," has the following type: ",property.getPropertyType().getName());
					}
					else {
						m = property.getReadMethod();
					}
				}
				else {
					// try to find a method with this name
					// the advantage is that we can use  expressions like person.isMale 
					// by only using properties we would have to use person.male
					try {
						Method m2 = t.getType().getMethod(p,new Class[]{});
						if (m2.getReturnType()==Boolean.TYPE && java.lang.reflect.Modifier.isPublic(m2.getModifiers())) {
							m = m2;
						}
					}
					catch (Exception x) {
						error(line,x);
					}
				}
				if (m==null) {
					this.error(line,"Cannot build predicate for ",p," - it represents neither a boolean property nor a getter for such a property in ",t.getType() );
				}
				else {
					JPredicate predicate = new JPredicate();
					predicate.setMethod(m);
					Prerequisite prereq = new Prerequisite();
					prereq.setPredicate(predicate);
					prereq.setTerms(new Term[]{t});
					return prereq;
				}
			}
		}
		this.error(line,"Unsupported EL expression: ",s );
		return null;
	} 
	private Function getFunction(Operator op, Class type1, Class type2,int line) throws ScriptException  {
		
		if (op == AstBinary.ADD && isNumeric(type1) &&  isNumeric(type2)) {
			return BinaryArithmeticFunction.getInstance("+",type1,type2);
		} 
		else if (op == AstBinary.SUB && isNumeric(type1) &&  isNumeric(type2)) {
			return BinaryArithmeticFunction.getInstance("-",type1,type2);
		} 
		else if (op == AstBinary.MUL && isNumeric(type1) &&  isNumeric(type2)) {
			return BinaryArithmeticFunction.getInstance("*",type1,type2);
		} 
		else if (op == AstBinary.DIV && isNumeric(type1) &&  isNumeric(type2)) {
			return BinaryArithmeticFunction.getInstance("/",type1,type2);
		} 
		else if (op == AstBinary.MOD && isNumeric(type1) &&  isNumeric(type2)) {
			return BinaryArithmeticFunction.getInstance("%",type1,type2);
		} 
		else 
			this.error(line,"Cannot build function ",op.toString()," with parameter types ",type1.toString()," ",type2.toString());
		return null;
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
		else if (op == AstBinary.NE && isNumeric(type1) &&  isNumeric(type2)) {
			try {
				return new Comparison("!=");
			} catch (TakeException e) {
				error(line,e.getMessage());
			}
		} 
		else if (op == AstBinary.NE && !isNumeric(type1) && !isNumeric(type2)) {
			try {
				Method m = type1.getMethod("equals",new Class[]{Object.class});
				JPredicate p = new JPredicate();
				p.setNegated(true);
				p.setMethod(m);
				return p;
			} catch (Exception e) {
				// nothing todo - always exists
			}
		} 
		else if (isNumeric(type1) && isNumeric(type2)) {
			// all other operators are numeric
			try {
				if (op == AstBinary.GE)
					return new Comparison(">=");
				else if (op == AstBinary.GT)
					return new Comparison(">");
				else if (op == AstBinary.LE)
					return new Comparison("<=");
				else if (op == AstBinary.LT)
					return new Comparison("<");
			} catch (TakeException e) {
				error(line,e.getMessage());
			}
				
		} 
		this.error(line,"Cannot build predicate for operation ",op.toString());
		return null;
	}
	
	private boolean isNumeric(Class type) {
		return PrimitiveTypeUtils.isNumericType(type);
	}
	public Term parseTerm(String s,int line) throws ScriptException {
		s = "${" + s + "}"; // EL parser expects this
		try {
			Tree tree = builder.build(s);
			return parseTerm(tree.getRoot(),line);
		}
		catch (ELException x) {
			throw new ScriptException("Parser exception at line "+line+ " caused by EL parser exception",line);
		}
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
			
			// function with this name
			Function f = createFunction(p, line,t.getType());
			if (f!=null) {
				ComplexTerm ct = new ComplexTerm();
				ct.setFunction(f);
				ct.setTerms(new Term[]{t});
				return ct;
			}			
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
				this.error(line,"identifier ",identifier,"has not yet been declared - use var or ref to declare it");
		}
		
		else if (n instanceof AstBinary) {
			AstBinary binN = (AstBinary)n;
			Operator op = binN.getOperator();
			Term t1 = this.parseTerm(binN.getChild(0),line); // left
			Term t2 = this.parseTerm(binN.getChild(1),line); // right
			Function f = this.getFunction(op,t1.getType(),t2.getType(),line);
			ComplexTerm ct = new ComplexTerm();
			ct.setFunction(f);
			ct.setTerms(new Term[]{t1,t2});
			return ct;
		}
		
		else if (n instanceof AstNested) {
			AstNested nn = (AstNested)n;
			return this.parseTerm(nn.getChild(0), line);
		}
		else if (n instanceof AstFunction) {
			AstFunction f = (AstFunction)n;
			String name = f.getName();
			int s = f.getCardinality();
			Term[] terms = new Term[s];
			Class[] termTypes = new Class[s];
			for (int i=0;i<s;i++) {
				terms[i] = this.parseTerm(n.getChild(i), line);
				termTypes[i] = terms[i].getType();
			}
			ComplexTerm ct = new ComplexTerm();
			ct.setTerms(terms);
			
			Function af = createFunction(name,line,termTypes);
			if (af!=null) {
				ct.setFunction(af);
				return ct;
			}
		}
		
		this.error(line,"cannot parse EL expression: ",n);
		return null;
	}
	private Function createFunction(String name,int line,Class... termTypes) throws ScriptException {
		Function f = FunctionFactory.getDefaultInstance().createFunction(name,this.aggregations,termTypes);	
		if (f==null) {
			this.error(line,"cannot interpret ",name," as a function");
			return null;
		}
		else {
			this.debug("interpreting ",name," in line ",line," as function ",f);
			return f;
		}
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

	public Map<String, AggregationFunction> getAggregations() {
		return aggregations;
	}

	public void setAggregations(Map<String, AggregationFunction> aggregations) {
		this.aggregations = aggregations;
	}
}
