package com.servicelive.orderfulfillment.command;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;

public class AutoCloseRulesExecutionCmd extends SOCommand {
	private static final String officeMaxBuyer = "1953";

	@Override
	public void execute(Map<String, Object> processVariables) {
		ServiceOrder serviceOrder = getServiceOrder(processVariables);
       
		//begining of auto close logic.
		int awaitingPayment=1;
		int pendingTransaction=0;
		int maxLimit=0;
		int serviceincomplete=0;
		int firmListed=0;
		int providerListed=0;
		int sameDayOrder=0;
		Integer ruleExecution=0;
		//AUTO_CLOSE_FAILURE_RULES
		String autoCloseFailureRules="";
		
	
	
		/*if(serviceOrder.getAdditionalPayment()!=null && serviceOrder.getAdditionalPayment().getPaymentType()!=null)
		{
			if(serviceOrder.getAdditionalPayment().getPaymentType().equals("CA"))
		{
			awaitingPayment=0;	
			
		}
		else
		{
			awaitingPayment=1;	
		}
		}*/
		
		if(serviceOrder.getAdditionalPayment()!=null && serviceOrder.getAdditionalPayment().getCheckNumber()!=null)
		{
			awaitingPayment=0;	
		}
		
		
		
		boolean isReconciled=walletGateway.areAllValueLinkTransactionsReconciled(serviceOrder.getSoId());
		/*boolean isAchPending=walletGateway.isACHTransPending(serviceOrder.getSoId());
		if(isReconciled && isAchPending)
		{
			pendingTransaction=1;	
		}else
		{
			pendingTransaction=0;
		}*/
		
		
		if(isReconciled || officeMaxBuyer.equals(serviceOrder.getBuyerId().toString()))
		{
			pendingTransaction=1;	
		}else
		{
			pendingTransaction=0;
		}
	  /*Object isAutoClosePending = processVariables.get(OrderfulfillmentConstants.AUTO_CLOSE_PENDING_TRANSACTION);
		if(isAutoClosePending!=null && isAutoClosePending.toString().equals("pending"))
		{
			pendingTransaction=0;
		}*/
		
		List<Integer> autoCloseFirmList=serviceOrderDao.autoCloseFirmExclusionList(serviceOrder.getBuyerId());
		
		//logger.info("autoCloseFirmList.size()"+autoCloseFirmList.size());
		//logger.info("serviceOrder.getAcceptedProviderId().intValue()"+serviceOrder.getAcceptedProviderId().intValue());
		
		
		 if (autoCloseFirmList==null || autoCloseFirmList.size()==0) 
			 {
			 firmListed=1;
			 }
		 else
		 {
			if( autoCloseFirmList.contains(serviceOrder.getAcceptedProviderId().intValue()))
			{
				firmListed=0;
			}
			else
			{
				firmListed=1;	
			}
		 }
		 
		 List<Integer> autoCloseProviderList=serviceOrderDao.autoCloseProviderExclusionList(serviceOrder.getBuyerId());
		
		 if (autoCloseProviderList==null || autoCloseProviderList.size()==0) 
			 {
			 providerListed=1;
			 }
		 else
		 {
			if( autoCloseProviderList.contains(serviceOrder.getAcceptedProviderResourceId().intValue()))
			{
				providerListed=0;
			}
			else
			{
				providerListed=1;
			}
		 }
		 
		 String ruleValue="";
		 List<String> autoCloseCriteriaValue;
		 autoCloseCriteriaValue=serviceOrderDao.autoCloseCriteriaValue(serviceOrder.getBuyerId());
		 ruleValue=autoCloseCriteriaValue.get(0);
		
		double totalAddonPrice=0.0;
		 if(serviceOrder.getTotalAddon()!=null)
		 {
			 totalAddonPrice=serviceOrder.getTotalAddon().doubleValue();
		 }
		 double totalPrice=0.00;
		 totalPrice=totalAddonPrice+serviceOrder.getFinalPriceTotal().doubleValue();
		 
		
		if (autoCloseCriteriaValue==null || autoCloseCriteriaValue.size()==0) 
		 {
			maxLimit=0;
		 }
		else
		{
		if( Double.parseDouble(autoCloseCriteriaValue.get(0)) < totalPrice)
		{
			maxLimit=0;
		}
		else
		{
			maxLimit=1;
		}
	 }
		
		
		 int serviceIncompleteOrder=0;
			Object isAutoClose = processVariables.get(OrderfulfillmentConstants.PVKEY_AUTOCLOSE);
			if(isAutoClose!=null && isAutoClose.toString().equals("autoClose"))
			{
				serviceIncompleteOrder=1;	
			}
			if(serviceIncompleteOrder==1 && serviceOrder.getFinalPriceTotal().doubleValue()>0)
			{
				serviceincomplete=0;	
			}
			else
			{
				serviceincomplete=1;
			}
			
			String currentDate="";
			String postedDate="";
			Date routedDate=serviceOrder.getRoutedDate();
			logger.info("routedDate"+routedDate);    
			DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			currentDate = sdf.format(new Date());
			postedDate =sdf.format(routedDate);
			logger.info("currentDate"+currentDate);
			logger.info("postedDate"+postedDate);
			Date current=new Date(currentDate);
			Date posted=new Date(postedDate);
			logger.info("current"+current);
			logger.info("posted"+posted);
			if(posted.before(current) || officeMaxBuyer.equals(serviceOrder.getBuyerId().toString()))
			{
				sameDayOrder=1;
			}
			else
			{
				sameDayOrder=0;
			}
			logger.info("new rule"+sameDayOrder);
			
		/*logger.info("awaitingPayment"+awaitingPayment);
		logger.info("pendingTransaction"+pendingTransaction);
		logger.info("maxlimit"+maxLimit);
		logger.info("serviceincomplete"+serviceincomplete);
		logger.info("firmListed"+firmListed);
		logger.info("providerListed"+providerListed);*/
		
		logger.info("AutoCloseRulesExecutionCmd:Rule values --------------");
		logger.info("awaitingPayment="+awaitingPayment+",pendingTransaction="+pendingTransaction+",maxLimit="+maxLimit+",serviceincomplete="+serviceincomplete+",firmListed="+firmListed+",providerListed="+providerListed+",sameDayOrder="+sameDayOrder);
		String autoCloseStatus="";
		ruleExecution= awaitingPayment*pendingTransaction*maxLimit*serviceincomplete*firmListed*providerListed*sameDayOrder;
		
		
		logger.info("ruleExecution"+ruleExecution);
		
	if(awaitingPayment==0)
		{
			autoCloseFailureRules=autoCloseFailureRules+" AWAITING PAYMENT";
		}
	if(pendingTransaction==0 && autoCloseFailureRules.length()>0)
	{
		autoCloseFailureRules=autoCloseFailureRules+" , PENDING TRANSACTION";
	}
	if(pendingTransaction==0 && autoCloseFailureRules.length()<1)
	{
		autoCloseFailureRules=autoCloseFailureRules+" PENDING TRANSACTION";
	}
	
	if(serviceincomplete==0 && autoCloseFailureRules.length()>0)
	{
		autoCloseFailureRules=autoCloseFailureRules+" , SERVICE INCOMPLETE";
	}
	if(serviceincomplete==0 && autoCloseFailureRules.length()<1)
	{
		autoCloseFailureRules=autoCloseFailureRules+" SERVICE INCOMPLETE";
	}
	if(maxLimit==0 && autoCloseFailureRules.length()>0)
	{
		autoCloseFailureRules=autoCloseFailureRules+" , MAX LIMIT";
	}
	if(maxLimit==0 && autoCloseFailureRules.length()<1)
	{
		autoCloseFailureRules=autoCloseFailureRules+" MAX LIMIT";
	}
	
	if(firmListed==0 && autoCloseFailureRules.length()>0)
	{
		autoCloseFailureRules=autoCloseFailureRules+" , FIRM LISTED";
	}
	if(firmListed==0 && autoCloseFailureRules.length()<1)
	{
		autoCloseFailureRules=autoCloseFailureRules+" FIRM LISTED";
	}
	
	if(providerListed==0 && autoCloseFailureRules.length()>0)
	{
		autoCloseFailureRules=autoCloseFailureRules+" , PROVIDER LISTED";
	}
	if(providerListed==0 && autoCloseFailureRules.length()<1)
	{
		autoCloseFailureRules=autoCloseFailureRules+" PROVIDER LISTED";
	}
	if(sameDayOrder==0 && autoCloseFailureRules.length()>0)
	{
		autoCloseFailureRules=autoCloseFailureRules+" , SAME DAY ORDER";
	}
	if(sameDayOrder==0 && autoCloseFailureRules.length()<1)
	{
		autoCloseFailureRules=autoCloseFailureRules+" SAME DAY ORDER";
	}
	
	/*autoCloseFailureRules=autoCloseFailureRules.replace(',', '~');
	autoCloseFailureRules=autoCloseFailureRules.replace('~', ' ');
	autoCloseFailureRules=autoCloseFailureRules.trim();
	autoCloseFailureRules=autoCloseFailureRules.replace(" ",", ");
	*/
	String finalLaborPrice="0.00";
	if(serviceOrder.getFinalPriceLabor()!=null)
	{
		finalLaborPrice=serviceOrder.getFinalPriceLabor().toString();
	}
	String finalPartsPrice="0.00";
	if(serviceOrder.getFinalPriceParts()!=null)
	{
	finalPartsPrice=serviceOrder.getFinalPriceParts().toString();
	if(finalPartsPrice!=null){
		if(finalPartsPrice.indexOf(".") == -1){
		finalPartsPrice = finalPartsPrice + ".00";
	}
		else{
		int decimalIndex = finalPartsPrice.indexOf(".");
		int len=finalPartsPrice.length();
		int diff=len - decimalIndex;
		if(diff<=2){
			finalPartsPrice =finalPartsPrice + "0";
		}
		}
		}
	
	}
	DecimalFormat twoDForm = new DecimalFormat("#.##");
	String finalPrice=twoDForm.format(totalPrice);
	//new fix
	if(finalPrice!=null){
	if(finalPrice.indexOf(".") == -1){
	finalPrice = finalPrice + ".00";
	}
	else{
	int decimalIndex = finalPrice.indexOf(".");
	int  len=finalPrice.length();
	int diff=len - decimalIndex;
	if(diff<=2){
		finalPrice =finalPrice + "0";
	}
	}
	}
	processVariables.put(OrderfulfillmentConstants.FINAL_PRICE_LABOR,finalLaborPrice);
	processVariables.put(OrderfulfillmentConstants.FINAL_PRICE_PARTS,finalPartsPrice);
	processVariables.put(OrderfulfillmentConstants.FINAL_PRICE,finalPrice);

	processVariables.put(OrderfulfillmentConstants.AUTO_CLOSE_FAILURE_RULES,autoCloseFailureRules);
		
		if(ruleExecution.intValue()==1)
		{
			autoCloseStatus="Passed";
			processVariables.put(OrderfulfillmentConstants.PVKEY_AUTOCLOSE_SO,"autoClosed");
			String autoCloseSucessRules=" AWAITING PAYMENT, PENDING TRANSACTION, SERVICE INCOMPLETE, MAX LIMIT, FIRM LISTED, PROVIDER LISTED and SAME DAY ORDER";

			processVariables.put(OrderfulfillmentConstants.AUTO_CLOSE_SUCESS_RULES,autoCloseSucessRules);

		}
		else
		{
			 autoCloseStatus="Failed";
		}
		
		 List<Integer> autoClosingRuleStatus=new ArrayList<Integer>();
		
		 if(officeMaxBuyer.equals(serviceOrder.getBuyerId().toString())){
			 autoClosingRuleStatus.add(serviceincomplete);
			 autoClosingRuleStatus.add(maxLimit);
			 autoClosingRuleStatus.add(firmListed);
			 autoClosingRuleStatus.add(providerListed); 
		 }
		 else{
		 autoClosingRuleStatus.add(awaitingPayment);
		 autoClosingRuleStatus.add(pendingTransaction);
		 autoClosingRuleStatus.add(serviceincomplete);
		 autoClosingRuleStatus.add(maxLimit);
		 autoClosingRuleStatus.add(firmListed);
		 autoClosingRuleStatus.add(providerListed);
		 autoClosingRuleStatus.add(sameDayOrder);
		 }
		 List<String> autoCloseRuleStatus=new ArrayList<String>();
		 	for(int j=0;j<autoClosingRuleStatus.size();j++)
		 	{
		 		if(autoClosingRuleStatus.get(j).intValue()==0)
		 		{
		autoCloseRuleStatus.add("Yes");
		
		 		}
		 		else
		 		{
		autoCloseRuleStatus.add("No");
		 		}
		 	}
		 //	double cancelledPrice=serviceOrder.getFinalPriceTotal().doubleValue();
		 	
		 	
			if(serviceIncompleteOrder==1 && serviceOrder.getFinalPriceTotal().doubleValue()<=0)
			{
				processVariables.put(OrderfulfillmentConstants.CANCELLED_FIXED_PRICE,"0.00");		
			}
			else
			{
			 serviceOrderDao.autoCloseUpdation(serviceOrder.getSoId(), autoCloseStatus);
			 Integer autoClosingId=serviceOrderDao.getAutoCloseId(serviceOrder.getSoId());
			 serviceOrderDao.autoCloseRuleUpdation(autoClosingId,autoCloseRuleStatus,ruleValue, serviceOrder.getBuyerId());
			}
			 
			
	}

}
