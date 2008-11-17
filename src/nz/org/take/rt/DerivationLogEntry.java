/**
 * Copyright 2007 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */

package nz.org.take.rt;

/**
 * Represents one node in the derivation.
 * Contains the name of the artefact used (can be used to look up rules in the kb),
 * and an int defining what kind of object this is.
 * See the constants in DerivationController for possible values.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class DerivationLogEntry {
	private String name = null;
	private int kind = 0;
	public DerivationLogEntry(String name, int kind) {
		super();
		this.name = name;
		this.kind = kind;
	}
	public int getKind() {
		return kind;
	}
	public void setKind(int kind) {
		this.kind = kind;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + kind;
		result = PRIME * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final DerivationLogEntry other = (DerivationLogEntry) obj;
		if (kind != other.kind)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	/**
	 * Get the type of the node as string.
	 * @return a string
	 */
	public String getCategory() {
		switch (kind) {
			case DerivationController.RULE:return "rule";
			case DerivationController.FACT:return "fact";
			case DerivationController.BEAN_PROPERTY: return "property";
			case DerivationController.JAVA_METHOD: return "method";
			default: return "other";
		}
	}
}
