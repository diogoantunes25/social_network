package sonet.core;

import java.io.FileNotFoundException;
import java.io.IOException;

import sonet.core.agent.Agent;
import sonet.core.agent.ProfileAccess;
import sonet.core.exceptions.InaccessibleAgentException;
import sonet.core.exceptions.InaccessiblePublicationException;
import sonet.core.exceptions.PublicationCommentException;
import sonet.core.exceptions.PublicationExistsException;
import sonet.core.exceptions.PublicationRatingException;
import sonet.core.exceptions.RestrictedOperationException;
import sonet.core.exceptions.UnknownAgentException;
import sonet.core.exceptions.UnknownDataException;
import sonet.core.exceptions.UnknownEmailMessageException;
import sonet.core.exceptions.UnknownPublicationException;
import sonet.core.exceptions.UnnamedDBException;
import sonet.core.message.Email;
import sonet.core.message.MessagingAccess;
import sonet.core.publication.Publication;
import sonet.core.publication.PublicationAccess;
import sonet.core.visits.AgentVisitor;
import sonet.core.visits.AttachmentVisitor;
import sonet.core.visits.ConnectionVisitor;
import sonet.core.visits.Selector;

/**
 * This is a façade for browsing the network.
 */
public class AgentProxy {

  /** The network itself. */
  private NetworkManager _networkManager;

  /** The network. */
  private Network _network;

  /** Logged in agent. */
  private Agent _login;

  /** Visited agent. */
  private Agent _visited;

