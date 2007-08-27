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

package test.nz.org.take.compiler.scenario6;

import java.util.Iterator;

import javax.script.Bindings;
import javax.script.SimpleBindings;

import test.nz.org.take.TakeTestCase;
import test.nz.org.take.compiler.scenario6.generated.*;
import nz.org.take.deployment.KnowledgeBaseManager;
import nz.org.take.rt.DerivationController;
import nz.org.take.rt.ResultSet;
import nz.org.take.script.ScriptKnowledgeSource;
import junit.framework.TestCase;

/**
 * Tests for this scenario. 
 * For now, the classes have to generated and compiled manually.
 * For generation, use the script GenerateClasses.
 * Code pretty printing is used.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class Tests extends TakeTestCase
{

	static String SCRIPT_URL = "/test/nz/org/take/compiler/scenario6/rules6.take";
	private KB kb= null;
	
	/**
	 * Construct new test instance
	 *
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
		KnowledgeBaseManager<KB> kbm = new KnowledgeBaseManager<KB>();
		Bindings bindings = new SimpleBindings();
		bindings.put("Frank",new Person("Frank"));
		bindings.put("Guenther",new Person("Guenther"));
		bindings.put("Jens",new Person("Jens"));
		bindings.put("Klaus",new Person("Klaus"));
		bindings.put("Lutz",new Person("Lutz"));
		bindings.put("Max",new Person("Max"));
		bindings.put("Otto",new Person("Otto"));
		bindings.put("Ralf",new Person("Ralf"));
		bindings.put("Werner",new Person("Werner"));
		kb = kbm.getKnowledgeBase(
				KB.class, 
				new ScriptKnowledgeSource(Tests.class.getResourceAsStream("/test/nz/org/take/compiler/scenario6/rules6.take")),
				bindings
				); 

	}

	/**
	 * Perform post-test clean up
	 *
	 * @throws Exception
	 *
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception
	{
		super.tearDown();
		// Add additional tear down code here
	}

	/**
	 * Test 1.
	 */
	public void test1(){
		Iterator<IsFatherOf> results = kb.getFather(new Person("Max"));	
		assertTrue(results.hasNext());
		IsFatherOf r = results.next();
		Person father = r.father;
		assertEquals(new Person("Jens"),father);
		assertFalse(results.hasNext());
	}
	/**
	 * Test 2.
	 */
	public void test2(){
		ResultSet<IsGrandfatherOf> results = kb.getGrandfather(new Person("Max"));	
		assertTrue(results.hasNext());
		IsGrandfatherOf r = results.next();
		DerivationController x = results.getDerivationController();
		x.printLog();
		Person grandfather = r.grandfather;
		assertEquals(new Person("Klaus"),grandfather);
		assertFalse(results.hasNext());
	}
	

	/**
	 * Test 3.
	 */
	public void test3(){
		Iterator<IsFatherOf> results = kb.getSons(new Person("Jens"));	
		assertTrue(results.hasNext());
		IsFatherOf r = results.next();
		Person son = r.son;
		assertEquals(new Person("Max"),son);
		assertFalse(results.hasNext());
	}
	

	/**
	 * Test 4.
	 */
	public void test4(){
		Iterator<IsFatherOf> results = kb.getSons(new Person("Otto"));	
		assertTrue(results.hasNext());
		assertEquals(new Person("Guenther"),results.next().son);
		assertEquals(new Person("Lutz"),results.next().son);
		assertEquals(new Person("Klaus"),results.next().son);
		assertEquals(new Person("Werner"),results.next().son);
		assertFalse(results.hasNext());
	}
	
	/**
	 * Test 4.
	 */
	public void test5(){
		Iterator<IsGrandfatherOf> results = kb.getGrandchildren(new Person("Otto"));	
		assertTrue(results.hasNext());
		while(results.hasNext()) {
			System.out.println(results.next().grandson);
		}
	}

}
	