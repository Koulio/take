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

package nz.org.take.script;

import java.beans.*;
import java.io.Reader;
import java.lang.reflect.*;
import java.util.*;
import java.util.Map.Entry;

import javax.script.Bindings;
import javax.script.SimpleBindings;

import org.apache.log4j.Logger;
import nz.org.take.*;
import nz.org.take.script.parser.Parser;

/**
 * Utility class used to import a knowledge base from a script.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class KnowledgeBaseReader {
	private ClassLoader classloader = KnowledgeBaseReader.class.getClassLoader();
	private static Parser parser =null;
	public Logger LOGGER = Logger.getLogger(KnowledgeBaseReader.class);
	
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
	 * @param bindings the bindings associating refs with objects
	 * @return the knowledge base 
	 */
	public KnowledgeBase read(Reader input) throws ScriptException {
		return read(input,new SimpleBindings());
	}
	/**
	 * Read the knowledge base from a script.
	 * @param input the reader
	 * @param bindings the bindings associating refs with objects
	 * @return the knowledge base 
	 */
	public KnowledgeBase read(Reader input,Bindings bindings) throws ScriptException {
		Script script = null;
		try {
			script = parse(input);
		} catch (Exception e) {
			throw new ScriptSyntaxException("Cannot parse script",e);
		}
		List<QuerySpec> querySpecs = new ArrayList<QuerySpec>();
		// to store the predicates - the keys are names+number of slots
		// this supports reusing the names to some extent, but not true 
		// polymorphism - see also getId
		Map<String,Predicate> predicatesByName = new HashMap<String,Predicate>();
		
		KnowledgeBase kb = new DefaultKnowledgeBase(); 
		Set<String> ids = new HashSet<String>(); 
		Map<String,Variable> variables = new HashMap<String,Variable>();
		Map<String,Constant> constants = new HashMap<String,Constant>();
		List<Annotation> annotations = new ArrayList<Annotation>();
		for (Object part:script.getElements()) {
			if (part instanceof VariableDeclaration) {
				List<Variable> vars = buildVariables((VariableDeclaration)part);
				for (Variable v:vars)
					variables.put(v.getName(),v);
				annotations.clear(); // var declarations do not support annotations
			}
			if (part instanceof RefDeclaration) {
				List<Constant> cons = buildConstants((RefDeclaration)part,bindings);
				for (Constant c:cons)
					constants.put(c.getName(),c);
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
			else if (part instanceof Rule) {
				Rule rule = (Rule)part;
				
				if (rule.getConditions().size()==1) {
					Fact f = buildFact(variables,constants,predicatesByName,rule.getConditions().get(0));
					f.setId(rule.getId());
					checkId(f,ids);
					annotate(f,annotations); 
					kb.add(f);					
				}
				else {					
					DerivationRule r = buildRule(variables,constants,predicatesByName,(Rule)part);
					checkId(r,ids);
					annotate(r,annotations); 
					kb.add(r);
				}				
			}
			// ignore everything else, in particular comments
		}
		// now do the predicates
		for (QuerySpec q:querySpecs) {
			Query query = buildQuery(predicatesByName,q);
			for (Entry<String,String> e:q.getAnnotations().entrySet())  
				query.addAnnotation(e.getKey(),e.getValue());
			// take over annotations for predicate
			takeOverAnnotations(query);
			kb.add(query);
		}
		return kb;
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
		Class clazz = null;
		try {
			clazz = this.classloader.loadClass(vd.getType());
		}
		catch (ClassNotFoundException x) {
			throw new ScriptSemanticsException("Can not load the following type referenced in script: " + vd.getType() + this.printPosInfo(vd),x);
		}		
		List<Variable> variables = new ArrayList<Variable>();
		for (String name:vd.getNames()) {
			Variable v = new Variable();
			v.setName(name);
			v.setType(clazz);
			variables.add(v);
		}
		return variables;
	}
	private List<Constant> buildConstants(RefDeclaration cd,Bindings bindings) throws ScriptException {
		Class clazz = null;
		try {
			clazz = this.classloader.loadClass(cd.getType());
		}
		catch (ClassNotFoundException x) {
			throw new ScriptSemanticsException("Can not load the following type referenced in script: " + cd.getType() + this.printPosInfo(cd),x);
		}		
		List<Constant> constants = new ArrayList<Constant>();
		for (String name:cd.getNames()) {
			Constant c = new Constant();
			c.setName(name);
			c.setType(clazz);
			Object obj = bindings.get(name);
			if (obj==null) {
				throw new ScriptSemanticsException("No binding found for object reference " + name + this.printPosInfo(cd));
			}
			if (clazz.isAssignableFrom(obj.getClass())) {
				throw new ScriptSemanticsException("Type of object in bindings for "+name+" (" + obj.getClass() + ") is incompatible with declared type " + this.printPosInfo(cd));
			}
			c.setObject(obj);
			constants.add(c);
		}
		return constants;
	}
	private Query buildQuery(Map<String,Predicate> predicatesByName,QuerySpec q) throws ScriptException {
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
	private DerivationRule buildRule(Map<String,Variable> variables,Map<String,Constant> constants, Map<String,Predicate> predicatesByName,Rule r) throws ScriptException {
		DerivationRule rule = new DerivationRule();
		rule.setId(r.getId());
		List<Prerequisite> body = new ArrayList<Prerequisite>();
		for (int i=0;i<r.getConditions().size()-1;i++) {
			Prerequisite prereq = buildPrerequisite(variables,constants,predicatesByName,r.getConditions().get(i));
			body.add(prereq);
		}
		rule.setBody(body);
		Fact head = buildFact(variables,constants,predicatesByName,r.getConditions().get(r.getConditions().size()-1));
		rule.setHead(head);
		return rule;
	}
	private Prerequisite buildPrerequisite(Map<String,Variable> variables,Map<String,Constant> constants,Map<String,Predicate> predicatesByName, Condition c) throws ScriptException {
		Prerequisite p = new Prerequisite();
		nz.org.take.Term[] terms = buildTerms(variables,constants,c);
		nz.org.take.Predicate predicate = buildPredicate(variables,predicatesByName,c,terms);
		p.setPredicate(predicate);
		p.setTerms(terms);
		return p;
	}
	private Fact buildFact(Map<String,Variable> variables, Map<String,Constant> constants,Map<String,Predicate> predicatesByName, Condition c) throws ScriptException {
		Fact p = new Fact();
		nz.org.take.Term[] terms = buildTerms(variables,constants,c);
		nz.org.take.Predicate predicate = buildPredicate(variables,predicatesByName,c,terms);
		p.setPredicate(predicate);
		p.setTerms(terms);
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
	private nz.org.take.Predicate buildPredicate(Map<String,Variable> variables,Map<String,Predicate> predicatesByName,Condition c,nz.org.take.Term[] terms) throws ScriptException {
		Predicate predicate = null;
		boolean negated = c.isNegated();
		String name = c.getPredicate();
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
	private nz.org.take.Term[] buildTerms(Map<String,Variable> variables,Map<String,Constant> constants,TermContainer c) throws ScriptException {
		nz.org.take.Term[] terms = new nz.org.take.Term[c.getTerms().size()];
		for (int i=0;i<terms.length;i++)
			terms[i] = buildTerm(variables,constants,c.getTerms().get(i));
		return terms;
	}
	private Method findMethod(String name,nz.org.take.Term[] terms) throws ScriptException {
		Class clazz = terms[0].getType();
		Class[] paramTypes = getParamTypes( terms);
		try {
			return clazz.getMethod(name,paramTypes);
		} catch (Exception e) {
			throw new ScriptSemanticsException("No method for symbol found: " + name,e);
		}
	}

	private nz.org.take.Term buildTerm(Map<String,Variable> variables,Map<String,Constant> constants,Term t) throws ScriptException {
		if (t instanceof VariableTerm) {
			String name = ((VariableTerm)t).getName();
			Variable var = variables.get(name);
			Constant con = constants.get(name);
			if (var==null && con==null)
				throw new ScriptSemanticsException("This variable or reference is used before it is defined: " + print(t));
			if (var!=null && con!=null)
				throw new ScriptSemanticsException("Symbol is used to define an object reference and a variable: " + name);
			return var==null?con:var;
		}
		else if (t instanceof ConstantTerm) {
			ConstantTerm ct = (ConstantTerm)t;
			if (ct.getType().equals("java.lang.String")) {
				Constant c = new Constant();
				c.setObject(ct.getValue());
				return c;
			}
			
			else 
				throw new ScriptSemanticsException("Constant terms of this type are not yet supported: " + ct.getType() + " " + printPosInfo((Term)ct));
		}
		else if (t instanceof ComplexTerm) {
			ComplexTerm ct = (ComplexTerm)t;
			String f = ct.getFunction();
			nz.org.take.Term[] terms = buildTerms(variables,constants,ct);
			Method m = this.findMethod(f, buildTerms(variables,constants,ct));
			JFunction function = new JFunction();
			function.setMethod(m);
			nz.org.take.ComplexTerm  cplxTerm = new nz.org.take.ComplexTerm ();
			cplxTerm.setFunction(function);
			cplxTerm.setTerms(terms);
			return cplxTerm;
		}
		else
			throw new ScriptSemanticsException("This term type  is not yet supported " + print(t));
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

}
