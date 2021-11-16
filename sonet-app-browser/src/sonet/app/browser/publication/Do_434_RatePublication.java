package sonet.app.browser.publication;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import sonet.app.browser.exceptions.RatingDeniedException;
import sonet.app.browser.exceptions.UnknownPublicationKeyException;
import sonet.core.AgentProxy;
import sonet.core.exceptions.PublicationRatingException;
import sonet.core.exceptions.UnknownPublicationException;

/**
 * Rate publication.
 */
class Do_434_RatePublication extends Command<AgentProxy> {

  /** @param receiver */
  public Do_434_RatePublication(AgentProxy receiver) {
    super(MenuEntry.RATE_PUBLICATION, receiver);
  }

  /** @see ist.po.ui.Command#execute() */
  @Override
  protected final void execute() throws CommandException {
    try {
      // _receiver.ensureAgentAccessible();

      int pid = Form.requestInteger(Prompt.key());
      int rating = Form.requestInteger(Prompt.rating());

      _receiver.ratePublication(pid, rating);

    } catch (UnknownPublicationException e) {
      throw new UnknownPublicationKeyException(e.getKey());
    } catch (PublicationRatingException e) {
      throw new RatingDeniedException(e.getAgentKey(), e.getPublicationKey());
    }
  }
  
}
