package sonet.core.exceptions;

/**
 * Launched when trying to perform a restricted operation.
 */
public class RestrictedOperationException extends NetworkException {
	/**
	 * Class serial number (serialization).
	 */
	private static final long serialVersionUID = 201109020943L;

	/** Login agent. */
	private int _login = 0;

	/** Visited agent. */
	private int _visited = 0;

	/**
	 * @param login 
	 * @param visited 
	 */
	public RestrictedOperationException(int login, int visited) {
		_login = login;
		_visited = visited;
	}

	/**
	 * @return login
	 */
	public int getLogin() {
		return _login;
	}

	/**
	 * @return visited
	 */
	public int getVisited() {
		return _visited;
	}
	
}
