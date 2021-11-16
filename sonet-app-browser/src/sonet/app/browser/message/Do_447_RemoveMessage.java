package sonet.app.browser.message;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import sonet.app.browser.exceptions.UnknownMessageKeyException;
import sonet.app.browser.visitors.RenderDocumentDetailed;
import sonet.app.browser.visitors.RenderDocumentSummary;
import sonet.core.AgentProxy;
import sonet.core.exceptions.RestrictedOperationException;
import sonet.core.exceptions.UnknownEmailMessageException;

/**
 * Remove message.
 */
class Do_447_RemoveMessage extends Command<AgentProxy> {

  /** @param receiver */
  public Do_447_RemoveMessage(AgentProxy receiver) {
    super(Label.REMOVE_MESSAGE, receiver);
  }

  /** @see ist.po.ui.Command#execute() */
  @Override
  protected final void execute() throws CommandException {
    try {
      _receiver.ensureAuthorized();

      // SHOW MESSAGE
      int mid = Form.requestInteger(Prompt.key());
      RenderDocumentSummary renderer = new RenderDocumentSummary(new RenderDocumentDetailed());
      _receiver.acceptEmailMessageVisitor(mid, renderer);
      _display.popup(renderer.toString());

      // REMOVE MESSAGE
      if (Form.confirm(Prompt.removeMessage())) {
        _receiver.removeMessage(mid);
        _display.popup(Message.messageRemoved(mid));
      }
    } catch (UnknownEmailMessageException e) {
      throw new UnknownMessageKeyException(e.getKey());
    } catch (RestrictedOperationException e) {
      // EMPTY: do nothing: must do this to respect output messages
    }
  }

}
