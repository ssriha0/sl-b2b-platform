package com.servicelive.wallet.valuelink.ejb;

import java.util.Iterator;
import java.util.Vector;

import javax.ejb.EJBException;
import javax.ejb.MessageDrivenContext;
import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageEOFException;

import org.apache.log4j.Logger;
import org.springframework.context.access.ContextSingletonBeanFactoryLocator;
import org.springframework.ejb.support.AbstractMessageDrivenBean;

import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.wallet.valuelink.sharp.ISharpGateway;


/**
 * Class ValueLinkResponseBean.
 */
public class ValueLinkResponseBean extends AbstractMessageDrivenBean 
	implements javax.ejb.MessageDrivenBean, javax.jms.MessageListener {

	/** logger. */
	private static final Logger logger = Logger.getLogger(ValueLinkResponseBean.class);

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;
	private static final String BEAN_FACTORY_NAME = "Wallet.ValueLink.EJB";

	/** sharpGateway. */
	private ISharpGateway sharpGateway;
	
	/* (non-Javadoc)
	 * @see org.springframework.ejb.support.AbstractMessageDrivenBean#onEjbCreate()
	 */
	@Override
	protected void onEjbCreate() {	
		this.sharpGateway = (ISharpGateway)this.getBeanFactory().getBean("sharpGateway");
	}

	@Override
	public void setMessageDrivenContext(MessageDrivenContext context)
			throws EJBException {
		super.setMessageDrivenContext(context);
        this.setBeanFactoryLocator(ContextSingletonBeanFactoryLocator
                .getInstance());
        this.setBeanFactoryLocatorKey(BEAN_FACTORY_NAME);
	}

	/* (non-Javadoc)
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	public void onMessage(Message message) {
		logger.info("Response handler MDB received a message");
		try {
			if(message instanceof BytesMessage){
				BytesMessage bytesMsg = (BytesMessage) message;	
				byte [] bytes = readBytesFromMessage( bytesMsg );
				this.sharpGateway.handleMessage( bytes );
			}
		} catch (JMSException e) {
			logger.error("Failed to retrieve message... Re-deliver.",e);
			throw new EJBException(e);
		} catch (SLBusinessServiceException e) {
			logger.error("Failed to handle message... Re-deliver.",e);
			throw new EJBException(e);
		}	
	}
	
	private byte [] readBytesFromMessage( BytesMessage msg ) throws JMSException {

		Vector<Byte> bytesVector = new Vector<Byte>();
		byte[] bytes;

		int i = 0;
		while(true){
			try{
				bytesVector.add(Byte.valueOf(msg.readByte()));
				i=i+1;
			}catch(MessageEOFException mee){
				break;
			}
		}
		
		bytes = new byte[i];
		int j = 0;
		Iterator<Byte> iter = bytesVector.iterator();
		while(iter.hasNext()){
			bytes[j] = iter.next().byteValue();
			j=j+1;
		}
		
		return bytes;
	}

}
