/**
 * Copyright 2007 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */


package test.nz.org.take.compiler.scenario2;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import nz.org.take.KnowledgeBase;
import nz.org.take.KnowledgeSource;
import nz.org.take.TakeException;
import nz.org.take.script.ScriptKnowledgeSource;

/**
 * Script to generate a memory KB.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class GenerateKB implements KnowledgeSource {
	public static final String ROOT = "r";
	public static final int DEPTH=3;
		
		/**
		 * Generate the sources for the example.
		 * @param args
		 */
		public KnowledgeBase getKnowledgeBase() throws TakeException {
			ScriptKnowledgeSource KSrc = new ScriptKnowledgeSource(new StringReader(buildScript()));
			return KSrc.getKnowledgeBase();
		}
		/**
		 * Generate the script. 
		 */
		private String buildScript() {
			StringBuffer b = new StringBuffer(); 
			
			appendLine(b,"// script for test scenario 1");
			appendLine(b,"@@dc:date=30/02/2007");
			appendLine(b,"@@dc:creator=author jens dietrich");
			
			// variables
			appendLine(b,"var java.lang.String descendant,ancestor,x");
			
			// queries
			appendLine(b,"@take.compilerhint.slots=person1,person2");
			appendLine(b,"@take.compilerhint.method=isAncestor");
			appendLine(b,"@take.compilerhint.class=AncestorRelationship");
			appendLine(b,"query isAncestor|in,in|");
			
			// rules
			appendLine(b,"rule1: if isFather|descendant,ancestor| then isAncestor|descendant,ancestor|");
			appendLine(b,"rule2: if isAncestor|x,ancestor| and isFather|descendant,x| then isAncestor|descendant,ancestor|");
			
			// facts
			List<List<String>> generations = new ArrayList<List<String>>();
			int counter = 0;
			for (int i=0;i<DEPTH;i++) {
				List<String> previousGeneration = null;
				if (i==0) {
					previousGeneration = new ArrayList<String>();
					previousGeneration.add(ROOT);
					generations.add(previousGeneration);
				}
				else 
					previousGeneration = generations.get(generations.size()-1);
				
				List<String> newGeneration = new ArrayList<String>();
				for (String ancestor:previousGeneration) {
					for (int j=0;j<2;j++) {
						counter = counter+1;
						String descendant = ancestor+j;
						newGeneration.add(descendant);
						appendLine(b,"fact"+counter,": ","isFather|\"",descendant,"\",\"",ancestor,"\"|");
					}
				}
				generations.add(newGeneration);
				
			}
			
			return b.toString();
		}
		private void appendLine(StringBuffer b,String...strings ){
			for (String s:strings) {
				b.append(s);
				System.out.print(s);
			}
			b.append('\n');
			System.out.println();
		}


}
