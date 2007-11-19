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

import de.tu_cottbus.r2ml.PlainLiteral;
import de.tu_cottbus.r2ml.RuleSet;
import de.tu_cottbus.r2ml.RuleText;

/**
 * @author Bastian Schenke (bastian.schenke@googlemail.com)
 *
 */
public interface XmlTypeHandler {
	
	/**
	 * Name of the equals method of the targetlanguage. for instance <code>equals()</code> for Java 
	 */
	public static String EQUALITY_CHECK_METHOD= "equals";

	/**
	 *	Annotation key for the namespace of a QName identifying a variable.
	 */
	public static final String VARIABLE_QUALIFIED_NAME_NS = Variable.class.getCanonicalName() + ".qname.ns";
	/**
	 *	Annotation key for the local part of a QName identifying a variable. 
	 */
	//public static final String VARIABLE_QUALIFIED_NAME_LP = Variable.class.getCanonicalName() + ".qname.lp";
	public static final String PLAIN_LITERAL_LANGUAGE_TAG_KEY = PlainLiteral.class.getCanonicalName() + ".languageTag";
	public static final String RULE_SET_ID_KEY = RuleSet.class.getCanonicalName() + ".ruleSetId";

	public static final String RULE_TEXT_KEY = RuleText.class.getCanonicalName();
	public static final String RULE_TEXT_FORMAT_KEY = RuleText.class.getCanonicalName() + ".format";
	public static final String RULE_TEXT_DIAGRAM_KEY = RuleText.class.getCanonicalName() + ".diagram";
	public static final String RULE_TEXT_VOCABULARY_DIAGRAM_KEY = RuleText.class.getCanonicalName() + "vocabulary.diagram";
	
	/**
	 * @param obj
	 * @return
	 * @throws R2MLException
	 */
	Object importObject(Object obj) throws R2MLException;
	
}
