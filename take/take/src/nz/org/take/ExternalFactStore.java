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
 * Represents external fact stores.
 * The compiler will generate a resourec iterator factory interface
 * for these fact stores that can then be implemented.
 * Typically, an implementation is based on a database or web service.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class ExternalFactStore extends AbstractAnnotatable implements KnowledgeElement {

	private String id = null;
	private SimplePredicate predicate = null;

	public void accept(KnowledgeBaseVisitor visitor) {
		visitor.visit(this);
		visitor.endVisit(this);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SimplePredicate getPredicate() {
		return predicate;
	}

	public void setPredicate(SimplePredicate predicate) {
		this.predicate = predicate;
	}

}
