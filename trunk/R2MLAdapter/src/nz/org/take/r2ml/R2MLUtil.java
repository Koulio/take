/**
 * 
 */
package nz.org.take.r2ml;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import de.tu_cottbus.r2ml.EqualityAtom;
import de.tu_cottbus.r2ml.QfAndOrNafNegFormula;
import de.tu_cottbus.r2ml.QfConjunction;

import nz.org.take.Fact;
import nz.org.take.Prerequisite;
import nz.org.take.Term;
import nz.org.take.script.ScriptException;

/**
 * @author Bastian Schenke (bastian.schenke@googlemail.com)
 * 
 */
class R2MLUtil {

	static Prerequisite factAsPrerequisite(Fact fact) {

		if (fact instanceof Prerequisite)
			return (Prerequisite) fact;
		Prerequisite prereq = new Prerequisite();
		prereq.addAnnotations(fact.getAnnotations());
		prereq.setId(fact.getId());
		prereq.setPredicate(fact.getPredicate());
		prereq.setTerms(fact.getTerms());
		return prereq;

	}

	static Fact PrerequisiteAsFact(Prerequisite prereq) throws R2MLException {
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
	static Method getMethod(String name, Term[] terms)
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
		
		return true 
			&& (formula instanceof QfConjunction);
	}

	public static boolean returnsListOfFacts(Object formula) {
		return true
			&& (formula instanceof EqualityAtom);
	}
	
	public static boolean returnsFact(Object formula) {
		return true;
	}
}
