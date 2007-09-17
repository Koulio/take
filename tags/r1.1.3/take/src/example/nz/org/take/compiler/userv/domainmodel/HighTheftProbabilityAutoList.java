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

package example.nz.org.take.compiler.userv.domainmodel;

import java.util.Collection;
import java.util.HashSet;

/**
 * List of domain objects used in the example.
 * http://www.businessrulesforum.com/2005_Product_Derby.pdf 
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class HighTheftProbabilityAutoList {
	
	private static Collection<String> list = null;
	
	public static Collection<String> getList() {
		if (list==null) {
			list = new HashSet<String>();
			list.add("Mini");	
			list.add("VW Beetle");
			list.add("VW Phaeton");
			list.add("Audi A3");
			list.add("Mercedes Benz A class");
		}
		return list;
	}

}
