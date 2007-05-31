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
import java.util.*;
import org.apache.log4j.Logger;
import nz.org.take.*;
import nz.org.take.compiler.CompilerException;
import nz.org.take.compiler.Location;
import nz.org.take.compiler.NameGenerator;
import nz.org.take.compiler.SourceTransformation;


/**
 * Abstract class with utilities used for code generation.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public abstract class CompilerUtils {
	protected Logger logger = null;
	
	 public CompilerUtils() {
		super();
		logger = Logger.getLogger(this.getClass());
	}
	/**
	  * Get the name of the variable for the derivayion controller.
	  * @return a string
	  */
	public abstract String getVarName4DerivationController() ;
	/**
	  * Get the knowledge base for which code is to be generated.
	  * @return a kb
	  */
	public abstract KnowledgeBase getKB() ;
	/**
	 * Get the method names gathered from in query annotations.
	 * @return a map
	 */
	public abstract  Map<String, String> getMethodNames4QueriesFromAnnotations();
	/**
	 * Get the name generator.
	 * @return Returns the nameGenerator.
	 */
	public abstract NameGenerator getNameGenerator() ;
	/**
	 * Get a logger.
	 * @return a logger.
	 */
	public  Logger getLogger()  {
		return logger;
	}
	
	/**
	 * Get a list of source transformers installed.
	 * @return a list of transformations
	 */
	public abstract List<SourceTransformation> getSourceTransformers() ;

	/**
	 * Create the list of predicates referenced in the kb.
	 * @param kb a knowledge base
	 * @return a list of predicates
	 */
	protected Collection<Predicate> findPredicates(KnowledgeBase kb) {
		Collection<Predicate> predicates = new HashSet<Predicate>();
		for (KnowledgeElement e: kb.getElements()) {
			if (e instanceof DerivationRule) {
				DerivationRule r = (DerivationRule)e;
				for (Prerequisite p:r.getBody()) {
					predicates.add(p.getPredicate());
				}
			}
			predicates.add(e.getPredicate());
		}
		return predicates;
	}

	protected Slot buildSlot(Predicate p, int i) {
		Slot s = new Slot();
		s.position = i;
		s.name = p.getSlotNames()[i];
		s.type = getTypeName(p.getSlotTypes()[i]);
		s.accessor = this.getNameGenerator().getAccessorNameForSlot(p, i);
		s.mutator = this.getNameGenerator().getMutatorNameForSlot(p, i);
		s.var = this.getNameGenerator().getVariableNameForSlot(p, i);
		return s;
	}
	
	/**
	 * Get the type name for a class.
	 * 
	 */
	protected String getTypeName(Class clazz) {
		if (clazz==Boolean.class)
			return Boolean.TYPE.getName();
		
	 	else if (clazz==Character.class)
	 		return Character.TYPE.getName();
		
	 	else if (clazz==Byte.class)
	 		return Byte.TYPE.getName();
		
	 	else if (clazz==Short.class)
	 		return Short.TYPE.getName();
		
		else if (clazz==Integer.class)
			return Integer.TYPE.getName();
		
		else if (clazz==Long.class)
			return Long.TYPE.getName();
		
		else if (clazz==Float.class)
			return Float.TYPE.getName();
		
		else if (clazz==Double.class)
			return Byte.TYPE.getName();
		
		else return clazz.getName();
	}

	/**
	 * Build the input slots for a query.
	 * @param q
	 * @return an array of slots
	 */
	protected Slot[] buildInputSlots(Query q) {
		return this.buildSlots(q.getPredicate(), q.getInputParams());
	}

	/**
	 * Create an array consisting of slots that are the input params for calling
	 * a method asociated by a fact.
	 * @param f  a fact
	 * @param bindings  bindings
	 * @return an array of slots
	 */
	protected QueryRef buildQuery(Fact f, Bindings bindings) {
		Term[] terms = f.getTerms();
		boolean[] io = new boolean[terms.length];
		List<String> params = new ArrayList<String>();
		for (int i = 0; i < io.length; i++) {
			String ref = bindings.getRef(terms[i]);
			io[i] = ref != null;
			if (ref != null)
				params.add(ref);
		}
		QueryRef q = new QueryRef((Predicate) f.getPredicate(), io, params);
		configNewQuery(q);		
		return q;
	}

	/**
	 * Build the output slots for a query.
	 * 
	 * @param q
	 * @return an array of slots
	 */
	protected Slot[] buildOutputSlots(Query q) {
		boolean[] inputParams = q.getInputParams();
		boolean[] inv = new boolean[inputParams.length];
		for (int i = 0; i < inputParams.length; i++)
			inv[i] = !inputParams[i];
		return this.buildSlots(q.getPredicate(), inv);
	}

	/**
	 * Build the slots for some slots of a predicate.
	 * 
	 * @param p
	 * @param params
	 * @return
	 */
	protected Slot[] buildSlots(Predicate p, boolean[] params) {
		int l = 0;
		for (boolean b : params)
			if (b)
				l = l + 1;
		Slot[] slots = new Slot[l];
		int c = 0;
		for (int i = 0; i < p.getSlotTypes().length; i++) {
			if (params[i]) {
				slots[c] = buildSlot(p, i);
				c = c + 1;
			}
		}
		return slots;
	}

	/**
	 * Build the slots for a predicate. Public, may be called from templates.
	 * 
	 * @param p
	 * @return
	 */
	protected Slot[] buildSlots(Predicate p) {
		Slot[] slots = new Slot[p.getSlotTypes().length];
		for (int i = 0; i < p.getSlotTypes().length; i++)
			slots[i] = buildSlot(p, i);
		return slots;
	}

	protected Object getRuleRef(KnowledgeElement cs) {
		return cs.getId();
	}

	/**
	* Configure a query built by the compiler.
	* This means to add compiler hint annotations.
	* @param q
	*/
	protected void configNewQuery(QueryRef q) {
		String methodNameAnnotation = this.getMethodNames4QueriesFromAnnotations().get(this.createQueryHash(q));
		if (methodNameAnnotation!=null)
			q.addAnnotation(AnnotationKeys.TAKE_GENERATE_METHOD,methodNameAnnotation);
	}
	/**
	 * Create a string identifying a query.
	 * @param q
	 * @return
	 */
	protected String createQueryHash(Query q) {
		StringBuffer b = new StringBuffer();
		b.append(q.getPredicate().getName());
		b.append('_');
		for (boolean f:q.getInputParams()) {
			b.append(f?'1':'0');
		}
		return b.toString();
	} 
	/**
	 * Print a one line comment.
	 * @param out a print writer
	 * @param tokens (will be converted to strings using toString)
	 */
	protected void printOneLineComment(PrintWriter out, Object... tokens) {
		out.print("// ");
		for (Object t : tokens)
			out.print(t);
		out.println();
	}

	/**
	 * Print a parameter list generated from an array of input slots.
	 * @param out  a print writer
	 * @param islots an array of slots
	 * @param isDeclaration whether to include the type declarations
	 */
	protected void printParameters(PrintWriter out, Slot[] islots, boolean isDeclaration, boolean includeExplanation, boolean resetDerivationLevel) {
		out.print("(");
		boolean f = true;
		for (Slot slot : islots) {
			if (f)
				f = false;
			else
				out.print(",");
			if (isDeclaration) {
				out.print("final ");
				out.print(slot.type);
				out.print(" ");
			}
			out.print(slot.var);
		}
		// add reference to the explanation
		if (includeExplanation) {
			if (f)
				f = false;
			else
				out.print(",");
			
			if (isDeclaration) {	
				out.print("final DerivationController ");
				out.print(this.getVarName4DerivationController());
			}
			else {
				out.print(this.getVarName4DerivationController());
				if (resetDerivationLevel) {
					out.print(".reset(");
					out.print("_derivationlevel");
					out.print(")");
				}
				out.println();
			}
		}
		out.println(")");
	}

	/**
	 * Print a Method comment with javadoc tags generated from an array of
	 * slots.
	 * @param outa print writer
	 * @param comment the Methodcomment
	 * @param islots  the input/param slots
	 * @param returnComment  comment for the return tag
	 */
	protected void printMethodComment(PrintWriter out, String comment, Slot[] islots, String returnComment) {
		out.println("/**");
		out.print(" * ");
		out.println(comment);
		for (Slot slot : islots) {
			out.print(" * @param ");
			out.print(slot.var);
			out.print(" ");
			out.print("input parameter generated from slot ");
			out.println(slot.position);
		}
		out.print(" * @return ");
		out.println(returnComment);
		out.println("*/");
	}

	/**
	 * Print a generic type followed by a white space.
	 * @param out a print writer
	 * @param type the type name
	 * @param param the parameter type
	 */
	protected void printGenericType(PrintWriter out, String type, String param) {
		out.print(type);
		out.print('<');
		out.print(param);
		out.print("> ");
	}

	/**
	 * Print a constructor.
	 * @param out a print writer
	 * @param type  the type name
	 * @param genTypeParam the generic type parameter
	 * @param params the parameters
	 */
	protected void printContructorInvocation(PrintWriter out, String type, String genTypeParam, String... params) {
		out.print("new ");
		out.print(type);
		if (genTypeParam != null) {
			out.print('<');
			out.print(genTypeParam);
			out.print("> ");
		}
		out.print("(");
		boolean f = true;
		for (String param : params) {
			if (f)
				f = false;
			else
				out.print(",");
			out.print(param);
		}
		out.print(")");
	}

	/**
	 * Print a constructor invocation for a class with two generic types.
	 * @param out a print writer
	 * @param type   the type name
	 * @param genType1 the generic type parameter1
	 * @param genType2   the generic type parameter2
	 * @param params the parameters
	 */
	protected void printContructorInvocation2(PrintWriter out, String type, String genTypeParam1, String genTypeParam2, String... params) {
		out.print("new ");
		out.print(type);
		out.print('<');
		out.print(genTypeParam1);
		out.print(',');
		out.print(genTypeParam2);
		out.print("> ");
		out.print("(");
		boolean f = true;
		for (String param : params) {
			if (f)
				f = false;
			else
				out.print(",");
			out.print(param);
		}
		out.print(")");
	}

	/**
	 * Print the code used to invoke the constructor.
	 * @param out a print writer
	 * @param queryRef the query + the parameters used
	 */
	protected void printConstructorInvocation(PrintWriter out, QueryRef queryRef) {
		out.print("new ");
		out.print(getClassName(queryRef.getPredicate()));
		out.print("(");
		boolean f = true;
		for (String param : queryRef.getParamRefs()) {
			if (f)
				f = false;
			else
				out.print(',');
			out.print(param);
		}
		out.print(")");
	}

	/**
	 * Print a constructor invocation for a class with two generic types.
	 * @param out  a print writer
	 * @param type  the type name
	 */
	protected void printContructorInvocation(PrintWriter out, String type) {
		out.print("new ");
		out.print(type);
		out.print("()");
	}

	/**
	 * Print a constructor.
	 * @param out a print writer
	 * @param name  the method name
	 * @param target the target object ref
	 * @param params  the parameters
	 */
	protected void printMethodInvocation(PrintWriter out, String name, String target, String... params) {
		out.print(target);
		out.print('.');
		out.print(name);
		out.print('(');
		boolean f = true;
		for (String param : params) {
			if (f)
				f = false;
			else
				out.print(",");
			out.print(param);
		}
		out.print(')');
	}

	/**
	 * Print a variable assignment.
	 * @param out   a print writer
	 * @param obj    the object reference
	 * @param attr  the attribute (optional)
	 * @param value  the value
	 * @param valueAttr the value attribute (optional)
	 *  @param cast cast value to this type, don't cast if null
	 */
	protected void printVariableAssignment(PrintWriter out, String obj, String attr, String value, String valueAttr, String cast) {
		out.print(obj);
		if (attr != null) {
			out.print('.');
			out.print(attr);
		}
		out.print('=');
		if (cast!=null) {
			out.print('(');
			out.print(cast);
			out.print(')');
		}
		out.print(value);
		if (valueAttr != null) {
			out.print('.');
			out.print(valueAttr);
		}
		out.println(';');
	}

	/**
	 * Print a variable assignment.
	 * @param out   a print writer
	 * @param obj    the object reference
	 * @param attr  the attribute (optional)
	 * @param value  the value
	 * @param valueAttr the value attribute (optional)
	 */
	protected void printVariableAssignment(PrintWriter out, String obj, String attr, String value, String valueAttr) {
		printVariableAssignment( out,  obj, attr,  value,  valueAttr,null);
	}

	/**
	 * Print a variable assignment.
	 * @param out   a print writer
	 * @param obj    the object reference
	 * @param attr  the attribute (optional)
	 * @param value  the value
	 */
	protected void printVariableAssignment(PrintWriter out, String obj, String attr, String value) {
		printVariableAssignment( out,  obj, attr,  value,  null,null);
	}

	/**
	 * Print a variable declaration. 
	 * @param out  a print writer
	 * @param type the type
	 * @param name  the name
	 * @param defaultValue  the default value (optional)
	 * @param modifiers  modifiers like final and static (optional)
	 */
	protected void printVariableDeclaration(PrintWriter out, String modifiers, String type, String name, String defaultValue) {
		if (modifiers != null) {
			out.print(modifiers);
			out.print(" ");
		}
		out.print(type);
		out.print(" ");
		out.print(name);
		if (defaultValue != null) {
			out.print(" ");
			out.print(defaultValue);
		}
		out.println(';');
	}

	/**
	 * Print a variable declaration. 
	 * @param out a print writer
	 * @param type the type
	 * @param name   the name
	 * @param modifiers modifiers like final and static (optional)
	 */
	protected void printVariableDeclaration(PrintWriter out, String modifiers, String type, String name) {
		printVariableDeclaration(out, modifiers, type, name, null);
	}

	/**
	 * Get the method name associated with a query.
	 * @param q a query
	 * @return a method name
	 */
	protected String getMethodName(Query q) {
	
		return this.getNameGenerator().getMethodName(q);
	}

	/**
	 * Get the method name associated with a query.
	 * @param q  a query
	 * @param i  an index
	 * @return a method name
	 */
	protected String getMethodName(Query q, int i) {
		return this.getMethodName(q) + "_" + i;
	}
	


	/**
	 * Get the code that is used to reference a constant.	
	 * @param constantClassName
	 * @param t
	 * @return
	 */
	protected String getRef(String constantClassName, Constant t) throws CompilerException {
		if (t.isProxy()) {
			return constantClassName+'.'+t.getRef();
		}
		else {
			String lit = t.getLiteral();
			if (lit!=null)
				return lit;
			else
				throw new CompilerException(
						"Don't know how to reference this constant term: " + t);
		}
	}

	/**
	 * Get the name of the class for a predicate.
	 * @param p a predicate
	 * @return a class name
	 */
	protected String getClassName(Predicate p) {
		return this.getNameGenerator().getClassName(p);
	}

	/**
	 * Get the name of the class for a fact.
	 * @param f a fact
	 * @return a class name
	 */
	protected String getClassName(Fact f) {
		return this.getNameGenerator().getClassName(f.getPredicate());
	}

	/**
	 * Copy an annotation.
	 * @param key
	 * @param from
	 * @param to
	 */
	private void copyAnnotation(String key, Annotatable from, Annotatable to) {
		String value = from.getAnnotation(key);
		if (value!=null)
			to.addAnnotation(key, value);
	}

	/**
	 * Print the code used to invoke the method representing a query.
	 * 
	 * @param out
	 *            a print writer
	 * @param queryRef
	 *            the query + the parameters used
	 */
	protected void printInvocation(PrintWriter out, QueryRef queryRef, boolean includeExplanation, boolean  increaseLevel) {
		out.print(this.getMethodName(queryRef));
		out.print("(");
		boolean f = true;
		for (String param : queryRef.getParamRefs()) {
			if (f)
				f = false;
			else
				out.print(',');
			out.print(param);
		}
		// add reference to explanation
		if (includeExplanation) {
			if (f)
				f = false;
			else
				out.print(',');
			out.print(this.getVarName4DerivationController());
			if (increaseLevel) {
				out.print(".increaseDepth()");
			}
		}
		out.print(")");
	}
	protected void printLogStatement(PrintWriter out, KnowledgeElement cs) {
		String kind = null;
		if (cs instanceof DerivationRule)
			kind = "DerivationController.RULE";
		else if (cs instanceof Fact)
			kind = "DerivationController.FACT";
		else 
			kind = "DerivationController.ANY";
		
		out.print(this.getVarName4DerivationController());
		out.print(".log(\"");
		out.print(this.getRuleRef(cs));
		out.print("\",");
		out.print(kind);
		out.println(");");
	}
	/**
	 * Collect all terms.
	 * @param list the list used to collect the terms
	 * @param c the term container
	 */
	private void collectTerms(List<Term> terms, ComplexTerm c) {
		for (Term t:c.getTerms()) {
			// avoid duplication!
			if (!terms.contains(t))
				terms.add(t);
			if (t instanceof ComplexTerm)
				collectTerms(terms,(ComplexTerm)t);
		}
	}
	/**
	 * Collect all terms.
	 * @param list the list used to collect the terms
	 * @param c the term container
	 */
	protected void collectTerms(List<Term> terms, Fact c) {
		for (Term t:c.getTerms()) {
			// avoid duplication!
			if (!terms.contains(t))
				terms.add(t);
			if (t instanceof ComplexTerm)
				collectTerms(terms,(ComplexTerm)t);
		}
	}
	/**
	 * Log the creation of a class.
	 * @param loc the location
	 * @param qName the full class name
	 */
	protected void endorseClazz(Location loc, String fullClassName) throws CompilerException {
		if (getLogger().isDebugEnabled()) {
			getLogger().debug("Class created: " + fullClassName);
		}
		for (SourceTransformation t : this.getSourceTransformers()) {
			t.transform(loc, fullClassName);
		}
	}
	/**
	 * Log the creation of a method.
	 * 
	 * @param methodName
	 *            the method name
	 */
	protected void endorseMethod(String methodName) throws CompilerException {
		if (getLogger().isDebugEnabled()) {
			getLogger().debug("Method created: " + methodName);
		}
	}
	/**
	 * Get all terms (recursive) occuring in a rule.
	 * @param r a rule
	 * @return a list of terms
	 */
	protected List<Term> getAllTerms(DerivationRule r) {
		List<Term> terms = new ArrayList<Term>();
		List<Prerequisite>body = r.getBody();
		for (Prerequisite p:body) {
			collectTerms(terms,p);
		}
		collectTerms(terms,r.getHead());
		return terms;
	}
	/**
	 * Print a comma separated list.
	 * @param out
	 * @param list an iterable
	 */
	protected void printCommaSeparatedList(PrintWriter out,Iterable<String> list) {
		boolean f = true;
		for (String s:list) {
			if (f)
				f = false;
			else 
				out.print(',');
			out.print(s);
		}
	}
	/**
	 * Print a comma separated list.
	 * @param out
	 * @param list an array
	 */
	protected void printCommaSeparatedList(PrintWriter out,String[] list) {
		boolean f = true;
		for (String s:list) {
			if (f)
				f = false;
			else 
				out.print(',');
			out.print(s);
		}
	}

}
