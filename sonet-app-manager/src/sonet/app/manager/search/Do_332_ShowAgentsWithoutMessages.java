package sonet.app.manager.search;

import pt.tecnico.uilib.menus.Command;
import sonet.app.manager.visitors.RenderAgents;
import sonet.core.Network;
import sonet.core.agent.Agent;
import sonet.core.visits.Selector;

/**
 * Show agents without messages.
 */
class Do_332_ShowAgentsWithoutMessages extends Command<Network> {

  /** @param receiver */
  public Do_332_ShowAgentsWithoutMessages(Network receiver) {
    super(Label.SHOW_AGENTS_WITHOUT_MESSAGES, receiver);
  }

  /** @see ist.po.ui.Command#execute() */
  @Override
  protected final void execute() {
    RenderAgents renderer = new RenderAgents();
    Selector<Agent> selector = new Selector<>() {
      @Override
      public boolean ok(Agent agent) {
        return agent.getNumberOfReceivedMessages() + agent.getNumberOfSentMessages() == 0;
      }
    };
    _receiver.accept(selector, renderer);
    if (renderer.toString().length() != 0)
      _display.popup(renderer);
  }

}