package sonet.app.manager.agents;

import pt.tecnico.uilib.menus.Command;
import sonet.app.manager.visitors.RenderAgents;
import sonet.core.Network;

/**
 * Command for listing all agents.
 */
public class Do_321_ShowAllAgents extends Command<Network> {

  /** @param network */
  public Do_321_ShowAllAgents(Network network) {
    super(Label.SHOW_ALL_AGENTS, network);
  }

  /** @see ist.po.ui.Command#execute() */
  @Override
  protected final void execute() {
    RenderAgents renderer = new RenderAgents();
    _receiver.accept(renderer);
    if (renderer.toString().length() != 0)
      _display.popup(renderer);
  }
  
}