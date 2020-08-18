package com.servicelive.esb.actions;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Body;
import org.jboss.soa.esb.message.Message;

import com.newco.marketplace.translator.business.IStagingService;
import com.newco.marketplace.translator.util.SpringUtil;
import com.newco.marketplace.webservices.dao.LuAuditMessages;
import com.newco.marketplace.webservices.dao.NpsAuditOrderMessages;
import com.newco.marketplace.webservices.dao.ShcOrder;
import com.newco.marketplace.webservices.sei.INPSAuditStagingService;
import com.servicelive.esb.constant.MarketESBConstant;
import com.servicelive.esb.dto.NPSAuditOrder;
import com.servicelive.esb.dto.NPSAuditOrdersInfo;
import com.servicelive.esb.dto.NPSAuditRecord;
import com.servicelive.esb.dto.NPSServiceAudit;
import com.servicelive.esb.service.ExceptionHandler;

public class NPSAuditResponseMapperAction extends AbstractEsbSpringAction{

	
	private Logger logger = Logger.getLogger(NPSAuditResponseMapperAction.class);
	private INPSAuditStagingService auditStagingService= null;
	private IStagingService stagingService= null;
	
	public Message mapAuditFields(Message message){
		
		logger.info("in NPSAuditResponseMapperAction ..");
		NPSServiceAudit	serviceAudit = null;
		NPSAuditOrdersInfo auditorderInfo = null;
		Body body = message.getBody();
		
		//Capture the input file feed name
		Object fileFeedPropertyValue = message.getProperties().getProperty(MarketESBConstant.ORIGINAL_FILE_FEED_NAME);
		String inputFilefeedName = String.valueOf(fileFeedPropertyValue == null ? "" : fileFeedPropertyValue);
		//logger.info("*********** Processing the file feed : \""+inputFilefeedName+"\"");
		String client = (String) body.get(MarketESBConstant.CLIENT_KEY);
		
		try{
			if(message.getBody().get(MarketESBConstant.UNMARSHALLED_NPS_AUDIT_OBJ) != null){
			
			serviceAudit = (NPSServiceAudit)message.getBody().get(MarketESBConstant.UNMARSHALLED_NPS_AUDIT_OBJ);
			// get the related staged data
			auditorderInfo = getStagedData(serviceAudit, client, inputFilefeedName, message);
		}
			message.getBody().add(MarketESBConstant.UNMARSHALLED_NPS_AUDIT_OBJ,auditorderInfo);
		
		}catch(Exception e) {
			ExceptionHandler.handle(client, new String((byte[]) body.get()), 
					inputFilefeedName,	"Erro occured in mapAuditFields", 
					message.getBody().get(MarketESBConstant.UNMARSHALLED_OBJ_GRAPH));
		} 
		return message;
		
	}
	

