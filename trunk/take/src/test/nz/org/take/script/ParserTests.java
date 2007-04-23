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

package test.nz.org.take.script;

import java.io.StringReader;
import nz.org.take.script.*;
import nz.org.take.script.parser.Parser;
import junit.framework.TestCase;

/**
 * Parser tests.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class ParserTests extends TestCase {
	static Parser p =null;
	private Script parse(String input) throws Exception {
		if (p==null)
			 p = new Parser(new StringReader(input));
		else
			p.ReInit(new StringReader(input));
		Script script = new Script();
		p.parse(script);
		return script;
	}
	private VariableDeclaration getVarDecAt(Script script,int i) {
		return (VariableDeclaration)script.getElements().get(i);
	}
	private Rule getRuleAt(Script script,int i) {
		return (Rule)script.getElements().get(i);
	}
	public void test1() throws Exception {
		String input = 
			"var int x\n";
		Script script = parse(input);
		assertEquals(1,script.getElements().size());
		assertTrue(script.getElements().get(0) instanceof VariableDeclaration);
		assertEquals("x",this.getVarDecAt(script,0).getName());
		assertEquals("int",this.getVarDecAt(script,0).getType());
	}
	public void test2() throws Exception {
		String input = 
			"var java.lang.String aVar\n";
		Script script = parse(input);
		assertEquals(1,script.getElements().size());
		assertTrue(script.getElements().get(0) instanceof VariableDeclaration);	
		assertEquals("aVar",this.getVarDecAt(script,0).getName());
		assertEquals("java.lang.String",this.getVarDecAt(script,0).getType());
	}
	public void test3() throws Exception {
		String input = 
			"var int x\n"+
			"var java.lang.String aVar\n";
		Script script = parse(input);
		assertEquals(2,script.getElements().size());
		assertTrue(script.getElements().get(0) instanceof VariableDeclaration);
		assertTrue(script.getElements().get(1) instanceof VariableDeclaration);
		assertEquals("x",this.getVarDecAt(script,0).getName());
		assertEquals("int",this.getVarDecAt(script,0).getType());
		assertEquals("aVar",this.getVarDecAt(script,1).getName());
		assertEquals("java.lang.String",this.getVarDecAt(script,1).getType());
		
	}
	public void test10() throws Exception {
		String input = 
			"rule1: if p(x,y) then q(y,x)\n";
		Script script = parse(input);
		assertEquals(1,script.getElements().size());
		Rule r = this.getRuleAt(script,0);
		Condition prereq1= r.getConditions().get(0);
		Condition concl = r.getConditions().get(1);
		assertEquals("rule1",r.getId());
		assertEquals("p",prereq1.getPredicate());
		assertEquals("q",concl.getPredicate());
		assertEquals(new VariableTerm("x"),prereq1.getTerms().get(0));
	}
	public void testx() throws Exception {
		String input = 
			"var java.lang.String x\n"+
			"var int y\n"+
			"if p(x,y) then p(y,x)\n"+
			"if x.equals(y) then p(x,y)\n";
		Script script = parse(input);
		assertEquals(4,script.getElements().size());
		
	}

}
