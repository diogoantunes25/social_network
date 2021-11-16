package sonet.app.browser.exceptions;

import pt.tecnico.uilib.menus.CommandException;

/**
 * Launched when a unknown key is used.
 */
public class DuplicatePublicationKeyException extends CommandException {

  /** Serial number. */
  private static final long serialVersionUID = 201109020943L;

  /** @param key publication key */
  public DuplicatePublicationKeyException(int key) {
    super("A publicação '" + key + "' já existe.");
  }

}
