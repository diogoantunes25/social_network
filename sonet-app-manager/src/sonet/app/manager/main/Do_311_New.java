package sonet.app.manager.main;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import sonet.core.NetworkManager;

/**
 * 
 */
public class Do_311_New extends Command<NetworkManager> {

  /** @param manager */
  public Do_311_New(NetworkManager manager) {
    super(false, Label.NEW, manager);
  }

  /** @see ist.po.ui.Command#execute() */
  @Override
  protected final void execute() {
    if (_receiver.changed() && Form.confirm(Message.saveBeforeExit())) {
      Do_313_Save cmd = new Do_313_Save(_receiver);
      cmd.execute();
    }
    _receiver.reset();
  }

}
