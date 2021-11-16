package sonet.app.manager.agents;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import sonet.app.manager.exceptions.DuplicateClientKeyException;
import sonet.core.Network;
import sonet.core.exceptions.AgentExistsException;
import sonet.core.exceptions.UnknownDataException;

/**
 * Command for registering a new agent.
 */
public class Do_322_RegisterAgent extends Command<Network> {

  /** @param network */
  public Do_322_RegisterAgent(Network network) {
    super(Label.REGISTER_AGENT, network);
  }

  /** @see ist.po.ui.Command#execute() */
  @Override
  protected final void execute() throws CommandException {
    try {
      String type;
      do {
        type = Form.requestString(Prompt.type());
      } while (!type.equals(Message.typePerson()) && !type.equals(Message.typeOrganization()));

      Form request = new Form();
      request.addStringField("name", Prompt.name());
      request.addStringField("email", Prompt.email());
      request.addStringField("phone", Prompt.phone());
      request.parse();
      // null means get a new UUID
      _receiver.registerAgent(new String[] { //
          type, null, request.stringField("name"), //
          request.stringField("email"), request.stringField("phone") //
      });
    } catch (AgentExistsException e) {
      throw new DuplicateClientKeyException(e.getKey());
    } catch (UnknownDataException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
}
