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
 * Declaration of a variable.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class VariableDeclaration {
	private String type = null;
	private List<String> names = new ArrayList<String>();
	public void addName(String name) {
		this.names.add(name);
	}
	public String getType() {
		return type;
	}
	public void addToType(String token) {
		if (type==null)
			type=token;
		else 
			this.type = type+'.'+token;
	}
	public String toString() {
		StringBuffer b = new StringBuffer();
		b.append("var ");
		b.append(type);
		b.append(" ");
		boolean f = true;
		for (String n:names) {
			if (f)
				f = false;
			else 
				b.append(',');
			b.append(n);	
		}
		return b.toString();
	}
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((names == null) ? 0 : names.hashCode());
		result = PRIME * result + ((type == null) ? 0 : type.hashCode());
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
		final VariableDeclaration other = (VariableDeclaration) obj;
		if (names == null) {
			if (other.names != null)
				return false;
		} else if (!names.equals(other.names))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
	public List<String> getNames() {
		return names;
	}

	
}
