/**
 * Copyright 2007 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */
 

package example.nz.org.take.compiler.userv.domainmodel;

import java.util.Collection;
import java.util.HashSet;

/**
 * List of domain objects used in the example.
 * http://www.businessrulesforum.com/2005_Product_Derby.pdf 
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class HighTheftProbabilityAutoList {
	
	private static Collection<String> list = null;
	
	public static Collection<String> getList() {
		if (list==null) {
			list = new HashSet<String>();
			list.add("Mini");	
			list.add("VW Beetle");
			list.add("VW Phaeton");
			list.add("Audi A3");
			list.add("Mercedes Benz A class");
		}
		return list;
	}

}
