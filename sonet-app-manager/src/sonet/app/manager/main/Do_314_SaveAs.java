package sonet.app.manager.main;

import java.io.IOException;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import sonet.core.NetworkManager;
import sonet.core.exceptions.UnnamedDBException;

/**
 * Save a serialization file with a new name.
 */
public class Do_314_SaveAs extends Command<NetworkManager> {

  /** @param manager */
  public Do_314_SaveAs(NetworkManager manager) {
    super(Label.SAVE_AS, manager);
  }

  /** @see ist.po.ui.Command#execute() */
  @Override
  protected final void execute() {
    try {
      _receiver.saveAs(Form.requestString(Message.saveAs()));
    } catch (UnnamedDBException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

}
