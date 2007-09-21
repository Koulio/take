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


package example.nz.org.take.compiler.example1;

import java.io.InputStream;
import javax.script.Bindings;
import javax.script.SimpleBindings;
import org.apache.log4j.BasicConfigurator;
import example.nz.org.take.compiler.example1.spec.*;
import nz.org.take.deployment.KnowledgeBaseManager;
import nz.org.take.rt.DerivationLogEntry;
import nz.org.take.rt.ResultSet;
import nz.org.take.script.ScriptKnowledgeSource;


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
		Bindings bindings = new SimpleBindings();		
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
