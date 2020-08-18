package com.servicelive.orderfulfillment.common;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;

import org.apache.log4j.Logger;

import com.servicelive.orderfulfillment.domain.type.AutoRoutingBehavior;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;

public class ProcessVariableUtil {
	
	public static final String PVKEY_SERVICE_ORDER_IDS_FOR_GROUP_PROCESS = "serviceOrderIdsForGroupProcess";
	public static final String PVKEY_GROUP_ID = "so_group_id";
	public static final String PVKEY_SVC_ORDER_ID = "order";
	public static final String PVKEY_SVC_ORDER_TYPE = "serviceOrderType";
	public static final String PVKEY_WALLET_MESSAGE_ID = "walletMessageId";
    public static final String PVKEY_BUYER_STATE_CODE = "buyerStateCode";
    public static final String PVKEY_FUNDING_TYPE = "fundingType";
	public static final String PVKEY_USER_NAME = "userName";
    public static final String PVKEY_LEDGER_POST_FEE = "ledgerPostingFee";
    public static final String PVKEY_LEDGER_POST_TX_ID = "ledgerPostTransactionId";
    public static final String PVKEY_LEDGER_SPEND_LMT_RSRV_AMT = "ledgerSpendLimitRsrvAmt";
    public static final String PVKEY_LEDGER_SPEND_LMT_RSRV_TX_ID = "ledgerSpendLimitRsrvTransactionId";
    public static final String PVKEY_LEDGER_CANCEL_PNLTY_AMT = "ledgerCancellationPenaltyAmt";
    public static final String PVKEY_LEDGER_CANCEL_PNLTY_TX_ID = "ledgerCancellationPenaltyTransactionId";
    public static final String PVKEY_AUTO_ROUTING_BEHAVIOR = "autoRoutingBehavior";
    public static final String PVKEY_AUTO_ROUTING_BEHAVIOR_NAME = "autoRoutingBehaviorName";
    public static final String PVKEY_SERVICE_URL="slbucksagreementurl";
     
