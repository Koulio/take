/**
 * Copyright 2007 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */


package nz.org.take.compiler.util;

import java.util.HashMap;
import java.util.Map;
/**
 * Utility class used to generate tmp variable names.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 * @version 0.1
 */
public class TmpVarGenerator {

	private Map<String,Integer> counters = new HashMap<String,Integer>();
	
	public TmpVarGenerator() {
		super();
	}
	
	/**
	 * Generate the next tmp var.
	 * @return a string
	 */
	public String nextTmpVar(String name) {
		Integer c = counters.get(name);
		if (c==null) {
			c = new Integer(0);
			counters.put(name,c);
		}
		c = c+1;
		counters.put(name,c);
		return name+c;
	}
	

}
