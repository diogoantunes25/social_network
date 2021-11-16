package sonet.core.publication;

import java.util.ArrayList;
import java.util.List;

import sonet.core.visits.AttachmentVisitor;

/**
 * Album for publications.
 */
public class Album extends Publication {
  
	/** Class serial number. */
	private static final long serialVersionUID = 201109020943L;

	/** The album's contents. */
	public List<Publication> _contents = new ArrayList<>();

	/**
	 * @param key
	 * @param title
	 * @param content
	 */
	public Album(int key, String title, String content) {
		super(key, title);
	}

	/**
	 * @see sonet.core.publication.Publication#accept(sonet.core.visits.AttachmentVisitor, boolean)
	 */
	@Override
	public void accept(AttachmentVisitor visitor, boolean last) {
		visitor.visitAlbum(this, last);
	}

}
