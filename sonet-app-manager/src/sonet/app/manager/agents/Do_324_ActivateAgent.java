package sonet.app.manager.agents;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import sonet.app.manager.exceptions.AgentIsActiveException;
import sonet.app.manager.exceptions.UnknownClientKeyException;
import sonet.core.Network;
import sonet.core.exceptions.AgentAlreadyActiveException;
import sonet.core.exceptions.UnknownAgentException;

/**
 * Command for activating an agent.
 */
public class Do_324_ActivateAgent extends Command<Network> {

  /** @param network */
  public Do_324_ActivateAgent(Network network) {
    super(false, Label.ACTIVATE_AGENT, network);
    addIntegerField("key", Prompt.key());
  }

  /** @see ist.po.ui.Command#execute() */
  @Override
  protected final void execute() throws CommandException {
    try {
      _receiver.activateAgent(integerField("key"));
    } catch (UnknownAgentException e) {
      throw new UnknownClientKeyException(e.getKey());
    } catch (AgentAlreadyActiveException e) {
      throw new AgentIsActiveException(e.getKey());
    }
  }
  
}
