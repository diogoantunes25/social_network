package sonet.app.manager;

import pt.tecnico.uilib.Dialog;
import sonet.app.manager.main.Menu;
import sonet.core.NetworkManager;
import sonet.core.exceptions.ImportFileException;

/**
 * Management application.
 */
public class App {

  /** @param args */
  public static void main(String[] args) {
    try (var ui = Dialog.UI) {
      NetworkManager manager = new NetworkManager();
      String filename = System.getProperty("import");
      if (filename != null)
        manager.importFile(filename);

      Menu menu = new Menu(manager);
      menu.open();
    } catch (ImportFileException e) {
      //DAVID should not happen!
      e.printStackTrace();
    }
  }

}
