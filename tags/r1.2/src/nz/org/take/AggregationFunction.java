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

import java.util.ArrayList;
import java.util.List;

/**
 * Function aggregation the results of several other queries.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class AggregationFunction implements Function {

	private static final Class[] NUMTYPES={
	    Character.TYPE, Byte.TYPE, Short.TYPE, Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE, 
	    Boolean.class, Character.class, Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class
	};
	private String name = null;
	private Fact query = null;
	private Aggregations aggregation = null;
	private Variable variable = null;
	// derived
	private Class[] paramTypes = null;
	private int variableSlot = -1;

	/**
	 * Check the integrity of the parameters and set return and parameter types.
	 */
	private void init(boolean force) throws IllegalArgumentException {

		if (!force && (query==null||variable==null||aggregation==null))
			return; // delay initialisation
		
		// check whether variable occurs in query fact
		boolean checkVar = false;
		int slotNo = -1;
		for (Term t:query.getTerms()) {
			slotNo=slotNo+1;
			if (this.variable.equals(t)) {
				checkVar = true;
				variableSlot=slotNo;
			}
			if (t instanceof ComplexTerm) {
				throw new IllegalArgumentException("Complex terms in the definition of aggregations are not (yet) supported: " + this.getName());
			} 
		}
		if (!checkVar) 
			throw new IllegalArgumentException("The aggregation variable in " + this.getName() + " does not occur in the query");
		
		// check whether output slot is numeric
		Class oType = this.variable.getType();
		boolean isPrimitive = false;
		for (Class numType:NUMTYPES) {
			if (oType==numType) 
				isPrimitive = true;
		}
		if (!isPrimitive&&this.getAggregation()!=Aggregations.COUNT) 
			throw new IllegalArgumentException("The return type in aggregation functions must be numeric but is " + oType.getName());
		

		// TODO
		// check variable and also check constants
		final List<Class> paramTypes = new ArrayList<Class>();
		KnowledgeBaseVisitor visitor = new AbstractKnowledgeBaseVisitor() {
			public boolean visit(Variable variable) {
				paramTypes.add(variable.getType());
				return false;
			} 
		};
		this.query.accept(visitor);
		
		this.paramTypes = new Class[paramTypes.size()];
		for (int i=0;i<paramTypes.size();i++)
			this.paramTypes[i]=paramTypes.get(i);

			
	}
	public Class[] getParamTypes() {
		if (paramTypes==null)
			init(true);
		return paramTypes;
	}

	public Class getReturnType() {
		if (this.getAggregation()==Aggregations.COUNT)
			return Integer.TYPE;
		else
			return this.variable.getType();		
	}
	
	public String toString() {
		return getName();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Aggregations getAggregation() {
		return aggregation;
	}

	public void setAggregation(Aggregations aggregation) {
		this.aggregation = aggregation;
		init(false);
	}

	public Fact getQuery() {
		return query;
	}

	public void setQuery(Fact query) {
		this.query = query;
		init(false);
	}

	public Variable getVariable() {
		return variable;
	}

	public void setVariable(Variable variable) {
		this.variable = variable;
		init(false);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((aggregation == null) ? 0 : aggregation.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((query == null) ? 0 : query.hashCode());
		result = prime * result
				+ ((variable == null) ? 0 : variable.hashCode());
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
		final AggregationFunction other = (AggregationFunction) obj;
		if (aggregation == null) {
			if (other.aggregation != null)
				return false;
		} else if (!aggregation.equals(other.aggregation))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (query == null) {
			if (other.query != null)
				return false;
		} else if (!query.equals(other.query))
			return false;
		if (variable == null) {
			if (other.variable != null)
				return false;
		} else if (!variable.equals(other.variable))
			return false;
		return true;
	}
	public int getVariableSlot() {
		return variableSlot;
	}

}
