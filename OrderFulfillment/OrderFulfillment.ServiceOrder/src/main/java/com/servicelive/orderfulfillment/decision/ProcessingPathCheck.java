package com.servicelive.orderfulfillment.decision;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jbpm.api.model.OpenExecution;

import com.servicelive.domain.common.BuyerFeatureSetEnum;
import com.servicelive.orderfulfillment.common.OFConstants;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.domain.SOCustomReference;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.AutoRoutingBehavior;
import com.servicelive.orderfulfillment.domain.type.SOFieldsChangedType;
import com.servicelive.orderfulfillment.lookup.BuyerFeatureLookup;
import com.servicelive.orderfulfillment.lookup.QuickLookupCollection;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;

public class ProcessingPathCheck extends AbstractServiceOrderDecision {

    /**
	 * 
	 */
	private static final Logger logger = Logger.getLogger(ProcessingPathCheck.class);
	private static final long serialVersionUID = 713289412944790869L;
//    private static final Logger logger = Logger.getLogger(MajorChangesCheck.class);
	QuickLookupCollection quickLookupCollection;
	
	public String decide(OpenExecution openExecution) {
		ServiceOrder so = getServiceOrder(openExecution);
		List<SOFieldsChangedType> changes = (List<SOFieldsChangedType>) openExecution.getVariable(ProcessVariableUtil.SERVICE_ORDER_CHANGES);
		String isUpdate = (String)openExecution.getVariable(ProcessVariableUtil.ISUPDATE);
		String postFromFrontend = (String)openExecution.getVariable(ProcessVariableUtil.FE_POST_ORDER);
		String saveAsDraft = (String)openExecution.getVariable(ProcessVariableUtil.SAVE_AS_DRAFT);
		String mandatoryCustRefPresent=(String)openExecution.getVariable(ProcessVariableUtil.SERVICE_ORDER_HAS_MANDATORY_CUSTOM_REFERENCE);
		logger.info("Mandatory CustRef value getting  from process variable"+ mandatoryCustRefPresent);
		logger.info("@@@+CREATEDVIAAPI"+so.isCreatedViaAPI());
		
		logger.info("Auto route SO:"+so.getSoId());

		String autoRoute = "nin";
		if(openExecution.getVariable(ProcessVariableUtil.PVKEY_AUTO_ROUTING_BEHAVIOR)!=null){
			autoRoute = (String)openExecution.getVariable(ProcessVariableUtil.PVKEY_AUTO_ROUTING_BEHAVIOR);
		}
		logger.info("Auto route Behaviour:"+autoRoute);

		Object saveAutoPostInd = openExecution.getVariable(ProcessVariableUtil.SAVE_AND_AUTOPOST);
		Boolean createWithOutTasks = (Boolean)openExecution.getVariable(ProcessVariableUtil.CREATE_WITHOUT_TASKS);
		Boolean jobCodeMismatch = (Boolean)openExecution.getVariable(ProcessVariableUtil.JOBCODE_MISMATCH);
		BuyerFeatureLookup buyerFeatureLookup = quickLookupCollection.getBuyerFeatureLookup();
		
		Object postFromFrontEndAction=openExecution.getVariable(OrderfulfillmentConstants.POST_FROM_FRONTEND_ACTION);
		if(null!=postFromFrontEndAction){
			logger.info("postFromFrontEndAction.toString()"+postFromFrontEndAction.toString());
		}
		else
		{
			logger.info("postFromFrontEndAction.toString() is null");
		}
		//AT&T orders should stay in draft status if 'MANUAL REVIEW' custom reference is present
		boolean manualReviewRequired = false;
		List<SOCustomReference> customReferences = new ArrayList<SOCustomReference>();
		customReferences = so.getCustomReferences();
		if (customReferences != null && !customReferences.isEmpty()) {
			for(SOCustomReference customRef:customReferences){
				if(null !=customRef.getBuyerRefTypeName() && customRef.getBuyerRefTypeName().equalsIgnoreCase(SOCustomReference.MANUAL_REVIEW)){
					String refValue=customRef.getBuyerRefValue();
					if(null!=refValue && !(StringUtils.isBlank(refValue))){
						manualReviewRequired = true;
					}
				}
			}
		}
		if (!buyerFeatureLookup.isInitialized()) {
	            throw new ServiceOrderException("Unable to lookup buyer feature . BuyerFeatureLookup not initialized.");
	    }
		Long buyerId = so.getBuyerId();
		if(null!=postFromFrontend && postFromFrontend.equals("true")){
			openExecution.setVariable(ProcessVariableUtil.FE_POST_ORDER,"false");
		}
		if(null!=saveAsDraft && saveAsDraft.equals("true")){
			openExecution.setVariable(ProcessVariableUtil.SAVE_AS_DRAFT,"false");
		}
		
		if((null != createWithOutTasks && null != jobCodeMismatch)&& (createWithOutTasks || jobCodeMismatch)){
			//reset the above process variables
			openExecution.setVariable(ProcessVariableUtil.CREATE_WITHOUT_TASKS, false);
			openExecution.setVariable(ProcessVariableUtil.JOBCODE_MISMATCH,false);
			removeValidationWarningsVariable(openExecution);
			return "WaitInDraftForValidJobcodes";
		}
		else if (hasValidationWarnings(openExecution)){
			logger.info("HAS VALIDATION WARNINGS");
            removeValidationWarningsVariable(openExecution);
            return "WaitInDraftForMoreInformation";
        }//Check mandatory customRef present for so creation via api
		else if(so.isCreatedViaAPI() && StringUtils.isNotBlank(mandatoryCustRefPresent) && mandatoryCustRefPresent.equals("false") && (!so.getBuyerId().equals(OFConstants.FACILITIES_BUYER))){
			logger.debug("Mandatory CustRef value is"+ mandatoryCustRefPresent+"returning waitinDraftMoreInformation");
        	return "WaitInDraftForMoreInformation";
        }
		else if((null!=postFromFrontend && postFromFrontend.equals("true"))||(null!=saveAsDraft && saveAsDraft.equals("true"))){
        	return "WaitInDraft";
        }
		//Non W2 priority 2 : Auto Route Order to Inhome Warranty Order Provider Firm
        else if(so.isCustomRefPresent() && so.isCreatedViaAPI()&& so.getBuyerId().equals(OFConstants.INHOME_BUYER_LONG)){
        	//Removed this line to resolve R16.0 post production issue
        	//openExecution.setVariable(ProcessVariableUtil.PROVIDER_POST,"post");
        	return "evaluateProviderForRecallOrder";
        }
		
		 
		//AUTO_POST_API_SOS :post sos through timer.this means block auto posting while creation.
		else if (buyerFeatureLookup.isActiveFeatureAssociatedWithBuyer(BuyerFeatureSetEnum.AUTO_POST_API_SO, buyerId) && !(saveAutoPostInd!= null && saveAutoPostInd.toString().equals("true")) 
				&& ((buyerFeatureLookup.isActiveFeatureAssociatedWithBuyer(BuyerFeatureSetEnum.TIER_ROUTE, buyerId)&& manualReviewRequired
						&& !(postFromFrontEndAction!= null && postFromFrontEndAction.toString().equals("true"))	
							)|| (!(buyerFeatureLookup.isActiveFeatureAssociatedWithBuyer(BuyerFeatureSetEnum.TIER_ROUTE, buyerId))))){
        	return "WaitInDraftAndAutoRouteTimer";
        }
		//SL-19019 prevent autorouting for assurant and facilities buyer
		else if (isEligibleForAutoRouting(openExecution) && (!so.getBuyerId().equals(OFConstants.FACILITIES_BUYER)) && (!so.getBuyerId().equals(OFConstants.ASSURANT_BUYER))) {
        	logger.info("Sl 18553in auto routing function");
            return "ProceedToPost";
        } 
           else if(saveAutoPostInd!= null && saveAutoPostInd.toString().equals("true") &&
        		((so.getRoutedResources() != null && !so.getRoutedResources().isEmpty()) || (so.getTierRoutedResources() != null && !so.getTierRoutedResources().isEmpty())))
      {
        logger.info("Sl 18553 Value of service date 1 before proceed to post"+so.getSchedule().getServiceDate1());
     	return "ProceedToPost";
        }
        else if(isUpdate!=null && isUpdate.equals("true") && (changes!=null && changes.contains(SOFieldsChangedType.TASKS_ADDED)||changes.contains(SOFieldsChangedType.TASKS_DELETED) || changes.contains(SOFieldsChangedType.SERVICE_LOCATION)
				|| changes.contains(SOFieldsChangedType.SCHEDULE)))
        {
        	logger.info("Sl 18553 in is update function");
        	if(!(buyerFeatureLookup.isActiveFeatureAssociatedWithBuyer(BuyerFeatureSetEnum.TIER_ROUTE, buyerId))){
        		openExecution.setVariable(ProcessVariableUtil.PROVIDER_POST,"post");
        	}
        	return "ProceedToPost";
        }
        else if((null!=postFromFrontEndAction && postFromFrontEndAction.toString().equals("true"))){
        	logger.info("inside workflow control or post from frontend");
        	return "ProceedToPost";
        }
        else {
            return "WaitInDraft";
        }
    }

    private boolean isEligibleForAutoRouting(OpenExecution openExecution) {
        AutoRoutingBehavior autoRoutingBehavior = ProcessVariableUtil.getAutoRoutingBehavior(openExecution.getVariables());
        return !(autoRoutingBehavior == AutoRoutingBehavior.None);
    }
    
    private boolean hasValidationWarnings(OpenExecution openExecution){
        Boolean hasWarnings = (Boolean) openExecution.getVariable(ProcessVariableUtil.SERVICE_ORDER_HAS_VALIDATION_WARNINGS);
        if (null != hasWarnings) return hasWarnings.booleanValue();
        return false;
    }
    private void removeValidationWarningsVariable(OpenExecution openExecution){
        openExecution.removeVariable(ProcessVariableUtil.SERVICE_ORDER_HAS_VALIDATION_WARNINGS);    
    }

	public QuickLookupCollection getQuickLookupCollection() {
		return quickLookupCollection;
	}

	public void setQuickLookupCollection(QuickLookupCollection quickLookupCollection) {
		this.quickLookupCollection = quickLookupCollection;
	}
}
