package com.servicelive.orderfulfillment.signal;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.servicelive.domain.common.BuyerFeatureSetEnum;
import com.servicelive.orderfulfillment.common.OFConstants;
import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.domain.SOElement;
import com.servicelive.orderfulfillment.domain.SOPrice;
import com.servicelive.orderfulfillment.domain.SOPriceChangeHistory;
import com.servicelive.orderfulfillment.domain.SOTask;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.SOPriceType;
import com.servicelive.orderfulfillment.domain.util.PricingUtil;
import com.servicelive.orderfulfillment.lookup.BuyerFeatureLookup;
import com.servicelive.orderfulfillment.lookup.QuickLookupCollection;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;

public class AddFundSignal extends Signal {
	protected QuickLookupCollection quickLookupCollection;
	private String reasonComment;
	private Identification id;
	
	@Override
	public void accessMiscParams(Map<String, Serializable> miscParams, Identification id, ServiceOrder so) {
		String oldPrice = "";
		if(null!=so.getSpendLimitLabor() && null!=so.getSpendLimitParts()){
			BigDecimal spendLimit = so.getSpendLimitLabor().add(so.getSpendLimitParts());
			oldPrice = spendLimit.toString();
		}
		String reasonCode = "";
		if(null!=miscParams.get("spendLimitReason")){
			reasonCode= (String)miscParams.get("spendLimitReason");
		}
		if(null==reasonCode || reasonCode.equalsIgnoreCase("")){
			miscParams.put("spendLimitReason", "");
		}
		if(null!=miscParams.get("spendLmtReasonForPriceHistory")){
			reasonComment = (String)miscParams.get("spendLmtReasonForPriceHistory");
		}
		
		miscParams.put(OrderfulfillmentConstants.PVKEY_SPEND_LIMIT_OLD_PRICE, oldPrice);
		miscParams.put(OrderfulfillmentConstants.PVKEY_SPEND_LIMIT_OLD_LABOR_PRICE, null ==  so.getSpendLimitLabor() ? "0.0" : so.getSpendLimitLabor().toString());
		miscParams.put(OrderfulfillmentConstants.PVKEY_SPEND_LIMIT_OLD_PARTS_PRICE, null == so.getSpendLimitParts() ? "0.0" : so.getSpendLimitParts().toString());
	}
	
	@Override
	protected void update(SOElement soe, ServiceOrder so){
		logger.debug("Inside AddFundSignal.update()");

		
		ServiceOrder source = (ServiceOrder)soe;
		
		so.setSpendLimitLabor(source.getSpendLimitLabor());
		so.setSpendLimitParts(source.getSpendLimitParts());
		so.setSpendLimitIncrComment(source.getSpendLimitIncrComment());
		
		if(null!=so.getPrice()){
			updateSOPrice(source.getPrice(), so.getPrice());
		}
		BuyerFeatureLookup buyerFeatureLookup = quickLookupCollection.getBuyerFeatureLookup();
		logger.info("AddFundSignal buyerFeatureLookup.isActiveFeatureAssociatedWithBuyer  "+buyerFeatureLookup.isActiveFeatureAssociatedWithBuyer(BuyerFeatureSetEnum.TASK_LEVEL, so.getBuyerId()));
		 if (SOPriceType.TASK_LEVEL.name().equals(so.getPriceType())){
			 if(null!=so.getTasks()){
				 logger.info("SL-16058. Starting updateSOTask action");
				 updateSOTask(source,so);
				 logger.info("SL-16058. Ending updateSOTask action");
			 }
		 }
		/**
		 * SL-18007 setting so price change history if there is a change in the
		 * so spend limit labour during increase spend limit from front end
		 */
		if (null != so.getSoPriceChangeHistory()) {

			logger.info("18007: so price change history contins: " + so.getSoPriceChangeHistory());
			
			SOPriceChangeHistory newSOPriceChangeHistory = new SOPriceChangeHistory();
					
			// set specific components before passing to the generic method
			newSOPriceChangeHistory.setAction(OFConstants.INCREASE_SPEND_LIMIT);
			newSOPriceChangeHistory.setReasonComment(reasonComment);
			//newSOPriceChangeHistory.setReasonRoleId(OFConstants.BUYER_ROLE);
			
			// call generic method to save so price change history			
			PricingUtil.addSOPriceChangeHistory(so, newSOPriceChangeHistory,source.getModifiedBy(), source.getModifiedByName());
		}
		serviceOrderDao.update(so);		
		logger.debug("Done AddFundSignal.update()");
	}

	
	protected void updateSOTask(ServiceOrder source, ServiceOrder target){

		for(SOTask taskTarget: target.getTasks()){
	
			for(SOTask taskSource: source.getTasks()){	
				if(taskSource.getTaskId().equals(taskTarget.getTaskId())){
					if (taskSource.getFinalPrice() != null
							&& ((taskTarget.getFinalPrice() != null && taskSource
									.getFinalPrice().doubleValue() != taskTarget
									.getFinalPrice().doubleValue()) || (taskTarget
									.getFinalPrice() == null && taskSource
									.getFinalPrice().doubleValue() != 0.0))) {
						taskTarget.setFinalPrice(taskSource.getFinalPrice());
						PricingUtil.addTaskPriceHistory(taskTarget, source.getModifiedBy(), source.getModifiedByName());
				
					}
					break;
				}
				
			}
		}
	}

	
	protected void updateSOPrice(SOPrice source, SOPrice target){
		target.setDiscountedSpendLimitLabor(source.getDiscountedSpendLimitLabor());
		target.setDiscountedSpendLimitParts(source.getDiscountedSpendLimitParts());
		
	}

    @Override
    protected List<String> validate(SOElement soe, ServiceOrder soTarget) {
        List<String> returnVal = new ArrayList<String>();
        if (!(soe instanceof ServiceOrder)){
			returnVal.add("Invalid type! Expected ServiceOrder, received:" + soe.getTypeName());
		}
        ServiceOrder source = (ServiceOrder)soe;
        BigDecimal total = new BigDecimal("0");
        if(null == source.getSpendLimitLabor()){
            returnVal.add("Null value detected for spendLimitLabor");
        }else{
            total = total.add(source.getSpendLimitLabor());
        }
        if(null == source.getSpendLimitParts()){
            returnVal.add("Null value detected for spendLimitParts");
        }else{
            total = total.add(source.getSpendLimitParts());
        }
        if(total.signum() < 1){
            returnVal.add("No funds have been specified.");
        }
        return returnVal;
    }

    protected HashMap<String,Object> getLogMap(SOElement request, Identification id, ServiceOrder target) throws ServiceOrderException {
		HashMap<String,Object> map = new HashMap<String,Object>();
		if(request instanceof ServiceOrder){
			ServiceOrder source = (ServiceOrder)request;
			map.put("SPEND_LIMIT_TO_LABOR", source.getSpendLimitLabor());
			map.put("SPEND_LIMIT_TO_PARTS", source.getSpendLimitParts());
			map.put("TOTAL_SPEND_LIMIT", source.getTotalSpendLimit());
		}
		 
		return map;
	}
	public void setQuickLookupCollection(QuickLookupCollection quickLookupCollection) {
		this.quickLookupCollection = quickLookupCollection;
	}

	public Identification getId() {
		return id;
	}

	public void setId(Identification id) {
		this.id = id;
	}
}
