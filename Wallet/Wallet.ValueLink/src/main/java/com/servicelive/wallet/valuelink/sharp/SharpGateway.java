package com.servicelive.wallet.valuelink.sharp;

import java.util.HashSet;
import java.util.Set;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate102;
import org.springframework.jms.core.MessageCreator;

import com.servicelive.common.exception.SLBusinessServiceException;

// TODO: Auto-generated Javadoc
/**
 * Class SharpGateway.
 */
public class SharpGateway implements ISharpGateway {

	/** delegates. */
	private Set<ISharpCallback> delegates;

	private JmsTemplate102 jmsTemplate;

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.valuelink.sharp.ISharpGateway#addDelegate(com.servicelive.wallet.valuelink.sharp.SharpGateway.ISharpCallback)
	 */
	public void addDelegate(ISharpCallback delegate) {

		if (delegates == null) {
			delegates = new HashSet<ISharpCallback>();
		}
		delegates.add(delegate);
	}

	public void setDelegate(ISharpCallback delegate) {
		delegates = new HashSet<ISharpCallback>();
		delegates.add(delegate);
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.valuelink.sharp.ISharpGateway#handleMessage(byte[])
	 */
	public void handleMessage(byte[] message) throws SLBusinessServiceException {

		for (ISharpCallback delegate : delegates) {
			delegate.handleMessage(message);
		}
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.valuelink.sharp.ISharpGateway#sendMessage(byte[])
	 */
	public void sendMessage(byte[] message) {

		final byte[] m = message;

		this.jmsTemplate.send(new MessageCreator() {

			public Message createMessage(Session session) throws JMSException {
				BytesMessage bm = session.createBytesMessage();
				bm.setJMSCorrelationID("");
				bm.setJMSReplyTo(replyTo);
				bm.writeBytes(m);
				return bm;
			}
		});
	}

	private Queue replyTo;

	public Queue getReplyTo() {
		return replyTo;
	}

	public void setReplyTo(Queue replyTo) {
		this.replyTo = replyTo;
	}

	public void setJmsTemplate(JmsTemplate102 jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
}
