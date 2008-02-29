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

import nz.org.take.r2ml.reference.SimpleAssociationResolvPolicy;
import de.tu_cottbus.r2ml.AssociationAtom;

/**
 * @author Bastian Schenke (bastian.schenke@googlemail.com)
 * 
 */
class AssociationAtomHandler implements XmlTypeHandler {

	/**
	 * Map a AssociationAtom to a PropertyPredicate.
	 * 
	 * @param obj
	 *            a
	 * @return a
	 * 
	 * @see nz.org.take.r2ml.XmlTypeHandler#importObject(java.lang.Object,
	 *      nz.org.take.r2ml.R2MLDriver)
	 */
	public Object importObject(Object obj) throws R2MLException {

		R2MLDriver driver = R2MLDriver.get();
		AssociationAtom atom = (AssociationAtom) obj;
		if (MappingContext.get().isConclusion()) {
			return new SimpleAssociationResolvPolicy().resolv(atom);
		} else {
			AssociationResolvPolicy policy = driver
					.getAssociationResolvPolicy();
			return policy.resolv(atom);
		}
	}

}
