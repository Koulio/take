/*
 * Copyright (C) 2007 <A href="http://www-ist.massey.ac.nz/JBDietrich" target="_top">Jens Dietrich</a>
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package test.nz.org.take.script;

import java.io.StringReader;
import nz.org.take.script.*;
import nz.org.take.script.parser.Parser;
import junit.framework.TestCase;

/**
 * Parser tests.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class ParserTests extends TestCase {
	static Parser p =null;
	private Script parse(String input) throws Exception {
		if (p==null)
			 p = new Parser(new StringReader(input));
		else
			p.ReInit(new StringReader(input));
		Script script = new Script();
		p.parse(script);
		return script;
	}
	private VariableDeclaration getVarDecAt(Script script,int i) {
		return (VariableDeclaration)script.getElements().get(i);
	}
	private Comment getCommentAt(Script script,int i) {
		return (Comment)script.getElements().get(i);
	}
	private Annotation getAnnotationAt(Script script,int i) {
		return (Annotation)script.getElements().get(i);
	}
	private ScriptElement getElementAt(Script script,int i) {
		return (ScriptElement)script.getElements().get(i);
	}
	private Rule getRuleAt(Script script,int i) {
		return (Rule)script.getElements().get(i);
	}
	private QuerySpec getQueryAt(Script script,int i) {
		return (QuerySpec)script.getElements().get(i);
	}
	public void test1() throws Exception {
		String input = 
			"var int x\n";
		Script script = parse(input);
		assertEquals(1,script.getElements().size());
		assertTrue(script.getElements().get(0) instanceof VariableDeclaration);
		assertEquals("x",this.getVarDecAt(script,0).getNames().get(0));
		assertEquals("int",this.getVarDecAt(script,0).getType());
	}
	public void test2() throws Exception {
		String input = 
			"var java.lang.String aVar\n";
		Script script = parse(input);
		assertEquals(1,script.getElements().size());
		assertTrue(script.getElements().get(0) instanceof VariableDeclaration);	
		assertEquals("aVar",this.getVarDecAt(script,0).getNames().get(0));
		assertEquals("java.lang.String",this.getVarDecAt(script,0).getType());
	}
	public void test3() throws Exception {
		String input = 
			"var int x\n"+
			"var java.lang.String aVar\n";
		Script script = parse(input);
		assertEquals(2,script.getElements().size());
		assertTrue(script.getElements().get(0) instanceof VariableDeclaration);
		assertTrue(script.getElements().get(1) instanceof VariableDeclaration);
		assertEquals("x",this.getVarDecAt(script,0).getNames().get(0));
		assertEquals("int",this.getVarDecAt(script,0).getType());
		assertEquals("aVar",this.getVarDecAt(script,1).getNames().get(0));
		assertEquals("java.lang.String",this.getVarDecAt(script,1).getType());
		
	}
	public void test4() throws Exception {
		String input = 
			"var int x,y\n"+
			"var java.lang.String a,b,c\n";
		Script script = parse(input);
		assertEquals(2,script.getElements().size());
		assertTrue(script.getElements().get(0) instanceof VariableDeclaration);
		assertTrue(script.getElements().get(1) instanceof VariableDeclaration);
		assertEquals("x",this.getVarDecAt(script,0).getNames().get(0));
		assertEquals("y",this.getVarDecAt(script,0).getNames().get(1));
		assertEquals("int",this.getVarDecAt(script,0).getType());
		assertEquals("a",this.getVarDecAt(script,1).getNames().get(0));
		assertEquals("b",this.getVarDecAt(script,1).getNames().get(1));
		assertEquals("java.lang.String",this.getVarDecAt(script,1).getType());
		
	}
	
	public void test10() throws Exception {
		String input = 
			"rule1: if p(x,y) then q(y,x)\n";
		Script script = parse(input);
		assertEquals(1,script.getElements().size());
		Rule r = this.getRuleAt(script,0);
		Condition prereq1= r.getConditions().get(0);
		Condition concl = r.getConditions().get(1);
		assertEquals("rule1",r.getId());
		assertEquals("p",prereq1.getPredicate());
		assertEquals("q",concl.getPredicate());
		assertEquals(new VariableTerm("x"),prereq1.getTerms().get(0));
	}
	public void test11() throws Exception {
		String input = "rule2: if equals(x,y) then q(y,x)\n";
		Script script = parse(input);
		Rule r = this.getRuleAt(script,0);
		Condition prereq1= r.getConditions().get(0);
		Condition concl = r.getConditions().get(1);
		assertEquals("rule2",r.getId());
		assertEquals("equals",prereq1.getPredicate());
		assertEquals("q",concl.getPredicate());
		assertEquals(new VariableTerm("x"),prereq1.getTerms().get(0));		
	}
	public void test12() throws Exception {
		String input = 
			"rule3: if p1(x,y) and p2(x,y) then p3(y,x)\n";
		Script script = parse(input);
		Rule r = this.getRuleAt(script,0);
		Condition prereq1= r.getConditions().get(0);
		Condition prereq2= r.getConditions().get(1);
		Condition concl = r.getConditions().get(2);
		assertEquals("rule3",r.getId());
		assertEquals("p1",prereq1.getPredicate());
		assertEquals("p2",prereq2.getPredicate());
		assertEquals("p3",concl.getPredicate());	
	}
	public void test13() throws Exception {
		String input = 
			"fact1: p(x,y)\n";
		Script script = parse(input);
		Rule r = this.getRuleAt(script,0);
		Condition f = r.getConditions().get(0);
		assertEquals("fact1",r.getId());
		assertEquals("p",f.getPredicate());
	}
	// string literals
	public void test14() throws Exception {
		String input = 
			"fact1: p(\"Max\",y)\n";
		Script script = parse(input);
		Rule r = this.getRuleAt(script,0);
		Condition f = r.getConditions().get(0);
		Term t1 = f.getTerms().get(0);
		Term t2 = f.getTerms().get(1);
		assertTrue(t1 instanceof ConstantTerm);
		assertEquals("Max",((ConstantTerm)t1).getValue());
		assertTrue(t2 instanceof VariableTerm);
	}
	// complex terms
	public void test15() throws Exception {
		String input = "fact: p(f(x),g(y,z))\n";
		Script script = parse(input);
		Condition f = this.getRuleAt(script,0).getConditions().get(0);
		assertEquals("p",f.getPredicate());
		assertEquals("p",f.getPredicate());
		Term t1 = f.getTerms().get(0);
		Term t2 = f.getTerms().get(1);
		assertTrue(t1 instanceof ComplexTerm);
		assertTrue(t2 instanceof ComplexTerm);	
		ComplexTerm c1 = (ComplexTerm)t1;
		ComplexTerm c2 = (ComplexTerm)t2;
		assertEquals("f",c1.getFunction());
		assertEquals(1,c1.getTerms().size());
		assertTrue(c1.getTerms().get(0) instanceof VariableTerm);
		assertEquals("x",((VariableTerm)c1.getTerms().get(0)).getName());
		assertEquals("g",c2.getFunction());
		assertEquals(2,c2.getTerms().size());
		assertTrue(c2.getTerms().get(0) instanceof VariableTerm);
		assertEquals("y",((VariableTerm)c2.getTerms().get(0)).getName());
		assertTrue(c2.getTerms().get(1) instanceof VariableTerm);
		assertEquals("z",((VariableTerm)c2.getTerms().get(1)).getName());
	}
	
	public void test20() throws Exception {
		String input = 
			"query p(in,out)\n";
		Script script = parse(input);
		assertEquals(1,script.getElements().size());
		QuerySpec q = this.getQueryAt(script,0);
		assertEquals("p",q.getPredicate());
		assertEquals(2,q.getIoSpec().size());
		assertTrue(q.getIoSpec().get(0));
		assertFalse(q.getIoSpec().get(1));
	}
	public void test30() throws Exception {
		String input = 
			"// variables\n"+
			"var int x,y\n"+
			"var java.lang.String a,b,c\n"+
			"//queries\n"+
			"query p(in,out)\n"+
			"//rules and facts\n"+
			"fact1: p(\"Max\",y)\n";
		Script script = parse(input);
		assertEquals(7,script.getElements().size());
		Comment c1 = this.getCommentAt(script,0);
		Comment c2 = this.getCommentAt(script,3);
		Comment c3 = this.getCommentAt(script,5);
		assertEquals("variables",c1.getText().trim());
		assertEquals("queries",c2.getText().trim());
		assertEquals("rules and facts",c3.getText().trim());
	}
	public void test40() throws Exception {
		String input = 
			"@@dc:Creator=Jens\n"+
			"@@dc:Date=01/01/07\n"+
			"var int x,y\n"+
			"var java.lang.String a,b,c\n"+
			"//queries\n"+
			"@description=a query\n"+
			"query p(in,out)\n";
		
		Script script = parse(input);
		
		assertEquals(7,script.getElements().size());
		
		Annotation a1 = getAnnotationAt(script,0);
		Annotation a2 = getAnnotationAt(script,1);
		Annotation a3 = getAnnotationAt(script,5);
		
		assertEquals("dc:Creator",a1.getKey().trim());
		assertEquals("Jens",a1.getValue().trim());
		assertTrue(a1.isGlobal());
		
		assertEquals("dc:Date",a2.getKey().trim());
		assertEquals("01/01/07",a2.getValue().trim());
		assertTrue(a2.isGlobal());
		
		assertEquals("description",a3.getKey().trim());
		assertEquals("a query",a3.getValue().trim());
		assertFalse(a3.isGlobal());
	}
	public void test50() throws Exception {
		String input = 
			"@@dc:Creator=Jens\n"+
			"@@dc:Date=01/01/07\n"+
			"var int x,y\n"+
			"var java.lang.String a,b,c\n"+
			"//queries\n"+
			"@description=a query\n"+
			"query p(in,out)\n"+
			"rule3: if p1(x,y) and p2(x,y) then p3(y,x)\n";
		
		Script script = parse(input);
		for (int i=0;i<8;i++)
			assertEquals(i+1,getElementAt(script,i).getLine());
		
		// check terms
		Rule r = this.getRuleAt(script,7);
		Condition c = r.getConditions().get(0);
		assertEquals(8,c.getLine());
		VariableTerm v = (VariableTerm)c.getTerms().get(0);
		assertEquals(8,v.getLine());
		

	}
}
