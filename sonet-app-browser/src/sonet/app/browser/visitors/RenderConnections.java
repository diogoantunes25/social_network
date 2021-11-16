package sonet.app.browser.visitors;

import sonet.core.Connection;
import sonet.core.visits.ConnectionVisitor;

/**
 * 
 */
public class RenderConnections implements ConnectionVisitor {

  /** Rendered connection. */
  private String _rendering = "";

  /**
   * @param connection
   * @return common fields.
   */
  private String renderFields(Connection connection) {
    return connection.getOther().getId() + "|" + (connection.isOtherAccepting() ? 1 : 0);
  }

  /**
   * @see sonet.core.visits.ConnectionVisitor#visitConnection(sonet.core.Connection,
   *      boolean)
   */
  @Override
  public void visitConnection(Connection connection, boolean last) {
    _rendering += "CONNECTION" + "|" + renderFields(connection) + (last ? "" : "\n");
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return _rendering;
  }
  
}
