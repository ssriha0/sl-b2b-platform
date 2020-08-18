package com.servicelive.orderfulfillment.command;

import java.util.Map;

import com.servicelive.domain.common.BuyerFeatureSetEnum;
import com.servicelive.domain.so.SORoutingRuleAssoc;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.dao.ICARAssociationDao;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.AutoRoutingBehavior;
import com.servicelive.orderfulfillment.domain.type.RoleType;
import com.servicelive.orderfulfillment.lookup.BuyerFeatureLookup;
import com.servicelive.orderfulfillment.lookup.QuickLookupCollection;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;

public class SetAutoRoutingBehaviorCmd extends SOCommand {
    QuickLookupCollection quickLookupCollection;
    ICARAssociationDao carAssociationDao;

    @Override
    public void execute(Map<String, Object> processVariables) {
        AutoRoutingBehavior autoRoutingBehavior = determineAutoRoutingBehaviorForServiceOrder(processVariables);
        logger.info("Inside SetAutoRoutingBehaviorCmd>>autoRoutingBehavior.name()>>"+autoRoutingBehavior.name());
        prepProcessForAutoRoutingBehavior(processVariables, autoRoutingBehavior);
    }

    private AutoRoutingBehavior determineAutoRoutingBehaviorForServiceOrder(Map<String, Object> processVariables) {
    	logger.info("Entering SetAutoRoutingBehaviorCmd.determineAutoRoutingBehaviorForServiceOrder()...");
        ServiceOrder serviceOrder = getServiceOrder(processVariables);

        BuyerFeatureLookup buyerFeatureLookup = quickLookupCollection.getBuyerFeatureLookup();
        if (!buyerFeatureLookup.isInitialized()) {
            throw new ServiceOrderException("Unable to lookup buyer feature for grouping. BuyerFeatureLookup not initialized.");
        }
        Long buyerId = serviceOrder.getBuyerId();
        String isUpdate = (String) processVariables.get(ProcessVariableUtil.ISUPDATE);

        if (buyerFeatureLookup.isActiveFeatureAssociatedWithBuyer(BuyerFeatureSetEnum.CONDITIONAL_ROUTE, buyerId)) {
            Integer conditionalAutoRoutingRuleId = (Integer)processVariables.get(ProcessVariableUtil.CONDITIONAL_AUTO_ROUTING_RULE_ID);

            if (conditionalAutoRoutingRuleId != null ) {
                if (logger.isDebugEnabled()) {
                    logger.info("Setting conditional routing rule associations for service order " + serviceOrder.getSoId() + " with ruleID " + conditionalAutoRoutingRuleId);
                }
                //create car association with service order..This part has been taken care in updateRoutingRuleId method
                //of OrderProcessingServiceImpl after CAR evaluation
                if(isUpdate==null){
	                SORoutingRuleAssoc assoc = new SORoutingRuleAssoc();
	                assoc.setSoId(serviceOrder.getSoId());
	                assoc.setRoutingRuleHdrId(conditionalAutoRoutingRuleId);
	                assoc.setModifiedBy(RoleType.System.name());
	                carAssociationDao.save(assoc);
                }
                return AutoRoutingBehavior.Conditional;
            }
        }
        String fromFrontEnd = (String) processVariables.get(OrderfulfillmentConstants.POST_FROM_FRONTEND_ACTION);
        String isSaveAndAutopost = (String) processVariables.get(ProcessVariableUtil.SAVE_AND_AUTOPOST);
        String isAutopost = (String) processVariables.get(OrderfulfillmentConstants.PVKEY_AUTO_POST);
        String saveAndAutopost = (String) processVariables.get(ProcessVariableUtil.AUTO_POST_IND); 
        logger.info("Inside SetAutoRoutingBehaviorCmd>>fromFrontEnd"+fromFrontEnd);
        logger.info("Inside SetAutoRoutingBehaviorCmd>>isSaveAndAutopost"+isSaveAndAutopost);
        logger.info("Inside SetAutoRoutingBehaviorCmd>>isAutopost"+isAutopost);
        logger.info("Inside SetAutoRoutingBehaviorCmd>>saveAndAutopost"+saveAndAutopost);
        if ((!buyerFeatureLookup.isActiveFeatureAssociatedWithBuyer(BuyerFeatureSetEnum.ORDER_GROUPING, buyerId)) 
        		|| (null!=fromFrontEnd && fromFrontEnd.equals("true")) 
        		|| (null!=isSaveAndAutopost && isSaveAndAutopost.equals("true")) 
        		|| (null!= isAutopost && isAutopost.equals("isAutoPost"))) {
            if (buyerFeatureLookup.isActiveFeatureAssociatedWithBuyer(BuyerFeatureSetEnum.TIER_ROUTE, buyerId) && serviceOrder.getSOWorkflowControls().getTierRouteInd()) {
                logger.info("determineAutoRoutingBehaviorForServiceOrder() method returning AutoRoutingBehavior.Tier");
                return AutoRoutingBehavior.Tier;
            } else if (buyerFeatureLookup.isActiveFeatureAssociatedWithBuyer(BuyerFeatureSetEnum.AUTO_ROUTE, buyerId)) {
                logger.info("determineAutoRoutingBehaviorForServiceOrder() method returning AutoRoutingBehavior.Basic");
                return AutoRoutingBehavior.Basic;
            }
        } else {
            logger.info("Skipping auto routing due to ORDER_GROUPING feature for buyer " + buyerId);
        }
        
        //for enabling tier routing while processing the order through frontend(reject,counter offer etc.)
        if ((buyerFeatureLookup.isActiveFeatureAssociatedWithBuyer(BuyerFeatureSetEnum.ORDER_GROUPING, buyerId)) 
        		&& buyerFeatureLookup.isActiveFeatureAssociatedWithBuyer(BuyerFeatureSetEnum.TIER_ROUTE, buyerId)) {
        processVariables.put(ProcessVariableUtil.ROUTING_BEHAVIOUR, "Tier");
        }

        return AutoRoutingBehavior.None;
    }

    private void prepProcessForAutoRoutingBehavior(Map<String, Object> processVariables, AutoRoutingBehavior autoRoutingBehavior) {
        ProcessVariableUtil.setAutoRoutingBehavior(processVariables, autoRoutingBehavior);
        switch (autoRoutingBehavior) {
            case Tier:
                break;
            case Conditional:
                break;
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    // SETTERS FOR SPRING INJECTION
    ////////////////////////////////////////////////////////////////////////////
    public void setQuickLookupCollection(QuickLookupCollection quickLookupCollection) {
        this.quickLookupCollection = quickLookupCollection;
    }

    public void setCarAssociationDao(ICARAssociationDao carAssociationDao) {
        this.carAssociationDao = carAssociationDao;
    }
}
