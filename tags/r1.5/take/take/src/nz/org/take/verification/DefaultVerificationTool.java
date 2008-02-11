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
 * this predicate. This tool will run all checks available in this package.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class DefaultVerificationTool extends VerificationTool{

	private VerificationTool[] parts = {
		new CheckPredicatesInQueries(),
		new CheckVariablesInQueries()
	};
	
	public String getName() {
		return "default verification tool";
	}

	public boolean verify(KnowledgeBase kb) {
		boolean result = true;
		for (VerificationTool part:parts) {
			result = result && part.verify(kb);
		}
		return result;
	}

}
