package nz.org.take.r2ml;

import de.tu_cottbus.r2ml.Subject;

public class SubjectHandler implements XmlTypeHandler {

	public Object importObject(Object obj, MappingContext context,
			R2MLDriver driver) throws R2MLException {
		Subject sub = (Subject)obj;
		XmlTypeHandler handler = driver.getHandlerByXmlType(sub.getObjectTerm().getValue().getClass());
		
		return handler.importObject(sub.getObjectTerm().getValue(), context, driver);
	}

}
