/** @version $Id: Agent.java,v 1.27 2011/12/11 18:33:39 david Exp $ */
/*
 * $Log: Agent.java,v $
 * Revision 1.27  2011/12/11 18:33:39  david
 * *** empty log message ***
 *
 * Revision 1.26  2011/12/11 18:12:54  david
 * *** empty log message ***
 *
 * Revision 1.25  2011/12/11 18:07:36  david
 * *** empty log message ***
 *
 * Revision 1.24  2011/12/11 17:35:52  david
 * *** empty log message ***
 *
 * Revision 1.23  2011/12/11 16:58:24  david
 * *** empty log message ***
 *
 * Revision 1.22  2011/12/11 16:52:29  david
 * *** empty log message ***
 *
 * Revision 1.21  2011/12/11 16:40:14  david
 * *** empty log message ***
 *
 * Revision 1.20  2011/12/11 16:34:25  david
 * *** empty log message ***
 *
 * Revision 1.19  2011/12/11 15:43:51  david
 * *** empty log message ***
 *
 * Revision 1.18  2011/12/11 13:10:16  david
 * *** empty log message ***
 *
 * Revision 1.17  2011/12/10 20:20:11  david
 * *** empty log message ***
 *
 * Revision 1.16  2011/12/10 20:10:59  david
 * *** empty log message ***
 *
 * Revision 1.15  2011/12/10 18:41:29  david
 * *** empty log message ***
 *
 * Revision 1.14  2011/11/29 15:09:58  david
 * *** empty log message ***
 *
 * Revision 1.13  2011/11/29 10:37:53  david
 * *** empty log message ***
 *
 * Revision 1.12  2011/11/29 09:38:42  david
 * *** empty log message ***
 *
 * Revision 1.11  2011/11/29 07:56:42  david
 * *** empty log message ***
 *
 * Revision 1.10  2011/11/28 23:26:55  david
 * *** empty log message ***
 *
 * Revision 1.9  2011/11/28 19:53:19  david
 * *** empty log message ***
 *
 * Revision 1.8  2011/11/28 19:50:51  david
 * *** empty log message ***
 *
 * Revision 1.7  2011/11/28 19:47:11  david
 * *** empty log message ***
 *
 * Revision 1.6  2011/11/28 19:42:17  david
 * *** empty log message ***
 *
 * Revision 1.5  2011/11/28 19:17:28  david
 * Fixed all-messages bug.
 *
 * Revision 1.4  2011/11/28 14:39:40  david
 * *** empty log message ***
 *
 * Revision 1.3  2011/11/27 20:11:43  david
 * Coding spree.
 *
 * Revision 1.2  2011/11/15 21:33:23  david
 * *** empty log message ***
 *
 * Revision 1.1  2011/11/14 18:02:05  david
 * Created package "sonet.core" and moved "sonet" contents there (except for "textui").
 *
 * Revision 1.8  2011/11/12 21:11:26  david
 * Serialization works.
 *
 * Revision 1.7  2011/10/28 13:56:53  david
 * *** empty log message ***
 *
 * Revision 1.6  2011/10/14 08:35:41  david
 * Keys are integers.
 *
 * Revision 1.5  2011/09/23 19:49:50  david
 * *** empty log message ***
 *
 * Revision 1.4  2011/09/05 17:10:10  david
 * Basic support for the administrative functionality.
 *
 * Revision 1.3  2011/09/04 18:52:53  david
 * *** empty log message ***
 *
 * Revision 1.2  2011/09/03 22:51:24  david
 * *** empty log message ***
 *
 * Revision 1.1  2011/09/02 20:06:22  david
 * *** empty log message ***
 *
 * 
 */
package sonet.core.agent;

