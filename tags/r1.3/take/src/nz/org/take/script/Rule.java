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
 * Derivation rule.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class Rule extends Identifiable  {
	@Override
	public int getLine() {
		if (this.line==-1 && this.getConditions().size()>1)
			return this.conditions.get(0).getLine();
		else
			return super.getLine();
	}

	private List<Condition> conditions = new ArrayList<Condition>();

	public List<Condition> getConditions() {
		return conditions;
	}
	
	public void add(Condition c) {
		this.conditions.add(c);
	}

	public String toString() {
		StringBuffer b = new StringBuffer();
		b.append("if ");
		for (int i=0;i<this.conditions.size()-1;i++) {			
			if (i>0)
				b.append("and ");
			b.append(this.conditions.get(i));
			b.append(' ');
		}
		if (this.conditions.size()>1)
			b.append(" then ");
		b.append(this.conditions.get(this.conditions.size()-1));
		return b.toString();
	}


}
