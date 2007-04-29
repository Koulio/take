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
import nz.org.take.compiler.*;
import nz.org.take.compiler.Compiler;
import nz.org.take.compiler.util.*;
import nz.org.take.*;

/**
 * Default compiler implementation.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class DefaultCompiler implements Compiler, Logging {

	private static final String RESULT = "result";
	
	// instance variable names
	private String varName4DerivationController = "_derivation";

	// custom settings
	private String derivationControllerClass = "DefaultDerivationController";
	private String[] derivationControllerInitialisationParameters = {}; // will be passed to the constructor of derivationControllerClass
	
	private List<Query> publicAgenda = new ArrayList<Query>();
	private Collection<Query> done = new ArrayList<Query>();
	private NameGenerator nameGenerator = new DefaultNameGenerator();
	private KnowledgeBase kb = null;
	private List<SourceTransformation> transformations = new ArrayList<SourceTransformation>();
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
			for (Predicate p:kb.getSupportedPredicates()) {
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

	private Slot buildSlot(Predicate p, int i) {
		Slot s = new Slot();
		s.position = i;
		s.name = p.getSlotNames()[i];
		s.type = p.getSlotTypes()[i].getName();
		s.accessor = this.nameGenerator.getAccessorNameForSlot(p, i);
		s.mutator = this.nameGenerator.getMutatorNameForSlot(p, i);
		s.var = this.nameGenerator.getVariableNameForSlot(p, i);
		return s;
	}

	/**
	 * Build the input slots for a query.
	 * 
	 * @param q
	 * @return an array of slots
	 */
	private Slot[] buildInputSlots(Query q) {
		return this.buildSlots(q.getPredicate(), q.getInputParams());
	}

	/**
	 * Create an array consisting of slots that are the input params for calling
	 * a method asociated by a fact.
	 * 
	 * @param f
	 *            a fact
	 * @param bindings
	 *            bindings
	 * @return an array of slots
	 */
	private QueryRef buildQuery(Fact f, Map<Term, String> bindings) {
		Term[] terms = f.getTerms();
		boolean[] io = new boolean[terms.length];
		List<String> params = new ArrayList<String>();
		for (int i = 0; i < io.length; i++) {
			String ref = bindings.get(terms[i]);
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
	private Slot[] buildOutputSlots(Query q) {
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
	private Slot[] buildSlots(Predicate p, boolean[] params) {
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
	private Slot[] buildSlots(Predicate p) {
		Slot[] slots = new Slot[p.getSlotTypes().length];
		for (int i = 0; i < p.getSlotTypes().length; i++)
			slots[i] = buildSlot(p, i);
		return slots;
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
	 * Create a class that can be used to keep track of variable bindings in
	 * rules.
	 * 
	 * @param out
	 *            the writer
	 * @param r
	 *            the rule
	 * @return a map associating variables with instance variable names
	 */
	@SuppressWarnings("unchecked")
	private Map<Variable, String> createBindingClass(PrintWriter out, DerivationRule r)
			throws CompilerException {
		Map<Variable, String> map = new HashMap<Variable, String>();
		this.printOneLineComment(out, "Variable bindings in rule: ", r);
		String name = this.getBindingClassName(r);
		out.print("class ");
		out.print(name);
		out.println('{');
		int counter = 1;
		for (Prerequisite prereq : (List<Prerequisite>) r.getBody()) {
			for (Term t : prereq.getTerms()) {
				if (t instanceof Variable) {
					Variable v = (Variable) t;
					if (!map.containsKey(v)) {
						String property = "p" + counter;
						counter = counter + 1;
						map.put(v, property);
						this.printOneLineComment(out,"Property generated for var \"", v, "\" from ",prereq);
						out.print(v.getType().getName());
						out.print(" ");
						out.print(property);
						out.println(";");
					}
				} else
					throw new CompilerException(
							"Only variable terms are accepted in prerequisites, this is not a variable term: "
									+ t);
			}

		}

		out.println("};");
		return map;
	}

	/**
	 * Create a class for a return type.
	 * 
	 * @param out
	 *            the writer
	 * @param clazz
	 *            the class name
	 * @param pck
	 *            the package name
	 * @param p
	 *            the predicate
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
		out.println("return _result;} // blabla");
	}
	

	/**
	 * Create a private method for the given query.
	 * @param out
	 * @param q
	 * @throws CompilerException
	 */
	@SuppressWarnings("unchecked")
	private void createPrivateMethod(PrintWriter out, Query q) throws CompilerException {
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

	private Object getRuleRef(KnowledgeElement cs) {
		return cs.toString();
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

	private void printLogStatement(PrintWriter out,KnowledgeElement cs) {
		out.print(this.getVarName4DerivationController());
		out.print(".log(\"");
		out.print(this.getRuleRef(cs));
		out.print("\");");
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
		Map<Term, String> bindings = new HashMap<Term, String>();
		// compute initial bindings
		Fact head = r.getHead();
		Term[] terms = head.getTerms();
		// bind all inputslots to variables of the current rule
		for (int i = 0; i < islots.length; i++) {
			bindings.put(terms[islots[i].position], islots[i].var);
		}

		String bindingsClass = this.getBindingClassName(r);
		// all variablenames of the current rule
		Map<Variable, String> refs = createBindingClass(out, r);
		out.print("final ");
		out.print(bindingsClass);
		out.print(" ");
		out.print("bindings");
		out.print(" = ");
		this.printContructorInvocation(out, bindingsClass, null);
		out.println(';');

		// assign input vars
		for (Map.Entry<Variable, String> refEntry : refs.entrySet()) {
			String var = bindings.get(refEntry.getKey());
			if (var != null) {
				out.print("bindings.");
				out.print(refEntry.getValue());
				out.print("=");
				out.print(var);
				out.println(";");
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
				// call method
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
					if (t instanceof Variable) {
						Variable vt = (Variable) t;
						Slot slot = this.buildSlot(previousFact.getPredicate(),i);
						printVariableAssignment(out, "bindings", refs.get(vt),"object", slot.var);
						bindings.put(vt, refs.get(vt));
					} else {
						// REFACTOR generalise
						throw new CompilerException("Only variables are supported here");
					}
				}
				// build method call
				// here we call the method that supplies the next iterator
				boolean[] sig = new boolean[prereq.getPredicate().getSlotTypes().length];
				List<String> params = new ArrayList<String>(sig.length);
				for (int j = 0; j < sig.length; j++) {
					// problem
					//String expr = refs.get(prereq.getTerms()[j]);
					// terms  that have been bound
					sig[j] = bindings.containsKey(prereq.getTerms()[j]);
					if (sig[j]) {
						params.add("bindings."+ refs.get(prereq.getTerms()[j]));
					}
				}
				QueryRef nextQuery = new QueryRef(prereq.getPredicate(), sig,params);
				configNewQuery(nextQuery);
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
    * Configure a query built by the compiler.
    * This means to add compiler hint annotations.
    * @param q
    */
	private void configNewQuery(QueryRef q) {
		String methodNameAnnotation = this.methodNames4QueriesFromAnnotations.get(this.createQueryHash(q));
		if (methodNameAnnotation!=null)
			q.addAnnotation(AnnotationKeys.TAKE_GENERATE_METHOD,methodNameAnnotation);
	}

	/**
	 * Get the code that is used to reference an object.
	 * 
	 * @param type
	 * @param obj
	 * @return
	 */
	private String getObjectRefCode(Class type, Object obj)
			throws CompilerException {
		if (type == String.class)
			return "\"" + obj + "\"";
		else
			throw new CompilerException(
					"Don't know how to reference this object: " + obj);
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
	 * Get the method name associated with a query.
	 * @param q a query
	 * @return a method name
	 */
	private String getMethodName(Query q) {

		return this.getNameGenerator().getMethodName(q);
		// StringBuffer b = new StringBuffer();
		// char[] name = q.getPredicate().getName().toCharArray();
		// for (char ch : name)
		// if (!Character.isWhitespace(ch))
		// b.append(ch);
		// else
		// b.append("_");
		// b.append("_");
		// for (boolean f : q.getInputParams())
		// b.append( f ? "1" : "0" );
		// return b.toString();
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
	 * Get the method name associated with a query.
	 * @param q  a query
	 * @param i  an index
	 * @return a method name
	 */
	private String getMethodName(Query q, int i) {
		return this.getMethodName(q) + "_" + i;
	}

	/**
	 * Get the name of the class for a predicate.
	 * @param p a predicate
	 * @return a class name
	 */
	private String getClassName(Predicate p) {
		return this.getNameGenerator().getClassName(p);
	}

	/**
	 * Get the name of the class for a fact.
	 * @param f a fact
	 * @return a class name
	 */
	private String getClassName(Fact f) {
		return this.getNameGenerator().getClassName(f.getPredicate());
	}

	/**
	 * Log the creation of a class.
	 * @param loc the location
	 * @param qName the full class name
	 */
	private void endorseClazz(Location loc, String fullClassName)throws CompilerException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Class created: " + fullClassName);
		}
		for (SourceTransformation t : this.transformations) {
			t.transform(loc, fullClassName);
		}
	}

	/**
	 * Log the creation of a method.
	 * 
	 * @param methodName
	 *            the method name
	 */
	private void endorseMethod(String methodName) throws CompilerException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Method created: " + methodName);
		}
	}

	/**
	 * Add a source code transformer.
	 * 
	 * @param t
	 */
	public void add(SourceTransformation t) {
		this.transformations.add(t);
	}

	/**
	 * Remove a source code transformer.
	 * 
	 * @param t
	 */
	public void remove(SourceTransformation t) {
		this.transformations.remove(t);
	}

	/**
	 * Get a list of source transformers installed.
	 * 
	 * @return a list of transformations
	 */
	public List<SourceTransformation> getSourceTransformers() {
		return this.transformations;
	}

	/**
	 * Print a one line comment.
	 * 
	 * @param out
	 *            a print writer
	 * @param tokens
	 *            (will be converted to strings using toString)
	 */
	private void printOneLineComment(PrintWriter out, Object... tokens) {
		out.print("// ");
		for (Object t : tokens)
			out.print(t);
		out.println();
	}

	/**
	 * Print a parameter list generated from an array of input slots.
	 * 
	 * @param out
	 *            a print writer
	 * @param islots
	 *            an array of slots
	 * @param isDeclaration
	 *            whether to include the type declarations
	 */
	private void printParameters(PrintWriter out, Slot[] islots,boolean isDeclaration,boolean includeExplanation,boolean resetDerivationLevel) {
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
	 * 
	 * @param out
	 *            a print writer
	 * @param comment
	 *            the Methodcomment
	 * @param islots
	 *            the input/param slots
	 * @param returnComment
	 *            comment for the return tag
	 */
	private void printMethodComment(PrintWriter out, String comment,
			Slot[] islots, String returnComment) {
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
	 * 
	 * @param out
	 *            a print writer
	 * @param type
	 *            the type name
	 * @param param
	 *            the parameter type
	 */
	private void printGenericType(PrintWriter out, String type, String param) {
		out.print(type);
		out.print('<');
		out.print(param);
		out.print("> ");
	}

	/**
	 * Print a constructor.
	 * 
	 * @param out
	 *            a print writer
	 * @param type
	 *            the type name
	 * @param genTypeParam
	 *            the generic type parameter
	 * @param params
	 *            the parameters
	 */
	private void printContructorInvocation(PrintWriter out, String type,
			String genTypeParam, String... params) {
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
	 * 
	 * @param out
	 *            a print writer
	 * @param type
	 *            the type name
	 * @param genType1
	 *            the generic type parameter1
	 * @param genType2
	 *            the generic type parameter2
	 * @param params
	 *            the parameters
	 */
	private void printContructorInvocation2(PrintWriter out, String type,
			String genTypeParam1, String genTypeParam2, String... params) {
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
	 * 
	 * @param out
	 *            a print writer
	 * @param queryRef
	 *            the query + the parameters used
	 */
	private void printConstructorInvocation(PrintWriter out, QueryRef queryRef) {
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
	 * Print a variable assignment.
	 * 
	 * @param out
	 *            a print writer
	 * @param obj
	 *            the object reference
	 * @param attr
	 *            the attribute (optional)
	 * @param value
	 *            the value
	 * @param valueAttr
	 *            the value attribute (optional)
	 */
	private void printVariableAssignment(PrintWriter out, String obj,
			String attr, String value, String valueAttr) {
		out.print(obj);
		if (attr != null) {
			out.print('.');
			out.print(attr);
		}
		out.print('=');
		out.print(value);
		if (valueAttr != null) {
			out.print('.');
			out.print(valueAttr);
		}
		out.println(';');
	}

	/**
	 * Print a variable declaration.
	 * 
	 * @param out
	 *            a print writer
	 * @param type
	 *            the type
	 * @param name
	 *            the name
	 * @param defaultValue
	 *            the default value (optional)
	 * @param modifiers
	 *            modifiers like final and static (optional)
	 */
	private void printVariableDeclaration(PrintWriter out, String modifiers,
			String type, String name, String defaultValue) {
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
	 * 
	 * @param out
	 *            a print writer
	 * @param type
	 *            the type
	 * @param name
	 *            the name
	 * @param modifiers
	 *            modifiers like final and static (optional)
	 */
	private void printVariableDeclaration(PrintWriter out, String modifiers,
			String type, String name) {
		printVariableDeclaration(out, modifiers, type, name, null);
	}

	/**
	 * Print the code used to invoke the method representing a query.
	 * 
	 * @param out
	 *            a print writer
	 * @param queryRef
	 *            the query + the parameters used
	 */
	private void printInvocation(PrintWriter out, QueryRef queryRef, boolean includeExplanation, boolean  increaseLevel) {
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

	/**
	 * Create a string identifying a query.
	 * @param q
	 * @return
	 */
	private String createQueryHash(Query q) {
		StringBuffer b = new StringBuffer();
		b.append(q.getPredicate().getName());
		b.append('_');
		for (boolean f:q.getInputParams()) {
			b.append(f?'1':'0');
		}
		return b.toString();
	} 
	
	/**
	 * Copy an annotation.
	 * @param key
	 * @param from
	 * @param to
	 */
	private void copyAnnotation(String key,Annotatable from,Annotatable to) {
		String value = from.getAnnotation(key);
		if (value!=null)
			to.addAnnotation(key, value);
	}
	
}
