package com.servicelive.orderfulfillment.orderprep.processor.ri;


import com.servicelive.domain.so.BuyerOrderRetailPrice;
import com.servicelive.domain.so.BuyerOrderSpecialtyAddOn;
import com.servicelive.orderfulfillment.common.OFConstants;
import com.servicelive.orderfulfillment.dao.IServiceOrderDao;
import com.servicelive.orderfulfillment.domain.SOCustomReference;
import com.servicelive.orderfulfillment.domain.SOTask;
import com.servicelive.orderfulfillment.domain.SOTaskHistory;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.ProblemType;
import com.servicelive.orderfulfillment.domain.util.PricingUtil;
import com.servicelive.orderfulfillment.orderprep.OrderEnhancementContext;
import com.servicelive.orderfulfillment.orderprep.buyer.IOrderBuyer;
import com.servicelive.orderfulfillment.orderprep.buyersku.BuyerSkuPricingItem;
import com.servicelive.orderfulfillment.orderprep.processor.common.AbstractOrderPriceEnhancer;
import com.servicelive.orderfulfillment.orderprep.processor.common.PermitSkuChecker;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;

import java.math.BigDecimal;
import java.util.Date;

public class OrderPriceEnhancer extends AbstractOrderPriceEnhancer {
    IServiceOrderDao serviceOrderDao;
    PermitSkuChecker permitSkuChecker;

    @Override
    protected void calculateAndSetPriceInfo(ServiceOrder serviceOrder, OrderEnhancementContext orderEnhancementContext) {
        if (cannotProceedWithOrderPricing(serviceOrder)) {
            validationUtil.addWarningsIfBlank(serviceOrder.getCustomRefValue(SOCustomReference.CREF_STORE_NUMBER), serviceOrder.getValidationHolder(), ProblemType.MissingStoreNumberForPricing);
            validationUtil.addWarningsIfNull(serviceOrder.getServiceLocation(), serviceOrder.getValidationHolder(), ProblemType.MissingZipForPricing);
            validationUtil.addWarningsIfBlank(serviceOrder.getServiceLocation().getZip(), serviceOrder.getValidationHolder(), ProblemType.MissingZipForPricing);
            return;
        }

        String specialtyCode = serviceOrder.getCustomRefValue(SOCustomReference.CREF_SPECIALTY_CODE);
        String storeNumber = serviceOrder.getCustomRefValue(SOCustomReference.CREF_STORE_NUMBER);
        String serviceLocationZip = serviceOrder.getServiceLocation().getZip();
        Long buyerId = serviceOrder.getBuyerId();
        BigDecimal totalRetailPrice = PricingUtil.ZERO;
        BigDecimal totalPermitPrice = PricingUtil.ZERO;
        BuyerOrderRetailPrice buyerPrice = null;
        StringBuilder note = new StringBuilder();
        for (SOTask soTask : serviceOrder.getTasks()) {
        	BigDecimal taskPrice = soTask.getPrice();
        	BigDecimal retailPrice =PricingUtil.ZERO;
        	if (taskPrice == null) { 
        		continue;
        	}
        	
        	logger.info("taskPrice-------------"+taskPrice);
        	soTask.setSellingPrice(taskPrice);
            BuyerOrderSpecialtyAddOn specialtyAddOn = serviceOrderDao.getSpecialtyAddOn(specialtyCode, soTask.getExternalSku());
            if (specialtyAddOn != null && specialtyAddOn.getCoverage().equals("CC")) continue;
            Double specialtyAddOnMarkUpPercentage = specialtyAddOn != null ? specialtyAddOn.getMarkUpPercentage() : 0.0000;
            logger.info("soTask.getExternalSku()-------------"+soTask.getExternalSku());
            logger.info("isWorkPermitSku-------------"+permitSkuChecker.isWorkPermitSku(soTask.getExternalSku()));
          
            if (!permitSkuChecker.isWorkPermitSku(soTask.getExternalSku()) || "65000".equals(soTask.getExternalSku())) {
                taskPrice = getRetailPrice(specialtyCode, soTask.getExternalSku(),
                                            storeNumber, serviceLocationZip,
                                            specialtyAddOnMarkUpPercentage,
                                            orderEnhancementContext.getOrderBuyer(),
                                            note, soTask.getTaskName());
                logger.info("calculated getRetailPrice-----------"+taskPrice);
                
                try {
    				buyerPrice=serviceOrderDao.findByStoreNoAndSKU(storeNumber, soTask.getExternalSku(),serviceOrder.getBuyerId(), OrderfulfillmentConstants.DEFAULT_STORE_NO);
    			if(buyerPrice!=null)
    			{
    				retailPrice=buyerPrice.getRetailPrice();
    				 soTask.setRetailPrice(retailPrice);
    			}
    			else{
    				soTask.setRetailPrice(PricingUtil.ZERO);
    			}
                } catch (Exception e) {
    				
                	logger.error("Exception while  calculating the retail price");
    			}
                if (taskPrice!=null) {
                    soTask.setPrice(taskPrice);
                   
                    soTask.setFinalPrice(taskPrice);
                } else {
                    //TODO: warn that price could not be found
                    soTask.setPrice(PricingUtil.ZERO);
                    
                    soTask.setFinalPrice(PricingUtil.ZERO);
                }
            } else {
            	if (taskPrice!=null) {
            		soTask.setPrice(taskPrice);
            		soTask.setRetailPrice(taskPrice);
            		soTask.setFinalPrice(taskPrice);
            		totalPermitPrice = totalPermitPrice.add(taskPrice);
            	}
            	else {
                    soTask.setPrice(PricingUtil.ZERO);
                    soTask.setRetailPrice(PricingUtil.ZERO);
                    soTask.setFinalPrice(PricingUtil.ZERO);
                }
            	if(null!=soTask.getExternalSku() && soTask.getExternalSku().equals(OrderfulfillmentConstants.PERMIT_SKU)){
            	//soTask.setPermitTask(true);
            	soTask.setTaskType(OFConstants.PERMIT_TASK);
            		
            	}
            
            }
            //Adding Task price history here since final price is not available in CommomOrderTaskEnhancer
            SOTaskHistory sOTaskHistory =new SOTaskHistory();
			
			sOTaskHistory.setPrice(soTask.getFinalPrice());
			sOTaskHistory.setSku(soTask.getExternalSku());
        	Date startingDate = new Date();
        	sOTaskHistory.setModifiedDate(startingDate);
        	sOTaskHistory.setModifiedByName("SYSTEM");
        	sOTaskHistory.setModifiedBy("SYSTEM");
        	sOTaskHistory.setCreatedDate(startingDate);
        	sOTaskHistory.setTaskSeqNum(soTask.getTaskSeqNum());
        	sOTaskHistory.setTask(soTask);
        	//sOTaskHistory.setServiceOrder(soTask.getServiceOrder());        	
        	soTask.getTaskHistory().add(sOTaskHistory);
            
            logger.info("calculated soTask.getPrice()-----------"+soTask.getPrice());
            logger.info("calculated soTask.getRetailPrice()-----------"+soTask.getRetailPrice());
            logger.info("calculated soTask.getFinalPrice()-----------"+soTask.getFinalPrice());
            logger.info("calculated soTask.getSellingPrice()-----------"+soTask.getSellingPrice());
            if (taskPrice!=null) totalRetailPrice = totalRetailPrice.add(taskPrice);
        }
        BigDecimal marketAdjRate = orderEnhancementContext.getOrderBuyer().getBuyerPricingScheme().getMarketAdjustmentRate(buyerId, serviceLocationZip);
        if(0 != note.length()){
            orderEnhancementContext.getOrderBuyer().getBuyerPricingScheme().createNoteForSkuWithMAR(marketAdjRate, serviceOrder, note);
        }
        logger.info("calculated totalPermitPrice-----------"+totalPermitPrice);
        serviceOrder.setTotalPermitPrice(totalPermitPrice);
        PricingUtil.setTotalRetailPrice(serviceOrder, totalRetailPrice);
    }

