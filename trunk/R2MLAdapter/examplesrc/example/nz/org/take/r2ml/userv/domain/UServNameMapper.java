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
		this.addSlotNames("premium", new String[] { "policy", "premium" });
		this.addSlotNames("DriverAirbag", new String[] { "airbag" });
	}
}
