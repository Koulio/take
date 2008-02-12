package test.nz.org.take.r2ml.d;

import nz.org.take.r2ml.DatatypeMapper;
import nz.org.take.r2ml.reference.DefaultDatatypeMapper;

public class ThingMapper extends DefaultDatatypeMapper implements
		DatatypeMapper {

	public ThingMapper() {
		super();
		this.setType("Thing", Thing.class);
	}
}
