package sonet.core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import sonet.core.agent.Agent;
import sonet.core.agent.Organization;
import sonet.core.agent.Person;
import sonet.core.agent.ProfileAccess;
import sonet.core.exceptions.AgentAlreadyActiveException;
import sonet.core.exceptions.AgentAlreadyInactiveException;
import sonet.core.exceptions.AgentExistsException;
import sonet.core.exceptions.ImportFileException;
import sonet.core.exceptions.InvalidIdentifierException;
import sonet.core.exceptions.PublicationExistsException;
import sonet.core.exceptions.UnknownAgentException;
import sonet.core.exceptions.UnknownAttachmentException;
import sonet.core.exceptions.UnknownDataException;
import sonet.core.exceptions.UnknownPublicationException;
import sonet.core.message.Attachment;
import sonet.core.message.Email;
import sonet.core.message.MessagingAccess;
import sonet.core.message.Recipient;
import sonet.core.message.Sender;
import sonet.core.publication.Image;
import sonet.core.publication.Publication;
import sonet.core.publication.PublicationAccess;
import sonet.core.publication.TextNote;
import sonet.core.publication.URI;
import sonet.core.visits.AgentVisitor;
import sonet.core.visits.AttachmentVisitor;
import sonet.core.visits.Selector;

/**
 * Networks have agents, connections, messages, and so on.
 */
public class Network implements Serializable {

  /** Class serial number. */
  private static final long serialVersionUID = 201109020943L;

  /** Agents. */
  private Map<Integer, Agent> _agents = new TreeMap<>();

  /** Emails. */
  private Map<Integer, Email> _emails = new TreeMap<>();

  /** Network object has been changed. */
  private boolean _changed = false;

  /** UUID used for last entity. */
  private int _uuid = 0;

  /** Shortcut to root agent. */
  private Agent _root;

  /**
   * Register root agent.
   */
  public Network() {
    // type|id-or-null|name|email|phone
    String[] fields = { "PERSON", "0", "Root", "root@network", "0" };
    try {
      registerAgent(fields);
      setChanged(false); // root registration does not count as change
      _root = fetchAgent(0);
    } catch (UnknownAgentException | AgentExistsException | UnknownDataException e) {
      // should not happen
      e.printStackTrace();
    }
  }

  /** @return the new UUID. */
  private int getUUID() {
    return ++_uuid;
  }

  /**
   * Force UUID to advance (for instance, because it has been used by an imported
   * entity).
   * 
   * @param uuid
   */
  private void setLastUUID(int uuid) {
    _uuid = uuid;
  }

  /**
   * Set changed.
   */
  public void changed() {
    setChanged(true);
  }

  /**
   * @return changed
   */
  public boolean hasChanged() {
    return _changed;
  }

  /**
   * @param changed
   */
  public void setChanged(boolean changed) {
    _changed = changed;
  }

  /**
   * @param id
   * @throws InvalidIdentifierException
   */
  public void assertValidIdentifier(int id) throws InvalidIdentifierException {
    // TODO Auto-generated method stub
  }

  /**
   * Get the agent with the given id.
   * 
   * @param id the agent's id.
   * 
   * @return the agent or null if the number does not correspond to a valid agent.
   * @throws UnknownAgentException
   */
  public Agent getAgent(int id) throws UnknownAgentException {
    return fetchAgent(id);
  }

  /**
   * @return all clients as an unmodifiable collection.
   */
  public Collection<Agent> getAgents() {
    return Collections.unmodifiableCollection(_agents.values());
  }

  /**
   * @param key
   * @param agent
   * @throws AgentExistsException
   */
  public void addAgent(int key, Agent agent) throws AgentExistsException {
    assertNewAgent(key);
    _agents.put(key, agent);
    changed();
  }

  /**
   * @param key
   * @return the agent identified by the key
   * @throws UnknownAgentException
   */
  private Agent fetchAgent(int key) throws UnknownAgentException {
    if (!_agents.containsKey(key))
      throw new UnknownAgentException(key);
    return _agents.get(key);
  }

  /**
   * @param key
   * @throws UnknownAgentException
   * @throws AgentAlreadyActiveException
   */
  public void activateAgent(int key) throws UnknownAgentException, AgentAlreadyActiveException {
    Agent agent = fetchAgent(key);
    agent.activate();
    changed();
  }

