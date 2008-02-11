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

import java.util.ArrayList;
import java.util.List;

/**
 * Derivation rules, i.e., if .. then ..  constructs.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class DerivationRule extends AbstractAnnotatable implements Clause {
	private Fact head = null;
	private String id = null;
	private List<Prerequisite> body = new ArrayList<Prerequisite>();
	

	public Predicate getPredicate() {
		return head==null?null:head.getPredicate();
	}


	public Fact getHead() {
		return head;
	}


	public void setHead(Fact head) {
		this.head = head;
	}


	public List<Prerequisite> getBody() {
		return body;
	}


	public void setBody(List<Prerequisite> body) {
		this.body = body;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}
	
	public String toString() {
		StringBuffer b = new StringBuffer();
		boolean f = true;
		b.append(" IF ");
		for (Prerequisite p:this.body) {
			if (f)
				f = false;
			else 
				b.append(" AND ");
			b.append(p);
		}
		b.append(" THEN ");
		b.append(head);
		return b.toString();
	}
	public void accept(KnowledgeBaseVisitor visitor) {
		if (visitor.visit(this)) {
			head.accept(visitor);
			for (Prerequisite p:body)
				p.accept(visitor);
		}		
		visitor.endVisit(this);
	}
}
