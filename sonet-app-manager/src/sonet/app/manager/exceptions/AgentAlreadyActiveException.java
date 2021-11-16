package sonet.app.manager.exceptions;

import pt.tecnico.uilib.menus.CommandException;

/**
 * Launched when a unknown key is used.
 */
public class AgentAlreadyActiveException extends CommandException {

  /** Class serial number (serialization). */
  private static final long serialVersionUID = 201109020943L;

  /** @param key */
  public AgentAlreadyActiveException(String key) {
    super("Agent '" + key + "' já está activo.");
  }

}
