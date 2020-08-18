package com.servicelive.esb.actions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.soa.esb.ConfigurationException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

import com.newco.marketplace.translator.util.EmailSender;
import com.servicelive.esb.constant.MarketESBConstant;
import com.servicelive.esb.integration.domain.IntegrationName;
import com.servicelive.esb.integration.domain.OmsBuyerNotificationResponse;
import com.servicelive.esb.integration.domain.OmsBuyerNotificationResponseMessage;
import com.servicelive.esb.integration.domain.Transaction;

public class NPSCloseAuditNotificationAction extends AbstractIntegrationSpringAction {

	public NPSCloseAuditNotificationAction() { super(); }
	public NPSCloseAuditNotificationAction(ConfigTree config) throws ConfigurationException { super(config); }
	
	@Override
	protected Long getIntegrationId(String fileName) {
		return IntegrationName.NPS_CLOSE_AUDIT.getId();
	}

	@Override
	protected String getIntegrationName(String fileName) {
		return IntegrationName.NPS_CLOSE_AUDIT.name();
	}
	
	public Message sendNotifications(Message message) {
		Object fileFeedPropertyValue = message.getProperties().getProperty(MarketESBConstant.ORIGINAL_FILE_FEED_NAME);
		String fileName = String.valueOf(fileFeedPropertyValue == null ? "" : fileFeedPropertyValue);
		List<Transaction> transactions = this.getIntegrationServiceCoordinator().getIntegrationBO().getTransactionsByBatchFileName(fileName);
		List<OmsBuyerNotificationResponse> buyerNotificationResponses = 
			this.getIntegrationServiceCoordinator().getIntegrationBO().getOmsBuyerNotificationResponsesByTransactions(transactions);
		
		notifyAuditOwners(buyerNotificationResponses);
		
		// Determine which transactions did not have a corresponding buyerNotificationResponse
		StringBuilder missingOrdersErrSb = new StringBuilder();
		missingOrdersErrSb.append("The following orders do not have a record in oms_buyer_notifications: ");
		missingOrdersErrSb.append(MarketESBConstant.NEWLINE_CHAR);
		StringBuilder missingMessagesErrSb = new StringBuilder();
		missingMessagesErrSb.append("The following messages don't exist in lu_audit_messages: ");
		missingMessagesErrSb.append(MarketESBConstant.NEWLINE_CHAR);
		boolean missingOrders = false;
		boolean missingMessages = false;
		
		for (Transaction transaction : transactions) {
			boolean found = false;
			for (OmsBuyerNotificationResponse response : buyerNotificationResponses) {
				if (response.getTransactionId().equals(transaction.getTransactionId())) {
					found = true;
					
					// Determine whether the buyerNotificationResponse has invalid messages
					for (OmsBuyerNotificationResponseMessage thisMessage : response.getMessages()) {
						// If fields from the message lookup tables are null, then we must not have been able to find the message, so it
						// is not a valid message
						if (thisMessage.getReportable() == null) {
							missingMessagesErrSb.append(String.format("NpsStatus: '%s'.  Message: '%s'.  External order number: %s", response.getNpsStatus(), thisMessage.getMessage(), transaction.getExternalOrderNumber()));
							missingMessagesErrSb.append(MarketESBConstant.NEWLINE_CHAR);
							missingMessages = true;
						}
					}					
					break;
				}
			}
			if (!found) {
				missingOrdersErrSb.append(" externalOrderNo-- ");
				missingOrdersErrSb.append(transaction.getExternalOrderNumber());
				missingOrdersErrSb.append("***");
				missingOrdersErrSb.append(MarketESBConstant.NEWLINE_CHAR);
				missingOrders = true;
			}
		}
		
		
		// send emails if any orders are missing or any messages doesn't exist in our system
		if(missingOrders || missingMessages){
			
			String errorMessage = "";
			if(missingOrders){
				errorMessage = missingOrdersErrSb.toString();
			}
			
			if(missingMessages){
				errorMessage = errorMessage + MarketESBConstant.NEWLINE_CHAR ;
				errorMessage = errorMessage + missingMessagesErrSb.toString();
			}			
			
			exceptionHandler(message, new Exception(errorMessage));
		}
		
		return message;
	}
	
	private void notifyAuditOwners(List<OmsBuyerNotificationResponse> npsAuditOrders/*List<NPSAuditOrder> npsAuditOrders*/) {
		Map<Integer, List<OmsBuyerNotificationResponse>> ownerOrdersMap = createMapForOrdersWithOwners(npsAuditOrders);
		String ownerEmailIds = null;
		Map<String, Object> emailInfo = null;
		String bodyText = "";
		String ccEmail = null;
		for (Integer ownerId : ownerOrdersMap.keySet()) {
			StringBuilder emailContent = null;
			emailInfo = new HashMap<String, Object>();
			List<OmsBuyerNotificationResponse> auditOrders = ownerOrdersMap.get(ownerId);
			for (OmsBuyerNotificationResponse auditOrder : auditOrders) {
				emailInfo = prepareEmailContent(ownerId, auditOrder, emailInfo);
				ownerEmailIds = (String)emailInfo.get("ownerEmailIds");
				emailContent = (StringBuilder)emailInfo.get("emailContent");
				
			}	
			 // ownerId refers to audit_owner_id in lu_audit_owners  table
			if(ownerId.intValue() == 1){
				bodyText = " http://wiki.intra.sears.com/confluence/display/SOUQ/ServiceLive+Environments ";
			}else if(ownerId.intValue() == 2){
				bodyText = " http://wiki.intra.sears.com/confluence/display/SOUQ/ServiceLive+Environments ";
				ccEmail = "slprdteam@searshc.com";
			}
			
			emailOwner(ownerEmailIds, emailContent, bodyText, ccEmail);			
		}
	}
	
