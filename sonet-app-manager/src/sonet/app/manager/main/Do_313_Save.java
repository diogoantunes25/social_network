package sonet.app.manager.main;

import java.io.FileNotFoundException;
import java.io.IOException;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import sonet.core.NetworkManager;
import sonet.core.exceptions.UnnamedDBException;

/**
 * Save a serialization file.
 */
public class Do_313_Save extends Command<NetworkManager> {

  /** @param manager */
  public Do_313_Save(NetworkManager manager) {
    super(false, Label.SAVE, manager);
  }

  /** @see ist.po.ui.Command#execute() */
  @Override
  protected final void execute() {
    try {
      _receiver.save();
    } catch (UnnamedDBException e) {
      try {
        _receiver.saveAs(Form.requestString(Message.newSaveAs()));
      } catch (UnnamedDBException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      } catch (IOException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
}
