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
