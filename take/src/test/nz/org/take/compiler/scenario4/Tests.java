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

package test.nz.org.take.compiler.scenario4;

import nz.org.take.deployment.KnowledgeBaseManager;
import nz.org.take.rt.ResultSet;
import nz.org.take.script.ScriptKnowledgeSource;
import test.nz.org.take.TakeTestCase;
import test.nz.org.take.compiler.scenario4.generated.*;
import junit.framework.TestCase;

/**
 * Tests for this scenario. 
 * For now, the classes have to generated and compiled manually.
 * For generation, use the script GenerateClasses.
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
				new ScriptKnowledgeSource(Tests.class.getResourceAsStream("/test/nz/org/take/compiler/scenario4/rules4.take"))
				); 
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

	public void test1(){
		
		System.out.println("starting test case 1");
		
		Student s1 = new Student("John");
		Student s2 = new Student("Tom");
		Course c1 = new Course("comp101");
		Course c2 = new Course("se201");
		Course c3 = new Course("fin101");
		College coll1 = new College("engineering");
		College coll2 = new College("business");
		
		s1.getCourses().add(c1);
		s1.getCourses().add(c2);
		s2.getCourses().add(c2);
		s2.getCourses().add(c3);
		c1.setCollege(coll1);
		c1.setCollege(coll1);
		c2.setCollege(coll2);		

		ResultSet<IsEnrolled> result = kb.isEnrolled(s1);
		IsEnrolled r1 = result.next();
		assertTrue(r1.college.equals(coll1));
	}
	
	public void test2(){
		
		System.out.println("starting test case 2");

		Student s1 = new Student("John");
		Student s2 = new Student("Tom");
		Course c1 = new Course("comp101");
		Course c2 = new Course("se201");
		Course c3 = new Course("fin101");
		College coll1 = new College("engineering");
		College coll2 = new College("business");
		
		s1.getCourses().add(c1);
		s1.getCourses().add(c2);
		s2.getCourses().add(c2);
		s2.getCourses().add(c3);
		c1.setCollege(coll1);
		c2.setCollege(coll1);
		c3.setCollege(coll2);		

		ResultSet<IsEnrolled> result = kb.isEnrolled(s1,coll1);
		assertTrue(result.hasNext());
		IsEnrolled r = result.next();
		assertTrue(r.college.equals(coll1));
		assertTrue(r.student.equals(s1));
	}
	
	public void test3(){
		
		System.out.println("starting test case 2");
		
		Student s1 = new Student("John");
		Student s2 = new Student("Tom");
		Course c1 = new Course("comp101");
		Course c2 = new Course("se201");
		Course c3 = new Course("fin101");
		College coll1 = new College("engineering");
		College coll2 = new College("business");
		
		s1.getCourses().add(c1);
		s1.getCourses().add(c2);
		s2.getCourses().add(c2);
		s2.getCourses().add(c3);
		c1.setCollege(coll1);
		c2.setCollege(coll1);
		c3.setCollege(coll2);		

		ResultSet<IsEnrolled> result = kb.isEnrolled(s2);
		assertTrue(result.hasNext());
		IsEnrolled r = result.next();
		assertTrue(r.college.equals(coll1));
		assertTrue(r.student.equals(s2));
		
		assertTrue(result.hasNext());
		r = result.next();
		assertTrue(r.college.equals(coll2));
		assertTrue(r.student.equals(s2));
		
		assertTrue(!result.hasNext());
	}

}
	