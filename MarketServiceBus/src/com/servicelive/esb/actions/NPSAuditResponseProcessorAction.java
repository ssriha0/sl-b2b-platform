package com.servicelive.esb.actions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

import com.newco.marketplace.translator.util.EmailSender;
import com.newco.marketplace.webservices.dao.LuAuditMessages;
import com.newco.marketplace.webservices.dao.LuAuditOwners;
import com.newco.marketplace.webservices.dao.NpsAuditOrderMessages;
import com.servicelive.esb.constant.MarketESBConstant;
import com.servicelive.esb.dto.NPSAuditOrder;
import com.servicelive.esb.dto.NPSAuditOrdersInfo;

public class NPSAuditResponseProcessorAction extends AbstractEsbSpringAction {
	
	Logger logger = Logger.getLogger(NPSAuditResponseProcessorAction.class);
	
	public Message processAuditOrders (Message message){
		NPSAuditOrdersInfo reportableOrdersInfo = 
			(NPSAuditOrdersInfo)message.getBody().get(MarketESBConstant.TRANSLATED_NPS_AUDIT_OBJ);
		if (reportableOrdersInfo.getErrorOrdersCnt() > 0) {
			notifyAuditOwners(reportableOrdersInfo.getNpsAuditOrders());
		}
		
		return message;
	}
	
	private void notifyAuditOwners(List<NPSAuditOrder> npsAuditOrders) {
		Map<Integer, List<NPSAuditOrder>> ownerOrdersMap = createMapForOrdersWithOwners(npsAuditOrders);
		String ownerEmailIds = null;
		Map<String, Object> emailInfo = null;
		String bodyText = "";
		String ccEmail = null;
		
		for (Integer ownerId : ownerOrdersMap.keySet()) {
			StringBuilder emailContent = null;
			emailInfo = new HashMap<String, Object>();
			List<NPSAuditOrder> auditOrders = ownerOrdersMap.get(ownerId);
			for (NPSAuditOrder auditOrder : auditOrders) {
				emailInfo = prepareEmailContent(ownerId, auditOrder, emailInfo);
				ownerEmailIds = (String)emailInfo.get("ownerEmailIds");
				emailContent = (StringBuilder)emailInfo.get("emailContent");
				
			}	
			 /* ownerId refers to audit_owner_id in lu_audit_owners  table*/
			if(ownerId.intValue() == 1){
				bodyText = " http://wiki.intra.sears.com/confluence/display/SOUQ/NPS+Callclose ";
			}else if(ownerId.intValue() == 2){
				bodyText = " ";
				ccEmail = "slprdteam@searshc.com";
			}
			
			emailOwner(ownerEmailIds, emailContent, bodyText, ccEmail);			
		}
	}

	/**
	 * @param npsAuditOrders
	 * @return
	 */
	private Map<Integer, List<NPSAuditOrder>> createMapForOrdersWithOwners(
			List<NPSAuditOrder> npsAuditOrders) {
		Map<Integer, List<NPSAuditOrder>> ownerOrdersMap = new HashMap<Integer, List<NPSAuditOrder>>();
		for (NPSAuditOrder auditOrder : npsAuditOrders) {
			for (NpsAuditOrderMessages orderMessage : auditOrder.getNpsAuditOrderMessages()) {
				Integer ownerId = orderMessage.getLuAuditMessages().getLuAuditOwners().getAuditOwnerId();
				List<NPSAuditOrder> auditOrders = ownerOrdersMap.get(ownerId);
				if (auditOrders == null) {
					auditOrders = new ArrayList<NPSAuditOrder>();
					ownerOrdersMap.put(ownerId, auditOrders);
				}
				/* if order is already in List, don't add it again*/
				if(!auditOrders.contains(auditOrder)){
					auditOrders.add(auditOrder);
				}
				
			}			
		}
		return ownerOrdersMap;
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

	/**
	 * @param ownerId
	 * @param ownerEmailIds
	 * @param sb
	 * @param auditOrder
	 * @param emailInfo2 
	 * @return
	 */
	private Map<String, Object> prepareEmailContent(Integer ownerId, NPSAuditOrder auditOrder, Map<String, Object> emailInfo) {
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
		sb.append(auditOrder.getSlSoId());
		sb.append(MarketESBConstant.AUDIT_EMAIL_DELIMITER);
		sb.append(auditOrder.getSalesCheck().getNumber());
		sb.append(MarketESBConstant.AUDIT_EMAIL_DELIMITER);
		sb.append(auditOrder.getSalesCheck().getDate());
		sb.append(MarketESBConstant.AUDIT_EMAIL_DELIMITER);
		for (NpsAuditOrderMessages orderMessage : auditOrder.getNpsAuditOrderMessages()) {
			LuAuditMessages auditMessage = orderMessage.getLuAuditMessages();
			LuAuditOwners auditOwner = auditMessage.getLuAuditOwners();
			if (auditOwner.getAuditOwnerId()!= null && auditOwner.getAuditOwnerId().intValue() == ownerId.intValue() ) {
				if (ownerEmailIds == null) {
					ownerEmailIds = auditOwner.getEmailIds();
				}
				sb.append(auditMessage.getMessage());
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
	 * Default Constructor for JUnit test cases
	 */
	public NPSAuditResponseProcessorAction (){
		
	}
	
	public NPSAuditResponseProcessorAction(ConfigTree configTree ) {
		super.configTree = configTree;
		
	}

}
