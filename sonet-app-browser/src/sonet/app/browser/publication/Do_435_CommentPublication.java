package sonet.app.browser.publication;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import sonet.app.browser.exceptions.CommentDeniedException;
import sonet.app.browser.exceptions.UnknownPublicationKeyException;
import sonet.core.AgentProxy;
import sonet.core.exceptions.InaccessibleAgentException;
import sonet.core.exceptions.PublicationCommentException;
import sonet.core.exceptions.UnknownPublicationException;

/**
 * Comment publication.
 */
class Do_435_CommentPublication extends Command<AgentProxy> {

  /** @param receiver */
  public Do_435_CommentPublication(AgentProxy receiver) {
    super(MenuEntry.COMMENT_PUBLICATION, receiver);
  }

  /** @see ist.po.ui.Command#execute() */
  @Override
  protected final void execute() throws CommandException {
    try {
      _receiver.ensureAgentAccessible();
      int pid = Form.requestInteger(Prompt.key());
      String comment = Form.requestString(Prompt.comment());
      _receiver.commentPublication(pid, comment);
    } catch (UnknownPublicationException e) {
      throw new UnknownPublicationKeyException(e.getKey());
    } catch (PublicationCommentException e) {
      throw new CommentDeniedException(e.getAgentKey(), e.getPublicationKey());
    } catch (InaccessibleAgentException e) {
      // EMPTY: fail silently
    }
  }
  
}
