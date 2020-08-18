package com.servicelive.orderfulfillment.command;

import java.math.BigDecimal;
import java.util.Map;

import com.servicelive.domain.so.TaskStatus;
import com.servicelive.orderfulfillment.common.OFConstants;
import com.servicelive.orderfulfillment.domain.SOAddon;
import com.servicelive.orderfulfillment.domain.SOPriceChangeHistory;
import com.servicelive.orderfulfillment.domain.SOProviderInvoiceParts;
import com.servicelive.orderfulfillment.domain.SOTask;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.util.PricingUtil;

public class AutoCancelCmd extends SOCommand{

	@Override
	public void execute(Map<String, Object> processVariables) {

		ServiceOrder so = getServiceOrder(processVariables);
		
		for(SOTask task:so.getActiveTasks()){
			if(null != task.getFinalPrice()){
				// to update task history for price $0
				if(task.getFinalPrice().compareTo(new BigDecimal(0.0))!=0)
				{
				task.setFinalPrice(new BigDecimal(0.0));
				PricingUtil.addTaskPriceHistory(task, "SYSTEM","SYSTEM");
				}
			}
			task.setTaskStatus(TaskStatus.CANCELED.name());
		}

		//so.setSpendLimitLabor(new BigDecimal(0.0));
		//so.setSpendLimitParts(new BigDecimal(0.0));
		so.setFinalPriceLabor(new BigDecimal(0.0));
		so.setFinalPriceParts(new BigDecimal(0.0));
		
		if(null!=so.getPrice()){
			so.getPrice().setDiscountedSpendLimitLabor(new BigDecimal(0.0));
			so.getPrice().setDiscountedSpendLimitParts(new BigDecimal(0.0));
			so.getPrice().setTotalAddonPriceGL(new BigDecimal(0.0));
		}
		
		if(so.getSoProviderInvoiceParts()!=null)
		{
		 for (SOProviderInvoiceParts providerInvoiceParts : so.getSoProviderInvoiceParts()) {
				serviceOrderDao.delete(providerInvoiceParts);
			}	
		}
		
		so.setSoProviderInvoiceParts(null);
		if(so.getAddons() != null){
			for(SOAddon tadd : so.getAddons()){
				
				tadd.setQuantity(0);
			}
		}
		if(null!=so.getPrice()){
			so.getPrice().setTotalAddonPriceGL(new BigDecimal(0.0));
			}
		
		/** 
		* SL-18007 setting so price change history
		* during NPS cancellation 
		*/
		if (null != so.getSoPriceChangeHistory()){
			SOPriceChangeHistory newSOPriceChangeHistory = new SOPriceChangeHistory();
			so.setFromCancelFlow(true);
			newSOPriceChangeHistory.setAction(OFConstants.ORDER_CANCELLATION);
			newSOPriceChangeHistory.setReasonComment(null);			
			PricingUtil.addSOPriceChangeHistory(so,newSOPriceChangeHistory, null, null);
		}
		
		serviceOrderDao.update(so);

	}

}
