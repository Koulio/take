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
import example.nz.org.take.r2ml.userv.domain.Driver;
import example.userv.ageCategory.UServKB;
import example.userv.ageCategory.driverAgeCategory;
import junit.framework.TestCase;

public class UServAgeCategoryTC03 extends TestCase {

	UServKB uservkb = new UServKB();
	
	public void test01() {
		Driver driver = new Driver("Person1", Driver.GENDER_MALE, 29);
		
		ResultSet<driverAgeCategory> x = uservkb.driverAgeCategory_10(driver);
		assertFalse(x.hasNext());
		
		driver.setAge(23);
		ResultSet<driverAgeCategory> y = uservkb.driverAgeCategory_10(driver);
		assertTrue(y.hasNext());
		driverAgeCategory dac = y.next();
		assertEquals("young driver", dac.slot2);
		assertFalse(y.hasNext());
		
		driver.setGender(Driver.GENDER_FEMALE);
		ResultSet<driverAgeCategory> z = uservkb.driverAgeCategory_10(driver);
		assertFalse(z.hasNext());
	}
	
	public void test02() {
		Driver driver = new Driver("Person2", Driver.GENDER_FEMALE, 71);
		
		ResultSet<driverAgeCategory> x = uservkb.driverAgeCategory_10(driver);
		assertTrue(x.hasNext());
		driverAgeCategory dac = x.next();
		assertEquals("senior driver", dac.slot2);
		assertFalse(x.hasNext());
	}

}
