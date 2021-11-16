package sonet.core;

/**
 * Messages.
 */
interface Message {

	/**
	 * @param key agent key
	 * @return "unavailable id" notice
	 */
	static String unavailableId(int key) {
		return "O agente '" + key + "' não aceita ligações.";
	}

	/**
	 * @param key agent key
	 * @return "unknown id" notice
	 */
	static String unknownId(int key) {
		return "O agente '" + key + "' é desconhecido.";
	}

	/**
	 * @param key agent key
	 * @return "connection approved" notice
	 */
	static String connectionApproved(int key) {
    return "O agente '" + key + "' aprovou o pedido de ligação.";
  }

  /**
   * @param key message key
   * @return "message rejected" notice
   */
  static String msgRejected(int key) {
    return "A mensagem '" + key + "' foi rejeitada.";
  }

  /**
   * @param key agent key
   * @return "unknown destination" notice
   */
  static String unknownDestination(int key) {
    return "O destinatário '" + key + "' é desconhecido.";
  }

  /**
   * @param key attachment key
   * @return "unable to attach" notice
   */
  static String couldNotAttach(int key) {
    return "Não foi possível anexar '" + key + "'.";
  }

  /**
   * @param key attachment key
   * @return "unknown attachment" notice
   */
  static String unknownAttachment(int key) {
    return "O anexo '" + key + "' é desconhecido.";
  }

  /**
   * @return subject prefix for reply
   */
  static String inReply() {
    return "Re: ";
  }

  /**
   * @return subject prefix for forwarded message
   */
  static String forwarded() {
    return "Fwd: ";
  }

}