import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import sonet.core.Connection;
import sonet.core.exceptions.AgentAlreadyActiveException;
import sonet.core.exceptions.AgentAlreadyInactiveException;
import sonet.core.exceptions.PublicationExistsException;
import sonet.core.exceptions.UnknownAttachmentException;
import sonet.core.exceptions.UnknownEmailMessageException;
import sonet.core.exceptions.UnknownPublicationException;
import sonet.core.message.Attachment;
import sonet.core.message.Email;
import sonet.core.message.MessagingAccess;
import sonet.core.message.Recipient;
import sonet.core.message.Sender;
import sonet.core.publication.Publication;
import sonet.core.publication.PublicationAccess;
import sonet.core.visits.AgentVisitor;
import sonet.core.visits.AttachmentVisitor;
import sonet.core.visits.ConnectionVisitor;
import sonet.core.visits.Selector;

/**
 * This is the root class for all agents in the network.
 * 
 * It contains the unique identifier and the agent's name and contact
 * information. Other information has to be provided by subclasses.
 * 
 */
public abstract class Agent implements Sender, Recipient, Serializable {
  /**
   * Class serial number (serialization).
   */
  private static final long serialVersionUID = 201109020943L;

  /**
   * KEY_COMPARATOR is an instance of an inner class that implements a comparator
   * defining a comparison method for agents based on their unique identifiers.
   * 
   * It would probably be better to have a static method for accessing this
   * object.
   * 
   * @see java.util.Comparator
   */
  public static final Comparator<Agent> KEY_COMPARATOR = new Comparator<Agent>() {

    /**
     * Compare two agents by their unique identifiers.
     * 
     * @param agent1 agent 1
     * @param agent2 agent 2
     * @return -1 if h1.id < h2.id; 0 if h1.id = h2.id; 1 if h1.id >h2.id.
     */
    @Override
    public int compare(Agent agent1, Agent agent2) {
      return Integer.compare(agent1.getId(), agent2.getId());
    }

  }; // KEY_COMPARATOR

  /**
   * NAME_COMPARATOR is an instance of an inner class that implements an agent
   * comparator defining a comparison method for agents based on their names.
   * 
   * It would probably be better to have a static method for accessing this
   * object.
   * 
   * @see java.util.Comparator
   */
  public static final Comparator<Agent> NAME_COMPARATOR = new Comparator<Agent>() {

    /**
     * Compare two agents by name (lexicographically and ignoring case).
     * 
     * @param agent1 agent 1
     * @param agent2 agent 2
     * @return -1 if h1.name comes before h2.name; 0 if h1.name is the same as
     *         h2.name; 1 if h1.name comes after h2.name.
     */
    @Override
    public int compare(Agent agent1, Agent agent2) {
      return agent1.getName().compareToIgnoreCase(agent2.getName());
    }

  }; // NAME_COMPARATOR

  /** The agent's id. */
  private int _id;

  /** The agent's name. */
  private String _name;

  /** The agent's email address. */
  private String _emailAddress;

  /** The agent's phone number. */
  private String _phoneNumber;

  /** Connections seen by this agent. */
  private Map<Integer, Connection> _connections = new TreeMap<>();

  /** Publications by this agent. */
  private Map<Integer, Publication> _publications = new TreeMap<>();

  /** Messages sent by this agent. */
  private Map<Integer, Email> _inbox = new TreeMap<>();

  /** Messages received by this agent. */
  private Map<Integer, Email> _outbox = new TreeMap<>();

  /** Who can access the profile. */
  private Map<Integer, ProfileAccess> _profileProtection = new TreeMap<>();

  /** Who can access messaging. */
  private Map<Integer, MessagingAccess> _messagingProtection = new TreeMap<>();

  /** Activity setting. */
  private boolean _active = true;

  /**
   * FIXME unhappy with this approach. Mailbox selector.
   */
  public enum Mailbox {
    INBOX {
      @Override
      Map<Integer, Email> getMailbox(Agent agent) {
        return agent.getInbox();
      }
    },
    OUTBOX {
      @Override
      Map<Integer, Email> getMailbox(Agent agent) {
        return agent.getOutbox();
      }
    };

