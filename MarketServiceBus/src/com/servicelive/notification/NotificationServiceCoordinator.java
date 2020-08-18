package com.servicelive.notification;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.inhomeoutbound.constants.InHomeNPSConstants;
import com.newco.marketplace.buyeroutboundnotification.constatns.BuyerOutBoundConstants;
import com.newco.marketplace.leadoutboundnotification.vo.LeadNotificationStatusEnum;
import com.servicelive.notification.beans.OrderUpdateRequest;
import com.servicelive.notification.beans.RequestInHomeMessageDetails;
import com.servicelive.notification.bo.INotificationBO;
import com.servicelive.notification.mapper.NotificationServiceMapper;
import com.servicelive.notification.mapper.NotificationServiceValidator;
import com.servicelive.notification.vo.OutboundNotificationVO;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.thoughtworks.xstream.XStream;

/**
 * 
 * @author Infosys
 * Used for creating In Home out-bound notifications
 * for status changes and Closure  
 */

public class NotificationServiceCoordinator implements INotificationServiceCoordinator {

	private static final Logger LOGGER = Logger.getLogger(NotificationServiceCoordinator.class);
	private INotificationBO notificationBO;
	//for mapping the request details
	private NotificationServiceMapper notificationMapper;
	//for validating request details
	private NotificationServiceValidator notificationValidator;
	
	
    /**
	 * This method gets triggered for Sears HSR Service Order moving to
	 * CLOSED. This method is triggered
	 * from recordBatch() in SearsHSROrderAcceptedEventHandler
	 * 
	 * @param ServiceOrder
	 */
	public void insertInHomeOutBoundNotification(ServiceOrder serviceOrder) {

		LOGGER.info("Entering insertInHomeOutBoundNotification");
		try{
			// check if the out-bound application flag is enabled
			if (InHomeNPSConstants.ON.equalsIgnoreCase(notificationBO.getOutBoundFlag())) {
				
				//check the nps_inactive_indicator for the SO
				//Integer inactiveInd = notificationBO.getNPSInactiveInd(serviceOrder.getSoId());
				
				OrderUpdateRequest inHomeDetails = new OrderUpdateRequest();
				String result = "";
					
				if(null != serviceOrder){	
					String serialNumberFlag=notificationBO.getSerialNumberFlag();
					LOGGER.info("serialNumberFlag:"+serialNumberFlag);
					inHomeDetails = notificationMapper.mapInHomeDetails(inHomeDetails, serviceOrder,serialNumberFlag);						
					//validation for mandatory fields
					if(null != inHomeDetails){
						result = notificationValidator.validateInHomeDetails(inHomeDetails,serialNumberFlag);
					}
					else{
						// no service order data available
						result = InHomeNPSConstants.NO_DATA_MESSAGE;
					}
				}
				else{
					// no service order data available
					result = InHomeNPSConstants.NO_DATA_MESSAGE;
				}
				/*if(null != inactiveInd && 1 == inactiveInd.intValue()){
					result = InHomeNPSConstants.INACTIVE;
				}*/
					
				//setting notification object
				OutboundNotificationVO outBoundNotificationVO = populateNotification(result, inHomeDetails);
										
				// insert the details to buyer outbound notification table
				if(null != outBoundNotificationVO){
					notificationBO.insertInHomeOutBoundNotification(outBoundNotificationVO);
				}
			}
		}catch (Exception dse) {
			LOGGER.error(
					"Exception in NotificationServiceCoordinator.insertInHomeOutBoundNotification() "
							+ dse.getMessage(), dse);
		} 
	}

	
	//setting the values to be inserted to buyer_outbound_notification table 
	private OutboundNotificationVO populateNotification(String result, OrderUpdateRequest inHomeDetails) {
		
		LOGGER.info("Inside populateNotification");
		//generating the request xml as string
		XStream xstream = new XStream();
		xstream.processAnnotations(OrderUpdateRequest.class);
		String requestXml = (String) xstream.toXML(inHomeDetails);
		
		//setting notification object
		OutboundNotificationVO outBoundNotificationVO = new OutboundNotificationVO();
		outBoundNotificationVO.setSeqNo(inHomeDetails.getCorrelationId());
		outBoundNotificationVO.setSoId(inHomeDetails.getSoId());
		// Service Id, defaulted to '2'
		outBoundNotificationVO.setServiceId(InHomeNPSConstants.IN_HOME_SERVICE_ID);
		outBoundNotificationVO.setXml(requestXml);
		// retry count, defaulted to '-1'
		outBoundNotificationVO.setNoOfRetries(InHomeNPSConstants.IN_HOME_RETRY_COUNT);
		outBoundNotificationVO.setCreatedDate(new Date());
		outBoundNotificationVO.setModifiedDate(new Date());
		outBoundNotificationVO.setActiveInd(0);
		if (InHomeNPSConstants.VALIDATION_SUCCESS.equalsIgnoreCase(result)) {
			// no validation error
			outBoundNotificationVO.setStatus(LeadNotificationStatusEnum.WAITING.name());
		}
		else if(InHomeNPSConstants.INACTIVE.equalsIgnoreCase(result)){
			//if SO is inactive
			outBoundNotificationVO.setStatus(LeadNotificationStatusEnum.EXCLUDED.name());
			outBoundNotificationVO.setException(result);
		}
		else {
			// Changes starts for SL-20602
			String temp = result;
			temp = temp.replaceAll(",", "");
			
			/*boolean exclude = true;
			String searchString = "Missing Information :callCd partCoverageCode for Part 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 | Invalid Information :jobCoverageCd : Not in valid set ";
			String[] words = temp.split("\\s+"); // splits by whitespace
			for (String word : words) {
			    if (!searchString.contains(word))
			    	exclude = false;
			}*/
			
			
			//SL-21246: Code Change starts
			
			boolean exclude = false;
            
            List <String> errors = notificationBO.getErrorList();
            
            String[] array = new String[errors.size()];
            
            for( int i = 0; i < errors.size(); i++ ){
              array[i] = errors.get(i).toString().replaceAll(",", "");
            }
            
            if((Arrays.asList(array).contains(temp))){
                  exclude = true;
            }
            
           //Code Change ends
            
			
			if (exclude){
				outBoundNotificationVO.setStatus(LeadNotificationStatusEnum.EXCLUDED.name());
				outBoundNotificationVO.setException(result);
			}
			else {
				// validation error
				outBoundNotificationVO.setStatus(LeadNotificationStatusEnum.ERROR.name());
				outBoundNotificationVO.setException(result);
			}
			//Changes ends for SL-20602
		}
		return outBoundNotificationVO;
	}
	

