package com.servicelive.orderfulfillment.group.command;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Date;

import com.servicelive.marketplatform.serviceinterface.IMarketPlatformBuyerBO;
import com.servicelive.orderfulfillment.common.OFConstants;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.common.ServiceOrderNoteUtil;
import com.servicelive.orderfulfillment.common.TransitionNotAvailableException;
import com.servicelive.orderfulfillment.domain.SOGroup;
import com.servicelive.orderfulfillment.domain.SOGroupPrice;
import com.servicelive.orderfulfillment.domain.SONote;
import com.servicelive.orderfulfillment.domain.SOPriceChangeHistory;
import com.servicelive.orderfulfillment.domain.SOTask;
import com.servicelive.orderfulfillment.domain.SOTaskHistory;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.SOTaskType;
import com.servicelive.orderfulfillment.domain.util.PricingUtil;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;

public class GroupPricingCmd extends GroupSignalSOCmd {

	private IMarketPlatformBuyerBO marketPlatformBuyerBO;
	private ServiceOrderNoteUtil serviceOrderNoteUtil;

	private boolean calculateGroupPrice(SOGroup soGroup) {
		
		boolean priceCalculationSuccessful = true;
		SOGroupPrice groupPrice = new SOGroupPrice();
		if (soGroup.getGroupPrice() != null)
			groupPrice = soGroup.getGroupPrice();
		else
			soGroup.setGroupPrice(groupPrice);
		logger.info("groupPrice = " + groupPrice);
		//assumption is that all the service orders should 
		//have same buyer and skill category since they are grouped
		ServiceOrder so1 = soGroup.getFirstServiceOrder();
		
		int soCount = soGroup.getServiceOrders().size();
		logger.info("soCount = " + soCount);
		//get trip charges based on the buyer and skill category
		BigDecimal tripCharge = marketPlatformBuyerBO.getTripChargeByBuyerAndSkillCategory(so1.getBuyerId(), so1.getPrimarySkillCatId());
		BigDecimal groupSpendLimit = PricingUtil.ZERO;
		BigDecimal spendLimitLaborExisting = PricingUtil.ZERO;
		logger.info("tripCharge = " + tripCharge);
		//Calculate the amount that will be applied to all the orders for trip charges
		//Basically the idea is to allow one trip charge and rest of the trip charges gets distribute
		//equally among service order
		BigDecimal multipleOrderDiscount = tripCharge.divide(new BigDecimal(soCount), RoundingMode.HALF_EVEN).multiply(new BigDecimal(soCount - 1));
		logger.info("multipleOrderDiscount = " + multipleOrderDiscount);
		for (ServiceOrder so : soGroup.getServiceOrders()){
			logger.info("Service Order"+ so.getSoId()+"original spendLimit Labor from Price object"+ so.getPrice().getOrigSpendLimitLabor());
			groupSpendLimit = groupSpendLimit.add(so.getPrice().getOrigSpendLimitLabor());
		}
		logger.info("groupSpendLimit = " + groupSpendLimit);
		BigDecimal discountGroupSpendLimitLabor = PricingUtil.ZERO;
		BigDecimal groupSpendLimitParts = PricingUtil.ZERO;
		BigDecimal groupTotalPermitsPrice = PricingUtil.ZERO;
		//if the total spend limit of an order is greater than the multiple order discount apply the discount to the next order
		//the logic is copied as is but it does not make sense. Need to consult with business and find out what is the 
		//optimal way of distributing the trip charge
		BigDecimal tripChargeAdjustment = PricingUtil.ZERO;
		if (groupSpendLimit.compareTo(multipleOrderDiscount.multiply(new BigDecimal(soCount))) >= 0){
			//sort service order ascending based on spend limit
			List<ServiceOrder> sortedList = getSortedList(soGroup.getServiceOrders());
			for(ServiceOrder so : sortedList){
				BigDecimal discountSpendLimit = so.getPrice().getOrigSpendLimitLabor().subtract(multipleOrderDiscount.add(tripChargeAdjustment));
				if (discountSpendLimit.doubleValue() < 0){
                    tripChargeAdjustment = discountSpendLimit.negate();
                    discountSpendLimit = PricingUtil.ZERO;
                }else{
					tripChargeAdjustment = PricingUtil.ZERO;
				}
				logger.info("Service Order"+ so.getSoId() + "SpendLimit Labor"+ so.getSpendLimitLabor());
				logger.info("Service Order"+ so.getSoId() + "Total Permit Price"+ so.getTotalPermitPrice());
				/*SL-20527 : During Update file processing,once a task whose price is reduced because of grouping is gets deleted,
				 *the spend limit labor will be recalculated from tasks**/
				for (SOTask soTask : so.getActiveTasks()) {
					if (!(soTask.getTaskType().equals(SOTaskType.Delivery.getId()))) {
	    				BigDecimal taskPrice = 	soTask.getFinalPrice();
	    				spendLimitLaborExisting = spendLimitLaborExisting.add(taskPrice);
					}
					
				}
				logger.info("Service Order"+ so.getSoId() + "SpendLimit Labor Calculated"+ spendLimitLaborExisting);
				so.setSpendLimitLabor(spendLimitLaborExisting);
				BigDecimal orderLaborSpendLimitexcludesPermit =so.getSpendLimitLabor().subtract(so.getTotalPermitPrice());
				logger.info("orderLaborSpendLimitexcludesPermit = " + orderLaborSpendLimitexcludesPermit);
					so.setSpendLimitLabor(discountSpendLimit);
					so.getPrice().setDiscountedSpendLimitLabor(discountSpendLimit);
					discountGroupSpendLimitLabor = discountGroupSpendLimitLabor.add(discountSpendLimit);
					for (SOTask soTask : so.getActiveTasks()) {
		    			logger.info("soTask.getTaskName().trim() = " + soTask.getTaskName().trim());
		    			if (!soTask.getTaskName().trim().startsWith(OFConstants.PERMIT_SKU)) {
		    				BigDecimal taskPrice = 	soTask.getFinalPrice();
		    			if(taskPrice !=null && taskPrice.doubleValue() != 0.0){
		    				logger.info("taskPrice = " + taskPrice);
		    				logger.info("soTask.getSellingPrice = " + soTask.getSellingPrice());
		    				logger.info("soTask.getSequenceNumber = " + soTask.getSequenceNumber());
		    				BigDecimal discountedOrderLaborSpendLimitexcludesPermit =so.getSpendLimitLabor().subtract(so.getTotalPermitPrice());
		    				logger.info("discountedOrderLaborSpendLimitexcludesPermit = " + discountedOrderLaborSpendLimitexcludesPermit);
		    				if(orderLaborSpendLimitexcludesPermit.doubleValue() > 0){
		    				BigDecimal taskToOrdeLaborRatio =getProportion(taskPrice,orderLaborSpendLimitexcludesPermit);
		    				logger.info("taskToOrdeLaborRatio = " + taskToOrdeLaborRatio);
		    				if(taskToOrdeLaborRatio.doubleValue() > 0){
		    					soTask.setFinalPrice(taskToOrdeLaborRatio.multiply(discountedOrderLaborSpendLimitexcludesPermit).setScale(2, RoundingMode.HALF_EVEN));
		    					}
		    				}
		    				logger.info("soTask.getFinalPrice() = " + soTask.getFinalPrice());
			    			//setting task history data
		    				if(soTask.getLatestPrice()!=null && soTask.getLatestPrice().compareTo(soTask.getFinalPrice()) != 0){
		    					PricingUtil.addTaskPriceHistory(soTask, "SYSTEM", "SYSTEM");
		    				}   				
		    			}
		       		  }
		    		}	
					//SL-18007 setting so price change history while grouping if there is a change in the so spend limit
					if (null != so.getSoPriceChangeHistory()){
						
						logger.info("18007: so price change history contins:" + so.getSoPriceChangeHistory());
						
						List<SOPriceChangeHistory>  soPriceChangeHistoryList =  so.getSoPriceChangeHistory();
						int listSize = soPriceChangeHistoryList.size();
						if (listSize>0){
							SOPriceChangeHistory  latestSOPriceChangeHistory = soPriceChangeHistoryList.get(listSize-1);
							
							if (discountSpendLimit.compareTo(latestSOPriceChangeHistory.getSoTotalPrice().subtract(so.getSpendLimitParts()))!= 0){
								
								SOPriceChangeHistory newSOPriceChangeHistory = new SOPriceChangeHistory();
								//set specific components before passing to the generic method
								newSOPriceChangeHistory.setAction(OFConstants.ORDER_GROUPING);
								newSOPriceChangeHistory.setReasonComment(null);
								//newSOPriceChangeHistory.setReasonRoleId(OFConstants.BUYER_ROLE);
								// call generic method to save so price change history
								PricingUtil.addSOPriceChangeHistory(so,newSOPriceChangeHistory, PricingUtil.SYSTEM,PricingUtil.SYSTEM);
							}					
						}
					}
					
                serviceOrderDao.update(so);
        		
				groupSpendLimitParts = groupSpendLimitParts.add(so.getSpendLimitParts());
				groupTotalPermitsPrice=groupTotalPermitsPrice.add(so.getTotalPermitPrice());
				logger.info("discountGroupSpendLimitLabor = " + discountGroupSpendLimitLabor);
				logger.info("groupSpendLimitParts = " + groupSpendLimitParts);
				logger.info("groupTotalPermitsPrice = " + groupTotalPermitsPrice);
			}
		} else {
            throw new ServiceOrderException("Cannot apply group discount to orders. Orders does not have enough money to apply the group discount");
		}
		
		groupPrice.setDiscountedSpendLimitLabor(discountGroupSpendLimitLabor);
		groupPrice.setDiscountedSpendLimitParts(groupSpendLimitParts);
		groupPrice.setFinalSpendLimitLabor(discountGroupSpendLimitLabor);
		groupPrice.setFinalSpendLimitParts(groupSpendLimitParts);
		groupPrice.setTotalPermitPrice(groupTotalPermitsPrice);
		groupPrice.setSoGroup(soGroup);
		serviceOrderDao.save(soGroup);

        return priceCalculationSuccessful;
	}
	
