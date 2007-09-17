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
