package sonet.app.browser.exceptions;

import pt.tecnico.uilib.menus.CommandException;

/**
 * Launched when a unknown key is used.
 */
public class UnknownAgentKeyException extends CommandException {

  /** Serial number. */
  private static final long serialVersionUID = 201109020943L;

  /** @param key agent key */
  public UnknownAgentKeyException(int key) {
    super("O agente '" + key + "' não existe.");
  }

}
