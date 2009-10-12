package nz.org.take.script;

/**
 * Thrown during script parsing when a query is declared more than once.
 * 
 * Because queries can be overloaded, this requires that a query with the same 
 * name **and** input slot types exist.
 */
public class DuplicateQueryException extends DuplicateSymbolException {
	private static final long serialVersionUID = 1L;
	private final QueryDeclaration queryDeclaration;
	
	public DuplicateQueryException(QueryDeclaration queryDeclaration) {
		super(String.format("A query matching the format '{0}' already exists.", 
				queryDeclaration.getQuerySignature()));
		
		this.queryDeclaration = queryDeclaration;
	}
	
	public QueryDeclaration getQueryDeclaration() {
		return queryDeclaration;
	}
}
