/*
 * Copyright (C) 2006 <A href="http://www-ist.massey.ac.nz/JBDietrich" target="_top">Jens Dietrich</a>
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

package nz.org.take.compiler;

import nz.org.take.Predicate;
import nz.org.take.script.Rule;


/**
 * Service used to generate the names for artifacts (classes,methods)
 * representing artefacts in the kb (predicates, facts, rules, ..).
 * 
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 * @version 0.1
 */
public interface NameGenerator {
	/**
	 * Generate a class name.
	 * 
	 * @param p
	 *            a predicate
	 * @return a name
	 */
	public String getClassName(Predicate p);

	/**
	 * Generate a method name fo a predicate with given input parameter
	 * 
	 * @param p
	 *            a predicate
	 * @param inputParam
	 *            indicates whether the slots are in or output slots
	 * @return a method name
	 */
	public String getMethodName(Predicate p, boolean[] inputParam);

	/**
	 * Generate an accessor name for a slot.
	 * 
	 * @param p
	 *            a predicate
	 * @param slot
	 *            a slot position
	 * @return a name
	 */
	public String getAccessorNameForSlot(Predicate p, int slot);

	/**
	 * Generate a mutator name for a slot.
	 * 
	 * @param p
	 *            a predicate
	 * @param slot
	 *            a slot position
	 * @return a name
	 */
	public String getMutatorNameForSlot(Predicate p, int slot);

	/**
	 * Generate a variable name for a slot.
	 * 
	 * @param p
	 *            a predicate
	 * @param slot
	 *            a slot position
	 * @return a name
	 */
	public String getVariableNameForSlot(Predicate p, int slot);

	/**
	 * Get the class name for the bindings used in the method generated for a
	 * rule.
	 * 
	 * @param r
	 *            a rule
	 * @return the name of the binding class for the rule
	 */
	public String getBindingClassName(Rule r);
}
