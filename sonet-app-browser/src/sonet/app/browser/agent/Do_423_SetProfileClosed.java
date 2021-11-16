package sonet.app.browser.agent;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import sonet.app.browser.exceptions.UnknownAgentKeyException;
import sonet.core.AgentProxy;
import sonet.core.agent.ProfileAccess;
import sonet.core.exceptions.RestrictedOperationException;
import sonet.core.exceptions.UnknownAgentException;

/**
 * Close agent profile.
 */
class Do_423_SetProfileClosed extends Command<AgentProxy> {

  /** @param receiver */
  public Do_423_SetProfileClosed(AgentProxy receiver) {
    super(Label.PROTECT_PROFILE, receiver);
  }

  /** @see ist.po.ui.Command#execute() */
  @Override
  protected final void execute() throws CommandException {
    try {
      _receiver.setAgentProfileProtection(Form.requestString(Prompt.keys()), ProfileAccess.CLOSED);
    } catch (UnknownAgentException e) {
      throw new UnknownAgentKeyException(e.getKey());
    } catch (RestrictedOperationException e) {
      // EMPTY: do nothing: must do this to respect output messages
    }
  }

}
