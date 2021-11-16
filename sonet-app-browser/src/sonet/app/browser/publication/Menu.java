package sonet.app.browser.publication;

import sonet.core.AgentProxy;

/**
 * Menu builder for clients.
 */
public class Menu extends pt.tecnico.uilib.menus.Menu {

  /** @param browser */
  public Menu(AgentProxy browser) {
    super(MenuEntry.TITLE, //
        new Do_431_ShowPublications(browser), //
        new Do_432_ShowPublication(browser), //
        new Do_433_RegisterPublication(browser), //
        new Do_434_RatePublication(browser), //
        new Do_435_CommentPublication(browser), //
        new Do_436_CloseAccessToPublication(browser), //
        new Do_437_CloseAccessToPublications(browser), //
        new Do_438_OpenAccessToPublication(browser), //
        new Do_439_OpenAccessToPublications(browser) //
    );
  }

}