	private Map<Integer, List<OmsBuyerNotificationResponse>> createMapForOrdersWithOwners(
			List<OmsBuyerNotificationResponse> npsAuditOrders) {
		Map<Integer, List<OmsBuyerNotificationResponse>> ownerOrdersMap = new HashMap<Integer, List<OmsBuyerNotificationResponse>>();
		for (OmsBuyerNotificationResponse auditOrder : npsAuditOrders) {
			if (auditOrder.getMessages() != null) {
				for (OmsBuyerNotificationResponseMessage orderMessage : auditOrder.getMessages()) {
					if (Boolean.TRUE.equals(orderMessage.getReportable())) {
						Integer ownerId = orderMessage.getOwnerId();
						List<OmsBuyerNotificationResponse> auditOrders = ownerOrdersMap.get(ownerId);
						if (auditOrders == null) {
							auditOrders = new ArrayList<OmsBuyerNotificationResponse>();
							ownerOrdersMap.put(ownerId, auditOrders);
						}
						/* if order is already in List, don't add it again*/
						if(!auditOrders.contains(auditOrder)){
						auditOrders.add(auditOrder);
					}
				}
			}
		}
		}
		return ownerOrdersMap;
	}
	
	private Map<String, Object> prepareEmailContent(Integer ownerId, OmsBuyerNotificationResponse auditOrder, Map<String, Object> emailInfo) {
		String ownerEmailIds = null;
		StringBuilder sb = null;
		
		if(emailInfo != null && emailInfo.get("emailContent")!= null){
			sb = (StringBuilder)emailInfo.get("emailContent");
			sb.append(System.getProperty("line.separator"));
		}else{
			sb = new StringBuilder();
		}
				
		sb.append(auditOrder.getServiceUnitNumber());
		sb.append(MarketESBConstant.AUDIT_EMAIL_DELIMITER);
		sb.append(auditOrder.getServiceOrderNumber());
		sb.append(MarketESBConstant.AUDIT_EMAIL_DELIMITER);
		sb.append(auditOrder.getServiceLiveOrderId());
		sb.append(MarketESBConstant.AUDIT_EMAIL_DELIMITER);
		sb.append(auditOrder.getSalesCheckNumber());
		sb.append(MarketESBConstant.AUDIT_EMAIL_DELIMITER);
		sb.append(auditOrder.getSalesCheckDate());
		sb.append(MarketESBConstant.AUDIT_EMAIL_DELIMITER);
		for (OmsBuyerNotificationResponseMessage orderMessage : auditOrder.getMessages()) {
			if (orderMessage.getOwnerId()!= null && orderMessage.getOwnerId().intValue() == ownerId.intValue() ) {
				if (ownerEmailIds == null) {
					ownerEmailIds = orderMessage.getEmailIds();
				}
				sb.append(orderMessage.getMessage());
				sb.append(MarketESBConstant.AUDIT_EMAIL_DELIMITER);						
			}
		}				
		sb.append(auditOrder.getProcessId());
		sb.append(MarketESBConstant.AUDIT_EMAIL_DELIMITER);
		sb.append(System.getProperty("line.separator"));

		emailInfo.put("ownerEmailIds", ownerEmailIds);
		emailInfo.put("emailContent", sb);
		
		return emailInfo;
	}
	
	/**
	 * @param ownerEmailIds
	 * @param sb
	 * @param bodyText 
	 */
	private void emailOwner(String ownerEmailIds, StringBuilder sb, String bodyText, String ccEmail) {
		
		SimpleDateFormat sdf = new SimpleDateFormat ( "yyyyMMddHHmmss" ); 
		String errorDate = sdf.format(new Date());

		String fileName = "SLNPSAudit" + errorDate + ".txt";
		StringBuilder body = new StringBuilder();
		body.append("There were problems on closing orders at NPS. See the attachement for order information. For working instructions click on the link  ");
		body.append(System.getProperty("line.separator"));
		body.append(bodyText);
		String subject = "NPS Audit Errors";
		
		StringBuilder emailContentHeader = new StringBuilder();
		String header = "ServiceUnitNumber| ServiceOrderNumber|ServiceLiveSO Number|SalesCheckNumber|SalesCheckDate|Message(s)|ProcessId";
		emailContentHeader.append(header);
		emailContentHeader.append(System.getProperty("line.separator"));
		emailContentHeader.append(System.getProperty("line.separator"));
		emailContentHeader.append(sb.toString());
		
		EmailSender.sendMessage(body, subject, emailContentHeader, fileName, ownerEmailIds, ccEmail);
	}
}
