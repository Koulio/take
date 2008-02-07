/**
 * Copyright 2007 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */

package test.nz.org.take.compiler.annotations;

import java.util.Map;
import test.nz.org.take.TakeTestCase;
import test.nz.org.take.compiler.annotations.generated.KB;
import nz.org.take.deployment.KnowledgeBaseManager;
import nz.org.take.nscript.AnnotationPropagationPolicy;
import nz.org.take.nscript.ScriptKnowledgeSource;
import junit.framework.TestCase;

/**
 * Tests for this scenario. 
 * For now, the classes have to generated and compiled manually.
 * For generation, use the script GenerateClasses.
 * Code pretty printing is used.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class Tests extends TakeTestCase
{
	
	/**
	 * Construct new test instance
	 *
	 * @param name the test name
	 */
	public Tests(String name)
	{
		super(name);
	}


	
	/**
	 * Get the kb.
	 */
	protected KB getKB(AnnotationPropagationPolicy ap) throws Exception {
		ScriptKnowledgeSource source = new ScriptKnowledgeSource(Tests.class.getResourceAsStream("/test/nz/org/take/compiler/annotations/rules.take"));
		source.setAnnotationPolicy(ap);
		KnowledgeBaseManager<KB> kbm = new KnowledgeBaseManager<KB>();
		return kbm.getKnowledgeBase(
				KB.class, 
				source
		); 
	}



	/**
	 * Test 1.
	 * @throws Exception 
	 */
	public void testLocalAnnotations1() throws Exception{
		Map<String,String> annotations = getKB(AnnotationPropagationPolicy.ALL).getAnnotations("rule1");
		assertEquals(3,annotations.size());
		assertEquals("a description",annotations.get("description")); // overridden global annotation
		assertEquals("2007-09-19",annotations.get("date"));
		assertEquals("jens dietrich",annotations.get("author"));
	}
	/**
	 * Test 2.
	 * @throws Exception 
	 */
	public void testLocalAnnotations2() throws Exception{
		Map<String,String> annotations = getKB(AnnotationPropagationPolicy.NONE).getAnnotations("rule1");
		assertEquals(2,annotations.size());
		assertEquals("a description",annotations.get("description")); // overridden global annotation
		assertEquals("2007-09-19",annotations.get("date"));
	}
	/**
	 * Test 3.
	 * @throws Exception 
	 */
	public void testGlobalAnnotations() throws Exception{
		Map<String,String> annotations = getKB(AnnotationPropagationPolicy.ALL).getAnnotations();
		assertEquals(2,annotations.size());
		assertEquals("test knowledge base",annotations.get("description"));
		assertEquals("jens dietrich",annotations.get("author"));
	}

	
}
	