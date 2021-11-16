package sonet.app.browser.publication;

import java.util.Arrays;
import java.util.List;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import sonet.app.browser.exceptions.DuplicatePublicationKeyException;
import sonet.core.AgentProxy;
import sonet.core.exceptions.PublicationExistsException;
import sonet.core.exceptions.RestrictedOperationException;
import sonet.core.exceptions.UnknownAgentException;
import sonet.core.exceptions.UnknownDataException;

/**
 * Register publication.
 */
class Do_433_RegisterPublication extends Command<AgentProxy> {

  /** @param receiver */
  public Do_433_RegisterPublication(AgentProxy receiver) {
    super(MenuEntry.REGISTER_PUBLICATION, receiver);
  }

  /** @see ist.po.ui.Command#execute() */
  @Override
  protected final void execute() throws CommandException {
    try {
      _receiver.ensureAuthorized();
      final String tNOTE = Message.typeNote();
      final String tURI = Message.typeURI();
      final String tIMAGE = Message.typeImage();
      List<String> validTypes = Arrays.asList(tIMAGE, tNOTE, tURI);
      String type = "";
      do {
        type = Form.requestString(Prompt.type());
      } while (!validTypes.contains(type));
      String legend = Form.requestString(Prompt.title());

      String vid = Integer.toString(_receiver.getVisited().getId());
      // null means get a new UUID
      // fields 0 - type; 1 - id/null; 2 - agent; other - pub-specific
      if (type.equals(tNOTE)) {
        String text = Form.requestString(Prompt.noteText());
        _receiver.registerPublication(type, null, vid, legend, text);
      } else if (type.equals(tIMAGE)) {
        String img = Form.requestString(Prompt.imageContents());
        _receiver.registerPublication(type, null, vid, legend, img);
      } else if (type.equals(tURI)) {
        String uri = Form.requestString(Prompt.uri());
        _receiver.registerPublication(type, null, vid, legend, uri);
      } else {
        // CANNOT HAPPEN!
      }
    } catch (PublicationExistsException e) {
      throw new DuplicatePublicationKeyException(e.getKey());
    } catch (UnknownAgentException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (UnknownDataException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (RestrictedOperationException e) {
      // EMPTY: do nothing: must do this to respect output messages
    }
  }
  
}
