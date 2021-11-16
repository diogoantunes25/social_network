package sonet.core.publication;

import sonet.core.visits.AttachmentVisitor;

/**
 * Text note.
 */
public class TextNote extends Publication {
  
	/** Class serial number. S */
	private static final long serialVersionUID = 201109020943L;

	/** Note contents (text). */
	private String _contents = ""; //$NON-NLS-1$

	/**
	 * @param key
	 * @param title
	 * @param text
	 */
	public TextNote(int key, String title, String text) {
		super(key, title);
		_contents = text;
	}

	/**
	 * @return contents
	 */
	public String getContents() {
		return _contents;
	}

	/**
	 * @see sonet.core.message.Attachment#accept(sonet.core.visits.AttachmentVisitor, boolean)
	 */
	@Override
	public void accept(AttachmentVisitor visitor, boolean last) {
		visitor.visitTextNote(this, last);
	}

}
