package sonet.app.browser.message;

import pt.tecnico.uilib.menus.Command;
import sonet.app.browser.visitors.RenderDocumentSummary;
import sonet.core.AgentProxy;
import sonet.core.exceptions.RestrictedOperationException;

/**
 * Show received messages.
 */
class Do_441_ShowReceivedMessages extends Command<AgentProxy> {

  /** @param receiver */
  public Do_441_ShowReceivedMessages(AgentProxy receiver) {
    super(Label.LIST_INBOX, receiver);
  }

  /** @see ist.po.ui.Command#execute() */
  @Override
  protected final void execute() {
    try {
      _receiver.ensureAuthorized();
      RenderDocumentSummary renderer = new RenderDocumentSummary();
      _receiver.acceptInboxVisitor(renderer); // show all messages
      if (renderer.toString().length() != 0)
        _display.popup(renderer);
    } catch (RestrictedOperationException e) {
      // EMPTY: do nothing: must do this to respect output messages
    }
  }
  
}
