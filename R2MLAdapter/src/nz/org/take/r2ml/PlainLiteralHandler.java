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
	 * @param driver
	 *            the used R2MLDriver
	 * @return Constant representing the literal
	 * 
	 * @see nz.org.take.r2ml.XmlTypeHandler#importObject(java.lang.Object,
	 *      nz.org.take.r2ml.R2MLDriver)
	 */
	public Object importObject(Object obj, MappingContext context, R2MLDriver driver)
			throws R2MLException {
		Constant constant = new Constant();
		PlainLiteral pl = (PlainLiteral)obj;
		constant.setObject(pl.getLexicalValue());
		constant.setType(String.class);
		constant.addAnnotation(PLAIN_LITERAL_LANGUAGE_TAG_KEY, pl.getLanguageTag());
		return constant;
	}

}
