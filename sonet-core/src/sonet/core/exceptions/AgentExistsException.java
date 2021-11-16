package sonet.core.exceptions;

/**
 * Given id has already been used.
 */
public class AgentExistsException extends NetworkException {
  
  /** Class serial number. */
  private static final long serialVersionUID = 201109020943L;

  /** The agent's key. */
  private final int _key;

  /** @param key */
  public AgentExistsException(int key) {
    _key = key;
  }

  /** @return the key */
  public int getKey() {
    return _key;
  }

}
