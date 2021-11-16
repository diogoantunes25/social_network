package sonet.app.manager.agents;

/**
 * Prompts.
 */
interface Prompt {

  /** @return prompt for agent type */
  static String type() {
    return "Tipo de agente: ";
  }

  /** @return prompt for key */
  static String key() {
    return "Identificador do agente: ";
  }

  /** @return prompt for name */
  static String name() {
    return "Nome do agente: ";
  }

  /** @return prompt for email */
  static String email() {
    return "Email do agente: ";
  }

  /** @return prompt for phone number */
  static String phone() {
    return "Telefone do agente: ";
  }

}
