package example.nz.org.take.compiler.userv.testcases;

import nz.org.take.rt.DerivationLogEntry;
import nz.org.take.rt.ResultSet;
import example.nz.org.take.compiler.userv.domainmodel.*;
import example.nz.org.take.compiler.userv.generated.*;
import junit.framework.TestCase;

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
		car.setConvertible(true);
		car.setPrice(18000);
		car.setType("Skoda Fabia");
		car.setHasDriversAirbag(true);
		car.setHasFrontPassengerAirbag(true);
		car.setHasSidePanelAirbags(true);
		ResultSet<AutoEligibility> result = kb.getAutoEligibility(car);
		assertTrue(result.hasNext());
		assertEquals("eligible",result.next().value);
	}
}
