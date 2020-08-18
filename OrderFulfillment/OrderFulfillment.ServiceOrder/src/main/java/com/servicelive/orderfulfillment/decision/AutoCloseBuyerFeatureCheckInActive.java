package com.servicelive.orderfulfillment.decision;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jbpm.api.model.OpenExecution;

import com.servicelive.domain.common.BuyerFeatureSetEnum;
import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.domain.SOAdditionalPayment;
import com.servicelive.orderfulfillment.domain.SOAddon;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.lookup.BuyerFeatureLookup;
import com.servicelive.orderfulfillment.lookup.QuickLookupCollection;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;


public class AutoCloseBuyerFeatureCheckInActive extends AbstractServiceOrderDecision {

	/**
	 * 
	 */
	QuickLookupCollection quickLookupCollection;
	private static final long serialVersionUID = 100102764695148267L;
	protected final Logger logger = Logger.getLogger(getClass());

    public String decide(OpenExecution execution) {
		ServiceOrder so = getServiceOrder(execution);
		/*
		logic to check whether the buyer has auto close feature should come here
		*/
	    
        BuyerFeatureLookup buyerFeatureLookup = quickLookupCollection.getBuyerFeatureLookup();
        if (!buyerFeatureLookup.isInitialized()) {
            throw new ServiceOrderException("Unable to lookup buyer feature . BuyerFeatureLookup not initialized.");
        }
        
        //SL-20926 : Check if there is any issue with addons or additional payment
      	//Orders with such issues should not be auto closed
      	boolean isError = false;
      	if(null != so && !((null == so.getAddons() || (null != so.getAddons() && so.getAddons().isEmpty())) && null == so.getAdditionalPayment())){
      		isError = validateAddonsAndPayment(so.getAddons(), so.getAdditionalPayment());
      	}
      	logger.info("AutoCloseBuyerFeatureCheckInActive:isError" + isError);
      		
        Long buyerId = so.getBuyerId();

        if (buyerFeatureLookup.isActiveFeatureAssociatedWithBuyer(BuyerFeatureSetEnum.AUTO_CLOSE, buyerId)) {
        
        	
        	execution.setVariable(OrderfulfillmentConstants.PVKEY_CANCELLATION_COMMENT, "Cancelled by System");
        	
        	execution.setVariable(OrderfulfillmentConstants.CANCELLED_FIXED_PRICE, "0.00");

        	
        	int serviceIncompleteOrder=0;
    		Object isAutoClose = (String) execution.getVariable(OrderfulfillmentConstants.PVKEY_AUTOCLOSE);
    		if(isAutoClose!=null && isAutoClose.toString().equals("autoClose"))
    		{
    			
    			serviceIncompleteOrder=1;	
    		}
    		if(serviceIncompleteOrder==1 && so.getFinalPriceTotal().doubleValue()==0)
    		{
    			return "cancelled";	
    			
    		}
    		
    		//SL-20926 : Orders with addon issues should not be auto closed
    		if(isError){
    			return "false";
    		}
		
			return "true"; 
        }
        
        //SL-20926 : Orders with addon issues should not be auto closed
		if(isError){
			return "false";
		}
		
        //R12_1 SL-20660              
        if (buyerFeatureLookup.isActiveFeatureAssociatedWithBuyer
        		(BuyerFeatureSetEnum.INHOME_AUTO_CLOSE, buyerId) &&(null== so.getSoProviderInvoiceParts() || so.getSoProviderInvoiceParts().size()==0 )){
        	
        	 return "autocloseHSR";
        }  	        	    
    	if (buyerFeatureLookup.isActiveFeatureAssociatedWithBuyer(BuyerFeatureSetEnum.INHOME_AUTO_CLOSE, buyerId) && 
    	    		 (null!=so.getSOWorkflowControls() && StringUtils.isNotBlank(so.getSOWorkflowControls().getInvoicePartsPricingModel()) && so.getSOWorkflowControls().getInvoicePartsPricingModel().equalsIgnoreCase("COST_PLUS"))) {
    	        	
    	    	 return "autocloseHSR";
    	}
        
    return "false";
    
    }
    public void setQuickLookupCollection(QuickLookupCollection quickLookupCollection) {
        this.quickLookupCollection = quickLookupCollection;
    }
    
	    /**
		 * SL-20926 changes
		 * Check if addon issues exists for the order, if
		 * AddOn qty = 0 but having additional payment details entry
		 * AddOn qty > 0 but having no additional payment details
		 * AddOn qty > 0 but the payment type in additional payment details is blank
		 * @param addons
		 * @param additionalPayment
		 * @return boolean
		 */
	    private boolean validateAddonsAndPayment(List<SOAddon> addons, SOAdditionalPayment additionalPayment) {
	    	
	    	boolean isError = false;
	    	
			logger.info("Inside AutoCloseBuyerFeatureCheckInActive - validate()2");
			boolean hasQty = false;
			String paymentType = null;
				
			//check if any addon is having qty > 0
			if(null != addons && !addons.isEmpty()){
				for(SOAddon addon : addons){
					if(null != addon && null != addon.getQuantity() && addon.getQuantity() > 0){
						hasQty = true;
						break;
					}
				}
			}
				
			if(null != additionalPayment && null != additionalPayment.getPaymentType()){
				paymentType = additionalPayment.getPaymentType().getShortName();
			}
				
			//if addon qty = 0 but having additional payment details or
			//addon qty > 0 but having no additional payment details or
			//addon qty > 0 but the payment type in additional payment details is blank
			if((!hasQty && null != additionalPayment) || 
					(hasQty && null == additionalPayment) || 
					(hasQty && null != additionalPayment && StringUtils.isBlank(paymentType))){
				logger.info("Inside AutoCloseBuyerFeatureCheckInActive - if condition");	
				isError = true;
			}
			return isError;
		}
  	}
 
	

