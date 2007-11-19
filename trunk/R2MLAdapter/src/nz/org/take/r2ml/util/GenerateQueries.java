package nz.org.take.r2ml.util;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.List;

import nz.org.take.KnowledgeBase;
import nz.org.take.Predicate;
import nz.org.take.Query;

/**
 * A generator that builds querries for all predicates in a given knowledge
 * base. For each predicate all possible in out signatures are generated.
 * 
 * @author schenke
 *
 */
public class GenerateQueries {
	
	/**
	 * Add queries with all possible in-out-signatures for each predicate in
	 * the knowledge base.
	 * 
	 * @param kb a knowledge base
	 */
	public static void generateQueries(KnowledgeBase kb) {
		
		Collection<Predicate> predicates = kb.getSupportedPredicates();
		for (Predicate predicate : predicates) {
			
			boolean[][] signatures = generateInOutSignatures(predicate);
			for (boolean[] signature : signatures) {
				
				Query q = new Query();
				q.setPredicate(predicate);
				q.setInputParams(signature);
				q.setPublic(true);
				kb.add(q);
				
			} // all signatures
			
		} // all predicates
		
	} // generateQueries

	private static boolean[][] generateInOutSignatures(Predicate predicate) {

		// number of possible signatures
		int sigSize = predicate.getSlotTypes().length;
		int sigCount = (int)Math.pow(2, sigSize);

		boolean[][] signatures = new boolean[sigCount][sigSize];
		
		for (int i = 0; i < sigSize; i++) {
			for (int j = 0; j < sigCount; j++) {
				signatures[j][i] = (int)(j/Math.pow(2, i)%2) == 0;
			}
		}
		
		return signatures;
	}

}
