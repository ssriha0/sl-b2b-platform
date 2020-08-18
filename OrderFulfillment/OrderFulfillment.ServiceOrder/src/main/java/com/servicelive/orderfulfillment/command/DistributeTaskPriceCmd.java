package com.servicelive.orderfulfillment.command;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Map;

import com.servicelive.domain.pendingcancel.PendingCancelHistory;
import com.servicelive.orderfulfillment.domain.SOTask;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.util.PricingUtil;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;

public class DistributeTaskPriceCmd extends SOCommand{

	@Override
	public void execute(Map<String, Object> processVariables) {

		ServiceOrder so = getServiceOrder(processVariables);
		double price=0.0d;
		int noOfTasks =0;
		for (SOTask task : so.getActiveTasks()) {
			if(task.getPrice()!=null)
			{
				price=price+ task.getFinalPrice().doubleValue();
			}			
		}

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
			BigDecimal soPrice = so.getSpendLimitLabor();

			additionalAmount =soPrice.doubleValue()-price;


			if (additionalAmount != 0 ) {
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
							double percentageShare = task.getFinalPrice().doubleValue() / totalFinalPriceOfTasks * 100;
							amount = additionalAmount * percentageShare / 100;
							amount = task.getFinalPrice().doubleValue() + amount;
							task.setFinalPrice(BigDecimal.valueOf(amount));
							PricingUtil.addTaskPriceHistory(task,"SYSTEM", "SYSTEM");
						}// divide additionalAmount equally among skus in case of the initial total task price is 0 
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
		serviceOrderDao.update(so);

	}

}
