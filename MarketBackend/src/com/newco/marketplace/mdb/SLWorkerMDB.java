package com.newco.marketplace.mdb;

import javax.ejb.EJBException;
import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;

import org.apache.log4j.Logger;
import org.springframework.context.access.ContextSingletonBeanFactoryLocator;
import org.springframework.ejb.support.AbstractMessageDrivenBean;

import com.newco.marketplace.dto.vo.fullfillment.VLHeartbeatVO;
import com.newco.marketplace.persistence.iDao.ledger.IFullfillmentDao;
import com.newco.marketplace.util.FullfillmentHelper;
import com.newco.spring.jms.ISpringJMSFacility;

public class SLWorkerMDB extends AbstractMessageDrivenBean implements MessageDrivenBean, MessageListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String BEAN_FACTORY_NAME = "SLEJB";
	private transient MessageDrivenContext mdc = null;
	private static final Logger logger = Logger.getLogger(SLWorkerMDB.class
			.getName());
	static QueueConnectionFactory  reqqcf, resqcf;
	static Queue reqDestination, resDestination;
	IFullfillmentDao fullfillmentDao;
	private ISpringJMSFacility jmsFacility;
	

	public SLWorkerMDB() {
		logger.info("In SimpleMessageBean.SimpleMessageBean()");
	}

	@Override
	public void setMessageDrivenContext(MessageDrivenContext context) {
		logger.info("In "
				+ "SimpleMessageBean.setMessageDrivenContext()");
		super.setMessageDrivenContext(context);
        this.setBeanFactoryLocator(ContextSingletonBeanFactoryLocator
                .getInstance());
        this.setBeanFactoryLocatorKey(BEAN_FACTORY_NAME);		
	}

	public void onMessage(Message inMessage) {
		try {
			VLHeartbeatVO vlHeartbeatVO = fullfillmentDao.getValuelinkStatuses();
			if(null != vlHeartbeatVO.getSharpInd() && vlHeartbeatVO.getSharpInd().trim().equalsIgnoreCase("Y")
					&& null != vlHeartbeatVO.getValuelinkInd() && vlHeartbeatVO.getValuelinkInd().equalsIgnoreCase("Y")){
				if(inMessage instanceof BytesMessage){
					BytesMessage bytesMsg = (BytesMessage) inMessage;
					byte[] bytesArray = new FullfillmentHelper().getBytesFromBytesMessage(bytesMsg);
					String strArray[] = new String(bytesArray).split("queue_");
					String queueName = "";
					if(strArray!=null && strArray.length>1)	
					{
						queueName = strArray[1];
						int initialisationLength=153;
						if(bytesArray.length>160)
							initialisationLength=199;
							
						byte[] messageToBeSentBytes = new byte[initialisationLength];
						for (int count=0; count < initialisationLength; count++) {
							messageToBeSentBytes[count] = bytesArray[count];
						}
						logger.info("Worker Thread Sent Message at " + messageToBeSentBytes);
						logger.info("Worker Thread " + "queue" + queueName);
						fullfillmentDao.updateLastTransmittedTimeAndDepth("queue_"+queueName);
						jmsFacility.sendMesage(messageToBeSentBytes);
						Thread.sleep(2000);
					}
					else
					{
						mdc.setRollbackOnly();
						Thread.sleep(1000);
					}
				}
			}else{
				logger.error("-->>>> MDB Baddness - Sharp or Value Link is down.  Sleeping to Requeue Transaction");
				try {
					Thread.sleep(1000);
					mdc.setRollbackOnly();
				} catch (Exception e) {
					mdc.setRollbackOnly();
				}
				logger.error("-->>>> MDB Baddness - releasing QUEUED Transaction");
				throw new EJBException();
			}
		} catch (JMSException e) {
			mdc.setRollbackOnly();
			throw new EJBException();
		} catch (Throwable te) {
			throw new EJBException();
		}
	} // onMessage

	@Override
	public void ejbRemove() {
		logger.info("In SimpleMessageBean.remove()");
	}
	@Override
	protected void onEjbCreate() {
		logger.info("In SimpleMessageBean.onEjbCreate()");
		jmsFacility = (ISpringJMSFacility) this.getBeanFactory().getBean("jmsFacility");
		fullfillmentDao = (IFullfillmentDao) this.getBeanFactory().getBean("fullfillmentDao");
	}

	public ISpringJMSFacility getJmsFacility() {
		return jmsFacility;
	}

	public void setJmsFacility(ISpringJMSFacility jmsFacility) {
		this.jmsFacility = jmsFacility;
	}	

} // class

