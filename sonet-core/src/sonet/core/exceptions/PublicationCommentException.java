package sonet.core.exceptions;

/**
 * Launched when a given agent id tries to rate a protected publication.
 */
public class PublicationCommentException extends NetworkException {
  
	/** Class serial number. */
	private static final long serialVersionUID = 201109020943L;

	/** The publication's key. */
	private final int _pkey;

	/** The agent's key. */
	private final int _akey;

	/**
	 * @param pkey
	 * @param akey 
	 */
	public PublicationCommentException(int pkey, int akey) {
		_pkey = pkey;
		_akey = akey;
	}

	/**
	 * @return the key
	 */
	public int getPublicationKey() {
		return _pkey;
	}
	
	/**
	 * @return the key
	 */
	public int getAgentKey() {
		return _akey;
	}
	
}
