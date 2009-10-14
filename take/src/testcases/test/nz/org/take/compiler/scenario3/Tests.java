/**
 * Copyright 2007 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */

package test.nz.org.take.compiler.scenario3;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import nz.org.take.deployment.KnowledgeBaseManager;
import nz.org.take.rt.DerivationController;
import nz.org.take.rt.DerivationLogEntry;
import nz.org.take.rt.ResultSet;
import nz.org.take.script.ScriptKnowledgeSource;
import test.nz.org.take.TakeTestCase;
import test.nz.org.take.compiler.scenario3.generated.*;

/**
 * Tests for this scenario. 
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class Tests extends TakeTestCase
{
	private KB kb= null;
	
	/**
	 * Construct new test instance
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
				new ScriptKnowledgeSource(Tests.class.getResourceAsStream("/test/nz/org/take/compiler/scenario3/rules3.take"))
				); 
	}

	@After
	public void tearDown() throws Exception
	{
		super.tearDown();
	}
	@Test
	public void test1(){
		
		System.out.println("starting test case 1");
		Person p1 = new Person("Klaus");
		Person p2 = new Person("Lutz");
		ResultSet<IsBrotherRelationship> results = kb.isBrother(p1,p2);	
		assertTrue(results.hasNext());
		List<DerivationLogEntry> x = results.getDerivationLog();
		results.getDerivationController().printLog();
		assertEquals("Wrong number of rules",1,countRules(x));
	}
	@Test
	public void test2(){
		
		System.out.println("starting test case 2");
		
		Person p1 = new Person("Klaus");
		Person p2 = new Person("Lutz");
		ResultSet<IsBrotherRelationship2> results = kb.isBrother2(p1,p2);	
		assertTrue(results.hasNext());
		List<DerivationLogEntry> x = results.getDerivationLog();
		results.getDerivationController().printLog();
		assertEquals("Wrong number of rules",1,countRules(x));
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

}
	