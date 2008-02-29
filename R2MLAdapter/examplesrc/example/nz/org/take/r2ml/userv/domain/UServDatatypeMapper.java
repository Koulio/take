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

import nz.org.take.r2ml.reference.DefaultDatatypeMapper;


public class UServDatatypeMapper extends DefaultDatatypeMapper {

	public UServDatatypeMapper() {
		super();
		
		setType("Vehicle", Car.class);
		setType("Car", Car.class);
		setType("CarModel", CarModel.class);
//		setType("Airbag", Airbag.class);
//		setType("SideAirbag", Airbag.class);
//		setType("PassengerAirbag", Airbag.class);
//		setType("DriverAirbag", Airbag.class);
		setType("Driver", Driver.class);
		setType("ServiceParticipant", Driver.class);
		setType("BusinessParty", Driver.class);
		setType("Person", Driver.class);
		setType("Policy", Policy.class);
		setType("Policy", Policy.class);
//		setType("AlarmSystem", AlarmSystem.class);

	}

}
