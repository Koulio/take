package nz.org.take.script;

public class NoSuchPredicateException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private String predicateName;
	
	public NoSuchPredicateException(String predicateName) {
		super(String.format("The predicate '{0}' does not exist.", predicateName));
		
		this.predicateName = predicateName;
	}
	
	public String getPredicateName() {
		return predicateName;
	}

}
