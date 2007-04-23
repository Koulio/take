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
 * The script itself.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class Script implements Visitable {
	private List elements = new ArrayList();


	public void add(VariableDeclaration v) {
		this.elements.add(v);
	}
	public void add(Rule r) {
		this.elements.add(r);
	}
	public void accept(ScriptVisitor visitor) {
		if (visitor.visit(this)) {
			for (Object o:elements){
				if (o instanceof VariableDeclaration)
					((VariableDeclaration)o).accept(visitor);
				else if (o instanceof Rule)
					((Rule)o).accept(visitor);
			}
		}
		visitor.endVisit(this);
	}
	public List getElements() {
		return elements;
	}

}
