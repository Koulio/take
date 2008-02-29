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

import java.util.ArrayList;
import java.util.List;

public class Policy {

	Car covers = null;

	Double premium = 0.0;

	Integer vehiclePolicyEligibilityScore = 0;

	public Car getCovers() {
		return covers;
	}

	public void setCovers(Car covers) {
		this.covers = covers;
	}

	public Double getPremium() {
		return premium;
	}

	public void setPremium(Double premium) {
		this.premium = premium;
	}

	public Integer getVehiclePolicyEligibilityScore() {
		return vehiclePolicyEligibilityScore;
	}

	public void setVehiclePolicyEligibilityScore(
			Integer vehiclePolicyEligibilityScore) {
		this.vehiclePolicyEligibilityScore = vehiclePolicyEligibilityScore;
	}

}
