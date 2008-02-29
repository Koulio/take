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
	 * @param obj
	 *            a Documentation object
	 * 
	 * @return a Map<String, String> containg all Annotations extracted from the Documentation object
	 * 
	 * @see nz.org.take.r2ml.XmlTypeHandler#importObject(java.lang.Object,
	 *      nz.org.take.r2ml.R2MLDriver)
	 */
	@SuppressWarnings("unchecked")
	public Object importObject(Object obj)
			throws R2MLException {
		Documentation documentation = (Documentation) obj;
		Map<String, String> docAnnotations = new HashMap<String, String>();
		for (JAXBElement<?> docElem : documentation.getRuleTextOrSourceCodeOrSubject()) {
			XmlTypeHandler handler = R2MLDriver.get().getHandlerByXmlType(docElem.getDeclaredType());
			Map<String, String> newAnnotations = (Map<String, String>) handler.importObject(docElem.getValue());
			docAnnotations.putAll(newAnnotations);
		}
			
		return docAnnotations;
	}

}
