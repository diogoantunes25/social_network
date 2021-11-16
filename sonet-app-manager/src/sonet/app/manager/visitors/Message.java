package sonet.app.manager.visitors;

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
  
  /** @return tag for active agents */
  static String agentActive() {
    return "ACTIVE";
  }

  /** @return tag for inactive agents */
  static String agentInactive() {
    return "INACTIVE";
  }

  /** @return tag for text notes */
  static String typeNote() {
    return "NOTE";
  }

  /** @return tag for URIs */
  static String typeURI() {
    return "URI";
  }

  /** @return tag for images */
  static String typeImage() {
    return "IMAGE";
  }

}
