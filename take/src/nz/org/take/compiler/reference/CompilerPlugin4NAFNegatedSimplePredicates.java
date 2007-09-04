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
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

import nz.org.take.Constant;
import nz.org.take.PropertyPredicate;
import nz.org.take.Query;
import nz.org.take.SimplePredicate;
import nz.org.take.Term;
import nz.org.take.Variable;
import nz.org.take.compiler.CompilerException;
import nz.org.take.rt.DerivationController;

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
		if (!ok)
			throw new CompilerException();
	}

	@Override
	public String createMethod(PrintWriter out, Query q) throws CompilerException {

		String templateName = "NAFNegatedSimplePredicate.vm";
		Template template = VelocitySupport.getTemplate(templateName);
		SimplePredicate p = (SimplePredicate)q.getPredicate();
		
		// bind template variables
		String methodName = getMethodName(q);
		String prereq = "\"negated prerequisite\"";
		///
		StringBuffer methodParameterDeclarations = new StringBuffer();
		StringBuffer methodParameterRefs = new StringBuffer();
		String aggregationVariable = null;
		List<String> queryParams = new ArrayList<String>();
		for (int i=0;i<q.getPredicate().getSlotTypes().length;i++) {
			if (q.getInputParams()[i]) {			
				queryParams.add(p.getSlotNames()[i]); 
				if (methodParameterDeclarations.length()>0) {
					methodParameterDeclarations.append(',');
					methodParameterRefs.append(',');
				}
				methodParameterDeclarations.append(p.getSlotTypes()[i].getName());
				methodParameterRefs.append(p.getSlotNames()[i]);
				methodParameterDeclarations.append(' ');
				methodParameterDeclarations.append(p.getSlotNames()[i]);
			}
			else {
				this.logger.warn("Output slot in query with NAF: " + p.getSlotNames()[i] + " in " + q);
			}
		}
		// add derivation controller
		if (methodParameterDeclarations.length()>0) {
			methodParameterDeclarations.append(',');
		}
		methodParameterDeclarations.append("DerivationController _derivation");
		
		// build unnegated query and add it to the agenda
		SimplePredicate unnegatedPredicate = p.copy();
		unnegatedPredicate.setNegated(false);		
		QueryRef query = new QueryRef(unnegatedPredicate,q.getInputParams(),queryParams);
		configNewQuery(query);
		owner.addToAgenda(query);
		
		// build method invocation
		StringWriter sout = new StringWriter();
		printInvocation(new PrintWriter(sout),query,false,false);
		sout.flush();
		
		VelocityContext context = new VelocityContext();
		context.put("query", q);
		context.put("methodname",methodName);
		context.put("resulttype", getClassName(p));
		context.put("resulttype_unneg",getClassName(unnegatedPredicate));
		context.put("templatename",templateName);
		context.put("methodParameterDeclarations",methodParameterDeclarations);
		context.put("methodParameterRefs",methodParameterRefs);
		context.put("invocation",sout.getBuffer());
		context.put("prereq", prereq);
		context.put("properties", queryParams);
		
		
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
		return true;
	}

}
