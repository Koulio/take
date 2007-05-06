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

import java.util.List;
import java.util.Map;
import nz.org.take.compiler.NameGenerator;
import nz.org.take.compiler.SourceTransformation;

/**
 * Compiler helper that generates the code for a certain types of query. 
 * Usually, a helper is specific for one type of predictate, and the code generated takes advantage of the 
 * semantics of this predicate.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public abstract class CompilerPlugin extends CompilerUtils {
	private DefaultCompiler owner = null;

	public CompilerPlugin(DefaultCompiler owner) {
		super();
		this.owner = owner;
	}

	@Override
	public Map<String, String> getMethodNames4QueriesFromAnnotations() {
		return owner.getMethodNames4QueriesFromAnnotations();
	}

	@Override
	public NameGenerator getNameGenerator() {
		return owner.getNameGenerator();
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
