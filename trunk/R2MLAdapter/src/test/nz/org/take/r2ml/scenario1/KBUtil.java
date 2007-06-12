package test.nz.org.take.r2ml.scenario1;

import nz.org.take.Constant;
import nz.org.take.Fact;
import nz.org.take.KnowledgeBase;
import nz.org.take.Predicate;
import nz.org.take.Query;
import nz.org.take.SimplePredicate;
import nz.org.take.TakeException;
import nz.org.take.Term;
import nz.org.take.r2ml.R2MLKnowledgeSource;

public class KBUtil {

	static void addKnowledge(R2MLKnowledgeSource src) throws TakeException {

		Query query = new Query();

		KnowledgeBase kb = src.getKnowledgeBase();

		query.setPredicate(kb.getSupportedPredicates().iterator().next());
		query.setInputParams(new boolean[] { true, false });

		kb.add(query);

	}
}
