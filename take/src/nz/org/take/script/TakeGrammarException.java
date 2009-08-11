package nz.org.take.script;

import org.antlr.runtime.RecognitionException;

public class TakeGrammarException extends RecognitionException {
	private static final long serialVersionUID = 1L;
	
	private Exception innerException;
	
	public TakeGrammarException(Exception innerException) {
		super();
		
		this.innerException = innerException;
	}
	
	public Exception getInnerException() {
		return innerException;
	}
}
