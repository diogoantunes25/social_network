package sonet.core.publication;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import sonet.core.agent.Agent;
import sonet.core.message.Attachment;

/**
 * Publication.
 */
public abstract class Publication implements Attachment {

  /** Class serial number. */
  private static final long serialVersionUID = 201109020943L;

  /** The publication's unique identifier. */
  private int _key;

  /** The publication's owner. */
  private Agent _owner;

  /** The publication's title. */
  private String _title;

  /** Publication comments. */
  private List<Comment> _comments = new ArrayList<>();

  /** Who can access the profile. */
  private Map<Integer, PublicationAccess> _protection = new TreeMap<>();

  /** Positive votes. */
  private int _positiveVotes;

  /** Negative votes. */
  private int _negativeVotes;

  /**
   * @param key   the publication's unique identifier
   * @param title the publication's title
   */
  public Publication(int key, String title) {
    _key = key;
    _title = title;
  }

  /**
   * @return the publication's unique identifier
   */
  public final int getKey() {
    return _key;
  }

  /**
   * @return the publication's title
   */
  public final String getTitle() {
    return _title;
  }

  /**
   * @param title the publication's title
   * 
   */
  public final void setTitle(String title) {
    _title = title;
  }

  /**
   * @return the publication's owner
   */
  public final Agent getOwner() {
    return _owner;
  }

  /**
   * @return the number of positive votes
   */
  public final int getPositiveVotes() {
    return _positiveVotes;
  }

  /**
   * @return the number of negative votes
   */
  public final int getNegativeVotes() {
    return _negativeVotes;
  }

  /**
   * @param agent
   * @param comment
   */
  public void addComment(Agent agent, String comment) {
    // FIXME
    _comments.add(new Comment(agent, comment));
  }

  /**
   * @return comments
   */
  public List<Comment> getComments() {
    return _comments;
  }

  /**
   * @return number of comments
   */
  public int getNumberOfComments() {
    return _comments.size();
  }

  /**
   * @param id
   * @param access
   */
  public void setProtection(int id, PublicationAccess access) {
    _protection.put(id, access);
  }

  /**
   * @param aid
   * @return access condition
   */
  public boolean grantsAccessTo(int aid) {
    return aid == 0 /* root */ || _protection.get(aid) == null || _protection.get(aid) == PublicationAccess.ENABLED;
  }

  /**
   * @param agent
   * @param rating
   */
  public void rate(Agent agent, int rating) {
    if (rating < 0)
      _negativeVotes += rating;
    else
      _positiveVotes += rating;
  }

  /**
   * @param agent
   * @param comment
   */
  public void comment(Agent agent, String comment) {
    _comments.add(new Comment(agent, comment));
  }

}
