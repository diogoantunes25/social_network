package sonet.core.visits;

import sonet.core.Connection;

/**
 * Render agents to be shown.
 */
public interface ConnectionVisitor {

	/**
	 * @param connection
	 * @param last 
	 */
	void visitConnection(Connection connection, boolean last);
	
}
