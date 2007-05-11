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
import nz.org.take.JPredicate;
import nz.org.take.Query;
import nz.org.take.compiler.CompilerException;

/**
 * Plugin used to generate code for JPredicates.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class CompilerPlugin4JPredicates extends CompilerPlugin {

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
		Slot[] inSlots = this.buildInputSlots(q);
		JPredicate p = (JPredicate)q.getPredicate();
		
		printMethodComment(out, "Method generated for query " + p, inSlots,"an interator for instances of " + getClassName(p));

		// start header
		this.printGenericType(out, "private ResourceIterator", getClassName(p));
		String methodName = getMethodName(q);
		out.print(methodName);

		printParameters(out, inSlots, true,true,false);
		// end params
		out.println("{");
		
		// variable to cache derivation depth
		out.print("final int _derivationlevel=");
		out.print(this.getVarName4DerivationController());
		out.println(".getDepth();");
		
		// start condition
		out.print("if (");		
		String target = inSlots[0].var;
		String[] params = new String[inSlots.length-1];
		for (int i=0;i<params.length;i++)
			params[i]=inSlots[i+1].var;
		this.printMethodInvocation(out, p.getMethod().getName(), target,params);
		out.println(" ){");
		
		// log
		out.print("_derivation.log(\"");
		out.print(p.getMethod());
		out.println("\");");		
		
		out.print(getClassName(p));
		out.print(' ');
		out.print(RESULT);
		out.print('=');
		this.printContructorInvocation(out,getClassName(p));
		out.println(';');
		
		// assign input vars
		for (Slot slot:inSlots) {
				printVariableAssignment(out,RESULT,slot.name,slot.var);
		}
		out.print("return new SingletonIterator<");
		out.print(getClassName(p));
		out.print(">(");
		out.print(RESULT);
		out.println(");");
		out.println('}');
		out.println("return EmptyIterator.DEFAULT;");
		out.println('}');
		return methodName;
	}

	@Override
	public boolean supports(Query q) {
		return q.getPredicate() instanceof JPredicate ;
	}

}
