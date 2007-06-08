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

package test.nz.org.take.r2ml.scenario2;

import nz.org.take.TakeException;
import nz.org.take.deployment.KnowledgeBaseManager;
import nz.org.take.r2ml.R2MLKnowledgeSource;
import nz.org.take.rt.ResultSet;
import test.nz.org.take.r2ml.scenario2.generated.EnrollmentKB;
import test.nz.org.take.r2ml.scenario2.generated._isEnrolled;
import junit.framework.TestCase;

/**
 * Scenario0Test for this scenario. For now, the classes have to generated and
 * compiled manually. For generation, use the script ___OLDGenerateClasses.
 * 
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class Scenario2Test extends TestCase {

	private EnrollmentKB kb = null;

	/**
	 * Construct new test instance
	 * 
	 * @param name
	 *            the test name
	 */
	public Scenario2Test() {
		super("R2MLImportTest");
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
		try {
			KnowledgeBaseManager<EnrollmentKB> kbm = new KnowledgeBaseManager<EnrollmentKB>();
			R2MLKnowledgeSource kSrc = new R2MLKnowledgeSource(Scenario2Test.class.getResourceAsStream("/test/nz/org/take/r2ml/scenario2/rules.xml"));
			kSrc.setDatatypeMapper(new MyDatatypeMapper());
			kSrc.setSlotNameGenerator(new MyNameMapper());
			GenerateQuerries.addQuerries(kSrc);
			kb = kbm.getKnowledgeBase(EnrollmentKB.class, kSrc);
		} catch (TakeException e) {
			e.printStackTrace();
		}
	}

	public void test1() {

		System.out.println("starting test case 1");

		Student s1 = new Student("John");
		Student s2 = new Student("Tom");
		// Student s3 = new Student("Tim");
		Course c1 = new Course("comp101");
		Course c2 = new Course("se201");
		Course c3 = new Course("fin101");
		College coll1 = new College("engineering");
		College coll2 = new College("business");

		s1.setCourse(c1);
		s2.setCourse(c2);
		c1.setCollege(coll1);
		c2.setCollege(coll2);

		ResultSet<_isEnrolled> result = kb.isEnrolled_01(s1);

		assertTrue(result.hasNext());
		_isEnrolled r = result.next();
		assertTrue(r.college.equals(coll1));
		assertTrue(r.student.equals(s1));
		assertFalse(result.hasNext());

	}

//	public void test2() {
//
//		System.out.println("starting test case 1");
//
//				Student s1 = new Student("John");
//				Student s2 = new Student("Tom");
//				// Student s3 = new Student("Tim");
//				Course c1 = new Course("comp101");
//				Course c2 = new Course("se201");
//				Course c3 = new Course("fin101");
//				College coll1 = new College("engineering");
//				College coll2 = new College("business");
//
//				s1.getCourses().add(c1);
//				s1.getCourses().add(c2);
//				s2.getCourses().add(c2);
//				s2.getCourses().add(c3);
//				c1.setCollege(coll1);
//				c2.setCollege(coll2);
//
//				ResultSet<_isEnrolled> rs = kb.isEnrolled_11(s1, coll1);
//
//				assertTrue("Result set is empty.", rs.hasNext());
//				_isEnrolled e1 = rs.next();
//				assertTrue(e1.student.getName().equals("John"));
//				assertEquals(2, e1.student.getCourses().size());
//				assertTrue(e1.college.getName().equals("engineering"));
//
//				assertFalse(rs.hasNext());
//
//	}

}
