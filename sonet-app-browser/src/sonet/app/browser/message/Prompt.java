package sonet.app.browser.message;

/**
 * Prompts.
 */
interface Prompt {

  /** @return prompt for key */
  static String key() {
    return "Identificador da mensagem: ";
  }

  /** @return prompt for keys */
  static String keys() {
    return "Identificadores de anexos: ";
  }

  /** @return prompt for message subject */
  static String subject() {
    return "Assunto: ";
  }

  /** @return prompt for message text */
  static String text() {
    return "Texto (terminar com . no ínicio de uma linha)\n";
  }

  /** @return prompt for message removal */
  static String removeMessage() {
    return "Remover mensagem? (s/n) ";
  }

  /** @return prompt for keys */
  static String agentKeys() {
    return "Identificadores dos agentes: ";
  }

}