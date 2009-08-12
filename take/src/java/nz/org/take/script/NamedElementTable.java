package nz.org.take.script;


import java.util.HashMap;
import java.util.Map;
import nz.org.take.NamedElement;


/**
 * A specialised lookup table for elements.
 */
public class NamedElementTable<T extends NamedElement> {
	
	private Map<String, T> elementMap = new HashMap<String, T>();

	public boolean containsName(String name) {
		return elementMap.containsKey(name);
	}

	public T get(String name) {
		return elementMap.get(name);
	}

	public void put(T element) throws DuplicateSymbolException {
		if (this.containsName(element.getName()))
			throw new DuplicateSymbolException(element.getName());
		
		elementMap.put(element.getName(), element);
	}
	
	public Iterable<T> getValues() {
		return elementMap.values();
	}

}
