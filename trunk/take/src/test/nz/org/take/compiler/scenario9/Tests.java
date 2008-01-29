/**
 * Copyright 2007 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */

package test.nz.org.take.compiler.scenario9;

import java.util.HashMap;
import java.util.Map;
import junit.framework.TestCase;
import nz.org.take.deployment.KnowledgeBaseManager;
import nz.org.take.rt.ResultSet;
import nz.org.take.nscript.ScriptKnowledgeSource;
import test.nz.org.take.TakeTestCase;
import test.nz.org.take.compiler.scenario9.generated.KB;
import test.nz.org.take.compiler.scenario9.generated.LoanAssessment;

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
		kb = kbm.getKnowledgeBase(
				KB.class, 
				new ScriptKnowledgeSource(Tests.class.getResourceAsStream("/test/nz/org/take/compiler/scenario9/rules9.take")),
				new HashMap<String,Object>()
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
		Loan loan = new Loan();
		loan.setClientRisk(10);
		loan.setCountryRisk(10);
		loan.setCurrencyRisk(10);
		
		ResultSet<LoanAssessment> results = kb.assess1(loan);
		assertTrue(results.hasNext());
		LoanAssessment r = results.next();
		assertEquals("reject: total risk score to high",r.result);
		assertFalse(results.hasNext());
		results.close();
		
	}
	/**
	 * Test 2.
	 */
	public void test2(){
		Loan loan = new Loan();
		loan.setClientRisk(10);
		loan.setCountryRisk(10);
		loan.setCurrencyRisk(10);
		
		ResultSet<LoanAssessment> results = kb.assess2(loan);
		assertTrue(results.hasNext());
		LoanAssessment r = results.next();
		assertEquals("reject: lowest risk score to high",r.result);
		assertFalse(results.hasNext());
		results.close();
	}
	
	/**
	 * Test 3.
	 */
	public void test3(){
		Loan loan = new Loan();
		loan.setClientRisk(10);
		loan.setCountryRisk(10);
		loan.setCurrencyRisk(10);
		
		ResultSet<LoanAssessment> results = kb.assess3(loan);
		assertTrue(results.hasNext());
		LoanAssessment r = results.next();
		assertEquals("reject: max risk score to high",r.result);
		assertFalse(results.hasNext());
		results.close();
	}
	
	/**
	 * Test 4.
	 */
	public void test4(){
		Loan loan = new Loan();
		loan.setClientRisk(10);
		loan.setCountryRisk(10);
		loan.setCurrencyRisk(10);
		
		ResultSet<LoanAssessment> results = kb.assess4(loan);
		assertTrue(results.hasNext());
		LoanAssessment r = results.next();
		assertEquals("reject: risk count to high",r.result);
		assertFalse(results.hasNext());
		results.close();
	}
	
	/**
	 * Test 5.
	 */
	public void test5(){
		Loan loan = new Loan();
		loan.setClientRisk(10);
		loan.setCountryRisk(10);
		loan.setCurrencyRisk(10);
		
		ResultSet<LoanAssessment> results = kb.assess5(loan);
		assertTrue(results.hasNext());
		LoanAssessment r = results.next();
		assertEquals("reject: average risk score to high",r.result);
		assertFalse(results.hasNext());
		results.close();
	}
}
	