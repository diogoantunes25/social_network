package sonet.app.manager.agents;

/**
 * Messages.
 */
interface Message {

  /**
   * @return tag for individual agents
   */
  static String typePerson() {
    return "PERSON";
  }

  /**
   * @return tag for organizations
   */
  static String typeOrganization() {
    return "ORGANIZATION";
  }

  /**
   * @return tag for active agents
   */
  static String agentActive() {
    return "ACTIVE";
  }

  /**
   * @return tag for inactive agents
   */
  static String agentInactive() {
    return "INACTIVE";
  }

}
