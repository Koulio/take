package test.nz.org.take.r2ml.scenario2;

import javax.xml.namespace.QName;

import nz.org.take.r2ml.DefaultNameMapper;

public class MyNameMapper extends DefaultNameMapper {

	public MyNameMapper() {
		super();
		addSlotNames(new QName("isEnrolled"), new String[] { "student", "college" });
	}

}
