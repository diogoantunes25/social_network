package sonet.app.manager.search;

import sonet.core.Network;

/**
 * Menu for network searching.
 */
public class Menu extends pt.tecnico.uilib.menus.Menu {

  /** @param receiver */
  public Menu(Network receiver) {
    super(Label.TITLE, //
        new Do_331_ShowAllMessages(receiver), //
        new Do_332_ShowAgentsWithoutMessages(receiver) //
    );
  }

}
