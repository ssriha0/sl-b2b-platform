package com.servicelive.orderfulfillment.group.command;

import java.util.Map;

import org.apache.log4j.Logger;

import com.servicelive.domain.common.BuyerFeatureSetEnum;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.domain.SOGroup;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.AutoRoutingBehavior;
import com.servicelive.orderfulfillment.lookup.BuyerFeatureLookup;
import com.servicelive.orderfulfillment.lookup.QuickLookupCollection;

public class SetAutoRouteForGroupCmd extends SOGroupCmd {
	private Logger logger = Logger.getLogger(getClass());
	
    private QuickLookupCollection quickLookupCollection;

	@Override
	public void handleGroup(SOGroup soGroup, Map<String, Object> processVariables) {
		logger.info("Setting autoroute for group");
		logger.info("Inside handleGroup");
		ServiceOrder so = soGroup.getFirstServiceOrder();
		AutoRoutingBehavior autoRoutingBehavior = getAutoRoutingBehaviour(so);
		prepProcessForAutoRoutingBehavior(processVariables, autoRoutingBehavior);
		logger.info("Done Setting autoroute for group");
	}

	@Override
	public void handleServiceOrder(ServiceOrder so, Map<String, Object> processVariables) {
		logger.info("Inside handleServiceOrder");
		AutoRoutingBehavior autoRoutingBehavior = getAutoRoutingBehaviour(so);
		prepProcessForAutoRoutingBehavior(processVariables, autoRoutingBehavior);
	}

	private AutoRoutingBehavior getAutoRoutingBehaviour(ServiceOrder serviceOrder){
        BuyerFeatureLookup buyerFeatureLookup = quickLookupCollection.getBuyerFeatureLookup();
        if (!buyerFeatureLookup.isInitialized()) {
            throw new ServiceOrderException("Unable to lookup buyer feature for grouping. BuyerFeatureLookup not initialized.");
        }

		Long buyerId = serviceOrder.getBuyerId();
		logger.info("Inside SetAutoRouteForGroupCmd>>buyerId"+buyerId);
		boolean tierEligibleInd = false;
		if(null!=serviceOrder.getSOWorkflowControls() && null!= serviceOrder.getSOWorkflowControls().getTierRouteInd() && serviceOrder.getSOWorkflowControls().getTierRouteInd()){
			tierEligibleInd = true;
		}
		logger.info("Inside SetAutoRouteForGroupCmd>>"+tierEligibleInd);
        if (buyerFeatureLookup.isActiveFeatureAssociatedWithBuyer(BuyerFeatureSetEnum.TIER_ROUTE, buyerId) && (tierEligibleInd)) {
        	logger.info("inside tier check>>"+serviceOrder.getSoId());
            return AutoRoutingBehavior.Tier;
        } else if (buyerFeatureLookup.isActiveFeatureAssociatedWithBuyer(BuyerFeatureSetEnum.AUTO_ROUTE, buyerId)) {
        	logger.info("inside auto check>>"+serviceOrder.getSoId());
            return AutoRoutingBehavior.Basic;
        } else {
        	logger.info("inside else check>>"+serviceOrder.getSoId());
        	return AutoRoutingBehavior.None;
        }

	}
	
    private void prepProcessForAutoRoutingBehavior(Map<String, Object> processVariables, AutoRoutingBehavior autoRoutingBehavior) {
        ProcessVariableUtil.setAutoRoutingBehavior(processVariables, autoRoutingBehavior);
    }


    public void setQuickLookupCollection(QuickLookupCollection quickLookupCollection) {
        this.quickLookupCollection = quickLookupCollection;
    }
}
