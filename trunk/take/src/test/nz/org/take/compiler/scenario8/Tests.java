/**
 * Copyright 2007 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */

package test.nz.org.take.compiler.scenario8;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import junit.framework.TestCase;
import nz.org.take.deployment.KnowledgeBaseManager;
import nz.org.take.rt.ResultSet;
import nz.org.take.nscript.ScriptKnowledgeSource;
import test.nz.org.take.TakeTestCase;
import test.nz.org.take.compiler.scenario8.generated.IsFatherOf;
import test.nz.org.take.compiler.scenario8.generated.IsGrandfatherOf;
import test.nz.org.take.compiler.scenario8.generated.KB;

/**
 * Tests for this scenario. 
 * Code pretty printing is used.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class Tests extends TakeTestCase
{

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
		Map<String,Object> factStores = new HashMap<String,Object>();
		factStores.put("facts1", new FactStore());
		kb = kbm.getKnowledgeBase(
				KB.class, 
				new ScriptKnowledgeSource(Tests.class.getResourceAsStream("/test/nz/org/take/compiler/scenario8/rules8.take")),
				new HashMap<String,Object>(),
				factStores
		); 
		
		// clear database and recreate a new db
		File dbFolder = new File("testdata/example8");
		for (File f:dbFolder.listFiles()) {
			if (f.getName().startsWith("example8db")) {
				f.delete();
				System.out.println("deleting old test db file " + f.getAbsolutePath());
			}
		}

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
		ResultSet<IsFatherOf> results = kb.getFather(new Person("Max"));	
		assertTrue(results.hasNext());
		IsFatherOf r = results.next();
		Person father = r.father;
		assertEquals(new Person("Jens"),father);
		assertFalse(results.hasNext());
		results.close();
		
	}
	/**
	 * Test 2.
	 */
	public void test2(){
		ResultSet<IsGrandfatherOf> results = kb.getGrandfather(new Person("Max"));	
		assertTrue(results.hasNext());
		IsGrandfatherOf r = results.next();
		Person father = r.grandfather;
		assertEquals(new Person("Klaus"),father);
		assertFalse(results.hasNext());
		results.close();
	}
	
	/**
	 * Test 3.
	 */
	public void test3(){
		ResultSet<IsFatherOf> results = kb.getSons(new Person("Jens"));	
		assertTrue(results.hasNext());
		IsFatherOf r = results.next();
		Person son = r.son;
		assertEquals(new Person("Max"),son);
		assertFalse(results.hasNext());
		results.close();
	}
	
	
}
	