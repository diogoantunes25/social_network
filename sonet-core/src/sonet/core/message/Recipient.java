package sonet.core.message;

import java.io.Serializable;

/**
 * Message recipient. This interface is to be implemented by those agents
 * willing or capable of receiving messages.
 */
public interface Recipient extends Serializable {

  /**
   * @return the sender's id
   */
  int getId();

	/**
	 * @param message
	 */
	void addToInbox(Email message);

	/**
	 * @param id
	 * @return whether this recipient accepts messages from sender
	 */
	boolean acceptsMessagesFrom(int id);
	
}
