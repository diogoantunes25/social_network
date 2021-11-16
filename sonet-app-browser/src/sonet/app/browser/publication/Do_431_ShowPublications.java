package sonet.app.browser.publication;

import pt.tecnico.uilib.menus.Command;
import sonet.app.browser.visitors.RenderDocumentSummary;
import sonet.core.AgentProxy;
import sonet.core.exceptions.InaccessibleAgentException;

/**
 * Show publications.
 */
class Do_431_ShowPublications extends Command<AgentProxy> {

  /** @param receiver */
  public Do_431_ShowPublications(AgentProxy receiver) {
    super(MenuEntry.SHOW_PUBLICATIONS, receiver);
  }

  /** @see ist.po.ui.Command#execute() */
  @Override
  protected final void execute() {
    try {
      _receiver.ensureAgentAccessible();
      // FIXME: should also check publications permissions
      RenderDocumentSummary renderer = new RenderDocumentSummary();
      _receiver.acceptPublicationsVisitor(renderer); // show all
      if (renderer.toString().length() != 0)
        _display.popup(renderer);
    } catch (InaccessibleAgentException e) {
      // EMPTY: fail silently
    }
  }

}
