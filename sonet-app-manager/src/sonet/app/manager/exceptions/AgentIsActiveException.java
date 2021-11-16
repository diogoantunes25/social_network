package sonet.app.manager.exceptions;

import pt.tecnico.uilib.menus.CommandException;

/**
 * Launched when a unknown key is used.
 */
public class AgentIsActiveException extends CommandException {

  /** Serial number. */
  private static final long serialVersionUID = 201109020943L;

  /** @param key agent key */
  public AgentIsActiveException(int key) {
    super("O agente '" + key + "' já está activo.");
  }

}
