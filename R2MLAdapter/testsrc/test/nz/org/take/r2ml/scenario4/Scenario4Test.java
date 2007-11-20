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

package test.nz.org.take.r2ml.scenario4;

import nz.org.take.TakeException;
import nz.org.take.deployment.KnowledgeBaseManager;
import nz.org.take.r2ml.R2MLKnowledgeSource;
import nz.org.take.rt.ResultSet;
import test.nz.org.take.r2ml.Log4jConfigurator;
import test.nz.org.take.r2ml.scenario4.generated.EnrollmentKB;
import test.nz.org.take.r2ml.scenario4.generated.isEnrolled;
import junit.framework.TestCase;

/**
 * Test for this scenario. For now, the classes have to generated and
 * compiled manually. 
 * 
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 * @author Bastian Schenke (bastian.schenke(at)googlemail.com)
 */
public class Scenario4Test extends TestCase {

	private EnrollmentKB kb = null;

	/**
	 * Construct new test instance
	 * 
	 * @param name
	 *            the test name
	 */
	public Scenario4Test() {
		super("R2MLImportTest");
	}

	/**
	 * Perform pre-test initialization
	 * 
	 * @throws Exception
	 * 
	 * @see TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		try {
			Log4jConfigurator.configure();
			KnowledgeBaseManager<EnrollmentKB> kbm = new KnowledgeBaseManager<EnrollmentKB>();
			R2MLKnowledgeSource kSrc = new R2MLKnowledgeSource(Scenario4Test.class.getResourceAsStream("/test/nz/org/take/r2ml/scenario4/rules.xml"));
			kSrc.setDatatypeMapper(new MyDatatypeMapper());
			kSrc.setSlotNameGenerator(new MyNameMapper());
			KBUtil.addQuerries(kSrc);
			kb = kbm.getKnowledgeBase(EnrollmentKB.class, kSrc);
		} catch (TakeException e) {
			e.printStackTrace();
		}
	}
	
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception {
		Log4jConfigurator.shutdown();
		super.tearDown();
	}

	public void testQuerry1() {

		Student s1 = new Student("John");
		Course c1 = new Course("comp101");
		Course c2 = new Course("se201");
		College coll1 = new College("engineering");
		College coll2 = new College("business");

		s1.getCourses().add(c1);
		s1.getCourses().add(c2);
		c1.setCollege(coll1);
		c2.setCollege(coll2);

		ResultSet<isEnrolled> result = kb.isEnrolled_01(s1);

		isEnrolled r;
		assertTrue(result.hasNext());
		r = result.next();
		assertNotNull("result student should be enrolled at a college", r.college);
		assertTrue(r.college.equals(coll1));
		assertTrue(r.student.equals(s1));
		assertTrue(result.hasNext());
		r = result.next();
		assertTrue(r.college.equals(coll2));
		assertTrue(r.student.equals(s1));
		assertFalse(result.hasNext());
		
	}
	
	public void testQuerry2 () {
		
		Student s1 = new Student("John");
		Course c1 = new Course("comp101");
		Course c2 = new Course("se201");
		College coll1 = new College("engineering");
		College coll2 = new College("business");
		
		s1.getCourses().add(c1);
		s1.getCourses().add(c2);
		c1.setCollege(coll1);
		c2.setCollege(coll2);

		ResultSet<isEnrolled> result = kb.isEnrolled_11(coll1, s1);
		
		isEnrolled r;
		assertTrue(result.hasNext());
		r = result.next();
		assertTrue(r.college.equals(coll1));
		assertTrue(r.student.equals(s1));
		assertFalse(result.hasNext());

	}

}
