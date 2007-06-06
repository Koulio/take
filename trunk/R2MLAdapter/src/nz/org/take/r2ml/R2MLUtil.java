/**
 * 
 */
package nz.org.take.r2ml;

import nz.org.take.Fact;
import nz.org.take.Prerequisite;

/**
 * @author Bastian Schenke (bastian.schenke@googlemail.com)
 *
 */
class R2MLUtil {
	
	static Prerequisite factAsPrerequisite (Fact fact) {
		
		if (fact instanceof Prerequisite)
			return (Prerequisite)fact;
		Prerequisite prereq = new Prerequisite();
		prereq.addAnnotations(fact.getAnnotations());
		prereq.setId(fact.getId());
		prereq.setPredicate(fact.getPredicate());
		prereq.setTerms(fact.getTerms());
		return prereq;
		
	}
	
	static Fact PrerequisiteAsFact (Prerequisite prereq) throws R2MLException {
		Fact fact = new Fact();
		fact.setId(prereq.getId());
		fact.setPredicate(prereq.getPredicate());
		fact.setTerms(prereq.getTerms());
		return fact;
	}

}
