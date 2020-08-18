package com.servicelive.orderfulfillment.orderprep.processor.ri;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.servicelive.orderfulfillment.domain.SOTask;
import org.apache.log4j.Logger;

import com.servicelive.domain.so.BuyerOrderRetailPrice;
import com.servicelive.domain.so.BuyerOrderSpecialtyAddOn;
import com.servicelive.orderfulfillment.dao.IOrderBuyerDao;
import com.servicelive.orderfulfillment.dao.IServiceOrderDao;
import com.servicelive.orderfulfillment.domain.SOAddon;
import com.servicelive.orderfulfillment.domain.SOCustomReference;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.util.PricingUtil;
import com.servicelive.orderfulfillment.orderprep.OrderEnhancementContext;
import com.servicelive.orderfulfillment.orderprep.buyer.IOrderBuyer;
import com.servicelive.orderfulfillment.orderprep.pricing.MarketAdjustedBuyerPricingScheme;
import com.servicelive.orderfulfillment.orderprep.processor.common.AbstractOrderEnhancer;
import com.servicelive.orderfulfillment.orderprep.processor.common.PermitSkuChecker;

public class OrderAddOnsEnhancer extends AbstractOrderEnhancer {
    protected final Logger logger = Logger.getLogger(getClass());

    PermitSkuChecker permitSkuChecker;
    IServiceOrderDao serviceOrderDao;
    IOrderBuyerDao orderBuyerDao;


    public void enhanceOrder(ServiceOrder serviceOrder, OrderEnhancementContext orderEnhancementContext) {
    	//SL-18368 no need to create so addons during order creation if the SO is created using invalid job codes and no tasks.
    	if (null != serviceOrder.getTasks() && serviceOrder.getTasks().size()>0){
	        List<SOAddon> newAddOnList = createAddOnsFromInput(serviceOrder, orderEnhancementContext.getOrderBuyer());
	        serviceOrder.getAddons().clear();
	        serviceOrder.setAddons(newAddOnList);
    	}
    }

    private List<SOAddon> createAddOnsFromInput(ServiceOrder serviceOrder, IOrderBuyer orderBuyer) {
        List<SOAddon> newAddOnList = new ArrayList<SOAddon>();

        String specialtyCode = serviceOrder.getCustomRefValue(SOCustomReference.CREF_SPECIALTY_CODE);
        String divisionCode = serviceOrder.getDivisionCode();
        String storeNumber = serviceOrder.getCustomRefValue(SOCustomReference.CREF_STORE_NUMBER);
        logger.info("OrderAddOnsEnhancer.specialtyCode"+specialtyCode);
        logger.info("OrderAddOnsEnhancer.divisionCode"+divisionCode);
        logger.info("OrderAddOnsEnhancer.storeNumber"+storeNumber);
        List<BuyerOrderSpecialtyAddOn> specialtyAddOnList = serviceOrderDao.getSpecialtyAddOnListBySpecialtyAndDivision(specialtyCode, divisionCode);
        for (BuyerOrderSpecialtyAddOn specialtyAddOn : specialtyAddOnList) {
            String sku = specialtyAddOn.getStockNumber();
            BigDecimal retailPrice = getRetailPrice(sku, storeNumber, orderBuyer.getBuyerId());
            logger.info("OrderAddOnsEnhancer.sku"+sku);
            logger.info("OrderAddOnsEnhancer.retailPrice"+retailPrice);
            boolean isMiscAddOn = isMiscAddOn(specialtyAddOn, divisionCode);
            logger.info("OrderAddOnsEnhancer.isMiscAddOn"+isMiscAddOn);
            if (notNullAndMoreThanAPenny(retailPrice) || isMiscAddOn) {
            	
                SOAddon addOn = createAddon(specialtyAddOn, sku, retailPrice, isMiscAddOn, 0);
                logger.info("OrderAddOnsEnhancer.addOn"+addOn);
                newAddOnList.add(addOn);
            }
        }

        //also get the call collect addons
        addCallCollectAddOn(serviceOrder, newAddOnList);
        return newAddOnList;
    }

