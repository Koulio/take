/**
 * Copyright 2007 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */


package test.nz.org.take.compiler.scenario5;

import org.apache.log4j.BasicConfigurator;
import nz.org.take.KnowledgeBase;
import nz.org.take.compiler.reference.DefaultCompiler;
import nz.org.take.compiler.util.jalopy.JalopyCodeFormatter;
import nz.org.take.script.ScriptException;
import nz.org.take.script.ScriptKnowledgeSource;


/**
 * Script to generate the interface for the test kb.
 * @see KBFactory
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class GenerateInterfaces {

	/**
	 * Generate the interface for the example.
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		BasicConfigurator.configure();
		nz.org.take.compiler.Compiler compiler = new DefaultCompiler();
		compiler.add(new JalopyCodeFormatter());
		
		// generate kb
		KnowledgeBase kb = null;
		try {
			ScriptKnowledgeSource KSrc = new ScriptKnowledgeSource(Tests.class.getResourceAsStream("/test/nz/org/take/compiler/scenario5/rules5.take"));
			kb = KSrc.getKnowledgeBase();
		} catch (ScriptException e) {
			e.printStackTrace();
		}
		compiler.setPackageName("test.nz.org.take.compiler.scenario5.generated");
		compiler.setClassName("KB");
		compiler.compileInterface(kb);

	}
}
