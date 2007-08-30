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
import nz.org.take.Query;

/**
 * Service used to generate the names for artifacts (classes,methods)
 * representing artefacts in the kb (predicates, facts, rules, ..).
 * 
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */
public interface NameGenerator {
	/**
	 * Generate a class name.
	 * @param p  a predicate
	 * @return a name
	 */
	public String getClassName(Predicate p);

	/**
	 * Generate a method name for a query
	 * @param q a query
	 * @return a method name
	 */
	public String getMethodName(Query q);
	
	/**
	 * Generate the name of the class that has the methods
	 * generated for a query
	 * @param q a query
	 * @return a class name
	 */
	public String getKBFragementName(Query q);

	/**
	 * Generate an accessor name for a slot.
	 * @param p a predicate
	 * @param slot a slot position
	 * @return a name
	 */
	public String getAccessorNameForSlot(Predicate p, int slot);

	/**
	 * Generate a mutator name for a slot.
	 * @param p a predicate
	 * @param slot a slot position
	 * @return a name
	 */
	public String getMutatorNameForSlot(Predicate p, int slot);

	/**
	 * Generate a variable name for a slot.
	 * @param p a predicate
	 * @param slot a slot position
	 * @return a name
	 */
	public String getVariableNameForSlot(Predicate p, int slot);
	/**
	 * Get the (local, without package name) name of the registry class for the aggregation functions.
	 * @return
	 */
	public String getAggregationFunctionsRegistryClassName();
	/**
	 * Get the (local, without package name) name of the registry class for the constants.
	 * @return
	 */
	public String getConstantRegistryClassName();
	/**
	 * Get the (local, without package name) name of the registry class for fact stores.
	 * @return
	 */
	public String getFactStoreRegistryClassName();
	

}
