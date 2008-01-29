/**
 * Copyright 2007 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */

package test.nz.org.take.compiler.verification;

import java.io.StringReader;
import org.apache.log4j.BasicConfigurator;
import test.nz.org.take.TakeTestCase;
import nz.org.take.KnowledgeBase;
import nz.org.take.nscript.*;
import nz.org.take.verification.CheckPredicatesInQueries;
import nz.org.take.verification.CheckVariablesInQueries;

/**
 * Parser tests.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class VerificationTests extends TakeTestCase {

	public VerificationTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		BasicConfigurator.configure();
	}

	public void testCheckVariablesInQuery1() throws Exception {
		String input = 
			"var java.lang.String grandchild,father,grandfather\n"+
			"query is_grandfather_of[in,out]\n"+
			"rule2: if is_father_of[grandchild,father] and is_father_of[father,grandfather] then is_grandfather_of[grandchild,grandfather]\n"+
			"fact1: is_father_of[\"Frank\",\"Lutz\"]";
		ScriptKnowledgeSource source = new ScriptKnowledgeSource(new StringReader(input));
		KnowledgeBase kb = source.getKnowledgeBase();
		boolean result =new CheckVariablesInQueries().verify(kb);
		assertTrue(result);
	}
	public void testCheckVariablesInQuery2() throws Exception {
		String input = 
			"var java.lang.String grandchild,father,grandfather,x\n"+
			"query is_grandfather_of[in,out]\n"+
			"rule2: if is_father_of[grandchild,father] and is_father_of[father,grandfather] then is_grandfather_of[grandchild,x]\n"+
			"fact1: is_father_of[\"Frank\",\"Lutz\"]";
		ScriptKnowledgeSource source = new ScriptKnowledgeSource(new StringReader(input));
		KnowledgeBase kb = source.getKnowledgeBase();
		boolean result =new CheckVariablesInQueries().verify(kb);
		assertFalse(result);
	}
	public void testCheckPredicatesInQuery1() throws Exception {
		String input = 
			"var java.lang.String grandchild,father,grandfather\n"+
			"query is_grandfather_of[in,out]\n"+
			"rule2: if is_father_of[grandchild,father] and is_father_of[father,grandfather] then is_grandfather_of[grandchild,grandfather]\n"+
			"fact1: is_father_of[\"Frank\",\"Lutz\"]";
		ScriptKnowledgeSource source = new ScriptKnowledgeSource(new StringReader(input));
		KnowledgeBase kb = source.getKnowledgeBase();
		boolean result =new CheckPredicatesInQueries().verify(kb);
		assertTrue(result);
	}
	public void testCheckPredicatesInQuery2() throws Exception {
		String input = 
			"var java.lang.String grandchild,father,grandfather\n"+
			"query is_father_of[in,out]\n"+
			"rule2: if is_father_of[grandchild,father] and is_father_of[father,grandfather] then is_grandfather_of[grandchild,grandfather]";
		ScriptKnowledgeSource source = new ScriptKnowledgeSource(new StringReader(input));
		KnowledgeBase kb = source.getKnowledgeBase();
		boolean result =new CheckPredicatesInQueries().verify(kb);
		assertFalse(result);
	}

	@Override
	protected void tearDown() throws Exception {
		org.apache.log4j.LogManager.shutdown();
		super.tearDown();
	}
}
