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
import nz.org.take.AggregationFunction;
import nz.org.take.Aggregations;
import nz.org.take.Constant;
import nz.org.take.Predicate;
import nz.org.take.Query;
import nz.org.take.Term;
import nz.org.take.Variable;
import nz.org.take.compiler.CompilerException;

/**
 * Abstract superclass for template based function generators.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public abstract class AbstractTemplatedBasedAggregationFunctionGenerator implements AggregationFunctionGenerator {

	public abstract String getTemplateName() ;
	
	public Query createAggregationFunction(PrintWriter out,AggregationFunction f, CompilerUtils owner)throws CompilerException {
		String templateName = getTemplateName();
		Template template = TemplateManager.getInstance().getTemplate(templateName);
		Predicate predicate = f.getQuery().getPredicate();
		
		// build the query and the variable list
		List<String> queryParams = new ArrayList<String>();
		StringBuffer methodParams = new StringBuffer();
		String aggregationVariable = null;
		boolean[] io = new boolean[predicate.getSlotTypes().length];
		for (int i=0;i<io.length;i++) {
			io[i] = i!=f.getVariableSlot();
			Term t = f.getQuery().getTerms()[i];
			if (io[i]) {			
				if (t instanceof Variable) {
					queryParams.add("arg"+i); 
					if (methodParams.length()>0) {
						methodParams.append(',');
					}
					methodParams.append(t.getType().getName());
					methodParams.append(' ');
					methodParams.append("arg");
					methodParams.append(i);
				}
				else if (t instanceof Constant) {
					queryParams.add(owner.getRef(owner.getNameGenerator().getConstantRegistryClassName(),(Constant)t));
				}
				// complex terms are not yet supported
			}
			else {
				Slot slot = owner.buildSlot(predicate,i);
				aggregationVariable = slot.getVar();
			}
		}
		
		QueryRef query = new QueryRef(predicate,io,queryParams);
		
		// query invocation
		StringWriter sout = new StringWriter();
		owner.printInvocation(new PrintWriter(sout),query,false,false);
		sout.flush();
		
		// bind template variables
		VelocityContext context = new VelocityContext();
		String methodName = owner.getMethodName(f);
		context.put("query", query);
		context.put("methodname",methodName);
		context.put("methodparameters",methodParams);
		context.put("resulttype", f.getReturnType());
		context.put("templatename",templateName);
		context.put("invocation",sout.getBuffer().toString());
		context.put("varslot",aggregationVariable);
		context.put("resultsettype",owner.getClassName(predicate));
		context.put("function",f.getName());
		
		try {
			template.merge(context, out);
		} catch (Exception x) {
			throw new CompilerException("Problem merging compilation template",x);
		} 
		
		return query;
	}

	public abstract Aggregations getSupportedAggregation() ;
	
}
