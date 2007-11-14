/**
 * Copyright 2007 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
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
