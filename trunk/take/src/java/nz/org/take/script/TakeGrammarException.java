package nz.org.take.script;

import org.antlr.runtime.IntStream;
import org.antlr.runtime.RecognitionException;

public class TakeGrammarException extends RecognitionException {
	private static final long serialVersionUID = 1L;
	
	private Exception innerException;
	
	public TakeGrammarException(Exception innerException) {
		super();
		
		this.innerException = innerException;
	}
	
	public TakeGrammarException(IntStream input, Exception innerException) {
		super(input);
		
		this.innerException = innerException;
	}
	
	public Exception getInnerException() {
		return innerException;
	}
	
	@Override
	public String getMessage() {
		return innerException.getMessage();
	}
}
