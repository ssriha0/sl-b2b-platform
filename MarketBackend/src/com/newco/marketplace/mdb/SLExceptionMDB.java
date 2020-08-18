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

public class SLExceptionMDB extends AbstractMessageDrivenBean implements MessageDrivenBean, MessageListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String BEAN_FACTORY_NAME = "SLEJB";
	private transient MessageDrivenContext mdc = null;
	private static final Logger logger = Logger.getLogger(SLExceptionMDB.class
			.getName());
	static QueueConnectionFactory  reqqcf, resqcf;
	static Queue reqDestination, resDestination;
	IFullfillmentDao fullfillmentDao;
	

	public SLExceptionMDB() {
		logger.debug("In SimpleMessageBean.SimpleMessageBean()");
	}

	@Override
	public void setMessageDrivenContext(MessageDrivenContext context) {
		logger.debug("In "
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
					//MessageProducerHelper mph = new MessageProducerHelper();
					ISpringJMSFacility mph = (ISpringJMSFacility)this.getBeanFactory().getBean("jmsFacility");
					mph.sendLocalMesage(new FullfillmentHelper().getBytesFromBytesMessage(bytesMsg),new Long(0));
				}
			}else{
				logger.info("-->>>> MDB Baddness - Sharp or Value Link is down.  Sleeping to Requeue Transaction");
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					// TODO: handle exception
				}
				logger.info("-->>>> MDB Baddness - releasing QUEUED Transaction");
				mdc.setRollbackOnly();
			}
		} catch (JMSException e) {
			//e.printStackTrace();
			mdc.setRollbackOnly();
			throw new EJBException(e.getMessage());
		} catch (Throwable te) {
			//te.printStackTrace();
			throw new EJBException(te.getMessage());
		}
	} // onMessage

	@Override
	public void ejbRemove() {
		logger.debug("In SimpleMessageBean.remove()");
	}
	@Override
	protected void onEjbCreate() {
		logger.debug("In SimpleMessageBean.onEjbCreate()");
		fullfillmentDao = (IFullfillmentDao) this.getBeanFactory().getBean("fullfillmentDao");
	}	

} // class