    /**
     * @param agent
     * @return the specified mailbox
     */
    abstract Map<Integer, Email> getMailbox(Agent agent);
  }

  /**
   * @param id
   * @param name
   * @param emailAddress
   * @param phoneNumber
   */
  public Agent(int id, String name, String emailAddress, String phoneNumber) {
    _id = id;
    _name = name;
    _emailAddress = emailAddress;
    _phoneNumber = phoneNumber;
  }

  /**
   * @return the agent's id.
   */
  @Override
  public int getId() {
    return _id;
  }

  /**
   * @return the agent's name.
   */
  public String getName() {
    return _name;
  }

  /**
   * @param name the agent's new name.
   */
  public final void setName(String name) {
    _name = name;
  }

  /**
   * @return the agent's email address.
   */
  public String getEmailAddress() {
    return _emailAddress;
  }

  /**
   * @param emailAddress the agent's new email address.
   */
  public final void setEmailAddress(String emailAddress) {
    _emailAddress = emailAddress;
  }

  /**
   * @return the agent's phone number.
   */
  public String getPhoneNumber() {
    return _phoneNumber;
  }

  /**
   * @param phoneNumber the agent's new phone number.
   */
  public final void setPhoneNumber(String phoneNumber) {
    _phoneNumber = phoneNumber;
  }

  /**
   * @return whether the agent is active.
   */
  public boolean isActive() {
    return _active;
  }

  /**
   * Check whether two agents are equal. Two clients are considered equal if they
   * have the same identifier.
   * 
   * @param agent the other agent
   * @return true, if they have the same id; false, otherwise.
   */
  @Override
  public boolean equals(Object agent) {
    return agent instanceof Agent && _id == ((Agent) agent).getId();
  }

  /**
   * @param key
   * @param publication
   * @throws PublicationExistsException
   */
  public void addPublication(int key, Publication publication) throws PublicationExistsException {
    if (_publications.containsKey(key))
      throw new PublicationExistsException(key);
    _publications.put(key, publication);
  }

  /**
   * Should not exist...
   * 
   * @return the map of publications
   */
  public Map<Integer, Publication> getPublications() {
    return _publications;
  }

  /**
   * @return the publications key set
   */
  public Collection<Integer> getPublicationsKeys() {
    return _publications.keySet();
  }

  /**
   * @param pid
   * @return the publication indexed by pid
   */
  public Publication getPublication(int pid) {
    return _publications.get(pid);
  }

  /**
   * @throws AgentAlreadyActiveException
   */
  public void activate() throws AgentAlreadyActiveException {
    if (_active)
      throw new AgentAlreadyActiveException(_id);
    _active = true;
  }

  /**
   * @throws AgentAlreadyInactiveException
   */
  public void deactivate() throws AgentAlreadyInactiveException {
    if (!_active)
      throw new AgentAlreadyInactiveException(_id);
    _active = false;
  }

  /**
   * @param message
   */
  @Override
  public void addToInbox(Email message) {
    _inbox.put(message.getKey(), message);
  }

  /**
   * FIXME
   * 
   * @param message
   */
  @Override
  public void addToOutbox(Email message) {
    int key = message.getKey();
    Email msg = _outbox.get(key);
    if (msg == null)
      _outbox.put(message.getKey(), message);
  }

  /**
   * @return inbox
   */
  private Map<Integer, Email> getInbox() {
    return _inbox;
  }

  /**
   * @return outbox
   */
  private Map<Integer, Email> getOutbox() {
    return _outbox;
  }

  /**
   * @param connection
   */
  public void addConnection(Connection connection) {
    _connections.put(connection.getOther().getId(), connection);
  }

  /**
   * @return the number of publications.
   */
  public int getNumberOfPublications() {
    return _publications.size();
  }

  /**
   * @return the number of received messages.
   */
  public int getNumberOfReceivedMessages() {
    return _inbox.size();
  }

  /**
   * @return the number of sent messages.
   */
  public int getNumberOfSentMessages() {
    return _outbox.size();
  }

