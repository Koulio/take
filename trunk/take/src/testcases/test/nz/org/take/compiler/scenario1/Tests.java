/**
 * Copyright 2007 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */

package test.nz.org.take.compiler.scenario1;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import test.nz.org.take.TakeTestCase;
import test.nz.org.take.compiler.scenario1.generated.*;
import nz.org.take.deployment.KnowledgeBaseManager;
import nz.org.take.rt.DerivationController;
import nz.org.take.rt.ResultSet;
import nz.org.take.script.ScriptKnowledgeSource;

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
	public Tests()
	{
		super();
	}

	@Before
	public void setUp() throws Exception
	{
		super.setUp();		
		KnowledgeBaseManager<KB> kbm = new KnowledgeBaseManager<KB>();
		kb = kbm.getKnowledgeBase(
				KB.class, 
				new ScriptKnowledgeSource(Tests.class.getResourceAsStream("/test/nz/org/take/compiler/scenario1/rules1.take"))
				); 
	}

	@After
	public void tearDown()
		throws Exception
	{
		super.tearDown();
		// Add additional tear down code here
	}

	@Test
	public void test1(){
		Iterator<IsFatherOf> results = kb.getFather("Max");	
		assertTrue(results.hasNext());
		IsFatherOf r = results.next();
		String father = r.father;
		assertEquals("Jens",father);
		assertFalse(results.hasNext());
	}
	@Test
	public void test2(){
		ResultSet<IsGrandfatherOf> results = kb.getGrandfather("Max");	
		assertTrue(results.hasNext());
		IsGrandfatherOf r = results.next();
		DerivationController x = results.getDerivationController();
		x.printLog();
		String grandfather = r.grandfather;
		assertEquals("Klaus",grandfather);
		assertFalse(results.hasNext());
	}
	

	@Test
	public void test3(){
		Iterator<IsFatherOf> results = kb.getSons("Jens");	
		assertTrue(results.hasNext());
		IsFatherOf r = results.next();
		String son = r.son;
		assertEquals("Max",son);
		assertFalse(results.hasNext());
	}
	

	@Test
	public void test4(){
		Iterator<IsFatherOf> results = kb.getSons("Otto");	
		while (results.hasNext()) {
			IsFatherOf r = results.next();
			System.out.println(r.father + " " + r.son);
		}
		/*
		assertTrue(results.hasNext());
		assertEquals("Guenther",results.next().son);
		assertEquals("Lutz",results.next().son);
		assertEquals("Klaus",results.next().son);
		assertEquals("Werner",results.next().son);
		assertFalse(results.hasNext());
		*/
	}
	
	@Test
	public void test5(){
		Iterator<IsGrandfatherOf> results = kb.getGrandchildren("Otto");	
		assertTrue(results.hasNext());
		while(results.hasNext()) {
			System.out.println(results.next().grandson);
		}
	}
	
	
}
	