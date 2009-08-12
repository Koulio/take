package nz.org.take.script;

/**
 * Thrown during script parsing when a symbol is declared more than once.
 */
public class DuplicateSymbolException extends Exception {
	private static final long serialVersionUID = 1L;
	private final String symbolName;
	
	public DuplicateSymbolException(String symbolName) {
		super(String.format("A symbol with the name '{0}' already exists.", symbolName));
		
		this.symbolName = symbolName;
	}
	
	public String getSymbolName() {
		return symbolName;
	}
}
