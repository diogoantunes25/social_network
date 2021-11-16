package sonet.app.browser.exceptions;

import pt.tecnico.uilib.menus.CommandException;

/**
 * Launched when a unknown key is used.
 */
public class RatingDeniedException extends CommandException {

  /** Serial number. */
  private static final long serialVersionUID = 201109020943L;

  /**
   * @param agtKey agent key
   * @param pubKey publication key
   */
  public RatingDeniedException(int agtKey, int pubKey) {
    super("O agente '" + agtKey + "' não pode pontuar a publicação '" + pubKey + "'.");
  }

}
