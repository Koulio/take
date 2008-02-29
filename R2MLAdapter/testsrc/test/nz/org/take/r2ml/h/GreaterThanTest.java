package test.nz.org.take.r2ml.h;

import nz.org.take.rt.ResultSet;
import test.nz.org.take.r2ml.h.generatedKb.TestKB;
import test.nz.org.take.r2ml.h.generatedKb.okay;
import junit.framework.TestCase;

public class GreaterThanTest extends TestCase {
	TestKB tkb = new TestKB();
	
	public void test01() {
		ResultSet<okay> x = tkb.okay_10(50);
		assertFalse(x.hasNext());
	}
	
	public void test02() {
		ResultSet<okay> x = tkb.okay_10(5);
		assertTrue(x.hasNext());
		okay ok = x.next();
		assertEquals("yes", ok.slot2);
	}
}
