package nz.org.take.r2ml;

public class ObjectNameHandler implements XmlTypeHandler {

	public Object importObject(Object obj, MappingContext context,
			R2MLDriver driver) throws R2MLException {
		// map ObjectName to Constant???
		throw new R2MLException("Feature not yet implemented.", R2MLException.FEATURE_NOT_YET_IMPLEMENTED);
	}

}
