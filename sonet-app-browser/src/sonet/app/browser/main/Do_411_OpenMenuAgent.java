package sonet.app.browser.main;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import sonet.app.browser.agent.Menu;
import sonet.app.browser.exceptions.UnknownAgentKeyException;
import sonet.core.AgentProxy;
import sonet.core.exceptions.UnknownAgentException;

/**
 * Open agent menu.
 */
class Do_411_OpenMenuAgent extends Command<AgentProxy> {

  /** @param receiver */
  public Do_411_OpenMenuAgent(AgentProxy receiver) {
    super(Label.OPEN_MENU_AGENT, receiver);
  }

  /** @see ist.po.ui.Command#execute() */
  @Override
  protected final void execute() throws CommandException {
    try {
      _receiver.setVisited(Form.requestInteger(Prompt.agentKey()));
      // no access control in this case
      Menu menu = new Menu(_receiver);
      menu.open();
    } catch (UnknownAgentException e) {
      throw new UnknownAgentKeyException(e.getKey());
    }
  }

}
