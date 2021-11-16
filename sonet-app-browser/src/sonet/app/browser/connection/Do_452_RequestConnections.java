package sonet.app.browser.connection;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import sonet.core.AgentProxy;
import sonet.core.exceptions.RestrictedOperationException;

/**
 * Request connections.
 */
class Do_452_RequestConnections extends Command<AgentProxy> {

  /** @param receiver */
  public Do_452_RequestConnections(AgentProxy receiver) {
    super(Label.REQUEST_CONNECTIONS, receiver);
  }

  /** @see ist.po.ui.Command#execute() */
  @Override
  protected final void execute() throws CommandException {
    try {
      _receiver.requestConnections(Form.requestString(Prompt.agentKeys()));
    } catch (RestrictedOperationException e) {
      // EMPTY: do nothing: must do this to respect output messages
    }
  }
  
}
