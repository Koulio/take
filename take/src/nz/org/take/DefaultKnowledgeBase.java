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

import java.util.*;
import org.apache.commons.collections.ListUtils;

/**
 * Default implementation of knowledge base.
 * Access to instance variables is synchronized, i.e. this class is thread safe.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class DefaultKnowledgeBase implements KnowledgeBase {
	private List<KnowledgeElement> elements = new ArrayList<KnowledgeElement>();
	private Map<String,KnowledgeElement> elementsById = new HashMap<String,KnowledgeElement>();
	/**
	 * Retrieve knowledge by id.
	 * @param id an id
	 * @return a knowledge element
	 */
	public synchronized KnowledgeElement getElement(String id) {
		return this.elementsById.get(id);
	}
	/**
	 * Retrieve knowledge by predicate.
	 * @param p the predicate
	 * @return knowledge elements
	 */
	public synchronized List<KnowledgeElement> getElements(final Predicate p) {
		org.apache.commons.collections.Predicate filter = new org.apache.commons.collections.Predicate() {
			public boolean evaluate(Object arg) {
				KnowledgeElement e = (KnowledgeElement)arg;
				return p.equals(e.getPredicate());
			}
		};
		return (List<KnowledgeElement>)ListUtils.predicatedList(this.elements,filter);
	}
	
	/**
	 * Get all elements.
	 * @return knowledge elements
	 */
	public synchronized List<KnowledgeElement> getElements() {
		return (List<KnowledgeElement>)ListUtils.unmodifiableList(this.elements) ;
	}
	/**
	 * Get all predicates for which knowledge elements are available.
	 * @return predicates
	 */
	public synchronized Collection<Predicate> getSupportedPredicates() {
		Set<Predicate> predicates = new HashSet<Predicate>();
		for (KnowledgeElement e:this.elements) {
			predicates.add(e.getPredicate());
		}
		return predicates;
	}
	/**
	 * Add an element.
	 * @param e an element
	 */
	public synchronized void add(KnowledgeElement e) {
		this.elements.add(e);
		this.elementsById.put(e.getId(),e);
	}
	/**
	 * Remove an element, return true if this succeeded.
	 * @param e an element
	 * @return a boolean
	 */
	public synchronized boolean remove(KnowledgeElement e) {
		boolean result = this.elements.remove(e);
		result = result && (null!=this.elementsById.remove(e.getId()));
		return result;
	}
	
}
