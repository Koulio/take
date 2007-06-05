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


package nz.org.take;


/**
 * A record is more or less fact without variables (=ground).
 * Records are imported into the kb from external fact stores,
 * such as relational databases or web services. 
 * The predicate is usually simple, i.e. it consists of a name and 
 * the types associated by the predicate.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public interface Record {
	
	/**
	 * Get the predicate.
	 */
	public SimplePredicate getPredicate() ;
	
	/**
	 * Get the object at a given position.
	 * The type of this object must be consistent with the type of the respective predicate slot.
	 * I.e. the following constarint must be satisfied: 
	 * assert(this.getPredicate().getSlotTypes()[i].isAssignableFrom(this.getObject(i).getClass()))
	 * @param pos a position
	 * @return an object
	 */
	public Object getObject(int pos) throws ExternalFactStoreException;

}
