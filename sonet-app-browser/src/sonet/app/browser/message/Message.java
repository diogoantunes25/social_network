package sonet.app.browser.message;

/**
 * Messages.
 */
interface Message {

  /**
   * @param key message key
   * @return message removal notice
   */
  static String messageRemoved(int key) {
    return "A mensagem '" + key + "' foi removida.";
  }

}