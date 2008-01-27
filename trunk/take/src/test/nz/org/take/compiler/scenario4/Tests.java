/**
 * Copyright 2007 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */

package test.nz.org.take.compiler.scenario4;

import nz.org.take.deployment.KnowledgeBaseManager;
import nz.org.take.rt.ResultSet;
import nz.org.take.nscript.ScriptKnowledgeSource;
import test.nz.org.take.TakeTestCase;
import test.nz.org.take.compiler.scenario4.generated.*;
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
	