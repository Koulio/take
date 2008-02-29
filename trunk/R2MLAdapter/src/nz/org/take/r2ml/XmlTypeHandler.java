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
