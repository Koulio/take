package nz.org.take;
/**
 * Exception indicating problems with the expression such as:
 * <ol>
 * <li>Syntax problems (expression cannot be parsed by expression engine)
 * <li>Type problems (there are no known input types for some of the input variables
 * </ol>
 * @author jens dietrich
 *
 */
public class ExpressionException extends Exception {

	public ExpressionException() {
		super();
	}

	public ExpressionException(String message, Throwable cause) {
		super(message, cause);
	}

	public ExpressionException(String message) {
		super(message);
	}

	public ExpressionException(Throwable cause) {
		super(cause);
	}

}
