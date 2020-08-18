package com.servicelive.orderfulfillment.domain.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


import com.servicelive.orderfulfillment.domain.SOAddon;
import com.servicelive.orderfulfillment.domain.SOPrice;
import com.servicelive.orderfulfillment.domain.SOPriceChangeHistory;
import com.servicelive.orderfulfillment.domain.SOTask;
import com.servicelive.orderfulfillment.domain.SOTaskHistory;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.SOTaskType;


public class PricingUtil {

    public final static String ORDER_GROUPING = "Order Grouped";
	//creating these BigDecimal because we want to have scale of 2
    public static final BigDecimal ZERO = new BigDecimal("0.00");
    public static final BigDecimal ONE_SCALED_WITH_4 = new BigDecimal("1.0000");
    public static final BigDecimal PENNY = new BigDecimal("0.01");
    public static final String SYSTEM = "SYSTEM" ;
    public static final Logger logger = Logger.getLogger(PricingUtil.class);
    private static final  Integer PERMIT_TASK = 1;
    /**
	 * Number of decimals to retain. Also referred to as "scale".
	 */
	private static int DECIMALS = Currency.getInstance("USD").getDefaultFractionDigits();


    public static void setTotalRetailPrice(ServiceOrder serviceOrder, BigDecimal totalRetailPrice) {
    	logger.info("Service order spend Limit labor for"+ serviceOrder.getSoId()+"before setting"+ serviceOrder.getSpendLimitLabor());
        totalRetailPrice = totalRetailPrice.setScale(2, RoundingMode.HALF_DOWN);
        serviceOrder.setSpendLimitLabor(totalRetailPrice);
        logger.info("Service order spend Limit labor for"+ serviceOrder.getSoId()+"after setting"+ serviceOrder.getSpendLimitLabor());
        serviceOrder.setInitialPrice(totalRetailPrice);
        if(null == serviceOrder.getSpendLimitParts()){
        	serviceOrder.setSpendLimitParts(ZERO);
        }       

        SOPrice soPrice = new SOPrice();
        soPrice.setOrigSpendLimitLabor(totalRetailPrice);
        soPrice.setDiscountedSpendLimitLabor(totalRetailPrice);
        soPrice.setInitPostedSpendLimitLabor(totalRetailPrice);
        if(null != serviceOrder.getSpendLimitParts()){
        	 soPrice.setOrigSpendLimitParts(serviceOrder.getSpendLimitParts());
             soPrice.setDiscountedSpendLimitParts(serviceOrder.getSpendLimitParts());
        }
        else{
        	soPrice.setOrigSpendLimitParts(ZERO);
            soPrice.setDiscountedSpendLimitParts(ZERO);
        }        
        soPrice.setInitPostedSpendLimitParts(ZERO);
        soPrice.setFinalServiceFee(ZERO);
        serviceOrder.setPrice(soPrice);
    }
    
    
    public static void updateLaborPrice(ServiceOrder serviceOrder, BigDecimal totalRetailPrice) {
        totalRetailPrice = totalRetailPrice.setScale(2, RoundingMode.HALF_DOWN);
        serviceOrder.setSpendLimitLabor(totalRetailPrice);
		if (serviceOrder.getPrice() != null) {
			serviceOrder.getPrice().setOrigSpendLimitLabor(totalRetailPrice);
			serviceOrder.getPrice().setDiscountedSpendLimitLabor(
					totalRetailPrice);
		}
    }
    
    /**
	 * Set the price for the order when the service order has tasks
	 * 
	 * @param serviceOrder
	 * @param labourSpendLimit
	 * @param partsSpenLimit
	 */
    public static void setTotalRetailPriceForTasks(ServiceOrder serviceOrder, 
    		BigDecimal labourSpendLimit,BigDecimal partsSpendLimit) {
    	labourSpendLimit = labourSpendLimit.setScale(2, RoundingMode.HALF_DOWN);
        serviceOrder.setSpendLimitLabor(labourSpendLimit);
        serviceOrder.setInitialPrice(labourSpendLimit);
        serviceOrder.setSpendLimitParts(partsSpendLimit);

        SOPrice soPrice = new SOPrice();
        soPrice.setOrigSpendLimitLabor(labourSpendLimit);
        soPrice.setDiscountedSpendLimitLabor(labourSpendLimit);
        // soPrice.setInitPostedSpendLimitLabor(ZERO);
        soPrice.setOrigSpendLimitParts(partsSpendLimit);
        soPrice.setDiscountedSpendLimitParts(partsSpendLimit);
        // soPrice.setInitPostedSpendLimitParts(ZERO);
        serviceOrder.setPrice(soPrice);
    }
    
