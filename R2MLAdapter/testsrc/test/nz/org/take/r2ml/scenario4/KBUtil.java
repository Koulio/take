package test.nz.org.take.r2ml.scenario4;

import nz.org.take.KnowledgeBase;
import nz.org.take.Predicate;
import nz.org.take.Query;
import nz.org.take.SimplePredicate;
import nz.org.take.TakeException;
import nz.org.take.r2ml.R2MLKnowledgeSource;

public class KBUtil {

	static void addQuerries(R2MLKnowledgeSource src) throws TakeException {

		Query query1 = new Query();
		Query query2 = new Query();

		// there is only one predicate that
		KnowledgeBase kb = src.getKnowledgeBase();
		Predicate predicate = kb.getSupportedPredicates().iterator().next();
		
		query1.setPredicate(predicate);
		query2.setPredicate(predicate);
		query1.setInputParams(new boolean[] { true, true });
		query2.setInputParams(new boolean[] { false, true });

		kb.add(query1);
		kb.add(query2);

	}
}
