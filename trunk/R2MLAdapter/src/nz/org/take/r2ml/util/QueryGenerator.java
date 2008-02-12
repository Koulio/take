/**
 * 
 */
package nz.org.take.r2ml.util;

import nz.org.take.KnowledgeBase;

/**
 * @author Bastian Schenke (bastian.schenke(at)googlemail.com)
 *
 */
public interface QueryGenerator {

	public void generateQueries(KnowledgeBase kb);
}
