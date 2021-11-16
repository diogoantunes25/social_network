package sonet.app.browser.message;

import pt.tecnico.uilib.menus.Command;
import sonet.app.browser.visitors.RenderDocumentSummary;
import sonet.core.AgentProxy;
import sonet.core.exceptions.RestrictedOperationException;

/**
 * Show sent messages.
 */
class Do_442_ShowSentMessages extends Command<AgentProxy> {

  /** @param receiver */
  public Do_442_ShowSentMessages(AgentProxy receiver) {
    super(false, Label.LIST_OUTBOX, receiver);
  }

  /** @see ist.po.ui.Command#execute() */
  @Override
  protected final void execute() {
    try {
      _receiver.ensureAuthorized();
      RenderDocumentSummary renderer = new RenderDocumentSummary();
      _receiver.acceptOutboxVisitor(renderer); // show all messages
      if (renderer.toString().length() != 0)
        _display.popup(renderer);
    } catch (RestrictedOperationException e) {
      // EMPTY: do nothing: must do this to respect output messages
    }
  }
  
}
