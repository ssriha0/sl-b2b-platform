package com.servicelive.orderfulfillment.command;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Map;
import com.servicelive.domain.pendingcancel.PendingCancelHistory;
import com.servicelive.orderfulfillment.domain.SOTask;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.util.PricingUtil;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;


public class BuyerDisagreeCmd extends SOCommand {

	@Override
	public void execute(Map<String, Object> processVariables) {
		

		ServiceOrder so = getServiceOrder(processVariables);
		
		PendingCancelHistory pendingCancelHistory = new PendingCancelHistory();

		Object comments = processVariables
				.get(OrderfulfillmentConstants.PVKEY_CANCELLATION_COMMENT);
		if (comments != null) {
			pendingCancelHistory.setComments(comments.toString());
		}
		Object price = processVariables
				.get(OrderfulfillmentConstants.PVKEY_RQSTD_ACTIVE_CANCELLATION_AMT);
		if (price != null) {
			NumberFormat formatter = new DecimalFormat("###0.00");
			String cancelPrice=formatter.format(Double.parseDouble(price.toString()));
			processVariables
			.put(OrderfulfillmentConstants.PVKEY_RQSTD_ACTIVE_CANCELLATION_AMT,cancelPrice);
            pendingCancelHistory.setPrice(Double.parseDouble(price.toString()));
		}

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
			processVariables.put(OrderfulfillmentConstants.PVKEY_PENDING_CANCEL_FIRMNAME ,userName.toString());

		}

		Object userId = processVariables
				.get(OrderfulfillmentConstants.PVKEY_MODIFIED_USER_ID);
		if (userId != null && !userId.toString().equals("")) {
			pendingCancelHistory.setUserId(Integer.parseInt(userId.toString()));
		}

		pendingCancelHistory.setRoleId(OrderfulfillmentConstants.BUYER_ROLE_ID);
		pendingCancelHistory.setSoId(so.getSoId());
		pendingCancelHistory.setWithdrawFlag(false);
		pendingCancelHistory.setWfStateId(OrderfulfillmentConstants.PENDING_RESPONSE);

		PendingCancelHistory pendingCancelProviderHistory = serviceOrderDao
		.getPendingCancelHistory(OrderfulfillmentConstants.PROVER_ROLE_ID, so.getSoId());
		
		
		if(null!=pendingCancelProviderHistory && null!=pendingCancelProviderHistory.getPrice())
		{
			NumberFormat formatter = new DecimalFormat("###0.00");
			String cancelProviderPrice=formatter.format(pendingCancelProviderHistory.getPrice());
		processVariables.put(OrderfulfillmentConstants.PVKEY_RQSTD_BUYER_AMT,cancelProviderPrice);
		}
      
		processVariables.put("SOID",so.getSoId());
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
		if (null != price) {
			additionalAmount = Double.parseDouble(price.toString())-soPrice.doubleValue();
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
						PricingUtil.addTaskPriceHistory(task, "SYSTEM","SYSTEM");
					}

				}
			}
		}
	} */
		serviceOrderDao.pendingCancelUpdation(pendingCancelHistory);
		if (price != null) {
			so.setSpendLimitLabor(new BigDecimal(Double.parseDouble(price
					.toString())));
			so.setSpendLimitParts(new BigDecimal(0.0));
			so.setFinalPriceLabor(new BigDecimal(Double.parseDouble(price
					.toString())));
			so.setFinalPriceParts(new BigDecimal(0.0));
			so.getPrice().setDiscountedSpendLimitLabor(new BigDecimal(Double.parseDouble(price
					.toString())).setScale(2, RoundingMode.HALF_UP));
			
			serviceOrderDao.update(so);
		}
		
	}

}
