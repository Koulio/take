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

import java.util.*;
import nz.org.take.AggregationFunction;
import nz.org.take.BinaryArithmeticFunction;
import nz.org.take.ComplexTerm;
import nz.org.take.JFunction;
import nz.org.take.Term;
import nz.org.take.UnaryArithmeticFunction;
import nz.org.take.compiler.CompilerException;
import nz.org.take.compiler.NameGenerator;

/**
 * Utility class to keep track of term to expression mappings when generating code for rules.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */
public class Bindings  {
	private Collection<Term> terms = null;
	private List<Term> agenda = new ArrayList<Term>();
	private HashMap<Term,String> delegate = new HashMap<Term,String>();
	private NameGenerator naming = null; // needed to look up class name for generated functions
	private Set<Term> assigned = new HashSet<Term>();
	
	public Bindings(Collection<Term> terms,NameGenerator naming) {
		super();
		this.terms = terms;
		this.naming = naming; 
	}
	
	public void put(Term t,String ref) throws CompilerException {
		delegate.put(t,ref) ;
		this.agenda.add(t);
		// recursion !!
		resolveOwners(t);

	}

	private void resolveOwners(Term t) throws CompilerException {
		ComplexTerm p = null;
		for (Term v:terms) {
			if ((p=isChild(v,t))!=null) {
				boolean result = checkComplexTerm(p);
				if (result) {
					delegate.put(p,createRef(p));
				}
			}
			p = null;
		}
	}
	private String createRef(ComplexTerm t) throws CompilerException {
		StringBuffer buf = new StringBuffer();
		Term[] terms = t.getTerms();
		if (t.getFunction() instanceof JFunction) {
			JFunction f = (JFunction)t.getFunction();
			if (f.isStatic()) {
				buf.append(f.getMethod().getDeclaringClass());
				buf.append('.');
				buf.append(f.getName());
				for (int i=0;i<terms.length;i++) {
					if (i>0)
						buf.append(',');
					buf.append(this.getRef(terms[i]));
				}			
				buf.append(')');
			}
			else {
				buf.append(this.getRef(terms[0]));
				buf.append('.');
				buf.append(f.getName());
				buf.append('(');
				for (int i=1;i<terms.length;i++) {
					if (i>1)
						buf.append(',');
					buf.append(this.getRef(terms[i]));
				}			
				buf.append(')');
			}
		}
		else if (t.getFunction() instanceof AggregationFunction) {
			AggregationFunction f = (AggregationFunction)t.getFunction();
			buf.append(this.naming.getAggregationFunctionsRegistryClassName());
			buf.append('.');
			buf.append(naming.getMethodName(f));
			buf.append('(');
			for (int i=0;i<terms.length;i++) {
				if (i>0)
					buf.append(',');
				buf.append(this.getRef(terms[i]));
			}			
			buf.append(')');
		}
		else if (t.getFunction() instanceof BinaryArithmeticFunction) {
			BinaryArithmeticFunction f = (BinaryArithmeticFunction)t.getFunction();
			buf.append(this.getRef(terms[0]));
			buf.append(f.getName());
			buf.append(this.getRef(terms[1]));
		}
		else if (t.getFunction() instanceof UnaryArithmeticFunction) {
			UnaryArithmeticFunction f = (UnaryArithmeticFunction)t.getFunction();
			buf.append(f.getName());
			buf.append(this.getRef(terms[0]));
		}
		else 
			throw new CompilerException("ComplexTerms are only supported if the function is a JFunction or an AggregationFunction");
		return buf.toString();
	}

	/**
	 * Check whether all parts of a complex term are already resolved.
	 * @param term
	 * @return
	 */
	private boolean checkComplexTerm(ComplexTerm term) {
		boolean result = true;
		for (Term t:term.getTerms()) {
			result = result && this.delegate.get(t)!=null;
			if (t instanceof ComplexTerm)
				result = result && checkComplexTerm((ComplexTerm)t);
		}
		return result;
	}

	private ComplexTerm  isChild(Term parent,Term child) {
		if (parent instanceof ComplexTerm) {
			Term[] children = ((ComplexTerm)parent).getTerms();
			for (Term t:children) {
				if (t.equals(child))
					return (ComplexTerm)parent;
			}
		}
		return null;
	}
	/**
	 * Get the terms currently on the agenda. This will rteset the agenda.
	 * @return a list of terms 
	 */
	public List<Term> getAndResetAgenda() {
		List<Term> terms = new ArrayList<Term>(this.agenda.size());
		terms.addAll(this.agenda);
		this.agenda.clear();
		return agenda;
	}
	/**
	 * Get the reference string for a term.
	 * @param t a term
	 * @return a string
	 */
	public String getRef(Term t) {
		return this.delegate.get(t);
	}
	/**
	 * Check whether the term has a binding
	 * @param t a term
	 * @return a boolean
	 */
	public boolean hasBinding(Term t) {
		return this.delegate.containsKey(t);
	}
	
	/**
	 * Register a term as being assigned. 
	 * This means that assignment has been printed.
	 * @param t a term
	 */
	public void assigned(Term t) {
		assigned.add(t);
	}
	
	/**
	 * Indicates whether the term has been assigned.
	 * @param t a term
	 * @return a boolean
	 */
	public boolean isAssigned(Term t) {
		return assigned.contains(t);
	}
	

}
