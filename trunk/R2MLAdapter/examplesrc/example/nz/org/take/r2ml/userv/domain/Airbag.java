package example.nz.org.take.r2ml.userv.domain;

public class Airbag {

	public static final int TYPE_AIRBAG = 0;

	public static final int TYPE_DRIVER = 1;

	public static final int TYPE_PASSENGER = 2;

	public static final int TYPE_SIDE = 3;

	boolean driverAirbag = false;

	boolean passengerAirbag = false;

	boolean sideAirbag = false;

	boolean airbag = true;

	public Airbag() {
		this(TYPE_AIRBAG);
	}

	public Airbag(int type) {
		switch (type) {
		case TYPE_AIRBAG:
			airbag = true;
			break;
		case TYPE_DRIVER:
			driverAirbag = true;
			break;
		case TYPE_PASSENGER:
			passengerAirbag = true;
			break;
		case TYPE_SIDE:
			sideAirbag = true;
			break;

		default:
			throw new IllegalArgumentException("Unknown airbag type");
		}
	}

	public boolean isDriverAirbag() {
		return driverAirbag;
	}

	public boolean isPassengerAirbag() {
		return passengerAirbag;
	}

	public boolean isSideAirbag() {
		return sideAirbag;
	}

	public boolean isAirbag() {
		return airbag;
	}
	
	@Override
	public String toString () {
		if (isDriverAirbag())
			return "DriverAirbag";
		else if (isPassengerAirbag())
			return "PassengerAirbag";
		else if (isSideAirbag())
			return "SideAirbag";
		
		return "Airbag";
	}

}
