package sonet.app.manager.main;

import java.io.FileNotFoundException;
import java.io.IOException;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import sonet.core.NetworkManager;

/**
 * Open a serialization file.
 */
public class Do_312_Open extends Command<NetworkManager> {

  /** @param manager */
  public Do_312_Open(NetworkManager manager) {
    super(Label.OPEN, manager);
  }

  /** @see ist.po.ui.Command#execute() */
  @Override
  protected final void execute() {
    try {
      if (_receiver.changed() && Form.confirm(Message.saveBeforeExit())) {
        Do_313_Save cmd = new Do_313_Save(_receiver);
        cmd.execute();
      }
      _receiver.load(Form.requestString(Message.openFile()));
    } catch (FileNotFoundException e) {
      // DAVID FIXME TODO
      _display.popup(Message.fileNotFound());
    } catch (ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

}
