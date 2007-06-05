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
