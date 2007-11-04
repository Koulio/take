/**
 * Copyright 2007 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
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
