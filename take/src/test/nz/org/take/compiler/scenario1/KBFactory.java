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
	protected KnowledgeBase buildKB() {
		kb = new DefaultKnowledgeBase();
		// add rules
		DerivationRule rule1 = new /**
		 * Knowledge iterator. The additional close method can be used to release resources,
		 * e.g. database connections.
		 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
		 */ lfs.rule(
				lfs.fact(predicate_is_father, lfs.variable("person 1"), lfs.variable("person 2")), 
				lfs.fact(predicate_is_son, lfs.variable("person 2"), lfs.variable("person 1")));
		kb.add(rule1);
		Rule rule2 =
			lfs.rule(
				lfs.fact(predicate_is_father, lfs.variable("grandchild"), lfs.variable("father")),
				lfs.fact(predicate_is_father, lfs.variable("father"), lfs.variable("grandfather")),
				lfs.fact(predicate_is_grandfather, lfs.variable("grandchild"), lfs.variable("grandfather")));
		kb.add(rule2);
//		Rule rule3 =
//			lfs.rule(
//				lfs.fact(predicate_is_father, lfs.variable("person 1"), lfs.variable("person 3")),
//				lfs.fact(predicate_is_father, lfs.variable("person 2"), lfs.variable("person 3")),
//				lfs.fact(org.mandarax.lib.text.StringArithmetic.NOT_EQUAL, lfs.variable("person 1"), lfs.variable("person 2")),
//				lfs.fact(predicate_is_brother, lfs.variable("person 1"), lfs.variable("person 2")));
//		kb.add(rule3);
//		Rule rule4 =
//			lfs.rule(
//				lfs.fact(predicate_is_father, lfs.variable("person 1"), lfs.variable("person 2")),
//				lfs.fact(predicate_is_brother, lfs.variable("person 2"), lfs.variable("person 3")),
//				lfs.fact(predicate_is_oncle, lfs.variable("person 1"), lfs.variable("person 3")));
//		kb.add(rule4);
		
		kb.add(lfs.fact(predicate_is_father, "Frank", "Lutz"));
		kb.add(lfs.fact(predicate_is_father, "Guenther", "Otto"));
		kb.add(lfs.fact(predicate_is_father, "Jens", "Klaus"));
		kb.add(lfs.fact(predicate_is_father, "Lutz", "Otto"));
		kb.add(lfs.fact(predicate_is_father, "Klaus", "Otto"));		
		kb.add(lfs.fact(predicate_is_father, "Max", "Jens"));
		kb.add(lfs.fact(predicate_is_father, "Ralf", "Lutz"));
		kb.add(lfs.fact(predicate_is_father, "Werner", "Otto"));

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