    public static final String PVKEY_IDENTIFICATION = "id";
	public static final String PVKEY_NUMBER_OF_SERVICE_ORDER = "NumberOfServiceOrder";
	public static final String PVKEY_EFFECTIVE_SERVICE_ORDER = "EffectiveServiceOrderId";
	public static final String PVKEY_FUNDS_CONFIRMED_TX_ID = "fundsConfirmedTransactionId";
	public static final String PVKEY_TRANSACTION_AMOUNT = "transactionAmount";
	public static final String GROUP_EXPIRATION_TIME = "groupExpirationTime";
    public static final String AUTO_ROUTING_TIER = "autoRoutingTier";
    public static final String AUTO_CLOSE = "autoClose";
    public static final String CANCEL_NO_FEE = "cancelNoFee";
    public static final String SERVICE_ORDER_CHANGES = "ServiceOrderChanges";
    public static final String MAIN_JOB_CODE = "MainJobCode";
    public static final String SERVICE_DATE = "serviceDate";
    public static final String ROUTING_TIME = "routingTime";
    public static final String SERVICE_EXPIRE_DATE = "serviceExpireDate";
    public static final String AUTO_TIER_ROUTE_DATE = "autoTierRouteDate";
    public static final String NEXT_TIER_TO_BE_ROUTED_IS = "nextTierToBeRoutedIs"; 
    public static final String NEXT_TIER_DUE_DATE = "nextTierDueDate"; 
    public static final String CONDITIONAL_AUTO_ROUTING_RULE_ID = "condAutoRouteRuleId";
    public static final String CONDITIONAL_AUTO_ROUTING_RULE_NAME = "condAutoRouteRuleName";
    public static final String GROUP_QUEUE_EXPIRATION = "groupQueueExpirationDate";
    public static final String PROVIDER_LIST = "provider_list";
    public static final String PROVIDER_LIST_SIZE = "provider_list_size";
    public static final String SERVICE_ORDER_HAS_VALIDATION_WARNINGS = "serviceOrderHasValidationWarnings";
    public static final String SERVICE_ORDER_HAS_MANDATORY_CUSTOM_REFERENCE = "serviceOrderHasMandatoryCustomReference";
	public static final String PVKEY_WALLET_JMS_MESSAGE_ID = "jmsMessageId";
    public static final String CUSTOM_REFERENCE = "customReference";
    public static final String USE_HOLD_TIME_FOR_QUEUE = "useHoldTimeForQueue";
    public static final String PVKEY_CREDIT_CARD_TYPE = "creditCardType";
    public static final String PVKEY_CREDIT_CARD_NUMBER = "creditCardNumber";
    public static final String AUTO_POST_IND = "autoPost";
    public static final String TO_DRAFT = "toDraft";
    public static final String CAR_RULE_CHANGED= "ruleIdChangeInd";
    public static final String REPOST = "isRepost";
    public static final String ISUPDATE = "isUpdate";
    public static final String SAVE_AND_AUTOPOST = "isSaveAutoPost";
    public static final String PROVIDER_POST="providerPost";
    public static final String ROUTE_DATE="routeDate";
    public static final String TEMPLATE_NAME="templateName";
    public static final String FE_POST_ORDER="FE_POST_ORDER";
    public static final String SAVE_AS_DRAFT = "saveAsDraft";
    public static final String IS_SPENDLIMIT_INC = "isSpendLimitIncreased";
    //For the email to be send to Provider when an SO is Closed
    public static final String PVKEY_LEDGER_TRANID_PAY = "ledgerTranIdPay";
    public static final String PVKEY_TRANS_AMOUNT_PAY = "transAmtPay";
    public static final String PVKEY_LEDGER_TRANID_FEE = "ledgerTranIdFee";
    public static final String PVKEY_TRANS_AMOUNT_FEE = "transAmtFee";
    public static final String AGREED_AMOUNT="agreedAmount";
    public static final String DISAGREED_AMOUNT="disagreedAmount";
    public static final String CREATE_WITHOUT_TASKS="createWithoutTasks";
    public static final String JOBCODE_MISMATCH="jobcodeMisMatch";
    //SL-18226
    public static final String PROVIDERS_IN_CURRENT_TIER = "provInCurrentTier";
    public static final String ROUTING_PRIORITY_IND = "routingPriorityInd";
    public static final String PROVIDERS_IN_PREVIOUS_TIERS = "provInPreviousTiers";
    public static final String PERF_CRITERIA_LEVEL = "perfCriteriaLevel";
    public static final String ROUTING_PRIORITY_STATUS = "routingPriorityStatus";
    public static final String FRONTEND_POSTING = "frontEndPosting";
    public static final String OLD_SPN_ID = "oldSpnId";
    public static final String OVERFLOW_ELIGIBLE = "ofEligible";
    public static final String FE_POST = "fePost";
    public static final String NEXT_TIER = "nextTier";
    public static final String ROUTING_BEHAVIOUR = "routingBehaviour";
    public static final String RELEASE_ACTION_PERFORMED_IND = "ReleaseActionPerformedInd";
    public static final String POST_FROM_FRONTEND_ACTION = "postFromFrontEndAction";
    public static final String PVKEY_LEAD_ID = "leadId";
    public static final String REJECTED_BY_ALL_PROV = "rejectedByAllProv";
    public static final String COUNTER_OFFER_BY_ALL_PROV = "counterOfferByAllProv";
    public static final String NEXT_TIER_TO_BE_ROUTED = "nextTierToBeRouted";
    public static final String COUNT_OF_PROV_FOR_CURRENT_TIER= "countTotalNumberOfProvidersForCurrentTier";
    public static final String BUYER_ID= "buyerID";
    public static final String OVERFLOW_TIER_IND= "overFlowTierInd";
    public static final String IS_OTHER_MAJOR_CHANGES= "isOtherMajorChanges";

    public static final String SO_ID_FOR_GROUP="serviceOrderIdsForGroupProcess";
    
    /*** SL-20647 ***/
    public static final String  PVKEY_MANUAL_COUNT = "manualCount";
    public static final String  PVKEY_ESTMAXLIMIT_COUNT = "estMaxLimitCount";
    public static final String  PVKEY_PARTS_COUNT = "partsCount";
	public static final String IS_CURRENTLY_POSTED = "isCurrentlyPosted";
	public static final String PVKEY_IS_UPDATE_BATCH_GROUP = "isUpdateBatchGroup";
	public static final String PVKEY_UPDATE_BATCH_TRANSITION = "isUpdateBatchTransition";
	public static final String PVKEY_ORDER_GROUP_STATUS = "orderGroupStatus";
	public static final String IS_GROUP_UPDATE = "isGroupUpdate";
	public static final String PVKEY_UPDATED_SERVICE_ORDER = "updatedServiceOrderId";
	public static final String REPEAT_REPAIR_NOTE = "repeatRepairNote";
	public static final String INHOME_AUTO_ACCEPT = "inhomeAutoAccept";
	public static final String ORIGINAL_SO_ID= "original_so_id";


