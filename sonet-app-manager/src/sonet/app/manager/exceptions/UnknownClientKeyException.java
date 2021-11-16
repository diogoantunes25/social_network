package sonet.app.manager.exceptions;

import pt.tecnico.uilib.menus.CommandException;

/**
 * Launched when a unknown key is used.
 */
public class UnknownClientKeyException extends CommandException {

  /** Serial number. */
  private static final long serialVersionUID = 201109020943L;

  /** @param key agent key */
  public UnknownClientKeyException(int key) {
    super("O agente '" + key + "' não existe.");
  }

}
