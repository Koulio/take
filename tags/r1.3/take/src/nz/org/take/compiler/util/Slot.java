/**
 * Copyright 2007 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */

package nz.org.take.compiler.util;

/**
 * Represents a (term) slot of a predicate.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */
public class Slot {

	int position = -1;
	String name = null;
	String var = null;
	String accessor = null;
	String mutator = null;
	String type = null;
	
	/**
	 * Getters/Setters for all private fields. 
	 */

	public void setAccessor(String accessor) {
		this.accessor = accessor;
	}
	public void setMutator(String mutator) {
		this.mutator = mutator;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setVar(String var) {
		this.var = var;
	}
	public String getAccessor() {
		return accessor;
	}
	public String getMutator() {
		return mutator;
	}
	public String getName() {
		return name;
	}
	public int getPosition() {
		return position;
	}
	public String getType() {
		return type;
	}
	public String getVar() {
		return var;
	}
}
