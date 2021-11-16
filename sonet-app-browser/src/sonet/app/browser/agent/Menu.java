package sonet.app.browser.agent;

import pt.tecnico.uilib.menus.DoOpenMenu;
import sonet.core.AgentProxy;

/**
 * Menu for agent operations.
 */
public class Menu extends pt.tecnico.uilib.menus.Menu {

  /** @param receiver */
  public Menu(AgentProxy receiver) {
    super(Message.menuTitle(receiver.getVisited().getId()), //
        new Do_421_ShowProfile(receiver), //
        new Do_422_EditProfile(receiver), //
        new Do_423_SetProfileClosed(receiver), //
        new Do_424_SetProfileOpen(receiver), //
        new DoOpenMenu(Label.OPEN_MENU_PUBLICATIONS, new sonet.app.browser.publication.Menu(receiver)), //
        new Do_426_OpenMenuMessages(receiver), //
        new DoOpenMenu(Label.OPEN_MENU_CONNECTIONS, new sonet.app.browser.connection.Menu(receiver)) //
    );
  }

}
