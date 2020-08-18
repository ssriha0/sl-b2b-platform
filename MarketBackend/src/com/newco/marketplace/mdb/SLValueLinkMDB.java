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

import com.newco.marketplace.business.iBusiness.iso.IWorkerMapper;
import com.newco.marketplace.dto.vo.fullfillment.VLHeartbeatVO;
import com.newco.marketplace.persistence.iDao.ledger.IFullfillmentDao;
import com.newco.marketplace.util.FullfillmentHelper;
import com.newco.spring.jms.ISpringJMSFacility;

public class SLValueLinkMDB extends AbstractMessageDrivenBean implements MessageDrivenBean, MessageListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private transient MessageDrivenContext mdc = null;
	private static final Logger logger = Logger.getLogger(SLValueLinkMDB.class
			.getName());
	static QueueConnectionFactory  reqqcf, resqcf;
	private static final String BEAN_FACTORY_NAME = "SLEJB";
	static Queue reqDestination, resDestination;
	private ISpringJMSFacility jmsFacility;
	IFullfillmentDao fullfillmentDao;
	IWorkerMapper workerMapper;
	byte[] bytes = null;
	

	public SLValueLinkMDB() {
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
				if(inMessage instanceof BytesMessage){
					BytesMessage bMsg = (BytesMessage) inMessage;
						try{
							//If Sharp / VL is down we do not send the messages to the worker queue itself. This is to fix the 
							//messages order mix up that occurs when we send out messages after a prolonged period of VL outage.
							if(null != vlHeartbeatVO.getSharpInd() && vlHeartbeatVO.getSharpInd().trim().equalsIgnoreCase("Y")
									&& null != vlHeartbeatVO.getValuelinkInd() && vlHeartbeatVO.getValuelinkInd().equalsIgnoreCase("Y")){
								byte[] bytesArray = new FullfillmentHelper().getBytesFromBytesMessage(bMsg);
								String inputByteMessage1 = new String(bytesArray);
								if(inputByteMessage1.indexOf("ACCTNO") != -1)
								{
							        String pan = "";
							        String strArray[] = new String(bytesArray).split("ACCTNO");
							        if(strArray!=null && strArray.length>1)
							        {
							        	logger.info("StrArray[0]" + new String(strArray[0]));
							        	logger.info("StrArray[1]"+ new String(strArray[1]));
							        	pan = strArray[1];
							        }
									int initialisationLength=153;
									if(bytesArray.length>=221)
										initialisationLength=199;
									byte[] sentByteMessage = new byte[initialisationLength];
									for(int count = 0; count < initialisationLength; count++)
									{
										sentByteMessage[count] = bytesArray[count];
									}
									logger.info("Sent mesage in bytes transformation" + sentByteMessage.toString());
									String queueName = workerMapper.findNextAvailableQueue(Long.parseLong(pan));
									logger.info("Sent " + new String(sentByteMessage)+ "Message to worker thread " + queueName);
									if(queueName!=null)
									{
										jmsFacility.sendWorkerMesage(sentByteMessage,queueName);
										fullfillmentDao.updateLastPutTimeAndDepth(queueName,pan);
									}
									else
									{
										mdc.setRollbackOnly();
										Thread.sleep(2000);
										throw new EJBException();
									}
								}
								else
								{
									//To send directly to the SLRequestQueue if Pan number is not present.ie: it is an activation reload / balance inquiry
									jmsFacility.sendMesage(bytesArray);
								}
							}
							else
							{
								mdc.setRollbackOnly();
								Thread.sleep(2000);
								throw new EJBException();
							}
							}catch(JMSException jmse){
								logger.error("Error occured in SLValueLinkMDB --------->On extract byte message");
								throw jmse;
							}
					}
		}
		catch (Exception e) {
			mdc.setRollbackOnly();
			throw new EJBException();
		} 
	} // onMessage
	@Override
	public void ejbRemove() {
		logger.info("In SimpleMessageBean.remove()");
	}
	@Override
	protected void onEjbCreate() {
		logger.info("In ValueLinkMessageBean.onEjbCreate()");
		fullfillmentDao = (IFullfillmentDao) this.getBeanFactory().getBean("fullfillmentDao");
		workerMapper = (IWorkerMapper) this.getBeanFactory().getBean("workerMapper");
		jmsFacility = (ISpringJMSFacility) this.getBeanFactory().getBean("jmsFacility");
	}
	
	public ISpringJMSFacility getJmsFacility() {
		return jmsFacility;
	}

	public void setJmsFacility(ISpringJMSFacility jmsFacility) {
		this.jmsFacility = jmsFacility;
	}

	public IFullfillmentDao getFullfillmentDao() {
		return fullfillmentDao;
	}

	public void setFullfillmentDao(IFullfillmentDao fullfillmentDao) {
		this.fullfillmentDao = fullfillmentDao;
	}

	public IWorkerMapper getWorkerMapper() {
		return workerMapper;
	}

	public void setWorkerMapper(IWorkerMapper workerMapper) {
		this.workerMapper = workerMapper;
	}	
	
	public static void main(String[] args){
		byte[] bytesArrayOrig = new byte[]{2, 73, 83, 79, 71, 67, 81, 49, 50, 48, 48, 112, 48, 4, 0, 8, -127, 0, 2, 49, 54, 55, 55, 55, 55, 48, 48, 56, 50, 52, 49, 54, 52, 56, 54, 55, 50, 49, 53, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 49, 48, 48, 48, 49, 56, 55, 56, 55, 48, 57, 48, 50, 50, 52, 49, 52, 53, 55, 51, 49, 50, 48, 48, 49, 48, 49, 49, 48, 48, 49, 52, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 57, 57, 51, 48, 48, 48, 48, 48, 48, 54, 48, 48, 48, 48, 83, 77, 48, 51, 53, 70, 71, 48, 48, 48, 48, 48, 48, 53, 49, 49, 53, 52, 51, 54, 53, 50, 76, 65, 48, 48, 48, 48, 48, 48, 48, 53, 49, 49, 53, 57, 52, 49, 49, 52, 3, 65, 67, 67, 84, 78, 79, 55, 55, 55, 55, 48, 48, 56, 50, 52, 49, 54, 52, 56, 54, 55, 50};
		byte[] bytesArrayNew = new byte[] {2, 73, 83, 79, 71, 67, 81, 49, 50, 48, 48, 112, 48, 4, 0, 8, -17, -65, -67, 0, 2, 49, 54, 55, 55, 55, 55, 48, 48, 56, 50, 52, 49, 54, 52, 56, 54, 55, 50, 49, 53, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 49, 48, 48, 48, 49, 56, 55, 56, 55, 48, 57, 48, 50, 50, 52, 49, 52, 53, 55, 51, 49, 50, 48, 48, 49, 48, 49, 49, 48, 48, 49, 52, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 57, 57, 51, 48, 48, 48, 48, 48, 48, 54, 48, 48, 48, 48, 83, 77, 48, 51, 53, 70, 71, 48, 48, 48, 48, 48, 48, 53, 49, 49, 53, 52, 51, 54, 53, 50, 76, 65, 48, 48, 48, 48, 48, 48, 48, 53, 49, 49, 53, 57, 52, 49, 49, 52, 3};
		byte[] bytesArrayNew1 = new byte[] {-17,-65, -67};
		byte[] bytesArrayNew2 = new byte[] {-127};
		System.out.println("Orignanl" + bytesArrayNew1.toString());
		System.out.println("Orignanl" + bytesArrayNew2.toString());
		/*for (byte b : bytesArrayOrig) {
			Byte bOld = new Byte(b);
			System.out.print(new String(bytesArrayNew));
			
		}
		System.out.println("\nNew:");
		for (byte bn : bytesArrayNew) {
			Byte bNew = new Byte(bn);
			System.out.print(bNew.toString());
		}*/

	}
	

} // class

