package sonet.core.visits;

import sonet.core.agent.Organization;
import sonet.core.agent.Person;

/**
 * Render agents to be shown.
 */
public interface AgentVisitor {

	/**
	 * @param agent
	 * @param last 
	 */
	void visitPerson(Person agent, boolean last);

	/**
	 * @param agent
	 * @param last 
	 */
	void visitOrganization(Organization agent, boolean last);
	
}
