package sonet.core.exceptions;

/**
 * Launched when a given id has already been used.
 */
public class UnknownAttachmentException extends NetworkException {
	/** Class serial number. */
	private static final long serialVersionUID = 201109020943L;

	/** The message's key. */
	private final int _key;

	/** @param key */
	public UnknownAttachmentException(int key) {
		_key = key;
	}

	/** @return the key */
	public int getKey() {
		return _key;
	}
	
}
