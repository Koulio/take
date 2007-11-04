/**
 * Copyright 2007 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */

package nz.org.take.script;

import java.beans.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.*;
import java.util.*;
import java.util.Map.Entry;
import org.apache.log4j.Logger;
import nz.org.take.*;
import nz.org.take.script.parser.Parser;

/**
 * Utility class used to import a knowledge base from a script.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class ScriptKnowledgeSource implements KnowledgeSource  {
	private ClassLoader classloader = ScriptKnowledgeSource.class.getClassLoader();
	private static Parser parser =null;
	public Logger LOGGER = Logger.getLogger(ScriptKnowledgeSource.class);
	private Reader reader = null;
	private InputStream in = null;
	private KnowledgeBase kb = null;
	
	// to store the predicates - the keys are names+number of slots
	// this supports reusing the names to some extent, but not true 
	// polymorphism - see also getId
	private Map<String,Predicate> predicatesByName = new HashMap<String,Predicate>();
	
	private Map<String,Variable> variables = new HashMap<String,Variable>();
	private Map<String,Constant> constants = new HashMap<String,Constant>();
	private Map<String,AggregationFunction> aggregationFunctions = new HashMap<String,AggregationFunction>();

	
	
	public ScriptKnowledgeSource (Reader reader) {
		super();
		this.reader = reader;
	}	
	
	public ScriptKnowledgeSource (InputStream in) {
		super();
		this.in = in;
		this.reader = new InputStreamReader(in);
	}	
	
	public ScriptKnowledgeSource (File file) throws FileNotFoundException {
		super();
		this.reader = new FileReader(file);
	}	
	
	public ScriptKnowledgeSource (String fileName) throws FileNotFoundException {
		super();
		File file = new File(fileName);
		this.reader = new FileReader(file);
	}	
	
	/**
	 * Get a knowledge base.
	 * @return a knowledge base
	 */
	public KnowledgeBase getKnowledgeBase()  throws TakeException {
		if (kb==null)
			kb = this.read(reader);
		return kb;
	}
	
	
	/**
	 * Parse a script. 
	 * @param input
	 * @return
	 * @throws Exception
	 */
	private Script parse(Reader input) throws Exception {
		if (parser==null)
			parser = new Parser(input);
		else
			parser.ReInit(input);
		Script script = new Script();
		parser.parse(script);
		return script;
	}
	/**
	 * Annotate an element.
	 */
	private void annotate(Annotatable annotatable,List<Annotation> annotations) {
		for (Annotation ann:annotations)  
			annotatable.addAnnotation(ann.getKey(),ann.getValue());
		annotations.clear(); // reset annotations
	}

	/**
	 * Read the knowledge base from a script.
	 * @param input the reader
	 * @return the knowledge base 
	 */
	private KnowledgeBase read(Reader input) throws ScriptException {
		
		// first reset state
		predicatesByName.clear();
		variables.clear();
		constants.clear();
		aggregationFunctions.clear();
		
		Script script = null;
		try {
			script = parse(input);
			try {
				input.close();
			}
			catch (IOException x) {}
			if (in!=null){
				try {
					in.close();
				}
				catch (IOException x) {}
			}
		} catch (Exception e) {
			throw new ScriptSyntaxException("Cannot parse script",e);
		}
		List<QuerySpec> querySpecs = new ArrayList<QuerySpec>();

		KnowledgeBase kb = new DefaultKnowledgeBase(); 
		Set<String> ids = new HashSet<String>(); 
		List<Annotation> annotations = new ArrayList<Annotation>();
		for (Object part:script.getElements()) {
			if (part instanceof VariableDeclaration) {
				List<Variable> vars = buildVariables((VariableDeclaration)part);
				for (Variable v:vars)
					variables.put(v.getName(),v);
				annotations.clear(); // var declarations do not support annotations
			}
			if (part instanceof Aggregation) {
				AggregationFunction aggregation = buildAggregationFunction((Aggregation)part);
				aggregationFunctions.put(aggregation.getName(),aggregation);
			}
			if (part instanceof RefDeclaration) {
				List<Constant> cons = buildConstants((RefDeclaration)part);
				for (Constant c:cons)
					constants.put(getId(c),c);
				annotations.clear(); // var declarations do not support annotations
			}
			else if (part instanceof Annotation) {
				Annotation ann = (Annotation)part;
				if (ann.isGlobal())
					kb.addAnnotation(ann.getKey(),ann.getValue());
				else annotations.add(ann);
			}
			else if (part instanceof QuerySpec) {
				// must be build later once we know the predicates
				querySpecs.add((QuerySpec)part); 
				annotate((QuerySpec)part,annotations); 
			}
			else if (part instanceof FactStore) {
				ExternalFactStore fs = buildFactStore((FactStore)part); 
				annotate(fs,annotations); 
				kb.add(fs);
			}
			else if (part instanceof Rule) {
				Rule rule = (Rule)part;
				
				if (rule.getConditions().size()==1) {
					Clause f = buildClause(rule.getConditions().get(0));
					f.setId(rule.getId());
					checkId(f,ids);
					annotate(f,annotations); 
					kb.add(f);					
				}
				else {					
					DerivationRule r = buildRule((Rule)part);
					checkId(r,ids);
					annotate(r,annotations); 
					kb.add(r);
				}				
			}
			// ignore everything else, in particular comments
		}
		// now do the queries
		for (QuerySpec q:querySpecs) {
			Query query = buildQuery(q);
			for (Entry<String,String> e:q.getAnnotations().entrySet())  
				query.addAnnotation(e.getKey(),e.getValue());
			// take over annotations for predicate
			takeOverAnnotations(query);
			kb.add(query);
		}
		return kb;
	}
	private AggregationFunction buildAggregationFunction(Aggregation aggregation) throws ScriptException {
		AggregationFunction f = new AggregationFunction();
		f.setName(aggregation.getName());
		// build variable
		Variable var = variables.get(aggregation.getVariable());
		if (var==null) {
			throw new ScriptSemanticsException("Aggregation " + aggregation.getName() + " references undeclared variable " + aggregation.getVariable());
		}
		f.setVariable(var);
		// build function
		String aggFunction = aggregation.getFunction().toLowerCase();
		if (aggFunction.equals("avg")) {
			f.setAggregation(nz.org.take.Aggregations.AVG);
		}
		else if (aggFunction.equals("sum")) {
			f.setAggregation(nz.org.take.Aggregations.SUM);
		}
		else if (aggFunction.equals("count")) {
			f.setAggregation(nz.org.take.Aggregations.COUNT);
		}
		else if (aggFunction.equals("min")) {
			f.setAggregation(nz.org.take.Aggregations.MIN);
		}
		else if (aggFunction.equals("max")) {
			f.setAggregation(nz.org.take.Aggregations.MAX);
		}
		else {
			throw new ScriptSemanticsException("Unknown aggregation function " + aggFunction + " used in aggregation " + " ggregation.getName()");
		}
		// build query
		Fact query = this.buildFact(aggregation.getCondition());
		f.setQuery(query);
		
		return f;
	}

	private ExternalFactStore buildFactStore(FactStore store) throws ScriptSemanticsException {
		ExternalFactStore fs = new ExternalFactStore();
		fs.setId(store.getId());
		SimplePredicate p = new SimplePredicate();
		p.setName(store.getPredicate());
		Class[] types = new Class[store.getTypeNames().size()];
		for (int i=0;i<types.length;i++) {
			try {
				types[i] = classloader.loadClass(store.getTypeNames().get(i));
			}
			catch (Exception x) {
				throw new ScriptSemanticsException("Cannot load class " + store.getTypeNames().get(i) + this.printPosInfo(store),x);
			}
		}
		p.setSlotTypes(types);
		
		String id = this.getId(p);
		Predicate existingPredicate = predicatesByName.get(id);
		if (existingPredicate==null) {
			predicatesByName.put(this.getId(p),p);
		}
		else
			p = (SimplePredicate)existingPredicate;
		
		fs.setPredicate(p);
		
		return fs;
	}

	private String getId(Constant c) {
		return c.getRef()==null?(c.getObject()==null?null:c.getObject().toString()):c.getRef();
	}
	// take over query annotations for the query predicate
	private void takeOverAnnotations(Query q) {
		Predicate p = q.getPredicate();
		for (Entry<String,String> e:q.getAnnotations().entrySet())  {
			String key = e.getKey();
			p.addAnnotation(key,e.getValue());	
			if (AnnotationKeys.TAKE_GENERATE_SLOTS.equals(key)) {
				// set slot names from annotation
				List<String> slots = new ArrayList<String>();
				for (StringTokenizer tok = new StringTokenizer(e.getValue(),",");tok.hasMoreTokens();) {
					slots.add(tok.nextToken().trim());
				}
				if (slots.size()!=p.getSlotTypes().length) {
					// TODO log warning
				}
				else {
					String[] arr = slots.toArray(new String[slots.size()]);
					if (p instanceof AbstractPredicate) 
						((AbstractPredicate)p).setSlotNames(arr);
					//else  TODO log warning
				}
			}
		}
	}
	private String getId(Predicate p) {
		return p.getName()+'_'+p.getSlotTypes().length+(p.isNegated()?"-":"+");
	}
	private void checkId(KnowledgeElement e,Set<String> ids) throws ScriptException {
		String id = e.getId();
		if (id==null || id.trim().length()==0)
			throw new ScriptSemanticsException("This element has no id: " + e);
		else if (ids.contains(id)) 
			throw new ScriptSemanticsException("The knowledge base has two elements with this id: " + id);
		else 
			ids.add(id);
	}
	private List<Variable> buildVariables(VariableDeclaration vd) throws ScriptException {
		Class clazz = this.classForName(vd.getType(),vd);	
		List<Variable> variables = new ArrayList<Variable>();
		for (String name:vd.getNames()) {
			Variable v = new Variable();
			v.setName(name);
			v.setType(clazz);
			variables.add(v);
		}
		return variables;
	}
	private List<Constant> buildConstants(RefDeclaration cd) throws ScriptException {
		Class clazz = this.classForName(cd.getType(),cd);
		List<Constant> constants = new ArrayList<Constant>();
		for (String name:cd.getNames()) {
			Constant c = new Constant();
			c.setRef(name);
			c.setType(clazz);
			constants.add(c);
		}
		return constants;
	}
	private Query buildQuery(QuerySpec q) throws ScriptException {
		Query query = new Query();
		String predicateName = q.getPredicate()+'_'+q.getIoSpec().size()+(q.isNegated()?"-":"+");
		Predicate p = predicatesByName.get(predicateName);
		if (p==null)
			throw new ScriptSemanticsException("Query references a predicate that is not defined in the script: " + q.getPredicate() + " with " + q.getIoSpec().size() + " slots" + this.printPosInfo(q));
		
		query.setPredicate(p);
		boolean[] io = new boolean[q.getIoSpec().size()];
		for (int i=0;i<io.length;i++)
			io[i]=q.getIoSpec().get(i);
		query.setInputParams(io);
		return query;
	}
	private DerivationRule buildRule(Rule r) throws ScriptException {
		DerivationRule rule = new DerivationRule();
		rule.setId(r.getId());
		List<Prerequisite> body = new ArrayList<Prerequisite>();
		for (int i=0;i<r.getConditions().size()-1;i++) {
			Prerequisite prereq = buildPrerequisite(r.getConditions().get(i));
			body.add(prereq);
		}
		rule.setBody(body);
		Fact head = buildFact(r.getConditions().get(r.getConditions().size()-1));
		rule.setHead(head);
		return rule;
	}
	private Prerequisite buildPrerequisite(Condition c) throws ScriptException {
		Prerequisite p = new Prerequisite();
		nz.org.take.Term[] terms = buildTerms(c);
		nz.org.take.Predicate predicate = buildPredicate(c,terms);
		p.setPredicate(predicate);
		p.setTerms(terms);
		return p;
	}
	private Fact buildFact(Condition c) throws ScriptException {
		Fact p = new Fact();
		nz.org.take.Term[] terms = buildTerms(c);
		nz.org.take.Predicate predicate = buildPredicate(c,terms);
		p.setPredicate(predicate);
		p.setTerms(terms);
		return p;
	}
	private Clause buildClause(Condition c) throws ScriptException {
		Fact p = new Fact();
		nz.org.take.Term[] terms = buildTerms(c);
		nz.org.take.Predicate predicate = buildPredicate(c,terms);
		p.setPredicate(predicate);
		p.setTerms(terms);
		for (int i=0;i<terms.length;i++){
			if (!(terms[i] instanceof Constant)) {
				// fact contains variables - in this case build a rule
				DerivationRule rule = new DerivationRule();
				rule.setHead(p);
				return rule;
			}
		}
		return p;
	}
	// try to find a method for the term (types) and the name
	// this is used to decide the predicate type
	private Method getMethod(String name,nz.org.take.Term[] terms) throws ScriptException  {		
		Class clazz = terms[0].getType();
		Class[] paramTypes = new Class[terms.length-1];
		Method m = null;
		for (int i=1;i<terms.length;i++) {
			paramTypes[i-1]=terms[i].getType();
		}
		try {
			m = clazz.getMethod(name,paramTypes);
		}
		catch (Exception x) {}
		if (m==null) {
			// start investigating supertypes
			Method[] methods = clazz.getMethods();
			for (Method m1:methods) {
				if (m1.getReturnType()==Boolean.TYPE && Modifier.isPublic(m1.getModifiers())) {
					if (m1.getName().equals(name) && m1.getParameterTypes().length==paramTypes.length) {
						// check types
						boolean ok = true;
						for (int i=0;i<paramTypes.length;i++) {
							ok = ok && m1.getParameterTypes()[i].isAssignableFrom(paramTypes[i]);
						}
						if (ok){
							m = m1;
							break;
						}
					}
				}
			}
		}
		return m;
		
	}
	//	 try to find a property for the clazz and the name, return null if there is no such property
	private PropertyDescriptor getProperty(String name,Class clazz) throws ScriptException  {
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
			PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property:properties) {
				if (name.equals(property.getName()) && property.getReadMethod()!=null) {
					return property;
				}
			}
		}
		catch (Exception x) {}
		return null;
		
	}
	private nz.org.take.Predicate buildPredicate(Condition c,nz.org.take.Term[] terms) throws ScriptException {
		Predicate predicate = null;
		boolean negated = c.isNegated();
		String name = c.getPredicate();

		if (c.isPrimitiveComparison()) {
			try {
				Comparison comp = new Comparison(name);
				Class[] types = new Class[2];
				types[0] = terms[0].getType();
				types[1] = terms[1].getType();
				// TODO validate numerical types
				comp.setTypes(types);
				return comp;
			}
			catch (TakeException x) {
				throw new ScriptException("Cannot build comparison for predicate symbol " + name,x);
			}
		}
		
		Method m = getMethod(name,terms);
		PropertyDescriptor property = null;
		if (m==null)
			property = getProperty(c.getPredicate(), terms[0].getType());
		
		if (m!=null) {
			LOGGER.debug("Interpreting predicate symbol " + name + this.printPosInfo(c) + " as Java method " + m);
			JPredicate p = new JPredicate();
			Class[] paramTypes = getParamTypes( terms);
			p.setMethod(m);
			p.setNegated(negated);
			predicate=p;			
		}
		else if (property!=null) {
			LOGGER.debug("Interpreting predicate symbol " + name + this.printPosInfo(c) + " as bean property");
			PropertyPredicate p = new PropertyPredicate();
			p.setProperty(property);
			p.setOwnerType(terms[0].getType());
			p.setNegated(negated);
			// todo remove this line that just does lazy initialization
			predicate=p;			
		}
		else {
			LOGGER.debug("Interpreting predicate symbol " + name + this.printPosInfo(c) + " as simple predicate");
			SimplePredicate p = new SimplePredicate();
			p.setName(name);
			p.setNegated(negated);
			Class[] types = new Class[terms.length];
			for (int i=0;i<terms.length;i++) { 
				types[i] = terms[i].getType();			
			}
			p.setSlotTypes(types);
			predicate=p;
		}
		String id = this.getId(predicate);
		Predicate existingPredicate = predicatesByName.get(id);
		if (existingPredicate==null) {
			predicatesByName.put(this.getId(predicate),predicate);
			return predicate;
		}
		else
			return existingPredicate;
	}
	private nz.org.take.Term[] buildTerms(TermContainer c) throws ScriptException {
		nz.org.take.Term[] terms = new nz.org.take.Term[c.getTerms().size()];
		for (int i=0;i<terms.length;i++)
			terms[i] = buildTerm(c.getTerms().get(i));
		return terms;
	}
	private Method findMethod(String name,nz.org.take.Term[] terms) throws ScriptException {
		Class clazz = terms[0].getType();
		Class[] paramTypes = getParamTypes( terms);
		try {
			return clazz.getMethod(name,paramTypes);
		} catch (Exception e) {
			return null;
		}
	}
	private Method findPropertyAccessor(String name,nz.org.take.Term[] terms) throws ScriptException {
		if (terms.length>1)
			return null; // getters dont have parameters
		Class clazz = terms[0].getType();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
			for (PropertyDescriptor property:beanInfo.getPropertyDescriptors()) {
				if (property.getName().equals(name)) 
					return property.getReadMethod();
			}
			return null;
		} catch (IntrospectionException e1) {
			return null;
		}
	}

	private nz.org.take.Term buildTerm(Term t) throws ScriptException {
		if (t instanceof VariableTerm && ((VariableTerm)t).isSimple()) {
			String name = ((VariableTerm)t).getName();
			Variable var = variables.get(name);
			Constant con = constants.get(name);
			if (var==null && con==null)
				throw new ScriptSemanticsException("This variable or reference is used before it is defined: " + print(t));
			if (var!=null && con!=null)
				throw new ScriptSemanticsException("Symbol is used to define an object reference and a variable: " + name);
			return var==null?con:var;
		}
		else if (t instanceof VariableTerm && !((VariableTerm)t).isSimple()) {
			List<String> names = ((VariableTerm)t).getNames();
			// base term
			VariableTerm v = new VariableTerm(names.get(0));
			nz.org.take.Term part = buildTerm(v);
			
			for (int i=1;i<names.size();i++) {
				String f = names.get(i);	
				nz.org.take.Term[] args = new nz.org.take.Term[1];
				args[0] = part;
				Function function = this.aggregationFunctions.get(f);
				if (function==null) {
					Method m = this.findMethod(f, args);
					if (m==null) // check whether there is such a property
						m = findPropertyAccessor(f, args);
					if (m==null)
						throw new ScriptSemanticsException("No method or property found for symbol: " + f);
					JFunction jfunction = new JFunction();
					jfunction.setMethod(m);
					function=jfunction;
				}
				nz.org.take.ComplexTerm  cplxTerm = new nz.org.take.ComplexTerm ();
				cplxTerm.setFunction(function);
				cplxTerm.setTerms(args);
				part = cplxTerm;
			}
			return part;
		}
		else if (t instanceof ConstantTerm) {
			ConstantTerm ct = (ConstantTerm)t;
			if (ct.getType().equals("java.lang.String")) {
				Constant c = new Constant();
				c.setObject(ct.getValue());
				return c;
			}
			else if (ct.getType().equals("java.lang.Integer")) {
				Constant c = new Constant();
				c.setObject(Integer.parseInt(ct.getValue()));
				return c;
			}
			else if (ct.getType().equals("java.lang.Double")) {
				Constant c = new Constant();
				c.setObject(Double.parseDouble(ct.getValue()));
				return c;
			}
			
			else 
				throw new ScriptSemanticsException("Constant terms of this type are not yet supported: " + ct.getType() + " " + printPosInfo((Term)ct));
		}
		else if (t instanceof ComplexTerm) {
			ComplexTerm ct = (ComplexTerm)t;
			String f = ct.getFunction();
			nz.org.take.Term[] terms = buildTerms(ct);
			// try to build aggregation function
			Function function = this.aggregationFunctions.get(f);
			// try to build arithmetic operation
			if (function==null) {
				function=buildArithmeticFunction(f,terms);
			}
			// try to build jfunction
			if (function==null) {
				Method m = this.findMethod(f, terms);
				if (m==null) // check whether there is such a property
					m = findPropertyAccessor(f, terms);
				if (m==null)
					throw new ScriptSemanticsException("No method or property found for symbol: " + f);
				JFunction jfunction = new JFunction();
				jfunction.setMethod(m);
				function=jfunction;
			}

			nz.org.take.ComplexTerm cplxTerm = new nz.org.take.ComplexTerm ();
			cplxTerm.setFunction(function);
			cplxTerm.setTerms(terms);
			return cplxTerm;
		}
		else
			throw new ScriptSemanticsException("This term type  is not yet supported " + print(t));
	}
	private Function buildArithmeticFunction(String f, nz.org.take.Term[] terms) {
		if (terms.length!=2)
			return null; // only binary functions supported
		if (!isNumericType(terms[0].getType()) || !isNumericType(terms[1].getType()))
			return null;
		return nz.org.take.BinaryArithmeticFunction.getInstance(f, terms[0].getType(), terms[1].getType());
	}

	private boolean isNumericType(Class clazz) {
		return clazz==Integer.class || clazz==Integer.TYPE || clazz==Double.class || clazz==Double.TYPE;
	}
	private Class[] getParamTypes(nz.org.take.Term[] terms) {
		Class[] paramTypes = new Class[terms.length-1];
		for (int i=1;i<terms.length;i++) {
			paramTypes[i-1]=terms[i].getType();
		}
		return paramTypes;
	}
	private String print(ScriptElement e) {
		return new StringBuffer()
			.append(e.toString())
			.append(printPosInfo(e))
			.toString();
	}
	private String printPosInfo(ScriptElement e) {
		return new StringBuffer()
			.append("[in line ")
			.append(e.getLine())
			.append(']')
			.toString();
	}
	private String printPosInfo(Term t) {
		return  printPosInfo((ScriptElement)t);
	}
	private String print(Term t) {
		return print((ScriptElement)t);
	}

	public ClassLoader getClassloader() {
		return classloader;
	}

	public void setClassloader(ClassLoader classloader) {
		this.classloader = classloader;
	}
	private Class classForName(String type,ScriptElement e) throws ScriptSemanticsException {
		try {
			if ("char".equals(type))
				return Character.TYPE;
			if ("byte".equals(type))
				return Byte.TYPE;
			if ("int".equals(type))
				return Integer.TYPE;
			if ("short".equals(type))
				return Short.TYPE;
			if ("long".equals(type))
				return Long.TYPE;
			if ("double".equals(type))
				return Double.TYPE;
			if ("float".equals(type))
				return Float.TYPE;
			if ("boolean".equals(type))
				return Boolean.TYPE;
			return  this.classloader.loadClass(type);
		}
		catch (ClassNotFoundException x) {
			throw new ScriptSemanticsException("Can not load the following type referenced in script: " + type + this.printPosInfo(e),x);
		}	
	}
}
