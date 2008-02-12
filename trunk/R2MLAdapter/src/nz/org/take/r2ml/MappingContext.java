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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import javax.xml.namespace.QName;

import nz.org.take.Predicate;
import nz.org.take.SimplePredicate;
import nz.org.take.Variable;

/**
 * @author Bastian Schenke (bastian.schenke@googlemail.com)
 * 
 */
public class MappingContext {

	private static MappingContext singletonContext = null;

	private Map<QName, Variable> variables = new HashMap<QName, Variable>();

	private Map<QName, Predicate> predicates = new HashMap<QName, Predicate>();

	private Stack<XmlTypeHandler> ancestors = new Stack<XmlTypeHandler>();

	private boolean insideCondition = false;

	private Collection<String> propertyPredicateNames = new ArrayList<String>();

	/**
	 * Return the singleton instance of this MappingContext.
	 * 
	 * @return the Singleton - MappingContext
	 */
	public static MappingContext get() {
		if (singletonContext == null) {
			singletonContext = new MappingContext();
		}
		return singletonContext;
	}

	/**
	 * This method is only for debugging and test purposes.
	 * 
	 * @return
	 */
	public boolean isClean() {
		return (ancestors.size() == 0);
	}
	
	public static void reset() {
		singletonContext = null;
	}

	/**
	 * @param string
	 * @param variable
	 */
	void addVariable(String name, Variable variable) {
		addVariable(new QName("", name), variable);
	}

	void addVariable(QName name, Variable variable) {
		variables.put(name, variable);
	}

	/**
	 * @param localName
	 * @return the variable
	 */
	Variable getVariable(String localName) {
		return getVariable(new QName("", localName));
	}

	/**
	 * @param fullName a fully qualified xml name
	 * @return the variable
	 */
	Variable getVariable(QName fullName) {
		return variables.get(fullName);
	}

	/**
	 * @param predicate a new predicate
	 */
	void addPredicate(Predicate predicate) {
		addPredicate(new QName("", predicate.getName()), predicate);
	}

	void addPredicate(QName fullName, Predicate predicate) {
		predicates.put(fullName, predicate);
	}

	public Predicate getPredicate(String localName) {
		return getPredicate(new QName("", localName));
	}

	public Predicate getPredicate(QName fullName) {
		Predicate predicate = predicates.get(fullName);
		if (predicate == null)
			return null;
		if (isConclusion() && !(predicate instanceof SimplePredicate))
			return null;
		return predicate;
	}

	/**
	 * Each handler that may effect the way children are processed has to
	 * register. The policy is to unregister again after calling all children
	 * handler.
	 * 
	 * @param handler
	 *            the visiting handler
	 * @see void leave(XmlTypeHandler handler)
	 */
	void enter(XmlTypeHandler handler) {
		if (handler instanceof ConditionHandler) {
			this.insideCondition = true;
		}
		ancestors.push(handler);
	}

	/**
	 * Each handler that registers itself in the context has to unregister again
	 * after he finished the processing of its children.
	 * 
	 * @param handler
	 *            the leaving handler
	 * @throws R2MLException
	 *             if the handler is not the same as the one on top of the stack
	 *             (recursion not properly resolved)
	 */
	void leave(XmlTypeHandler handler) throws R2MLException {
		XmlTypeHandler current = ancestors.pop();
		if (!current.equals(handler)) {
			R2MLDriver.get().logger.debug("Recursion error! Last handler entering handler was \"" + current.getClass().getSimpleName() + "\" actual leavung handler is \"" + handler.getClass().getSimpleName() + "\"!");
			throw new R2MLException("Error while resolving recursion." + "Last handler entering handler was \"" + current.getClass().getSimpleName() + "\" actual leavung handler is \"" + handler.getClass().getSimpleName() + "\"!");
		}
		if (handler instanceof ConditionHandler) {
			insideCondition = false;
		}

	}

	public boolean isInsideCondition() {
		return insideCondition;
	}
	
	public boolean isCondition() {
		return ancestors.peek() instanceof ConditionHandler;
	}

	public boolean isConclusion() {
		return ancestors.peek() instanceof ConclusionHandler;
	}

	public void cleanUpToHandler(XmlTypeHandler handler) throws R2MLException {
		while (!ancestors.peek().equals(handler)) {
			leave(ancestors.peek());
		}
		leave(handler);
	}

	public Collection<String> getPropertyPredicateNames() {
		return propertyPredicateNames;
	}
	
	void addPredicatePropertyName(String name) {
		propertyPredicateNames.add(name);
	}

}
