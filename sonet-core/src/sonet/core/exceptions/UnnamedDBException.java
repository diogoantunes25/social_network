package sonet.core.exceptions;

/**
 * 
 */
public class UnnamedDBException extends NetworkException {

	/** Serial number. */
	private static final long serialVersionUID = 201109232014L;

	/** @see java.lang.Throwable#getMessage() */
	@Override
	public String getMessage() {
		return "UNOFFICIAL: CURRENT DB HAS NO NAME.";
	}

}
