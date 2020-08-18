package com.newco.marketplace.business.businessImpl.conditionalautorouting;

import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.conditionalautorouting.IConditionalAutoRoutingBO;
import com.newco.marketplace.business.iBusiness.orderGroup.IRouteOrderGroupBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.BuyerSOTemplateDTO;
import com.newco.marketplace.dto.vo.serviceorder.Buyer;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSearchResultsVO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.iDao.so.order.ServiceOrderDao;
import com.newco.marketplace.util.so.ServiceOrderUtil;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.business.ABaseBO;
import com.servicelive.routingrulesengine.services.OrderProcessingService;

public class ConditionalAutoRoutingBOImpl  extends ABaseBO implements IConditionalAutoRoutingBO {
	private Logger logger = Logger.getLogger(ConditionalAutoRoutingBOImpl.class);
	
	private IRouteOrderGroupBO routeOrderGroupBO;
	private ServiceOrderDao serviceOrderDao;
	private IServiceOrderBO serviceOrderBO;
	private OrderProcessingService orderProcessingService;
	

	public void routeIndividualConditionalOrder(ServiceOrderSearchResultsVO searchResultVO) throws BusinessServiceException {
		String soId = searchResultVO.getSoId();
		
		ServiceOrder so;
		try {
			so = serviceOrderDao.getServiceOrder(soId);
				
			Buyer buyer = so.getBuyer();
			Integer buyerId = buyer.getBuyerId();
			
			BuyerSOTemplateDTO template = routeOrderGroupBO.getTemplateForOrder(so);
			
			if (template != null) {
				Integer spnId = template.getSpnId();
				Boolean isNewSpn = template.getIsNewSpn();
		
				//List<Integer> routedResourceIds = new ArrayList<Integer>(); // call to the CAR method to get providers
                Integer ruleId = orderProcessingService.findRuleIdForSO(soId);
				List<Integer> routedResourceIds = orderProcessingService.getApprovedProviderList(ruleId, spnId, isNewSpn);
				
				SecurityContext securityContext = ServiceOrderUtil.getSecurityContextForBuyer(buyerId);				
				ProcessResponse businessProcessResponse = serviceOrderBO.processRouteSO(buyerId, soId, routedResourceIds, OrderConstants.NON_TIER, securityContext);
				
				handlePostRoutingLogging(soId, routedResourceIds, securityContext,
					businessProcessResponse);
			}
		} catch (com.newco.marketplace.exception.core.DataServiceException e) {
			throw new BusinessServiceException(e.getCause());
		}
			
	}
	
	
	private void handlePostRoutingLogging(String soId,
			List<Integer> routedResourceIds, SecurityContext securityContext,
			ProcessResponse businessProcessResponse)
			throws BusinessServiceException {
		
		if (businessProcessResponse.isError()) {
			String message = "Error thrown while routing SO with soId--> " + soId + " due to " +businessProcessResponse.getMessages().get(0);
			throw new BusinessServiceException(message);
		}
		
		String conditionalRuleName = orderProcessingService.getRoutedRuleName(soId);
		logger.info("Order routed using conditional rule: " + conditionalRuleName);
		String message = null;
		
		if (routedResourceIds.size() > 0) {
			logger.info("Service Order " + soId + " is routed successfully to " + routedResourceIds.size() + " providers.");
			message = "Service Order Routed - " + conditionalRuleName;
		}
		else {
			logger.info("Service Order " + soId + " is not routed due to 'no providers.'");
			message = "Service Order NOT Routed  due to NO PROVIDERS - " + conditionalRuleName;			
		}
		processSONotePostConditionalRouting(soId, message, routedResourceIds,
				securityContext);
	}

	
	
	private void processSONotePostConditionalRouting(String soId, String message,
			List<Integer> routedResourceIds, SecurityContext securityContext) {

		try {
			serviceOrderBO.processAddNote(securityContext.getCompanyId(), 
					OrderConstants.BUYER_ROLEID, soId, "Service Order - Conditional Routing", 
					message, 
					OrderConstants.SO_NOTE_GENERAL_TYPE, "System", 
					"System", securityContext.getCompanyId(), 
					OrderConstants.SO_NOTE_PRIVATE_ACCESS, false, false, securityContext);
		} catch (com.newco.marketplace.exception.core.BusinessServiceException e) {
			logger.error("Error creating private note for service Order " + soId );
		}
	}


	public IRouteOrderGroupBO getRouteOrderGroupBO() {
		return routeOrderGroupBO;
	}


	public void setRouteOrderGroupBO(IRouteOrderGroupBO routeOrderGroupBO) {
		this.routeOrderGroupBO = routeOrderGroupBO;
	}


	public ServiceOrderDao getServiceOrderDao() {
		return serviceOrderDao;
	}


	public void setServiceOrderDao(ServiceOrderDao serviceOrderDao) {
		this.serviceOrderDao = serviceOrderDao;
	}


	public IServiceOrderBO getServiceOrderBO() {
		return serviceOrderBO;
	}


	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}


	public OrderProcessingService getOrderProcessingService() {
		return orderProcessingService;
	}


	public void setOrderProcessingService(
			OrderProcessingService orderProcessingService) {
		this.orderProcessingService = orderProcessingService;
	}

}
