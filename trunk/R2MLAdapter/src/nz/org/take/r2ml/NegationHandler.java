package nz.org.take.r2ml;

public class NegationHandler implements XmlTypeHandler {

	public Object importObject(Object obj, MappingContext context, R2MLDriver driver)
			throws R2MLException {
		throw new R2MLException("Feature not yet implemented", R2MLException.FEATURE_NOT_YET_IMPLEMENTED);
	}

}
