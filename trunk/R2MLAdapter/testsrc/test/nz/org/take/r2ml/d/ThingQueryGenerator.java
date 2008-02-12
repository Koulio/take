package test.nz.org.take.r2ml.d;

import java.util.Collection;

import nz.org.take.KnowledgeBase;
import nz.org.take.Predicate;
import nz.org.take.Query;
import nz.org.take.r2ml.util.AbstractQueryGenerator;

public class ThingQueryGenerator extends AbstractQueryGenerator {

	public void generateQueries(KnowledgeBase kb) {
		
		Collection<Predicate> predicates = kb.getSupportedPredicates();
		System.out
				.println(kb.getElements().get(0).getPredicate().getName());
		Collection<Query> queries = buildAllQueries(predicates);
		// add queries
		for (Query query : queries) {
			if ((query.getPredicate().getName().equals("result") || query.getPredicate().getName().equals("value"))
					&& (!query.getInputParams()[0] || query.getInputParams()[1])) {
				;// ommit this query
			} else {
				System.out.println("Added query: " + query.toString());
				kb.add(query);
			}
		}
//		System.out.println("generated querries for " + predicates.size()
//				+ " predicates");

	}

}
