/**
 * Copyright 2008 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */

package example.nz.org.take.compiler.example2;

import nz.org.take.rt.ResultSet;
import example.nz.org.take.compiler.example2.generated.CategorizeCustomers;
import example.nz.org.take.compiler.example2.generated.CustomerCategory;


/**
 * Script that queries the generated kb.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class Example {
		
	/**
	 * Generate the sources for the example.
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		CategorizeCustomers kb = new CategorizeCustomers();
		Customer c = new Customer();
		ResultSet<CustomerCategory> rs = kb.getCategory(c);
		CustomerCategory r1 = rs.next();
		System.out.println(r1.category);
		
		
		
	}
}
