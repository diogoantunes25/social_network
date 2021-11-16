package sonet.app.browser;

/**
 * Prompts.
 */
interface Prompt {

  /** @return prompt for identifier. */
  static String login() {
    return "Introduza o seu identificador: ";
  }

}