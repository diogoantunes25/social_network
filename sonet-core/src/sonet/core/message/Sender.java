/** @version $Id: Sender.java,v 1.2 2011/11/27 20:11:44 david Exp $ */
/*
 * $Log: Sender.java,v $
 * Revision 1.2  2011/11/27 20:11:44  david
 * Coding spree.
 *
 * Revision 1.1  2011/11/14 18:02:06  david
 * Created package "sonet.core" and moved "sonet" contents there (except for "textui").
 *
 * Revision 1.3  2011/11/12 21:11:26  david
 * Serialization works.
 *
 * Revision 1.2  2011/10/28 13:56:53  david
 * *** empty log message ***
 *
 * Revision 1.1  2011/09/02 20:06:24  david
 * *** empty log message ***
 *
 * 
 */
package sonet.core.message;

import java.io.Serializable;

/**
 * Message sender. This interface is to be implemented by those agents willing
 * or capable of sending messages.
 */
public interface Sender extends Serializable {

  /**
   * @return the sender's id
   */
  int getId();
  
	/**
	 * @param message
	 */
	void addToOutbox(Email message);

}