	private NPSAuditOrdersInfo getStagedData(NPSServiceAudit serviceAudit, String client, String inputFilefeedName, Message message) throws Exception{
		stagingService = (IStagingService) SpringUtil.factory.getBean(MarketESBConstant.SL_STAGING_SERVICE);
		
		String orderNo = "";
		String unitNo = "";
		String missingOrdersErrMsg = "Followings orders are missing in shc_order ";
		String missingMessagesErrMsg = "";
		Body body = message.getBody();
		boolean missingOrders = false;
		List<NPSAuditRecord> npsAuditRecordList = serviceAudit.getAuditRecords();
		NPSAuditOrdersInfo npsAuditOrdersInfo = new NPSAuditOrdersInfo();
		List<NPSAuditOrder> npsAuditOrdersList = new ArrayList<NPSAuditOrder>();
		NPSAuditOrder npsAuditOrder = null;
		
		if(npsAuditRecordList!= null){
			for(NPSAuditRecord auditRecord : npsAuditRecordList){
				orderNo = auditRecord.getServiceOrderNumber();
				unitNo = auditRecord.getServiceUnitNumber();
				ShcOrder shcOrder = stagingService.getShcOrder(orderNo, unitNo);
				if (shcOrder == null){
					missingOrdersErrMsg = missingOrdersErrMsg + " orderNo-- " + orderNo + " unitNo-- " + unitNo + "***" ; 
					missingOrders = true;
					continue;
				}
				
				npsAuditOrder = new NPSAuditOrder();
						
				npsAuditOrder.setNpsStatus(shcOrder.getNpsStatus());
				if(auditRecord.getProcessId()!= null){
					npsAuditOrder.setProcessId(new Integer(auditRecord.getProcessId()));
				}
				if(auditRecord.getReturnCode()!= null){
					npsAuditOrder.setReturnCode(new Integer(auditRecord.getReturnCode()));
				}
				npsAuditOrder.setSalesCheck(auditRecord.getNpsSalesCheck());
				npsAuditOrder.setServiceOrderNumber(auditRecord.getServiceOrderNumber());
				npsAuditOrder.setServiceUnitNumber(auditRecord.getServiceUnitNumber());
				npsAuditOrder.setStagingOrderId(shcOrder.getShcOrderId());
				npsAuditOrder.setSlSoId(shcOrder.getSoId());
								
				/* get messages from AuditRecord , set into auditOrder */
				String errMsgForOrder = getMessageInfo(npsAuditOrder, auditRecord);
				if(errMsgForOrder!= null && StringUtils.isNotEmpty(errMsgForOrder)){
					missingMessagesErrMsg = missingMessagesErrMsg + errMsgForOrder + MarketESBConstant.NEWLINE_CHAR;
					continue;
				}
				
				npsAuditOrdersList.add(npsAuditOrder);
			}
			npsAuditOrdersInfo.setNpsAuditOrders(npsAuditOrdersList);
		}
		
		/* send emails if any orders are missing or any messages doesn't exist in our system*/
		if(missingOrders || (missingMessagesErrMsg!= null && StringUtils.isNotEmpty(missingMessagesErrMsg))){
			
			String errorMessage = "";
			if(missingOrders){
				errorMessage = missingOrdersErrMsg;
			}
			if(missingMessagesErrMsg!= null && StringUtils.isNotEmpty(missingMessagesErrMsg)){
				errorMessage = errorMessage + MarketESBConstant.NEWLINE_CHAR ;
				errorMessage = errorMessage + missingMessagesErrMsg;
			}
			
			ExceptionHandler.handle(client, new String((byte[]) body.get()), 
					inputFilefeedName,	errorMessage , 
					message.getBody().get(MarketESBConstant.UNMARSHALLED_OBJ_GRAPH));
		}
	
		return npsAuditOrdersInfo;
	}

	private String getMessageInfo(NPSAuditOrder npsAuditOrder,
			NPSAuditRecord auditRecord) throws Exception{
		
		auditStagingService = (INPSAuditStagingService) SpringUtil.factory.getBean("npsAuditStagingService");
		List<NpsAuditOrderMessages> npsAuditOrderMessageses = new ArrayList<NpsAuditOrderMessages>();
		NpsAuditOrderMessages auditOrderMessages = null;
		List<String> messageList = null;
		Calendar cal = Calendar.getInstance();
		Timestamp createdDateTS = new Timestamp(cal.getTimeInMillis());
		
		String orderNo = auditRecord.getServiceOrderNumber();
		String unitNo = auditRecord.getServiceUnitNumber();
		String missingMessagesErrorMsg = "These messages doesn't exist in lu_audit_messages for the order with ";
		boolean missingMessages = false;
		
		try{
			if(auditRecord.getMessages()!= null ){
				messageList = auditRecord.getMessages().getMessages();
				
				for(String auditMessage : messageList){
					auditOrderMessages = new NpsAuditOrderMessages();
					@SuppressWarnings("unchecked")
					List<LuAuditMessages>  messageDetails = (List<LuAuditMessages>) auditStagingService.retrieveAuditMessageInfo(auditMessage, npsAuditOrder.getNpsStatus());
					
					if(messageDetails!= null && messageDetails.size()>0){
						LuAuditMessages messageDetail = messageDetails.get(0);
						auditOrderMessages.setLuAuditMessages(messageDetail);
						auditOrderMessages.setCreatedDate(createdDateTS);
						
					}else {
						missingMessagesErrorMsg = missingMessagesErrorMsg + " orderNo: " + orderNo + " unitNo: " + unitNo + " Message: " +  auditMessage +  " ***  " ; 
						missingMessages = true;
						continue;
					}
					npsAuditOrderMessageses.add(auditOrderMessages);
				}
				npsAuditOrder.setNpsAuditOrderMessages(npsAuditOrderMessageses);
				
			}
			if(!missingMessages){
				missingMessagesErrorMsg = null;
			}
			
		}catch(Exception e){
			logger.error("error occurred in NPSAuditResponseMapperAction.getMessageInfo due to ", e);
			throw e;
		}
		
		return missingMessagesErrorMsg;
	}

	
	/**
	 * Default Constructor for JUnit test cases
	 */
	public NPSAuditResponseMapperAction (){
		
	}
	
	/**
	 * The Constructor which is called by ESB runtime to configure actions
	 * @param configTree
	 */
	public NPSAuditResponseMapperAction(ConfigTree configTree)	{
		super.configTree = configTree;
	}



}
