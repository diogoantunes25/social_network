package sonet.app.browser;

/**
 * Messages.
 */
interface Message {

  static String noSuchUser(int key) {
    return "O agente '" + key + "' não existe.";
  }
  
}