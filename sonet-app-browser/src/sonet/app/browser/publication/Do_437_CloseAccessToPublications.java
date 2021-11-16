package sonet.app.browser.publication;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import sonet.app.browser.exceptions.UnknownAgentKeyException;
import sonet.core.AgentProxy;
import sonet.core.exceptions.RestrictedOperationException;
import sonet.core.exceptions.UnknownAgentException;
import sonet.core.publication.PublicationAccess;

/**
 * Close access to publications.
 */
class Do_437_CloseAccessToPublications extends Command<AgentProxy> {

  /** @param receiver */
  public Do_437_CloseAccessToPublications(AgentProxy receiver) {
    super(MenuEntry.PROTECT_PUBLICATIONS, receiver);
  }

  /** @see ist.po.ui.Command#execute() */
  @Override
  protected final void execute() throws CommandException {
    try {
      _receiver.setPublicationsProtection(Form.requestString(Prompt.agentKeys()), PublicationAccess.DISABLED);
    } catch (UnknownAgentException e) {
      throw new UnknownAgentKeyException(e.getKey());
    } catch (RestrictedOperationException e) {
      // EMPTY: do nothing: must do this to respect output messages
    }
  }
  
}
