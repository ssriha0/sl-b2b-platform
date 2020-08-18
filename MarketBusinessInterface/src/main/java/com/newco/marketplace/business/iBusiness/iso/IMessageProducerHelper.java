/**
 * 
 */
package com.newco.marketplace.business.iBusiness.iso;

import javax.jms.JMSException;
import javax.naming.NamingException;

/**
 * @author schavda
 *
 */
public interface IMessageProducerHelper {

	public void sendMessageToSharp(byte[] bytesMessage) throws Exception;
	
	public void sendMessageToExceptionQueue(byte[] bytesMessage)throws JMSException, NamingException;
}
