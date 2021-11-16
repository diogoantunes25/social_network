package sonet.core.exceptions;

/**
 * Launched when a unknown key is used.
 */
public class UnknownPublicationException extends NetworkException {
	/** Class serial number. */
	private static final long serialVersionUID = 201109020943L;

	/** The publication's key. */
	private final int _key;

	/** @param key */
	public UnknownPublicationException(int key) {
		_key = key;
	}

	/** @return the key */
	public int getKey() {
		return _key;
	}
	
}
