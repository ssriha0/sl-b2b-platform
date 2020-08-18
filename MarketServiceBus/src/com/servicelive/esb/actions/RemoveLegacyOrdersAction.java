package com.servicelive.esb.actions;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

import com.newco.marketplace.webservices.dao.ShcOrder;
import com.newco.marketplace.webservices.dto.StagingDetails;
import com.newco.marketplace.webservices.dto.serviceorder.CreateDraftRequest;
import com.servicelive.esb.constant.MarketESBConstant;
import com.servicelive.util.MarketESBUtil;


public class RemoveLegacyOrdersAction extends AbstractEsbSpringAction {
	
	private Logger logger = Logger.getLogger(RemoveLegacyOrdersAction.class);
	
	/**
	 * The Constructor which is called by ESB runtime to configure actions
	 * 
	 * @param config
	 */
	public RemoveLegacyOrdersAction(ConfigTree config) {
		super.configTree = config;
	}

	/**
	 * Default Constructor for JUnit test cases
	 */
	public RemoveLegacyOrdersAction() {
		logger.warn("***** This constructor is for Test Cases only *****");
	}
	
	@SuppressWarnings("unchecked")
	public Message removeLegacyOrders(Message message) {
		
		List<CreateDraftRequest> createDraftReqListNew = (List<CreateDraftRequest>) message.getBody().get(MarketESBConstant.MAPPED_OBJ_GRAPH_NEW);
		List<CreateDraftRequest> createDraftReqListUpdate = (List<CreateDraftRequest>) message.getBody().get(MarketESBConstant.MAPPED_OBJ_GRAPH_UPDATE);
		StagingDetails stagingData = (StagingDetails) message.getBody().get(MarketESBConstant.UNMARSHALLED_STAGE_OBJ_GRAPH);
		
		List<String> legacyOrders = null;
		if (message.getProperties().getProperty(MarketESBConstant.LEGACY_ORDERS) != null) {
			legacyOrders = (List<String>) message.getProperties().getProperty(MarketESBConstant.LEGACY_ORDERS);
		}
		
		if (legacyOrders != null && legacyOrders.size() > 0) {
			if (createDraftReqListNew != null) {
				removeLegacyOrdersFromCreateDraftRequestList(createDraftReqListNew, legacyOrders);
				message.getBody().add(MarketESBConstant.MAPPED_OBJ_GRAPH_NEW, createDraftReqListNew);
			}
			if (createDraftReqListUpdate != null) {
				removeLegacyOrdersFromCreateDraftRequestList(createDraftReqListUpdate, legacyOrders);
				message.getBody().add(MarketESBConstant.MAPPED_OBJ_GRAPH_UPDATE, createDraftReqListUpdate);
			}
			if (stagingData != null) {
				removeLegacyOrdersFromStagingDetails(stagingData, legacyOrders);
				message.getBody().add(MarketESBConstant.UNMARSHALLED_STAGE_OBJ_GRAPH, stagingData);
			}
		}
		
		return message;
	}

	private void removeLegacyOrdersFromStagingDetails(
			StagingDetails stagingData, List<String> legacyOrders) {
		
		List<ShcOrder> orders = stagingData.getStageServiceOrder();
		for (Iterator<ShcOrder> i = orders.iterator(); i.hasNext(); ) {
			ShcOrder order = i.next();
			String externalOrderNumber = MarketESBUtil.constructOrderIdUtil(order.getOrderNo(), order.getUnitNo());
			if (legacyOrders.contains(externalOrderNumber)) {
				i.remove();
			}
		}
		
	}

	private void removeLegacyOrdersFromCreateDraftRequestList(
			List<CreateDraftRequest> createDraftRequestList,
			List<String> legacyOrders) {
		
		for (Iterator<CreateDraftRequest> i = createDraftRequestList.iterator(); i.hasNext(); ) {
			CreateDraftRequest createDraftRequest = i.next();
		
			String externalOrderNumber = MarketESBUtil.constructOrderIdUtil(createDraftRequest.getOrderNumber(), createDraftRequest.getUnitNumber());
			if (legacyOrders.contains(externalOrderNumber)) {
				i.remove();
			}
		}		
	}

}