    /******  service order id  ******/
	public static void addServiceOrderId(Map<String,Object> processVariables, String serviceOrderId) {
		processVariables.put(PVKEY_SVC_ORDER_ID, serviceOrderId);
	}
	public static String extractServiceOrderId(Map<String,Object> processVariables) {
		return (String) processVariables.get(PVKEY_SVC_ORDER_ID);
	}
	
	public static String extractServiceOrderIdForGroup(Map<String,Object> processVariables) {
		return (String) processVariables.get(SO_ID_FOR_GROUP);
	}
	
	public static boolean mapContainsOrderId(Map<String,Object> processVariables ) {
		return processVariables.containsKey(PVKEY_SVC_ORDER_ID);
	}
	
	
	public static boolean mapContainsSoIdForGroup(Map<String,Object> processVariables ) {
		return processVariables.containsKey(SO_ID_FOR_GROUP);
	}
	
	public static void addRouteDate(Map<String,Object> processVariables, String serviceDate){
        processVariables.put(ROUTE_DATE, serviceDate);
    }
	
    public static void addServiceDate(Map<String,Object> processVariables, String serviceDate){
        processVariables.put(SERVICE_DATE, serviceDate);
    }
    
    public static void addRoutingTime(Map<String,Object> processVariables, String serviceDate){
        processVariables.put(ROUTING_TIME, serviceDate);
    }

    public static void addServiceExpireDate(Map<String,Object> processVariables, String serviceDate){
        processVariables.put(SERVICE_EXPIRE_DATE, serviceDate);
    }

    public static void addAutoTierRouteDate(Map<String,Object> processVariables, String autoTierRouteDate) {
        processVariables.put(AUTO_TIER_ROUTE_DATE, autoTierRouteDate);
    }

    public static String extractServiceDate(Map<String,Object> processVariables){
        return String.valueOf(processVariables.get(SERVICE_DATE));
    }

	/******  service order type  ******/
	public static void addServiceOrderType(Map<String,Object> processVariables, String serviceOrderId) {
		processVariables.put(PVKEY_SVC_ORDER_TYPE, serviceOrderId);
	}
	public static String extractServiceOrderType(Map<String,Object> processVariables) {
		return (String) processVariables.get(PVKEY_SVC_ORDER_TYPE);
	}

	public static String extractBuyerState(Map<String,Object> processVariables) {
		return (String) processVariables.get(PVKEY_BUYER_STATE_CODE);
	}	
    public static String extractProviderState(Map<String,Object> processVariables) {
        return (String) processVariables.get(OrderfulfillmentConstants.PVKEY_ACCEPTED_PROVIDER_STATE);
    }

    public static void setFarFutureDateInProcessVariables(Map<String, Object> pvars, String key) {
        Calendar farFutureDate = new GregorianCalendar(2100, Calendar.JANUARY, 1);
        String farFutureDateString = convertToJPDLDueDate(farFutureDate);
        pvars.put(key, farFutureDateString);
    }

    public static AutoRoutingBehavior getAutoRoutingBehavior(Map<String, Object> processVariables){
        if(null != processVariables && null != processVariables.get(PVKEY_AUTO_ROUTING_BEHAVIOR)){
            Object obj = processVariables.get(PVKEY_AUTO_ROUTING_BEHAVIOR);
            if(obj instanceof AutoRoutingBehavior){
                return (AutoRoutingBehavior) obj;
            }else {
                return AutoRoutingBehavior.valueOf((String)obj);
            }
        }
        return AutoRoutingBehavior.None;
    }

    public static void setAutoRoutingBehavior(Map<String, Object> processVariables, AutoRoutingBehavior autoRoutingBehavior){
    	processVariables.put(PVKEY_AUTO_ROUTING_BEHAVIOR, autoRoutingBehavior.name());  
    }
    
    public static String convertToJPDLDueDate(Calendar calendar){
    	return String.format("date=%1$tH:%1$tM %1$tm/%1$td/%1$tY", calendar);
    }
    
    /********** Lead *************/
    public static void addLeadId(Map<String,Object> processVariables, String leadId) {
		processVariables.put(PVKEY_LEAD_ID, leadId);
	}
    
    public static String extractLeadId(Map<String,Object> processVariables) {
		return (String) processVariables.get(PVKEY_LEAD_ID);
	}
	public static boolean mapContainsLeadId(Map<String,Object> processVariables ) {
		return processVariables.containsKey(PVKEY_LEAD_ID);
	}
}
