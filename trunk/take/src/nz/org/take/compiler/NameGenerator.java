/**
 * Copyright 2007 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */

package nz.org.take.compiler;

import nz.org.take.AggregationFunction;
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
	 * Generate a method name for an aggregation function
	 * @param f an aggregation function
	 * @return a method name
	 */
	public String getMethodName(AggregationFunction f);
	
	
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
	 * Get the (local, without package name) name of the registry class for the expressions.
	 * @return
	 */
	public String getExpressionRegistryClassName();
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
	/**
	 * Reset cached information. Thsi method should be called before reusing name generators.
	 */
	public void reset();
	
	

}
