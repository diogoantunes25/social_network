package sonet.app.browser.message;

import sonet.core.AgentProxy;

/**
 * Menu for messages.
 */
public class Menu extends pt.tecnico.uilib.menus.Menu {

  /** @param receiver */
  public Menu(AgentProxy receiver) {
    super(Label.TITLE, //
        new Do_441_ShowReceivedMessages(receiver), //
        new Do_442_ShowSentMessages(receiver), //
        new Do_443_ShowMessage(receiver), //
        new Do_444_SendMessage(receiver), //
        new Do_445_ReplyToMessage(receiver), //
        new Do_446_ForwardMessage(receiver), //
        new Do_447_RemoveMessage(receiver), //
        new Do_448_EnableMessagingAccess(receiver), //
        new Do_449_DisableMessagingAccess(receiver) //
    );
  }

}
