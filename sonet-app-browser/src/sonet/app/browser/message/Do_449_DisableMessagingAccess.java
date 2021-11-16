package sonet.app.browser.message;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import sonet.app.browser.exceptions.UnknownMessageKeyException;
import sonet.core.AgentProxy;
import sonet.core.exceptions.RestrictedOperationException;
import sonet.core.exceptions.UnknownAgentException;
import sonet.core.message.MessagingAccess;

/**
 * Disable messages.
 */
class Do_449_DisableMessagingAccess extends Command<AgentProxy> {

  /** @param receiver */
  public Do_449_DisableMessagingAccess(AgentProxy receiver) {
    super(false, Label.DISABLE_MESSAGING_ACCESS, receiver);
  }

  /** @see ist.po.ui.Command#execute() */
  @Override
  protected final void execute() throws CommandException {
    try {
      _receiver.setMessagingProtection(Form.requestString(Prompt.agentKeys()), MessagingAccess.DISABLED);
    } catch (UnknownAgentException e) {
      throw new UnknownMessageKeyException(e.getKey());
    } catch (RestrictedOperationException e) {
      // EMPTY: do nothing: must do this to respect output messages
    }
  }

}
