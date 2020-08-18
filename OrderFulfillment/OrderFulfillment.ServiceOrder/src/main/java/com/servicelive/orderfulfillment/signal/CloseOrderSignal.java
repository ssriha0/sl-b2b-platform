package com.servicelive.orderfulfillment.signal;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.servicelive.common.util.MoneyUtil;
import com.servicelive.orderfulfillment.common.OFConstants;
import com.servicelive.orderfulfillment.common.RelayNetPriceCalculatorUtil;
import com.servicelive.marketplatform.common.vo.FeeType;
import com.servicelive.marketplatform.serviceinterface.IMarketPlatformPromoBO;
import com.servicelive.orderfulfillment.common.OFConstants;
import com.servicelive.orderfulfillment.domain.SOElement;
import com.servicelive.orderfulfillment.domain.SOPrice;
import com.servicelive.orderfulfillment.domain.SOPriceChangeHistory;
import com.servicelive.orderfulfillment.domain.SOTask;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.SOPriceType;
import com.servicelive.orderfulfillment.domain.util.PricingUtil;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;

public class CloseOrderSignal extends Signal {

    private IMarketPlatformPromoBO marketPlatformBO;
    private Identification id;
    
    @Override
	public void accessMiscParams(Map<String, Serializable> miscParams, Identification id, ServiceOrder so) {
		this.id = id;
		
	}
    
    /**
     * @param soe
     * @param serviceOrder
     */
    @Override
    public void update(SOElement soe, ServiceOrder serviceOrder) {
        ServiceOrder inputSO = (ServiceOrder) soe;

        serviceOrder.setFinalPriceParts(inputSO.getFinalPriceParts());
        serviceOrder.setFinalPriceLabor(inputSO.getFinalPriceLabor());
        boolean tasklevelPriceInd =  SOPriceType.TASK_LEVEL.name().equals(serviceOrder.getPriceType());
        updateSOPriceWithFinalAmounts(serviceOrder, tasklevelPriceInd);
        /**
		 * SL-18007 setting so price change history during closure of an SO
		 */
		if (null != serviceOrder.getSoPriceChangeHistory()) {

			logger.info("18007: so price change history contins: " + serviceOrder.getSoPriceChangeHistory());
			
			SOPriceChangeHistory newSOPriceChangeHistory = new SOPriceChangeHistory();
			String modifiedBy = PricingUtil.SYSTEM;
			String modifiedByName = PricingUtil.SYSTEM;
			// set specific components before passing to the generic method
			newSOPriceChangeHistory.setAction(OFConstants.ORDER_CLOSURE);
			newSOPriceChangeHistory.setReasonComment(null);
			serviceOrder.setFromClosureFlow(true);
			//newSOPriceChangeHistory.setReasonRoleId(OFConstants.BUYER_ROLE);
			
			/*if (StringUtils.isNotBlank(serviceOrder.getModifiedBy())){
				modifiedBy = serviceOrder.getModifiedBy();
			}
			if (StringUtils.isNotBlank(serviceOrder.getModifiedByName())){
				modifiedByName = serviceOrder.getModifiedByName();
			}*/
			// call generic method to save so price change history
			if(id==null){
				PricingUtil.addSOPriceChangeHistory(serviceOrder, newSOPriceChangeHistory,
						"SYSTEM", "SYSTEM");
			}else{
			PricingUtil.addSOPriceChangeHistory(serviceOrder, newSOPriceChangeHistory,
					id.getBuyerResourceId().toString(), id.getFullname());
			}
		}
		//JIRA SL-17042
		//increment the completed orders count in survey_response_summary & vendor_resource
		serviceOrderDao.updateCompletedSOCount(serviceOrder.getBuyerId(), serviceOrder.getAcceptedProviderResourceId());

        serviceOrderDao.update(serviceOrder);
    }

