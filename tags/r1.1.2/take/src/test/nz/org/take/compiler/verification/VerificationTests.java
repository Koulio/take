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

package test.nz.org.take.compiler.verification;

import java.io.StringReader;
import org.apache.log4j.BasicConfigurator;
import test.nz.org.take.TakeTestCase;
import nz.org.take.KnowledgeBase;
import nz.org.take.script.*;
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
