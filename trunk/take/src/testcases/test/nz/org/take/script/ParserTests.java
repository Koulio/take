/**
 * Copyright 2008 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */

package test.nz.org.take.script;

import static org.junit.Assert.*;

import java.io.StringReader;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import nz.org.take.Constant;
import nz.org.take.DerivationRule;
import nz.org.take.ExpressionPrerequisite;
import nz.org.take.ExternalFactStore;
import nz.org.take.Fact;
import nz.org.take.FactPrerequisite;
import nz.org.take.KnowledgeBase;
import nz.org.take.Predicate;
import nz.org.take.Prerequisite;
import nz.org.take.Query;
import nz.org.take.Term;
import nz.org.take.Variable;
import nz.org.take.mvel2.MVEL2ExpressionLanguage;
import nz.org.take.script.Parser;


/**
 * Tests for the script parser.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class ParserTests {
	@Test
	public void testFacts1() throws Exception {
		String script = "fact1: cond1|42,\"aaa\"|";
		KnowledgeBase kb = new Parser().parse(new StringReader(script));
		assertEquals(1,kb.getElements().size());
		Fact r = (Fact)kb.getElement("fact1");
		assertNotNull(r);
		Predicate p = r.getPredicate();
		assertTrue(java.util.Arrays.equals(p.getSlotTypes(),new Class[]{Integer.class,String.class}));
		assertEquals("cond1",p.getName());
		Term t = r.getTerms()[0];
		assertTrue(t instanceof Constant);
		assertEquals(Integer.valueOf(42),((Constant)t).getObject());
		t = r.getTerms()[1];
		assertTrue(t instanceof Constant);
		assertEquals("aaa",((Constant)t).getObject());
		
	}
	// test syntax of predicate names
	@Test
	public void testFacts2() throws Exception {
		String script = "fact1: cond_test1|42,\"aaa\"|";
		KnowledgeBase kb = new Parser().parse(new StringReader(script));
		assertEquals(1,kb.getElements().size());
		Fact r = (Fact)kb.getElement("fact1");
		assertNotNull(r);
		Predicate p = r.getPredicate();
		assertTrue(java.util.Arrays.equals(p.getSlotTypes(),new Class[]{Integer.class,String.class}));
		assertEquals("cond_test1",p.getName());
		Term t = r.getTerms()[0];
		assertTrue(t instanceof Constant);
		assertEquals(Integer.valueOf(42),((Constant)t).getObject());
		t = r.getTerms()[1];
		assertTrue(t instanceof Constant);
		assertEquals("aaa",((Constant)t).getObject());
		
	}
	@Test
	public void testRules1() throws Exception {
		String script = "rule1: if cond1|42,\"aaa\"| then cond2|42,\"bbb\"|";
		KnowledgeBase kb = new Parser().parse(new StringReader(script));
		assertEquals(1,kb.getElements().size());
		DerivationRule r = (DerivationRule)kb.getElement("rule1");
		assertNotNull(r);
		Fact head = r.getHead();
		Predicate p = head.getPredicate();
		assertTrue(java.util.Arrays.equals(p.getSlotTypes(),new Class[]{Integer.class,String.class}));
		assertEquals("cond2",p.getName());
		Term t = head.getTerms()[0];
		assertTrue(t instanceof Constant);
		assertEquals(Integer.valueOf(42),((Constant)t).getObject());
		t = head.getTerms()[1];
		assertTrue(t instanceof Constant);
		assertEquals("bbb",((Constant)t).getObject());
		List<Prerequisite> body = r.getBody();
		assertEquals(1,body.size());
		Prerequisite prereq = body.get(0);
		assertTrue (prereq instanceof FactPrerequisite);
		FactPrerequisite bFact = (FactPrerequisite)prereq;
		p = bFact.getPredicate();
		assertEquals("cond1",p.getName());
		assertTrue(java.util.Arrays.equals(p.getSlotTypes(),new Class[]{Integer.class,String.class}));
	}
	@Test
	public void testRules2() throws Exception {
		String script = "var java.util.Date v1,v2 \n" +
			"ref java.util.Calendar c1,c2 \n" +
			"rule1: if cond1|c1,v1| then cond2|c2,v2|";
		KnowledgeBase kb = new Parser().parse(new StringReader(script));
		assertEquals(1,kb.getElements().size());
		DerivationRule r = (DerivationRule)kb.getElement("rule1");
		assertNotNull(r);
		Fact head = r.getHead();
		Predicate p = head.getPredicate();
		assertTrue(java.util.Arrays.equals(p.getSlotTypes(),new Class[]{java.util.Calendar.class,java.util.Date.class}));
		Term t = head.getTerms()[0];
		assertTrue(t instanceof Constant);
		assertEquals(java.util.Calendar.class,t.getType());
		assertEquals("c2",((Constant)t).getRef());
		t = head.getTerms()[1];
		assertTrue(t instanceof Variable);
		assertEquals(java.util.Date.class,t.getType());
		assertEquals("v2",((Variable)t).getName());
		assertTrue(r.getBody().get(0) instanceof FactPrerequisite);
		FactPrerequisite prereq = (FactPrerequisite)r.getBody().get(0);
		t = prereq.getTerms()[0];
		assertTrue(t instanceof Constant);
		assertEquals(java.util.Calendar.class,t.getType());
		assertEquals("c1",((Constant)t).getRef());
		t = prereq.getTerms()[1];
		assertTrue(t instanceof Variable);
		assertEquals(java.util.Date.class,t.getType());
		assertEquals("v1",((Variable)t).getName());
	}
	@Test
	public void testRules3() throws Exception {
		String script =  "// test3\nrule1: if not cond1|\"a\"| and cond2|\"b\"|then cond4|\"d\"|";
		KnowledgeBase kb = new Parser().parse(new StringReader(script));
		assertEquals(1,kb.getElements().size());
		DerivationRule r = (DerivationRule)kb.getElement("rule1");
		List<Prerequisite> body = r.getBody();
		assertEquals(2,body.size());
		assertTrue(((FactPrerequisite)body.get(0)).getPredicate().isNegated());
		assertFalse(((FactPrerequisite)body.get(1)).getPredicate().isNegated());

	}
	
	@Test
	public void testRules4() throws Exception {
		String script = "var java.util.Date d \n" +
			"ref java.util.Calendar c \n" +
			"rule1: if 'c.getTime()==d' then sameTime|c,d|";
		KnowledgeBase kb = new Parser().parse(new StringReader(script));
		assertEquals(1,kb.getElements().size());
		DerivationRule r = (DerivationRule)kb.getElement("rule1");
		assertNotNull(r);
		Fact head = r.getHead();
		Predicate p = head.getPredicate();
		assertTrue(java.util.Arrays.equals(p.getSlotTypes(),new Class[]{java.util.Calendar.class,java.util.Date.class}));
		Term t = head.getTerms()[0];
		assertTrue(t instanceof Constant);
		assertEquals(java.util.Calendar.class,t.getType());
		assertEquals("c",((Constant)t).getRef());
		t = head.getTerms()[1];
		assertTrue(t instanceof Variable);
		assertEquals(java.util.Date.class,t.getType());
		assertEquals("d",((Variable)t).getName());
		assertTrue(r.getBody().get(0) instanceof ExpressionPrerequisite);
		ExpressionPrerequisite prereq = (ExpressionPrerequisite)r.getBody().get(0);
		assertEquals("c.getTime()==d",prereq.getExpression());
	}
	
	
	@Test
	public void testRules5() throws Exception {
		String script = 
			"external factstore1: cond|java.util.Date,long|";
		KnowledgeBase kb = new Parser().parse(new StringReader(script));
		assertEquals(1,kb.getElements().size());
		ExternalFactStore x = (ExternalFactStore)kb.getElement("factstore1");
		assertEquals("factstore1",x.getId());
		assertEquals("cond",x.getPredicate().getName());
		assertTrue(Arrays.equals(new Class[]{java.util.Date.class,long.class},x.getPredicate().getSlotTypes()));
	}

	@Test
	public void testRules6() throws Exception {
		String script = "var java.util.Date v1,v2 \n" +
			"ref java.util.Calendar c1,c2 \n" +
			"rule1: if cond1|c1,v1| and 'c1==c2' then cond2|c2,v2|";
		KnowledgeBase kb = new Parser().parse(new StringReader(script));
		assertEquals(1,kb.getElements().size());
		DerivationRule r = (DerivationRule)kb.getElement("rule1");
		assertNotNull(r);
		Fact head = r.getHead();
		Predicate p = head.getPredicate();
		assertTrue(java.util.Arrays.equals(p.getSlotTypes(),new Class[]{java.util.Calendar.class,java.util.Date.class}));
		Term t = head.getTerms()[0];
		assertTrue(t instanceof Constant);
		assertEquals(java.util.Calendar.class,t.getType());
		assertEquals("c2",((Constant)t).getRef());
		t = head.getTerms()[1];
		assertTrue(t instanceof Variable);
		assertEquals(java.util.Date.class,t.getType());
		assertEquals("v2",((Variable)t).getName());
		
		assertTrue(r.getBody().get(0) instanceof FactPrerequisite);
		FactPrerequisite prereq1 = (FactPrerequisite)r.getBody().get(0);
		t = prereq1.getTerms()[0];
		assertTrue(t instanceof Constant);
		assertEquals(java.util.Calendar.class,t.getType());
		assertEquals("c1",((Constant)t).getRef());
		t = prereq1.getTerms()[1];
		assertTrue(t instanceof Variable);
		assertEquals(java.util.Date.class,t.getType());
		assertEquals("v1",((Variable)t).getName());
		
		assertTrue(r.getBody().get(1) instanceof ExpressionPrerequisite);
		ExpressionPrerequisite prereq2 = (ExpressionPrerequisite)r.getBody().get(1);
		prereq2.getExpression().equals("c1==c2");
		assertTrue(prereq2.getLanguage() instanceof MVEL2ExpressionLanguage);
	}
	// same as 6, but with comments
	@Test
	public void testRules6a() throws Exception {
		String script = "// script for test scenario 1\n"+
			"var java.util.Date v1,v2 \n" +
			"ref java.util.Calendar c1,c2 \n" +
			"rule1: if cond1|c1,v1| and 'c1==c2' then cond2|c2,v2|";
		KnowledgeBase kb = new Parser().parse(new StringReader(script));

		assertEquals(1,kb.getElements().size());
		DerivationRule r = (DerivationRule)kb.getElement("rule1");
		assertNotNull(r);
		Fact head = r.getHead();
		Predicate p = head.getPredicate();
		assertTrue(java.util.Arrays.equals(p.getSlotTypes(),new Class[]{java.util.Calendar.class,java.util.Date.class}));
		Term t = head.getTerms()[0];
		assertTrue(t instanceof Constant);
		assertEquals(java.util.Calendar.class,t.getType());
		assertEquals("c2",((Constant)t).getRef());
		t = head.getTerms()[1];
		assertTrue(t instanceof Variable);
		assertEquals(java.util.Date.class,t.getType());
		assertEquals("v2",((Variable)t).getName());
		
		assertTrue(r.getBody().get(0) instanceof FactPrerequisite);
		FactPrerequisite prereq1 = (FactPrerequisite)r.getBody().get(0);
		t = prereq1.getTerms()[0];
		assertTrue(t instanceof Constant);
		assertEquals(java.util.Calendar.class,t.getType());
		assertEquals("c1",((Constant)t).getRef());
		t = prereq1.getTerms()[1];
		assertTrue(t instanceof Variable);
		assertEquals(java.util.Date.class,t.getType());
		assertEquals("v1",((Variable)t).getName());
		
		assertTrue(r.getBody().get(1) instanceof ExpressionPrerequisite);
		ExpressionPrerequisite prereq2 = (ExpressionPrerequisite)r.getBody().get(1);
		prereq2.getExpression().equals("c1==c2");
		assertTrue(prereq2.getLanguage() instanceof MVEL2ExpressionLanguage);
	}

	
	@Test
	public void testQueries1() throws Exception {
		String script = 
			"query cond|in,out| \n" +
			"fact1: cond|true,false|\n";
		KnowledgeBase kb = new Parser().parse(new StringReader(script));
		assertEquals(1,kb.getQueries().size());
		Query q = kb.getQueries().get(0);
		assertEquals("cond",q.getPredicate().getName());
		assertEquals(2,q.getPredicate().getSlotTypes().length);
		assertEquals(true,q.getInputParams()[0]);
		assertEquals(false,q.getInputParams()[1]);
	}
	
	@Test
	public void testQueries2() throws Exception {
		String script = "@@dc:creator=jens dietrich\n"+
			"@@dc:date=26/04/2007\n"+
			"var java.lang.String person1,person2,person3\n"+
			"var java.lang.String grandchild,father,grandfather\n"+
			"query cond2|in,in|\n"+
			"rule1: if cond1|person1,person2| then cond2|person1,person2|\n";
		KnowledgeBase kb = new Parser().parse(new StringReader(script));
		assertEquals(1,kb.getQueries().size());
		Query q = kb.getQueries().get(0);
		assertEquals("cond2",q.getPredicate().getName());
		assertEquals(2,q.getPredicate().getSlotTypes().length);
		assertEquals(true,q.getInputParams()[0]);
		assertEquals(true,q.getInputParams()[1]);
	}
	// multiple queries for same predicate
	@Test
	public void testQueries3() throws Exception {
		String script = "@@dc:creator=jens dietrich\n"+
			"@@dc:date=26/04/2007\n"+
			"var java.lang.String person1,person2,person3\n"+
			"var java.lang.String grandchild,father,grandfather\n"+
			"query cond_2|in,in|\n"+
			"query cond_2|out,in|\n"+
			"rule1: if cond1|person1,person2| then cond_2|person1,person2|\n";
		KnowledgeBase kb = new Parser().parse(new StringReader(script));
		assertEquals(1,kb.getQueries().size());
		Query q = kb.getQueries().get(0);
		assertEquals("cond_2",q.getPredicate().getName());
		assertEquals(2,q.getPredicate().getSlotTypes().length);
		assertEquals(true,q.getInputParams()[0]);
		assertEquals(true,q.getInputParams()[1]);
	}
	

	
}
