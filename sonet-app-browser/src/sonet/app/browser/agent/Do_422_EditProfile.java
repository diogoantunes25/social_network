package sonet.app.browser.agent;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import sonet.core.AgentProxy;
import sonet.core.exceptions.RestrictedOperationException;

/**
 * Edit agent profile.
 */
class Do_422_EditProfile extends Command<AgentProxy> {

  /** @param receiver */
  public Do_422_EditProfile(AgentProxy receiver) {
    super(Label.EDIT_PROFILE, receiver);
  }

  /** @see ist.po.ui.Command#execute() */
  @Override
  protected final void execute() {
    try {
      _receiver.ensureAuthorized();
      Form req = new Form();
      req.addStringField("name", Prompt.name());
      req.addStringField("email", Prompt.email());
      req.addStringField("phone", Prompt.phone());
      req.parse();
      _receiver.setAgentProperties(req.stringField("name"), req.stringField("email"), req.stringField("phone"));
    } catch (RestrictedOperationException e) {
      // EMPTY: do nothing: must do this to respect output messages
    }
  }

}
