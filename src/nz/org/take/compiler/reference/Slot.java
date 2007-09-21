/*
 * Copyright (C) 2006 <A href="http://www-ist.massey.ac.nz/JBDietrich" target="_top">Jens Dietrich</a>
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

package nz.org.take.compiler.reference;

/**
 * Represents a (term) slot of a predicate.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */
public class Slot {

	int position = -1;
	String name = null;
	String var = null;
	String accessor = null;
	String mutator = null;
	String type = null;
	
	// public accessors to make properties visible to velocity
	public String getAccessor() {
		return accessor;
	}
	public String getMutator() {
		return mutator;
	}
	public String getName() {
		return name;
	}
	public int getPosition() {
		return position;
	}
	public String getType() {
		return type;
	}
	public String getVar() {
		return var;
	}

}
