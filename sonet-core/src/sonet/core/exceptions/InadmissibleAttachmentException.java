package sonet.core.exceptions;

/**
 * Launched when a given id has already been used.
 */
public class InadmissibleAttachmentException extends NetworkException {
  
	/** Class serial number. */
	private static final long serialVersionUID = 201109020943L;

	/** The message's key. */
	private final String _key;

	/** @param key */
	public InadmissibleAttachmentException(String key) {
		_key = key;
	}

	/** @return the key */
	public String getKey() {
		return _key;
	}
	
}
