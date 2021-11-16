package sonet.app.browser.connection;

import pt.tecnico.uilib.menus.Command;
import sonet.app.browser.visitors.RenderConnections;
import sonet.core.AgentProxy;
import sonet.core.exceptions.InaccessibleAgentException;

/**
 * Show current connections.
 */
class Do_451_ShowCurrentConnections extends Command<AgentProxy> {

  /** @param receiver */
  public Do_451_ShowCurrentConnections(AgentProxy receiver) {
    super(Label.SHOW_CURRENT_CONNECTIONS, receiver);
  }

  /** @see ist.po.ui.Command#execute() */
  @Override
  protected final void execute() {
    try {
      _receiver.ensureAgentAccessible();
      RenderConnections renderer = new RenderConnections();
      _receiver.acceptConnectionsVisitor(renderer); // show all
      String output = renderer.toString().trim();
      if (!output.isEmpty())
        _display.popup(output);
    } catch (InaccessibleAgentException e) {
      // EMPTY: fail silently
    }
  }

}
