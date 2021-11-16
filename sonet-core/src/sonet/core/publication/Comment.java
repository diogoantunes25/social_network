package sonet.core.publication;

import java.io.Serializable;

import sonet.core.agent.Agent;

/**
 * Comment.
 */
public class Comment implements Serializable {
  
	/** Class serial number. */
	private static final long serialVersionUID = 201109020943L;

	/** Agent (commenter). */
	private Agent _author;

	/** The comment itself. */
	private String _comment;

	/**
	 * @param author
	 * @param comment
	 */
	public Comment(Agent author, String comment) {
		_author = author;
		_comment = comment;
	}

	/**
	 * @return the author's key
	 */
	public Agent getAuthor() {
		return _author;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return _comment;
	}

}
