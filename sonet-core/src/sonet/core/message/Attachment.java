package sonet.core.message;

import java.io.Serializable;

import sonet.core.visits.AttachmentVisitor;

/**
 * Something that can be attached to a message.
 */
public interface Attachment extends Serializable {
  
	/**
	 * @param visitor
	 * @param last
	 */
	void accept(AttachmentVisitor visitor, boolean last);
	
}
