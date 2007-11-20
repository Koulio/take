package test.nz.org.take.r2ml.c;

import test.nz.org.take.r2ml.Log4jConfigurator;
import test.nz.org.take.r2ml.c.generated.FamilyKB;
import test.nz.org.take.r2ml.c.generated.isFamily;

import nz.org.take.rt.ResultSet;

import junit.framework.TestCase;

public class EqualityAtomHandlerTest extends TestCase {
	
	FamilyKB kb = null;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		try {
			Log4jConfigurator.configure();
			kb = new FamilyKB();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void test1() {
		Person m = new Person("Max", "Dietrich");
		Person j = new Person("Jens", "Dietrich");
		Person b = new Person("Bastian", "Schenke");
		
		ResultSet<isFamily> result = kb.isFamily_11(m, j);
		assertTrue(result.hasNext());
		isFamily r = result.next();
		assertTrue(r.slot1.equals(m));
		
		ResultSet<isFamily> result2 = kb.isFamily_11(j, b);
		assertFalse(result2.hasNext());

	}

}
