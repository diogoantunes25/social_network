package sonet.core.message;

import java.util.ArrayList;
import java.util.List;

import sonet.core.visits.AttachmentVisitor;

/**
 * Email message.
 */
public class Email implements Comparable<Email>, Attachment {

  /** Class serial number. */
  private static final long serialVersionUID = 201109020943L;

  /** The message key. */
  private int _key;

  /** The sender of this message. */
  private Sender _sender;

  /** The original "from" string. */
  private String _fromString;

  /** The recipients of this message. */
  private List<Recipient> _recipients;

  /** The original "to" string. */
  private String _toString;

  /** The message subject. */
  private String _subject;

  /** The message body. */
  private String _body;

  /** The attachments of this message. */
  private List<Attachment> _attachments = new ArrayList<Attachment>();

  /**
   * @param key
   * @param sender
   * @param recipients
   */
  @Deprecated
  private Email(int key, Sender sender, List<Recipient> recipients) {
    _key = key;
    _sender = sender;
    _fromString = Integer.toString(sender.getId());
    _recipients = recipients;
    _toString = ""; //$NON-NLS-1$
    for (Recipient recipient : _recipients) {
      // FIXME: extra comma at last entry
      _toString += Integer.toString(recipient.getId()) + ",";
    }
  }

  /**
   * @param key
   * @param from
   * @param sender
   * @param to
   * @param recipients
   */
  private Email(int key, String from, Sender sender, String to, List<Recipient> recipients) {
    _key = key;
    _sender = sender;
    _fromString = from;
    _recipients = recipients;
    _toString = to;
  }

  /**
   * @param key
   * @param from
   * @param sender
   * @param to
   * @param recipients
   * @param subject
   * @param body
   * @param attachments
   */
  public Email(int key, String from, Sender sender, String to, List<Recipient> recipients, String subject,
      String body, String att, List<Attachment> attachments) {
    this(key, from, sender, to, recipients);
    _subject = subject;
    _body = body;
    if (attachments != null)
      _attachments = attachments;
  }

  /**
   * @return the account number.
   */
  public final int getKey() {
    return _key;
  }

  /** @see java.lang.Comparable#compareTo(Object) */
  @Override
  public int compareTo(Email message) {
    return _key - message.getKey();
  }

  /**
   * @return the _sender
   */
  public Sender getSender() {
    return _sender;
  }

  /**
   * @return the original text "from" string.
   */
  public String getFrom() {
    return _fromString;
  }

  /**
   * Returns the message recipients.
   * 
   * @return a list with the message recipients.
   */
  public List<Recipient> getRecipients() {
    return _recipients;
  }

  /**
   * Returns the message's attachments.
   * 
   * @return a list with the message's attachments.
   */
  public List<Attachment> getAttachments() {
    return _attachments;
  }

  /**
   * @return the original text comma-separated "to" string.
   */
  public String getTo() {
    return _toString;
  }

  /**
   * Add a recipient.
   * 
   * @param recipient the recipient to be added.
   */
  public void addClient(Recipient recipient) {
    _recipients.add(recipient);
  }

  /**
   * @return the subject
   */
  public String getSubject() {
    return _subject;
  }

  /**
   * @return the body
   */
  public String getBody() {
    return _body;
  }

  /**
   * @return number of attachments
   */
  public int getNumberOfAttachments() {
    return _attachments.size();
  }

  /**
   * @param preserveFirstAttachment
   */
  public void clearAttachments(boolean preserveFirstAttachment) {
    if (preserveFirstAttachment && _attachments.size() > 0) {
      Attachment first = _attachments.get(0);
      _attachments = new ArrayList<Attachment>();
      _attachments.add(first);
    } else {
      _attachments = new ArrayList<Attachment>();
    }
  }

  /** @see java.lang.Object#equals(Object) */
  @Override
  public boolean equals(Object obj) {
    return obj instanceof Email email && _key == email.getKey();
  }

  /**
   * @see sonet.core.message.Attachment#accept(sonet.core.visits.AttachmentVisitor,
   *      boolean)
   */
  @Override
  public void accept(AttachmentVisitor visitor, boolean last) {
    visitor.visitEmailMessage(this, last);
  }

}
