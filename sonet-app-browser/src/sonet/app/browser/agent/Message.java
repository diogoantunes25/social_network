package sonet.app.browser.agent;

/**
 * Messages.
 */
public interface Message {

	/**
	 * @param agentKey
	 * @return Menu title.
	 */
	static String menuTitle(int agentKey) {
		return "Inspecção do agente " + agentKey;
	}
	
}