	/**
	 * This method gets triggered for Sears HSR Service Order moving from
	 * one status to another
	 * This method is triggered
	 * from recordBatch() in SearsHSROrderAcceptedEventHandler 
	 * @param ServiceOrder
	 */
	public void insertRecordForStatusChange(ServiceOrder serviceOrder,String orderEventName) {
		Integer npsInactiveInd = 1;
		String outboundFlag = "";
		String message = "";
		String result = "";
		try {
			outboundFlag = notificationBO.getOutBoundFlag();
			LOGGER.info("Outbound Flag Value:" + outboundFlag);
		} catch (Exception e) {
			LOGGER.error("Exception in getting outbound flag" + e.getMessage());
		}
		if (InHomeNPSConstants.OUTBOUND_FLAG_ON.equals(outboundFlag)) {
			LOGGER.info("Service OrderId:" + serviceOrder.getSoId());
			try {
				npsInactiveInd = notificationBO.getNPSInactiveInd(serviceOrder.getSoId());
				LOGGER.info("npsInactiveInd:" + npsInactiveInd);
			} catch (Exception e) {
				LOGGER.error("Exception in getting NPS InactiveInd"+ e.getMessage());
			}

			LOGGER.info("Service Order WfStatus value:"+ serviceOrder.getWfStateId());
			try {
				message = notificationBO.getMessageForStatus(serviceOrder.getWfStateId());
				LOGGER.info("Message retrieved:" + message);
			} catch (Exception e) {
				LOGGER.error("Exception in getting message" + e.getMessage());

			}
		}
		if (InHomeNPSConstants.NPS_INACTIVE_IND.equals(npsInactiveInd) && InHomeNPSConstants.OUTBOUND_FLAG_ON.equals(outboundFlag)) {
			RequestInHomeMessageDetails requestDetails = new RequestInHomeMessageDetails();
			OutboundNotificationVO notificationVO = new OutboundNotificationVO();
			requestDetails = notificationMapper.mapInHomeDetails(message, serviceOrder);
			result = notificationValidator.validateDetails(requestDetails);
			notificationVO = createRequestXML(serviceOrder.getSoId(),requestDetails, notificationVO, result);
			try {
				notificationBO.insertInHomeOutBoundNotification(notificationVO);
			} catch (Exception e) {
				LOGGER.error("Exception in inserting values into notification table"+ e.getMessage());
			}
		}
	}


