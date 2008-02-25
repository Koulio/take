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
