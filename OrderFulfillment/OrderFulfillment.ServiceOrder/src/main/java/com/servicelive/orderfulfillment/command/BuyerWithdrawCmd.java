package com.servicelive.orderfulfillment.command;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

import com.servicelive.domain.pendingcancel.PendingCancelHistory;
import com.servicelive.orderfulfillment.common.OFConstants;
import com.servicelive.orderfulfillment.domain.SOPriceChangeHistory;
import com.servicelive.orderfulfillment.domain.SOTask;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.util.PricingUtil;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;


public class BuyerWithdrawCmd extends SOCommand {

	@Override
	public void execute(Map<String, Object> processVariables) {
		

		ServiceOrder so = getServiceOrder(processVariables);
		
		PendingCancelHistory pendingCancelBuyerHistory=serviceOrderDao.getPendingCancelCriteria(OrderfulfillmentConstants.BUYER_ROLE_ID, so.getSoId());
		PendingCancelHistory pendingCancelHistory=new PendingCancelHistory();
		if(null!=pendingCancelBuyerHistory)
		{
		pendingCancelHistory.setComments(pendingCancelBuyerHistory.getComments());
		pendingCancelHistory.setPrice(pendingCancelBuyerHistory.getPrice());
		}
		pendingCancelHistory.setRoleId(OrderfulfillmentConstants.BUYER_ROLE_ID);
		pendingCancelHistory.setSoId(so.getSoId());
		pendingCancelHistory.setWithdrawFlag(true);
		
		Object adminUserName = processVariables
				.get(OrderfulfillmentConstants.PVKEY_ADMIN_USER_NAME);
		if (adminUserName != null) {
			pendingCancelHistory.setAdminUserName(adminUserName.toString());
		}

		Object adminResourceId = processVariables
				.get(OrderfulfillmentConstants.PVKEY_ADMIN_USER_ID);
		if (adminResourceId != null && !adminResourceId.toString().equals("")) {
			pendingCancelHistory.setAdminResourceId(Integer
					.parseInt(adminResourceId.toString()));
		}

		Object userName = processVariables
				.get(OrderfulfillmentConstants.PVKEY_MODIFIED_USER_NAME);
		if (userName != null) {
			pendingCancelHistory.setModifiedBy(userName.toString());
		}

		Object userId = processVariables
				.get(OrderfulfillmentConstants.PVKEY_MODIFIED_USER_ID);
		if (userId != null && !userId.toString().equals("")) {
			pendingCancelHistory.setUserId(Integer.parseInt(userId.toString()));
		}
		
		processVariables.put("SOID",so.getSoId());
		PendingCancelHistory pendingCancelWithdrawHistory=serviceOrderDao.getPendingCancelHistory(OrderfulfillmentConstants.BUYER_ROLE_ID, so.getSoId());

		if(null!=pendingCancelWithdrawHistory && null!=pendingCancelWithdrawHistory.getPrice())
		{
			NumberFormat formatter = new DecimalFormat("###0.00");
			String cancelProviderPrice=formatter.format(pendingCancelWithdrawHistory.getPrice());
			processVariables.put("withdrawAmount",cancelProviderPrice)	;
		}
		if(null!=pendingCancelWithdrawHistory && null!=pendingCancelWithdrawHistory.getModifiedDate())
		{
			 DateFormat formatterDate = new SimpleDateFormat( "MM/dd/yyyy h:mm a" );
		     String strDate = formatterDate.format(pendingCancelWithdrawHistory.getModifiedDate());
		     processVariables.put(OrderfulfillmentConstants.PENDING_CANCEL_REQUEST_DATE, strDate);
		}
		processVariables.put(OrderfulfillmentConstants.PVKEY_CANCELLATION_COMMENT, "Buyer has withdrawn Pending Cancel Request");
		serviceOrderDao.pendingCancelUpdation(pendingCancelHistory);
		
		// for service order task updation
	/*	if(so.getPriceType().equalsIgnoreCase(OrderfulfillmentConstants.TASK_LEVEL_PRICING)){

		double totalFinalPriceOfTasks = 0.0;

		for (SOTask task : so.getActiveTasks()) {

			if (task.getFinalPrice() != null && (task.getTaskType().equals(
					OrderfulfillmentConstants.PRIMARY_TASK)
					|| task.getTaskType().equals(
							OrderfulfillmentConstants.NON_PRIMARY_TASK)
					 )) {

				totalFinalPriceOfTasks = totalFinalPriceOfTasks
						+ task.getFinalPrice().doubleValue();
			}
		}

		double additionalAmount = 0.0;
		BigDecimal soPrice = so.getSpendLimitLabor();
		if (null!=pendingCancelBuyerHistory && null!=pendingCancelBuyerHistory.getPrice()) {
			additionalAmount = pendingCancelBuyerHistory.getPrice().doubleValue() - soPrice.doubleValue();
		}

		if (additionalAmount != 0 && totalFinalPriceOfTasks > 0) {
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

				}
			}
		}
	} */
		if(null!=pendingCancelBuyerHistory)
		{
			so.setSpendLimitLabor(new BigDecimal(pendingCancelBuyerHistory.getPrice()));
			so.setSpendLimitParts(new BigDecimal(0.0));
			so.setFinalPriceLabor(new BigDecimal(pendingCancelBuyerHistory.getPrice()));
			so.setFinalPriceParts(new BigDecimal(0.0));
			so.getPrice().setDiscountedSpendLimitLabor(new BigDecimal(pendingCancelBuyerHistory.getPrice()).setScale(2, RoundingMode.HALF_UP));
			
			serviceOrderDao.update(so);
		}
		
		
		
	}

}
