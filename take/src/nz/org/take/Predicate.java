/**
 * Copyright 20072009 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */

package nz.org.take;

/**
 * Predicate - associates terms of certain types.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */
public class Predicate extends AbstractAnnotatable implements NamedElement {
	
	private String name;
	private Class[] slotTypes;
	private boolean negated = false;
	protected String[] slotNames;

	public boolean isNegated() {
		return negated;
	}
	
	public void setNegated(boolean negated) {
		this.negated = negated;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Class[] getSlotTypes() {
		return slotTypes;
	}
	
	public void setSlotTypes(Class[] slotTypes) {
		this.slotTypes = slotTypes;
	}
	
	public String toString() {
		// use UML syntax for derivated properties
		return (negated?"!":"") + "/"+name;
	}
	
	/**
	 * Get a copy of this predicate.
	 * @return
	 */
	public Predicate copy() {
		Predicate copy = new Predicate();
		copy.slotTypes = this.getSlotTypes();
		copy.slotNames = this.getSlotNames();
		copy.name = this.getName();
		// copy.annotations = this.getAnnotations();
		copy.negated = this.isNegated();
		return copy;
	}
	
	public String[] getSlotNames() {
		if (slotNames==null && this.getSlotTypes()!=null) {
			slotNames = new String[this.getSlotTypes().length];
			for (int i=1;i<slotNames.length+1;i++) {
				slotNames[i-1]="slot"+i;
			}
		}
		return slotNames;
	}
	
	public void setSlotNames(String[] slotNames) {
		this.slotNames = slotNames;
	}

}
