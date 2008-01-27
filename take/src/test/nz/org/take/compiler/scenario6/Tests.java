/**
 * Copyright 2007 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */

package test.nz.org.take.compiler.scenario6;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import junit.framework.TestCase;
import nz.org.take.deployment.KnowledgeBaseManager;
import nz.org.take.rt.DerivationController;
import nz.org.take.rt.ResultSet;
import nz.org.take.nscript.ScriptKnowledgeSource;
import test.nz.org.take.TakeTestCase;
import test.nz.org.take.compiler.scenario6.generated.IsFatherOf;
import test.nz.org.take.compiler.scenario6.generated.IsGrandfatherOf;
import test.nz.org.take.compiler.scenario6.generated.KB;

/**
 * Tests for this scenario. 
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
		Map<String,Object> bindings = new HashMap<String,Object>();
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
	