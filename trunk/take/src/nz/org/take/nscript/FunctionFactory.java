/**
 * Copyright 2008 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */

package nz.org.take.nscript;

import java.util.Map;
import nz.org.take.AggregationFunction;
import nz.org.take.Function;

/**
 * Factory for functions. Uses the AbstractFactory pattern.
 * To register additional functions, install a new instance.
 * It is recommended  to subclass the default factory and call super
 * first in overridden methods.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public abstract class FunctionFactory {
	protected static FunctionFactory defaultInstance = new DefaultFunctionFactory() ;
	public void install() {
		defaultInstance = this;
	}
	public abstract Function createFunction (String name, Map<String,AggregationFunction> aggregationFunctions,Class... termTypes);
	public static FunctionFactory getDefaultInstance() {
		return defaultInstance;
	}
}
