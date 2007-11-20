/*
 * Copyright (C) 2007 Bastian Schenke (bastian.schenke(at)gmail.com) and 
 * <a href="http://www-ist.massey.ac.nz/JBDietrich" target="_top">Jens Dietrich</a>
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
package test.nz.org.take.r2ml;

import test.nz.org.take.r2ml.a.DisjunctiveConditionsTest;
import test.nz.org.take.r2ml.a.R2MLDriverTest;
import test.nz.org.take.r2ml.b.AttributionAtomHandlerTest;
import test.nz.org.take.r2ml.c.EqualityAtomHandlerTest;
import test.nz.org.take.r2ml.scenario3.Scenario3Test;
import test.nz.org.take.r2ml.scenario4.Scenario4Test;
import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {
	
	public static Test suite() {
		TestSuite ts;
		ts = new TestSuite();
		ts.addTestSuite(R2MLDriverTest.class);
		ts.addTestSuite(DisjunctiveConditionsTest.class);
		ts.addTestSuite(AttributionAtomHandlerTest.class);
		ts.addTestSuite(EqualityAtomHandlerTest.class);
		ts.addTestSuite(Scenario3Test.class);
		ts.addTestSuite(Scenario4Test.class);
		return ts;
	}

	void test1() {
	}
}
