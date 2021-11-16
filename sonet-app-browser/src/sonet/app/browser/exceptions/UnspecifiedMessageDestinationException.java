package sonet.app.browser.exceptions;

import pt.tecnico.uilib.menus.CommandException;

/**
 * Launched when a unknown key is used.
 */
public class UnspecifiedMessageDestinationException extends CommandException {

  /** Serial number. */
  private static final long serialVersionUID = 201109020943L;

  /** Default. */
  public UnspecifiedMessageDestinationException() {
    super("Nenhum destino especificado.");
  }

}
