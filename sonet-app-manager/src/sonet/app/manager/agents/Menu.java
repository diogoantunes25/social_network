package sonet.app.manager.agents;

import sonet.core.Network;

/**
 * Menu builder for clients.
 */
public class Menu extends pt.tecnico.uilib.menus.Menu {

  /** @param receiver */
  public Menu(Network receiver) {
    super(Label.TITLE, //
        new Do_321_ShowAllAgents(receiver), // §3.2.1
        new Do_322_RegisterAgent(receiver), // §3.2.2
        new Do_323_DeactivateAgent(receiver), // §3.2.3
        new Do_324_ActivateAgent(receiver) // §3.2.4
    );
  }

}
