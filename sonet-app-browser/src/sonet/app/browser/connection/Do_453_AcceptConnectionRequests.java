package sonet.app.browser.connection;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import sonet.core.AgentProxy;
import sonet.core.exceptions.RestrictedOperationException;
import sonet.core.exceptions.UnknownAgentException;

/**
 * Accept connection requests.
 */
class Do_453_AcceptConnectionRequests extends Command<AgentProxy> {

  /** @param receiver */
  public Do_453_AcceptConnectionRequests(AgentProxy receiver) {
    super(Label.APPROVE_CONNECTIONS, receiver);
  }

  /** @see ist.po.ui.Command#execute() */
  @Override
  protected final void execute() {
    try {
      _receiver.acceptConnections(Form.requestString(Prompt.agentKeys()));
    } catch (UnknownAgentException e) {
      // EMPTY: do nothing (as specified)
    } catch (RestrictedOperationException e) {
      // EMPTY: do nothing (as specified)
    }
  }

}
