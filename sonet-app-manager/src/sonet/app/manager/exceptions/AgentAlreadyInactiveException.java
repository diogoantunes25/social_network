package sonet.app.manager.exceptions;

import pt.tecnico.uilib.menus.CommandException;

/**
 * Launched when a unknown key is used.
 */
public class AgentAlreadyInactiveException extends CommandException {

  /** Class serial number (serialization). */
  private static final long serialVersionUID = 201109020943L;

  /** @param key the key */
  public AgentAlreadyInactiveException(String key) {
    super("Agente '" + key + "' já está inactivo.");
  }

}
