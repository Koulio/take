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

package nz.org.take.compiler.reference;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import nz.org.take.KnowledgeBase;
import nz.org.take.Query;
import nz.org.take.compiler.CompilerException;
import nz.org.take.compiler.NameGenerator;
import nz.org.take.compiler.SourceTransformation;

/**
 * Compiler helper that generates the code for a certain types of query. 
 * Usually, a helper is specific for one type of predictate, and the code generated takes advantage of the 
 * semantics of this predicate.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public abstract class CompilerPlugin extends CompilerUtils {
	protected DefaultCompiler owner = null;
	// constant to be used for the tmp variable to store the return value of methods to be generated 
	protected static String RESULT = "result";
	
	public CompilerPlugin(DefaultCompiler owner) {
		super();
		this.owner = owner;
	}
	
	/**
	 * Create a method for a query. 
	 * @return the name of the method created
	 * @param out an output stream
	 * @param q a query
	 */
	public abstract String createMethod(PrintWriter out, Query q) throws CompilerException ;
	
	/**
	 * Check whether this plugin can create code for the given query. 
	 * Usually, this means investigate the predicate type.
	 * @param q a query
	 */
	public abstract boolean supports(Query q) ;
	
	/**
	 * Check the prerequisites. E.g., whether code can be generated for the 
	 * set of input variables in the query.  If this fails, throw an exception.
	 * @param q a query
	 */
	public abstract void checkPrerequisites(Query q) throws CompilerException;

	@Override
	public Map<String, String> getMethodNames4QueriesFromAnnotations() {
		return owner.getMethodNames4QueriesFromAnnotations();
	}

	@Override
	public NameGenerator getNameGenerator() {
		return owner.getNameGenerator();
	}
	@Override
	public KnowledgeBase getKB() {
		return owner.getKB();
	}
	@Override
	public List<SourceTransformation> getSourceTransformers() {
		return owner.getSourceTransformers();
	}

	@Override
	public String getVarName4DerivationController() {
		return owner.getVarName4DerivationController();
	}
	
	

}
