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
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import nz.org.take.PropertyPredicate;
import nz.org.take.Query;
import nz.org.take.SimplePredicate;
import nz.org.take.compiler.CompilerException;

/**
 * Abstract plugin used to generate code for PropertyPredicates.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class CompilerPlugin4NAFNegatedSimplePredicates extends CompilerPlugin {
	

	public CompilerPlugin4NAFNegatedSimplePredicates(DefaultCompiler owner) {
		super(owner);
		
	}

	@Override
	public void checkPrerequisites(Query q) throws CompilerException {
		boolean ok = q.getPredicate() instanceof SimplePredicate;
		ok = ok && q.getPredicate().isNegated();
		for (boolean b:q.getInputParams()) {
			ok = ok && b; 	// only input parameters expected
		}
	}

	@Override
	public String createMethod(PrintWriter out, Query q) throws CompilerException {

		String templateName = "NAFNegatedSimplePredicate.vm";
		Template template = VelocitySupport.getTemplate(templateName);
		SimplePredicate p = (SimplePredicate)q.getPredicate();
		
		// bind template variables
		String methodName = getMethodName(q);
		
		VelocityContext context = new VelocityContext();
		context.put("query", q);
		context.put("methodname",methodName);
		context.put("resulttype", getClassName(p));
		context.put("templatename",templateName);
		
		try {
			template.merge(context, out);
		} catch (Exception x) {
			throw new CompilerException("Problem merging compilation template",x);
		} 
		return methodName;
	}

	@Override
	public boolean supports(Query q) {
		// check parameters
		try {
			this.checkPrerequisites(q);			
		}
		catch (CompilerException x) {
			this.logger.error(x.getMessage(), x);
			return false;
		}
		// check predicate
		return q.getPredicate() instanceof PropertyPredicate;
	}

}
