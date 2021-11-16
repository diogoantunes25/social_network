package sonet.app.browser.agent;

import pt.tecnico.uilib.menus.Command;
import sonet.app.browser.message.Menu;
import sonet.core.AgentProxy;
import sonet.core.exceptions.RestrictedOperationException;

/**
 * Open agent's messages menu.
 */
class Do_426_OpenMenuMessages extends Command<AgentProxy> {

  /** @param receiver */
  public Do_426_OpenMenuMessages(AgentProxy receiver) {
    super(Label.OPEN_MENU_MESSAGES, receiver);
  }

  /** @see ist.po.ui.Command#execute() */
  @Override
  protected final void execute() {
    try {
      _receiver.ensureAuthorized();
      Menu menu = new Menu(_receiver);
      menu.open();
    } catch (RestrictedOperationException e) {
      // EMPTY: do nothing: must do this to respect output messages
    }
  }
  
}
