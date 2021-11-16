package sonet.app.manager.visitors;

import sonet.core.agent.Agent;
import sonet.core.agent.Organization;
import sonet.core.agent.Person;
import sonet.core.visits.AgentVisitor;

/**
 * 
 */
public class RenderAgents implements AgentVisitor {

  /** Rendered agent. */
  private String _rendering = ""; //$NON-NLS-1$

  /**
   * @param agent
   * @return common fields.
   */
  private String renderFields(Agent agent) {
    return agent.getId() + "|" + agent.getName() + "|" + agent.getEmailAddress() + "|" + agent.getNumberOfPublications()
        + "|" + agent.getNumberOfReceivedMessages() + "|" + agent.getNumberOfSentMessages() + "|"
        + agent.getNumberOfConnections() + "|" + (agent.isActive() ? Message.agentActive() : Message.agentInactive());
  }

  /**
   * @see sonet.core.visits.AgentVisitor#visitPerson(sonet.core.agent.Person,
   *      boolean)
   */
  @Override
  public void visitPerson(Person agent, boolean last) {
    // do not show the root agent.
    if (agent.getId() == 0)
      return;
    _rendering += Message.typePerson() + "|" + renderFields(agent) + (last ? "" : "\n");
  }

  /**
   * @see sonet.core.visits.AgentVisitor#visitOrganization(sonet.core.agent.Organization,
   *      boolean)
   */
  @Override
  public void visitOrganization(Organization agent, boolean last) {
    _rendering += Message.typeOrganization() + "|" + renderFields(agent) + (last ? "" : "\n");
  }

  /** @see java.lang.Object#toString() */
  @Override
  public String toString() {
    return _rendering;
  }

}
