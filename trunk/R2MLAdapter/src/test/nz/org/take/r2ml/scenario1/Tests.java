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

package test.nz.org.take.r2ml.scenario1;

import java.util.*;

import nz.org.take.DerivationRule;
import nz.org.take.Fact;
import nz.org.take.KnowledgeBase;
import nz.org.take.rt.ResultSet;
import test.nz.org.take.compiler.scenario4.College;
import test.nz.org.take.compiler.scenario4.Course;
import test.nz.org.take.compiler.scenario4.Student;
import test.nz.org.take.compiler.scenario4.generated.*;
import junit.framework.TestCase;

/**
 * Tests for this scenario. 
 * For now, the classes have to generated and compiled manually.
 * For generation, use the script GenerateClasses.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class Tests extends TestCase
{

	private KnowledgeBase kb = null;
	/**
	 * Construct new test instance
	 * @param name the test name
	 */
	public Tests(String name)
	{
		super(name);
		try {
			GenerateClasses.main(null);
		} catch (Exception e) {
			fail("Error while generating classes.");
		}
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
		kb = GenerateKB.buildKB();
	}

	public void test1(){
		
		System.out.println("starting test case 1");
		
		_KB kb = new _KB();
		Student s1 = new Student("John");
		Student s2 = new Student("Tom");
		Student s3 = new Student("Tim");
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
		c2.setCollege(coll2);		

		ResultSet<IsEnrolled> rs = kb.isEnrolled(s1, coll1);
		
		assertTrue(rs.hasNext());
		IsEnrolled e1 = rs.next();
		assertTrue(e1.student.getName().equals("John"));
		assertEquals(2, e1.student.getCourses().size());
		assertTrue(e1.college.getName().equals("engineering"));

		assertFalse(rs.hasNext());
		
	}
	
	public void test2(){
		
		System.out.println("starting test case 2");
		
		_KB kb = new _KB();
		Student s1 = new Student("John");
		Student s2 = new Student("Tom");
		Student s3 = new Student("Tim");
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
		c2.setCollege(coll2);		

		ResultSet<IsEnrolled> result = kb.isEnrolled(s1);
		
		assertTrue(result.hasNext());
		IsEnrolled r = result.next();
		assertTrue(r.college.equals(coll1));
		assertTrue(r.student.equals(s1));
		assertTrue(result.hasNext());
		
		r = result.next();
		assertTrue(r.college.equals(coll2));
		assertTrue(r.student.equals(s1));
		assertFalse(result.hasNext());
		
	}
	
}
	