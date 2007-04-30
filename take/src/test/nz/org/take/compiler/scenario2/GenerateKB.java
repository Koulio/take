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


package test.nz.org.take.compiler.scenario2;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.BasicConfigurator;
import nz.org.take.KnowledgeBase;
import nz.org.take.script.KnowledgeBaseReader;

/**
 * Script to generate a KB.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class GenerateKB {
	public static final String ROOT = "r";
	public static final int DEPTH=3;
		
		/**
		 * Generate the sources for the example.
		 * @param args
		 */
		public static KnowledgeBase buildKB() throws Exception {
			BasicConfigurator.configure();			
			// generate kb
			KnowledgeBaseReader reader = new KnowledgeBaseReader();
			KnowledgeBase kb = reader.read(new StringReader(buildScript()));
			return kb;
		}
		/**
		 * Generate the script. 
		 */
		private static String buildScript() {
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
			appendLine(b,"query isAncestor(in,in)");
			
			// rules
			appendLine(b,"rule1: if isFather(descendant,ancestor) then isAncestor(descendant,ancestor)");
			appendLine(b,"rule2: if isAncestor(x,ancestor) and isFather(descendant,x) then isAncestor(descendant,ancestor)");
			
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
						appendLine(b,"fact"+counter,": ","isFather(\"",descendant,"\",\"",ancestor,"\")");
					}
				}
				generations.add(newGeneration);
				
			}
			
			return b.toString();
		}
		private static void appendLine(StringBuffer b,String...strings ){
			for (String s:strings) {
				b.append(s);
				System.out.print(s);
			}
			b.append('\n');
			System.out.println();
		}
}
