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

import java.util.Arrays;

/**
 * Predicates to compare numbers (==,!=, < etc).
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */


public class Comparison extends AbstractPredicate {
	
	
	private String symbol = null;
	private Class[] types = new Class[]{Double.class,Double.class};
	private String name = null;
	
	public Comparison(String symbol) throws TakeException {
		super();
		this.symbol = symbol;
		if ( "<".equals(symbol)) name = "less_than";
		else if ( "<=".equals(symbol)) name = "less_than_or_equal";
		else if ( ">".equals(symbol)) name = "greater_than";
		else if ( ">=".equals(symbol)) name = "greater_than_or_equal";
		else if ( "==".equals(symbol)) name = "equal";
		else if ( "!=".equals(symbol)) name = "not_equal";
		else throw new TakeException("This comparison operator is unknown: " + symbol);
	}
	
	public String getName() {
		return name;
	}

	public Class[] getSlotTypes() {
		return types;
	}

	public String toString() {
		return getName();
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public void setTypes(Class[] types) {
		this.types = types;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((symbol == null) ? 0 : symbol.hashCode());
		result = PRIME * result + Arrays.hashCode(types);
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
		final Comparison other = (Comparison) obj;
		if (symbol == null) {
			if (other.symbol != null)
				return false;
		} else if (!symbol.equals(other.symbol))
			return false;
		if (!Arrays.equals(types, other.types))
			return false;
		return true;
	}
}
