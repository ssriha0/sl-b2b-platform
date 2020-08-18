package com.servicelive.orderfulfillment.orderprep.processor.ri;

import com.servicelive.domain.common.BuyerFeatureSetEnum;
import com.servicelive.domain.so.BuyerOrderTemplate;
import com.servicelive.marketplatform.common.vo.SPNetHdrVO;
import com.servicelive.orderfulfillment.domain.SOTask;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.SOWorkflowControls;
import com.servicelive.orderfulfillment.orderprep.processor.common.AbstractOrderHdrEnhancer;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;
import com.servicelive.orderfulfillment.domain.type.PartSupplierType;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class OrderHdrEnhancerForOfficeMax extends AbstractOrderHdrEnhancer {
	@Override
	    protected void setMiscOrderHdrInfo(ServiceOrder serviceOrder, BuyerOrderTemplate buyerOrderTemplate) {
	        
		    //SL-17627 - Edit existing enhancers for buyer 1953 (OfficeMax) 
		    if(serviceOrder.isCreatedViaAPI()){
	    		if(!(StringUtils.isBlank(serviceOrder.getSowTitle()))){
	    			serviceOrder.setSowTitle(serviceOrder.getSowTitle());	    			
	    		   }
	    		else{
	    	        serviceOrder.setSowTitle(getAllTaskNames(serviceOrder.getTasks()));
	    			}
	    		serviceOrder.setSowDs(getAllAppendedOverview(serviceOrder, buyerOrderTemplate));
	    	}
	    	else{
	    		 serviceOrder.setSowTitle(serviceOrder.getSowTitle() + "-" + serviceOrder.getPrimaryTask().getTaskName());
	    	     serviceOrder.setSowDs(serviceOrder.getSowDs() + getAllTaskComments(serviceOrder.getTasks()));
	    	}
	    	
	}
	private String getAllTaskComments(List<SOTask> taskList) {
		StringBuilder stringBuilder = new StringBuilder("\n");
		for (SOTask task : taskList) {
			stringBuilder.append(task.getTaskComments()).append("\n");
		}
		return stringBuilder.toString();
	}

	private String getAllTaskNames(List<SOTask> taskList) {
		StringBuilder stringBuilder = new StringBuilder();
		List<String> taskName = new ArrayList<String>();
		for (SOTask task : taskList) {
			taskName.add(task.getTaskName());
		}
		for (int i = 0; i < taskName.size(); i++) {
			stringBuilder.append(taskName.get(i).toString());
			if (i != taskName.size() - 1) {
				stringBuilder.append(" & ");
			}
		}
		return stringBuilder.toString();
	}

	@Override
	protected void setInfoFromTemplate(ServiceOrder serviceOrder,
			BuyerOrderTemplate buyerOrderTemplate) {
		logger.info("inside setInfoFromTemplate overrided.");
		serviceOrder.setSowTitle(fixStringIfNull(serviceOrder.getSowTitle()));
		serviceOrder.setSowDs(fixStringIfNull(serviceOrder.getSowDs()));
		serviceOrder.setSpnId(buyerOrderTemplate.getSpnId());
		if (buyerOrderTemplate.getPartsSuppliedBy() != null)
			serviceOrder.setPartsSupplier(PartSupplierType
					.fromId(buyerOrderTemplate.getPartsSuppliedBy()));
		serviceOrder.setBuyerTermsCond(buyerOrderTemplate.getTerms());
		// SL-16834 Do not override the value if it is already set via API
		boolean isCreatedViaAPI = false;
		isCreatedViaAPI = serviceOrder.isCreatedViaAPI();
		if (!isCreatedViaAPI) {
			serviceOrder.setBuyerContactId(buyerOrderTemplate
					.getAltBuyerContactId());
		}
		if (!isCreatedViaAPI) {
			serviceOrder.setBuyerResourceId(buyerOrderTemplate
					.getBuyerResourceId().longValue());
		}
		serviceOrder.setLogoDocumentId(buyerOrderTemplate.getDocumentLogoId());
		serviceOrder.setProviderServiceConfirmInd(buyerOrderTemplate
				.getConfirmServiceTime());
		logger.info("about to auto accept check.");
		SOWorkflowControls soWorkflowControls = serviceOrder
		.getSOWorkflowControls() == null ? new SOWorkflowControls()
		: serviceOrder.getSOWorkflowControls();
		if (buyerOrderTemplate.getAutoAccept() != null) {
			soWorkflowControls
					.setAutoAcceptRescheduleRequestIndicator(buyerOrderTemplate
							.getAutoAccept().equals(1) ? new Boolean(true)
							: new Boolean(false));
			soWorkflowControls
					.setAutoAcceptRescheduleRequestDays(buyerOrderTemplate
							.getAutoAcceptDays());
			soWorkflowControls
					.setAutoAcceptRescheduleRequestCount(buyerOrderTemplate
							.getAutoAcceptTimes());
		}
		if(null!=buyerOrderTemplate.getSpnId()){
        	SPNetHdrVO spnHdr = serviceOrderDao.fetchSpnInfo(buyerOrderTemplate.getSpnId());
        	if(null != spnHdr && null!=spnHdr.getPriorityStatus() && !spnHdr.getPriorityStatus().equals("ACTIVE")){
        		serviceOrder.setRoutingPriorityInd("false");
        		soWorkflowControls.setTierRouteInd(false);
        		soWorkflowControls.setMpOverFlow(false);
        	}else{
        		serviceOrder.setRoutingPriorityInd("true");
        		serviceOrder.setPerfCriteriaLevel(spnHdr.getPerfCriteriaLevel());
        		soWorkflowControls.setTierRouteInd(true);
        		soWorkflowControls.setPerformanceScore(0.00);
        		boolean mpEligible = quickLookupCollection.getBuyerFeatureLookup().isActiveFeatureAssociatedWithBuyer(BuyerFeatureSetEnum.AUTO_ROUTE, serviceOrder.getBuyerId());
        		logger.info("mpEligible in AbstractOrderHdrEnhancer>>"+mpEligible);
        		if(mpEligible){//should be set true only for buyers with AUTO_ROUTE = 1
        			soWorkflowControls.setMpOverFlow(spnHdr.getMpOverFlow());
        		}else{
        			soWorkflowControls.setMpOverFlow(false);
        		}
        	}
        }
		serviceOrder.setSOWorkflowControls(soWorkflowControls);
		logger.info("soWorkflowControls is set.");
	}

	private String fixStringIfNull(String str) {
		return (str == null) ? "" : str;
	}

	private String getAllAppendedOverview(ServiceOrder serviceOrder,
			BuyerOrderTemplate buyerOrderTemplate) {
		StringBuilder appendedFromTemplate = new StringBuilder();
		appendedFromTemplate.append(OrderfulfillmentConstants.OVERVIEW_TITLE);
		appendedFromTemplate.append(getAllTaskNames(serviceOrder.getTasks()))
				.append("<p>");
		appendedFromTemplate.append(buyerOrderTemplate.getOverview()).append(
				"</p><p>");
		appendedFromTemplate.append(
				OrderfulfillmentConstants.OVERVIEW_QUESTION_HEADING).append(
				"</p><p>");
		appendedFromTemplate.append(serviceOrder.getSowDs()).append("</p>");
		return appendedFromTemplate.toString();
	}
}
