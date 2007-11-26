package example.nz.org.take.r2ml.eurent.domain;

import nz.org.take.r2ml.reference.DefaultDatatypeMapper;

public class EURentDatatypeMapper extends DefaultDatatypeMapper {

	/**
	 * 
	 */
	public EURentDatatypeMapper() {
		super();
		setType("Rental", Rental.class);
		setType("RentalCar", RentalCar.class);
		setType("RentalCarScheduledForService", RentalCar.class);
		setType("Branch", Branch.class);
	}

}
