/**
 * Copyright 2008 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */

package test.nz.org.take.nscript;

import nz.org.take.*;
import nz.org.take.nscript.JSPELParser;
import junit.framework.TestCase;

/**
 * Tests for the EL parser.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class JSPELParserTests extends TestCase {
	public void _testCond1() throws Exception {
		Fact f = new JSPELParser().parseCondition("42==48");
		Predicate p = f.getPredicate();
		Term[] terms = f.getTerms();
	}
	public void testTermNumberLiteral1() throws Exception {
		Term t = new JSPELParser().parseTerm("42");
		assertTrue(t instanceof Constant);
		assertEquals(Long.class,t.getType());
		assertEquals(new Long(42),((Constant)t).getObject());
	}
	public void testTermNumberLiteral2() throws Exception {
		Term t = new JSPELParser().parseTerm("42.42");
		assertTrue(t instanceof Constant);
		assertEquals(Double.class,t.getType());
		assertEquals(new Double(42.42),((Constant)t).getObject());
	}
	public void testTermNumberLiteral3() throws Exception {
		Term t = new JSPELParser().parseTerm("42e2");
		assertTrue(t instanceof Constant);
		assertEquals(Double.class,t.getType());
		assertEquals(new Double(42e2),((Constant)t).getObject());
	}
	public void testTermNumberLiteral4() throws Exception {
		Term t = new JSPELParser().parseTerm("-42");
		assertTrue(t instanceof Constant);
		assertEquals(Long.class,t.getType());
		assertEquals(new Long(-42),((Constant)t).getObject());
	}	
	public void testTermNumberLiteral5() throws Exception {
		Term t = new JSPELParser().parseTerm("-42.42");
		assertTrue(t instanceof Constant);
		assertEquals(Double.class,t.getType());
		assertEquals(new Double(-42.42),((Constant)t).getObject());
	}
	public void testTermNumberLiteral6() throws Exception {
		Term t = new JSPELParser().parseTerm("-42e2");
		assertTrue(t instanceof Constant);
		assertEquals(Double.class,t.getType());
		assertEquals(new Double(-42e2),((Constant)t).getObject());
	}
	public void testTermStringLiteral1() throws Exception {
		Term t = new JSPELParser().parseTerm("'test'");
		assertTrue(t instanceof Constant);
		assertEquals(String.class,t.getType());
		assertEquals("test",((Constant)t).getObject());
	}
	public void test1() throws Exception {
		Term t = new JSPELParser().parseTerm("test.name");
		assertTrue(t instanceof Constant);
		assertEquals(String.class,t.getType());
		assertEquals("test",((Constant)t).getObject());
	}

}
