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
 * Reply to message.
 */
class Do_445_ReplyToMessage extends Command<AgentProxy> {

  /** @param receiver */
  public Do_445_ReplyToMessage(AgentProxy receiver) {
    super(Label.REPLY_TO_MESSAGE, receiver);
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

      // REPLY TO MESSAGE
      String text = "";
      String line = Form.requestString(Prompt.text());
      while (!line.equals(".")) {
        text += line + "\n";
        line = Form.requestString("");
      }
      text += line; // append last "."

      String attachments = Form.requestString(Prompt.keys()).trim();

      _receiver.replyToMessage(mid, text, attachments);

    } catch (UnknownEmailMessageException e) {
      throw new UnknownMessageKeyException(e.getKey());
    } catch (RestrictedOperationException e) {
      // EMPTY: do nothing: must do this to respect output messages
    }
  }

}
