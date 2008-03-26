package example.nz.org.take.compiler.example2;
/**
 * Copyright 2008 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */
import java.lang.reflect.Method;
import java.util.Map;

import nz.org.take.AggregationFunction;
import nz.org.take.Function;
import nz.org.take.JFunction;
import nz.org.take.nscript.DefaultFunctionFactory;

/**
 * Custom function factory used to initialize functions encountered in scripts.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class CustomFunctionFactory extends DefaultFunctionFactory {

	@Override
	public Function createFunction(String name,
			Map<String, AggregationFunction> aggregationFunctions,
			Class... termTypes) {
		Function f = super.createFunction(name, aggregationFunctions, termTypes);
		if (f==null && "turnover".equals(name) && termTypes.length==2 && termTypes[0]==Customer.class && termTypes[1]==Long.class) {
			Method m;
			try {
				m = Customer.class.getMethod("getTurnover",int.class);
				JFunction f2 = new JFunction();
				f2.setMethod(m);
				f=f2;
			} catch (Exception x) {}
		}
		return f;
	}
	
	

}
