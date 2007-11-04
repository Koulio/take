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
 * spec for queries
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class QuerySpec extends ScriptElement {
	private String predicate = null;
	private List<Boolean> ioSpec = new ArrayList<Boolean>();
	private boolean negated = false;

	public boolean isNegated() {
		return negated;
	}
	public void setNegated(boolean negated) {
		this.negated = negated;
	}
	public void setIoSpec(List<Boolean> ioSpec) {
		this.ioSpec = ioSpec;
	}
	public String getPredicate() {
		return predicate;
	}
	public void setPredicate(String predicate) {
		this.predicate = predicate;
	}
	public List<Boolean> getIoSpec() {
		return ioSpec;
	}
	public void addToIOSpec(boolean io) {
		this.ioSpec.add(io);
	}
}