  /**
   * @return the number of connections.
   */
  public int getNumberOfConnections() {
    return _connections.size();
  }

  /**
   * Visit the agent.
   * 
   * @param visitor
   * @param last    (is it the last agent in the set?)
   */
  public abstract void accept(AgentVisitor visitor, boolean last);

  /**
   * @param msgselector
   * @param visitor
   * @param mboxselector
   */
  public void accept(Selector<Email> msgselector, AttachmentVisitor visitor, Mailbox mboxselector) {
    int messageCount = 0;
    Map<Integer, Email> mailbox = mboxselector.getMailbox(this);
    int numberOfMessages = mailbox.keySet().size();
    for (int mid : mailbox.keySet()) {
      ++messageCount;
      Email msg = mailbox.get(mid);
      if (msgselector.ok(msg))
        msg.accept(visitor, messageCount == numberOfMessages);
    }
  }

  /**
   * Visit all messages (inbox and outbox).
   * 
   * @param msgselector
   * @param visitor
   * @param lastAgent
   */
  public void acceptAllMessagesVisitor(Selector<Email> msgselector, AttachmentVisitor visitor, boolean lastAgent) {
    int messageCount = 0;
    int numberOfMessages = _inbox.keySet().size() + _outbox.keySet().size();
    for (int mid : _inbox.keySet()) {
      ++messageCount;
      Email msg = _inbox.get(mid);
      if (msgselector.ok(msg))
        msg.accept(visitor, (messageCount == numberOfMessages) && lastAgent);
    }
    for (int mid : _outbox.keySet()) {
      ++messageCount;
      Email msg = _outbox.get(mid);
      if (msgselector.ok(msg))
        msg.accept(visitor, (messageCount == numberOfMessages) && lastAgent);
    }
  }

  /**
   * Visit selected publications.
   * 
   * @param selector
   * 
   * @param visitor
   */
  public void accept(Selector<Publication> selector, AttachmentVisitor visitor) {
    int publicationCount = 0;
    int numberOfPublications = _publications.keySet().size();
    for (int pid : _publications.keySet()) {
      ++publicationCount;
      Publication pub = _publications.get(pid);
      if (selector.ok(pub))
        pub.accept(visitor, publicationCount == numberOfPublications);
    }
  }

  /**
   * @param id
   * @param access
   */
  public void setProfileProtection(int id, ProfileAccess access) {
    _profileProtection.put(id, access);
  }

  /**
   * @param id
   * @param access
   */
  public void setMessagingProtection(int id, MessagingAccess access) {
    _messagingProtection.put(id, access);
  }

  /**
   * @param id
   * @param access
   */
  public void setPublicationsProtection(int id, PublicationAccess access) {
    for (Publication publication : _publications.values()) {
      publication.setProtection(id, access);
    }
  }

  /**
   * @param pid
   * @param id
   * @param access
   * @throws UnknownPublicationException
   */
  public void setPublicationProtection(int pid, int id, PublicationAccess access) throws UnknownPublicationException {
    Publication publication = assertPublicationExists(pid);
    publication.setProtection(id, access);
  }

  /**
   * @param id
   * @return whether the id is authorized to access profile.
   */
  public boolean isProfileAuthorized(int id) {
    ProfileAccess access = _profileProtection.get(id);
    return isConnectionActiveFrom(id) && (access == null || access == ProfileAccess.OPEN);
  }

  /**
   * @param pid
   * @return the publication
   * @throws UnknownPublicationException
   */
  public Publication assertPublicationExists(int pid) throws UnknownPublicationException {
    Publication publication = _publications.get(pid);
    if (publication == null)
      throw new UnknownPublicationException(pid);
    return publication;
  }

  /**
   * @param mid
   * @return the email message
   * @throws UnknownEmailMessageException
   */
  public Email assertEmailMessageExists(int mid) throws UnknownEmailMessageException {
    Email msg = _inbox.get(mid);
    if (msg != null)
      return msg; // found in the inbox
    msg = _outbox.get(mid);
    if (msg != null)
      return msg; // found in the outbox
    throw new UnknownEmailMessageException(mid);
  }

