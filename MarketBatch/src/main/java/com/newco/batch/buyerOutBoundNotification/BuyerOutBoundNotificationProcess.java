package com.newco.batch.buyerOutBoundNotification;

import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.buyeroutboundnotification.beans.RequestHeader;
import com.newco.marketplace.buyeroutboundnotification.beans.RequestJobcode;
import com.newco.marketplace.buyeroutboundnotification.beans.RequestMsgBody;
import com.newco.marketplace.buyeroutboundnotification.beans.RequestOrder;
import com.newco.marketplace.buyeroutboundnotification.beans.RequestOrders;
import com.newco.marketplace.buyeroutboundnotification.beans.RequestReschedInformation;
import com.newco.marketplace.buyeroutboundnotification.beans.RequestRescheduleInfo;
import com.newco.marketplace.buyeroutboundnotification.service.IBuyerOutBoundAPIService;
import com.newco.marketplace.buyeroutboundnotification.service.IBuyerOutBoundNotificationService;
import com.newco.marketplace.buyeroutboundnotification.vo.BuyerNotificationStatusEnum;
import com.newco.marketplace.buyeroutboundnotification.vo.BuyerOutboundFailOverVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.MPConstants;
import com.thoughtworks.xstream.XStream;

/**
 * Batch for sending notification to buyer through webservice regarding updates done to the SO
 * @author Infosys
 */  

public class BuyerOutBoundNotificationProcess {
	
	private IBuyerOutBoundNotificationService buyerOutBoundNotificationService;
	private static final Logger logger = Logger.getLogger(BuyerOutBoundNotificationProcess.class);
	private IBuyerOutBoundAPIService buyerOutBoundAPIService;
	

	/**
	 * Process the record picked by the batch
	 */
	public void process() throws BusinessServiceException {
		try {
			List<BuyerOutboundFailOverVO> failOverList = buyerOutBoundNotificationService
					.fetchRecords();

			for (BuyerOutboundFailOverVO vo : failOverList) {
				// TODO Call web service .If the call is successful, set success
				// as true else set it as false
				XStream xstream = new XStream();
				Class[] classes = new Class[] { RequestMsgBody.class,
						RequestHeader.class, RequestOrders.class,
						RequestOrder.class, RequestReschedInformation.class,
						RequestJobcode.class, RequestRescheduleInfo.class };
				xstream.processAnnotations(classes);
				RequestMsgBody request = (RequestMsgBody) xstream.fromXML(vo.getXml());
				BuyerOutboundFailOverVO failOverVO = buyerOutBoundAPIService.callAPIService(vo.getXml(),request, vo.getSoId(),vo);
			}
		}

		catch (BusinessServiceException bse) {
			logger.error("Caught Exception", bse);
		}
	}
	
	/**
	 * Get buyerOutBoundNotificationService
	 * 
	 * @return IBuyerOutBoundNotificationService
	 */
	public IBuyerOutBoundNotificationService getBuyerOutBoundNotificationService() {
		return buyerOutBoundNotificationService;
	}
	
	/**
	 * Set buyerOutBoundNotificationService
	 * @param buyerOutBoundNotificationService
	 */
	public void setBuyerOutBoundNotificationService(
			IBuyerOutBoundNotificationService buyerOutBoundNotificationService) {
		this.buyerOutBoundNotificationService = buyerOutBoundNotificationService;
	}

	public IBuyerOutBoundAPIService getBuyerOutBoundAPIService() {
		return buyerOutBoundAPIService;
	}

	public void setBuyerOutBoundAPIService(
			IBuyerOutBoundAPIService buyerOutBoundAPIService) {
		this.buyerOutBoundAPIService = buyerOutBoundAPIService;
	}
	
	

}
