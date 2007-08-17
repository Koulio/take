/*
 * Copyright (C) 2007 <A href="http://www-ist.massey.ac.nz/JBDietrich" target="_top">Jens Dietrich</a>
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package nz.org.take.verification;

import nz.org.take.*;


/**
 * Check whether for predicates in queries there is at least one knowledge element supporting
 * this predicate.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class CheckPredicatesInQueries extends VerificationTool{

	public String getName() {
		return "check predicates in queries";
	}

	public boolean verify(KnowledgeBase kb) {

		boolean result = true;
		for (Query query:kb.getQueries()) {
			boolean result4this = false;
			for  (KnowledgeElement e:kb.getElements()) {
				if (query.getPredicate().equals(e.getPredicate())) {
					result4this = true;
				}
			}	
			if (result4this)
				reportOK(query);
			else {
				reportViolation("query ",query," contains predicate not supported by any fact, rule or fact store");
				result=false;
			}
		}
		
		return result;
	}

}
