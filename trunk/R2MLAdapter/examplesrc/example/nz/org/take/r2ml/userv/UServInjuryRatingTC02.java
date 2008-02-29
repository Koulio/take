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

import nz.org.take.rt.ResultSet;
import example.nz.org.take.r2ml.userv.domain.Car;
import example.userv.injuryRating.*;
import junit.framework.TestCase;

public class UServInjuryRatingTC02 extends TestCase {

	UServKB uservkb = new UServKB();

	public void test01() {
		Car car = new Car();
		car.setDriverAirbag(true);
		car.setSideAirbag(true);
		car.setPassengerAirbag(true);

		ResultSet<potentialOccupantInjuryRating> x = uservkb
				.potentialOccupantInjuryRating_10(car);
		assertTrue(x.hasNext());
		potentialOccupantInjuryRating poir = x.next();
		assertEquals("low", poir.injuryRating);
		assertFalse(x.hasNext());
	}

	public void test02() {
		Car car = new Car();
		car.setDriverAirbag(true);

		ResultSet<potentialOccupantInjuryRating> x = uservkb
				.potentialOccupantInjuryRating_10(car);
		assertTrue(x.hasNext());
		potentialOccupantInjuryRating poir = x.next();
		assertEquals("high", poir.injuryRating);
		assertFalse(x.hasNext());
	}

	public void test03() {
		Car car = new Car();
		car.setConvertible(true);
		car.setRollbar(false);
		car.setDriverAirbag(true);

		ResultSet<potentialOccupantInjuryRating> x = uservkb
				.potentialOccupantInjuryRating_10(car);
		assertTrue(x.hasNext());
		while(x.hasNext()) {
			potentialOccupantInjuryRating poir = x.next();
			System.out.println(poir.injuryRating);
		}
		//fail("more than one solution its complete, but not sound.");
	}
	
	public void test04() {
		Car car = new Car();
		car.setDriverAirbag(true);
		car.setPassengerAirbag(true);
		car.setSideAirbag(false);
		
		ResultSet<potentialOccupantInjuryRating> x = uservkb.potentialOccupantInjuryRating_10(car);
		assertTrue(x.hasNext());
		potentialOccupantInjuryRating poir = x.next();
		assertEquals("moderate", poir.injuryRating);
		assertFalse(x.hasNext());
	}

}
