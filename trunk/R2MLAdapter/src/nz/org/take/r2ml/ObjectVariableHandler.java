/*
 * Copyright 2007 Bastian Schenke Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */
package nz.org.take.r2ml;

import de.tu_cottbus.r2ml.ObjectVariable;
import nz.org.take.Variable;

class ObjectVariableHandler implements XmlTypeHandler {

	public Object importObject(Object obj) throws R2MLException {
		ObjectVariable oVar = (ObjectVariable) obj;
		R2MLDriver driver = R2MLDriver.get();
		MappingContext context = MappingContext.get();
		Variable var = context.getVariable(oVar.getName());
		if (oVar.getClassID() == null && var == null)
			throw new R2MLException("untyped object variable " + oVar.getName());
		if (var != null) {
			if (var.getType() == null) {
				Class type = driver.getDatatypeMapper().getType(
						oVar.getClassID());
				var.setType(type);
			}
			return var;
		}
		try {
			var = new Variable();
			var.setName(oVar.getName());
			Class type;
			try {
				type = driver.getDatatypeMapper().getType(
						oVar.getClassID());
			} catch (R2MLException e) {
				throw new R2MLException("unknown classid for variable " + oVar.getName() + ":" + oVar.getClassID()==null?"nothing":oVar.getClassID().toString(), e);
			}
			
			var.setType(type);
			if (driver.logger.isInfoEnabled()) {
				driver.logger.info("Create new Variable (" + var.getName()
						+ ":" + var.getType() + ").");
			}
		} catch (NullPointerException e) {
			String msg = "No Class specified for "
					+ oVar.getClassID().toString() + ".";
			throw new R2MLException(msg, e);
		}
		context.addVariable(oVar.getName(), var);
		return var;
	}

}
