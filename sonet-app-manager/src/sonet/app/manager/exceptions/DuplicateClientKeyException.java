package sonet.app.manager.exceptions;

import pt.tecnico.uilib.menus.CommandException;

/**
 * Launched when a duplicate key is used.
 */
public class DuplicateClientKeyException extends CommandException {

  /** Serial number. */
  private static final long serialVersionUID = 201109020943L;

  /** @param key the key */
  public DuplicateClientKeyException(int key) {
    super("O agente '" + key + "' já existe.");
  }

}
