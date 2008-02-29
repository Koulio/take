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

import nz.org.take.Constant;
import de.tu_cottbus.r2ml.PlainLiteral;

/**
 * @author Bastian Schenke (bastian.schenke@googlemail.com)
 *
 */
class PlainLiteralHandler implements XmlTypeHandler {

	/**
	 * Maps a PlainLiteral to a Constant.
	 * 
	 * TODO implement protected String languageTag;
	 * TODO implement protected String typeCategory;
	 * 
	 * @param obj
	 *            a PlainLiteral
	 * @return Constant representing the literal
	 * 
	 * @see nz.org.take.r2ml.XmlTypeHandler#importObject(java.lang.Object,
	 *      nz.org.take.r2ml.R2MLDriver)
	 */
	public Object importObject(Object obj)
			throws R2MLException {
		Constant constant = new Constant();
		PlainLiteral pl = (PlainLiteral)obj;
		constant.setObject(pl.getLexicalValue());
		constant.setType(String.class);
		constant.addAnnotation(PLAIN_LITERAL_LANGUAGE_TAG_KEY, pl.getLanguageTag());
		return constant;
	}

}
