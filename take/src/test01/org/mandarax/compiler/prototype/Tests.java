/*
 * Copyright (C) 2006 <A href="http://www-ist.massey.ac.nz/JBDietrich" target="_top">Jens Dietrich</a>
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

package test01.org.mandarax.compiler.prototype;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.mandarax.compiler.rt.DerivationController;
import org.mandarax.compiler.rt.ResultSet;
import test01.org.mandarax.compiler.prototype.generated._KB;
import test01.org.mandarax.compiler.prototype.generated._is_father_of;
import test01.org.mandarax.compiler.prototype.generated._is_grandfather_of;
import test01.org.mandarax.compiler.prototype.generated._is_oncle_of;
import junit.framework.TestCase;

/**
 * Tests for this scenario. 
 * For now, the classes have to generated and compiled manually.
 * For generation, use the script GenerateClasses.
 * Code pretty printer.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 * @version 0.1
 */

public class Tests extends TestCase
{

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
	}

	/**
	 * Perform post-test clean up
	 *
	 * @throws Exception
	 *
	 * @see TestCase#tearDown()
	 */
	protected void tearDown()
		throws Exception
	{
		super.tearDown();
		// Add additional tear down code here
	}

	/**
	 * Test 1.
	 */
	public void test1(){
		_KB kb = new _KB();
		
		Iterator<_is_father_of> results = kb.is_father_of_10("Max");	
		assertTrue(results.hasNext());
		_is_father_of r = results.next();
		String father = r.father;
		assertEquals("Jens",father);
		assertFalse(results.hasNext());
	}
	/**
	 * Test 2.
	 */
	public void test2(){
		_KB kb = new _KB();
		ResultSet<_is_grandfather_of> results = kb.is_grandfather_of_10("Max");	
		assertTrue(results.hasNext());
		_is_grandfather_of r = results.next();
		DerivationController x = results.getDerivationController();
		x.printLog();
		String grandfather = r.grandfather;
		assertEquals("Klaus",grandfather);
		assertFalse(results.hasNext());
	}
	

	/**
	 * Test 3.
	 */
	public void test3(){
		_KB kb = new _KB();
		Iterator<_is_father_of> results = kb.is_father_of_01("Jens");	
		assertTrue(results.hasNext());
		_is_father_of r = results.next();
		String son = r.son;
		assertEquals("Max",son);
		assertFalse(results.hasNext());
	}
	

	/**
	 * Test 4.
	 */
	public void test4(){
		_KB kb = new _KB();
		Iterator<_is_father_of> results = kb.is_father_of_01("Otto");	
		assertTrue(results.hasNext());
		assertEquals("Guenther",results.next().son);
		assertEquals("Lutz",results.next().son);
		assertEquals("Klaus",results.next().son);
		assertEquals("Werner",results.next().son);
		assertFalse(results.hasNext());
	}
	
	/**
	 * Test 4.
	 */
	public void test5(){
		_KB kb = new _KB();
		Iterator<_is_grandfather_of> results = kb.is_grandfather_of_01("Otto");	
		assertTrue(results.hasNext());
		while(results.hasNext()) {
			System.out.println(results.next().grandchild);
		}
	}
	
	public void test6() {
		_KB kb = new _KB();
		Iterator<_is_oncle_of> results = kb.is_oncle_of_01("Jens");
		assertTrue(results.hasNext());
		List<String> expected = new LinkedList<String>();
		expected.add("Guenther");
		expected.add("Lutz");
		expected.add("Werner");
		while(results.hasNext())
			assertTrue(expected.contains(results.next().oncle));
	}
	
}
	