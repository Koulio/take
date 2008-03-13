package example.nz.org.take.r2ml.userv.domain;

import java.util.Collection;

import nz.org.take.KnowledgeBase;
import nz.org.take.Predicate;
import nz.org.take.Query;
import nz.org.take.r2ml.util.AbstractQueryGenerator;

public class UServQueryGenerator extends AbstractQueryGenerator {
	
	public UServQueryGenerator () {
		System.out.println("UServQueryGenerator loaded.");
	}

	public void generateQueries(KnowledgeBase kb) {
		Collection<Predicate> predicates = kb.getSupportedPredicates();
		Collection<Query> queries = buildAllQueries(predicates);
		// add queries
		for (Query query : queries) {
			if (!query.getInputParams()[0] || (query.getInputParams().length > 1 && query.getInputParams()[1]) ) {
				;// ommit this query
			} else {
				kb.add(query);
			}
		}
//		System.out.println("generated querries for " + predicates.size()
//				+ " predicates");
	}

}