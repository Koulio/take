package nz.org.take.script;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import nz.org.take.Term;

public class TermList extends ArrayList<Term> {
	private static final long serialVersionUID = 1L;

	public TermList() {
		super();
	}
	
	public TermList(Collection<? extends Term> collection) {
		super(collection);
	}
	
	public List<Class<?>> getTypes() {
		List<Class<?>> types = new ArrayList<Class<?>>();
		
		for (Term term : this)
			types.add(term.getType());
		
		return types;
	}
	
	public Class[] getArrayOfTypes() {
		List<Class<?>> types = getTypes();
		Class[] typesArray = new Class[types.size()];
		
		return types.toArray(typesArray);
	}
	
	@Override
	public Term[] toArray() {
		Term[] terms = new Term[size()];
		return super.toArray(terms);
	}
	
}
