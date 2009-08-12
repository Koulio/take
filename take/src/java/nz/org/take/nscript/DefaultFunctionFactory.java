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

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Map;
import nz.org.take.AggregationFunction;
import nz.org.take.Function;
import nz.org.take.JFunction;

/**
 * Default function factory.
 * Will try to find functions as follows:
 * <ol>
 * <li>first, look up the defined aggregation functions for this name
 * <li>if this fails and there is one term type, try to find a (bean) property
 * for this name and build a JFunction wrapping the getter
 * <li>otherwise return null
 * </ol>
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class DefaultFunctionFactory extends FunctionFactory {
	public Function createFunction (String name, Map<String,AggregationFunction> aggregationFunctions,Class... termTypes){
		// first, check whether there is an aggregation function registered under this name
		Function f = aggregationFunctions.get(name);
		if (f!=null)
			return f;
		
		// check whether there is a property
		if (termTypes.length==1) {
			PropertyDescriptor property = findProperty(termTypes[0],name);
			if (property!=null) {
				JFunction jf = new JFunction();
				jf.setMethod(property.getReadMethod());
				return jf;
			}
		}
		
		return null;
	}
	private PropertyDescriptor findProperty(Class type, String p)  {
		return PropertyFinder.findProperty(type,p);
	}
}
