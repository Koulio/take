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

import java.util.*;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;

/**
 * Default implementation of knowledge base.
 * Access to instance variables is synchronized, i.e. this class is thread safe.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class DefaultKnowledgeBase extends AbstractAnnotatable implements KnowledgeBase {
	private List<KnowledgeElement> elements = new ArrayList<KnowledgeElement>();
	private List<Query> queries = new ArrayList<Query>();
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
		return (List<KnowledgeElement>)CollectionUtils.select(this.elements,filter);
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
	/**
	 * Add a query.
	 * @param q a query
	 */
	public synchronized void add(Query q) {
		this.queries.add(q);
	}
	/**
	 * Remove  a query, return true if this succeeded.
	 * @param q  a query
	 * @return a boolean
	 */
	public synchronized  boolean remove(Query q) {
		return this.queries.remove(q);
	}
	/**
	 * Get all queries.
	 * @return queries
	 */
	public List<Query> getQueries() {
		return (List<Query>)ListUtils.unmodifiableList(this.queries) ;
	}
	public void accept(KnowledgeBaseVisitor visitor) {
		if (visitor.visit(this)) {
			for (Query q:queries)
				q.accept(visitor);
			for (KnowledgeElement e:elements)
				e.accept(visitor);
		}		
		visitor.endVisit(this);
	}
	/**
	 * Get a knowledge base.
	 * @return a knowledge base
	 */
	public KnowledgeBase getKnowledgeBase()  throws TakeException {
		return this;
	}
}