    public static void addTaskPriceHistory(SOTask task, String modifiedBy, String modifiedByName){
    	// check to prevent delivery task from getting entered in task price history.
    	if(!task.getTaskType().equals(SOTaskType.Delivery.getId())){
    		SOTaskHistory taskHistory = new SOTaskHistory();
    		Date startingDate = new Date();
    	    taskHistory.setSoId(task.getServiceOrder().getSoId());
            taskHistory.setPrice(task.getFinalPrice());
            taskHistory.setModifiedByName(modifiedByName);
            taskHistory.setCreatedDate(startingDate);
            if(null!=modifiedBy){ 
    			taskHistory.setModifiedBy(modifiedBy);
            }
            if(modifiedByName!=null&&modifiedByName.equals(SYSTEM)){
    			taskHistory.setModifiedBy(SYSTEM);
    		}
            taskHistory.setSku(task.getExternalSku());
            taskHistory.setModifiedDate(startingDate);
            taskHistory.setTaskSeqNum(task.getTaskSeqNum());
            taskHistory.setTask(task);
            task.getTaskHistory().add(taskHistory);
    	}    		
    }
    /**
     * SL-18007 method to set so price change history in ServiceOrder Object in all scenarios other than SO Creation
     * @param: ServiceOrder - the modified service order object
     * @param: SOPriceChangeHistory - the latest SOPriceChangeHistory object holding the action & reasoncodeIds set from the calling method
     * @param: modifiedBy - username of the logged in user
     * @param: adoptedBy - username of the SL Admin who adopted the buyer.
     */
    public static void addSOPriceChangeHistory(ServiceOrder so, SOPriceChangeHistory newSOPriceChangeHistory, String modifiedBy, String modifiedByName){
    	
    	//change in various components
    	BigDecimal spendLimitLabourChange = ZERO;
    	BigDecimal partsPriceChange = ZERO;
    	BigDecimal permitTaskPrice = ZERO;
    	BigDecimal permitPriceChange = ZERO;
    	BigDecimal addOnPrice = ZERO;
    	BigDecimal addonPriceChange = ZERO;
    	BigDecimal partsInvoicePrice = ZERO;
    	BigDecimal partsInvoicePriceChange = ZERO;
    	
    	BigDecimal sumOfLabourHistory = ZERO;
    	BigDecimal sumOfPartsHistory = ZERO;
    	BigDecimal sumOfPermitsHistory = ZERO;
    	BigDecimal sumOfAddOnsHistory = ZERO;
    	BigDecimal sumOfInvoicePartsHistory = ZERO;
    	
    	List<SOPriceChangeHistory>  soPriceChangeHistoryList =  so.getSoPriceChangeHistory();
    	int listSize = 0;
    	if (null != soPriceChangeHistoryList){
    		listSize = soPriceChangeHistoryList.size();
    		
    		for(SOPriceChangeHistory history: soPriceChangeHistoryList){
    			
    			sumOfLabourHistory = sumOfLabourHistory.add(history.getSoLabourPrice()== null ? ZERO : history.getSoLabourPrice());
    			sumOfPartsHistory = sumOfPartsHistory.add(history.getSoMaterialsPrice()== null ? ZERO : history.getSoMaterialsPrice());
    			sumOfPermitsHistory = sumOfPermitsHistory.add(history.getSoPermitPrice()== null ? ZERO : history.getSoPermitPrice());
    			sumOfAddOnsHistory = sumOfAddOnsHistory.add(history.getSoAddonPrice()== null ? ZERO : history.getSoAddonPrice());
    			sumOfInvoicePartsHistory = sumOfInvoicePartsHistory.add(history.getSoPartsInvoicePrice()== null ? ZERO :history.getSoPartsInvoicePrice());
    		}
    		double totalPermitPrice = 0;
    		double totalPermitPriceWithAddonPermits =0;
    		BigDecimal addonPermitPrice = new BigDecimal("0.00");
    		
    		for (SOTask task : so.getActiveTasks()) {
				if(PERMIT_TASK.equals(task.getTaskType())){
					if(null!= task.getSellingPrice() && null!= task.getFinalPrice() && task.getSellingPrice().doubleValue()>task.getFinalPrice().doubleValue()){
						totalPermitPrice = totalPermitPrice + task.getFinalPrice().doubleValue();
					}else if(null!= task.getSellingPrice() && null!= task.getFinalPrice() && task.getSellingPrice().doubleValue()<=task.getFinalPrice().doubleValue()){
						totalPermitPrice = totalPermitPrice + task.getSellingPrice().doubleValue();
					}
				
				}	    	
	    	}
    		List<SOAddon> soAddons = so.getAddons();
        	
    		if (null != soAddons){
    			for (SOAddon soAddon: soAddons){
    				if(("99888").equals(soAddon.getSku()))
    				{	
    					logger.info("<<<<<addon*Quantity---"+soAddon.getAddonPrice());
    					addonPermitPrice = addonPermitPrice.add(soAddon.getAddonPrice());
    				}
    			}
    		}    
    		if (null != addonPermitPrice){
    			totalPermitPriceWithAddonPermits = totalPermitPrice + addonPermitPrice.doubleValue();
    		}
    		
    		if (listSize > 0){
            	SOPriceChangeHistory latestSOPriceChangeHistory = soPriceChangeHistoryList.get(listSize-1);
            	logger.info(latestSOPriceChangeHistory);
            	Calendar cal=Calendar.getInstance(TimeZone.getTimeZone("CST"));
            	Date modifiedDate=cal.getTime();
            	if (null != latestSOPriceChangeHistory.getSoTotalPrice()){
            		// Completed SOs will have their final price updated instead of spend limit labour. Hence this check for price updation while closing of an SO.
                	if ((160 == so.getWfStateId() && !(so.isFromRIScopeChangeFlowWhileCompleted()))|| so.isFromCompletionFlow()|| so.isFromUngroupSOFlowCancellation()){
                		/*spendLimitLabourChange = so.getFinalPriceLabor()
                				.subtract(latestSOPriceChangeHistory.getSoTotalPrice().subtract(sumOfPartsHistory));
                		
                		partsPriceChange = so.getFinalPriceParts()
                				.subtract(latestSOPriceChangeHistory.getSoTotalPrice().subtract(sumOfLabourHistory));*/
                		spendLimitLabourChange = (so.getFinalPriceLabor().subtract(new BigDecimal(totalPermitPrice)))
                				.subtract((latestSOPriceChangeHistory.getSoTotalPrice()
                						.subtract(sumOfPartsHistory)
                						.subtract(sumOfPermitsHistory)
                						.subtract(sumOfAddOnsHistory)
                						.subtract(sumOfInvoicePartsHistory)));
                		
                		partsPriceChange = so.getFinalPriceParts()
                				.subtract((latestSOPriceChangeHistory.getSoTotalPrice()
                						.subtract(sumOfLabourHistory)
                						.subtract(sumOfPermitsHistory)
                						.subtract(sumOfAddOnsHistory)
                						.subtract(sumOfInvoicePartsHistory)));
                    	logger.info("18007: spendLimitLabourChange during closure:" + spendLimitLabourChange);
                    	logger.info("18007: partsPriceChange during closure:" + partsPriceChange);
                    //for cancellation, the price has to be taken from soPrice.	
                	} else if (so.isFromCancelFlow()){
                		/*spendLimitLabourChange = so.getPrice().getDiscountedSpendLimitLabor()
                				.subtract(latestSOPriceChangeHistory.getSoTotalPrice().subtract(sumOfPartsHistory));
                		partsPriceChange = so.getPrice().getDiscountedSpendLimitParts()
                				.subtract(latestSOPriceChangeHistory.getSoTotalPrice().subtract(sumOfLabourHistory));*/
                		spendLimitLabourChange = so.getPrice().getDiscountedSpendLimitLabor()
                				.subtract((latestSOPriceChangeHistory.getSoTotalPrice()
                						.subtract(sumOfPartsHistory)
                						.subtract(sumOfPermitsHistory)
                						.subtract(sumOfAddOnsHistory)
                						.subtract(sumOfInvoicePartsHistory)));
                		partsPriceChange = so.getPrice().getDiscountedSpendLimitParts()
                				.subtract((latestSOPriceChangeHistory.getSoTotalPrice()
                						.subtract(sumOfLabourHistory)
                						.subtract(sumOfPermitsHistory)
                						.subtract(sumOfAddOnsHistory)
                						.subtract(sumOfInvoicePartsHistory)));
                    	logger.info("18007: spendLimitLabourChange while cancel:" + spendLimitLabourChange);
                    	logger.info("18007: partsPriceChange while cancel:" + partsPriceChange);
                	} else {           		
                		/*spendLimitLabourChange = so.getSpendLimitLabor()
                				.subtract(latestSOPriceChangeHistory.getSoTotalPrice().subtract(sumOfPartsHistory));
                		partsPriceChange = so.getSpendLimitParts()
                				.subtract(latestSOPriceChangeHistory.getSoTotalPrice().subtract(sumOfLabourHistory));*/
                		spendLimitLabourChange = (so.getSpendLimitLabor().subtract(new BigDecimal(totalPermitPrice)))
                				.subtract((latestSOPriceChangeHistory.getSoTotalPrice()
                						.subtract(sumOfPartsHistory)
                						.subtract(sumOfPermitsHistory)
                						.subtract(sumOfAddOnsHistory)
                						.subtract(sumOfInvoicePartsHistory)));
                		partsPriceChange = so.getSpendLimitParts()
                				.subtract((latestSOPriceChangeHistory.getSoTotalPrice()
                						.subtract(sumOfLabourHistory)
                						.subtract(sumOfPermitsHistory)
                						.subtract(sumOfAddOnsHistory)
                						.subtract(sumOfInvoicePartsHistory)));
                		
                		logger.info("18007: so.getSpendLimitLabor()" + so.getSpendLimitLabor());
                		logger.info("18007: totalPermitPrice" + totalPermitPrice);
                		logger.info("18007: latestSOPriceChangeHistory.getSoTotalPrice()" + latestSOPriceChangeHistory.getSoTotalPrice());
                		logger.info("18007:sumOfPartsHistory" + sumOfPartsHistory);
                		logger.info("18007:sumOfPermitsHistory" + sumOfPermitsHistory);
                		logger.info("18007:sumOfAddOnsHistory" + sumOfAddOnsHistory);
                		logger.info("18007:sumOfInvoicePartsHistory" + sumOfInvoicePartsHistory);
                		
                		
                		
                    	logger.info("18007: spendLimitLabourChange:" + spendLimitLabourChange);
                    	logger.info("18007: partsPriceChange:" + partsPriceChange);
                	}	
                	newSOPriceChangeHistory.setSoLabourPrice(spendLimitLabourChange);  	   	    	
                	newSOPriceChangeHistory.setSoMaterialsPrice(partsPriceChange);
                	   	
                	//permit price change
            		
                	permitPriceChange = new BigDecimal(totalPermitPriceWithAddonPermits).subtract((latestSOPriceChangeHistory.getSoTotalPrice()
                			.subtract(sumOfLabourHistory)
    						.subtract(sumOfPartsHistory)
    						.subtract(sumOfAddOnsHistory)
    						.subtract(sumOfInvoicePartsHistory)));
                	newSOPriceChangeHistory.setSoPermitPrice(permitPriceChange);
                	// addon price.
                	if(null != so.getPrice()){
                	addOnPrice = so.getPrice().getTotalAddonPriceGL();
                	}
                	if (addOnPrice == null) {
    					addonPriceChange = new BigDecimal("0.00");
    				} else {
    					/*addonPriceChange = addOnPrice
    							.subtract(latestSOPriceChangeHistory
    									.getSoAddonPrice() == null ? ZERO
    									: latestSOPriceChangeHistory
    											.getSoAddonPrice());*/
    					addonPriceChange = addOnPrice.subtract((latestSOPriceChangeHistory.getSoTotalPrice()
    	            			.subtract(sumOfLabourHistory)
    							.subtract(sumOfPartsHistory)
    							.subtract(sumOfPermitsHistory)
    							.subtract(sumOfInvoicePartsHistory)));
    				}
                	newSOPriceChangeHistory.setSoAddonPrice(addonPriceChange);
                	
                	//parts invoice price
                	partsInvoicePrice = so.getPartsPrice();
    				/*partsInvoicePriceChange = partsInvoicePrice
    						.subtract(latestSOPriceChangeHistory
    								.getSoPartsInvoicePrice() == null ? ZERO
    								: latestSOPriceChangeHistory
    										.getSoPartsInvoicePrice());*/
                	partsInvoicePriceChange = partsInvoicePrice.subtract((latestSOPriceChangeHistory.getSoTotalPrice()
                			.subtract(sumOfLabourHistory)
    						.subtract(sumOfPartsHistory)
    						.subtract(sumOfPermitsHistory)
    						.subtract(sumOfAddOnsHistory)));
    				newSOPriceChangeHistory.setSoPartsInvoicePrice(partsInvoicePriceChange);
                	
                	//Total SO Price = Labour + Materials + permit+ addon + parts invoice Price
                	newSOPriceChangeHistory
    						.setSoTotalPrice(latestSOPriceChangeHistory.getSoTotalPrice()
    								.add(spendLimitLabourChange)
    								.add(partsPriceChange)
    								.add(permitPriceChange)
    								.add(addonPriceChange)
    								.add(partsInvoicePriceChange));
            	}
            	//SL-18343 to set specific action while completion
           	    if (so.isFromCompletionFlow()){
           	    	int compareTotalPrice = compareTotalPrice(newSOPriceChangeHistory.getSoTotalPrice(), latestSOPriceChangeHistory.getSoTotalPrice());
           	    	if (compareTotalPrice<0){
           	    		newSOPriceChangeHistory.setAction("Decreased Spend Limit");
           	    	}else if (compareTotalPrice>0){
           	    		newSOPriceChangeHistory.setAction("Increased Spend Limit");
           	    	}else{
           	    		newSOPriceChangeHistory.setAction("Order completed with no spend limit change");
           	    	}
           	    	so.setFromCompletionFlow(false);
           	    }
				newSOPriceChangeHistory.setCreatedDate(modifiedDate);
				newSOPriceChangeHistory.setModifiedDate(modifiedDate);
            	
            	if (StringUtils.isNotBlank(modifiedBy)){
            		newSOPriceChangeHistory.setModifiedBy(modifiedBy);
            	} else {
            		newSOPriceChangeHistory.setModifiedBy(SYSTEM);
            	}
            	if (StringUtils.isNotBlank(modifiedByName)){
            		newSOPriceChangeHistory.setModifiedByName(modifiedByName);
            	} else{
            		newSOPriceChangeHistory.setModifiedByName(SYSTEM);
            	}
            	//When provider completes the order for a price lower than the spend limit there will be no entry in so_price_change_history.
            	//There will be an entry showing the decrease in price during order closure
            	//There will always be an entry for "Service Order Closed" and "Service Order Auto Closed" even if there is no change in price during closure.
            	logger.info("is from completion flow: "+so.isFromCompletionFlow());
            	logger.info("is from closure flow: "+so.isFromClosureFlow());
            	logger.info("is from manage scope flow: "+so.isFromManageScope());
            	logger.info("is from cancel flow"+so.isFromCancelFlow());
            	logger.info("action: "+newSOPriceChangeHistory.getAction());
            	logger.info("spendLimitLabourChange: "+spendLimitLabourChange);
            	logger.info("partsPriceChange: "+partsPriceChange);
            	logger.info("permitPriceChange: "+permitPriceChange);
            	logger.info("addonPriceChange: "+addonPriceChange);
            	logger.info("partsInvoicePriceChange: "+partsInvoicePriceChange);
            	if (!(spendLimitLabourChange.equals(ZERO) && partsPriceChange.equals(ZERO) && permitPriceChange.equals(ZERO) 
            			&& addonPriceChange.equals(ZERO) && partsInvoicePriceChange.equals(ZERO))){
            		logger.info("there is a change in price");
            	}
            	if ((so.isFromClosureFlow() || so.isFromManageScope() || so.isFromCancelFlow()) || (!(spendLimitLabourChange.equals(ZERO) && partsPriceChange.equals(ZERO) && permitPriceChange.equals(ZERO) 
            			&& addonPriceChange.equals(ZERO) && partsInvoicePriceChange.equals(ZERO)))){
            		soPriceChangeHistoryList.add(newSOPriceChangeHistory);
            	}
    		}
    	}
    	
    	so.setSoPriceChangeHistory(soPriceChangeHistoryList);
    }
    
