package nz.org.take.r2ml;

import nz.org.take.Variable;
import de.tu_cottbus.r2ml.DataVariable;

public class DataVariableHandler implements XmlTypeHandler {

	public Object importObject(Object obj, MappingContext context,
			R2MLDriver driver) throws R2MLException {
		DataVariable dVar = (DataVariable) obj;
		Variable var = context.getVariable(dVar.getName());
		if (var != null) {
			return var;
		}
		if (driver.logger.isInfoEnabled()) {
			driver.logger.info("Create new Variable ("
					+ dVar.getName() + ":"
					+ driver.getDatatypeMapper().getType(dVar.getDatatypeID())
							.getSimpleName() + ").");
		}
		var = new Variable();
		var.setName(dVar.getName());
		var.setType(driver.getDatatypeMapper().getType(dVar.getDatatypeID()));
		context.addVariable(var.getName(), var);
		return var;
	}

}
