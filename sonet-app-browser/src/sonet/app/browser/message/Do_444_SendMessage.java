package sonet.app.browser.message;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import sonet.app.browser.exceptions.UnspecifiedMessageDestinationException;
import sonet.core.AgentProxy;
import sonet.core.exceptions.RestrictedOperationException;

/**
 * Send message.
 */
class Do_444_SendMessage extends Command<AgentProxy> {

  /** @param receiver */
  public Do_444_SendMessage(AgentProxy receiver) {
    super(Label.SEND_MESSAGE, receiver);
  }

  /** @see ist.po.ui.Command#execute() */
  @Override
  protected final void execute() throws CommandException {
    try {
      _receiver.ensureAuthorized();

      String rcpts = Form.requestString(Prompt.agentKeys()).trim();
      if (rcpts.length() == 0)
        throw new UnspecifiedMessageDestinationException();

      String subject = Form.requestString(Prompt.subject());
      
      String text = "";
      String line = Form.requestString(Prompt.text());
      while (!line.equals(".")) {
        text += line + "\n";
        line = Form.requestString("");
      }
      text += line; // append last "."

      String attachments = Form.requestString(Prompt.keys()).trim();
      _receiver.sendMessage(rcpts, subject, text, attachments);
    } catch (RestrictedOperationException e) {
      // EMPTY: do nothing: must do this to respect output messages
    }
  }

}
