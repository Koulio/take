 package test.nz.org.take.r2ml.f;

import test.nz.org.take.r2ml.f.generatedKb.*;
import nz.org.take.rt.DerivationLogEntry;
import nz.org.take.rt.ResultSet;
import example.nz.org.take.r2ml.userv.domain.Car;
import example.nz.org.take.r2ml.userv.domain.CarModel;
import junit.framework.TestCase;

public class AutoDriverEligiblityTests extends TestCase {

	UServKB uservkb = new UServKB();

	public void test001() {
		System.out.println("### test01 ###");
		// carmodel wothout high theft probability model and a low price
		CarModel carModel = new CarModel();
		carModel.setModelType("myModel");
		Car car = new Car();
		car.setModel(carModel);
		car.setPrice(2000);

		ResultSet<htp> y = uservkb.htp_1(car);
		assertFalse(y.hasNext());
		car.setModel(new CarModel(true, "myModel"));
		ResultSet<potentialTheftRating> x = uservkb.potentialTheftRating_10(car);
		assertTrue(x.hasNext());
		potentialTheftRating ptr = x.next();
		assertEquals("high", ptr.theftRating);
		assertFalse(x.hasNext());
	}

	public void test002() {
		System.out.println("### test002 ###");
		Car car = new Car();
		CarModel carModel = new CarModel();
		carModel.setModelType("myModel");
		carModel.setHighTheftProbability(true);
		car.setModel(carModel);
		ResultSet<potentialTheftRating> x = uservkb
				.potentialTheftRating_10(car);
		assertTrue(x.hasNext());
		while (x.hasNext()) {
			potentialTheftRating ptr = x.next();
			assertNotNull(ptr.theftRating);
			System.out.println(ptr.theftRating);
		}
//		for (DerivationLogEntry log : x.getDerivationLog())
//			System.out.print(log.getName() + " ### ");
		System.out.println();
	}

//	public void test01() {
//
//		System.out.println("### test01 ###");
//		assertNotNull(uservkb);
//		Car car = new Car();
//		car.setModel(new CarModel(true, "niceCar"));
//
//		ResultSet<carEligibility> x = uservkb.carEligibility_10(car);
//		assertTrue(x.hasNext());
//		boolean correct = false;
//		while (x.hasNext()) {
//			System.out.print(x.getDerivationLog().get(0).getName() + " ### ");
//			carEligibility ce = x.next();
//			correct = ce.eligibility.equals("provisional");
//			System.out.println(ce.eligibility);
//		}
//		assertTrue(correct);
//
//	}

//	public void test02() {
//
//		System.out.println("### test02 ###");
//
//		Car car = new Car();
//		car.setDriverAirbag(true);
//		car.setPassengerAirbag(true);
//		car.setSideAirbag(true);
//		car.setModel(new CarModel());
//		ResultSet<carEligibility> x = uservkb.carEligibility_10(car);
//		assertTrue(x.hasNext());
//		boolean correct = false;
//		while (x.hasNext()) {
//			for (DerivationLogEntry log : x.getDerivationLog())
//				System.out.print(log.getName() + " ### ");
//			carEligibility ce = x.next();
//			correct = ce.eligibility.equals("eligible");
//			System.out.println(ce.eligibility);
//		}
//		assertTrue(correct);
//
//	}
}
