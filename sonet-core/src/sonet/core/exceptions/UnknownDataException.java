package sonet.core.exceptions;

/**
 * Launched when reading unknown data from the input.
 */
public class UnknownDataException extends NetworkException {
  
  /** Class serial number (serialization). */
  private static final long serialVersionUID = 201109020943L;

  /** @param unknownData the unknown data */
  public UnknownDataException(String unknownData) {
    super(unknownData);
  }

}
