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

import nz.org.take.Fact;
import de.tu_cottbus.r2ml.Atom;
import de.tu_cottbus.r2ml.Conclusion;

/**
 * @author Bastian Schenke (bastian.schenke(at)googlemail.com)
 *
 */
public class ConclusionHandler implements XmlTypeHandler {

	/**
	 * @param Object obj a Conclusion object
	 * 
	 * @return a Fact
	 */
	public Object importObject(Object obj) throws R2MLException {
		MappingContext.get().enter(this);
		Fact conclusionFact = null;
		try {
			Atom atom = ((Conclusion) obj).getAtom().getValue();
			XmlTypeHandler handler = R2MLDriver.get().getHandlerByXmlType(atom.getClass());
			conclusionFact = (Fact) handler.importObject(atom);
		} catch (R2MLException e) {
			e.printStackTrace();
		}
		MappingContext.get().leave(this);
		return conclusionFact;
	}

}
