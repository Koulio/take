/*
 * Copyright 2007 Bastian Schenke Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */
package example.nz.org.take.r2ml.userv;

import junit.framework.TestCase;
import nz.org.take.rt.ResultSet;
import example.nz.org.take.r2ml.userv.domain.Car;
import example.nz.org.take.r2ml.userv.domain.CarModel;
import example.userv.theftRating.UServKB;
import example.userv.theftRating.potentialTheftRating;

public class UServTheftRatingTC01 extends TestCase {
	UServKB uservkb = new UServKB();

	public void test01() {
		Car car = new Car();
		car.setModel(new CarModel(true, 200, "MyCoupe"));

		ResultSet<potentialTheftRating> x = uservkb
				.potentialTheftRating_10(car);
		assertTrue(x.hasNext());
		potentialTheftRating ptr1 = x.next();
		assertEquals("high", ptr1.theftRating);
		assertFalse(x.hasNext());
		//System.out.println("HighTheftProbability is " + ptr1.theftRating);
	}

	public void test02() {
		Car car = new Car();
		car.setPrice(50000);
		CarModel cm = new CarModel(false, 50, "MyVan");
		car.setModel(new CarModel());

		ResultSet<potentialTheftRating> x = uservkb
				.potentialTheftRating_10(car);
		assertTrue(x.hasNext());
		potentialTheftRating ptr = x.next();
		assertEquals("high", ptr.theftRating);
		assertFalse(x.hasNext());
		//System.out.println("HighTheftProbability is " + ptr.theftRating);

		Car car2 = new Car();
		car2.setPrice(50000);
		CarModel cm2 = new CarModel(true, 50, "MyVan");
		car.setModel(cm2);

		ResultSet<potentialTheftRating> y = uservkb
				.potentialTheftRating_10(car2);
		assertTrue(y.hasNext());
		potentialTheftRating ptr2 = y.next();
		//System.out.println(ptr2.theftRating);
		assertEquals("high", ptr2.theftRating);
		assertFalse(y.hasNext());
	}

	public void test03() {
		Car car = new Car();
		car.setPrice(15000);
		car.setModel(new CarModel(false, 200, "MyCompact"));
		ResultSet<potentialTheftRating> x = uservkb.potentialTheftRating_10(car);
		
		assertTrue(x.hasNext());
		potentialTheftRating ptr = x.next();
		assertEquals("low", ptr.theftRating);
		assertFalse(x.hasNext());
	}
	
	public void test04() {
		Car car = new Car();
		car.setPrice(30000);
		car.setModel(new CarModel(false, 200, "MyMedium"));
		
		ResultSet<potentialTheftRating> x = uservkb.potentialTheftRating_10(car);
		assertTrue(x.hasNext());
		potentialTheftRating ptr = x.next();
		assertEquals("moderate", ptr.theftRating);
		assertFalse(x.hasNext());
	}
}
