package nz.org.take.r2ml;

/**
 * @author Bastian Schenke (bastian.schenke@googlemail.com)
 *
 */
public class R2MLException extends Exception {
	
	/**
	 * 
	 */
	public static final byte GENERIC_ERROR = 00;
	
	/**
	 * 
	 */
	public static final byte FEATURE_NOT_SUPPORTED = 20;
	
	/**
	 * 
	 */
	public static final byte FEATURE_NOT_YET_IMPLEMENTED = 25;
	
	/**
	 * 
	 */
	public static final byte CLASS_CAST_ERROR = 40;
	
	/**
	 * 
	 */
	public static final byte NO_TYPE_HANDLER_FOUND = 60;
	
	private int errorCode = GENERIC_ERROR;

	/**
	 * @param msg
	 * @param cause
	 * @param error
	 */
	public R2MLException(String msg, Exception cause, int error) {
		super(msg, cause);
		errorCode = error;
	}
	
	/**
	 * @param msg
	 * @param cause
	 */
	public R2MLException(String msg, Exception cause) {
		super(msg, cause);
	}

	/**
	 * @param msg
	 * @param error
	 */
	public R2MLException(String msg, byte error) {
		super(msg);
		errorCode = error;
	}

	/**
	 * @param msg
	 */
	public R2MLException(String msg) {
		super(msg);
	}

	/**
	 * @return
	 */
	public int getErrorCode() {
		return errorCode;
	}
}
