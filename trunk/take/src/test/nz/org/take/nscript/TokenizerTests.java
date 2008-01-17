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

import java.util.List;

import nz.org.take.nscript.Tokenizer;

import junit.framework.TestCase;

/**
 * Tokenizer tests.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class TokenizerTests extends TestCase {
	
	private void check(String string,String[] separators,String[] expected) throws Exception {
		List<String> tokens = Tokenizer.tokenize(string,separators);
		String[] computed = tokens.toArray(new String[tokens.size()]);
		assertTrue(java.util.Arrays.equals(computed, expected));
	}
	
	public void test1() throws Exception {
		check(
				"cond1 and cond2 then cond3",
				new String[]{" and "," then "},
				new String[]{"cond1","cond2","cond3"}
		);
	}
	
	public void test2() throws Exception {
		check(
				"cond1 and cond2[' and then '] then cond3",
				new String[]{" and "," then "},
				new String[]{"cond1","cond2[' and then ']","cond3"}
		);
	}

}
