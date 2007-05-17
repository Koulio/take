package nz.org.take.r2ml;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import javax.xml.namespace.QName;

import nz.org.take.Predicate;
import nz.org.take.Variable;

/**
 * @author Bastian Schenke (bastian.schenke@googlemail.com)
 * 
 */
public class MappingContext {

	private Map<String, Variable> variables = new HashMap<String, Variable>();
	private Map<String, Predicate> predicates = new HashMap<String, Predicate>();

	private Stack<XmlTypeHandler> ancestors = new Stack<XmlTypeHandler>();

	/**
	 * This method is only for debugging and test purposes.
	 * @return
	 */
	public boolean isClean() {
		return (ancestors.size() == 0);
	}
	
	/**
	 * 
	 */
	void cleanContext() {
		variables = new HashMap<String, Variable>();
	}

	/**
	 * @param string
	 * @param variable
	 */
	void addVariable(String name, Variable variable) {
		variables.put(name, variable);
	}

	/**
	 * @param name
	 * @return
	 */
	Variable getVariable(String name) {
		return variables.get(name);
	}

	void addPredicate(Predicate predicate) {
		predicates.put(predicate.getName(), predicate);
	}
	
	Predicate getPredicate(String name) {
		return predicates.get(name);
		
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
		if (!ancestors.pop().equals(handler)) {
			throw new R2MLException("Error while resolving recursion.");
		}
	}

}
