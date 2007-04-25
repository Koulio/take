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

import java.util.ArrayList;
import java.util.List;

import nz.org.take.Predicate;
import nz.org.take.Query;

/**
 * Represents a query and a list of variables names that can be used 
 * to supply the parameters needed to call the method of the respective query in the given context. 
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */
class QueryRef extends Query {

	private List<String> paramRefs = new ArrayList<String>();
	/**
	 * Constructor.
	 * @param predicate
	 * @param params
	 * @param paramRefs
	 */
	QueryRef(Predicate predicate, boolean[] params,List<String> paramRefs) {
		super(predicate, params);
		this.paramRefs = paramRefs;
	}
	/**
	 * Get a list of parameter references.
	 * @return
	 */
	List<String> getParamRefs() {
		return paramRefs;
	}
	/**
	 * Add a parameter reference.
	 * @param paramRef a parameter
	 */
	void add(String paramRef) {
		this.paramRefs.add(paramRef);
	}


}
