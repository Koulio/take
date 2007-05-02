/*
 * Copyright (C) 2006 <A href="http://www-ist.massey.ac.nz/JBDietrich" target="_top">Jens Dietrich</a>
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

package nz.org.take.compiler;

import java.util.List;

import org.apache.log4j.Logger;

import nz.org.take.KnowledgeBase;
import nz.org.take.Query;
import nz.org.take.compiler.reference.DefaultCompiler;



/**
 * Compiler interface. Generates classes for a kb.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public interface Compiler {
		
	/**
	 * Compile the kb with an explicit list of queries.
	 * @param kb a knowledge base
	 * @param queries a list of query
	 * @param location the location where generated sources and compiled code will be stored
	 * @param packageName the '.'-separator packagename of the class to be generated
	 * @param className the class that will be generated
	 * @throws CompilerException
	 */
	public void compile (KnowledgeBase kb,List<Query> queries,Location location,String packageName,String className) throws CompilerException ;
	/**
	 * Compile the kb with the list of queries in the kb.
	 * @param kb a knowledge base
	 * @param location the location where generated sources and compiled code will be stored
	 * @param packageName the '.'-separator packagename of the class to be generated
	 * @param className the class that will be generated
	 * @throws CompilerException
	 */
	public void compile (KnowledgeBase kb,Location location,String packageName,String className) throws CompilerException ;
	/**
	 * Get the name generator.
	 * @return Returns the nameGenerator.
	 */
	public NameGenerator getNameGenerator();
	/**
	 * Set the name generator.
	 * @param nameGenerator The nameGenerator to set.
	 */
	public void setNameGenerator(NameGenerator nameGenerator);
	/**
	 * Add a source code transformer.
	 * @param a transformer
	 */
	public void add(SourceTransformation transformer);
	/**
	 * Remove a source code transformer.
	 * @param a transformer
	 */
	public void remove(SourceTransformation transformer);
	/**
	 * Get a list of source transformers installed.
	 * @return a list of transformations
	 */
	public List<SourceTransformation> getSourceTransformers();
	
}
