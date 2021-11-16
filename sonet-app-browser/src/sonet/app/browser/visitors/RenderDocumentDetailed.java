package sonet.app.browser.visitors;

import sonet.core.message.Attachment;
import sonet.core.message.Email;
import sonet.core.publication.Album;
import sonet.core.publication.Comment;
import sonet.core.publication.Image;
import sonet.core.publication.Publication;
import sonet.core.publication.TextNote;
import sonet.core.publication.URI;
import sonet.core.visits.AttachmentVisitor;

/**
 * 
 */
public class RenderDocumentDetailed extends AttachmentVisitor {

  /** Resulting rendering. */
  private String _rendering = "";

  /**
   * @param pub publication summary
   * @return string version
   */
  private String renderComments(Publication pub) {
    String comments = "";
    for (Comment comment : pub.getComments())
      comments += "\n" + comment.getComment();
    return comments;
  }

  /**
   * @see sonet.core.visits.AttachmentVisitor#visitImage(sonet.core.publication.Image,
   *      boolean)
   */
  @Override
  public void visitImage(Image publication, boolean last) {
    _rendering = publication.getImage();
    String comments = renderComments(publication);
    if (!comments.isEmpty())
      _rendering += comments;
  }

  /**
   * @see sonet.core.visits.AttachmentVisitor#visitTextNote(sonet.core.publication.TextNote,
   *      boolean)
   */
  @Override
  public void visitTextNote(TextNote publication, boolean last) {
    _rendering = publication.getContents();
    String comments = renderComments(publication);
    if (!comments.isEmpty())
      _rendering += comments;
  }

  /**
   * @see sonet.core.visits.AttachmentVisitor#visitURI(sonet.core.publication.URI,
   *      boolean)
   */
  @Override
  public void visitURI(URI publication, boolean last) {
    _rendering = publication.getURI();
    String comments = renderComments(publication);
    if (!comments.isEmpty())
      _rendering += comments;
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
    _rendering = msg.getBody();
    for (Attachment a : msg.getAttachments()) {
      RenderDocumentSummary summarizer = new RenderDocumentSummary();
      RenderDocumentDetailed detailer = new RenderDocumentDetailed();
      a.accept(summarizer, true);
      a.accept(detailer, true);
      String srendering = summarizer.toString();
      String drendering = detailer.toString();
      if (srendering.length() != 0) {
        _rendering += "\n" + srendering;
        if (drendering.length() != 0) {
          _rendering += "\n" + drendering;
        }
      }
    }
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return _rendering;
  }

}
