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
 * variable term.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class VariableTerm extends ScriptElement implements Term {
	private List<String> names = new ArrayList<String>();
	private String fullName =null;

	public VariableTerm() {
		super();
	}
	public VariableTerm(String... args) {
		super();
		for (String arg:args)
			addName(arg);
	}
	
	public String getName() {
		return fullName;
	}

	public void addName(String name) {
		this.names.add(name);
		if (fullName==null)
			fullName=name;
		else
			this.fullName = this.fullName + '.' + name;
	}
	
	public String toString() {
		return fullName;
	}

	@Override
	public int hashCode() {
		return fullName==null?0:fullName.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final VariableTerm other = (VariableTerm) obj;
		if (fullName == null) {
			if (other.fullName != null)
				return false;
		} else if (!fullName.equals(other.fullName))
			return false;
		return true;
	}
	public List<String> getNames() {
		return names;
	}
	public void setNames(List<String> names) {
		this.names = names;
	}
	public boolean isSimple() {
		return names.size()==1;
	}
}
