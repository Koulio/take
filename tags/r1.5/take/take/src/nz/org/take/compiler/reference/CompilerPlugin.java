/**
 * Copyright 2007 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */

package nz.org.take.compiler.reference;

import java.io.PrintWriter;
import java.util.List;
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
