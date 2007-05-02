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

package test.nz.org.take.compiler.scenario3;

import java.util.*;

import nz.org.take.DerivationRule;
import nz.org.take.Fact;
import nz.org.take.KnowledgeBase;
import nz.org.take.rt.ResultSet;
import test.nz.org.take.compiler.scenario2.GenerateKB;
import test.nz.org.take.compiler.scenario3.generated.IsBrotherRelationship;
import test.nz.org.take.compiler.scenario3.generated.IsBrotherRelationship2;
import test.nz.org.take.compiler.scenario3.generated._KB;
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
	 * Construct new test instance
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
		super.setUp();
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
		Person p1 = new Person("Klaus");
		Person p2 = new Person("Lutz");
		ResultSet<IsBrotherRelationship> results = kb.isBrother(p1,p2);	
		assertTrue(results.hasNext());
		List<String> x = results.getDerivationLog();
		results.getDerivationController().printLog();
		assertEquals("Wrong number of rules",1,countRules(x));
	}
	public void test2(){
		
		System.out.println("starting test case 2");
		
		_KB kb = new _KB();
		Person p1 = new Person("Klaus");
		Person p2 = new Person("Lutz");
		ResultSet<IsBrotherRelationship2> results = kb.isBrother2(p1,p2);	
		assertTrue(results.hasNext());
		List<String> x = results.getDerivationLog();
		results.getDerivationController().printLog();
		assertEquals("Wrong number of rules",1,countRules(x));
	}
	private int countRules(List<String> x) {
		Set set = new HashSet(); // remove duplicates
		set.addAll(x);
		int count = 0;
		for (Object e:set) {
			String r = e.toString();
			if (isRule(r))
				count = count+1;
		}
		return count;
	} 
	private int countFacts(List<String> x) {
		Set set = new HashSet(); // remove duplicates
		set.addAll(x);
		int count = 0;
		for (Object e:set) {
			String r = e.toString();
			if (isFact(r))
				count = count+1;
		}
		return count;
	} 
	private boolean isRule(String id) {
		return kb.getElement(id) instanceof DerivationRule;
	}
	private boolean isFact(String id) {
		return kb.getElement(id) instanceof Fact;
	}
}
	