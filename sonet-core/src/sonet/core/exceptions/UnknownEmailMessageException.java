package sonet.core.exceptions;

/**
 * Launched when an unknown identifier is used.
 */
public class UnknownEmailMessageException extends NetworkException {
  
	/** Class serial number. */
	private static final long serialVersionUID = 201109020943L;

	/** The message's key. */
	private final int _key;

	/** @param key */
	public UnknownEmailMessageException(int key) {
		_key = key;
	}

	/** @return the key */
	public int getKey() {
		return _key;
	}
	
}
