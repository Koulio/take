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

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import de.tu_cottbus.r2ml.Atom;
import de.tu_cottbus.r2ml.AttributeFunctionTerm;
import de.tu_cottbus.r2ml.DataTerm;
import de.tu_cottbus.r2ml.DatatypePredicateAtom;
import de.tu_cottbus.r2ml.EqualityAtom;
import de.tu_cottbus.r2ml.GenericAtom;
import de.tu_cottbus.r2ml.InequalityAtom;
import de.tu_cottbus.r2ml.QfConjunction;
import de.tu_cottbus.r2ml.TypedLiteral;
import de.tu_cottbus.r2ml.r2mlv.Property;
import de.tu_cottbus.r2ml.r2mlv.VocabularyEntry;

import nz.org.take.Fact;
import nz.org.take.Prerequisite;
import nz.org.take.Term;
import nz.org.take.r2ml.MappingContext;
import nz.org.take.r2ml.R2MLDriver;
import nz.org.take.r2ml.R2MLException;

/**
 * @author Bastian Schenke (bastian.schenke@googlemail.com)
 * 
 */
public class R2MLUtil {

	public static Fact newFact() {
		return MappingContext.get().isInsideCondition() ? new Prerequisite()
				: new Fact();

	}

	public static Prerequisite factAsPrerequisite(Fact fact) {

		if (fact instanceof Prerequisite)
			return (Prerequisite) fact;
		Prerequisite prereq = new Prerequisite();
		prereq.addAnnotations(fact.getAnnotations());
		prereq.setId(fact.getId());
		prereq.setPredicate(fact.getPredicate());
		prereq.setTerms(fact.getTerms());
		return prereq;

	}

	public static Fact PrerequisiteAsFact(Prerequisite prereq)
			throws R2MLException {
		Fact fact = new Fact();
		fact.setId(prereq.getId());
		fact.setPredicate(prereq.getPredicate());
		fact.setTerms(prereq.getTerms());
		return fact;
	}

	/**
	 * @param name
	 * @param terms
	 * @return
	 * @throws R2MLException
	 */
	public static Method getMethod(String name, Term[] terms)
			throws R2MLException {
		Class<?> clazz = terms[0].getType();
		Class[] paramTypes = new Class[terms.length - 1];
		Method m = null;

		// get parameter
		for (int i = 1; i < terms.length; i++) {
			paramTypes[i - 1] = terms[i].getType();
		}
		// find method
		try {
			m = clazz.getMethod(name, paramTypes);
		} catch (Exception x) {
		}
		if (m == null) {
			// start investigating supertypes
			Method[] methods = clazz.getMethods();
			for (Method m1 : methods) {
				if (m1.getReturnType() == Boolean.TYPE
						&& Modifier.isPublic(m1.getModifiers())) {
					if (m1.getName().equals(name)
							&& m1.getParameterTypes().length == paramTypes.length) {
						// check types
						boolean ok = true;
						for (int i = 0; i < paramTypes.length; i++) {
							ok = ok
									&& m1.getParameterTypes()[i]
											.isAssignableFrom(paramTypes[i]);
						}
						if (ok) {
							m = m1;
							break;
						}
					}
				}
			}
		}
		return m;

	}

	public static boolean returnsListOfPrerequisites(Object formula) {
		if (formula instanceof QfConjunction)
			return true;
		else
			return false;
	}

	public static boolean returnsListOfFacts(Object formula) {
		if (formula instanceof EqualityAtom)
			return true;
		else if (formula instanceof EqualityAtom)
			return true;
		else if (formula instanceof InequalityAtom)
			return true;
		else
			return false;
	}

	public static boolean returnsFact(Object formula) {
		if (formula instanceof GenericAtom)
			return true;
		return false;
	}

	public static PropertyDescriptor buildProperty(String name, Class clazz)
			throws R2MLException {
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
			PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : properties) {
				if (name.equals(property.getName())
						&& property.getReadMethod() != null) {
					return property;
				}
			}
		} catch (Exception e) {
		}
		throw new R2MLException("No java property found for property " + name
				+ " in class " + clazz.getCanonicalName());
	}

	public static boolean isNegated(Atom atom) {
		return atom.isIsNegated() == null ? false : atom.isIsNegated();
	}

	/**
	 * Checks if the given DatatypePredicateAtom contains an
	 * AttributioFunctionTerm and a TypedLiteral. STRELKA
	 * 
	 * @param atom
	 * @return true - if the atom contains a boolean TypedLiteral and an
	 *         AttributionFunctionTerm as DataArguments and is an equal or
	 *         notequal comparison, false - otherwise
	 */
	public static boolean isBooleanPredicate(DatatypePredicateAtom atom) {
		try {
			String symbol = DataPredicates.getComparisonSymbol(atom
					.getDatatypePredicateID());
			// equal or unequal?
			if ("==".equals(symbol) || "!=".equals(symbol)) {
				JAXBElement<? extends DataTerm> arg0 = atom.getDataArguments()
						.getDataTerm().get(0);
				JAXBElement<? extends DataTerm> arg1 = atom.getDataArguments()
						.getDataTerm().get(1);
				TypedLiteral tl = null;
				
				// a TypedLiteral and an AttributeFunctionTerm as Arguments?
				if (arg0.getDeclaredType() == TypedLiteral.class
						&& arg1.getDeclaredType() == AttributeFunctionTerm.class) {
					tl = (TypedLiteral) arg0.getValue();
					
				} else if (arg1.getDeclaredType() == TypedLiteral.class
						&& arg0.getDeclaredType() == AttributeFunctionTerm.class) {
					tl = (TypedLiteral) arg1.getValue();
				}
				
				Class type = R2MLDriver.get().getDatatypeMapper().getType(
						tl.getDatatypeID());
				if (Boolean.class == type || Boolean.TYPE == type) {
					return true;
				}
			}

		} catch (Exception e) {
			//e.printStackTrace();
			return false;
		}
		return false;
	}

	/**
	 * @param propId
	 * @throws R2MLException 
	 */
	public static QName getPropertyType(QName propId) throws R2MLException {
		try {
		for(JAXBElement<? extends VocabularyEntry> vocEntry : R2MLDriver.get().getRuleBase().getVocabulary().getVocabularyEntry()) {
			if (vocEntry.getDeclaredType().equals(de.tu_cottbus.r2ml.r2mlv.Class.class))
				for (Property prop : ((de.tu_cottbus.r2ml.r2mlv.Class)(vocEntry.getValue())).getAttributeOrReferenceProperty()) {
					if (prop.getID().equals(propId)) {
						return prop.getRange().getDatatype().getValue().getID();
					}
				}
		}
		} catch (NullPointerException e) {
			//throw new R2MLException("Unable to gather type for property " + propId.toString());
		}
		throw new R2MLException("Unable to gather type for property " + propId.toString());
	}

}
