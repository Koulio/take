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


package nz.org.take.compiler;

import nz.org.take.*;
/**
 * Query interface. A query consists of a predicate and flags classifying 
 * the terms as input or output slots. 
 * For input slots, values are supplied by the application, and therefore 
 * they will be turned into method parameters in the code generated.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */
public class Query implements Cloneable {

	private Predicate predicate = null;
	private boolean[] inputParams = null;


	/**
	 * @param predicate
	 * @param params
	 */
	public Query(Predicate predicate,boolean[] params) {
		super();
		assert(params!=null && params.length==predicate.getSlotTypes().length);
		inputParams = params;
		this.predicate = predicate;
	}


	/**
	 * @return Returns the predicate.
	 */
	public Predicate getPredicate() {
		return predicate;
	}


	/**
	 * @return the param input flags.
	 */
	public boolean[] getInputParams() {
		return inputParams;
	}


	/**
	 * @param inputParams The params input flags to set.
	 */
	public void setInputParams(boolean[] inputParams) {
		this.inputParams = inputParams;
	}
	
	/**
	 * Equals.
	 * @param obj an object
	 * @return a boolean
	 */
	public boolean equals(Object obj) {
		if (!(obj instanceof Query))
			return false;
		Query q = (Query)obj;
		if (!predicate.equals(q.predicate))
			return false;
		for (int i=0;i<inputParams.length;i++)
			if (inputParams[i]!=q.inputParams[i])
				return false;
		return true;
	}
	/**
	 * Return the hashcode.
	 * @return an int
	 */
	public int hashCode() {
		// REFACTOR finetune
		return this.predicate.hashCode();
	}

}
