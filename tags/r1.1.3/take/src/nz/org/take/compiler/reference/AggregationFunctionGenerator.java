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


package nz.org.take.compiler.reference;

import java.io.PrintWriter;

import nz.org.take.AggregationFunction;
import nz.org.take.Aggregations;
import nz.org.take.Query;
import nz.org.take.compiler.CompilerException;

/**
 * Code generator for aggregation functions.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */
public interface AggregationFunctionGenerator  {
	/**
	 * Create an aggregation function. Return the query used by this function - it
	 * has to be added to the agenda.
	 * @param out
	 * @param aggregationFunction
	 * @param context
	 * @return a query needed by the aggregation
	 * @throws CompilerException
	 */
	public Query createAggregationFunction(PrintWriter out,AggregationFunction aggregationFunction,CompilerUtils context) throws CompilerException ;
	/**
	 * Get the supported aggregation (AVG,SUM,MIN,...)
	 * @return
	 */
	public Aggregations getSupportedAggregation();

}
