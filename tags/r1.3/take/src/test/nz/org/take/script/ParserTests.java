/**
 * Copyright 2007 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */

package test.nz.org.take.script;

import java.io.StringReader;
import java.util.List;
import test.nz.org.take.TakeTestCase;
import nz.org.take.script.*;
import nz.org.take.script.parser.Parser;

/**
 * Parser tests.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class ParserTests extends TakeTestCase {
	public ParserTests(String name) {
		super(name);
	}
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
	private RefDeclaration getRefDecAt(Script script,int i) {
		return (RefDeclaration)script.getElements().get(i);
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
	public void testVariables1() throws Exception {
		String input = 
			"var int x\n";
		Script script = parse(input);
		assertEquals(1,script.getElements().size());
		assertTrue(script.getElements().get(0) instanceof VariableDeclaration);
		assertEquals("x",this.getVarDecAt(script,0).getNames().get(0));
		assertEquals("int",this.getVarDecAt(script,0).getType());
	}
	public void testVariables2() throws Exception {
		String input = 
			"var java.lang.String aVar\n";
		Script script = parse(input);
		assertEquals(1,script.getElements().size());
		assertTrue(script.getElements().get(0) instanceof VariableDeclaration);	
		assertEquals("aVar",this.getVarDecAt(script,0).getNames().get(0));
		assertEquals("java.lang.String",this.getVarDecAt(script,0).getType());
	}
	public void testVariables3() throws Exception {
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
	public void testVariables4() throws Exception {
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
	public void testAggregation1() throws Exception {
		String input = 
			"aggregation agg1 = avg x score[client,x]\n";
		Script script = parse(input);
		assertEquals(1,script.getElements().size());
		assertTrue(script.getElements().get(0) instanceof Aggregation);
		Aggregation agg = (Aggregation)script.getElements().get(0);
		assertEquals("agg1",agg.getName());
		assertEquals("avg",agg.getFunction());
		assertEquals("x",agg.getVariable());
		Condition c = agg.getCondition();
		assertEquals("score",c.getPredicate());
		assertEquals(2,c.getTerms().size());
		assertEquals("client",c.getTerms().get(0).toString());
		assertEquals("x",c.getTerms().get(1).toString());
	}
	public void testAggregation2() throws Exception {
		String input = 
			"aggregation agg1 = sum x score[client,x]\n";
		Script script = parse(input);
		assertEquals(1,script.getElements().size());
		assertTrue(script.getElements().get(0) instanceof Aggregation);
		Aggregation agg = (Aggregation)script.getElements().get(0);
		assertEquals("agg1",agg.getName());
		assertEquals("sum",agg.getFunction());
		assertEquals("x",agg.getVariable());
		Condition c = agg.getCondition();
		assertEquals("score",c.getPredicate());
		assertEquals(2,c.getTerms().size());
		assertEquals("client",c.getTerms().get(0).toString());
		assertEquals("x",c.getTerms().get(1).toString());
	}
	public void testAggregation3() throws Exception {
		String input = 
			"aggregation agg1 = count x score[client,x]\n";
		Script script = parse(input);
		assertEquals(1,script.getElements().size());
		assertTrue(script.getElements().get(0) instanceof Aggregation);
		Aggregation agg = (Aggregation)script.getElements().get(0);
		assertEquals("agg1",agg.getName());
		assertEquals("count",agg.getFunction());
		assertEquals("x",agg.getVariable());
		Condition c = agg.getCondition();
		assertEquals("score",c.getPredicate());
		assertEquals(2,c.getTerms().size());
		assertEquals("client",c.getTerms().get(0).toString());
		assertEquals("x",c.getTerms().get(1).toString());
	}
	
	public void testAggregation4() throws Exception {
		String input = 
			"aggregation agg1 = min x score[client,x]\n";
		Script script = parse(input);
		assertEquals(1,script.getElements().size());
		assertTrue(script.getElements().get(0) instanceof Aggregation);
		Aggregation agg = (Aggregation)script.getElements().get(0);
		assertEquals("agg1",agg.getName());
		assertEquals("min",agg.getFunction());
		assertEquals("x",agg.getVariable());
		Condition c = agg.getCondition();
		assertEquals("score",c.getPredicate());
		assertEquals(2,c.getTerms().size());
		assertEquals("client",c.getTerms().get(0).toString());
		assertEquals("x",c.getTerms().get(1).toString());
	}
	public void testAggregation5() throws Exception {
		String input = 
			"aggregation agg1 = max x score[client,x]\n";
		Script script = parse(input);
		assertEquals(1,script.getElements().size());
		assertTrue(script.getElements().get(0) instanceof Aggregation);
		Aggregation agg = (Aggregation)script.getElements().get(0);
		assertEquals("agg1",agg.getName());
		assertEquals("max",agg.getFunction());
		assertEquals("x",agg.getVariable());
		Condition c = agg.getCondition();
		assertEquals("score",c.getPredicate());
		assertEquals(2,c.getTerms().size());
		assertEquals("client",c.getTerms().get(0).toString());
		assertEquals("x",c.getTerms().get(1).toString());
	}
	
	
	
	public void testFactStore1() throws Exception {
		String input = 
			"external fs1: father[com.example.Person]\n";
		Script script = parse(input);
		assertEquals(1,script.getElements().size());
		assertTrue(script.getElements().get(0) instanceof FactStore);
		FactStore fs = (FactStore)script.getElements().get(0);
		assertEquals("fs1",fs.getId());
		assertEquals("father",fs.getPredicate());
		assertEquals(1,fs.getTypeNames().size());
		assertEquals("com.example.Person",fs.getTypeNames().get(0));
	}
	public void testFactStore2() throws Exception {
		String input = 
			"external fs2: father[com.example.Person,int]\n";
		Script script = parse(input);
		assertEquals(1,script.getElements().size());
		assertTrue(script.getElements().get(0) instanceof FactStore);
		FactStore fs = (FactStore)script.getElements().get(0);
		assertEquals("fs2",fs.getId());
		assertEquals("father",fs.getPredicate());
		assertEquals(2,fs.getTypeNames().size());
		assertEquals("com.example.Person",fs.getTypeNames().get(0));
		assertEquals("int",fs.getTypeNames().get(1));
	}
	public void testObjectRefs1() throws Exception {
		String input = 
			"ref int x\n"+
			"ref java.lang.String aVar\n";
		Script script = parse(input);
		assertEquals(2,script.getElements().size());
		assertTrue(script.getElements().get(0) instanceof RefDeclaration);
		assertTrue(script.getElements().get(1) instanceof RefDeclaration);
		assertEquals("x",this.getRefDecAt(script,0).getNames().get(0));
		assertEquals("int",this.getRefDecAt(script,0).getType());
		assertEquals("aVar",this.getRefDecAt(script,1).getNames().get(0));
		assertEquals("java.lang.String",this.getRefDecAt(script,1).getType());
		
	}
	public void testObjectRefs2() throws Exception {
		String input = 
			"ref int x,y\n"+
			"ref java.lang.String a,b,c\n";
		Script script = parse(input);
		assertEquals(2,script.getElements().size());
		assertTrue(script.getElements().get(0) instanceof RefDeclaration);
		assertTrue(script.getElements().get(1) instanceof RefDeclaration);
		assertEquals("x",this.getRefDecAt(script,0).getNames().get(0));
		assertEquals("y",this.getRefDecAt(script,0).getNames().get(1));
		assertEquals("int",this.getRefDecAt(script,0).getType());
		assertEquals("a",this.getRefDecAt(script,1).getNames().get(0));
		assertEquals("b",this.getRefDecAt(script,1).getNames().get(1));
		assertEquals("java.lang.String",this.getRefDecAt(script,1).getType());
		
	}
	
	
	public void testRules1() throws Exception {
		String input = 
			"rule1: if p[x,y] then q[y,x]\n";
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
	public void testRules2() throws Exception {
		String input = "rule2: if equals[x,y] then q[y,x]\n";
		Script script = parse(input);
		Rule r = this.getRuleAt(script,0);
		Condition prereq1= r.getConditions().get(0);
		Condition concl = r.getConditions().get(1);
		assertEquals("rule2",r.getId());
		assertEquals("equals",prereq1.getPredicate());
		assertEquals("q",concl.getPredicate());
		assertEquals(new VariableTerm("x"),prereq1.getTerms().get(0));		
	}
	public void testRules3() throws Exception {
		String input = 
			"rule3: if p1[x,y] and p2[x,y] then p3[y,x]\n";
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
	public void testFacts1() throws Exception {
		String input = 
			"fact1: p[x,y]\n";
		Script script = parse(input);
		Rule r = this.getRuleAt(script,0);
		Condition f = r.getConditions().get(0);
		assertEquals("fact1",r.getId());
		assertEquals("p",f.getPredicate());
	}
	// string literals
	public void testStringLiterals1() throws Exception {
		String input = 
			"fact1: p[\"Max\",y]\n";
		Script script = parse(input);
		Rule r = this.getRuleAt(script,0);
		Condition f = r.getConditions().get(0);
		Term t1 = f.getTerms().get(0);
		Term t2 = f.getTerms().get(1);
		assertTrue(t1 instanceof ConstantTerm);
		assertEquals("Max",((ConstantTerm)t1).getValue());
		assertTrue(t2 instanceof VariableTerm);
	}
	// int literals
	public void testIntLiterals1() throws Exception {
		String input = 
			"fact1: p[42,y]\n";
		Script script = parse(input);
		Rule r = this.getRuleAt(script,0);
		Condition f = r.getConditions().get(0);
		Term t1 = f.getTerms().get(0);
		Term t2 = f.getTerms().get(1);
		assertTrue(t1 instanceof ConstantTerm);
		assertEquals("42",((ConstantTerm)t1).getValue());
		assertEquals(Integer.class.getName(),((ConstantTerm)t1).getType());
		assertTrue(t2 instanceof VariableTerm);
	}
	public void testIntLiterals2() throws Exception {
		String input = 
			"fact1: p[-42,y]\n";
		Script script = parse(input);
		Rule r = this.getRuleAt(script,0);
		Condition f = r.getConditions().get(0);
		Term t1 = f.getTerms().get(0);
		Term t2 = f.getTerms().get(1);
		assertTrue(t1 instanceof ConstantTerm);
		assertEquals("-42",((ConstantTerm)t1).getValue());
		assertEquals(Integer.class.getName(),((ConstantTerm)t1).getType());
		assertTrue(t2 instanceof VariableTerm);
	}
	// decimal literals
	public void testDoubleLiterals1() throws Exception {
		String input = 
			"fact1: p[42.42,y]\n";
		Script script = parse(input);
		Rule r = this.getRuleAt(script,0);
		Condition f = r.getConditions().get(0);
		Term t1 = f.getTerms().get(0);
		Term t2 = f.getTerms().get(1);
		assertTrue(t1 instanceof ConstantTerm);
		assertEquals("42.42",((ConstantTerm)t1).getValue());
		assertEquals(Double.class.getName(),((ConstantTerm)t1).getType());
		assertTrue(t2 instanceof VariableTerm);
	}
	public void testDoubleLiterals2() throws Exception {
		String input = 
			"fact1: p[-42.42,y]\n";
		Script script = parse(input);
		Rule r = this.getRuleAt(script,0);
		Condition f = r.getConditions().get(0);
		Term t1 = f.getTerms().get(0);
		Term t2 = f.getTerms().get(1);
		assertTrue(t1 instanceof ConstantTerm);
		assertEquals("-42.42",((ConstantTerm)t1).getValue());
		assertEquals(Double.class.getName(),((ConstantTerm)t1).getType());
		assertTrue(t2 instanceof VariableTerm);
	}
	// complex terms
	public void testComplexTerms1() throws Exception {
		String input = "fact: p[f(x),g(y,z)]\n";
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
	public void testComplexTerms2() throws Exception {
		String input = "fact: p[*(4,x),+(y,3)]\n";
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
		assertEquals("*",c1.getFunction());
		assertEquals(2,c1.getTerms().size());
		assertTrue(c1.getTerms().get(0) instanceof ConstantTerm);
		assertEquals("4",((ConstantTerm)c1.getTerms().get(0)).getValue());
		assertTrue(c1.getTerms().get(1) instanceof VariableTerm);
		assertEquals("x",((VariableTerm)c1.getTerms().get(1)).getName());
		
		assertEquals("+",c2.getFunction());
		assertEquals(2,c2.getTerms().size());
		assertTrue(c2.getTerms().get(0) instanceof VariableTerm);
		assertEquals("y",((VariableTerm)c2.getTerms().get(0)).getName());
		assertTrue(c2.getTerms().get(1) instanceof ConstantTerm);
		assertEquals("3",((ConstantTerm)c2.getTerms().get(1)).getValue());
		
	}
	// property terms
	public void testPropertyTerms1() throws Exception {
		String input = "fact: p[x.f.g]\n";
		Script script = parse(input);
		Condition f = this.getRuleAt(script,0).getConditions().get(0);
		assertEquals("p",f.getPredicate());
		Term t1 = f.getTerms().get(0);
		assertTrue(t1 instanceof VariableTerm);
		VariableTerm v = (VariableTerm)t1;		
		List<String> names = v.getNames();
		assertEquals("x",names.get(0));
		assertEquals("f",names.get(1));
		assertEquals("g",names.get(2));
		
	}
	public void testQueryIOSignature1() throws Exception {
		String input = 
			"query p[in,out]\n";
		Script script = parse(input);
		assertEquals(1,script.getElements().size());
		QuerySpec q = this.getQueryAt(script,0);
		assertEquals("p",q.getPredicate());
		assertEquals(2,q.getIoSpec().size());
		assertTrue(q.getIoSpec().get(0));
		assertFalse(q.getIoSpec().get(1));
	}
	public void testIntegration1() throws Exception {
		String input = 
			"// variables\n"+
			"var int x,y\n"+
			"var java.lang.String a,b,c\n"+
			"//queries\n"+
			"query p[in,out]\n"+
			"//rules and facts\n"+
			"fact1: p[\"Max\",y]\n";
		Script script = parse(input);
		assertEquals(7,script.getElements().size());
		Comment c1 = this.getCommentAt(script,0);
		Comment c2 = this.getCommentAt(script,3);
		Comment c3 = this.getCommentAt(script,5);
		assertEquals("variables",c1.getText().trim());
		assertEquals("queries",c2.getText().trim());
		assertEquals("rules and facts",c3.getText().trim());
	}
	public void testAnnotations1() throws Exception {
		String input = 
			"@@dc:Creator=Jens\n"+
			"@@dc:Date=01/01/07\n"+
			"var int x,y\n"+
			"var java.lang.String a,b,c\n"+
			"//queries\n"+
			"@description=a query\n"+
			"query p[in,out]\n";
		
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
	public void testLineNumbers1() throws Exception {
		String input = 
			"@@dc:Creator=Jens\n"+
			"@@dc:Date=01/01/07\n"+
			"var int x,y\n"+
			"var java.lang.String a,b,c\n"+
			"//queries\n"+
			"@description=a query\n"+
			"query p[in,out]\n"+
			"rule3: if p1[x,y] and p2[x,y] then p3[y,x]\n";
		
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
	public void testNegation1() throws Exception {
		String input = "rule1: if not p[x,y] then q[y,x]\n";
		Script script = parse(input);
		assertEquals(1,script.getElements().size());
		Rule r = this.getRuleAt(script,0);
		Condition prereq1= r.getConditions().get(0);
		Condition concl = r.getConditions().get(1);
		assertEquals("rule1",r.getId());
		assertEquals("p",prereq1.getPredicate());
		assertEquals(true,prereq1.isNegated());
		assertEquals("q",concl.getPredicate());
		assertEquals(false,concl.isNegated());
	}
	public void testNegation2() throws Exception {
		String input = "rule1: if p[x,y] then not q[y,x]\n";
		Script script = parse(input);
		assertEquals(1,script.getElements().size());
		Rule r = this.getRuleAt(script,0);
		Condition prereq1= r.getConditions().get(0);
		Condition concl = r.getConditions().get(1);
		assertEquals("rule1",r.getId());
		assertEquals("p",prereq1.getPredicate());
		assertEquals(false,prereq1.isNegated());
		assertEquals("q",concl.getPredicate());
		assertEquals(true,concl.isNegated());
	}
	public void testNegation3() throws Exception {
		String input = "query not p[in,in]\n"+"query q[in,in]";
		Script script = parse(input);
		assertEquals(2,script.getElements().size());
		QuerySpec q1 = this.getQueryAt(script,0);
		assertEquals("p",q1.getPredicate());
		assertEquals(true,q1.isNegated());
		QuerySpec q2 = this.getQueryAt(script,1);
		assertEquals("q",q2.getPredicate());
		assertEquals(false,q2.isNegated());
	}
	public void testComparison1() throws Exception {
		String input = "rule1: 1<2";
		Script script = parse(input);
		assertEquals(1,script.getElements().size());
		Rule rule = this.getRuleAt(script,0);
		Condition fact = rule.getConditions().get(0);
		assertTrue(fact.isPrimitiveComparison());
		assertTrue(fact.getPredicate().equals("<"));
	}
	public void testComparison2() throws Exception {
		String input = "rule1: 1<=2";
		Script script = parse(input);
		assertEquals(1,script.getElements().size());
		Rule rule = this.getRuleAt(script,0);
		Condition fact = rule.getConditions().get(0);
		assertTrue(fact.isPrimitiveComparison());
		assertTrue(fact.getPredicate().equals("<="));
	}
	public void testComparison3() throws Exception {
		String input = "rule1: 2<=funct(3)";
		Script script = parse(input);
		assertEquals(1,script.getElements().size());
		Rule rule = this.getRuleAt(script,0);
		Condition fact = rule.getConditions().get(0);
		assertTrue(fact.isPrimitiveComparison());
		assertTrue(fact.getPredicate().equals("<="));
	}
	public void testComparison4() throws Exception {
		String input = "rule1: funct(2)<42";
		Script script = parse(input);
		assertEquals(1,script.getElements().size());
		Rule rule = this.getRuleAt(script,0);
		Condition fact = rule.getConditions().get(0);
		assertTrue(fact.isPrimitiveComparison());
		assertTrue(fact.getPredicate().equals("<"));
	}
	public void testComment1() throws Exception {
		String input = "// see also http://www.google.com";
		Script script = parse(input);
		assertEquals(1,script.getElements().size());
		Comment c = getCommentAt(script,0);
		assertEquals("see also http://www.google.com",c.getText().trim());
	}
	public void testComment2() throws Exception {
		// test key words and escaped double quotes
		String input = "// \"if\" and \"max\" should also work";
		Script script = parse(input);
		assertEquals(1,script.getElements().size());
		Comment c = getCommentAt(script,0);
		assertEquals("\"if\" and \"max\" should also work",c.getText().trim());
	}

}