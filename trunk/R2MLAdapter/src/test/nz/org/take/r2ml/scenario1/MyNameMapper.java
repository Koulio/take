package test.nz.org.take.r2ml.scenario1;

import javax.xml.namespace.QName;

import nz.org.take.r2ml.DefaultNameMapper;

public class MyNameMapper extends DefaultNameMapper {

	public MyNameMapper() {
		super();
		addSlotNames(new QName("isFather"), new String[] { "son", "father" });
		addSlotNames(new QName("lastname"), new String[] { "person", "lastname" });
	}

}
