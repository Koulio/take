/*
 * Copyright 2008 Jens Dietrich 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */


package test.nz.org.take.compiler.issue15;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import junit.framework.TestCase;
import nz.org.take.compiler.reference.DefaultCompiler;
import nz.org.take.compiler.util.DefaultLocation;
import nz.org.take.compiler.util.DefaultNameGenerator;
import nz.org.take.compiler.util.jalopy.JalopyCodeFormatter;
import nz.org.take.nscript.Parser;


/**
 * Test case for issue15.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class Tests extends TestCase {
		

	public void test1() throws Exception {
		nz.org.take.compiler.Compiler compiler = new DefaultCompiler();
		compiler.add(new JalopyCodeFormatter());
		compiler.setNameGenerator(new DefaultNameGenerator());
		compiler.setLocation(new DefaultLocation());
		// generate kb
		InputStream script = Tests.class.getResourceAsStream("/test/nz/org/take/compiler/issue15/rules.take");
		Reader reader = new InputStreamReader(script);
		Parser parser = new Parser();
		
		try {
			parser.parse(reader);
			assertTrue(false);
		}
		catch (Exception x) {
			assertTrue(true);
		}
		
	}
}