    private static int compareTotalPrice(BigDecimal newTotal, BigDecimal oldTotal){
    	BigDecimal newTotalRounded = getRoundedMoneyBigDecimal(newTotal.doubleValue());
    	return (newTotalRounded.compareTo(oldTotal));
    }
    
    public static BigDecimal getRoundedMoneyBigDecimal(Double doubleNumber) {
		if (doubleNumber == null) {
			return null;
		}
		BigDecimal bigDecimal = new BigDecimal(doubleNumber);
		String numberAsString = Double.toString(doubleNumber);
		int decimalPos = numberAsString.indexOf(".");
		String afterDecimal = numberAsString.substring(decimalPos);
		if(afterDecimal.length()>=4 && afterDecimal.substring(3,4).equals("5")){
			bigDecimal = bigDecimal.setScale(DECIMALS, BigDecimal.ROUND_UP);
		}else{
			bigDecimal = bigDecimal.setScale(DECIMALS, BigDecimal.ROUND_HALF_EVEN);
		}
		return bigDecimal;
	}
	/**
     * SL-18007 method to save so price change history during SO creation
     * @param: ServiceOrder serviceOrder
     */
    public static void setSOPriceChangeHistory(ServiceOrder serviceOrder, String modifiedBy, String modifiedByName,  boolean isPost){
    	logger.info("inside order creation from FE logic - value of isPost is : "+isPost);
    	List<SOPriceChangeHistory> soPriceChangeHistoryList = new ArrayList<SOPriceChangeHistory>();
    	SOPriceChangeHistory soPriceChangeHistory  = new SOPriceChangeHistory();
    	BigDecimal soLabourPrice = PricingUtil.ZERO;
    	BigDecimal soPartsPrice = PricingUtil.ZERO;
    	BigDecimal permitTaskPrice = PricingUtil.ZERO;
    	BigDecimal addonPermitPrice = PricingUtil.ZERO;
    	BigDecimal permitTaskPriceWithAddonPermit = PricingUtil.ZERO;
    	BigDecimal addOnPrice = PricingUtil.ZERO;
    	BigDecimal partsInvoicePrice = PricingUtil.ZERO;
    	Calendar cal=Calendar.getInstance(TimeZone.getTimeZone("CST"));
    	Date modifiedDate=cal.getTime();
    	if (null != serviceOrder){
    		soLabourPrice = serviceOrder.getSpendLimitLabor();
    		soPartsPrice = serviceOrder.getSpendLimitParts();
    		soPriceChangeHistory.setSoMaterialsPrice(soPartsPrice);
    		// set the price of Permit tasks from so_tasks    		
    		List<SOTask> soTasks = serviceOrder.getTasks();
    		if (null != soTasks){
    			for(SOTask task: soTasks){
    				if (1==task.getTaskType()){
    					permitTaskPrice = permitTaskPrice.add(task.getPrice());
    				}
    			}
    		}
    		
    		List<SOAddon> soAddons = serviceOrder.getAddons();
    		if (null != soAddons){
    			for (SOAddon soAddon: soAddons){
    				if(("99888").equals(soAddon.getSku()))
    				{	
    					logger.info("<<<<<addon*Quantity---"+soAddon.getAddonPrice());
    					addonPermitPrice = addonPermitPrice.add(soAddon.getAddonPrice());
    				}
    			}
    		}   
    		if (null != addonPermitPrice){
    			permitTaskPriceWithAddonPermit = permitTaskPrice.add(addonPermitPrice);
    		}
    		logger.info("<<<<<<permitTaskPriceWithAddonPermit---"+permitTaskPriceWithAddonPermit);
    		logger.info("<<<<<<permitTaskPrice---"+permitTaskPrice);
    		
    		soPriceChangeHistory.setSoPermitPrice(permitTaskPriceWithAddonPermit);
    		soPriceChangeHistory.setSoLabourPrice(soLabourPrice.subtract(permitTaskPrice));
    		// set the price of addons from so_addons 
    		if (null != serviceOrder.getPrice()){
    			addOnPrice = serviceOrder.getPrice().getTotalAddonPriceGL();
    		} 		
    		logger.info("<<<<<addOnPrice---"+addOnPrice);

    		if(addOnPrice == null){
        		soPriceChangeHistory.setSoAddonPrice(ZERO);
    		}else {	
    			logger.info(">>>>>>addOnPrice--"+addOnPrice);
    		    soPriceChangeHistory.setSoAddonPrice(addOnPrice);
    		}
    		//setting parts invoice price to zero as this is applicable only for buyer 3000 and only during completion of an SO
    		soPriceChangeHistory.setSoPartsInvoicePrice(partsInvoicePrice);
    		// Total SO Price = labour + parts price
    		
    		soPriceChangeHistory.setSoTotalPrice(soLabourPrice == null?PricingUtil.ZERO : soLabourPrice
    				.add(soPartsPrice == null? PricingUtil.ZERO : soPartsPrice )
    				.add(addOnPrice == null? PricingUtil.ZERO : addOnPrice)
    				.add(partsInvoicePrice == null ? PricingUtil.ZERO : partsInvoicePrice));
    		if (isPost){
    			soPriceChangeHistory.setAction("Order Created & Posted");
    		}else {
    			soPriceChangeHistory.setAction("Order Created");
    		}
    		soPriceChangeHistory.setReasonComment(null);
    		soPriceChangeHistory.setCreatedDate(modifiedDate);
    		soPriceChangeHistory.setModifiedDate(modifiedDate);
    		if (null != modifiedBy){
    			soPriceChangeHistory.setModifiedBy(modifiedBy);
    		}else {
    			soPriceChangeHistory.setModifiedBy(PricingUtil.SYSTEM);
    		}
    		if (null != modifiedByName){
    			soPriceChangeHistory.setModifiedByName(modifiedByName);
    		}else {
    			soPriceChangeHistory.setModifiedByName(PricingUtil.SYSTEM);
    		}
    		   		
    		soPriceChangeHistoryList.add(soPriceChangeHistory);
    		
    		serviceOrder.setSoPriceChangeHistory(soPriceChangeHistoryList);
    	}
    	
    }


	public static void setTaxAndDiscount(ServiceOrder serviceOrder, SOPrice tempPrice) {
		if (null != tempPrice) {
			if (null == serviceOrder.getPrice()) {
				serviceOrder.setPrice(new SOPrice());
			}

			serviceOrder.getPrice().setDiscountPercentLaborSL(tempPrice.getDiscountPercentLaborSL());
			serviceOrder.getPrice().setDiscountPercentPartsSL(tempPrice.getDiscountPercentPartsSL());
			serviceOrder.getPrice().setTaxPercentLaborSL(tempPrice.getTaxPercentLaborSL());
			serviceOrder.getPrice().setTaxPercentPartsSL(tempPrice.getTaxPercentPartsSL());
		}		
	}

}
