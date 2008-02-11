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

import java.util.regex.Pattern;

import nz.org.take.nscript.Parser;

import junit.framework.TestCase;

/**
 * Tests for the regular expressions used by the parser.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class RegExTests extends TestCase {

	private void shouldMatch(Pattern p,String s) throws Exception {
		assertTrue(p.matcher(s).matches());
	}
	private void shouldNotMatch(Pattern p,String s) throws Exception {
		assertFalse(p.matcher(s).matches());
	}
	public void testTypeName1() throws Exception {
		shouldMatch(Parser.TYPE_NAME,"int");
	}
	public void testTypeName2() throws Exception {
		shouldMatch(Parser.TYPE_NAME,"java.lang.String");
	}
	public void testTypeName3() throws Exception {
		shouldMatch(Parser.TYPE_NAME,"java.lang.String42");
	}
	public void testTypeName4() throws Exception {
		shouldNotMatch(Parser.TYPE_NAME,"java.lang.42String");
	}
	public void testTypeName5() throws Exception {
		shouldNotMatch(Parser.TYPE_NAME,"4int");
	}
	public void testGlobalAnnotation1() throws Exception {
		shouldMatch(Parser.GLOBAL_ANNOTATION,"@@key=value");
	}
	public void testGlobalAnnotation2() throws Exception {
		shouldMatch(Parser.GLOBAL_ANNOTATION,"@@ key=value= ");
	}
	public void testGlobalAnnotation3() throws Exception {
		shouldNotMatch(Parser.GLOBAL_ANNOTATION," @@ key=value");
	}
	public void testGlobalAnnotation4() throws Exception {
		shouldNotMatch(Parser.GLOBAL_ANNOTATION,"@@ key:value");
	}
	public void testGlobalAnnotation5() throws Exception {
		shouldNotMatch(Parser.GLOBAL_ANNOTATION,"@ key=value");
	}
	public void testLocalAnnotation1() throws Exception {
		shouldMatch(Parser.LOCAL_ANNOTATION,"@key=value");
	}
	public void testLocalAnnotation2() throws Exception {
		shouldMatch(Parser.LOCAL_ANNOTATION,"@ key=value= ");
	}
	public void testLocalAnnotation3() throws Exception {
		shouldNotMatch(Parser.LOCAL_ANNOTATION," @ key=value");
	}
	public void testLocalAnnotation4() throws Exception {
		shouldNotMatch(Parser.LOCAL_ANNOTATION,"@ key:value");
	}
	public void testLocalAnnotation5() throws Exception {
		shouldNotMatch(Parser.LOCAL_ANNOTATION,"@@ key=value");
	}
	public void testListOfNames1() throws Exception {
		shouldMatch(Parser.LIST_OF_NAMES,"a");
	}
	public void testListOfNames2() throws Exception {
		shouldMatch(Parser.LIST_OF_NAMES,"a,b");
	}
	public void testListOfNames3() throws Exception {
		shouldNotMatch(Parser.LIST_OF_NAMES,"a,b,");
	}
	public void testListOfNames4() throws Exception {
		shouldMatch(Parser.LIST_OF_NAMES,"abc");
	}
	public void testListOfNames5() throws Exception {
		shouldMatch(Parser.LIST_OF_NAMES,"a8c");
	}
	public void testListOfNames6() throws Exception {
		shouldNotMatch(Parser.LIST_OF_NAMES,"8c");
	}
	public void testListOfNames7() throws Exception {
		shouldMatch(Parser.LIST_OF_NAMES,"_8c");
	}
	public void testListOfNames8() throws Exception {
		shouldMatch(Parser.LIST_OF_NAMES,"_8c,ab");
	}
	public void testListOfNames9() throws Exception {
		shouldNotMatch(Parser.LIST_OF_NAMES,"a,8a");
	}
	public void testListOfNames10() throws Exception {
		shouldMatch(Parser.LIST_OF_NAMES,"c_8");
	}
	public void testListOfNames11() throws Exception {
		shouldMatch(Parser.LIST_OF_NAMES,"_8c, ab ,de , gg");
	}
	public void testQuery1() throws Exception {
		shouldMatch(Parser.QUERY,"test[in]");
	}
	public void testQuery2() throws Exception {
		shouldMatch(Parser.QUERY,"test2[out]");
	}
	public void testQuery3() throws Exception {
		shouldMatch(Parser.QUERY,"_test2[in,out]");
	}
	public void testQuery4() throws Exception {
		shouldMatch(Parser.QUERY,"not test[in,in]");
	}
	public void testQuery5() throws Exception {
		shouldMatch(Parser.QUERY,"not   test[in,in]");
	}

	public void testCondition1() throws Exception {
		shouldMatch(Parser.CONDITION1,"cond2[42,bbb]");
	}
	public void testExternalFactStore1() throws Exception {
		shouldMatch(Parser.EXTERNAL,"external factstore1: cond[java.util.Date,long]");
	}
	public void testExternalFactStore2() throws Exception {
		shouldNotMatch(Parser.EXTERNAL,"externa factstore1: cond[java.util.Date,long]");
	}
	public void testExternalFactStore3() throws Exception {
		shouldMatch(Parser.EXTERNAL,"external factstore_1: cond[java.util.Date]");
	}
	public void testExternalFactStore4() throws Exception {
		shouldMatch(Parser.EXTERNAL,"external factstore_1: cond[int]");
	}
	public void testExternalFactStore5() throws Exception {
		shouldNotMatch(Parser.EXTERNAL,"external factstore_1:cond[int]");
	}
	public void testExternalFactStore6() throws Exception {
		shouldNotMatch(Parser.EXTERNAL,"external factstore_1 cond[int]");
	}
	public void testImport1() throws Exception {
		shouldMatch(Parser.IMPORT,"import Person");
	}
	public void testImport2() throws Exception {
		shouldMatch(Parser.IMPORT,"import com.example.Person");
	}
	public void testImport3() throws Exception {
		shouldMatch(Parser.IMPORT,"import com.example.* ");
	}
	public void testImport4() throws Exception {
		shouldMatch(Parser.IMPORT,"import com.example.* ; ");
	}
	public void testImport5() throws Exception {
		shouldNotMatch(Parser.IMPORT,"import com.example.* , ");
	}
}
