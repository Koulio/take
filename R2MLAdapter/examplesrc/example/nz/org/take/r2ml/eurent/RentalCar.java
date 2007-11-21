/*
 * Copyright (C) 2007 <A href="http://www-ist.massey.ac.nz/JBDietrich" target="_top">Jens Dietrich</a>
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package example.nz.org.take.r2ml.eurent;

/**
 * Domain class implemented as PLOJO (plain old Java object). 
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class RentalCar {
	
	private boolean rentalCar = true;
	private boolean isRentalCarScheduledForService = false;
	private Rental assignedTo = null;
	private Branch storedAt = null;
	private String plateNumber = null;

	public boolean isRentalCarScheduledForService() {
		return isRentalCarScheduledForService;
	}

	public void setRentalCarScheduledForService(boolean isScheduledForService) {
		this.isRentalCarScheduledForService = isScheduledForService;
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

	/**
	 * @return the rentalCar
	 */
	public boolean isRentalCar() {
		return rentalCar;
	}

	/**
	 * @param rentalCar the rentalCar to set
	 */
	public void setRentalCar(boolean rentalCar) {
		this.rentalCar = rentalCar;
	}


}
