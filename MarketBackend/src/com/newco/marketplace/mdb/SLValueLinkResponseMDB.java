package com.newco.marketplace.mdb;

import javax.ejb.EJBException;
import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.jms.Message;
import javax.jms.MessageListener;
import org.apache.log4j.Logger;
import org.springframework.context.access.ContextSingletonBeanFactoryLocator;
import org.springframework.ejb.support.AbstractMessageDrivenBean;

import com.newco.marketplace.business.iBusiness.ledger.IFullfillmentTransactionBO;

public class SLValueLinkResponseMDB extends AbstractMessageDrivenBean implements
		MessageDrivenBean, MessageListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String BEAN_FACTORY_NAME = "SLEJB";
	private IFullfillmentTransactionBO fullfillmentTransactionBO;
	private static final Logger logger = Logger.getLogger(SLValueLinkResponseMDB.class
			.getName());
	@Override
	public void ejbRemove() throws EJBException {
		// TODO Auto-generated method stub
	}

	@Override
	public void setMessageDrivenContext(MessageDrivenContext context)
			throws EJBException {
		super.setMessageDrivenContext(context);
        this.setBeanFactoryLocator(ContextSingletonBeanFactoryLocator
                .getInstance());
        this.setBeanFactoryLocatorKey(BEAN_FACTORY_NAME);
       
	}

	public void onMessage(Message responseMessage){
//		logger.info("Entered response EJB, message = "+responseMessage);
//		try{
//			if(responseMessage instanceof BytesMessage){
//				BytesMessage bytesMsg = (BytesMessage) responseMessage;
//			fullfillmentTransactionBO.processFullfillmentResponse(bytesMsg);
//			}
//		}catch(Exception e){
//			logger.error("Error in EJB main Exception");
//			e.printStackTrace();
//		}
	}

	@Override
	protected void onEjbCreate() {
		 try{
	            fullfillmentTransactionBO = (IFullfillmentTransactionBO) this.
	            getBeanFactory().getBean("fullfillmentTransBOResponseMDB");
            }catch(Exception e){
            	System.out.println("Problem in EJB Create Callback method");
            }
	}
	public IFullfillmentTransactionBO getFullfillmentTransactionBO() {
		return fullfillmentTransactionBO;
	}

	public void setFullfillmentTransactionBO(
			IFullfillmentTransactionBO fullfillmentTransactionBO) {
		this.fullfillmentTransactionBO = fullfillmentTransactionBO;
	}
}
