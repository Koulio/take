/*
 * Copyright 2007 Jens Dietrich 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */


package example.nz.org.take.compiler.example1;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import nz.org.take.deployment.KnowledgeBaseManager;
import nz.org.take.rt.DerivationLogEntry;
import nz.org.take.rt.ResultSet;
import nz.org.take.nscript.ScriptKnowledgeSource;
import org.apache.log4j.BasicConfigurator;
import example.nz.org.take.compiler.example1.spec.CustomerDiscount;
import example.nz.org.take.compiler.example1.spec.DiscountPolicy;


/**
 * Script that generates the kb, compiles, loads and queries it.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class Example {
		
	/**
	 * Generate the sources for the example.
	 * @param args
	 */
	public static void main(String[] args) throws Exception {

		
		// prepare
		DiscountPolicy KB = null; // this is the generated interface
		BasicConfigurator.configure();

		// compile and bind constants referenced in rules
		KnowledgeBaseManager<DiscountPolicy> kbm = new KnowledgeBaseManager<DiscountPolicy>();
		Map<String,Object> bindings = new HashMap<String,Object>();		
		bindings.put("goldCustomerDiscount",new Discount(20,true));
		InputStream scriptSource = GenerateInterface.class.getResourceAsStream("/example/nz/org/take/compiler/example1/crm-example.take");
		KB = kbm.getKnowledgeBase(
				DiscountPolicy.class, 
				new ScriptKnowledgeSource(scriptSource),
				bindings);
		
		// now use the generated classes to query the kb
		Customer john = new Customer("John");
		john.setTurnover(1000);
		ResultSet<CustomerDiscount> result =  KB.getDiscount(john);
	    System.out.println("The discount for John is: " + result.next().discount);
	    
	    // print rules used
	    System.out.println("The following rules have been used to calculate the discount: ");
	    for (DerivationLogEntry e:result.getDerivationLog()) {
	    	System.out.print(e.getCategory());
	    	System.out.print(" : ");
	    	System.out.println(e.getName());
	    }
	    
	    // query again (first query is slow as kb has to be compiled, like in JSPs), measure time
	    long before = System.currentTimeMillis();
	    result =  KB.getDiscount(john);
	    long after = System.currentTimeMillis();
	    System.out.println("Second query took " + (after-before) + "ms");
	    
	    System.out.println("done");
	}
}
