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

package test.nz.org.take.compiler.scenario9;

import javax.script.Bindings;
import javax.script.SimpleBindings;

import test.nz.org.take.TakeTestCase;
import test.nz.org.take.compiler.scenario9.generated.KB;
import test.nz.org.take.compiler.scenario9.generated.LoanAssessment;
import nz.org.take.deployment.KnowledgeBaseManager;
import nz.org.take.rt.ResultSet;
import nz.org.take.script.ScriptKnowledgeSource;
import junit.framework.TestCase;

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
		Bindings factStores = new SimpleBindings();
		kb = kbm.getKnowledgeBase(
				KB.class, 
				new ScriptKnowledgeSource(Tests.class.getResourceAsStream("/test/nz/org/take/compiler/scenario9/rules9.take")),
				new SimpleBindings()
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
	