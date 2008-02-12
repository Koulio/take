/**
 * 
 */
package example.nz.org.take.r2ml.eurent.domain;

import nz.org.take.KnowledgeBase;
import nz.org.take.Query;
import nz.org.take.r2ml.util.AbstractQueryGenerator;

/**
 * @author Bastian Schenke (bastian.schenke(at)googlemail.com)
 *
 */
public class EURentQueryGenerator extends AbstractQueryGenerator {

	/**
	 * 
	 */
	public EURentQueryGenerator() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see nz.org.take.r2ml.util.QueryGenerator#generateQueries(nz.org.take.KnowledgeBase)
	 */
	public void generateQueries(KnowledgeBase kb) {
		for (Query q : buildAllQueries(kb.getSupportedPredicates()))
			if (q.getInputParams()[0] && !q.getInputParams()[1])	
				kb.add(q);
	}

}
