package test.nz.org.take.r2ml.b;

import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import nz.org.take.r2ml.DatatypeMapper;

public class DefaultMapper implements DatatypeMapper {
	
	static Map<QName, Class> types = new HashMap<QName, Class>();

	public DefaultMapper () {
		super();
		setType(new QName("Person"), Person.class);
		setType(new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class);
	}
	
	public Class getType (QName key) {
		return types.get(key);
	}
	
	public void setType (QName key, Class value) {
		types.put(key, value);
	}
	
}
