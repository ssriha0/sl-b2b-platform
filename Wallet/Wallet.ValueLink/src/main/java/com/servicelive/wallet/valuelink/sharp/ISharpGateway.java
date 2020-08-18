package com.servicelive.wallet.valuelink.sharp;

import com.servicelive.common.exception.SLBusinessServiceException;
import javax.jms.Queue;

// TODO: Auto-generated Javadoc
/**
 * Interface ISharpGateway.
 */
public interface ISharpGateway {

	/**
	 * addDelegate.
	 * 
	 * @param delegate 
	 * 
	 * @return void
	 */
	public void addDelegate(ISharpCallback delegate);

	/**
	 * setDelegate
	 * 
	 * @param delegate
	 * 
	 * @return void
	 */
	public void setDelegate(ISharpCallback delegate);
	
	/**
	 * handleMessage.
	 * 
	 * @param message 
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	public void handleMessage(byte[] message) throws SLBusinessServiceException;

	/**
	 * sendMessage.
	 * 
	 * @param message 
	 * 
	 * @return void
	 */
	public void sendMessage(byte[] message);
	
	public Queue getReplyTo();
}