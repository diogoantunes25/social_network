package sonet.app.manager.search;

import pt.tecnico.uilib.menus.Command;
import sonet.app.manager.visitors.RenderDocumentSummary;
import sonet.core.Network;

/**
 * Show all messages.
 */
class Do_331_ShowAllMessages extends Command<Network> {
  
  /** @param receiver */
  public Do_331_ShowAllMessages(Network receiver) {
    super(Label.SHOW_ALL_MESSAGES, receiver);
  }

  /** @see ist.po.ui.Command#execute() */
  @Override
  protected final void execute() {
    RenderDocumentSummary renderer = new RenderDocumentSummary();
    _receiver.acceptAllMessagesVisitor(renderer);
    if (renderer.toString().length() != 0)
      _display.popup(renderer);
  }
  
}