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

import java.util.*;

/**
 * Check whether the variables in rule heads also appear in rule bodies.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class CheckVariablesInQueries extends VerificationTool{

	public String getName() {
		return "check variables in queries";
	}

	public boolean verify(KnowledgeBase kb) {

		final Map<KnowledgeElement,Boolean> results = new LinkedHashMap<KnowledgeElement,Boolean>();
		
		KnowledgeBaseVisitor visitor = new AbstractKnowledgeBaseVisitor() {
			Collection<Variable> vars = null;
			Collection<Variable> varsInBody = new HashSet<Variable>();
			Collection<Variable> varsInHead = new ArrayList<Variable>();
			DerivationRule context = null;
			public boolean visit(DerivationRule r) {
				context = r;
				varsInHead.clear();
				varsInBody.clear();
				return true;
			}
			public void endVisit(DerivationRule r) {
				context=null;
				Variable var = null;
				for (Variable v:varsInHead) {
					if (!varsInBody.contains(v))
						var = v;
				}
				if (var==null)
					reportOK(r);
				else
					reportViolation("variable ",var," in ",r," occurs only in rule head");
				results.put(r,var==null);
			}
			public boolean visit(Fact f) {
				if (context==null) {
					return false;
				}
				else if (context.getHead()==f) {
					vars=varsInHead;
				}
				else if (context.getBody().contains(f)) {
					vars=varsInBody;
				}
				return true;
			}
			public boolean visit(Variable t) {
				vars.add(t);
				return true;
			}			
			
		};
		
		kb.accept(visitor);
		for (boolean b:results.values()) {
			if (!b) return false;
		}
		return true;
	}

}
