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


package example.nz.org.take.compiler.userv.scripts;

import java.io.InputStream;
import org.apache.log4j.BasicConfigurator;
import nz.org.take.compiler.NameGenerator;
import nz.org.take.compiler.reference.DefaultCompiler;
import nz.org.take.compiler.util.DefaultLocation;
import nz.org.take.compiler.util.DefaultNameGenerator;
import nz.org.take.compiler.util.jalopy.JalopyCodeFormatter;
import nz.org.take.script.ScriptKnowledgeSource;


/**
 * Script to generate the public interface for this scenario.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class GenerateStandaloneClass {
		
	/**
	 * Generate the sources for the example.
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		BasicConfigurator.configure();
		DefaultLocation location = new DefaultLocation();
		NameGenerator nameGenerator = new DefaultNameGenerator();
		nz.org.take.compiler.Compiler compiler = new DefaultCompiler();
		compiler.add(new JalopyCodeFormatter());
		compiler.setNameGenerator(nameGenerator);
		BasicConfigurator.configure();			
		// generate kb
		InputStream script = GenerateStandaloneClass.class.getResourceAsStream("/example/nz/org/take/compiler/userv/rules/userv.take");
		ScriptKnowledgeSource ksource = new ScriptKnowledgeSource(script);
		compiler.setLocation(location);
		compiler.setPackageName("example.nz.org.take.compiler.userv.generated");
		compiler.setClassName("UservRules");
		compiler.compile(ksource.getKnowledgeBase());
		
		long before = System.currentTimeMillis();
		compiler.compile(ksource.getKnowledgeBase());
		System.out.println("Compilation took "+(System.currentTimeMillis()-before)+"ms");
		
	}
}
