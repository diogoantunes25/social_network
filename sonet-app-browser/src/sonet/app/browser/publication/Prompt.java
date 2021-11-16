package sonet.app.browser.publication;

/**
 * Prompts.
 */
interface Prompt {

  /** @return prompt for key */
  static String key() {
    return "Identificador da publicação: ";
  }

  /** @return prompt for keys */
  static String keys() {
    return "Identificadores das publicações: ";
  }

  /** @return prompt for publication type */
  static String type() {
    return "Tipo de publicação: ";
  }

  /** @return prompt for publication title */
  static String title() {
    return "Legenda (título): ";
  }

  /** @return prompt for note text */
  static String noteText() {
    return "Texto da nota: ";
  }

  /** @return prompt for "image" */
  static String imageContents() {
    return "Conteúdo da imagem: ";
  }

  /** @return prompt for URI */
  static String uri() {
    return "URI: ";
  }

  /** @return prompt for rating */
  static String rating() {
    return "Pontuação: ";
  }

  /** @return prompt for comment */
  static String comment() {
    return "Comentário: ";
  }

  /** @return prompt for keys */
  static String agentKeys() {
    return "Identificadores dos agentes: ";
  }

}