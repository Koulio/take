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
