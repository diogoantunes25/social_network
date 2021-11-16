package sonet.app.browser.agent;

import pt.tecnico.uilib.menus.Command;
import sonet.app.browser.visitors.RenderAgents;
import sonet.core.AgentProxy;
import sonet.core.exceptions.InaccessibleAgentException;

/**
 * Show agent properties.
 */
class Do_421_ShowProfile extends Command<AgentProxy> {

  /** @param receiver */
  public Do_421_ShowProfile(AgentProxy receiver) {
    super(Label.SHOW_PROFILE, receiver);
  }

  /** @see ist.po.ui.Command#execute() */
  @Override
  protected final void execute() {
    try {
      _receiver.ensureAgentAccessible();
      RenderAgents renderer = new RenderAgents();
      _receiver.acceptAgentVisitor(renderer);
      _display.popup(renderer);
    } catch (InaccessibleAgentException e) {
      // EMPTY: fail silently
    }
  }

}