  /**
   * @param dbname
   * @param id
   * @throws UnknownAgentException
   */
  public AgentProxy(String dbname, int id) throws UnknownAgentException {
    _networkManager = new NetworkManager();
    try {
      _networkManager.load(dbname);
      _network = _networkManager.getNetwork();
      _login = _network.assertAgentExists(id);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * @param key
   * @throws UnknownAgentException
   */
  public void setVisited(int key) throws UnknownAgentException {
    _visited = getAgent(key);
  }

  /**
   * @return visited agent
   */
  public Agent getVisited() {
    return _visited;
  }

  /**
   * @param id
   * @return the agent corresponding to the specified identifier.
   * @throws UnknownAgentException
   */
  public Agent getAgent(int id) throws UnknownAgentException {
    return _network.getAgent(id);
  }

  /**
   * @throws FileNotFoundException
   * @throws UnnamedDBException
   * @throws IOException
   */
  public void shutdown() throws FileNotFoundException, UnnamedDBException, IOException {
    _networkManager.save();
  }

  /**
   * Ensure restricted operations are not executed by anyone.
   * 
   * @throws RestrictedOperationException
   */
  public final void ensureAuthorized() throws RestrictedOperationException {
    if (_login.getId() != _visited.getId() && _login.getId() != 0)
      throw new RestrictedOperationException(_login.getId(), _visited.getId());
  }

  /**
   * Ensure agent profile is accessible. FIXME "root" is assumed to be connected
   * to all agents.
   * 
   * @throws InaccessibleAgentException
   */
  public final void ensureAgentAccessible() throws InaccessibleAgentException {
    if (_login.getId() == 0 || (_visited.isActive() && _visited.isConnectionActiveFrom(_login)
        && _visited.isProfileAuthorized(_login.getId())))
      return;
    throw new InaccessibleAgentException(_login.getId(), _visited.getId());
  }

  /**
   * Ensure agent profile is accessible. FIXME
   * 
   * @param pid
   * @return publication
   * 
   * @throws InaccessiblePublicationException
   * @throws UnknownPublicationException
   */
  public final Publication ensurePublicationAccessible(int pid)
      throws InaccessiblePublicationException, UnknownPublicationException {
    try {
      ensureAgentAccessible();
      Publication publication = _visited.assertPublicationExists(pid);
      if (publication.grantsAccessTo(_login.getId()))
        return publication;
      throw new InaccessiblePublicationException(_login.getId(), pid);
    } catch (InaccessibleAgentException e) {
      throw new InaccessiblePublicationException(_login.getId(), pid);
    }
  }

  /**
   * @param keys
   * @param access
   * @throws UnknownAgentException
   * @throws RestrictedOperationException
   */
  public void setAgentProfileProtection(String keys, ProfileAccess access)
      throws UnknownAgentException, RestrictedOperationException {
    ensureAuthorized();
    _network.setAgentProfileProtection(_visited, keys, access);
  }

  /**
   * @param pid
   * @throws UnknownPublicationException
   */
  public void assertPublicationExists(int pid) throws UnknownPublicationException {
    _visited.assertPublicationExists(pid);
  }

  /**
   * @param name
   * @param email
   * @param phone
   */
  public void setAgentProperties(String name, String email, String phone) {
    try {
      ensureAuthorized();
      _visited.setName(name);
      _visited.setEmailAddress(email);
      _visited.setPhoneNumber(phone);
    } catch (RestrictedOperationException e) {
      // EMPTY: fail silently
    }
  }

  /**
   * @param visitor
   */
  public void acceptAgentVisitor(AgentVisitor visitor) {
    try {
      ensureAgentAccessible();
      _visited.accept(visitor, true);
    } catch (InaccessibleAgentException e) {
      // EMPTY: fail silently;
    }
  }

  /**
   * @param selector
   * @param visitor
   */
  public void acceptConnectionsVisitor(Selector<Connection> selector, ConnectionVisitor visitor) {
    try {
      ensureAgentAccessible();
      _visited.accept(selector, visitor);
    } catch (InaccessibleAgentException e) {
      // EMPTY: fail silently;
    }
  }

  /**
   * @param visitor
   */
  public void acceptConnectionsVisitor(ConnectionVisitor visitor) {
    acceptConnectionsVisitor(new Selector<Connection>() {
      // empty: default
    }, visitor);
  }

  /**
   * @param fields 0 - type; 1 - id/null; 2 - agent; other - pub-specific
   * @throws UnknownDataException
   * @throws UnknownAgentException
   * @throws PublicationExistsException
   */
  public void registerPublication(String... fields)
      throws PublicationExistsException, UnknownAgentException, UnknownDataException {
    try {
      ensureAuthorized();
      _network.registerPublication(fields);
    } catch (RestrictedOperationException e) {
      // EMPTY: fail silently
    }
  }

  /**
   * @param keys
   * @param access
   * @throws UnknownAgentException
   * @throws RestrictedOperationException
   */
  public void setPublicationsProtection(String keys, PublicationAccess access)
      throws UnknownAgentException, RestrictedOperationException {
    ensureAuthorized();
    _network.setPublicationsProtection(_visited, keys, access);
  }

  /**
   * @param keys
   * @param access
   * @throws UnknownAgentException
   * @throws RestrictedOperationException
   */
  public void setMessagingProtection(String keys, MessagingAccess access)
      throws UnknownAgentException, RestrictedOperationException {
    ensureAuthorized();
    _network.setMessagingProtection(_visited, keys, access);
  }

  /**
   * @param pid
   * @param keys
   * @param access
   * @throws UnknownPublicationException
   * @throws UnknownAgentException
   */
  public void setPublicationProtection(int pid, String keys, PublicationAccess access)
      throws UnknownPublicationException, UnknownAgentException {
    try {
      ensureAuthorized();
      assertPublicationExists(pid);
      _network.setPublicationProtection(_visited, pid, keys, access);
    } catch (RestrictedOperationException e) {
      // EMPTY: fail silently
    }
  }

  /**
   * @param pid
   * @param rating
   * @throws UnknownPublicationException
   * @throws PublicationRatingException
   */
  public void ratePublication(int pid, int rating) throws UnknownPublicationException, PublicationRatingException {
    try {
      Publication publication = ensurePublicationAccessible(pid);
      publication.rate(_visited, rating);
    } catch (InaccessiblePublicationException e) {
      throw new PublicationRatingException(e.getPublicationKey(), e.getAgentKey());
    }
  }

  /**
   * @param pid
   * @param comment
   * @throws UnknownPublicationException
   * @throws PublicationCommentException
   */
  public void commentPublication(int pid, String comment)
      throws UnknownPublicationException, PublicationCommentException {
    try {
      Publication publication = ensurePublicationAccessible(pid);
      publication.comment(_visited, comment);
    } catch (InaccessiblePublicationException e) {
      throw new PublicationCommentException(e.getPublicationKey(), e.getAgentKey());
    }
  }

  /**
   * @param selector
   * @param visitor
   */
  public void acceptPublicationsVisitor(Selector<Publication> selector, AttachmentVisitor visitor) {
    try {
      ensureAgentAccessible();

      int publicationCount = 0;
      int numberOfPublications = _visited.getNumberOfPublications();
      for (int pid : _visited.getPublicationsKeys()) {
        ++publicationCount;
        Publication publication = _visited.getPublication(pid);
        if (publication != null && publication.grantsAccessTo(_login.getId()) && selector.ok(publication)) {
          publication.accept(visitor, publicationCount == numberOfPublications);
        }
      }

    } catch (InaccessibleAgentException e) {
      // EMPTY: fail silently;
    }
  }

  /**
   * @param selector
   * @param visitor
   */
  public void acceptPublicationsVisitor(AttachmentVisitor visitor) {
    acceptPublicationsVisitor(new Selector<Publication>() {
      // empty: default
    }, visitor);
  }

  /**
   * @param pid
   * @param visitor
   * @throws UnknownPublicationException
   * @throws InaccessiblePublicationException
   */
  public void acceptPublicationVisitor(int pid, AttachmentVisitor visitor)
      throws UnknownPublicationException, InaccessiblePublicationException {
    Publication publication = ensurePublicationAccessible(pid);
    publication.accept(visitor, true);
  }

  /**
   * @param selector
   * 
   * @param visitor
   */
  public void acceptInboxVisitor(Selector<Email> selector, AttachmentVisitor visitor) {
    try {
      ensureAuthorized();
      _visited.accept(selector, visitor, Agent.Mailbox.INBOX);
    } catch (RestrictedOperationException e) {
      // EMPTY: fail silently;
    }
  }

  /**
   * @param visitor
   */
  public void acceptInboxVisitor(AttachmentVisitor visitor) {
    acceptInboxVisitor(new Selector<Email>() {
      // empty: default
    }, visitor);
  }

  /**
   * @param selector
   * @param visitor
   */
  public void acceptOutboxVisitor(Selector<Email> selector, AttachmentVisitor visitor) {
    try {
      ensureAuthorized();
      _visited.accept(selector, visitor, Agent.Mailbox.OUTBOX);
    } catch (RestrictedOperationException e) {
      // EMPTY: fail silently;
    }
  }

  /**
   * @param visitor
   */
  public void acceptOutboxVisitor(AttachmentVisitor visitor) {
    acceptOutboxVisitor(new Selector<Email>() {
      // empty: default
    }, visitor);
  }

  /**
   * @param mid
   * @param visitor
   * @throws UnknownEmailMessageException
   */
  public void acceptEmailMessageVisitor(int mid, AttachmentVisitor visitor) throws UnknownEmailMessageException {
    try {
      ensureAuthorized();
      Email msg = _visited.assertEmailMessageExists(mid);
      msg.accept(visitor, true);
    } catch (RestrictedOperationException e) {
      // EMPTY: fail silently;
    }
  }

  /**
   * @param rcpts
   * @param subject
   * @param text
   * @param attachments
   */
  public void sendMessage(String rcpts, String subject, String text, String attachments) {
    try {
      ensureAuthorized();
      _network.sendEmail(_visited, rcpts, subject, text, attachments, false);
    } catch (RestrictedOperationException e) {
      // EMPTY: fail silently;
    }
  }

  /**
   * @param mid
   * @param text
   * @param attachments
   * @throws UnknownEmailMessageException
   */
  public void replyToMessage(int mid, String text, String attachments) throws UnknownEmailMessageException {
    try {
      ensureAuthorized();
      Email message = _visited.getInboxMessage(mid);

      String actualAttachments = Integer.toString(mid);
      if (!attachments.trim().isEmpty())
        actualAttachments = actualAttachments + "," + attachments;

      String subject = Message.inReply() + message.getSubject();

      _network.sendEmail(_visited, Integer.toString(message.getSender().getId()), subject, text, actualAttachments,
          true);
    } catch (RestrictedOperationException e) {
      // EMPTY: fail silently;
    }
  }

  /**
   * @param mid
   * @param rcpts
   * @param text
   * @param attachments
   * @throws UnknownEmailMessageException
   * @throws RestrictedOperationException
   */
  public void forwardMessage(int mid, String rcpts, String text, String attachments)
      throws UnknownEmailMessageException, RestrictedOperationException {
    ensureAuthorized();
    Email message = _visited.getInboxMessage(mid);

    String actualAttachments = Integer.toString(mid);
    if (!attachments.trim().isEmpty())
      actualAttachments = actualAttachments + "," + attachments;

    String subject = Message.forwarded() + message.getSubject();
    _network.sendEmail(_visited, rcpts, subject, text, actualAttachments, true);
  }

  /**
   * @param mid
   * @throws UnknownEmailMessageException
   * @throws RestrictedOperationException
   */
  public void removeMessage(int mid) throws UnknownEmailMessageException, RestrictedOperationException {
    ensureAuthorized();
    _visited.removeMessage(mid);
  }

  /**
   * @param keys
   * @throws RestrictedOperationException
   */
  public void requestConnections(String keys) throws RestrictedOperationException {
    ensureAuthorized();
    _network.requestConnections(_visited, keys);
  }

  /**
   * @param keys
   * @throws UnknownAgentException
   * @throws RestrictedOperationException 
   */
  public void acceptConnections(String keys) throws UnknownAgentException, RestrictedOperationException {
    ensureAuthorized();
    _network.acceptConnections(_visited, keys);
  }

}
