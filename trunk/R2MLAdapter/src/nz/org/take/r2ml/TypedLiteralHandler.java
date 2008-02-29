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
