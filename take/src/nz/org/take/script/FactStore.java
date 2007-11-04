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
 * External facts stores.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class FactStore extends Identifiable  {

	private String predicate = null;
	private List<String> typeNames= new ArrayList<String>();
	
	public void nextClass() {
		typeNames.add("");
	}
	public void addToClassName(String token) {
		String className = typeNames.get(typeNames.size()-1);
		if (className.length()==0)
			className = token;
		else 
			className = className+'.'+token;
		typeNames.set(typeNames.size()-1, className);
	}

	public String toString() {
		return "external " + this.getId();
	}
	public List<String> getTypeNames() {
		return typeNames;
	}
	public String getPredicate() {
		return predicate;
	}
	public void setPredicate(String predicate) {
		this.predicate = predicate;
	}



}
