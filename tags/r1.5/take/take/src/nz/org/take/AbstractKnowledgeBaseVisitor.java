/**
 * Copyright 2007 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */

package nz.org.take;

/**
 * Useful abstract kb visitor. The visit method return all true, i.e., the children will be visited.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class AbstractKnowledgeBaseVisitor implements KnowledgeBaseVisitor {

	public void endVisit(KnowledgeBase kb) {
		

	}

	public void endVisit(DerivationRule r) {
		

	}

	public void endVisit(ExternalFactStore k) {
		

	}

	public void endVisit(Fact f) {
		

	}

	public void endVisit(ComplexTerm t) {
		

	}

	public void endVisit(Constant t) {
		

	}

	public void endVisit(Variable t) {
		

	}

	public void endVisit(Query q) {
		

	}

	public boolean visit(KnowledgeBase kb) {
		
		return true;
	}

	public boolean visit(DerivationRule r) {
		
		return true;
	}

	public boolean visit(ExternalFactStore k) {
		
		return true;
	}

	public boolean visit(Fact f) {
		
		return true;
	}

	public boolean visit(ComplexTerm t) {
		
		return true;
	}

	public boolean visit(Constant t) {
		
		return true;
	}

	public boolean visit(Variable t) {
		
		return true;
	}

	public boolean visit(Query q) {
		
		return true;
	}

}
