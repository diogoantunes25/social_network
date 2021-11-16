package sonet.core.publication;

import sonet.core.visits.AttachmentVisitor;

/**
 * URIs.
 */
public class URI extends Publication {
  
	/** Class serial number. */
	private static final long serialVersionUID = 201109020943L;

	/** URI. */
	private String _uri = ""; //$NON-NLS-1$

	/**
	 * @param key
	 * @param title
	 * @param uri
	 */
	public URI(int key, String title, String uri) {
		super(key, title);
		_uri = uri;
	}

	/**
	 * @return uri
	 */
	public String getURI() {
		return _uri;
	}

	/**
	 * @see sonet.core.message.Attachment#accept(sonet.core.visits.AttachmentVisitor, boolean)
	 */
	@Override
	public void accept(AttachmentVisitor visitor, boolean last) {
		visitor.visitURI(this, last);
	}

}
