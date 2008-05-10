/*
 * Copyright 2007 Bastian Schenke Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */
package nz.org.take.r2ml.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.namespace.QName;



import nz.org.take.r2ml.R2MLDriver;
import nz.org.take.r2ml.R2MLException;

import org.apache.commons.jxpath.JXPathContext;

import de.tu_cottbus.r2ml.ObjectClassificationAtom;
import de.tu_cottbus.r2ml.ObjectFactory;
import de.tu_cottbus.r2ml.ObjectVariable;
import de.tu_cottbus.r2ml.RuleBase;

/**
 * @author Bastian Schenke (bastian.schenke(at)googlemail.com)
 * 
 */
public class TypeVariablesFilter implements RuleBaseFilter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nz.org.take.strelka.RuleBaseFilter#repair(de.tu_cottbus.r2ml.RuleBase)
	 */
	public void repair(RuleBase ruleBase) throws R2MLException {

		
		JXPathContext context = JXPathContext.newContext(ruleBase);
		ObjectFactory of = new ObjectFactory();
		
		// search objectclassificationatoms that contain untyped variables
		context = JXPathContext.newContext(ruleBase);
		Iterator implicitTypedVars = context.iterate(
				"//qfAndOrNafNegFormula" +
				"[declaredType='class de.tu_cottbus.r2ml.ObjectClassificationAtom']" +
				"[value/objectTerm/declaredType='class de.tu_cottbus.r2ml.ObjectVariable']" +
				"[not(value/objectTerm/value/classID)]" +
				"/value");
		
		while (implicitTypedVars.hasNext()) {
			try {
				ObjectClassificationAtom classification = (ObjectClassificationAtom) implicitTypedVars.next();
				
				ObjectVariable var = (ObjectVariable) classification.getObjectTerm().getValue();
				ObjectVariable newVar = of.createObjectVariable();
				newVar.setName(var.getName());
				newVar.setTypeCategory(var.getTypeCategory());
				newVar.setClassID(classification.getClassID());
				classification.setObjectTerm(of.createObjectVariable(newVar));
			} catch (RuntimeException e) {
				if (R2MLDriver.get().logger.isDebugEnabled())
					R2MLDriver.get().logger.debug("Exception occured while typing all variables.", e);
				throw new R2MLException("Unable to type variables in rule base, abort rule import.");
			}
		}

		// search already typed variables and save them in a hash-map
		Iterator typedVar = context
				.iterate("//objectTerm[declaredType='class de.tu_cottbus.r2ml.ObjectVariable']/value[classID]");// ce.iterate(context);
		Map<String, QName> classID4name = new HashMap<String, QName>();
		while (typedVar.hasNext()) {
			try {
				ObjectVariable element = (ObjectVariable) typedVar.next();
				classID4name.put(element.getName(), element.getClassID());
			} catch (RuntimeException e) {
			}
		}

		// search all untyped variables and look up the types in the classId
		// hashmap
		Iterator untypedVar = context
				.iterate("//objectTerm[declaredType='class de.tu_cottbus.r2ml.ObjectVariable']/value[not(classID)]");
		while (untypedVar.hasNext()) {
			ObjectVariable element = (ObjectVariable) untypedVar.next();
			QName classID = classID4name.get(element.getName());
			if (classID != null) {
				element.setClassID(classID);
			}
		}

	}

	public String getName() {
		return this.getClass().getSimpleName();
	}

}
