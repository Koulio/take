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

package test.nz.org.take.compiler.scenario2;

import java.util.*;

import nz.org.take.Constant;
import nz.org.take.DerivationRule;
import nz.org.take.Fact;
import nz.org.take.KnowledgeBase;
import nz.org.take.KnowledgeElement;
import nz.org.take.rt.DerivationController;
import nz.org.take.rt.DerivationLogEntry;
import nz.org.take.rt.ResultSet;
import test.nz.org.take.compiler.scenario2.generated.*;
import junit.framework.TestCase;

/**
 * Tests for this scenario. 
 * For now, the classes have to generated and compiled manually.
 * For generation, use the script GenerateClasses.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class Tests extends TestCase
{
	private KnowledgeBase kb = null;
	/**
	 * Construct new test instance	 *
	 * @param name the test name
	 */
	public Tests(String name)
	{
		super(name);
	}

	/**
	 * Perform pre-test initialization
	 *
	 * @throws Exception
	 *
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception
	{
		kb = GenerateKB.buildKB();
	}

	/**
	 * Perform post-test clean up
	 *
	 * @throws Exception
	 *
	 * @see TestCase#tearDown()
	 */
	protected void tearDown()
		throws Exception
	{
		super.tearDown();
		// Add additional tear down code here
	}

	public void test1(){
		
		System.out.println("starting test case 1");
		
		_KB kb = new _KB();
		nz.org.take.rt.ResultSet<AncestorRelationship> results = kb.isAncestor("r0","r");	
		assertTrue(results.hasNext());
		AncestorRelationship next = results.next();
		List<DerivationLogEntry> x = results.getDerivationLog();
		results.getDerivationController().printLog();
		assertEquals("Wrong number of rules",1,countRules(x));
		assertEquals("Wrong number of facts",1,countFacts(x));
		assertTrue("Expected fact not found",checkFatherFact(x,"r0","r"));
	}
	
	public void test2(){
		
		System.out.println("starting test case 2");
		
		_KB kb = new _KB();
		ResultSet<AncestorRelationship> results = kb.isAncestor("r1","r");	
		assertTrue(results.hasNext());
		AncestorRelationship next = results.next();
		List<DerivationLogEntry> x = results.getDerivationLog();
		results.getDerivationController().printLog();
		assertEquals("Wrong number of rules",1,countRules(x));
		assertEquals("Wrong number of facts",1,countFacts(x));
		assertTrue("Expected fact not found",checkFatherFact(x,"r1","r"));
	}
	
	public void test3(){
		
		System.out.println("starting test case 3");
		
		_KB kb = new _KB();
		ResultSet<AncestorRelationship> results = kb.isAncestor("r00","r");	
		assertTrue(results.hasNext());
		AncestorRelationship next = results.next();
		List<DerivationLogEntry> x = results.getDerivationLog();
		results.getDerivationController().printLog();
		assertEquals("Wrong number of rules",2,countRules(x));
		assertEquals("Wrong number of facts",2,countFacts(x));
		assertTrue("Expected fact not found",checkFatherFact(x,"r0","r"));
		assertTrue("Expected fact not found",checkFatherFact(x,"r00","r0"));
	}
	
	public void test4(){
		
		System.out.println("starting test case 4");
		
		_KB kb = new _KB();
		ResultSet<AncestorRelationship> results = kb.isAncestor("r11","r");	
		assertTrue(results.hasNext());
		AncestorRelationship next = results.next();
		List<DerivationLogEntry> x = results.getDerivationLog();
		results.getDerivationController().printLog();
		assertEquals("Wrong number of rules",2,countRules(x));
		assertEquals("Wrong number of facts",2,countFacts(x));
		assertTrue("Expected fact not found",checkFatherFact(x,"r1","r"));
		assertTrue("Expected fact not found",checkFatherFact(x,"r11","r1"));
	}
	
	public void test5(){
		
		System.out.println("starting test case 5");
		
		_KB kb = new _KB();
		ResultSet<AncestorRelationship> results = kb.isAncestor("r000","r");	
		assertTrue(results.hasNext());
		AncestorRelationship next = results.next();
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
			KnowledgeElement e = kb.getElement(l.getName());
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
	