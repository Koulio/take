/*
 * Copyright 2007 Bastian Schenke Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */
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

import nz.org.take.Variable;
import de.tu_cottbus.r2ml.DataVariable;

class DataVariableHandler implements XmlTypeHandler {

	public Object importObject(Object obj) throws R2MLException {
		DataVariable dVar = (DataVariable) obj;
		R2MLDriver driver = R2MLDriver.get();
		MappingContext context = MappingContext.get();
		Variable var = context.getVariable(dVar.getName());
		if (var != null)
			return var;
		try {
			if (driver.logger.isInfoEnabled()) {
				driver.logger.info("Create new Variable ("
						+ dVar.getName() + ":"
						+ driver.getDatatypeMapper().getType(dVar.getDatatypeID())
								 + ").");
			}
		} catch (RuntimeException e) {
			//throw new R2MLException("error while handling variable " + dVar.getName(), e);
		}
		var = new Variable();
		var.setName(dVar.getName());
		var.setType(driver.getDatatypeMapper().getType(dVar.getDatatypeID()));
		context.addVariable(var.getName(), var);
		return var;
	}

}
