package sonet.app.browser.publication;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import sonet.app.browser.exceptions.UnknownPublicationKeyException;
import sonet.app.browser.visitors.RenderDocumentDetailed;
import sonet.app.browser.visitors.RenderDocumentSummary;
import sonet.core.AgentProxy;
import sonet.core.exceptions.InaccessibleAgentException;
import sonet.core.exceptions.InaccessiblePublicationException;
import sonet.core.exceptions.UnknownPublicationException;

/**
 * Show publication.
 */
class Do_432_ShowPublication extends Command<AgentProxy> {

  /** @param receiver */
  public Do_432_ShowPublication(AgentProxy receiver) {
    super(MenuEntry.SHOW_PUBLICATION, receiver);
  }

  /** @see ist.po.ui.Command#execute() */
  @Override
  protected final void execute() throws CommandException {
    try {
      _receiver.ensureAgentAccessible();

      int pid = Form.requestInteger(Prompt.key());
      _receiver.ensurePublicationAccessible(pid);

      RenderDocumentSummary renderer = new RenderDocumentSummary(new RenderDocumentDetailed());
      _receiver.acceptPublicationVisitor(pid, renderer);
      _display.popup(renderer);
    } catch (UnknownPublicationException e) {
      throw new UnknownPublicationKeyException(e.getKey());
    } catch (InaccessibleAgentException e) {
      // EMPTY: fail silently
    } catch (InaccessiblePublicationException e) {
      // EMPTY: fail silently
    }
  }
  
}
