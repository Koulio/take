/*
 * Copyright 2007 Bastian Schenke Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */
package example.nz.org.take.r2ml.userv.domain;

import nz.org.take.r2ml.reference.DefaultNameMapper;

public class UServNameMapper extends DefaultNameMapper {

	/**
	 * Set up the default name mapper for the UServ case study.
	 */
	public UServNameMapper() {
		super();
		addNames();
	}

	private void addNames() {
		//this.addSlotNames("premium", new String[] { "policy", "premium" });
		//this.addSlotNames("DriverAirbag", new String[] { "airbag" });
		
		addSlotNames("Car", new String[] {"car"});
		addSlotNames("potentialTheftRating", new String[] {"car", "theftRating"});
		addSlotNames("potentialOccupantInjuryRating", new String[] {"car", "injuryRating"});
		addSlotNames("carEligibility", new String[] {"car", "eligibility"});
		addSlotNames("driverAirbag", new String[] {"car", "driverAirbag"});
		addSlotNames("sideAirbag", new String[] {"car", "sideAirbag"});
		addSlotNames("passengerAirbag", new String[] {"car", "passengerAirbag"});
		addSlotNames("price", new String[] {"car", "price"});
		addSlotNames("CarModel", new String[] {"carModel"});
		addSlotNames("", new String[] {""});
		addSlotNames("", new String[] {""});
		addSlotNames("", new String[] {""});
		addSlotNames("", new String[] {""});
		addSlotNames("", new String[] {""});
		addSlotNames("", new String[] {""});
		
	}
}
