/**
 * Copyright 2007 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */

package nz.org.take.script;

import java.util.ArrayList;
import java.util.List;

/**
 * The script itself.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class Script {
	private List elements = new ArrayList();

	public void add(Comment c) {
		this.elements.add(c);
	}
	public void add(VariableDeclaration v) {
		this.elements.add(v);
	}
	public void add(RefDeclaration v) {
		this.elements.add(v);
	}
	public void add(Rule r) {
		this.elements.add(r);
	}
	public void add(QuerySpec q) {
		this.elements.add(q);
	}
	public void add(Annotation a) {
		this.elements.add(a);
	}
	public void add(FactStore fs) {
		this.elements.add(fs);
	}
	public void add(Aggregation a) {
		this.elements.add(a);
	}
	public List getElements() {
		return elements;
	}

}
