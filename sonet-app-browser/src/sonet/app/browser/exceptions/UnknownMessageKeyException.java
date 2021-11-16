package sonet.app.browser.exceptions;

import pt.tecnico.uilib.menus.CommandException;

/**
 * Launched when a unknown key is used.
 */
public class UnknownMessageKeyException extends CommandException {

  /** Serial number. */
  private static final long serialVersionUID = 201109020943L;

  /** @param key message key */
  public UnknownMessageKeyException(int key) {
    super("A mensagem '" + key + "' não existe.");
  }

}
