package sonet.core.exceptions;

/**
 * Launched when trying to perform a protected operation.
 */
public class InaccessiblePublicationException extends NetworkException {
  
	/** Class serial number (serialization). */
	private static final long serialVersionUID = 201109020943L;

	/** Login agent. */
	private int _login = 0;

	/** Visited agent. */
	private int _publication = 0;

	/**
	 * @param login
	 * @param publication
	 */
	public InaccessiblePublicationException(int login, int publication) {
		_login = login;
		_publication = publication;
	}

	/** @return agent key */
	public int getAgentKey() {
		return _login;
	}

	/** @return publication key */
	public int getPublicationKey() {
		return _publication;
	}

}
