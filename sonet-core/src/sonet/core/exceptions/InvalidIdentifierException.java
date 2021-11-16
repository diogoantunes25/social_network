package sonet.core.exceptions;

/**
 * Launched when a unknown key is used.
 */
public class InvalidIdentifierException extends NetworkException {
  
  /** Class serial number. */
  private static final long serialVersionUID = 201109020943L;

  /** The agent's key. */
  private final int _key;

  /** @param key */
  public InvalidIdentifierException(int key) {
    _key = key;
  }

  /** @return the key */
  public int getKey() {
    return _key;
  }
  
}
