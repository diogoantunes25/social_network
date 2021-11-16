package sonet.app.browser.main;

import sonet.core.AgentProxy;

/**
 * Menu builder for clients.
 */
public class Menu extends pt.tecnico.uilib.menus.Menu {

  /** @param receiver */
  public Menu(AgentProxy receiver) {
    super(Label.TITLE, //
        new Do_411_OpenMenuAgent(receiver) //
    );
  }

}
