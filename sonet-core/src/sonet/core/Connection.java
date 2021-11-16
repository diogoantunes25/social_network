package sonet.core;

import java.io.Serializable;

import sonet.core.agent.Agent;
import sonet.core.visits.ConnectionVisitor;

/**
 * Connection between agents.
 */
public class Connection implements Serializable {
  
	/** Class serial number. */
	private static final long serialVersionUID = 201109020943L;

	/** This end. */
	private Agent _self;
	
	/** The other end. */
	private Agent _other;

	/** Access granted? */
	private boolean _selfAccepting;
	
	/** Access granted? */
	private boolean _otherAccepting;

	/**
	 * @param self 
	 * @param other 
	 * @param selfAccepting 
	 * @param otherAccepting 
	 */
	public Connection(Agent self, Agent other, boolean selfAccepting, boolean otherAccepting) {
		_self = self;
		_other = other;
		_selfAccepting = selfAccepting;
		_otherAccepting = otherAccepting;
	}

	/**
	 * @return the other agent.
	 */
	public final Agent getSelf() {
		return _self;
	}

	/**
	 * @return the other agent.
	 */
	public final Agent getOther() {
		return _other;
	}

	/**
	 * @return connection accepted on our end?
	 */
	public final boolean isSelfAccepting() {
		return _selfAccepting;
	}

	/**
	 * @return connection accepted on our end?
	 */
	public final boolean isOtherAccepting() {
		return _otherAccepting;
	}
	
	/**
	 * 
	 */
	public final void setSelfAccepting() {
		_selfAccepting = true;
	}

	/**
	 * 
	 */
	public final void setOtherAccepting() {
		_otherAccepting = true;
	}
	
	/**
	 * @param visitor
	 * @param last
	 */
	public void accept(ConnectionVisitor visitor, boolean last) {
		visitor.visitConnection(this, last);
	}

}
