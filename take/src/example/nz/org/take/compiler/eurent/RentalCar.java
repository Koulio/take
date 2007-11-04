/*
 * Copyright 2007 Jens Dietrich 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */

package example.nz.org.take.compiler.eurent;

/**
 * Domain class implemented as PLOJO (plain old Java object). 
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class RentalCar {
	private boolean isScheduledForService = false;
	private Rental assignedTo = null;
	private Branch storedAt = null;
	private String plateNumber = null;

	public boolean isScheduledForService() {
		return isScheduledForService;
	}

	public void setScheduledForService(boolean isScheduledForService) {
		this.isScheduledForService = isScheduledForService;
	}

	public Rental getAssignedTo() {
		return assignedTo;
	}
	
	public boolean hasAssignedTo() {
		return getAssignedTo()!=null;
	}

	public void setAssignedTo(Rental assignedTo) {
		this.assignedTo = assignedTo;
	}

	public Branch getStoredAt() {
		return storedAt;
	}

	public void setStoredAt(Branch storedAt) {
		this.storedAt = storedAt;
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}


}
