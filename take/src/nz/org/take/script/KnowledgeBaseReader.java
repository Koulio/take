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
import java.io.StringReader;
import java.lang.reflect.Method;
import java.util.*;
import nz.org.take.DefaultKnowledgeBase;
import nz.org.take.DerivationRule;
import nz.org.take.Fact;
import nz.org.take.JavaMethodPredicate;
import nz.org.take.KnowledgeBase;
import nz.org.take.KnowledgeElement;
import nz.org.take.Prerequisite;
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
		KnowledgeBase kb = new DefaultKnowledgeBase(); 
		Set<String> ids = new HashSet<String>(); 
		Map<String,Variable> variables = new HashMap<String,Variable>();
		for (Object part:script.getElements()) {
			if (part instanceof VariableDeclaration) {
				Variable v = buildVariable((VariableDeclaration)part);
				variables.put(v.getName(),v);
			}
			else if (part instanceof Rule) {
				DerivationRule r = buildRule(kb,variables,(Rule)part);
				checkId(r,ids);
				kb.add(r);
			}
		}
		return kb;
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
	private Variable buildVariable(VariableDeclaration vd) throws ScriptException {
		Variable v = new Variable();
		v.setName(vd.getName());
		try {
			Class clazz = this.classloader.loadClass(vd.getType());
			v.setType(clazz);
			return v;
		}
		catch (ClassNotFoundException x) {
			throw new ScriptSemanticsException("Can not load the following type referenced in script: " + vd.getType(),x);
		}		
	}
	private DerivationRule buildRule(KnowledgeBase kb,Map<String,Variable> variables, Rule r) throws ScriptException {
		DerivationRule rule = new DerivationRule();
		rule.setId(r.getId());
		List<Prerequisite> body = new ArrayList<Prerequisite>();
		for (int i=0;i<r.getConditions().size()-1;i++) {
			Prerequisite prereq = buildPrerequisite(variables,r.getConditions().get(i));
			body.add(prereq);
		}
		rule.setBody(body);
		Fact head = buildFact(variables,r.getConditions().get(r.getConditions().size()-1));
		rule.setHead(head);
		return rule;
	}
	private Prerequisite buildPrerequisite(Map<String,Variable> variables, Condition c) throws ScriptException {
		Prerequisite p = new Prerequisite();
		nz.org.take.Term[] terms = buildTerms(variables,c);
		nz.org.take.Predicate predicate = buildPredicate(variables,c,terms);
		p.setPredicate(predicate);
		p.setTerms(terms);
		return p;
	}
	private Fact buildFact(Map<String,Variable> variables, Condition c) throws ScriptException {
		Fact p = new Fact();
		nz.org.take.Term[] terms = buildTerms(variables,c);
		nz.org.take.Predicate predicate = buildPredicate(variables,c,terms);
		p.setPredicate(predicate);
		p.setTerms(terms);
		return p;
	}
	private nz.org.take.Predicate buildPredicate(Map<String,Variable> variables,Condition c,nz.org.take.Term[] terms) throws ScriptException {
		PredicateType type = c.getPredicateType();
		if (type==PredicateType.SIMPLE) {
			SimplePredicate p = new SimplePredicate();
			Class[] types = new Class[terms.length];
			for (int i=0;i<terms.length;i++) { 
				types[i] = terms[i].getType();			
			}
			p.setSlotTypes(types);
			return p;
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
				return p;
			} catch (Exception e) {
				throw new ScriptSemanticsException("No method for predicate found: " + c.getPredicate(),e);
			}
			
		}
		else 
			throw new ScriptSemanticsException ("Unsupported predicate type encountered in condition: " + c);
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
		else
			throw new ScriptSemanticsException("Not yet supported feature: only variable terms are supported at the moment");
	}
}
