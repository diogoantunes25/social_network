package sonet.app.browser.publication;

/**
 * Messages.
 */
interface Message {

  /**
   * @return tag for text notes
   */
  static String typeNote() {
    return "NOTE";
  }

  /**
   * @return tag for URIs
   */
  static String typeURI() {
    return "URI";
  }

  /**
   * @return tag for images
   */
  static String typeImage() {
    return "IMAGE";
  }

}