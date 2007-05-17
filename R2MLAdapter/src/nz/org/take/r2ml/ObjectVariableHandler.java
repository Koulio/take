package nz.org.take.r2ml;

import de.tu_cottbus.r2ml.ObjectVariable;
import nz.org.take.Variable;

public class ObjectVariableHandler implements XmlTypeHandler {

	public Object importObject(Object obj, MappingContext context,
			R2MLDriver driver) throws R2MLException {
		ObjectVariable oVar = (ObjectVariable)obj;
		Variable var = context.getVariable(oVar.getName());
		if (var != null) {
			return var;
		}
		if (driver.logger.isInfoEnabled()) {
			driver.logger.info("Create new Variable ("
					+ oVar.getName() + ":"
					+ driver.getDatatypeMapper().getType(oVar.getClassID())
							.getSimpleName() + ").");
		}
		var = new Variable();
		var.setName(oVar.getName());
		var.setType(driver.getDatatypeMapper().getType(oVar.getClassID()));
		context.addVariable(oVar.getName(), var);
		return var;
	}

}
