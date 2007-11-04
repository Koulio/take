/**
 * Copyright 2007 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */


package nz.org.take;

import java.util.Collection;
import java.util.List;

/**
 * Interface for containers managing knowledge.
 * Knowledge bases are also knowlegde sources, the getKnowledgeBase  method should return <b>this</b>.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public interface KnowledgeBase extends Annotatable , Visitable, KnowledgeSource {
	
	/**
	 * Retrieve knowledge by id.
	 * @param id an id
	 * @return a knowledge element
	 */
	public KnowledgeElement getElement(String id) ;
	/**
	 * Retrieve knowledge by predicate.
	 * @param p the predicate
	 * @return knowledge elements
	 */
	public List<KnowledgeElement> getElements(Predicate p) ;
	
	/**
	 * Get all elements.
	 * @return knowledge elements
	 */
	public List<KnowledgeElement> getElements() ;
	/**
	 * Get all queries.
	 * @return queries
	 */
	public List<Query> getQueries() ;
	/**
	 * Get all predicates for which knowledge elements are available.
	 * @return predicates
	 */
	public Collection<Predicate> getSupportedPredicates() ;
	/**
	 * Add an element.
	 * @param e an element
	 */
	public void add(KnowledgeElement e) ;
	/**
	 * Remove an element, return true if this succeeded.
	 * @param e an element
	 * @return a boolean
	 */
	public boolean remove(KnowledgeElement e) ;
	/**
	 * Add a query.
	 * @param q a query
	 */
	public void add(Query q) ;
	/**
	 * Remove  a query, return true if this succeeded.
	 * @param q  a query
	 * @return a boolean
	 */
	public boolean remove(Query q) ;

}
