/*
 * Copyright (C) 2007 Bastian Schenke (bastian.schenke(at)gmail.com) and 
 * <a href="http://www-ist.massey.ac.nz/JBDietrich" target="_top">Jens Dietrich</a>
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
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
