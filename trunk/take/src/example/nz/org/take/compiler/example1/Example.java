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

import java.io.FileReader;

import javax.script.Bindings;
import javax.script.SimpleBindings;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import org.apache.log4j.BasicConfigurator;

import example.nz.org.take.compiler.example1.spec.CustomerDiscount;
import example.nz.org.take.compiler.example1.spec.DiscountPolicy;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import nz.org.take.KnowledgeBase;
import nz.org.take.compiler.Location;
import nz.org.take.compiler.NameGenerator;
import nz.org.take.compiler.reference.DefaultCompiler;
import nz.org.take.compiler.util.DefaultLocation;
import nz.org.take.compiler.util.DefaultNameGenerator;
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
		bindings.put("goldCustomer",new CustomerCategory("gold"));
		bindings.put("goldCustomerDiscount",new Discount(20,true));
		KB = kbm.getKnowledgeBase(
				DiscountPolicy.class, 
				new ScriptKnowledgeSource("exampledata/example1/crm-example.take"),
				bindings);

		
		// now use the generated classes to query the kb
		Customer john = new Customer("John");
		john.setCategory(new CustomerCategory("gold"));
		ResultSet<CustomerDiscount> result =  KB.getDiscount(john);
	    System.out.println("The discount for John is: " + result.next().discount);
	    
	    // print rules used
	    System.out.println("The following rules have been used to calculate the discount: ");
	    for (DerivationLogEntry e:result.getDerivationLog()) {
	    	System.out.print(e.getCategory());
	    	System.out.print(" : ");
	    	System.out.println(e.getName());
	    }
	    
	    
	    System.out.println("done");
	}
}
