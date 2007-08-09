package nz.org.take.compiler.reference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nz.org.take.ComplexTerm;
import nz.org.take.JFunction;
import nz.org.take.Term;
import nz.org.take.compiler.CompilerException;

/**
 * Utility class to keep track of term to expression mappings when generating code for rules.
 * @author jens
 * @param <Term>
 * @param <String>
 */
public class Bindings  {
	private List <Term> terms = null;
	private List<Term> agenda = new ArrayList<Term>();
	private HashMap<Term,String> delegate = new HashMap<Term,String>();

	public Bindings(List<Term> terms) {
		super();
		this.terms = terms;
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
		else 
			throw new CompilerException("ComplexTerms are only supported if the function is a JFunction");
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

}
