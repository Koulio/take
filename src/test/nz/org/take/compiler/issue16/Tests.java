/**
 * Copyright 2007 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */

package test.nz.org.take.compiler.issue16;

import junit.framework.TestCase;
import nz.org.take.deployment.KnowledgeBaseManager;
import nz.org.take.nscript.ScriptKnowledgeSource;
import nz.org.take.rt.ResultSet;
import test.nz.org.take.TakeTestCase;
import test.nz.org.take.compiler.issue16.generated.IsCollegue;
import test.nz.org.take.compiler.issue16.generated.KB;

/**
 * Tests for this scenario.
 * 
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class Tests extends TakeTestCase {
	private KB kb = null;

	/**
	 * Construct new test instance
	 * 
	 * @param name
	 *            the test name
	 */
	public Tests(String name) {
		super(name);
	}

	/**
	 * Perform pre-test initialization
	 * 
	 * @throws Exception
	 * 
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		KnowledgeBaseManager<KB> kbm = new KnowledgeBaseManager<KB>();
		kb = kbm
				.getKnowledgeBase(
						KB.class,
						new ScriptKnowledgeSource(
								Tests.class
										.getResourceAsStream("/test/nz/org/take/compiler/issue16/rules.take")));
	}

	/**
	 * Perform post-test clean up
	 * 
	 * @throws Exception
	 * 
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
		// Add additional tear down code here
	}

	public void test_validCompany() {

		System.out.println("starting test case 1");

		Person p1 = new Person("foo");
		Person p2 = new Person("bar");
		
		Company company = new Company("foobar");

		p1.setCompany(company);
		p2.setCompany(company);
		
		ResultSet<IsCollegue> result = kb.isCollegue(p1, p2);
		
		assertTrue(result.hasNext());
		
		IsCollegue r1 = result.next();
		assertTrue(r1.person1.equals(p1));
		assertEquals(p1, r1.person1);
		assertEquals(p2, r1.person2);
		
	}
	
	public void test_nullCompanySecondPerson() {

		System.out.println("starting test case nullCompanySecondPerson");

		Person p1 = new Person("foo");
		Person p2 = new Person("bar");
		
		Company company = new Company("foobar");

		p1.setCompany(company);
		p2.setCompany(null);
		
		ResultSet<IsCollegue> result = kb.isCollegue(p1, p2);
		
		assertFalse(result.hasNext());
		
	}

	public void test_nullCompanyFirstPerson() {

		System.out.println("starting test case nullCompanyFirstPerson");

		Person p1 = new Person("foo");
		Person p2 = new Person("bar");
		
		Company company = new Company("foobar");

		p1.setCompany(company);
		p2.setCompany(null);
		
		// we infer the same predicate but the argument order has changed
		ResultSet<IsCollegue> result = kb.isCollegue(p2, p1);
		
		assertFalse(result.hasNext());
		
	}

}
