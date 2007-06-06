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
	 * @param driver
	 *            the used R2MLDriver
	 * @return a Map<String, String> containing all informations given by the RuleText element
	 * 
	 * @see nz.org.take.r2ml.XmlTypeHandler#importObject(java.lang.Object,
	 *      nz.org.take.r2ml.R2MLDriver)
	 */
	public Object importObject(Object obj, MappingContext context,
			R2MLDriver driver) throws R2MLException {
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
