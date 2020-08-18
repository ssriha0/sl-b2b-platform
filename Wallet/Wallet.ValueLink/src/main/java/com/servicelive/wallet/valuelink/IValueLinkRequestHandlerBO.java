package com.servicelive.wallet.valuelink;

import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.wallet.valuelink.socket.SocketContainer;

// TODO: Auto-generated Javadoc
/**
 * Interface IValueLinkRequestHandlerBO.
 */
public interface IValueLinkRequestHandlerBO {

	/**
	 * createValueLinkMessages.
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	public void createValueLinkMessages() throws SLBusinessServiceException;

	/**
	 * sendValueLinkMessages.
	 * @param useIPSocket 
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	public void sendValueLinkMessages(boolean useIPSocket) throws SLBusinessServiceException;
	
	
	/**
	 * sendHeartbeatMessage
	 * @param useIPSocket 
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException
	 */
	public void sendHeartbeatMessage(boolean useIPSocket) throws SLBusinessServiceException;
	
	public void sendHeartbeatMessage(SocketContainer socket) throws SLBusinessServiceException;
}
