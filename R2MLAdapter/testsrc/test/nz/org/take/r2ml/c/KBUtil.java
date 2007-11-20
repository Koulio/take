package test.nz.org.take.r2ml.c;

import nz.org.take.KnowledgeBase;
import nz.org.take.Query;
import nz.org.take.TakeException;
import nz.org.take.r2ml.R2MLKnowledgeSource;

public class KBUtil {

	static void addQuerries(R2MLKnowledgeSource src) throws TakeException {

		Query query = new Query();

		KnowledgeBase kb = src.getKnowledgeBase();

		query.setPredicate(kb.getSupportedPredicates().iterator().next());
		query.setInputParams(new boolean[] { true, true });

		kb.add(query);

	}
}