    private void updateSOPriceWithFinalAmounts(ServiceOrder serviceOrder, boolean tasklevelPriceInd ) {
        SOPrice soPrice = serviceOrder.getPrice();
        if (soPrice == null) {
            soPrice = new SOPrice();
            soPrice.setServiceOrder(serviceOrder);
            serviceOrderDao.save(soPrice);
        }
        BigDecimal finalPriceLabor = serviceOrder.getFinalPriceLabor();
        BigDecimal finalPriceParts = serviceOrder.getFinalPriceParts();
        if(OFConstants.RELAY_SERVICES_BUYER.intValue()==serviceOrder.getBuyerId().intValue()){
         soPrice.setFinalServiceFee(RelayNetPriceCalculatorUtil.getServiceFeeForLaborAndParts(serviceOrder, getServiceFeePercentage(serviceOrder)));
        }else{
         soPrice.setFinalServiceFee(calculateServiceFee(serviceOrder, tasklevelPriceInd));
        }
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
    
    
    private BigDecimal calculateServiceFee(ServiceOrder serviceOrder, boolean tasklevelPriceInd ) {
    	
    	BigDecimal finalPriceLabor = serviceOrder.getFinalPriceLabor();
    	BigDecimal serviceFeePercentage = getServiceFeePercentage(serviceOrder);
    	BigDecimal finalPriceParts = serviceOrder.getFinalPriceParts();
    	double totalPermitPrice = 0;
    	if(tasklevelPriceInd){
	    	for (SOTask task : serviceOrder.getActiveTasks()) {
				if(task.getTaskType().equals(OFConstants.PERMIT_TASK)){
					if(null!= task.getSellingPrice() && null!= task.getFinalPrice() && task.getSellingPrice().doubleValue()>task.getFinalPrice().doubleValue()){
						totalPermitPrice = totalPermitPrice + task.getFinalPrice().doubleValue();
					}else if(null!= task.getSellingPrice() && null!= task.getFinalPrice() && task.getSellingPrice().doubleValue()<=task.getFinalPrice().doubleValue()){
						totalPermitPrice = totalPermitPrice + task.getSellingPrice().doubleValue();
					}
				
				}
	    	
	    	}
    	}
    	finalPriceLabor = new BigDecimal(finalPriceLabor.doubleValue() - totalPermitPrice);
    	
    	BigDecimal netServiceFee = MoneyUtil.getRoundedMoneyCustomBigDecimal((finalPriceLabor.doubleValue()+serviceOrder.getFinalPriceParts().doubleValue()+serviceOrder.getTotalAddonWithoutPermits().doubleValue())*serviceFeePercentage.doubleValue());
    	
    	//return finalPriceLabor.add(finalPriceParts).multiply(serviceFeePercentage).setScale(2, RoundingMode.HALF_EVEN);
    	//return MoneyUtil.getRoundedMoneyCustomBigDecimal(finalPriceLabor.add(finalPriceParts).multiply(serviceFeePercentage).doubleValue());
    	
    	Double finalPrice = finalPriceLabor.doubleValue()+serviceOrder.getFinalPriceParts().doubleValue();
    	Double serviceFee = finalPrice - MoneyUtil.getRoundedMoney((1d - serviceFeePercentage.doubleValue()) * finalPrice);
        //return netServiceFee.subtract(MoneyUtil.getRoundedMoneyCustomBigDecimal(serviceOrder.getTotalAddonWithoutPermits().doubleValue()*serviceFeePercentage.doubleValue()));
        return new BigDecimal(serviceFee);
    }

    
    private BigDecimal getServiceFeePercentage(ServiceOrder serviceOrder) {
        double percent = marketPlatformBO.getPromoFee(serviceOrder.getSoId(), serviceOrder.getBuyerId(), FeeType.PromoServiceFee);
        return new BigDecimal(percent);
    }
    
    @Override
    protected List<String> validate(SOElement soe, ServiceOrder soTarget) {
        List<String> returnVal = new ArrayList<String>();
        if (!(soe instanceof ServiceOrder)) {
            String errMsg = "Expecting a ServiceOrder instance in CloseOrderSignal";
            returnVal.add(errMsg);
        }
        return returnVal;
    }

    public void setMarketPlatformBO(IMarketPlatformPromoBO marketPlatformBO) {
        this.marketPlatformBO = marketPlatformBO;
    }
}