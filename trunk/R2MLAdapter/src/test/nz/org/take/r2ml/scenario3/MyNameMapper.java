package test.nz.org.take.r2ml.scenario3;

import javax.xml.namespace.QName;

import nz.org.take.r2ml.DefaultNameMapper;

public class MyNameMapper extends DefaultNameMapper {

	public MyNameMapper() {
		super();
		addSlotNames(new QName("isFather"), new String[] { "son", "father" });
		addSlotNames(new QName("isGrandfather"), new String[] { "grandson", "grandfather" });
	}

}
