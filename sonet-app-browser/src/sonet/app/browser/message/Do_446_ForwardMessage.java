package sonet.app.browser.message;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import sonet.app.browser.exceptions.UnknownMessageKeyException;
import sonet.app.browser.exceptions.UnspecifiedMessageDestinationException;
import sonet.app.browser.visitors.RenderDocumentDetailed;
import sonet.app.browser.visitors.RenderDocumentSummary;
import sonet.core.AgentProxy;
import sonet.core.exceptions.RestrictedOperationException;
import sonet.core.exceptions.UnknownEmailMessageException;

/**
 * Forward message.
 */
class Do_446_ForwardMessage extends Command<AgentProxy> {

  /** @param receiver */
  public Do_446_ForwardMessage(AgentProxy receiver) {
    super(Label.FORWARD_MESSAGE, receiver);
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
      _display.popup(renderer);

      // FORWARD MESSAGE
      String rcpts = Form.requestString(Prompt.agentKeys()).trim();
      if (rcpts.length() == 0)
        throw new UnspecifiedMessageDestinationException();

      String text = "";
      String line = Form.requestString(Prompt.text());
      while (!line.equals(".")) {
        text += line + "\n";
        line = Form.requestString("");
      }
      text += line; // append last "."

      String attachments = Form.requestString(Prompt.keys()).trim();

      _receiver.forwardMessage(mid, rcpts, text, attachments);
    } catch (UnknownEmailMessageException e) {
      throw new UnknownMessageKeyException(e.getKey());
    } catch (RestrictedOperationException e) {
      // EMPTY: do nothing: must do this to respect output messages
    }
  }
}
