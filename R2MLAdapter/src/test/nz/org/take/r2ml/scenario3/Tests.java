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

package test.nz.org.take.r2ml.scenario3;

import java.util.Iterator;

import test.nz.org.take.r2ml.scenario3.generated.FamilyKB;
import test.nz.org.take.r2ml.scenario3.generated._isFather;
import test.nz.org.take.r2ml.scenario3.generated._isGrandfather;

import nz.org.take.deployment.KnowledgeBaseManager;
import nz.org.take.r2ml.R2MLKnowledgeSource;
import nz.org.take.rt.DerivationController;
import nz.org.take.rt.ResultSet;
import junit.framework.TestCase;

/**
 * Tests for this scenario. For now, the classes have to generated and compiled
 * manually. For generation, use the script GenerateClasses. Code pretty
 * printing is used.
 * 
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class Tests extends TestCase {
	private FamilyKB kb = null;

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
		KnowledgeBaseManager<FamilyKB> kbm = new KnowledgeBaseManager<FamilyKB>();
		R2MLKnowledgeSource ksrc = new R2MLKnowledgeSource(Tests.class.getResourceAsStream("/test/nz/org/take/r2ml/scenario3/rules3.xml"));
		ksrc.setSlotNameGenerator(new MyNameMapper());
		KBUtil.addQuerries(ksrc);
		KBUtil.addKnowledge(ksrc);
		kb = kbm.getKnowledgeBase(
				FamilyKB.class,
				ksrc
				);
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

	/**
	 * Test 1.
	 */
	public void test1() {
		Iterator<_isFather> results = kb.isFather_10("Max");
		assertTrue(results.hasNext());
		_isFather r = results.next();
		String father = r.father;
		assertEquals("Jens", father);
		assertFalse(results.hasNext());
	}

	/**
	 * Test 2.
	 */
	public void test2() {
		ResultSet<_isGrandfather> results = kb.isGrandfather_10("Max");
		assertTrue(results.hasNext());
		_isGrandfather r = results.next();
		DerivationController x = results.getDerivationController();
		x.printLog();
		String grandfather = r.grandfather;
		assertEquals("Klaus", grandfather);
		assertFalse(results.hasNext());
	}

	/**
	 * Test 3.
	 */
	public void test3() {
		Iterator<_isFather> results = kb.isFather_01("Jens");
		assertTrue(results.hasNext());
		_isFather r = results.next();
		String son = r.son;
		assertEquals("Max", son);
		assertFalse(results.hasNext());
	}

	/**
	 * Test 4.
	 */
	public void test4() {
		Iterator<_isFather> results = kb.isFather_01("Otto");
		assertTrue(results.hasNext());
		assertEquals("Guenther", results.next().son);
		assertEquals("Lutz", results.next().son);
		assertEquals("Klaus", results.next().son);
		assertEquals("Werner", results.next().son);
		assertFalse(results.hasNext());
	}

	/**
	 * Test 4.
	 */
	public void test5() {
		Iterator<_isGrandfather> results = kb.isGrandfather_01("Otto");
		assertTrue(results.hasNext());
		while (results.hasNext()) {
			System.out.println(results.next().grandson);
		}
	}
}
