/*
 * Copyright (C) 2006 <A href="http://www-ist.massey.ac.nz/JBDietrich" target="_top">Jens Dietrich</a>
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

import nz.org.take.compiler.*;
import nz.org.take.compiler.Compiler;
import nz.org.take.compiler.util.*;
import nz.org.take.*;

/**
 * Default compiler implementation.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class DefaultCompiler extends CompilerUtils  implements Compiler {
	
	private static final String RESULT = "result";
	
	// instance variable names
	private String varName4DerivationController = "_derivation";

	// custom settings
	private String derivationControllerClass = "DefaultDerivationController";
	private String[] derivationControllerInitialisationParameters = {}; // will be passed to the constructor of derivationControllerClass
	
	private List<Query> publicAgenda = new ArrayList<Query>();
	private Collection<Query> done = new ArrayList<Query>();
	NameGenerator nameGenerator = new DefaultNameGenerator();
	private KnowledgeBase kb = null;
	List<SourceTransformation> transformations = new ArrayList<SourceTransformation>();
	private Map<DerivationRule, String> bindingClassNames = new HashMap<DerivationRule, String>();
	private int bindingClassCounter = 1;
	private Map<String,String> methodNames4QueriesFromAnnotations = new HashMap<String,String>();

	/**
	 * 
	 */
	public DefaultCompiler() throws Exception {
		super();
	}

	/**
	 * Compile the kb.
	 * @param kb a knowledge base
	 * @param queries a list of query
	 * @param location the location where generated sources and compiled code will be stored
	 * @param packageName the package of the class to be generated
	 * @param className the class that will be generated
	 * @throws CompilerException
	 */
	public void compile(KnowledgeBase kb, List<Query> queries,Location location, String packageName, String className)throws CompilerException {

		// put queries to publicAgenda (necessity is checked implicit)
		for (Query q : queries) {
			this.addToAgenda(q);
			// cache method names from annotation so that queries that are built later
			// can use the same annotations
			String methodName = q.getAnnotation(AnnotationKeys.TAKE_GENERATE_METHOD);
			if (methodName!=null) {
				String k = this.createQueryHash(q);
				methodNames4QueriesFromAnnotations.put(k, methodName);
			}
		}
		String fullClassName = packageName + "." + className;
		this.kb = kb;
		try {
			PrintWriter out = new PrintWriter(location.getSrcOut(fullClassName));
			createKBClass(out, className, packageName);
			out.close();
			endorseClazz(location, fullClassName);

			// return types
			for (Predicate p:findPredicates(kb)) {
				className = getClassName(p);
				fullClassName = packageName + "." + className;
				out = new PrintWriter(location.getSrcOut(fullClassName));
				createReturnType(out, className, packageName, p);
				out.close();
				endorseClazz(location, fullClassName);
			}

		} catch (Exception x) {
			throw new CompilerException(x);
		}
	}
	/**
	 * Compile the kb with the list of queries in the kb.
	 * @param kb a knowledge base
	 * @param location the location where generated sources and compiled code will be stored
	 * @param packageName the '.'-separator packagename of the class to be generated
	 * @param className the class that will be generated
	 * @throws CompilerException
	 */
	public void compile (KnowledgeBase kb,Location location,String packageName,String className) throws CompilerException {
		this.compile(kb,kb.getQueries(), location, packageName, className);
	}

	/**
	 * Create the kb class.
	 * 
	 * @param out
	 *            the writer
	 * @param clazz
	 *            the class name
	 * @param pck
	 *            the package name
	 */
	private void createKBClass(PrintWriter out, String clazz, String pck)
			throws CompilerException {
		out.print("package ");
		out.print(pck);
		out.println(";");
		out.println("import java.util.Iterator;");
		out.println("import nz.org.take.rt.*;");
		out.println("/**");
		out.println(" * Class generated by the take compiler. ");
		out.print(" * @version ");
		out.println(new Date());
		out.println(" */");
		out.println("@SuppressWarnings(\"unchecked\")");
		out.print("public class ");
		out.print(clazz);
		out.println("{");

		while (!this.isAgendaEmpty()) {
			createMethod(out, this.getNextQuery());
		}

		out.println("}");
	}

	/**
	 * Create a class that can be used to keep track of variable bindings in rules.
	 * @param out the writer
	 * @param r  the rule
	 * @return a map associating variables with instance variable names
	 */
	private Map<Term, String> createBindingClass(PrintWriter out, DerivationRule r) throws CompilerException {
		Map<Term, String> map = new HashMap<Term, String>();
		this.printOneLineComment(out, "Variable bindings in rule: ", r);
		String name = this.getBindingClassName(r);
		out.print("class ");
		out.print(name);
		out.println('{');
		int counter = 1;
		List<Term> termsInRule = this.getAllTerms(r);		
		for (Term t : termsInRule) {
			if (!map.containsKey(t)) {
					String property = "p" + counter;
					counter = counter + 1;
					map.put(t, property);
					this.printOneLineComment(out,	"Property generated for term  \"", t,"\"");
					out.print(t.getType().getName());
					out.print(" ");
					out.print(property);
					out.println(";");
				}
			} 

		out.println("};");
		return map;
	}

	/**
	 * Create a class for a return type. 
	 * @param out  the writer
	 * @param clazz   the class name
	 * @param pck the package name
	 * @param p  the predicate
	 */
	private void createReturnType(PrintWriter out, String clazz, String pck,Predicate p) throws CompilerException {
		Slot[] slots = buildSlots(p);
		out.print("package ");
		out.print(pck);
		out.println(";");

		out.println("/**");
		out.println(" * Class generated by the mandarax compiler. ");
		out.print(" * @version ");
		out.println(new Date());
		out.println(" */");

		out.print("public class ");
		out.print(clazz);
		out.println("{");

		// constructor with parameters
		out.print(clazz);
		out.print('(');
		boolean first = true;
		for (Slot slot : slots) {
			if (first)
				first = false;
			else
				out.print(',');
			out.print(slot.type);
			out.print(" ");
			out.print(slot.var);
		}
		out.println("){");
		out.println("super();");
		for (Slot slot : slots) {
			this.printVariableAssignment(out, "this", slot.var, slot.var, null);
		}
		out.println("}");

		// constructor withour params
		out.print(clazz);
		out.print("(){");
		out.println("super();");
		out.println("}");

		// fields
		for (Slot slot : slots) {
			printVariableDeclaration(out, "public", slot.type, slot.var);
		}
		
		out.println("}");
	}
	
	/**
	 * Create a private method for the given query.
	 * @param out
	 * @param q
	 * @throws CompilerException
	 */
	@SuppressWarnings("unchecked")
	private void createMethod(PrintWriter out, Query q)	throws CompilerException {
		createPublicMethod(out,q);
		createPrivateMethod(out,q);
	}
	
	
	/**
	 * Create a public method for the given query.
	 * @param out
	 * @param q
	 * @throws CompilerException
	 */
	@SuppressWarnings("unchecked")
	private void createPublicMethod(PrintWriter out, Query q)
			throws CompilerException {
		Slot[] inSlots = this.buildInputSlots(q);
		Slot[] outSlots = this.buildOutputSlots(q);
		Predicate p = q.getPredicate();

		printMethodComment(out, "Method generated for query " + p, inSlots,
				"an interator for instances of " + getClassName(p));

		// start header
		this.printGenericType(out, "public ResultSet", getClassName(p));
		String methodName = getMethodName(q);
		out.print(methodName);

		printParameters(out, inSlots, true,false,false);
		// end params
		out.println("{");
		out.print("DerivationController ");
		out.print(this.getVarName4DerivationController());
		out.print(" = new ");
		out.print(this.getDerivationControllerClass());
		out.print('(');
		boolean first = false;
		for (String param:this.getDerivationControllerInitialisationParameters()) {
			if (first)
				first = false;
			else
				out.print(',');
			out.print(param);
		}			
		out.println(");");
		
		//	call the private method
		this.printGenericType(out, "ResultSet", getClassName(p));
		out.print("_result = new ResultSet(");
		out.print(methodName); 
		printParameters(out, inSlots, false,true,false); 
		out.println(',');
		out.println(this.getVarName4DerivationController());
		out.println(");");
		out.println("return _result;} ");
	}
	

	/**
	 * Create a private method for the given query.
	 * @param out
	 * @param q
	 * @throws CompilerException
	 */
	@SuppressWarnings("unchecked")
	private void createPrivateMethod(PrintWriter out, Query q) 	throws CompilerException {
		Predicate p = q.getPredicate();
		if (p instanceof SimplePredicate) 
			createPrivateMethod1( out,  q);
		else if (p instanceof JPredicate) 
			createPrivateMethod2( out,  q);
		else throw new CompilerException("This kind of predicate is not supported in queries: " + p.getClass());
	}
	/**
	 * Create a private method for the given query.
	 * The predicate of the query is defined by rules and facts.
	 * @param out
	 * @param q
	 * @throws CompilerException
	 */
	private void createPrivateMethod1(PrintWriter out, Query q) 	throws CompilerException {
		Slot[] inSlots = this.buildInputSlots(q);
		Slot[] outSlots = this.buildOutputSlots(q);
		Predicate p = q.getPredicate();

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
		
		List<KnowledgeElement> css = kb.getElements(p);

		out.print("ResourceIterator<");
		out.print(getClassName(p));
		out.print("> ");
		out.print(RESULT);
		out.print("= new ");
		out.print("IteratorChain<");
		out.print(getClassName(p));
		out.print(">(");
		out.print(css.size());
		out.println("){");

		out.print("public Object getIteratorOrObject(int pos){");
		out.println("switch(pos){");
		for (int i = 0; i < css.size(); i++) {
			out.print("// ");
			out.println(css.get(i));
			out.print("case ");
			out.print(i);
			out.print(":");
			out.print("return ");
			out.print(getMethodName(q, i));
			printParameters(out, inSlots, false,true,true);
			out.println(';');
		}

		out.println("default:");
		out.println("return EmptyIterator.DEFAULT;");
		out.println("} // switch(pos)");
		out.println("} // getIterator()");
		
		out.print("public String getRuleRef(int pos){");
		out.println("switch(pos){");
		for (int i = 0; i < css.size(); i++) {
			out.print("// ");
			out.println(css.get(i));
			out.print("case ");
			out.print(i);
			out.print(":");
			out.print("return \"");
			out.print(getRuleRef(css.get(i)));
			out.println("\";");
		}

		out.println("default:");
		out.println("return \"\";");
		out.println("} // switch(pos)");
		out.println("} // getRuleRef()");
		out.println("};"); // end inner class
		out.print("return "); // end inner class
		out.println(RESULT);
		out.println(";} // blabla");
		
		// generate a method for each clause set
		for (int i = 0; i < css.size(); i++) {
			createMethod(out, q, inSlots, outSlots, css.get(i), i);
		}

		this.endorseMethod(methodName);
		this.removeFromAgenda();
	}

	/**
	 * Create a private method for the given query.
	 * The predicate of the query is defined by a Java method.
	 * @param out
	 * @param q
	 * @throws CompilerException
	 */
	private void createPrivateMethod2(PrintWriter out, Query q) throws CompilerException {
		Slot[] inSlots = this.buildInputSlots(q);
		Slot[] outSlots = this.buildOutputSlots(q);
		JPredicate p = (JPredicate)q.getPredicate();

		printMethodComment(out, "Method generated for query " + p, inSlots,"an interator for instances of " + getClassName(p));

		// start header
		this.printGenericType(out, "private ResourceIterator", getClassName(p));
		String methodName = getMethodName(q);
		out.print(methodName);

		printParameters(out, inSlots, true,true,false);
		// end params
		out.println("{");
		
		if (outSlots.length>0)
			throw new CompilerException("Cannot generate code for queries with JavaPredicates and unbound variables, extends are not yet supported ");

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
		
		// else = EMPTY Iterator
		out.println("return EmptyIterator.DEFAULT;");
		out.println('}');
		
		this.endorseMethod(methodName);
		this.removeFromAgenda();
	}

	// create private method
	private void createMethod(PrintWriter out, Query q, Slot[] islots,Slot[] oslots, KnowledgeElement cs, int i) throws CompilerException {
		Predicate p = q.getPredicate();
		printMethodComment(out, "Method generated for query " + p, islots,
				"an interator for instances of " + getClassName(p));

		// facts are handled differently to minimize memory consumption
		if (cs instanceof Fact) {
			out.print("private ");
			out.print(getClassName(q.getPredicate()));
			out.print(' ');
		}
		else {
			out.print("private ResourceIterator<");
			out.print(getClassName(q.getPredicate()));
			out.print("> ");
		}
		
		
		out.print(getMethodName(q, i));
		printParameters(out, islots, true,true,false);
		out.print("{");

		// generate method body
		if (cs instanceof Fact) {
			createBody(out, q, islots, oslots, (Fact) cs);
		} else if (cs instanceof DerivationRule) {
			createBody(out, q, islots, oslots, (DerivationRule) cs);
		} else {
			out.print("// this clause set type is not yet supported: ");
			out.println(cs.getClass());

		}

		out.println("}");
	}

	/**
	 * Create a proof for a query.
	 * 
	 * @param out -
	 *            a print writer
	 * @param q -
	 *            the query
	 * @param islots -
	 *            the input slots (known)
	 * @param oslots -
	 *            the output slots (to be bound)
	 * @param f -
	 *            the fact
	 * @throws CompilerException
	 */
	private void createBody(PrintWriter out, Query q, Slot[] islots,
			Slot[] oslots, Fact f) throws CompilerException {
		
		// log 
		printLogStatement(out,f);
		
		// start creating return var
		Predicate p = q.getPredicate();
		out.print(getClassName(p));
		out.print(" ");
		out.print(RESULT);
		out.print("=");
		printContructorInvocation(out, getClassName(p), null);
		out.println(";");

		// start initializing return var
		for (Slot slot : islots) {
			out.print(RESULT);
			out.print(".");
			out.print(slot.var);
			out.print("=");
			out.print(slot.var);
			out.println(";");
		}

		if (islots.length == 0)
			out.println("if (true) {");
		else {
			out.print("if (");
			boolean first = true;
			for (Slot slot : islots) {
				if (first)
					first = false;
				else
					out.print("&&");
				out.print(slot.var);
				out.print(".equals(");
				Term t = f.getTerms()[slot.position];
				assert (t instanceof Constant);
				Object obj = ((Constant) t).getObject();
				out.print(getObjectRefCode(t.getType(), obj));
				out.print(")");
			}
			out.println("){");
		}

		for (Slot slot : oslots) {
			out.print(RESULT);
			out.print(".");
			out.print(slot.var);
			out.print("=");
			Term t = f.getTerms()[slot.position];
			assert (t instanceof Constant);
			Object obj = ((Constant) t).getObject();
			out.print(getObjectRefCode(t.getType(), obj));
			out.println(";");
		}
		out.print("return ");
		out.print(RESULT);
		out.println(';');
		out.println("}");
		out.print("return null;");

	}

	/**
	 * Create a proof for a query.
	 * @param out a print writer
	 * @param q the query
	 * @param islots the input slots (known)
	 * @param oslots the output slots (to be bound)
	 * @param r the rule
	 * @throws CompilerException
	 */
	private void createBody(PrintWriter out, Query q, Slot[] islots,Slot[] oslots, DerivationRule r) throws CompilerException {
		
		// print log statement
		printLogStatement(out,r);
		
		// the concrete bindings for this rule
		List<Term> allTerms = this.getAllTerms(r);
		Bindings bindings = new Bindings(allTerms);
		
		// compute initial bindings
		Fact head = r.getHead();
		Term[] terms = head.getTerms();
		
		// bind all inputslots to variables of the current rule
		for (int i = 0; i < islots.length; i++) {
			bindings.put(terms[islots[i].position], islots[i].var);
		}

		String bindingsClass = this.getBindingClassName(r);
		// all variable names of the current rule
		Map<Term, String> refs = createBindingClass(out, r);
		out.print("final ");
		out.print(bindingsClass);
		out.print(" ");
		out.print("bindings");
		out.print(" = ");
		this.printContructorInvocation(out, bindingsClass, null);
		out.println(';');

		// assign input vars
		for (Map.Entry<Term, String> refEntry : refs.entrySet()) {
			String var = bindings.getRef(refEntry.getKey());
			if (var != null) {				
				printVariableAssignment(out, "bindings",refEntry.getValue(),var);
			}
		}

		List<Fact> literals = new ArrayList<Fact>();
		Fact previousFact = null;
		literals.addAll(r.getBody());
		literals.add(r.getHead());
		TmpVarGenerator varGen = new TmpVarGenerator();
		String iteratorName = null, className = null, previousIteratorName = null, previousClassName = null;
		boolean first = true;
		int counter = 1;
		for (Fact prereq : literals) {
			iteratorName = varGen.nextTmpVar("iterator");
			className = getClassName(prereq);
			this.printOneLineComment(out, "code for prereq ", prereq);
			out.print("final ");
			this.printGenericType(out, "ResourceIterator", className);

			out.print(iteratorName);
			out.print(" = ");

			/*
			 * if (prereq!=r.getHead()) addToAgenda(query);
			 */
			if (first) {
//				 call method
				// unification: find the input params known
				QueryRef query = buildQuery(prereq, bindings);
				first = false;
				printInvocation(out, query,true,true);
				counter = counter+1;
				out.println(";");
				if (prereq != r.getHead()) {
					addToAgenda(query);
				}
			} else {
				printContructorInvocation2(out, "NestedIterator",
						previousClassName, className, previousIteratorName);
				out.println("{");
				out.print("public ResourceIterator<");
				out.print(className);
				out.print("> getNextIterator(");
				out.print(previousClassName);
				out.print(" object");
				out.println("){");
				// bind params from previous iterator
				// here we set the attributes of the bindings object
				Term[] pterms = previousFact.getTerms();
				for (int i = 0; i < pterms.length; i++) {
					Term t = pterms[i];
					Slot slot = this.buildSlot(previousFact.getPredicate(),	i);
					
					// the type of the term and the type of the predicate slot might be different (but compatible - the term types must be a subtype
					// of the slot type. If they are different, a cast must be generated
					
					Class termType = t.getType();
					Class slotType = previousFact.getPredicate().getSlotTypes()[i];
					String cast = termType.equals(slotType)?null:termType.getName();
					String ref = refs.get(t);
					if (t instanceof Variable) {
						Variable vt = (Variable) t;						
						printVariableAssignment(out, "bindings",ref,"object", slot.var,cast);
						bindings.put(vt, refs.get(vt));
					} else if (t instanceof ComplexTerm) {
						ComplexTerm vt = (ComplexTerm) t;						
						printVariableAssignment(out, "bindings",ref,"object", slot.var,cast);
						bindings.put(vt, refs.get(vt));						
					}
					else {
						// REFACTOR generalise - cover constant terms
						throw new CompilerException("Only variables are supported here");
					}
				}
				// build method call
				// here we call the method that supplies the next iterator
				boolean[] sig = new boolean[prereq.getPredicate()	.getSlotTypes().length];
				List<String> params = new ArrayList<String>(sig.length);
				for (int j = 0; j < sig.length; j++) {
					// bind known variables
					// OLD TODO remove comments
					// problem
					//String expr = refs.get(prereq.getTerms()[j]);
					// terms  that have been bound
					sig[j] = bindings.hasBinding(prereq.getTerms()[j]);
					if (sig[j]) {
						params	.add("bindings."+refs.get(prereq.getTerms()[j]));
					}
				}
				// TODO
				QueryRef nextQuery = new QueryRef(prereq.getPredicate(), sig,	params);
				this.configNewQuery(nextQuery);
				
				out.print("return ");
				if (prereq == r.getHead()) {
					out.print("new SingletonIterator(");
					printConstructorInvocation(out, nextQuery);
					out.println(")");
				} else {
					addToAgenda(nextQuery);
					// the method that must be called
					printInvocation(out, nextQuery,true,true);
					counter = counter+1;
				}
				out.println(";");
				out.println("}");

				out.println("};");
			}
			previousIteratorName = iteratorName;
			previousClassName = className;
			previousFact = prereq;
		}
		out.print("return ");
		out.print(iteratorName);
		out.println(";");
	}
   /**
	 * Indicates whether the publicAgenda is empty.
	 * 
	 * @return Returns a boolean.
	 */
	private boolean isAgendaEmpty() {
		return publicAgenda.isEmpty();
	}

	/**
	 * Returns the next query.
	 * 
	 * @return a query
	 */
	private Query getNextQuery() {
		return publicAgenda.get(0);
	}

	/**
	 * Remove the current query.
	 * 
	 * @return a query
	 */
	private void removeFromAgenda() {
		Query q = publicAgenda.remove(0);
		done.add(q);
	}

	/**
	 * Add a query to the publicAgenda.
	 * 
	 * @param q
	 *            a query
	 */
	private void addToAgenda(Query q) {
		if (!done.contains(q) && !publicAgenda.contains(q))
			publicAgenda.add(q);
	}

	/**
	 * Get the name generator.
	 * 
	 * @return Returns the nameGenerator.
	 */
	public NameGenerator getNameGenerator() {
		return nameGenerator;
	}

	/**
	 * Set the name generator.
	 * 
	 * @param nameGenerator
	 *            The nameGenerator to set.
	 */
	public void setNameGenerator(NameGenerator nameGenerator) {
		this.nameGenerator = nameGenerator;
	}

	/**
	 * Get the class name for the bindings used in the method generated for a
	 * rule. 
	 * @param r a rule
	 * @return a string
	 */
	private String getBindingClassName(DerivationRule r) {
		String name = bindingClassNames.get(r);
		if (name == null) {
			name = "bindingsInRule" + this.bindingClassCounter;
			bindingClassNames.put(r, name);
			bindingClassCounter = bindingClassCounter + 1;
		}
		return name;
	}

	/**
	 * Add a source code transformer.
	 * @param t
	 */
	public void add(SourceTransformation t) {
		this.transformations.add(t);
	}

	/**
	 * Remove a source code transformer.
	 * @param t
	 */
	public void remove(SourceTransformation t) {
		this.transformations.remove(t);
	}

	/**
	 * Get a list of source transformers installed.
	 * @return a list of transformations
	 */
	public List<SourceTransformation> getSourceTransformers() {
		return this.transformations;
	}

	public String getDerivationControllerClass() {
		return derivationControllerClass;
	}

	public void setDerivationControllerClass(String derivationControllerClass) {
		this.derivationControllerClass = derivationControllerClass;
	}

	public String[] getDerivationControllerInitialisationParameters() {
		return derivationControllerInitialisationParameters;
	}

	public void setDerivationControllerInitialisationParameters(
			String[] derivationControllerInitialisationParameters) {
		this.derivationControllerInitialisationParameters = derivationControllerInitialisationParameters;
	}

	public String getVarName4DerivationController() {
		return varName4DerivationController;
	}

	public void setVarName4DerivationController(String varName4DerivationController) {
		this.varName4DerivationController = varName4DerivationController;
	}


	
	public Map<String, String> getMethodNames4QueriesFromAnnotations() {
		return methodNames4QueriesFromAnnotations;
	}
}
