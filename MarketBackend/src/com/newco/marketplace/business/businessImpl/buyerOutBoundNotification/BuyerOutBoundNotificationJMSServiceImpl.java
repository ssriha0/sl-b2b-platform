package com.newco.marketplace.business.businessImpl.buyerOutBoundNotification;


import javax.jms.JMSException;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.businessImpl.buyerOutBoundNotification.mdb.BuyerJMSInvoker;
import com.newco.marketplace.buyeroutboundnotification.constatns.BuyerOutBoundConstants;
import com.newco.marketplace.buyeroutboundnotification.service.IBuyerOutBoundNotificationJMSService;
import com.newco.marketplace.buyeroutboundnotification.vo.BuyerOutboundFailOverVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
//import EDU.oswego.cs.dl.util.concurrent.CountDown;

public  class BuyerOutBoundNotificationJMSServiceImpl implements
		IBuyerOutBoundNotificationJMSService {

	// Create the logger
	private static final Logger LOGGER = Logger
			.getLogger(BuyerOutBoundNotificationJMSServiceImpl.class.getName());
	
	private BuyerJMSInvoker invokeJMS;
	
	//static CountDown done = new CountDown(1);
	
	public BuyerJMSInvoker getInvokeJMS() {
		return invokeJMS;
	}

	public void setInvokeJMS(BuyerJMSInvoker invokeJMS) {
		this.invokeJMS = invokeJMS;
	}

	public void callJMSService(BuyerOutboundFailOverVO failoverVO) throws BusinessServiceException{
		LOGGER.info("Calling the JMS webservice:: Adding ::");
		// 5.call api -set a boolean true or false based on api response
		// TODO : TO call the API - Put the message in the queue.
		// Move the response processing part to the asynchronous process
		// (queue).
		 
		try {
			if(null!=failoverVO && null!=failoverVO.getXml() && null!=failoverVO.getSeqNO()){				
				invokeJMS.sendRecvAsync(failoverVO.getXml()+BuyerOutBoundConstants.SEPERATOR+failoverVO.getSoId());
				//done.acquire();
				//invokeJMS.stop();

			}
		}catch (Exception e ){
			LOGGER.error("EXception ::"+e);
			e.printStackTrace();
		}
	}

}
