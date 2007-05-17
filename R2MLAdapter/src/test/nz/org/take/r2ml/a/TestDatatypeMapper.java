package test.nz.org.take.r2ml.a;

import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import test.nz.org.take.r2ml.b.Person;

import nz.org.take.r2ml.DatatypeMapper;

public class TestDatatypeMapper implements DatatypeMapper {
	
	static Map<QName, Class> types = new HashMap<QName, Class>();

	public TestDatatypeMapper () {
		super();
	}
	
	public Class getType (QName key) {
		return types.get(key);
	}
	
	public void setType (QName key, Class value) {
		types.put(key, value);
	}
	
}
