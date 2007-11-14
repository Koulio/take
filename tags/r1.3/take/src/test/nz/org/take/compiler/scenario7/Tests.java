/**
 * Copyright 2007 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */

package test.nz.org.take.compiler.scenario7;

import java.util.Iterator;

import test.nz.org.take.TakeTestCase;
import test.nz.org.take.compiler.scenario7.generated.KB;
import test.nz.org.take.compiler.scenario7.generated.highincome;
import nz.org.take.deployment.KnowledgeBaseManager;
import nz.org.take.script.ScriptKnowledgeSource;
import junit.framework.TestCase;

/**
 * Tests for this scenario. 
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
		kb = kbm.getKnowledgeBase(
				KB.class, 
				new ScriptKnowledgeSource(Tests.class.getResourceAsStream("/test/nz/org/take/compiler/scenario7/rules7.take"))
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
		Person notMe = new Person("Not Me");
		notMe.setSalary(200000);
		Iterator<highincome> results = kb.highincome_1(notMe);
		assertTrue(results.hasNext());
	
	}
	
}
	