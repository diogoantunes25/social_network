package sonet.app.browser.agent;

/**
 * Menu entries.
 */
interface Label {

  /* Menu title. NOT USED! Use Messages.menuTitle() */
  // String TITLE = "Menu de Inspecção";

  /** Show agent properties. */
  String SHOW_PROFILE = "Visualizar propriedades";

  /** Edit agent profile. */
  String EDIT_PROFILE = "Editar perfil do agente";

  /** Protect agent profile. */
  String PROTECT_PROFILE = "Proteger perfil do agente";

  /** Unprotect agent profile. */
  String UNPROTECT_PROFILE = "Desproteger perfil do agente";

  /** Open publications menu. */
  String OPEN_MENU_PUBLICATIONS = "Menu de publicações";

  /** Open messages menu. */
  String OPEN_MENU_MESSAGES = "Menu de mensagens";

  /** Open connections menu. */
  String OPEN_MENU_CONNECTIONS = "Menu de ligações";

}