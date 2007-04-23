/*
 * Copyright (C) 2007 <A href="http://www-ist.massey.ac.nz/JBDietrich" target="_top">Jens Dietrich</a>
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package nz.org.take.script;

import java.util.ArrayList;
import java.util.List;

/**
 * Derivation rule.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class Rule implements Visitable {
	private List<Condition> conditions = new ArrayList<Condition>();

	public List<Condition> getConditions() {
		return conditions;
	}
	
	public void add(Condition c) {
		this.conditions.add(c);
	}

	public void accept(ScriptVisitor visitor) {		
		if (visitor.visit(this)) {
			for (Condition c:this.conditions) {
				c.accept(visitor);				
			}
		}
		visitor.endVisit(this);
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
