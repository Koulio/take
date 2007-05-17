package nz.org.take.r2ml;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBElement;
import de.tu_cottbus.r2ml.Documentation;

/**
 * @author Bastian Schenke (bastian.schenke@googlemail.com)
 * 
 */
public class DocumentationHandler implements XmlTypeHandler {

	/**
	 * Map a Documentation object to a Map<String, String> of annotations (key,
	 * value)
	 * 
@XmlElementRefs
 @XmlElementRef(name = "SourceCode", namespace = "http://www.rewerse.net/I1/2006/R2ML", type = JAXBElement.class),
 @XmlElementRef(name = "title", namespace = "http://purl.org/dc/elements/1.1/", type = JAXBElement.class),
 @XmlElementRef(name = "type", namespace = "http://purl.org/dc/elements/1.1/", type = JAXBElement.class),
 @XmlElementRef(name = "creator", namespace = "http://purl.org/dc/elements/1.1/", type = JAXBElement.class),
 @XmlElementRef(name = "contributor", namespace = "http://purl.org/dc/elements/1.1/", type = JAXBElement.class),
 @XmlElementRef(name = "date", namespace = "http://purl.org/dc/elements/1.1/", type = JAXBElement.class),
 @XmlElementRef(name = "source", namespace = "http://purl.org/dc/elements/1.1/", type = JAXBElement.class),
 @XmlElementRef(name = "subject", namespace = "http://purl.org/dc/elements/1.1/", type = JAXBElement.class),
 @XmlElementRef(name = "RuleText", namespace = "http://www.rewerse.net/I1/2006/R2ML", type = JAXBElement.class),
 @XmlElementRef(name = "description", namespace = "http://purl.org/dc/elements/1.1/", type = JAXBElement.class)
    
     * TODO implement protected List<JAXBElement<?>> ruleTextOrSourceCodeOrSubject;
	 * 
	 * @param obj
	 *            a Documentation object
	 * 
	 * @param driver
	 *            the used R2MLDriver
	 * 
	 * @return a Map<String, String> containg all Annotations extracted from the Documentation object
	 * 
	 * @see nz.org.take.r2ml.XmlTypeHandler#importObject(java.lang.Object,
	 *      nz.org.take.r2ml.R2MLDriver)
	 */
	public Object importObject(Object obj, MappingContext context, R2MLDriver driver)
			throws R2MLException {
		Documentation documentation = (Documentation) obj;
		Map<String, String> docAnnotations = new HashMap<String, String>();
		for (JAXBElement<?> docElem : documentation.getRuleTextOrSourceCodeOrSubject()) {
			XmlTypeHandler handler = driver.getHandlerByXmlType(docElem.getDeclaredType());
			Map.Entry<String, String> newAnnotation = (Map.Entry<String, String>) handler.importObject(docElem.getValue(), context, driver);
			if ( ! docAnnotations.containsKey(newAnnotation.getKey())) {
				docAnnotations.put(newAnnotation.getKey(), newAnnotation.getValue());
			} else {
				//String value = docAnnotations.get
			}
		}
			
		return docAnnotations;
	}

}
