package sonet.core.publication;

import sonet.core.visits.AttachmentVisitor;


/**
 * Image.
 */
public class Image extends Publication {
  
	/** Class serial number. */
	private static final long serialVersionUID = 201109020943L;

	/** Image representation. */
	private String _image = ""; //$NON-NLS-1$

	/**
	 * @param key
	 * @param title
	 * @param content
	 */
	public Image(int key, String title, String content) {
		super(key, title);
		_image = content;
	}

	/**
	 * @return the image
	 */
	public String getImage() {
		return _image;
	}

	/**
	 * @see sonet.core.message.Attachment#accept(sonet.core.visits.AttachmentVisitor, boolean)
	 */
	@Override
	public void accept(AttachmentVisitor visitor, boolean last) {
		visitor.visitImage(this, last);
	}

}
