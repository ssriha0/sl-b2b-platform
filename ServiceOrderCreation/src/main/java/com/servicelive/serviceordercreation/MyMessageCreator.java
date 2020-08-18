/**
 * 
 */
package com.servicelive.serviceordercreation;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.core.MessageCreator;

/**
 * @author himanshu
 *
 */
public class MyMessageCreator implements MessageCreator {
	/* (non-Javadoc)
	 * @see org.springframework.jms.core.MessageCreator#createMessage(javax.jms.Session)
	 */
	public Message createMessage(Session session) throws JMSException {
		TextMessage msg = session.createTextMessage(message);
		if(correlationId != null) {
			msg.setJMSCorrelationID(correlationId);
		}
		
		return msg;
	}
	public MyMessageCreator(String message, String correlationId) {
		super();
		this.message = message;
		this.correlationId = correlationId;
	}
	private String message;
	private String correlationId;
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the correlationId
	 */
	public String getCorrelationId() {
		return correlationId;
	}
	/**
	 * @param correlationId the correlationId to set
	 */
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}
	

}
