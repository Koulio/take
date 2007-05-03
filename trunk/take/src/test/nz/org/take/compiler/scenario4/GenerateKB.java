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


package test.nz.org.take.compiler.scenario4;

import java.io.FileReader;
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
			KnowledgeBase kb = reader.read(new FileReader("src/test/nz/org/take/compiler/scenario4/rules4.take"));
			return kb;
		}
}
