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
import nz.org.take.compiler.CompilerException;

/**
 * Abstract plugin used to generate code for PropertyPredicates.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class CompilerPlugin4PropertyPredicates extends CompilerPlugin {
	
	public static final String TEMPLATE_O11 = "PropertyPredicateONE2ONE_11.vm";
	public static final String TEMPLATE_O10 = "PropertyPredicateONE2ONE_10.vm";
	public static final String TEMPLATE_M10 = "PropertyPredicateONE2MANY_10.vm";
	public static final String TEMPLATE_M11 = "PropertyPredicateONE2MANY_10.vm";
	public static final String TEMPLATE_O11N = "PropertyPredicateONE2ONE_11_neg.vm";
	public static final String TEMPLATE_M11N = "PropertyPredicateONE2MANY_10_neg.vm";
	public static final String TEMPLATE_M10N = "PropertyPredicateONE2MANY_10_neg.vm";
	public static final String TEMPLATE_O10N = "PropertyPredicateONE2ONE_10_neg.vm";
	
	public CompilerPlugin4PropertyPredicates(DefaultCompiler owner) {
		super(owner);
		
	}

	@Override
	public void checkPrerequisites(Query q) throws CompilerException {
		boolean[] in = q.getInputParams();
		if (in.length!=2)
			throw new CompilerException("Facts with property predicates have to have 2 terms. Query is: " + q);
		// if inverse properties are supported, this rule can be relaxed
		if (!in[0]) 
			throw new CompilerException("For property predicates, the first parameter must be an input parameter. Query is: " + q);

	}

	@Override
	public String createMethod(PrintWriter out, Query q) throws CompilerException {

		PropertyPredicate p = (PropertyPredicate)q.getPredicate();
		boolean param2known = q.getInputParams()[1]; // true - both slots known
			
		String templateName = this.getTemplateName(param2known, p.isOne2One(),p.isNegated());
		Template template = TemplateManager.getInstance().getTemplate(templateName);
		
		// bind template variables
		String methodName = getMethodName(q);
		Slot[] slots = this.buildSlots(q.getPredicate());
		
		VelocityContext context = new VelocityContext();
		context.put("query", q);
		context.put("methodname",methodName);
		context.put("sourcetype",p.getSlotTypes()[0].getName());
		context.put("targettype", p.getSlotTypes()[1].getName());
		context.put("accessor", p.getProperty().getReadMethod().getName());
		context.put("resulttype", getClassName(p));
		context.put("slot1", slots[0].name);
		context.put("slot2", slots[1].name);
		context.put("property", "\"property " + p.getProperty().getName() + "\"");
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
			//this.logger.debug(x.getMessage());
			return false;
		}
		// check predicate
		return q.getPredicate() instanceof PropertyPredicate;
	}
	
	private String getTemplateName(boolean param2known, boolean isOne2One,boolean isNegated) {
		if (param2known&&isOne2One&&!isNegated)
			return TEMPLATE_O11;
		else if (param2known&&!isOne2One&&!isNegated)
			return TEMPLATE_M11;		
		else if (!param2known&&isOne2One&&!isNegated)
			return TEMPLATE_O10;
		else if (!param2known&&!isOne2One&&!isNegated)
			return TEMPLATE_M10;
		else if (param2known&&isOne2One&&isNegated)
			return TEMPLATE_O11N;
		else if (param2known&&!isOne2One&&isNegated)
			return TEMPLATE_M11N;	
		else if (!param2known&&isOne2One&&isNegated)
				return TEMPLATE_O10N;
		else if (!param2known&&!isOne2One&&isNegated)
				return TEMPLATE_M10N;

		return null;
	}
}