	/** Description:creating outbound vo for insertion
	 * @param soId
	 * @param requestDetails
	 * @param notificationVO
	 * @param result
	 * @return
	 */
	private OutboundNotificationVO createRequestXML(String soId, RequestInHomeMessageDetails requestDetails,OutboundNotificationVO notificationVO, String result){
		XStream xstream = new XStream();
		String seqNo = getCorrelationId();
		requestDetails.setCorrelationId(seqNo);
		xstream.processAnnotations(RequestInHomeMessageDetails.class);
		String createRequestXml = (String) xstream.toXML(requestDetails);
		notificationVO.setSeqNo(seqNo);
		notificationVO.setSoId(soId);
		notificationVO.setXml(createRequestXml);
		notificationVO.setCreatedDate(new Date());
		notificationVO.setModifiedDate(new Date());
		notificationVO.setNoOfRetries(InHomeNPSConstants.NO_OF_RETRIES);
		notificationVO.setServiceId(InHomeNPSConstants.SUBSTATUS_SERVICE_ID_INT);
		notificationVO.setActiveInd(InHomeNPSConstants.DEFAULT_ACTIVE_IND);
		if(result.equals(InHomeNPSConstants.VALIDATION_SUCCESS)){
			notificationVO.	setStatus(LeadNotificationStatusEnum.WAITING.name());
			notificationVO.setException(null);
		}
		else{
			notificationVO.setStatus(LeadNotificationStatusEnum.ERROR.name());
			notificationVO.setException(result);
		}
		return notificationVO;
    }
   /**Description:This method will create seq No with out hyphen 
	* which is to be used as correlation No for web service 
	* @return
	*/
	private String getCorrelationId() {
		String seqNo = "";
		try {
			seqNo = notificationBO.getCorrelationId();
			int length = 8 - seqNo.length();
			for (int i = 0; i < length; i++) {
				seqNo = BuyerOutBoundConstants.SEQ_ZERO + seqNo;
			}

		} catch (Exception e) {
			LOGGER.error(
					"Exception in NotificationServiceCoordinator.getSequenceNo() "
							+ e.getMessage(), e);
		}
		return seqNo;
	}

	public INotificationBO getNotificationBO() {
		return notificationBO;
	}

	public void setNotificationBO(INotificationBO notificationBO) {
		this.notificationBO = notificationBO;
	}

	public NotificationServiceMapper getNotificationMapper() {
		return notificationMapper;
	}

	public void setNotificationMapper(
			NotificationServiceMapper notificationMapper) {
		this.notificationMapper = notificationMapper;
	}

	public NotificationServiceValidator getNotificationValidator() {
		return notificationValidator;
	}

	public void setNotificationValidator(
			NotificationServiceValidator notificationValidator) {
		this.notificationValidator = notificationValidator;
	}

}