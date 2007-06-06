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

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBElement;
import de.tu_cottbus.r2ml.Documentation;

/**
 * @author Bastian Schenke (bastian.schenke@googlemail.com)
 * 
 */
class DocumentationHandler implements XmlTypeHandler {

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
	@SuppressWarnings("unchecked")
	public Object importObject(Object obj, MappingContext context, R2MLDriver driver)
			throws R2MLException {
		Documentation documentation = (Documentation) obj;
		Map<String, String> docAnnotations = new HashMap<String, String>();
		for (JAXBElement<?> docElem : documentation.getRuleTextOrSourceCodeOrSubject()) {
			XmlTypeHandler handler = driver.getHandlerByXmlType(docElem.getDeclaredType());
			Map<String, String> newAnnotations = (Map<String, String>) handler.importObject(docElem.getValue(), context, driver);
			docAnnotations.putAll(newAnnotations);
		}
			
		return docAnnotations;
	}

}
