/**
 *
 */
package com.orderinject;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.core.MessageCreator;

/**
 * @author yami
 *
 */
public class MyMessageCreator implements MessageCreator {

	/* (non-Javadoc)
	 * @see org.springframework.jms.core.MessageCreator#createMessage(javax.jms.Session)
	 */
	private String orderXML;
	private String correlationid;


	public MyMessageCreator(String orderXML, String correlationid) {
		super();
		this.orderXML = orderXML;
		this.correlationid = correlationid;
	}


	public Message createMessage(Session session) throws JMSException {
		TextMessage msg = session.createTextMessage(orderXML);
		msg.setJMSCorrelationID(correlationid);
		return msg;

	}

}
