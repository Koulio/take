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
		Template template = TemplateManager.getInstance().getTemplate(templateName);
		
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
			//this.logger.debug(x.getMessage());
			return false;
		} 
		// check predicate
		return q.getPredicate() instanceof Comparison;
	}

}
