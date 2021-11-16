package sonet.app.browser;

import java.io.IOException;

import pt.tecnico.uilib.Dialog;
import pt.tecnico.uilib.Display;
import pt.tecnico.uilib.forms.Form;
import sonet.app.browser.main.Menu;
import sonet.core.AgentProxy;
import sonet.core.exceptions.UnknownAgentException;
import sonet.core.exceptions.UnnamedDBException;

/**
 * This is a simple text user interface for the browser façade.
 */
public class App {

  /** @param args */
  public static void main(String[] args) {
    try (var ui = Dialog.UI) {
      AgentProxy browser = new AgentProxy("sonet.dat", Form.requestInteger(Prompt.login()));
      Menu menu = new Menu(browser);
      menu.open();
      browser.shutdown();
    } catch (UnknownAgentException e) {
      Display display = new Display();
      display.popup(Message.noSuchUser(e.getKey()));
    } catch (UnnamedDBException | IOException e) {
      e.printStackTrace();
    }
  }

}
