/**
 * Copyright 2008 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */

package test.nz.org.take.nscript;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import nz.org.take.*;
import nz.org.take.nscript.JSPELParser;
import nz.org.take.nscript.ScriptException;
import junit.framework.TestCase;

/**
 * Tests for the EL parser.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class JSPELParserTests extends TestCase {
	public void _testCond1() throws Exception {
		Fact f = new JSPELParser().parseCondition("42==48",42,false);
		Predicate p = f.getPredicate();
		Term[] terms = f.getTerms();
	}
	public void testTermNumberLiteral1() throws Exception {
		Term t = new JSPELParser().parseTerm("42",42);
		assertTrue(t instanceof Constant);
		assertEquals(Long.class,t.getType());
		assertEquals(new Long(42),((Constant)t).getObject());
	}
	public void testTermNumberLiteral2() throws Exception {
		Term t = new JSPELParser().parseTerm("42.42",42);
		assertTrue(t instanceof Constant);
		assertEquals(Double.class,t.getType());
		assertEquals(new Double(42.42),((Constant)t).getObject());
	}
	public void testTermNumberLiteral3() throws Exception {
		Term t = new JSPELParser().parseTerm("42e2",42);
		assertTrue(t instanceof Constant);
		assertEquals(Double.class,t.getType());
		assertEquals(new Double(42e2),((Constant)t).getObject());
	}
	public void testTermNumberLiteral4() throws Exception {
		Term t = new JSPELParser().parseTerm("-42",42);
		assertTrue(t instanceof Constant);
		assertEquals(Long.class,t.getType());
		assertEquals(new Long(-42),((Constant)t).getObject());
	}	
	public void testTermNumberLiteral5() throws Exception {
		Term t = new JSPELParser().parseTerm("-42.42",42);
		assertTrue(t instanceof Constant);
		assertEquals(Double.class,t.getType());
		assertEquals(new Double(-42.42),((Constant)t).getObject());
	}
	public void testTermNumberLiteral6() throws Exception {
		Term t = new JSPELParser().parseTerm("-42e2",42);
		assertTrue(t instanceof Constant);
		assertEquals(Double.class,t.getType());
		assertEquals(new Double(-42e2),((Constant)t).getObject());
	}
	public void testComplexArithmeticTerm1() throws Exception {
		Term t = new JSPELParser().parseTerm("4+2",42);
		assertTrue(t instanceof ComplexTerm);
		ComplexTerm ct = (ComplexTerm)t;
		Function f = ct.getFunction();
		BinaryArithmeticFunction bf = (BinaryArithmeticFunction)f;
		assertEquals("+",bf.getName());
		assertEquals(Long.class,bf.getReturnType());
	}
	
	public void testComplexArithmeticTermPrecedence1 () throws Exception {
		Term t = new JSPELParser().parseTerm("4*2+3",42);
		assertTrue(t instanceof ComplexTerm);
		ComplexTerm ct = (ComplexTerm)t;
		Function f = ct.getFunction();
		BinaryArithmeticFunction bf = (BinaryArithmeticFunction)f;
		assertEquals("+",bf.getName());
		assertEquals(Long.class,bf.getReturnType());
		Term t1 = ct.getTerms()[0];
		Term t2 = ct.getTerms()[1];
		assertTrue(t1 instanceof ComplexTerm);
		assertTrue(t2 instanceof Constant);
	}
	
	public void testComplexArithmeticTermPrecedence2() throws Exception {
		Term t = new JSPELParser().parseTerm("4+2*3",42);
		assertTrue(t instanceof ComplexTerm);
		ComplexTerm ct = (ComplexTerm)t;
		Function f = ct.getFunction();
		BinaryArithmeticFunction bf = (BinaryArithmeticFunction)f;
		assertEquals("+",bf.getName());
		assertEquals(Long.class,bf.getReturnType());
		Term t1 = ct.getTerms()[0];
		Term t2 = ct.getTerms()[1];
		assertTrue(t1 instanceof Constant);
		assertTrue(t2 instanceof ComplexTerm);
	}
	
	public void testComplexArithmeticTermBrackets1() throws Exception {
		Term t = new JSPELParser().parseTerm("(4+2)*3",42);
		assertTrue(t instanceof ComplexTerm);
		ComplexTerm ct = (ComplexTerm)t;
		Function f = ct.getFunction();
		BinaryArithmeticFunction bf = (BinaryArithmeticFunction)f;
		assertEquals("*",bf.getName());
		assertEquals(Long.class,bf.getReturnType());
		Term t1 = ct.getTerms()[0];
		Term t2 = ct.getTerms()[1];
		assertTrue(t1 instanceof ComplexTerm);
		assertTrue(t2 instanceof Constant);
	}
	
	public void testComplexArithmeticTermBrackets2() throws Exception {
		Term t = new JSPELParser().parseTerm("((4+2))*3",42);
		assertTrue(t instanceof ComplexTerm);
		ComplexTerm ct = (ComplexTerm)t;
		Function f = ct.getFunction();
		BinaryArithmeticFunction bf = (BinaryArithmeticFunction)f;
		assertEquals("*",bf.getName());
		assertEquals(Long.class,bf.getReturnType());
		Term t1 = ct.getTerms()[0];
		Term t2 = ct.getTerms()[1];
		assertTrue(t1 instanceof ComplexTerm);
		assertTrue(t2 instanceof Constant);
	}
	
	public void testTermStringLiteral1() throws Exception {
		Term t = new JSPELParser().parseTerm("'test'",42);
		assertTrue(t instanceof Constant);
		assertEquals(String.class,t.getType());
		assertEquals("test",((Constant)t).getObject());
	}
	public void testPropertyTerm1() throws Exception {
		Map<String,Constant> consts = new HashMap<String,Constant>();
		Map<String,Variable> vars = new HashMap<String,Variable>();
		Map<String,AggregationFunction> aggs = new HashMap<String,AggregationFunction>();
		Variable var = new Variable();
		var.setName("bean1");
		var.setType(TestBean.class);
		vars.put("bean1",var);
		Term t = new JSPELParser(vars,consts,aggs).parseTerm("bean1.name",42);
		assertTrue(t instanceof ComplexTerm);
		ComplexTerm xt = (ComplexTerm)t;
		JFunction f = (JFunction)xt.getFunction();
		assertEquals("getName",f.getMethod().getName());
		assertEquals(1,xt.getTerms().length);
		Term t1 = xt.getTerms()[0];
		assertEquals(var,t1);
	}
	public void testPropertyTerm2() throws Exception {
		Map<String,Constant> consts = new HashMap<String,Constant>();
		Map<String,Variable> vars = new HashMap<String,Variable>();
		Map<String,AggregationFunction> aggs = new HashMap<String,AggregationFunction>();
		Variable var = new Variable();
		var.setName("bean1");
		var.setType(TestBean.class);
		vars.put("bean1",var);
		Term t = new JSPELParser(vars,consts,aggs).parseTerm("bean1.id",42);
		assertTrue(t instanceof ComplexTerm);
		ComplexTerm xt = (ComplexTerm)t;
		JFunction f = (JFunction)xt.getFunction();
		assertEquals("getId",f.getMethod().getName());
		assertEquals(1,xt.getTerms().length);
		Term t1 = xt.getTerms()[0];
		assertEquals(var,t1);
	}
	public void testPropertyTerm3DeepAccess() throws Exception {
		Map<String,Constant> consts = new HashMap<String,Constant>();
		Map<String,Variable> vars = new HashMap<String,Variable>();
		Map<String,AggregationFunction> aggs = new HashMap<String,AggregationFunction>();
		Variable var = new Variable();
		var.setName("bean1");
		var.setType(TestBean.class);
		vars.put("bean1",var);
		Term t = new JSPELParser(vars,consts,aggs).parseTerm("bean1.property.name",42);
		
		ComplexTerm xt = (ComplexTerm)t;
		JFunction f = (JFunction)xt.getFunction();
		assertEquals("getName",f.getMethod().getName());
		assertEquals(1,xt.getTerms().length);
		
		xt = (ComplexTerm)xt.getTerms()[0];
		f = (JFunction)xt.getFunction();
		assertEquals("getProperty",f.getMethod().getName());
		assertEquals(1,xt.getTerms().length);
		
		Term t1 = xt.getTerms()[0];
		assertEquals(var,t1);
	}
	
	public void testPropertyTerm4() throws Exception {
		Map<String,Constant> consts = new HashMap<String,Constant>();
		Map<String,Variable> vars = new HashMap<String,Variable>();
		Map<String,AggregationFunction> aggs = new HashMap<String,AggregationFunction>();
		Variable var = new Variable();
		var.setName("bean1");
		var.setType(TestBean.class);
		vars.put("bean1",var);
		try {
			Term t = new JSPELParser(vars,consts,aggs).parseTerm("bean2.name",42);
			assertTrue(false);
		}
		catch (ScriptException x) {
			assertTrue(true);
		}
	}
	
	public void testPropertyTerm5() throws Exception {
		Map<String,Constant> consts = new HashMap<String,Constant>();
		Map<String,Variable> vars = new HashMap<String,Variable>();
		Map<String,AggregationFunction> aggs = new HashMap<String,AggregationFunction>();
		Variable var = new Variable();
		var.setName("bean1");
		var.setType(TestBean.class);
		vars.put("bean1",var);
		try {
			Term t = new JSPELParser(vars,consts,aggs).parseTerm("bean1.name2",42);
			assertTrue(false);
		}
		catch (ScriptException x) {
			assertTrue(true);
		}
	}
	
	public void testCondition1() throws Exception {
		Map<String,Constant> consts = new HashMap<String,Constant>();
		Map<String,Variable> vars = new HashMap<String,Variable>();
		Map<String,AggregationFunction> aggs = new HashMap<String,AggregationFunction>();
		Variable var = new Variable();
		var.setName("bean1");
		var.setType(TestBean.class);
		vars.put("bean1",var);
		Prerequisite c = new JSPELParser(vars,consts,aggs).parseCondition("bean1.name==\'Max\'",42,false);
		Predicate p = c.getPredicate();
		assertTrue(p instanceof JPredicate);
		Method m = ((JPredicate)p).getMethod();
		assertEquals("equals",m.getName());
		assertEquals(1,m.getParameterTypes().length);
		assertEquals(Object.class,m.getParameterTypes()[0]);
		Term t = c.getTerms()[0];
		assertTrue(t instanceof ComplexTerm);
		assertEquals(String.class,t.getType());
		t = c.getTerms()[1];
		assertTrue(t instanceof Constant);
		assertEquals(String.class,t.getType());
	}
	
	public void testCondition2() throws Exception {
		Map<String,Constant> consts = new HashMap<String,Constant>();
		Map<String,Variable> vars = new HashMap<String,Variable>();
		Map<String,AggregationFunction> aggs = new HashMap<String,AggregationFunction>();
		Variable var = new Variable();
		var.setName("bean1");
		var.setType(TestBean.class);
		vars.put("bean1",var);
		Prerequisite c = new JSPELParser(vars,consts,aggs).parseCondition("\'Max\'==bean1.name",42,false);
		Predicate p = c.getPredicate();
		assertTrue(p instanceof JPredicate);
		Method m = ((JPredicate)p).getMethod();
		assertEquals("equals",m.getName());
		assertEquals(1,m.getParameterTypes().length);
		assertEquals(Object.class,m.getParameterTypes()[0]);
		Term t = c.getTerms()[1];
		assertTrue(t instanceof ComplexTerm);
		assertEquals(String.class,t.getType());
		t = c.getTerms()[0];
		assertTrue(t instanceof Constant);
		assertEquals(String.class,t.getType());
	}
	
	public void testCondition3() throws Exception {
		Map<String,Constant> consts = new HashMap<String,Constant>();
		Map<String,Variable> vars = new HashMap<String,Variable>();
		Map<String,AggregationFunction> aggs = new HashMap<String,AggregationFunction>();
		Variable var = new Variable();
		var.setName("bean1");
		var.setType(TestBean.class);
		vars.put("bean1",var);
		Prerequisite c = new JSPELParser(vars,consts,aggs).parseCondition("bean1.id==42",42,false);
		Predicate p = c.getPredicate();
		assertTrue(p instanceof Comparison);
		assertEquals(new Comparison("=="),p);
		Term t = c.getTerms()[0];
		assertTrue(t instanceof ComplexTerm);
		assertEquals(int.class,t.getType());
		t = c.getTerms()[1];
		assertTrue(t instanceof Constant);
		assertEquals(Long.class,t.getType());
	}
	
	public void testCondition4() throws Exception {
		Map<String,Constant> consts = new HashMap<String,Constant>();
		Map<String,Variable> vars = new HashMap<String,Variable>();
		Map<String,AggregationFunction> aggs = new HashMap<String,AggregationFunction>();
		Variable var = new Variable();
		var.setName("bean1");
		var.setType(TestBean.class);
		vars.put("bean1",var);
		try{
			new JSPELParser(vars,consts,aggs).parseCondition("bean1.name==\'Max\' || bean1.name==\'Bender\'",42,false);
			assertTrue(false);
		}
		catch (ScriptException x) {
			assertTrue(true);
		}
		
	}
	// reference to property
	public void testJCondition1() throws Exception {
		Map<String,Constant> consts = new HashMap<String,Constant>();
		Map<String,Variable> vars = new HashMap<String,Variable>();
		Map<String,AggregationFunction> aggs = new HashMap<String,AggregationFunction>();
		Variable var = new Variable();
		var.setName("bean1");
		var.setType(TestBean.class);
		vars.put("bean1",var);
		Prerequisite c = new JSPELParser(vars,consts,aggs).parseCondition("bean1.cool",42,false);
		Predicate p = c.getPredicate();
		assertTrue(p instanceof JPredicate);
		Method m = ((JPredicate)p).getMethod();
		assertEquals("isCool",m.getName());
		assertEquals(0,m.getParameterTypes().length);
		assertEquals(Boolean.TYPE,m.getReturnType());
		Term t = c.getTerms()[0];
		assertTrue(t instanceof Variable);
		assertEquals(TestBean.class,t.getType());
		assertEquals("bean1",((Variable)t).getName());
		
	}
	// reference to getter
	public void testJCondition2() throws Exception {
		Map<String,Constant> consts = new HashMap<String,Constant>();
		Map<String,Variable> vars = new HashMap<String,Variable>();
		Map<String,AggregationFunction> aggs = new HashMap<String,AggregationFunction>();
		Variable var = new Variable();
		var.setName("bean1");
		var.setType(TestBean.class);
		vars.put("bean1",var);
		// alternative syntax using getter names instead of properties
		Prerequisite c = new JSPELParser(vars,consts,aggs).parseCondition("bean1.isCool",42,false);
		Predicate p = c.getPredicate();
		assertTrue(p instanceof JPredicate);
		Method m = ((JPredicate)p).getMethod();
		assertEquals("isCool",m.getName());
		assertEquals(0,m.getParameterTypes().length);
		assertEquals(Boolean.TYPE,m.getReturnType());
		Term t = c.getTerms()[0];
		assertTrue(t instanceof Variable);
		assertEquals(TestBean.class,t.getType());
		assertEquals("bean1",((Variable)t).getName());
		
	}
	// property of complex term
	public void testJCondition3() throws Exception {
		Map<String,Constant> consts = new HashMap<String,Constant>();
		Map<String,Variable> vars = new HashMap<String,Variable>();
		Map<String,AggregationFunction> aggs = new HashMap<String,AggregationFunction>();
		Variable var = new Variable();
		var.setName("bean1");
		var.setType(TestBean.class);
		vars.put("bean1",var);
		Prerequisite c = new JSPELParser(vars,consts,aggs).parseCondition("bean1.property.tested",42,false);
		Predicate p = c.getPredicate();
		assertTrue(p instanceof JPredicate);
		Method m = ((JPredicate)p).getMethod();
		assertEquals("isTested",m.getName());
		assertEquals(0,m.getParameterTypes().length);
		assertEquals(Boolean.TYPE,m.getReturnType());
		Term t = c.getTerms()[0];
		assertTrue(t instanceof ComplexTerm);
		assertEquals(TestProperty.class,t.getType());
		
	}
	
	// exception handling
	public void testJCondition4() throws Exception {
		Map<String,Constant> consts = new HashMap<String,Constant>();
		Map<String,Variable> vars = new HashMap<String,Variable>();
		Map<String,AggregationFunction> aggs = new HashMap<String,AggregationFunction>();
		Variable var = new Variable();
		var.setName("bean1");
		var.setType(TestBean.class);
		vars.put("bean1",var);
		try {
			Prerequisite c = new JSPELParser(vars,consts,aggs).parseCondition("bean1.isNotSoCool",42,false);
			assertTrue(false);
		}
		catch (ScriptException x) {
			assertTrue(true);
		}		
	}
	
}
