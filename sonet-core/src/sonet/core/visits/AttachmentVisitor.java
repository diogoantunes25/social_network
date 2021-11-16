package sonet.core.visits;

import sonet.core.message.Email;
import sonet.core.publication.Album;
import sonet.core.publication.Image;
import sonet.core.publication.TextNote;
import sonet.core.publication.URI;

/**
 * Render agents to be shown.
 */
public abstract class AttachmentVisitor {

	/** The parent visitor. */
	private AttachmentVisitor _parent;
	
	/** @return the parent visitor */
	public AttachmentVisitor getParent() {
		return _parent;
	}
	
	/** @param parent */
	public void setParent(AttachmentVisitor parent) {
		_parent = parent;
	}
	
	/**
	 * @param publication
	 * @param last 
	 */
	public abstract void visitImage(Image publication, boolean last);

	/**
	 * @param publication
	 * @param last 
	 */
	public abstract void visitTextNote(TextNote publication, boolean last);

	/**
	 * @param publication
	 * @param last 
	 */
	public abstract void visitURI(URI publication, boolean last);

	/**
	 * @param album
	 * @param last
	 */
	public abstract void visitAlbum(Album album, boolean last);

	/**
	 * @param msg
	 * @param last 
	 */
	public abstract void visitEmailMessage(Email msg, boolean last);

}
