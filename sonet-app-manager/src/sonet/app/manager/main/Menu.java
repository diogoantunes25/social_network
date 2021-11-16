package sonet.app.manager.main;

import pt.tecnico.uilib.menus.DoOpenMenu;
import sonet.core.NetworkManager;

/**
 * Menu builder for managers.
 */
public class Menu extends pt.tecnico.uilib.menus.Menu {

  /** @param receiver */
  public Menu(NetworkManager receiver) {
    super(Label.TITLE, //
        new Do_311_New(receiver), //
        new Do_312_Open(receiver), //
        new Do_313_Save(receiver), //
        new Do_314_SaveAs(receiver), //
        new DoOpenMenu(Label.MENU_AGENTS, new sonet.app.manager.agents.Menu(receiver.getNetwork())), //
        new DoOpenMenu(Label.MENU_SEARCH, new sonet.app.manager.search.Menu(receiver.getNetwork())) //
    );
  }

}
