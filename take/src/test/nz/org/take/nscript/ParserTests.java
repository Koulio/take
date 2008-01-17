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

import java.io.StringReader;
import java.util.List;
import nz.org.take.Constant;
import nz.org.take.DerivationRule;
import nz.org.take.Fact;
import nz.org.take.KnowledgeBase;
import nz.org.take.Predicate;
import nz.org.take.Prerequisite;
import nz.org.take.Term;
import nz.org.take.Variable;
import nz.org.take.nscript.Parser;
import junit.framework.TestCase;

/**
 * Tests for the script parser.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class ParserTests extends TestCase {
	
	public void test1() throws Exception {
		String script = "rule1: if cond1['aaa',42,55.3] then cond2[42,'bbb']";
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
		p = prereq.getPredicate();
		assertEquals("cond1",p.getName());
		assertTrue(java.util.Arrays.equals(p.getSlotTypes(),new Class[]{String.class,Integer.class,Float.class}));
	}
	
	public void test2() throws Exception {
		String script = "var java.util.Date v1,v2 \n" +
			"ref java.util.Calendar c1,c2 \n" +
			"rule1: if cond1[c1,v1] then cond2[c2,v2]";
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
		Fact prereq = r.getBody().get(0);
		t = prereq.getTerms()[0];
		assertTrue(t instanceof Constant);
		assertEquals(java.util.Calendar.class,t.getType());
		assertEquals("c1",((Constant)t).getRef());
		t = prereq.getTerms()[1];
		assertTrue(t instanceof Variable);
		assertEquals(java.util.Date.class,t.getType());
		assertEquals("v1",((Variable)t).getName());
	}
	
	public void test3() throws Exception {
		String script =  "// test3\nrule1: if not cond1['a'] and cond2['b'] and not cond3['c'] then cond4['d']";
		KnowledgeBase kb = new Parser().parse(new StringReader(script));
		assertEquals(1,kb.getElements().size());
		DerivationRule r = (DerivationRule)kb.getElement("rule1");
		List<Prerequisite> body = r.getBody();
		assertEquals(3,body.size());
		assertTrue(body.get(0).getPredicate().isNegated());
		assertFalse(body.get(1).getPredicate().isNegated());
		assertTrue(body.get(2).getPredicate().isNegated());
	}

}
