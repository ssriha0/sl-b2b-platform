package com.servicelive.orderfulfillment.orderprep.processor.relay; 
 
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import com.servicelive.orderfulfillment.common.OFConstants;
import com.servicelive.orderfulfillment.domain.SOPrice;
import com.servicelive.orderfulfillment.domain.SOTask;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.SOPriceType;
import com.servicelive.orderfulfillment.domain.util.PricingUtil;
import com.servicelive.orderfulfillment.orderprep.OrderEnhancementContext;
import com.servicelive.orderfulfillment.orderprep.buyer.IOrderBuyer;
import com.servicelive.orderfulfillment.orderprep.buyersku.BuyerSkuPricingItem;
import com.servicelive.common.util.MoneyUtil;
import com.servicelive.orderfulfillment.orderprep.processor.common.AbstractOrderPriceEnhancer;

import java.util.regex.Pattern;

public class RelayFixedOrderPriceEnhancer extends AbstractOrderPriceEnhancer {
 
	@Override  
	protected void calculateAndSetPriceInfo(ServiceOrder serviceOrder,
			OrderEnhancementContext orderEnhancementContext) { 
		BigDecimal totalRetailPrice = PricingUtil.ZERO;
		IOrderBuyer orderBuyer = orderEnhancementContext.getOrderBuyer();
		Long buyerId = orderBuyer.getBuyerId();
		
		// Check if tasks are present 
    	boolean taskPresent = false;
    	taskPresent = serviceOrder.isTasksPresent();
    	
		//check whether MAR is on    	
		boolean isMARfeatureOn = orderBuyer.getBuyerPricingScheme().isMARfeatureOn(buyerId);

		// store pricing node 
		SOPrice tempPrice = serviceOrder.getPrice();
		
    	//if feature set is on & created via API
    	if(isMARfeatureOn && serviceOrder.isCreatedViaAPI()){
    		//call the method to calculate price with MAR
    		orderBuyer.getBuyerPricingScheme().calculatePriceWithMAR(serviceOrder);	
    	}
    	else{
    		
    		BigDecimal laborTaxPercentage=PricingUtil.ZERO;
    		BigDecimal partsTaxPercentage=PricingUtil.ZERO;
    		BigDecimal laborTaxAmount=PricingUtil.ZERO;
    		BigDecimal partsTaxAmount=PricingUtil.ZERO;
    	
    		
    		if(null!=serviceOrder.getCustomRefValue(OFConstants.LABOR_TAX_PERCENTAGE) &&
    				(StringUtils.isNumeric(serviceOrder.getCustomRefValue(OFConstants.LABOR_TAX_PERCENTAGE)) 
    						|| Pattern.matches(OFConstants.PRICE_REGEX, serviceOrder.getCustomRefValue(OFConstants.LABOR_TAX_PERCENTAGE)))){
    			laborTaxPercentage=BigDecimal.valueOf(Double.valueOf(serviceOrder.getCustomRefValue(OFConstants.LABOR_TAX_PERCENTAGE)).doubleValue());
    			serviceOrder.getSOWorkflowControls().setLaborTaxPercentage(laborTaxPercentage);
    		} 
    		if(null!=serviceOrder.getCustomRefValue(OFConstants.MATERIALS_TAX_PERCENTAGE) &&
    				(StringUtils.isNumeric(serviceOrder.getCustomRefValue(OFConstants.MATERIALS_TAX_PERCENTAGE)) 
    						|| Pattern.matches(OFConstants.PRICE_REGEX, serviceOrder.getCustomRefValue(OFConstants.MATERIALS_TAX_PERCENTAGE)))){
    			partsTaxPercentage= BigDecimal.valueOf(Double.valueOf(serviceOrder.getCustomRefValue(OFConstants.MATERIALS_TAX_PERCENTAGE)).doubleValue());
    			serviceOrder.getSOWorkflowControls().setMaterialsTaxPercentage(partsTaxPercentage);
    		}
    		if(null!=serviceOrder.getCustomRefValue(OFConstants.LABOR_TAX_AMOUNT) &&
    				(StringUtils.isNumeric(serviceOrder.getCustomRefValue(OFConstants.LABOR_TAX_AMOUNT)) 
    						|| Pattern.matches(OFConstants.PRICE_REGEX, serviceOrder.getCustomRefValue(OFConstants.LABOR_TAX_AMOUNT)))){
    			laborTaxAmount= BigDecimal.valueOf(Double.valueOf(serviceOrder.getCustomRefValue(OFConstants.LABOR_TAX_AMOUNT)).doubleValue());
    			serviceOrder.getSOWorkflowControls().setLaborTaxAmount(laborTaxAmount);
    		} 
    		if(null!=serviceOrder.getCustomRefValue(OFConstants.MATERIALS_TAX_AMOUNT)&&
    				(StringUtils.isNumeric(serviceOrder.getCustomRefValue(OFConstants.MATERIALS_TAX_AMOUNT)) 
    						|| Pattern.matches(OFConstants.PRICE_REGEX, serviceOrder.getCustomRefValue(OFConstants.MATERIALS_TAX_AMOUNT)))){
    			partsTaxAmount= BigDecimal.valueOf(Double.valueOf(serviceOrder.getCustomRefValue(OFConstants.MATERIALS_TAX_AMOUNT)).doubleValue());
    			serviceOrder.getSOWorkflowControls().setMaterialsTaxAmount(partsTaxAmount);
    		}  
    		
    		if(!taskPresent){
    			//SL-21284: Overriding SKU price with the price coming in API Request for standard services for Relay Services buyer 
    			if ((null!=serviceOrder.getCustomRefValue(OFConstants.ORDER_TYPE) && OFConstants.STANDARD.equalsIgnoreCase(serviceOrder.getCustomRefValue(OFConstants.ORDER_TYPE))
    					&& StringUtils.isNotBlank(serviceOrder.getCustomRefValue(OFConstants.SELECTED_FIRM_ID)))){
    				logger.info("FixedOrderPriceEnhancer SELECTED_FIRM_ID:"+serviceOrder.getCustomRefValue(OFConstants.SELECTED_FIRM_ID));
    				for (SOTask task : serviceOrder.getTasks()) {
    	               
    					 BigDecimal taskPrice = orderBuyer.getBuyerPricingScheme().getServiceOfferingPrice(buyerId,task.getExternalSku(),serviceOrder.getServiceLocation().getZip(),serviceOrder.getCustomRefValue(OFConstants.SELECTED_FIRM_ID));
    					/*if(null!= laborTaxPercentage && !laborTaxPercentage.equals(PricingUtil.ZERO)){
    						laborTaxPercentage=BigDecimal.valueOf(laborTaxPercentage.doubleValue()/100.00d);
    						taskPrice= MoneyUtil.getRoundedMoneyBigDecimal((taskPrice.add(taskPrice.multiply(laborTaxPercentage))).doubleValue());
    					}*/
    					 if (taskPrice!=null) {
    	    				if(SOPriceType.TASK_LEVEL.name().equals(serviceOrder.getPriceType())){
    	    	            	task.setPrice(taskPrice);
    	    	            	task.setFinalPrice(taskPrice);
    	    	            }
    	    				totalRetailPrice = totalRetailPrice.add(taskPrice);
    	    			}
    	    			else{
    	    				task.setPrice(PricingUtil.ZERO);
    	                	task.setFinalPrice(PricingUtil.ZERO);
    	    			}
    	            }
				/*	if(null!= laborTaxAmount && !laborTaxAmount.equals(PricingUtil.ZERO)){
    				totalRetailPrice=totalRetailPrice.add(laborTaxAmount);
					}*/
    				logger.info("FixedOrderPriceEnhancer taskPrice:"+totalRetailPrice);
    	    		PricingUtil.setTotalRetailPrice(serviceOrder, totalRetailPrice);	
    			}
    			else if ((null!=serviceOrder.getCustomRefValue(OFConstants.ORDER_TYPE) && OFConstants.STANDARD.equalsIgnoreCase(serviceOrder.getCustomRefValue(OFConstants.ORDER_TYPE))  )){
    				//SL-21284: Overriding SKU price with the price coming in API Request for standard services for Relay Services buyer 
    				// Set the price coming from the request.
        			logger.info(" Relay Service labourSpendLimit"+serviceOrder.getSpendLimitLabor());
        			logger.info(" Relay Service partsSpendLimit"+serviceOrder.getSpendLimitParts());
    				BigDecimal labourSpendLimit = PricingUtil.ZERO;
        			BigDecimal partsSpendLimit = PricingUtil.ZERO;
        			labourSpendLimit = serviceOrder.getSpendLimitLabor();
					/*if(null!= laborTaxPercentage && !laborTaxPercentage.equals(PricingUtil.ZERO)){
						laborTaxPercentage=BigDecimal.valueOf(laborTaxPercentage.doubleValue()/100.00d);
						labourSpendLimit= MoneyUtil.getRoundedMoneyBigDecimal((labourSpendLimit.add(labourSpendLimit.multiply(laborTaxPercentage))).doubleValue());
					}*/
        			/*if(null!= laborTaxAmount && !laborTaxAmount.equals(PricingUtil.ZERO)){
        				labourSpendLimit=labourSpendLimit.add(laborTaxAmount);
        			}*/
        			partsSpendLimit = serviceOrder.getSpendLimitParts();
					/*if(null!= partsTaxPercentage && !partsTaxPercentage.equals(PricingUtil.ZERO)){
						partsTaxPercentage=BigDecimal.valueOf(partsTaxPercentage.doubleValue()/100.00d);
						partsSpendLimit= MoneyUtil.getRoundedMoneyBigDecimal((partsSpendLimit.add(partsSpendLimit.multiply(partsTaxPercentage))).doubleValue());
					}*/
        			/*if(null!= partsTaxAmount && !partsTaxAmount.equals(PricingUtil.ZERO)){
        				partsSpendLimit=partsSpendLimit.add(partsTaxAmount); 
        			}*/
        			PricingUtil.setTotalRetailPriceForTasks(serviceOrder, labourSpendLimit, partsSpendLimit);
    			}
    			else{
    				for (SOTask task : serviceOrder.getTasks()) {
    	                BigDecimal taskPrice = getRetailPrice(task.getExternalSku(),
    	                                                orderEnhancementContext.getOrderBuyer());
    	    			if (taskPrice!=null) {
    	    				if(SOPriceType.TASK_LEVEL.name().equals(serviceOrder.getPriceType())){
    	    	            	task.setPrice(taskPrice);
    	    	            	task.setFinalPrice(taskPrice);
    	    	            }
    	    				totalRetailPrice = totalRetailPrice.add(taskPrice);
    	    			}
    	    			else{
    	    				task.setPrice(PricingUtil.ZERO);
    	                	task.setFinalPrice(PricingUtil.ZERO);
    	    			}
    	            }
    	    		PricingUtil.setTotalRetailPrice(serviceOrder, totalRetailPrice);
    			}
	    		
    		}else{
    			// Set the price coming from the request.
    			BigDecimal labourSpendLimit = PricingUtil.ZERO;
    			BigDecimal partsSpenLimit = PricingUtil.ZERO;
    			labourSpendLimit = serviceOrder.getSpendLimitLabor();
				/*if(null!= laborTaxPercentage && !laborTaxPercentage.equals(PricingUtil.ZERO)){
					laborTaxPercentage=BigDecimal.valueOf(laborTaxPercentage.doubleValue()/100.00d);
					labourSpendLimit= MoneyUtil.getRoundedMoneyBigDecimal((labourSpendLimit.add(labourSpendLimit.multiply(laborTaxPercentage))).doubleValue());
				}*/
    			/*if(null!= laborTaxAmount && !laborTaxAmount.equals(PricingUtil.ZERO)){
    				labourSpendLimit=labourSpendLimit.add(laborTaxAmount);
    			}*/
    			partsSpenLimit = serviceOrder.getSpendLimitParts();
				/*if(null!= partsTaxPercentage && !partsTaxPercentage.equals(PricingUtil.ZERO)){
					partsTaxPercentage=BigDecimal.valueOf(partsTaxPercentage.doubleValue()/100.00d);
					partsSpenLimit= MoneyUtil.getRoundedMoneyBigDecimal((partsSpenLimit.add(partsSpenLimit.multiply(partsTaxPercentage))).doubleValue());
				}*/
    			/*if(null!= partsTaxAmount && !partsTaxAmount.equals(PricingUtil.ZERO)){
    				partsSpenLimit=partsSpenLimit.add(partsTaxAmount); 
    			}*/ 			
    			PricingUtil.setTotalRetailPriceForTasks(serviceOrder, labourSpendLimit, partsSpenLimit);
    		}
    	}  
    	
    	PricingUtil.setTaxAndDiscount(serviceOrder, tempPrice);
	}

	private BigDecimal getRetailPrice(String externalSku, IOrderBuyer orderBuyer) {
        BuyerSkuPricingItem buyerSkuPricingItem = new BuyerSkuPricingItem(externalSku);
        orderBuyer.getBuyerPricingScheme().priceSkuItem(buyerSkuPricingItem);
        return buyerSkuPricingItem.getRetailPrice();
	}

}