  /**
   * @param mid
   * @throws UnknownEmailMessageException
   */
  public void removeMessage(int mid) throws UnknownEmailMessageException {
    assertEmailMessageExists(mid);
    if (_inbox.containsKey(mid))
      _inbox.remove(mid);
  }

  /**
   * @param agent
   * @return whether the id is connected to the agent.
   */
  public boolean isConnectionActiveFrom(Agent agent) {
    int id = agent.getId();
    return isConnectionActiveFrom(id);
  }

  /**
   * @param id
   * @return whether the id is connected to the agent.
   */
  public boolean isConnectionActiveFrom(int id) {
    return id == 0 || _id == id || (_connections.containsKey(id) && _connections.get(id).isSelfAccepting());
  }

  /**
   * Run by the requester.
   * 
   * @param remote
   */
  public void requestConnectionTo(Agent remote) {
    Connection connection = getConnection(remote.getId());
    if (connection == null) {
      remote.processIncomingConnectionFrom(this);
    } else
      acceptConnection(remote);
  }

  /**
   * Run by the accepting agent.
   * 
   * @param remote
   */
  public void processIncomingConnectionFrom(Agent remote) {
    remote.addConnection(new Connection(remote, this, true, false));
    addConnection(new Connection(this, remote, false, true));
  }

  /**
   * Run by the accepting agent.
   * 
   * @param remote
   */
  public void acceptConnection(Agent remote) {
    Connection connection = _connections.get(remote.getId());
    if (connection == null || connection.isSelfAccepting())
      return;
    connection.setSelfAccepting();
    // update the other end
    remote.remoteApproveConnection(this);
  }

  /**
   * Run by the requester after the acceptor has approved the connection. FIXME
   * really needed?
   * 
   * @param accepting
   */
  private void remoteApproveConnection(Agent accepting) {
    Connection connection = _connections.get(accepting.getId());
    if (connection == null || connection.isOtherAccepting())
      return;
    connection.setOtherAccepting();
  }

  /**
   * FIXME really needed?
   * 
   * @param id
   * @return the connection
   */
  protected Connection getConnection(int id) {
    return _connections.get(id);
  }

  /**
   * Visit selected connections.
   * 
   * @param selector
   * 
   * @param visitor
   */
  public void accept(Selector<Connection> selector, ConnectionVisitor visitor) {
    int connectionCount = 0;
    int numberOfConnections = _connections.keySet().size();
    for (int pid : _connections.keySet()) {
      ++connectionCount;
      Connection conn = _connections.get(pid);
      if (selector.ok(conn))
        conn.accept(visitor, connectionCount == numberOfConnections);
    }
  }

  /**
   * @param id
   * @return attachment
   * @throws UnknownAttachmentException
   */
  public Attachment fetchAttachment(int id) throws UnknownAttachmentException {
    Attachment attachment = _publications.get(id);
    if (attachment != null)
      return attachment;
    attachment = _inbox.get(id);
    if (attachment != null)
      return attachment;
    attachment = _outbox.get(id);
    if (attachment != null)
      return attachment;
    throw new UnknownAttachmentException(id);
  }

  /**
   * @param mid
   * @return message from inbox
   * @throws UnknownEmailMessageException
   */
  public Email getInboxMessage(int mid) throws UnknownEmailMessageException {
    Email message = _inbox.get(mid);
    if (message != null)
      return message;
    throw new UnknownEmailMessageException(mid);
  }

  /**
   * @see sonet.core.message.Recipient#acceptsMessagesFrom(int)
   */
  @Override
  public boolean acceptsMessagesFrom(int id) {
    if (id == 0)
      return true;
    MessagingAccess access = _messagingProtection.get(id);
    return isProfileAuthorized(id) && (access == null || access == MessagingAccess.ENABLED);
  }

}
