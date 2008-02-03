/**
 * Copyright 2007 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */

package test.nz.org.take.compiler.scenario11;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import nz.org.take.deployment.KnowledgeBaseManager;
import nz.org.take.nscript.ScriptKnowledgeSource;
import test.nz.org.take.TakeTestCase;
import test.nz.org.take.compiler.scenario11.generated.HasCategory;
import test.nz.org.take.compiler.scenario11.generated.KB;

/**
 * Tests for this scenario. 
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class Tests extends TakeTestCase
{

	static String SCRIPT_URL = "/test/nz/org/take/compiler/scenario11/rules11.take";
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
		kb = kbm.getKnowledgeBase(
				KB.class, 
				new ScriptKnowledgeSource(Tests.class.getResourceAsStream("/test/nz/org/take/compiler/scenario11/rules11.take")),
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
		Bean bean = new Bean();
		bean.setRanking(42);
		Iterator<HasCategory> results = kb.hasCategory(bean);
		assertTrue(results.hasNext());
		HasCategory r = results.next();
		assertEquals("high",r.name);
		assertFalse(!results.hasNext());
	}
	/**
	 * Test 2.
	 */
	public void test2(){
		Bean bean = new Bean();
		bean.setRanking(3);
		Iterator<HasCategory> results = kb.hasCategory(bean);
		assertTrue(results.hasNext());
		HasCategory r = results.next();
		assertEquals("normal",r.name);
		assertFalse(results.hasNext());
	}

}
	