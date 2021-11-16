/** @version $Id: Organization.java,v 1.8 2011/12/11 17:35:52 david Exp $ */
/*
 * $Log: Organization.java,v $
 * Revision 1.8  2011/12/11 17:35:52  david
 * *** empty log message ***
 *
 * Revision 1.7  2011/12/11 16:52:29  david
 * *** empty log message ***
 *
 * Revision 1.6  2011/12/11 16:34:25  david
 * *** empty log message ***
 *
 * Revision 1.5  2011/11/29 10:37:53  david
 * *** empty log message ***
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
 * Revision 1.6  2011/11/12 21:11:26  david
 * Serialization works.
 *
 * Revision 1.5  2011/10/28 13:56:53  david
 * *** empty log message ***
 *
 * Revision 1.4  2011/10/14 08:35:41  david
 * Keys are integers.
 *
 * Revision 1.3  2011/09/23 19:49:50  david
 * *** empty log message ***
 *
 * Revision 1.2  2011/09/04 18:52:53  david
 * *** empty log message ***
 *
 * Revision 1.1  2011/09/02 20:06:23  david
 * *** empty log message ***
 *
 * 
 */
package sonet.core.agent;

import sonet.core.Connection;
import sonet.core.visits.AgentVisitor;

/**
 * Information about individual agents (persons).
 */
public class Organization extends Agent {
	/** Class serial number. */
	private static final long serialVersionUID = 201109020943L;

	/**
	 * @param id
	 * @param name
	 * @param emailAddress
	 * @param phoneNumber
	 */
	public Organization(int id, String name, String emailAddress, String phoneNumber) {
		super(id, name, emailAddress, phoneNumber);
	}

	/**
	 * Render agent. The visitor is expected to store the result.
	 * 
	 * @param visitor
	 * @param last
	 */
	@Override
	public void accept(AgentVisitor visitor, boolean last) {
		visitor.visitOrganization(this, last);
	}

	/**
	 * @see sonet.core.agent.Agent#processIncomingConnectionFrom(sonet.core.agent.Agent)
	 */
	@Override
	public void processIncomingConnectionFrom(Agent remote) {
		remote.addConnection(new Connection(remote, this, true, true));
		addConnection(new Connection(this, remote, true, true));
	}
	
}
