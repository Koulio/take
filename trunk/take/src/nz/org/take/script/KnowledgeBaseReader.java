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

import java.io.Reader;
import java.lang.reflect.Method;
import java.util.*;
import nz.org.take.Constant;
import nz.org.take.DefaultKnowledgeBase;
import nz.org.take.DerivationRule;
import nz.org.take.Fact;
import nz.org.take.JavaMethodPredicate;
import nz.org.take.KnowledgeBase;
import nz.org.take.KnowledgeElement;
import nz.org.take.Predicate;
import nz.org.take.Prerequisite;
import nz.org.take.Query;
import nz.org.take.SimplePredicate;
import nz.org.take.Variable;
import nz.org.take.script.parser.Parser;

/**
 * Utility class used to import a knowledge base from a script.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class KnowledgeBaseReader {
	private ClassLoader classloader = KnowledgeBaseReader.class.getClassLoader();
	private static Parser parser =null;
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
	 * Read the knowledge base from a script.
	 */
	public KnowledgeBase read(Reader input) throws ScriptException {
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
		for (Object part:script.getElements()) {
			if (part instanceof VariableDeclaration) {
				List<Variable> vars = buildVariables((VariableDeclaration)part);
				for (Variable v:vars)
					variables.put(v.getName(),v);
			}
			else if (part instanceof QuerySpec) {
				// must be build later once we know the predicates
				querySpecs.add((QuerySpec)part); 
			}
			else if (part instanceof Rule) {
				Rule rule = (Rule)part;
				if (rule.getConditions().size()==1) {
					Fact f = buildFact(variables,predicatesByName,rule.getConditions().get(0));
					f.setId(rule.getId());
					checkId(f,ids);
					kb.add(f);					
				}
				else {					
					DerivationRule r = buildRule(variables,predicatesByName,(Rule)part);
					checkId(r,ids);
					kb.add(r);
				}				
			}
			// ignore everything else, in particular comments
		}
		// now do the predicates
		for (QuerySpec q:querySpecs) {
			Query query = buildQuery(predicatesByName,q);
			kb.add(query);
		}
		return kb;
	}
	private String getId(Predicate p) {
		return p.getName()+'_'+p.getSlotTypes().length;
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
			throw new ScriptSemanticsException("Can not load the following type referenced in script: " + vd.getType(),x);
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
	private Query buildQuery(Map<String,Predicate> predicatesByName,QuerySpec q) throws ScriptException {
		Query query = new Query();
		String predicateName = q.getPredicate()+'_'+q.getIoSpec().size();
		Predicate p = predicatesByName.get(predicateName);
		if (p==null)
			throw new ScriptSemanticsException("Query references a predicate that is not defined in the script: " + q.getPredicate() + " with " + q.getIoSpec().size() + " slots");
		
		query.setPredicate(p);
		boolean[] io = new boolean[q.getIoSpec().size()];
		for (int i=0;i<io.length;i++)
			io[i]=q.getIoSpec().get(i);
		query.setInputParams(io);
		return query;
	}
	private DerivationRule buildRule(Map<String,Variable> variables, Map<String,Predicate> predicatesByName,Rule r) throws ScriptException {
		DerivationRule rule = new DerivationRule();
		rule.setId(r.getId());
		List<Prerequisite> body = new ArrayList<Prerequisite>();
		for (int i=0;i<r.getConditions().size()-1;i++) {
			Prerequisite prereq = buildPrerequisite(variables,predicatesByName,r.getConditions().get(i));
			body.add(prereq);
		}
		rule.setBody(body);
		Fact head = buildFact(variables,predicatesByName,r.getConditions().get(r.getConditions().size()-1));
		rule.setHead(head);
		return rule;
	}
	private Prerequisite buildPrerequisite(Map<String,Variable> variables,Map<String,Predicate> predicatesByName, Condition c) throws ScriptException {
		Prerequisite p = new Prerequisite();
		nz.org.take.Term[] terms = buildTerms(variables,c);
		nz.org.take.Predicate predicate = buildPredicate(variables,predicatesByName,c,terms);
		p.setPredicate(predicate);
		p.setTerms(terms);
		return p;
	}
	private Fact buildFact(Map<String,Variable> variables, Map<String,Predicate> predicatesByName, Condition c) throws ScriptException {
		Fact p = new Fact();
		nz.org.take.Term[] terms = buildTerms(variables,c);
		nz.org.take.Predicate predicate = buildPredicate(variables,predicatesByName,c,terms);
		p.setPredicate(predicate);
		p.setTerms(terms);
		return p;
	}
	private nz.org.take.Predicate buildPredicate(Map<String,Variable> variables,Map<String,Predicate> predicatesByName,Condition c,nz.org.take.Term[] terms) throws ScriptException {
		Predicate predicate = null;
		PredicateType type = c.getPredicateType();
		if (type==PredicateType.SIMPLE) {
			SimplePredicate p = new SimplePredicate();
			p.setName(c.getPredicate());
			Class[] types = new Class[terms.length];
			for (int i=0;i<terms.length;i++) { 
				types[i] = terms[i].getType();			
			}
			p.setSlotTypes(types);
			predicate=p;
		}
		else if (type==PredicateType.JAVA) {
			JavaMethodPredicate p = new JavaMethodPredicate();
			Class clazz = terms[0].getType();
			// TODO matching using superclasses
			Class[] paramTypes = new Class[terms.length-1];
			for (int i=1;i<terms.length;i++) {
				paramTypes[i-1]=terms[i].getType();
			}
			Method m;
			try {
				m = clazz.getMethod(c.getPredicate(),paramTypes);
				p.setMethod(m);
				predicate=p;
			} catch (Exception e) {
				throw new ScriptSemanticsException("No method for predicate found: " + c.getPredicate(),e);
			}
			
		}
		else 
			throw new ScriptSemanticsException ("Unsupported predicate type encountered in condition: " + c);
		predicatesByName.put(this.getId(predicate),predicate);
		return predicate;
	}
	private nz.org.take.Term[] buildTerms(Map<String,Variable> variables,Condition c) throws ScriptException {
		nz.org.take.Term[] terms = new nz.org.take.Term[c.getTerms().size()];
		for (int i=0;i<terms.length;i++)
			terms[i] = buildTerm(variables,c.getTerms().get(i));
		return terms;
	}
	private nz.org.take.Term buildTerm(Map<String,Variable> variables,Term t) throws ScriptException {
		if (t instanceof VariableTerm) {
			String name = ((VariableTerm)t).getName();
			Variable var = variables.get(name);
			if (var==null)
				throw new ScriptSemanticsException("This variable is used before it is defined: " + name);
			return var;
		}
		else if (t instanceof ConstantTerm) {
			ConstantTerm ct = (ConstantTerm)t;
			if (ct.getType().equals("java.lang.String")) {
				Constant c = new Constant();
				c.setObject(ct.getValue());
				return c;
			}
			else 
				throw new ScriptSemanticsException("Constant terms of this type are not yet supported " + ct.getType());
		}
		else
			throw new ScriptSemanticsException("This term type  is not yet supported " + t);
	}
}
