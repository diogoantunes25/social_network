package sonet.app.browser.publication;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import sonet.app.browser.exceptions.UnknownAgentKeyException;
import sonet.app.browser.exceptions.UnknownPublicationKeyException;
import sonet.core.AgentProxy;
import sonet.core.exceptions.RestrictedOperationException;
import sonet.core.exceptions.UnknownAgentException;
import sonet.core.exceptions.UnknownPublicationException;
import sonet.core.publication.PublicationAccess;

/**
 * Close access to publication.
 */
class Do_436_CloseAccessToPublication extends Command<AgentProxy> {

  /** @param receiver */
  public Do_436_CloseAccessToPublication(AgentProxy receiver) {
    super(MenuEntry.PROTECT_PUBLICATION, receiver);
  }

  /** @see ist.po.ui.Command#execute() */
  @Override
  protected final void execute() throws CommandException {
    try {
      _receiver.ensureAuthorized();
      final int pid = Form.requestInteger(Prompt.key());
      _receiver.assertPublicationExists(pid); // has to be checked here
      String keys = Form.requestString(Prompt.agentKeys());
      _receiver.setPublicationProtection(pid, keys, PublicationAccess.DISABLED);
    } catch (UnknownAgentException e) {
      throw new UnknownAgentKeyException(e.getKey());
    } catch (UnknownPublicationException e) {
      throw new UnknownPublicationKeyException(e.getKey());
    } catch (RestrictedOperationException e) {
      // EMPTY: do nothing: must do this to respect output messages
    }
  }
  
}
