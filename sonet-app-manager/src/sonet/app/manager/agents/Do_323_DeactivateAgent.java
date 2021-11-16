package sonet.app.manager.agents;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import sonet.app.manager.exceptions.AgentIsInactiveException;
import sonet.app.manager.exceptions.UnknownClientKeyException;
import sonet.core.Network;
import sonet.core.exceptions.AgentAlreadyInactiveException;
import sonet.core.exceptions.UnknownAgentException;

/**
 * Command for changing a client's name.
 */
public class Do_323_DeactivateAgent extends Command<Network> {

  /** @param network */
  public Do_323_DeactivateAgent(Network network) {
    super(Label.DEACTIVATE_AGENT, network);
    addIntegerField("key", Prompt.key());
  }

  /** @see ist.po.ui.Command#execute() */
  @Override
  protected final void execute() throws CommandException {
    try {
      _receiver.deactivateAgent(integerField("key"));
    } catch (UnknownAgentException e) {
      throw new UnknownClientKeyException(e.getKey());
    } catch (AgentAlreadyInactiveException e) {
      throw new AgentIsInactiveException(e.getKey());
    }
  }
  
}
