package test.nz.org.take.r2ml.scenario1;

import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import nz.org.take.r2ml.DatatypeMapper;

public class MyDatatypeMapper implements DatatypeMapper {

	static Map<QName, Class> types = new HashMap<QName, Class>();

	public MyDatatypeMapper () {
		super();
		setType(new QName("Student"), Student.class);
		setType(new QName("Course"), Course.class);
		setType(new QName("College"), College.class);
	}
	
	public Class getType (QName key) {
		return types.get(key);
	}
	
	public void setType (QName key, Class value) {
		types.put(key, value);
	}
	
}
