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
import nz.org.take.Comparison;
import nz.org.take.Query;
import nz.org.take.compiler.CompilerException;

/**
 * Plugin used to generate code for comparison predicates.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class CompilerPlugin4Comparisons extends CompilerPlugin {
	
	
	public CompilerPlugin4Comparisons(DefaultCompiler owner) {
		super(owner);
		
	}

	@Override
	public void checkPrerequisites(Query q) throws CompilerException {
		boolean[] in = q.getInputParams();
		for (boolean f:in) {
			if (!f)
				throw new CompilerException("For queries using comparisons, all parameters have to be input parameters. Query is: " + q);
		}
	}

	@Override
	public String createMethod(PrintWriter out, Query q) throws CompilerException {

		Comparison p = (Comparison)q.getPredicate();
			
		// load and (lazy) init template		
		String templateName = "Comparison_11.vm";
		Template template = VelocitySupport.getTemplate(templateName);
		
		// bind template variables
		Slot[] slots = this.buildSlots(q.getPredicate());
		String methodName = getMethodName(q);
		VelocityContext context = new VelocityContext();
		context.put("query", q);
		context.put("methodname",methodName);
		context.put("symbol",p.getSymbol());
		context.put("slot1",slots[0]);
		context.put("slot2",slots[1]);
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
			return false;
		} 
		// check predicate
		return q.getPredicate() instanceof Comparison;
	}

}
