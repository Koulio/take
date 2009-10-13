/**
 * Copyright 2007 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */

package test.nz.org.take.compiler.scenario2;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import nz.org.take.Constant;
import nz.org.take.Fact;
import nz.org.take.KnowledgeBase;
import nz.org.take.KnowledgeElement;
import nz.org.take.deployment.KnowledgeBaseManager;
import nz.org.take.rt.DerivationController;
import nz.org.take.rt.DerivationLogEntry;
import nz.org.take.rt.ResultSet;
import test.nz.org.take.TakeTestCase;
import test.nz.org.take.compiler.scenario2.generated.*;

/**
 * Tests for this scenario. 
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class Tests extends TakeTestCase
{
	private KB kb = null;
	private KnowledgeBase memoryKB = null;
	/**
	 * Construct new test instance.
	 */
	public Tests()
	{
		super();
	}

	@Before
	public void setUp() throws Exception
	{
		memoryKB = new GenerateKB().getKnowledgeBase();
		KnowledgeBaseManager<KB> kbm = new KnowledgeBaseManager<KB>();
		kb = kbm.getKnowledgeBase(KB.class,memoryKB); 
		
	}

	@After
	public void tearDown() throws Exception
	{
		super.tearDown();
	}
	@Test
	public void test1(){
		
		System.out.println("starting test case 1");
		nz.org.take.rt.ResultSet<AncestorRelationship> results = kb.isAncestor("r0","r");	
		assertTrue(results.hasNext());
		List<DerivationLogEntry> x = results.getDerivationLog();
		results.getDerivationController().printLog();
		assertEquals("Wrong number of rules",1,countRules(x));
		assertEquals("Wrong number of facts",1,countFacts(x));
		assertTrue("Expected fact not found",checkFatherFact(x,"r0","r"));
	}
	@Test
	public void test2(){
		
		System.out.println("starting test case 2");
		
		ResultSet<AncestorRelationship> results = kb.isAncestor("r1","r");	
		assertTrue(results.hasNext());
		List<DerivationLogEntry> x = results.getDerivationLog();
		results.getDerivationController().printLog();
		assertEquals("Wrong number of rules",1,countRules(x));
		assertEquals("Wrong number of facts",1,countFacts(x));
		assertTrue("Expected fact not found",checkFatherFact(x,"r1","r"));
	}
	@Test
	public void test3(){
		
		System.out.println("starting test case 3");
		
		ResultSet<AncestorRelationship> results = kb.isAncestor("r00","r");	
		assertTrue(results.hasNext());
		List<DerivationLogEntry> x = results.getDerivationLog();
		results.getDerivationController().printLog();
		assertEquals("Wrong number of rules",2,countRules(x));
		assertEquals("Wrong number of facts",2,countFacts(x));
		assertTrue("Expected fact not found",checkFatherFact(x,"r0","r"));
		assertTrue("Expected fact not found",checkFatherFact(x,"r00","r0"));
	}
	@Test
	public void test4(){
		
		System.out.println("starting test case 4");
		
		ResultSet<AncestorRelationship> results = kb.isAncestor("r11","r");	
		assertTrue(results.hasNext());
		List<DerivationLogEntry> x = results.getDerivationLog();
		results.getDerivationController().printLog();
		assertEquals("Wrong number of rules",2,countRules(x));
		assertEquals("Wrong number of facts",2,countFacts(x));
		assertTrue("Expected fact not found",checkFatherFact(x,"r1","r"));
		assertTrue("Expected fact not found",checkFatherFact(x,"r11","r1"));
	}
	@Test
	public void test5(){
		
		System.out.println("starting test case 5");
		
		ResultSet<AncestorRelationship> results = kb.isAncestor("r000","r");	
		assertTrue(results.hasNext());
		List<DerivationLogEntry> x = results.getDerivationLog();
		results.getDerivationController().printLog();
		assertEquals("Wrong number of rules",2,countRules(x));
		assertEquals("Wrong number of facts",3,countFacts(x));
		assertTrue("Expected fact not found",checkFatherFact(x,"r0","r"));
		assertTrue("Expected fact not found",checkFatherFact(x,"r00","r0"));
		assertTrue("Expected fact not found",checkFatherFact(x,"r000","r00"));
	}
	
	
	private boolean checkFatherFact(List<DerivationLogEntry> list,String name1,String name2) {
		for (DerivationLogEntry l:list) {
			KnowledgeElement e = memoryKB.getElement(l.getName());
			if (e instanceof Fact) {
				Fact f = (Fact)e;
				boolean ok = "isFather".equals(f.getPredicate().getName());
				ok = ok && f.getTerms().length==2;
				ok = ok && ((Constant)f.getTerms()[0]).getObject().equals(name1);
				ok = ok && ((Constant)f.getTerms()[1]).getObject().equals(name2);
				if (ok)
					return true;
			}
		}
		return false;
	}
	private int countRules(List<DerivationLogEntry> x) {
		Set<DerivationLogEntry> set = new HashSet<DerivationLogEntry>(); // remove duplicates
		set.addAll(x);
		int count = 0;
		for (DerivationLogEntry e:set) {
			if (e.getKind()==DerivationController.RULE)
				count = count+1;
		}
		return count;
	} 
	private int countFacts(List<DerivationLogEntry> x) {
		Set<DerivationLogEntry> set = new HashSet<DerivationLogEntry>(); // remove duplicates
		set.addAll(x);
		int count = 0;
		for (DerivationLogEntry e:set) {
			if (e.getKind()==DerivationController.FACT)
				count = count+1;
		}
		return count;
	} 

}
	