  /**
   * @param key
   * @throws UnknownAgentException
   * @throws AgentAlreadyInactiveException
   */
  public void deactivateAgent(int key) throws UnknownAgentException, AgentAlreadyInactiveException {
    Agent agent = fetchAgent(key);
    agent.deactivate();
    changed();
  }

  /**
   * @param key
   * @return the agent
   * @throws UnknownAgentException
   */
  public Agent assertAgentExists(int key) throws UnknownAgentException {
    Agent agent = _agents.get(key);
    if (agent != null)
      return agent;
    throw new UnknownAgentException(key);
  }

  /**
   * @param key
   * @throws AgentExistsException
   */
  public void assertNewAgent(int key) throws AgentExistsException {
    if (_agents.containsKey(key))
      throw new AgentExistsException(key);
  }

  /**
   * @param name
   * @throws ImportFileException
   */
  public void importFile(String name) throws ImportFileException {
    try (BufferedReader reader = new BufferedReader(new FileReader(name))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] fields = line.split("\\|");
        try {
          registerEntry(fields);
        } catch (UnknownDataException | PublicationExistsException | UnknownAgentException | AgentExistsException
            | InvalidIdentifierException e) {
          // DAVID should not happen
          e.printStackTrace();
        }
      }
    } catch (IOException e1) {
      throw new ImportFileException();
    }
  }

  /**
   * @param fields
   * @throws UnknownDataException
   * @throws UnknownAgentException
   * @throws PublicationExistsException
   * @throws AgentExistsException
   * @throws InvalidIdentifierException
   */
  public void registerEntry(String... fields) throws UnknownDataException, PublicationExistsException,
      UnknownAgentException, AgentExistsException, InvalidIdentifierException {
    switch (fields[0]) {
    case "PERSON", "ORGANIZATION" -> registerAgent(fields);
    case "MESSAGE" -> registerEmail(fields);
    case "IMAGE", "NOTE", "URI" -> registerPublication(fields);
    case "CONNECTION" -> registerConnection(fields);
    default -> throw new UnknownDataException(fields[0]);
    }
  }

  /**
   * @param fields 0 - type; 1 - id/null; 2 - agent; other - pub-specific
   * @throws PublicationExistsException we throw it because we may be called from
   *                                    outside (i.e., we are not responsible for
   *                                    communicating the problem to the end-user)
   * @throws UnknownAgentException      we throw it because we may be called from
   *                                    outside (i.e., we are not responsible for
   *                                    communicating the problem to the end-user)
   * @throws UnknownDataException       we throw it because we may be called from
   *                                    outside (i.e., we are not responsible for
   *                                    communicating the problem to the end-user)
   */
  public void registerPublication(String... fields)
      throws PublicationExistsException, UnknownAgentException, UnknownDataException {
    int pubId = (fields[1] != null) ? Integer.parseInt(fields[1]) : getUUID();

    Publication pub = switch (fields[0]) {
    case "IMAGE" -> new Image(pubId, fields[3], fields[4]);
    case "NOTE" -> new TextNote(pubId, fields[3], fields[4]);
    case "URI" -> new URI(pubId, fields[3], fields[4]);
    default -> throw new UnknownDataException(fields[0]);
    };

    Agent owner = fetchAgent(Integer.parseInt(fields[2]));
    owner.addPublication(pubId, pub);
    setLastUUID(pubId);
    changed();
  }

  /**
   * Format: type|id-or-null|name|email|phone
   * 
   * @param fields
   * @throws AgentExistsException we throw it because we may be called from
   *                              outside (i.e., we are not responsible for
   *                              communicating the problem to the end-user)
   * @throws UnknownDataException we throw it because we may be called from
   *                              outside (i.e., we are not responsible for
   *                              communicating the problem to the end-user)
   */
  public void registerAgent(String... fields) throws AgentExistsException, UnknownDataException {
    int id = (fields[1] != null) ? Integer.parseInt(fields[1]) : getUUID();

    Agent agent = switch (fields[0]) {
    case "PERSON" -> new Person(id, fields[2], fields[3], fields[4]);
    case "ORGANIZATION" -> new Organization(id, fields[2], fields[3], fields[4]);
    default -> throw new UnknownDataException(fields[0]);
    };

    addAgent(id, agent);
    setLastUUID(id);
    changed();
  }

  /**
   * This method DOES NOT check message permissions when registering messages with
   * agents inboxes/outboxes. It must be private.
   * 
   * @param fields
   * @throws UnknownAgentException
   * @throws UnknownDataException
   * @throws InvalidIdentifierException
   */
  private void registerEmail(String... fields)
      throws UnknownAgentException, UnknownDataException, InvalidIdentifierException {
    if (!fields[0].equals("MESSAGE"))
      throw new UnknownDataException(fields[0]);

    int msgId = Integer.parseInt(fields[1]);
    Sender sender = fetchAgent(Integer.parseInt(fields[2]));
    List<Recipient> recipients = new ArrayList<>();
    for (String rcptId : fields[3].split(",")) {
      Recipient recipient = fetchAgent(Integer.parseInt(rcptId.trim()));
      recipients.add(recipient);
    }
    assertValidIdentifier(msgId);
    Email email = new Email(msgId, fields[2], sender, fields[3], recipients, fields[4], fields[5], "", null);
    sendEmail(email);
    setLastUUID(msgId);
    changed();
  }

  /**
   * This method DOES NOT check permissions regarding connections. It must be
   * private.
   * 
   * @param fields
   * @throws UnknownAgentException
   * @throws NumberFormatException
   * @throws UnknownDataException
   */
  private void registerConnection(String[] fields)
      throws NumberFormatException, UnknownAgentException, UnknownDataException {
    if (!fields[0].equals("CONNECTION"))
      throw new UnknownDataException(fields[0]);

    Agent a1 = fetchAgent(Integer.parseInt(fields[1]));
    Agent a2 = fetchAgent(Integer.parseInt(fields[2]));
    boolean a1Accepting = (Integer.parseInt(fields[3]) != 0);
    boolean a2Accepting = (Integer.parseInt(fields[4]) != 0);
    a1.addConnection(new Connection(a1, a2, a1Accepting, a2Accepting)); // FIXME
    a2.addConnection(new Connection(a2, a1, a2Accepting, a1Accepting)); // FIXME
    changed();
  }

  /**
   * @return all the messages as an unmodifiable collection.
   */
  public Collection<Email> getEmails() {
    return Collections.unmodifiableCollection(_emails.values());
  }

  /**
   * FIXME ugly implementation
   * 
   * @param sender
   * @param rcpts
   * @param subject
   * @param text
   * @param attachments
   * @param preserveFirstAttachment this is true when processing a reply or a
   *                                forwarded message (note that no attempt is
   *                                made to ensure that the attachment actually
   *                                exists -- this may cause this to fail messily)
   */
  public void sendEmail(Agent sender, String rcpts, String subject, String text, String attachments,
      boolean preserveFirstAttachment) {
    debug("SENDING MESSAGE FOR AGENT " + sender.getId());

    List<Recipient> recipients = new ArrayList<>();
    List<String> inadmissibleRcpts = new ArrayList<>();
    List<String> unknownRcpts = new ArrayList<>();
    for (String rcptId : rcpts.split(",")) {
      try {
        Recipient recipient = fetchAgent(Integer.parseInt(rcptId.trim()));
        if (recipient.acceptsMessagesFrom(sender.getId()))
          recipients.add(recipient);
        else
          inadmissibleRcpts.add(rcptId);
      } catch (UnknownAgentException e) {
        unknownRcpts.add(rcptId);
      }
    }

    List<Attachment> attList = new ArrayList<>();
    List<String> inadmissibleAtts = new ArrayList<>();
    List<String> unknownAtts = new ArrayList<>();
    if (attachments != null && !attachments.trim().isEmpty()) {
      attList = new ArrayList<>();
      for (String attId : attachments.split(",")) {
        try {
          Attachment att = sender.fetchAttachment(Integer.parseInt(attId.trim()));
          // FIXME not supporting other's attachments
          attList.add(att);
          // } catch (InadmissibleAttachmentException e) {
          // inadmissibleAttachments.add(attId);
        } catch (UnknownAttachmentException e) {
          unknownAtts.add(attId);
        }

      }
    }
    Email email = new Email(getUUID(), Integer.toString(sender.getId()), //
        sender, rcpts, recipients, subject, text, attachments, attList);
    // FIXME hack (has to be at least in outbox for error handling)
    sender.addToOutbox(email);

    debug("READY TO PROCESS!!!");

    if (inadmissibleAtts.size() == 0 && unknownAtts.size() == 0) {
      debug("POTENTIALLY OK!!!");
      if (inadmissibleRcpts.size() != 0) {
        // handle recipient errors
        debug("INADMISSIBLE RECIPIENTS!!!");
        for (String rcpt : inadmissibleRcpts) {
          debug("NOTIFYING " + sender.getId() + " ON " + rcpt + "!!!");
          sendErrorMessage(Integer.toString(sender.getId()), Message.msgRejected(email.getKey()), "", email);
        }
      }
      if (unknownRcpts.size() != 0) {
        // handle recipient errors
        debug("UNKNOWN RECIPIENTS!!!");
        for (String rcpt : unknownRcpts) {
          debug("NOTIFYING " + sender.getId() + " ON " + rcpt + "!!!");
          sendErrorMessage(Integer.toString(sender.getId()), Message.unknownDestination(Integer.parseInt(rcpt)), "",
              email);
        }
      }
      debug("SENDING!!! " + email.getRecipients().size() + " " + email.getNumberOfAttachments());
      sendEmail(email);
    } else {
      debug("NOT OK!!!");
      // handle attachment errors
      email.clearAttachments(true);
      for (String att : inadmissibleAtts)
        sendErrorMessage(Integer.toString(sender.getId()), Message.couldNotAttach(Integer.parseInt(att)), "", email);
      for (String att : unknownAtts)
        sendErrorMessage(Integer.toString(sender.getId()), Message.unknownAttachment(Integer.parseInt(att)), "", email);
    }
  }

  /**
   * FIXME ugly implementation
   * 
   * @param rcpts
   * @param subject
   * @param text
   * @param orig
   */
  public void sendErrorMessage(String rcpts, String subject, String text, Email orig) {
    Email copy = new Email(orig.getKey(), orig.getFrom(), orig.getSender(), orig.getTo(), orig.getRecipients(),
        orig.getSubject(), orig.getBody(), Integer.toString(orig.getKey()), null);

    debug("SENDING MESSAGE FOR ROOT AGENT");

    List<String> unknownRcpts = new ArrayList<>();
    List<Recipient> recipients = new ArrayList<>();
    for (String rcptId : rcpts.split(",")) {
      try {
        recipients.add(fetchAgent(Integer.parseInt(rcptId.trim())));
      } catch (UnknownAgentException e) {
        unknownRcpts.add(rcptId);
      }
    }
    List<Attachment> attList = new ArrayList<>();
    attList.add(copy);

    Email email = new Email(getUUID(), "0", _root, rcpts, recipients, subject, text, Integer.toString(copy.getKey()),
        attList);

    // FIXME (not adding to root's outbox)
    // sender.addToOutbox(message);

    debug("READY TO PROCESS!!!");
    debug("POTENTIALLY OK!!!");
    if (unknownRcpts.size() != 0) {
      debug("UNKNOWN RECIPIENTS!!!");
      // handle recipient errors
      for (String rcpt : unknownRcpts) {
        debug("NOTIFYING ROOT AGENT ABOUT " + rcpt + "!!!");
        sendEmail(_root, "0", Message.unknownDestination(Integer.parseInt(rcpt)), "", Integer.toString(email.getKey()),
            true);
      }
    }
    debug("SENDING!!! " + email.getRecipients().size());
    sendEmail(email);
  }

  /**
   * Send message only to valid recipients.
   * 
   * @param message
   */
  private void sendEmail(Email message) {
    Sender sender = message.getSender();
    List<Recipient> recipients = message.getRecipients();
    sender.addToOutbox(message);
    for (Recipient recipient : recipients) {
      debug("ADDING TO INBOX OF RECIPIENT " + recipient.getId());
      recipient.addToInbox(message);
    }
  }

  /**
   * @param agent
   * @param keys
   * @param access
   * @throws UnknownAgentException
   */
  public void setAgentProfileProtection(Agent agent, String keys, ProfileAccess access) throws UnknownAgentException {
    for (String key : keys.split(",")) {
      int id = Integer.parseInt(key.trim());
      assertAgentExists(id);
      agent.setProfileProtection(id, access);
    }
    changed();
  }

  /**
   * @param agent
   * @param keys
   * @param access
   * @throws UnknownAgentException
   */
  public void setMessagingProtection(Agent agent, String keys, MessagingAccess access) throws UnknownAgentException {
    for (String key : keys.split(",")) {
      int id = Integer.parseInt(key.trim());
      assertAgentExists(id);
      agent.setMessagingProtection(id, access);
    }
    changed();
  }

  /**
   * @param agent
   * @param pid
   * @param keys
   * @param access
   * @throws UnknownAgentException
   * @throws UnknownPublicationException
   */
  public void setPublicationProtection(Agent agent, int pid, String keys, PublicationAccess access)
      throws UnknownAgentException, UnknownPublicationException {
    agent.assertPublicationExists(pid);
    for (String key : keys.split(",")) {
      int id = Integer.parseInt(key.trim());
      assertAgentExists(id);
      agent.setPublicationProtection(pid, id, access);
    }
    changed();
  }

  /**
   * @param agent
   * @param keys
   * @param access
   * @throws UnknownAgentException
   */
  public void setPublicationsProtection(Agent agent, String keys, PublicationAccess access)
      throws UnknownAgentException {
    for (String key : keys.split(",")) {
      int id = Integer.parseInt(key.trim());
      assertAgentExists(id);
      agent.setPublicationsProtection(id, access);
    }
    changed();
  }

  /**
   * @param requester
   * @param keys
   */
  public void requestConnections(Agent requester, String keys) {
    for (String key : keys.split(",")) {
      int id = Integer.parseInt(key.trim());
      try {
        Agent remote = assertAgentExists(id);
        requester.requestConnectionTo(remote);
      } catch (UnknownAgentException e) {
        try {
          sendEmail(_root, Integer.toString(requester.getId()), key, Message.unknownId(Integer.parseInt(key)), null,
              false);
        } catch (NumberFormatException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
      }
    }

  }

  /**
   * Visit selected agents. We assume that the visitor controls whether the object
   * is changed. If it does not, then changes may be lost.
   * 
   * @param selector
   * @param visitor
   */
  public void accept(Selector<Agent> selector, AgentVisitor visitor) {
    int agentCount = 0;
    int numberOfAgents = _agents.keySet().size();
    for (int aid : _agents.keySet()) {
      ++agentCount;
      Agent agent = _agents.get(aid);
      if (selector.ok(agent))
        agent.accept(visitor, agentCount == numberOfAgents);
    }
  }

  /**
   * Visit selected agents. We assume that the visitor controls whether the object
   * is changed. If it does not, then changes may be lost.
   * 
   * @param selector
   * @param visitor
   */
  public void accept(AgentVisitor visitor) {
    accept(new Selector<Agent>() {
      // empty: default
    }, visitor);
  }

  /**
   * Visit selected messages of all agents.
   * 
   * @param selector
   * @param visitor
   */
  public void acceptAllMessagesVisitor(Selector<Email> selector, AttachmentVisitor visitor) {
    int numberOfAgentsWithMessages = 0;
    for (int aid : _agents.keySet()) {
      Agent agent = _agents.get(aid);
      if (agent.getNumberOfReceivedMessages() + agent.getNumberOfSentMessages() != 0)
        numberOfAgentsWithMessages++;
    }
    int agentCount = 0;
    for (int aid : _agents.keySet()) {
      Agent agent = _agents.get(aid);
      if (agent.getNumberOfReceivedMessages() + agent.getNumberOfSentMessages() == 0)
        continue;
      ++agentCount;
      agent.acceptAllMessagesVisitor(selector, visitor, agentCount == numberOfAgentsWithMessages);
    }
  }

  public void acceptAllMessagesVisitor(AttachmentVisitor visitor) {
    acceptAllMessagesVisitor(new Selector<Email>() {
      // empty: default
    }, visitor);
  }

  /**
   * Visit selected publications.
   * 
   * @param selector
   * @param visitor
   */
  public void accept(Selector<Publication> selector, AttachmentVisitor visitor) {
    int agentCount = 0;
    for (int aid : _agents.keySet()) {
      ++agentCount;
      Agent agent = _agents.get(aid);
      agent.accept(selector, visitor);
    }
  }

  /**
   * @param accepting
   * @param keys
   * @throws UnknownAgentException
   */
  public void acceptConnections(Agent accepting, String keys) throws UnknownAgentException {
    // FIXME not fully according with §1.6
    for (String key : keys.split(",")) {
      int id = Integer.parseInt(key.trim());
      Agent remote = assertAgentExists(id);
      accepting.acceptConnection(remote);
      sendEmail(_root, Integer.toString(remote.getId()), Integer.toString(accepting.getId()),
          Message.connectionApproved(accepting.getId()), null, false);
    }
  }

  /**
   * @param msg
   */
  private void debug(String msg) {
    if (false) {
      System.out.println("*** " + msg);
    }
  }

}
