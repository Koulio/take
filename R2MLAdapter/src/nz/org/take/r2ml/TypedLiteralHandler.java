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

import java.lang.reflect.Constructor;

import org.apache.log4j.Priority;

import de.tu_cottbus.r2ml.TypedLiteral;
import nz.org.take.Constant;

/**
 * 
 * @author Bastian Schenke (bastian.schenke@googlemail.com)
 * 
 */
class TypedLiteralHandler implements XmlTypeHandler {

	/**
	 * Maps a TypedLiteral to a Constant.
	 * 
	 * @param obj
	 *            a TypedLiteral
	 * @return Constant representing the literal
	 * 
	 * @see nz.org.take.r2ml.XmlTypeHandler#importObject(java.lang.Object,
	 *      nz.org.take.r2ml.R2MLDriver)
	 */
	public Object importObject(Object obj) throws R2MLException {
		Constant constant = new Constant();
		TypedLiteral tl = (TypedLiteral) obj;
		// System.out.println("TypedLiteral with type " + tl.getDatatypeID());
		R2MLDriver driver = R2MLDriver.get();
		try {
			constant.setType(driver.getDatatypeMapper().getType(
					tl.getDatatypeID()));
		} catch (RuntimeException e1) {
			// System.out.println("TypedLiteral with type " +
			// tl.getDatatypeID());
			e1.printStackTrace();
		}
		if (constant.getType() == String.class) {
			constant.setObject(tl.getLexicalValue());
			//return constant;
		} else {
			// constant.getType().isPrimitive() ||
			// else if (Number.class.isAssignableFrom(constant.getType()) ||
			// constant.getType() == Boolean.class) {
			// constant.setObject(tl.getLexicalValue());
			// return constant;
			// }
			// constant.setRef("_" + tl.getLexicalValue());
			try {
				Constructor constructor = constant.getType().getConstructor(
						new Class[] { String.class });
				Object value = constructor.newInstance(new Object[] { tl
						.getLexicalValue() });
				constant.setObject(value);
				if (driver.logger.isInfoEnabled()) {
					driver.logger.info("Create new Constant ("
							+ constant.getType() + ":<" + tl.getLexicalValue()
							+ ">)");
				}
			} catch (Exception e) {
				System.out.println("##################################");
				constant.setRef("_" + tl.getLexicalValue());
				System.out.println("##################################");
				// return constant;
			}
		}
		return constant;
	}

}