    private boolean cannotProceedWithOrderPricing(ServiceOrder serviceOrder) {
        return
                serviceOrder.getServiceLocation() == null
                        || serviceOrder.getServiceLocation().getZip() == null
                        ||serviceOrder.isCreateWithOutTasks()
                        || serviceOrder.getValidationHolder().getWarnings().contains(ProblemType.TaskTranslationProblem)
                        || serviceOrder.getCustomRefValue(SOCustomReference.CREF_STORE_NUMBER) == null
                       
                ;
    }

    private BigDecimal getRetailPrice(String specialtyCode, String sku, String storeNumber, String serviceLocationZip, 
    		Double margin, IOrderBuyer orderBuyer, StringBuilder note, String taskName) {
        BuyerSkuPricingItem buyerSkuPricingItem = new BuyerSkuPricingItem(specialtyCode, sku, storeNumber, serviceLocationZip, margin);
        orderBuyer.getBuyerPricingScheme().priceSkuItem(buyerSkuPricingItem, note, taskName);
        return buyerSkuPricingItem.getRetailPrice();
    }

    ////////////////////////////////////////////////////////////////////////////
    // SETTERS FOR SPRING INJECTION
    ////////////////////////////////////////////////////////////////////////////

    public void setServiceOrderDao(IServiceOrderDao serviceOrderDao) {
        this.serviceOrderDao = serviceOrderDao;
    }

    public void setPermitSkuChecker(PermitSkuChecker permitSkuChecker) {
        this.permitSkuChecker = permitSkuChecker;
    }
}
