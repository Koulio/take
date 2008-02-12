package test.nz.org.take.r2ml.scenario3;


import nz.org.take.Fact;
import nz.org.take.KnowledgeElement;
import nz.org.take.Query;
import nz.org.take.SimplePredicate;
import nz.org.take.TakeException;
import nz.org.take.nscript.ScriptKnowledgeSource;
import nz.org.take.r2ml.R2MLKnowledgeSource;

public class KBUtil {

	static void addQuerries(R2MLKnowledgeSource src) throws TakeException {

		SimplePredicate isFather = new SimplePredicate();
		isFather.setName("isFather");
		isFather.setNegated(false);
		isFather.setSlotNames(new String[] {"son", "father"} );
		isFather.setSlotTypes(new Class[] {String.class, String.class} );

		SimplePredicate isGrandfather = new SimplePredicate();
		isGrandfather.setName("isGrandfather");
		isGrandfather.setNegated(false);
		isGrandfather.setSlotNames(new String[] {"grandson", "grandfather"} );
		isGrandfather.setSlotTypes(new Class[] {String.class, String.class} );

//		query is_father_of[in,out]
		Query qIsFather = new Query();
		qIsFather.setPredicate(isFather);
		qIsFather.setInputParams(new boolean[] { true, false });
		src.getKnowledgeBase().add(qIsFather);
//		query is_father_of[out,in]
		Query qIsFather2 = new Query();
		qIsFather2.setPredicate(isFather);
		qIsFather2.setInputParams(new boolean[] { false, true });
		src.getKnowledgeBase().add(qIsFather2);
//		query is_grandfather_of[in,out]
		Query qIsGrandfather = new Query();
		qIsGrandfather.setPredicate(isGrandfather);
		qIsGrandfather.setInputParams(new boolean[] { true, false });
		src.getKnowledgeBase().add(qIsGrandfather);
//		query is_grandfather_of[out,in]
		Query qIsGrandfather2 = new Query();
		qIsGrandfather2.setPredicate(isGrandfather);
		qIsGrandfather2.setInputParams(new boolean[] { false, true });
		src.getKnowledgeBase().add(qIsGrandfather2);
	}

	// add some facts to reason on
	static void addKnowledge(R2MLKnowledgeSource src) throws TakeException {
		ScriptKnowledgeSource factSrc = new ScriptKnowledgeSource(
				Scenario3Test.class
						.getResourceAsStream("/test/nz/org/take/r2ml/scenario3/facts.take"));
		for (KnowledgeElement item : factSrc.getKnowledgeBase().getElements()) {
			((SimplePredicate)((Fact)item).getPredicate()).setSlotNames(new String[]{ "son", "father" });
			src.getKnowledgeBase().add(item);
		}
	}
}
