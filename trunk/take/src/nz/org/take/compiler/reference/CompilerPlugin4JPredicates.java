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
import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import nz.org.take.JPredicate;
import nz.org.take.Query;
import nz.org.take.compiler.CompilerException;

/**
 * Plugin used to generate code for JPredicates.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class CompilerPlugin4JPredicates extends CompilerPlugin {
	
	// unnegated
	public static final String TEMPLATE1 = "JPredicate_11.vm";
	// negated
	public static final String TEMPLATE2 = "JPredicate_11_neg.vm";
	
	

	public CompilerPlugin4JPredicates(DefaultCompiler owner) {
		super(owner);
		
	}

	@Override
	public void checkPrerequisites(Query q) throws CompilerException {
		boolean[] in = q.getInputParams();
		for (boolean f:in) {
			if (!f)
				throw new CompilerException("For queries with JPredicates, all parameters have to be input parameters. Query is: " + q);
		}
	}

	@Override
	public String createMethod(PrintWriter out, Query q) throws CompilerException {

		JPredicate p = (JPredicate)q.getPredicate();
			
		// load and (lazy) init templates
		String templateName = p.isNegated()?TEMPLATE2:TEMPLATE1;
		Template template = VelocitySupport.getTemplate(templateName);
		
		// bind template variables
		String methodName = getMethodName(q);
		Slot[] slots = this.buildSlots(q.getPredicate());
		
		StringBuffer args = new StringBuffer();
		for (int i=1;i<slots.length;i++) {
			if (i>1)
				args.append(',');
			args.append(slots[i].getVar());
		}
		
		VelocityContext context = new VelocityContext();
		context.put("query", q);
		context.put("methodname",methodName);
		context.put("method",p.getMethod());
		context.put("slots",slots);
		context.put("resulttype", getClassName(p));
		context.put("templatename",templateName);
		context.put("target",slots[0].getVar());
		context.put("args",args.toString());
		
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
			return false;
		} 
		// check predicate
		return q.getPredicate() instanceof JPredicate;
	}
}
