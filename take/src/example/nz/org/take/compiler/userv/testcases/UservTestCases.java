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

package example.nz.org.take.compiler.userv.testcases;

import nz.org.take.rt.DerivationLogEntry;
import nz.org.take.rt.ResultSet;
import example.nz.org.take.compiler.userv.domainmodel.*;
import example.nz.org.take.compiler.userv.generated.*;
import junit.framework.TestCase;

/**
 * Test cases based on the UServ example.
 * http://www.businessrulesforum.com/2005_Product_Derby.pdf 
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class UservTestCases extends TestCase {
	
	private UservRules kb = null;
	
	protected void setUp() throws Exception {
		super.setUp();
		kb = new UservRules();
		// bind constants referenced in the kb
		Constants.HighTheftProbabilityAutoList = HighTheftProbabilityAutoList.getList();
	}
	
	private void printLog(ResultSet result) {
		for (Object s:result.getDerivationLog()) {
			System.out.println(((DerivationLogEntry)s).getName());
		}
	}


	public void testAE_POIC01() throws Exception {
		Car car = new Car();
		car.setHasDriversAirbag(false);
		car.setHasFrontPassengerAirbag(false);
		car.setHasSidePanelAirbags(false);
		ResultSet<PotentialOccupantInjuryRating> result = kb.getPotentialOccupantInjuryRating(car);
		assertTrue(result.hasNext());
		assertEquals("extremely high",result.next().rating);
	}

	public void testAE_POIC02() throws Exception {
		Car car = new Car();
		car.setHasDriversAirbag(true);
		car.setHasFrontPassengerAirbag(false);
		car.setHasSidePanelAirbags(false);
		ResultSet<PotentialOccupantInjuryRating> result = kb.getPotentialOccupantInjuryRating(car);
		assertTrue(result.hasNext());
		assertEquals("high",result.next().rating);
	}

	public void testAE_POIC03() throws Exception {
		Car car = new Car();
		car.setHasDriversAirbag(true);
		car.setHasFrontPassengerAirbag(true);
		car.setHasSidePanelAirbags(false);
		ResultSet<PotentialOccupantInjuryRating> result = kb.getPotentialOccupantInjuryRating(car);
		assertTrue(result.hasNext());
		assertEquals("moderate",result.next().rating);
	}

	public void testAE_POIC04() throws Exception {
		Car car = new Car();
		car.setHasDriversAirbag(true);
		car.setHasFrontPassengerAirbag(true);
		car.setHasSidePanelAirbags(true);
		ResultSet<PotentialOccupantInjuryRating> result = kb.getPotentialOccupantInjuryRating(car);
		assertTrue(result.hasNext());
		assertEquals("low",result.next().rating);
	}

	public void testAE_POIC05() throws Exception {
		Car car = new Car();
		car.setConvertible(true);
		car.setHasRollBar(false);
		ResultSet<PotentialOccupantInjuryRating> result = kb.getPotentialOccupantInjuryRating(car);
		assertTrue(result.hasNext());
		assertEquals("extremely high",result.next().rating);
	}

	public void testAE_PTC01() throws Exception {
		Car car = new Car();
		car.setConvertible(true);
		car.setPrice(20000);
		ResultSet<PotentialTheftRating> result = kb.getPotenialTheftRating(car);
		assertTrue(result.hasNext());
		assertEquals("high",result.next().rating);
	}

	public void testAE_PTC02() throws Exception {
		Car car = new Car();
		car.setConvertible(false);
		car.setPrice(50000);
		ResultSet<PotentialTheftRating> result = kb.getPotenialTheftRating(car);
		assertTrue(result.hasNext());
		assertEquals("high",result.next().rating);
	}

	public void testAE_PTC03() throws Exception {
		Car car = new Car();
		car.setConvertible(false);
		car.setPrice(20000);
		car.setType("Mini");
		ResultSet<PotentialTheftRating> result = kb.getPotenialTheftRating(car);
		assertTrue(result.hasNext());
		assertEquals("high",result.next().rating);
	}

	public void testAE_PTC04() throws Exception {
		Car car = new Car();
		car.setConvertible(false);
		car.setPrice(30000);
		car.setType("BMW 3");
		ResultSet<PotentialTheftRating> result = kb.getPotenialTheftRating(car);
		assertTrue(result.hasNext());
		assertEquals("moderate",result.next().rating);
	}

	public void testAE_PTC05() throws Exception {
		Car car = new Car();
		car.setConvertible(false);
		car.setPrice(18000);
		car.setType("Daihatsu Sirion");
		ResultSet<PotentialTheftRating> result = kb.getPotenialTheftRating(car);
		assertTrue(result.hasNext());
		assertEquals("low",result.next().rating);
	}

	public void testAE01() throws Exception {
		Car car = new Car();
		car.setHasDriversAirbag(false);
		car.setHasFrontPassengerAirbag(false);
		car.setHasSidePanelAirbags(false);
		ResultSet<AutoEligibility> result = kb.getAutoEligibility(car);
		assertTrue(result.hasNext());
		assertEquals("not eligible",result.next().value);
	}

	public void testAE02() throws Exception {
		Car car = new Car();
		car.setHasDriversAirbag(true);
		car.setHasFrontPassengerAirbag(false);
		car.setHasSidePanelAirbags(false);
		ResultSet<AutoEligibility> result = kb.getAutoEligibility(car);
		assertTrue(result.hasNext());
		assertEquals("provisional",result.next().value);
		printLog(result);
	}

	public void testAE03() throws Exception {
		Car car = new Car();
		car.setConvertible(true);
		car.setPrice(20000);
		car.setType("Mini");
		car.setHasDriversAirbag(true);
		ResultSet<AutoEligibility> result = kb.getAutoEligibility(car);
		assertTrue(result.hasNext());
		assertEquals("provisional",result.next().value);
	}
	public void testAE04() throws Exception {
		Car car = new Car();
		car.setConvertible(false);
		car.setPrice(18000);
		car.setType("Skoda Fabia");
		car.setHasDriversAirbag(true);
		car.setHasFrontPassengerAirbag(true);
		car.setHasSidePanelAirbags(true);
		ResultSet<AutoEligibility> result = kb.getAutoEligibility(car);
		assertTrue(result.hasNext());
		assertEquals("eligible",result.next().value);
	}
	public void DE_DAC01() throws Exception {
		Driver driver = new Driver();
		driver.setAge(23);
		driver.setMale(true);
		ResultSet<YoungDriver> result = kb.getDriverCategory(driver);
		assertTrue(result.hasNext());
		assertEquals("young driver",result.next().category);
	}
	public void DE_DAC02() throws Exception {
		Driver driver = new Driver();
		driver.setAge(19);
		driver.setMale(false);
		ResultSet<YoungDriver> result = kb.getDriverCategory(driver);
		assertTrue(result.hasNext());
		assertEquals("young driver",result.next().category);
	}
}
