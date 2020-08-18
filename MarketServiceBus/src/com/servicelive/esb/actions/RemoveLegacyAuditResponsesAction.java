package com.servicelive.esb.actions;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

import com.servicelive.esb.constant.MarketESBConstant;
import com.servicelive.esb.dto.NPSAuditOrder;
import com.servicelive.esb.dto.NPSAuditOrdersInfo;
import com.servicelive.util.MarketESBUtil;

public class RemoveLegacyAuditResponsesAction extends AbstractEsbSpringAction {
	private Logger logger = Logger.getLogger(RemoveLegacyAuditResponsesAction.class);
	
	/**
	 * The Constructor which is called by ESB runtime to configure actions
	 * 
	 * @param config
	 */
	public RemoveLegacyAuditResponsesAction(ConfigTree config) {
		super.configTree = config;
	}

	/**
	 * Default Constructor for JUnit test cases
	 */
	public RemoveLegacyAuditResponsesAction() {
		logger.warn("***** This constructor is for Test Cases only *****");
	}
	
	@SuppressWarnings("unchecked")
	public Message removeLegacyAudits(Message message) {
		
		NPSAuditOrdersInfo reportableOrdersInfo = (NPSAuditOrdersInfo)message.getBody().get(MarketESBConstant.TRANSLATED_NPS_AUDIT_OBJ);
				
		List<String> legacyOrders = null;
		if (message.getProperties().getProperty(MarketESBConstant.LEGACY_ORDERS) != null) {
			legacyOrders = (List<String>) message.getProperties().getProperty(MarketESBConstant.LEGACY_ORDERS);
		}
		
		if (legacyOrders != null && legacyOrders.size() > 0) {
			if (reportableOrdersInfo != null && reportableOrdersInfo.getNpsAuditOrders() != null) {				
				removeLegacyOrders(reportableOrdersInfo, legacyOrders);
				message.getBody().add(MarketESBConstant.TRANSLATED_NPS_AUDIT_OBJ, reportableOrdersInfo);
			}
		}
		
		return message;
	}

	private void removeLegacyOrders(
			NPSAuditOrdersInfo reportableOrdersInfo, List<String> legacyOrders) {
		
		
		List<NPSAuditOrder> npsAuditOrders = reportableOrdersInfo.getNpsAuditOrders();
		
		for (Iterator<NPSAuditOrder> i = npsAuditOrders.iterator(); i.hasNext(); ) {
			NPSAuditOrder order = i.next();
			String externalOrderNumber = MarketESBUtil.constructOrderIdUtil(order.getServiceOrderNumber(), order.getServiceUnitNumber());
			if (legacyOrders.contains(externalOrderNumber)) {
				i.remove();
			}
		}
		
	}
}