    private SOAddon createAddon(BuyerOrderSpecialtyAddOn specialtyAddOn, String sku, BigDecimal retailPrice, boolean miscAddOn, int quantity) {
                SOAddon addOn = new SOAddon();
                addOn.setRetailPrice(retailPrice != null ? retailPrice : PricingUtil.ZERO);
        addOn.setMiscInd(miscAddOn);
                addOn.setCoverage(specialtyAddOn.getCoverage());
                addOn.setDescription(specialtyAddOn.getLongDescription());
                addOn.setMargin(specialtyAddOn.getMarkUpPercentage());
        addOn.setQuantity(quantity);
                addOn.setScopeOfWork(specialtyAddOn.getInclusionDescription());
                addOn.setSku(sku);
                addOn.setDispatchDaysOut(specialtyAddOn.getDispatchDaysOut());
                addOn.setServiceFee(PricingUtil.ZERO);
                logger.info("OrderAddOnsEnhancer.createAddon"+addOn);
        return addOn;
    }

    private void addCallCollectAddOn(ServiceOrder serviceOrder, List<SOAddon> newAddOnList) {
        String specialtyCode = serviceOrder.getCustomRefValue(SOCustomReference.CREF_SPECIALTY_CODE);
        String storeNumber = serviceOrder.getCustomRefValue(SOCustomReference.CREF_STORE_NUMBER);
        for (SOTask task : serviceOrder.getTasks()){
            BuyerOrderSpecialtyAddOn specialtyAddOn = serviceOrderDao.getCallCollectSpecialtyAddOn(specialtyCode, task.getExternalSku());
            BigDecimal retailPrice = getRetailPrice(task.getExternalSku(), storeNumber, serviceOrder.getBuyerId());
            logger.info("OrderAddOnsEnhancer.addCallCollectAddOn.specialtyAddOn"+specialtyAddOn);
            logger.info("OrderAddOnsEnhancer.addCallCollectAddOn.retailPrice"+retailPrice);
            if(null != specialtyAddOn && notNullAndMoreThanAPenny(retailPrice)){
                SOAddon addOn = createAddon(specialtyAddOn, task.getExternalSku(), retailPrice, false, 1);        
                newAddOnList.add(addOn);
                //zero out the price of task
                task.setPrice(PricingUtil.ZERO);
            }
        }
    }

    private BigDecimal getRetailPrice(String sku, String storeNumber, Long buyerId) {
        BuyerOrderRetailPrice buyerOrderRetailPrice = null;
        try {
            buyerOrderRetailPrice = orderBuyerDao.getBuyerOrderRetailPrice(buyerId, sku, storeNumber, MarketAdjustedBuyerPricingScheme.DFLT_STORE_NUMBER);
        } catch (Exception e) {
            String msg = String.format("Unable to get retail price for buyerid, sku, storeNo, defaultStoreNo - %d, %s, %s, %s", buyerId, sku, storeNumber, MarketAdjustedBuyerPricingScheme.DFLT_STORE_NUMBER);
            logger.error(msg, e);
        }
        if (buyerOrderRetailPrice != null) {
            return buyerOrderRetailPrice.getRetailPrice();
        } else {
            return null;
        }
    }

    private boolean notNullAndMoreThanAPenny(BigDecimal price) {
        return price!=null && price.compareTo(PricingUtil.PENNY)==1;
    }

    private boolean isMiscAddOn(BuyerOrderSpecialtyAddOn specialtyAddOn, String divisionCode) {
        String specialtySku = specialtyAddOn.getStockNumber();
        logger.info("OrderAddOnsEnhancer.specialtySku.substring(specialtySku.length()-3).equals(divisionCode)"+specialtySku.substring(specialtySku.length()-3).equals(divisionCode));
        logger.info("OrderAddOnsEnhancer.permitSkuChecker.isWorkPermitSku(specialtySku))"+permitSkuChecker.isWorkPermitSku(specialtySku));

        if (specialtySku == null) return false;
        return  specialtySku.substring(specialtySku.length()-3).equals(divisionCode)
                || permitSkuChecker.isWorkPermitSku(specialtySku);
    }

    ////////////////////////////////////////////////////////////////////////////
    // SETTERS FOR SPRING INJECTION
    ////////////////////////////////////////////////////////////////////////////


    public void setPermitSkuChecker(PermitSkuChecker permitSkuChecker) {
        this.permitSkuChecker = permitSkuChecker;
    }

    public void setServiceOrderDao(IServiceOrderDao serviceOrderDao) {
        this.serviceOrderDao = serviceOrderDao;
    }

    public void setOrderBuyerDao(IOrderBuyerDao orderBuyerDao) {
        this.orderBuyerDao = orderBuyerDao;
    }
}
