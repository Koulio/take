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

import java.io.StringReader;
import java.util.List;
import nz.org.take.Constant;
import nz.org.take.DerivationRule;
import nz.org.take.Fact;
import nz.org.take.FactPrerequisite;
import nz.org.take.KnowledgeBase;
import nz.org.take.Predicate;
import nz.org.take.Prerequisite;
import nz.org.take.Term;
import nz.org.take.script.Parser;
import junit.framework.TestCase;

/**
 * Tests for the script parser.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class ParserTestsTmp extends TestCase {
	
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
		assertEquals(Long.valueOf(42),((Constant)t).getObject());
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
	
}