    private BigDecimal getProportion(BigDecimal number1, BigDecimal number2){
    	if(number1.doubleValue() == 0 || number2.doubleValue() == 0){
    		return PricingUtil.ZERO;
    	}
    	
    	BigDecimal proportion = new BigDecimal(number1.doubleValue() / number2.doubleValue());
    	
    	return proportion;
    }

	private List<ServiceOrder> getSortedList(Set<ServiceOrder> serviceOrders) {
		//convert to list
		List<ServiceOrder> soList = new ArrayList<ServiceOrder>(serviceOrders);
		Collections.sort(soList, new Comparator<ServiceOrder>() {
				public int compare(ServiceOrder so1, ServiceOrder so2) {
					return so1.getSpendLimitLabor().compareTo(so2.getSpendLimitLabor());
				}
			});
		return soList;
	}
		
	public void setMarketPlatformBuyerBO(IMarketPlatformBuyerBO marketPlatformBuyerBO) {
		this.marketPlatformBuyerBO = marketPlatformBuyerBO;
	}

    public void setServiceOrderNoteUtil(ServiceOrderNoteUtil serviceOrderNoteUtil) {
        this.serviceOrderNoteUtil = serviceOrderNoteUtil;
    }

	@Override
	public void handleGroup(SOGroup soGroup, Map<String, Object> processVariables) {
		calculateGroupPrice(soGroup);
	}

	@Override
	public void handleServiceOrder(ServiceOrder so, Map<String, Object> processVariables) {
		throw new ServiceOrderException("This should not have happened");		
	}

}
