package sonet.app.browser.connection;

import sonet.core.AgentProxy;

/**
 * Menu for connections.
 */
public class Menu extends pt.tecnico.uilib.menus.Menu {

  /** @param receiver */
  public Menu(AgentProxy receiver) {
    super(Label.TITLE, //
        new Do_451_ShowCurrentConnections(receiver), //
        new Do_452_RequestConnections(receiver), //
        new Do_453_AcceptConnectionRequests(receiver) //
    );
  }

}
