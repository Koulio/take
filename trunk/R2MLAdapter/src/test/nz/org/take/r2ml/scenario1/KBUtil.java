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

		// there is only one predicate
		Predicate lastnamePred = kb.getSupportedPredicates().iterator().next();
		query.setPredicate(lastnamePred);
		query.setInputParams(new boolean[] { true, false });

		kb.add(query);

		Fact fact = new Fact();
		SimplePredicate isFather = new SimplePredicate();
		isFather.setName("isFather");
		isFather.setNegated(false);
		isFather.setSlotNames(new String[] { "son", "father" });
		isFather.setSlotTypes(new Class[] { Person.class, Person.class });
		
		fact.setPredicate(isFather);
// add Fact for Jens is Father of Max
		Term[] terms = new Term[2];
		term[0] = new Person("Max");
		term[1] = new Person("Jens");
		fact.setTerms(new Term[]{ ,  });

		kb.add(fact);

	}
}
