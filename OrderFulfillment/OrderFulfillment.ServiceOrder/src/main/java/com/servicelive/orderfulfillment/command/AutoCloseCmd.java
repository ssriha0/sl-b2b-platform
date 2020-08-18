package com.servicelive.orderfulfillment.command;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.servicelive.common.util.MoneyUtil;
import com.servicelive.orderfulfillment.common.OFConstants;
import com.servicelive.orderfulfillment.domain.SOElement;
import com.servicelive.orderfulfillment.domain.SOPrice;
import com.servicelive.orderfulfillment.domain.SOPriceChangeHistory;
import com.servicelive.orderfulfillment.domain.SOTask;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.ServiceOrderProcess;
import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.servicelive.marketplatform.common.vo.FeeType;
import com.servicelive.marketplatform.serviceinterface.IMarketPlatformPromoBO;
import com.servicelive.orderfulfillment.common.OFConstants;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.util.PricingUtil;
import com.servicelive.wallet.serviceinterface.vo.WalletResponseVO;

public class AutoCloseCmd extends SOCommand {
	
	private IMarketPlatformPromoBO marketPlatformBO;
	
	public void execute(Map<String,Object> processVariables) {
		logger.info("Starting AutoCloseCmd.execute");
        long startTime = System.currentTimeMillis();
		ServiceOrder serviceOrder = getServiceOrder(processVariables);
		//serviceOrder.setFinalPriceParts(inputSO.getFinalPriceParts());
       // serviceOrder.setFinalPriceLabor(inputSO.getFinalPriceLabor());

        updateSOPriceWithFinalAmounts(serviceOrder);
        
        /**
		 * SL-18007 setting so price change history during closure of an SO
		 */
		if (null != serviceOrder.getSoPriceChangeHistory()) {

			logger.info("18007: so price change history contins: " + serviceOrder.getSoPriceChangeHistory());
			
			SOPriceChangeHistory newSOPriceChangeHistory = new SOPriceChangeHistory();
			String modifiedBy = PricingUtil.SYSTEM;
			String modifiedByName = PricingUtil.SYSTEM;
			// set specific components before passing to the generic method
			newSOPriceChangeHistory.setAction(OFConstants.ORDER_AUTO_CLOSED);
			newSOPriceChangeHistory.setReasonComment(null);
			
			serviceOrder.setFromClosureFlow(true);
			// call generic method to save so price change history
			PricingUtil.addSOPriceChangeHistory(serviceOrder, newSOPriceChangeHistory,
					modifiedBy, modifiedByName);
		}
		//JIRA SL-17042
		//increment the completed orders count in survey_response_summary & vendor_resource
		serviceOrderDao.updateCompletedSOCount(serviceOrder.getBuyerId(), serviceOrder.getAcceptedProviderResourceId());
        serviceOrderDao.update(serviceOrder);
    }

	private void updateSOPriceWithFinalAmounts(ServiceOrder serviceOrder) {
        SOPrice soPrice = serviceOrder.getPrice();
        if (soPrice == null) {
            soPrice = new SOPrice();
            soPrice.setServiceOrder(serviceOrder);
            serviceOrderDao.save(soPrice);
        }
        BigDecimal finalPriceLabor = serviceOrder.getFinalPriceLabor();
        BigDecimal finalPriceParts = serviceOrder.getFinalPriceParts();
        soPrice.setFinalServiceFee(calculateServiceFee(serviceOrder));
        soPrice.setDiscountedSpendLimitLabor(finalPriceLabor);
        soPrice.setDiscountedSpendLimitParts(finalPriceParts);
    	if(OFConstants.HSR_BUYER_ID.equals(serviceOrder.getBuyerId())){
    		soPrice.setTotalAddonPriceGL(serviceOrder.getTotalAddon(true));
		}else{
			soPrice.setTotalAddonPriceGL(serviceOrder.getTotalAddonWithoutPermits());
		}

        serviceOrder.setPrice(soPrice);
        serviceOrderDao.update(soPrice);
    }
    
	private BigDecimal calculateServiceFee(ServiceOrder serviceOrder) {
    	
    	BigDecimal finalPriceLabor = serviceOrder.getFinalPriceLabor();
    	BigDecimal serviceFeePercentage = getServiceFeePercentage(serviceOrder);
    	BigDecimal finalPriceParts = serviceOrder.getFinalPriceParts();
    	double totalPermitPrice = 0;
    	
	    	for (SOTask task : serviceOrder.getActiveTasks()) {
				if(task.getTaskType().equals(OFConstants.PERMIT_TASK)){
					if(null!= task.getSellingPrice() && null!= task.getFinalPrice() && task.getSellingPrice().doubleValue()>task.getFinalPrice().doubleValue()){
						totalPermitPrice = totalPermitPrice + task.getFinalPrice().doubleValue();
					}else if(null!= task.getSellingPrice() && null!= task.getFinalPrice() && task.getSellingPrice().doubleValue()<=task.getFinalPrice().doubleValue()){
						totalPermitPrice = totalPermitPrice + task.getSellingPrice().doubleValue();
					}
				
				}
	    	
	    	}
    	
    	finalPriceLabor = new BigDecimal(finalPriceLabor.doubleValue() - totalPermitPrice);
    	BigDecimal netServiceFee = MoneyUtil.getRoundedMoneyCustomBigDecimal((finalPriceLabor.doubleValue()+serviceOrder.getFinalPriceParts().doubleValue()+serviceOrder.getTotalAddonWithoutPermits().doubleValue())*serviceFeePercentage.doubleValue());

    	Double finalPrice = finalPriceLabor.doubleValue() + serviceOrder.getFinalPriceParts().doubleValue();
    	Double serviceFee = finalPrice - MoneyUtil.getRoundedMoney((1d - serviceFeePercentage.doubleValue()) * finalPrice);
        //return netServiceFee.subtract(MoneyUtil.getRoundedMoneyCustomBigDecimal(serviceOrder.getTotalAddonWithoutPermits().doubleValue()*serviceFeePercentage.doubleValue()));
        return new BigDecimal(serviceFee);
        
	}
   

    private BigDecimal getServiceFeePercentage(ServiceOrder serviceOrder) {
        double percent = marketPlatformBO.getPromoFee(serviceOrder.getSoId(), serviceOrder.getBuyerId(), FeeType.PromoServiceFee);
        return new BigDecimal(percent);
    }
    
    

    public void setMarketPlatformBO(IMarketPlatformPromoBO marketPlatformBO) {
        this.marketPlatformBO = marketPlatformBO;
    }

}

