package com.servicelive.orderfulfillment.command;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Map;
import com.servicelive.domain.pendingcancel.PendingCancelHistory;
import com.servicelive.orderfulfillment.common.OFConstants;
import com.servicelive.orderfulfillment.domain.SOPriceChangeHistory;
import com.servicelive.orderfulfillment.domain.SOTask;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.util.PricingUtil;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;


public class ProviderAgreeCmd extends SOCommand {

	@Override
	public void execute(Map<String, Object> processVariables) {
		

		ServiceOrder so = getServiceOrder(processVariables);
		int noOfTasks =0;
		PendingCancelHistory pendingCancelBuyerHistory = serviceOrderDao
		.getPendingCancelHistory(OrderfulfillmentConstants.BUYER_ROLE_ID, so.getSoId());
		
		
		
		processVariables.put("SOID",so.getSoId());
		
		// for service order task updation
		if(so.getPriceType().equalsIgnoreCase(OrderfulfillmentConstants.TASK_LEVEL_PRICING)){

		double totalFinalPriceOfTasks = 0.0;

		for (SOTask task : so.getActiveTasks()) {

			if (task.getFinalPrice() != null && (task.getTaskType().equals(
					OrderfulfillmentConstants.PRIMARY_TASK)
					|| task.getTaskType().equals(
							OrderfulfillmentConstants.NON_PRIMARY_TASK)
					 )) {

				totalFinalPriceOfTasks = totalFinalPriceOfTasks
						+ task.getFinalPrice().doubleValue();
				noOfTasks +=1;
			}
		}

		double additionalAmount = 0.0;
		// BigDecimal soPrice = so.getSpendLimitLabor();
		double price=0.0d;
		for (SOTask task : so.getActiveTasks()) {
			if(task.getPrice()!=null)
			{
				price=price+ task.getPrice().doubleValue();
			}			
		}
		if (null != pendingCancelBuyerHistory
				&& null != pendingCancelBuyerHistory.getPrice()) {
			additionalAmount = pendingCancelBuyerHistory.getPrice()-price;
					
		}

		if (additionalAmount !=0 ) {
			for (SOTask task : so.getActiveTasks()) {

				if (task.getFinalPrice() != null && (task.getTaskType().equals(
						OrderfulfillmentConstants.PRIMARY_TASK)
						|| task.getTaskType().equals(
								OrderfulfillmentConstants.NON_PRIMARY_TASK)
						 )) {

					double amount = 0; 

					// divide new somaxlabor(the total price for tasks alone
					// excluding permit task) among tasks.
					if(null!=task.getFinalPrice()  && totalFinalPriceOfTasks >0) {
						double percentageShare = task.getFinalPrice()
								.doubleValue()
								/ totalFinalPriceOfTasks * 100;
						amount = additionalAmount * percentageShare / 100;
						amount = task.getFinalPrice().doubleValue() + amount;
						task.setFinalPrice(BigDecimal.valueOf(amount));
						PricingUtil.addTaskPriceHistory(task,"SYSTEM","SYSTEM");
					}
					// divide additionalAmount equally among skus in case of the initial total task price is 0 
					else if(null!=task.getFinalPrice() && noOfTasks>0){
						amount = additionalAmount /noOfTasks;
						amount = task.getFinalPrice().doubleValue() + amount;
						task.setFinalPrice(BigDecimal.valueOf(amount));
						PricingUtil.addTaskPriceHistory(task,"SYSTEM", "SYSTEM");
					}

				}
			}
		}
	}
		if(null!=pendingCancelBuyerHistory && null!=pendingCancelBuyerHistory.getPrice())
		{
			NumberFormat formatter = new DecimalFormat("###0.00");
			String cancelProviderPrice=formatter.format(pendingCancelBuyerHistory.getPrice());
		processVariables.put(OrderfulfillmentConstants.PVKEY_PREVIOUS_CANCELLATION_AMT,cancelProviderPrice);
		so.setSpendLimitLabor(new BigDecimal(pendingCancelBuyerHistory.getPrice()));
		so.setSpendLimitParts(new BigDecimal(0.0));
		so.setFinalPriceLabor(new BigDecimal(pendingCancelBuyerHistory.getPrice()));
		so.setFinalPriceParts(new BigDecimal(0.0));
		so.getPrice().setDiscountedSpendLimitLabor(new BigDecimal(pendingCancelBuyerHistory.getPrice()).setScale(2, RoundingMode.HALF_UP));
		so.getPrice().setDiscountedSpendLimitParts(new BigDecimal(0.0));
		so.getPrice().setTotalAddonPriceGL(new BigDecimal(0.0));
		
		/** 
		* SL-18007 setting so price change history if there is a change in the so spend limit labour 
		* during cancellation 
		*/
		if (null != so.getSoPriceChangeHistory()){
			logger.info("18007: so price change history contins:" + so.getSoPriceChangeHistory());
								
			SOPriceChangeHistory newSOPriceChangeHistory = new SOPriceChangeHistory();
			so.setFromCancelFlow(true);
			//set specific components before passing to the generic method
			newSOPriceChangeHistory.setAction(OFConstants.ORDER_CANCELLED);
			newSOPriceChangeHistory.setReasonComment(null);
			
			Object adminUserName = processVariables.get(OrderfulfillmentConstants.PVKEY_ADMIN_USER_NAME);			
			Object adminResourceId = processVariables.get(OrderfulfillmentConstants.PVKEY_ADMIN_USER_ID);			
			Object userName = processVariables.get(OrderfulfillmentConstants.PVKEY_MODIFIED_USER_NAME);			
			Object userId = processVariables.get(OrderfulfillmentConstants.PVKEY_MODIFIED_USER_ID);
			
			// call generic method to save so price change history
			if (null != adminUserName && null != adminResourceId ){
				PricingUtil.addSOPriceChangeHistory(so,newSOPriceChangeHistory, adminResourceId.toString(), adminUserName.toString());	
			}else if (null != userName && null != userId){
				PricingUtil.addSOPriceChangeHistory(so,newSOPriceChangeHistory, userId.toString(), userName.toString());	
			}					
		}
		
		serviceOrderDao.update(so);
		}
		
	}
	

}
