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

package test.nz.org.take.compiler.scenario10;

import javax.script.SimpleBindings;
import test.nz.org.take.TakeTestCase;
import test.nz.org.take.compiler.scenario10.generated.CanFly;
import test.nz.org.take.compiler.scenario10.generated.KB;
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
	 * @param name the test name
	 */
	public Tests(String name)
	{
		super(name);
	}

	/**
	 * Perform pre-test initialization
	 * @throws Exception
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception
	{
		super.setUp();
		KnowledgeBaseManager<KB> kbm = new KnowledgeBaseManager<KB>();
		kb = kbm.getKnowledgeBase(
				KB.class, 
				new ScriptKnowledgeSource(Tests.class.getResourceAsStream("/test/nz/org/take/compiler/scenario10/rules10.take")),
				new SimpleBindings()
		); 

	}

	/**
	 * Perform post-test clean up
	 * @throws Exception
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
		Bird bird = new Bird();
		bird.setKind("kiwi");
		ResultSet<CanFly> results = kb.canFly(bird);
		assertFalse(results.hasNext());
		results.close();
	}
	/**
	 * Test 2.
	 */
	public void test2(){
		Bird bird = new Bird();
		bird.setKind("ostrich");
		ResultSet<CanFly> results = kb.canFly(bird);
		assertFalse(results.hasNext());
		results.close();
	}
	/**
	 * Test 3.
	 */
	public void test3(){
		Bird bird = new Bird();
		bird.setKind("tui");
		ResultSet<CanFly> results = kb.canFly(bird);
		assertTrue(results.hasNext());
		results.close();
		
	}
}
	