package sonet.app.browser.visitors;

import java.util.TreeMap;

import sonet.core.message.Email;
import sonet.core.publication.Album;
import sonet.core.publication.Image;
import sonet.core.publication.Publication;
import sonet.core.publication.TextNote;
import sonet.core.publication.URI;
import sonet.core.visits.AttachmentVisitor;

/**
 * 
 */
public class RenderDocumentSummary extends AttachmentVisitor {

	/** Rendered publications: ordered by id. */
	private TreeMap<Integer, String> _rendering = new TreeMap<Integer, String>();

	/** Expand details in selected entities. */
	private AttachmentVisitor _detailer;

	/**
	 * 
	 */
	public RenderDocumentSummary() {
		// EMPTY no details
	}

	/**
	 * @param detailer
	 */
	public RenderDocumentSummary(AttachmentVisitor detailer) {
		_detailer = detailer;
		_detailer.setParent(this);
	}

	/**
	 * @param msg
	 * @return string version
	 */
	private String renderFields(Email msg) {
		return msg.getKey() + "|" + msg.getFrom() + "|" + msg.getTo() + "|"
				+ msg.getSubject() + "|" + msg.getNumberOfAttachments();
	}

	/**
	 * @param pub
	 *            publication summary
	 * @return string version
	 */
	private String renderFields(Publication pub) {
		return pub.getKey() + "|" + pub.getPositiveVotes() + "|" + pub.getNegativeVotes() + "|"
				+ pub.getNumberOfComments();
	}

	/**
	 * @see sonet.core.visits.AttachmentVisitor#visitImage(sonet.core.publication.Image,
	 *      boolean)
	 */
	@Override
	public void visitImage(Image publication, boolean last) {
		String rendering = Message.typeImage() + "|" + renderFields(publication);
		if (_detailer != null) {
			publication.accept(_detailer, last);
			String details = _detailer.toString();
			if (!details.isEmpty())
				rendering += "\n" + details;
		}
		_rendering.put(publication.getKey(), rendering + (last ? "" : "\n"));
	}

	/**
	 * @see sonet.core.visits.AttachmentVisitor#visitTextNote(sonet.core.publication.TextNote,
	 *      boolean)
	 */
	@Override
	public void visitTextNote(TextNote publication, boolean last) {
		String rendering = Message.typeNote() + "|" + renderFields(publication);
		if (_detailer != null) {
			publication.accept(_detailer, last);
			String details = _detailer.toString();
			if (!details.isEmpty())
				rendering += "\n" + details;
		}
		_rendering.put(publication.getKey(), rendering + (last ? "" : "\n"));
	}

	/**
	 * @see sonet.core.visits.AttachmentVisitor#visitURI(sonet.core.publication.URI,
	 *      boolean)
	 */
	@Override
	public void visitURI(URI publication, boolean last) {
		String rendering = Message.typeURI() + "|" + renderFields(publication);
		if (_detailer != null) {
			publication.accept(_detailer, last);
			String details = _detailer.toString();
			if (!details.isEmpty())
				rendering += "\n" + details;
		}
		_rendering.put(publication.getKey(), rendering + (last ? "" : "\n"));
	}

	/**
	 * @see sonet.core.visits.AttachmentVisitor#visitAlbum(sonet.core.publication.Album,
	 *      boolean)
	 */
	@Override
	public void visitAlbum(Album album, boolean last) {
		// TODO not implemented
	}

	/**
	 * @see sonet.core.visits.AttachmentVisitor#visitEmailMessage(sonet.core.message.Email,
	 *      boolean)
	 */
	@Override
	public void visitEmailMessage(Email msg, boolean last) {
		String rendering = "MESSAGE" + "|" + renderFields(msg);
		if (_detailer != null) {
			msg.accept(_detailer, last);
			String details = _detailer.toString();
			if (!details.isEmpty())
				rendering += "\n" + details;
		}
		_rendering.put(msg.getKey(), rendering + (last ? "" : "\n"));
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String output = ""; //$NON-NLS-1$
		for (int pid : _rendering.keySet())
			output += _rendering.get(pid);
		return output;
	}

}
