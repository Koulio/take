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
		String n = createName();
		if (n!=null)
			name = n;
		else 
			throw new TakeException("This comparison operator is unknown: " + symbol);
	}
	private String createName() {
		if ( "<".equals(symbol)) return "less_than";
		else if ( "<=".equals(symbol)) return "less_than_or_equal";
		else if ( ">".equals(symbol)) return "greater_than";
		else if ( ">=".equals(symbol)) return "greater_than_or_equal";
		else if ( "==".equals(symbol)) return "equal";
		else if ( "!=".equals(symbol)) return "not_equal";
		else return null;
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
		if (types.length!=2)
			throw new IllegalArgumentException("Comparisons are compare exactly two terms, but the number of arguments is " + types.length);
		checkType(types[0]);
		checkType(types[1]);
		this.types = types;
		this.name = createName() + "_" + computeTypeTag();
	}
	
	private void checkType(Class c) {
		if (c.isPrimitive()) 
			return;
		if (!Number.class.isAssignableFrom(c))
			throw new IllegalArgumentException("Comparisons are for numeric data types only");
			
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
	/**
	 * compute a short string representing the associated types
	 * used as part of the name in order to make names unique if overloading 
	 * occurs.
	 * @return a string
	 */
	private String computeTypeTag() {
		return ""+computeTypeTag(this.types[0])+computeTypeTag(this.types[1]);
	}

	private char computeTypeTag(Class clazz) {
		if (clazz.isPrimitive())
			return clazz.getName().charAt(0);
		else if (clazz.getName().startsWith("java.lang."))
			return Character.toLowerCase(clazz.getName().charAt("java.lang.".length()));
		else throw new IllegalArgumentException("Illegal primitive class name: " + clazz.getName());
		
	}
	
}
