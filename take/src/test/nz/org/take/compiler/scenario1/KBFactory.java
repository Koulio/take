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

package test.nz.org.take.compiler.scenario1;

import java.util.ArrayList;
import java.util.List;

import nz.org.take.DefaultKnowledgeBase;
import nz.org.take.KnowledgeBase;
import nz.org.take.Predicate;
import nz.org.take.compiler.Query;

/**
 * Generates a kb for this example.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */
public class KBFactory {

	private KnowledgeBase kb = null;
	private List<Query> queries = null;
	private Query query1, query2, query3, query4, query5/*, query6*/ = null;
	// predicates
	private Predicate predicate_is_father = null;
	private Predicate predicate_is_son = null;
	//private Predicate predicate_is_brother = null;
	private Predicate predicate_is_grandfather = null;
	private Predicate predicate_is_oncle = null;
	/**
	 * Constructor.
	 */
	public KBFactory() throws Exception {
		super();
		initPredicates();
	}

	/**
	 * Return the knowledge base for this example.
	 * @return a knowledge base
	 */
	public KnowledgeBase kb() {
		if (kb == null) {
			kb = buildKB();
		}
		return kb;
	}
	/**
	 * Return the query for this example.
	 * @return a list of queries 
	 */
	public List<Query> queries() {
		if (queries == null) {
			queries = new ArrayList<Query>();
			queries.add(query1());	
			queries.add(query2());	
			queries.add(query3());	
			queries.add(query4());
			queries.add(query5());
			//queries.add(query6());
		}
		return queries;
	}
	/**
	 * Get a query.
	 * @return a query
	 */
	public Query query1() {
		if (query1==null) {
			query1 = new Query(
				this.predicate_is_father,
				new boolean[]{true,false}
			);
		}
		return query1;
	}
	/**
	 * Get a query.
	 * @return a query
	 */
	public Query query2() {
		if (query2==null) {
			query2 = new Query(
				this.predicate_is_grandfather,
				new boolean[]{true,false}
			);
		}
		return query2;
	}
	/**
	 * Get a query.
	 * @return a query
	 */
	public Query query3() {
		if (query3==null) {
			query3 = new Query(
				this.predicate_is_father,
				new boolean[]{false,true}
			);
		}
		return query3;
	}
	/**
	 * Get a query.
	 * @return a query
	 */
	public Query query4() {
		if (query4==null) {
			query4 = new Query(
				this.predicate_is_grandfather,
				new boolean[]{false,true}
			);
		}
		return query4;
	}
	
	public Query query5() {
		if (query5 == null) {
			query5 = new Query(
					this.predicate_is_oncle,
					new boolean[]{false, true});
		}
		return query5;
	}

//	public Query query6() {
//		if (query6 == null) {
//			query6 = new Query(
//					this.predicate_is_oncle,
//					new boolean[]{true, false});
//		}
//		return query6;
//	}
	
	/**
	 * Build the knowledge base.
	 * @return a knowledge base
	 */
	protected KnowledgeBase buildKB() =
		String script =
			"var java.lang.String person1\n"+
			"var java.lang.String person2\n"+
			"var java.lang.String person3\n"+
			"var java.lang.String grandchild\n"+
			"var java.lang.String father\n"+
			"var java.lang.String grandfather\n"+
			"rule1: if father(person1,person2) then son(person2,person1)\n"+
			"rule2: if father(grandchild,father) and father(father,grandfather) then grandfather(grandchild,grandfather)\n"+
			"fact1: father, \"Frank\",\"Lutz\")\n"+
			"fact2: father, \"Guenther\", \"Otto\"))\n"+
			"fact3: father, \"Jens\", \"Klaus\"))\n"+
			"fact41: father,\"Lutz\", \"Otto\"))\n"+
			"fact5: father, \"Klaus\", \"Otto\"))\n"+		
			"fact6: father, \"Max\",\"Jens\"))\n"+
			"fact7: father,\"Ralf\", \"Lutz\"))\n"+
			"fact8: father,\"Werner\", \"Otto\"))\n";

		return kb;
	}

	/**
	 * Initialize the predicates.
	 * The is_father predicate is not initialized here, subclasses
	 * handle this differently.
	 */
	protected void initPredicates() {
		Class[] struct = { String.class, String.class };
//		predicate_is_brother = new SimplePredicate("is_brother_of", struct);
//		predicate_is_brother.setSlotNames(new String[]{"brother1","brother2"});
		predicate_is_oncle = new SimplePredicate("is_oncle_of", struct);
		predicate_is_oncle.setSlotNames(new String[]{"nephew","oncle"});
		predicate_is_son = new SimplePredicate("is_son_of", struct);
		predicate_is_son.setSlotNames(new String[]{"son","father"});
		predicate_is_grandfather = new SimplePredicate("is_grandfather_of", struct);
		predicate_is_grandfather.setSlotNames(new String[]{"grandchild","grandfather"});
		predicate_is_father = new SimplePredicate("is_father_of", struct);
		predicate_is_father.setSlotNames(new String[]{"son","father"});
	}




}
