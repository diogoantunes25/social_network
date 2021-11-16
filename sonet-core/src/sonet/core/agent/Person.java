/** @version $Id: Person.java,v 1.3 2011/11/27 20:11:43 david Exp $ */
/*
 * $Log: Person.java,v $
 * Revision 1.3  2011/11/27 20:11:43  david
 * Coding spree.
 *
 * Revision 1.2  2011/11/15 21:33:23  david
 * *** empty log message ***
 *
 * Revision 1.1  2011/11/14 18:02:05  david
 * Created package "sonet.core" and moved "sonet" contents there (except for "textui").
 *
 * Revision 1.7  2011/11/12 21:11:26  david
 * Serialization works.
 *
 * Revision 1.6  2011/10/28 13:56:53  david
 * *** empty log message ***
 *
 * Revision 1.5  2011/10/14 08:35:41  david
 * Keys are integers.
 *
 * Revision 1.4  2011/09/23 19:49:50  david
 * *** empty log message ***
 *
 * Revision 1.3  2011/09/04 18:52:53  david
 * *** empty log message ***
 *
 * Revision 1.2  2011/09/03 22:51:24  david
 * *** empty log message ***
 *
 * Revision 1.1  2011/09/02 20:06:23  david
 * *** empty log message ***
 *
 * 
 */
package sonet.core.agent;

import sonet.core.visits.AgentVisitor;

/**
 * Information about individual agents (persons).
 */
public class Person extends Agent {
	/** Class serial number. */
	private static final long serialVersionUID = 201109020943L;

	/** This is the superuser. */
	public static Person root = new Person(0, "Root", "000000", "000000");

	/**
	 * @param id
	 * @param name
	 * @param emailAddress
	 * @param phoneNumber
	 */
	public Person(int id, String name, String emailAddress, String phoneNumber) {
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
		visitor.visitPerson(this, last);
	}

}
