/*
 * Copyright (C) 2007 <A href="http://www-ist.massey.ac.nz/JBDietrich" target="_top">Jens Dietrich</a>
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


package nz.org.take;

import java.util.Collection;
import java.util.List;

/**
 * Interface for containers managing knowledge.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public interface KnowledgeBase {
	
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
