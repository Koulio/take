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

import java.util.HashMap;
import java.util.Map;

import de.tu_cottbus.r2ml.RuleText;

/**
 * @author Bastian Schenke (bastian.schenke@googlemail.com)
 *
 */
public class RuleTextHandler implements XmlTypeHandler {

	/**
	 * Maps a RuleText to a MapEntry for use as an annotation.
	 * 
	 * @param obj
	 *            a RuleText
	 * @return a Map<String, String> containing all informations given by the RuleText element
	 * 
	 * @see nz.org.take.r2ml.XmlTypeHandler#importObject(java.lang.Object,
	 *      nz.org.take.r2ml.R2MLDriver)
	 */
	public Object importObject(Object obj) throws R2MLException {
		RuleText text = (RuleText)obj;
		Map<String, String> annotations = new HashMap<String, String>();
		if (text.getValue() != null)
			annotations.put(RULE_TEXT_KEY, text.getValue());
		if (text.getTextFormat() != null)
			annotations.put(RULE_TEXT_FORMAT_KEY, text.getTextFormat());
		if (text.getRuleDiagram() != null)
			annotations.put(RULE_TEXT_DIAGRAM_KEY, text.getRuleDiagram());
		if (text.getRuleVocabularyDiagram() != null)
			annotations.put(RULE_TEXT_VOCABULARY_DIAGRAM_KEY, text
					.getRuleVocabularyDiagram());
		return annotations;
	}

}
