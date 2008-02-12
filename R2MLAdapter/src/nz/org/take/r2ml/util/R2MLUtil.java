/**
 * 
 */
package nz.org.take.r2ml.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import de.tu_cottbus.r2ml.Atom;
import de.tu_cottbus.r2ml.EqualityAtom;
import de.tu_cottbus.r2ml.GenericAtom;
import de.tu_cottbus.r2ml.InequalityAtom;
import de.tu_cottbus.r2ml.QfConjunction;

import nz.org.take.Fact;
import nz.org.take.Prerequisite;
import nz.org.take.Term;
import nz.org.take.r2ml.MappingContext;
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

}
