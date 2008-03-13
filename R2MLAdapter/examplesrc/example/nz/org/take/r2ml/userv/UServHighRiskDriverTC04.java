package example.nz.org.take.r2ml.userv;

import nz.org.take.rt.ResultSet;
import example.nz.org.take.r2ml.userv.domain.Driver;
import example.userv.highRiskDriver.UServKB;
import example.userv.highRiskDriver.highRiskDriver;
import junit.framework.TestCase;

public class UServHighRiskDriverTC04 extends TestCase {
	
	UServKB uservkb = new UServKB();
	
	public void test01() {
		Driver driver = new Driver("driver01", Driver.GENDER_FEMALE, 25);
		ResultSet<highRiskDriver> rs = uservkb.highRiskDriver_1(driver);
		assertFalse(rs.hasNext());
		driver.setDui(true);
		
		rs = uservkb.highRiskDriver_1(driver);
		assertTrue(rs.hasNext());
		assertEquals(driver, rs.next().slot1);
		
		driver.setDui(false);
		assertFalse(uservkb.highRiskDriver_1(driver).hasNext());
		driver.setNumberOfAccidents(3);
		rs = uservkb.highRiskDriver_1(driver);
		assertTrue(rs.hasNext());
		driver.setNumberOfAccidents(2);
		assertFalse(uservkb.highRiskDriver_1(driver).hasNext());
		driver.setNumberOfMovingViolations(4);
		rs = uservkb.highRiskDriver_1(driver);
		assertTrue(rs.hasNext());
		assertEquals(driver, rs.next().slot1);
	}

}
