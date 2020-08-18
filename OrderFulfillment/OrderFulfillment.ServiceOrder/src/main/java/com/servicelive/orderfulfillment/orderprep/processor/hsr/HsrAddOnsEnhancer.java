package com.servicelive.orderfulfillment.orderprep.processor.hsr;

import com.newco.marketplace.exception.BusinessServiceException;
import com.servicelive.common.util.MoneyUtil;
import com.servicelive.domain.common.BuyerFeatureSetEnum;
import com.servicelive.domain.so.BuyerOrderSku;
import com.servicelive.domain.so.BuyerOrderSkuAttribute;
import com.servicelive.domain.so.BuyerOrderSkuCategory;
import com.servicelive.domain.so.BuyerOrderSkuTask;
import com.servicelive.domain.so.BuyerSkuGroups;
import com.servicelive.domain.so.type.BuyerSkuAttributeType;
import com.servicelive.domain.so.type.PriceType;
import com.servicelive.orderfulfillment.common.SkuTaxCalculation;
import com.servicelive.orderfulfillment.dao.IServiceOrderDao;
import com.servicelive.orderfulfillment.domain.SOAddon;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.lookup.BuyerFeatureLookup;
import com.servicelive.orderfulfillment.lookup.QuickLookupCollection;
import com.servicelive.orderfulfillment.orderprep.OrderEnhancementContext;
import com.servicelive.orderfulfillment.orderprep.buyer.IOrderBuyer;
import com.servicelive.orderfulfillment.orderprep.buyersku.BuyerSkuMap;
import com.servicelive.orderfulfillment.orderprep.processor.common.AbstractOrderEnhancer;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;

public class HsrAddOnsEnhancer extends AbstractOrderEnhancer {
	 protected final Logger logger = Logger.getLogger(getClass());
	 protected QuickLookupCollection quickLookupCollection;
	 IServiceOrderDao serviceOrderDao;
	 
    public void enhanceOrder(ServiceOrder serviceOrder, OrderEnhancementContext orderEnhancementContext) {
    	// Check if tasks are present 
    	boolean taskPresent = false;
    	taskPresent = serviceOrder.isTasksPresent();
    	logger.info("Task Present"  +taskPresent );
    	if(!taskPresent){
    		
    		List<SOAddon> newAddOnList = createAddOnsFromInput(serviceOrder, orderEnhancementContext.getOrderBuyer());
            serviceOrder.getAddons().clear();
            serviceOrder.setAddons(newAddOnList);
    	}
    }

    private List<SOAddon> createAddOnsFromInput(ServiceOrder serviceOrder, IOrderBuyer orderBuyer) {
        List<SOAddon> newAddOnList = new ArrayList<SOAddon>();
      
        String zipCode =serviceOrder.getServiceLocation().getZip();
        Long buyerId= serviceOrder.getBuyerId();
        String state = serviceOrder.getServiceLocation().getState();
        Collection<BuyerOrderSku> buyerOrderSkus = getBuyerSkusForPrimaryCategory(serviceOrder, orderBuyer);

        if (buyerOrderSkus != null) {
            for (BuyerOrderSku buyerOrderSku : buyerOrderSkus) {
                if (hasAddOnAttribute(buyerOrderSku)) {
                    SOAddon addOn = createAddonFromBuyerOrderSku(buyerOrderSku,zipCode,buyerId, state);
                    BuyerSkuGroups buyerSkuGroup = new BuyerSkuGroups();
					try {
						logger.info("Sku Value is " +buyerOrderSku.getSkuId());
						buyerSkuGroup = serviceOrderDao.getBuyerSkuGroups(buyerOrderSku.getSkuId());
						
					} catch (BusinessServiceException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                    if(buyerSkuGroup!=null){
                    	 logger.info("buyerSkuGroup "  +buyerSkuGroup.getSkuId() );
                    	addOn.setSkuGroupType(buyerSkuGroup.getGroup_type());
                    	
                    }
                    newAddOnList.add(addOn);
                }
            }
        }

        BuyerOrderSku mainSku = orderBuyer.getBuyerSkuMap().getBuyerSku(serviceOrder.getPrimaryTask().getExternalSku());
        logger.info("Main Sku"  +mainSku );
        if (mainSku != null) {
            if (hasAddOnReqAttribute(mainSku)) {
                SOAddon addOn = createAddonFromBuyerOrderSku(mainSku,zipCode,buyerId, state);
                addOn.setQuantity(1);
                addOn.setCoverage("CC");
                newAddOnList.add(addOn);
            }
        }

        return newAddOnList;
    }

    private Collection<BuyerOrderSku> getBuyerSkusForPrimaryCategory(ServiceOrder serviceOrder, IOrderBuyer orderBuyer) {
        BuyerSkuMap buyerSkuMap = orderBuyer.getBuyerSkuMap();
        BuyerOrderSkuCategory primarySkuCategory = getPrimaryBuyerSkuCategory(serviceOrder, buyerSkuMap);
        
        logger.info("primarySkuCategory"  +primarySkuCategory );
        if (primarySkuCategory != null) {
            return buyerSkuMap.getBuyerSkusForCategory(primarySkuCategory.getCategoryName());
        }
        logger.warn("HsrAddOnsEnhancer.getBuyerSkusForPrimaryCategory() returned null for sku " + serviceOrder.getPrimaryTask().getExternalSku());
        return null;
    }

    private BuyerOrderSkuCategory getPrimaryBuyerSkuCategory(ServiceOrder serviceOrder, BuyerSkuMap buyerSkuMap) {
        BuyerOrderSku buyerOrderSku = buyerSkuMap.getBuyerSku(serviceOrder.getPrimaryTask().getExternalSku());
        return buyerOrderSku!=null ? buyerOrderSku.getBuyerSkuCategory() : null;
    }

    private boolean hasAddOnAttribute(BuyerOrderSku buyerOrderSku) {
        return hasAttribute(buyerOrderSku, BuyerSkuAttributeType.ADDON);
    }

    private boolean hasAddOnReqAttribute(BuyerOrderSku buyerOrderSku) {
        return hasAttribute(buyerOrderSku, BuyerSkuAttributeType.ADDONREQ);
    }

    private SOAddon createAddonFromBuyerOrderSku(BuyerOrderSku buyerOrderSku, String zipCode,Long buyerId, String state) {
    	Double taxPercentage=0.0000;
    	BuyerFeatureLookup buyerFeatureLookup = quickLookupCollection.getBuyerFeatureLookup();
    	boolean allowTaxFlag  =buyerFeatureLookup.isActiveFeatureAssociatedWithBuyer(BuyerFeatureSetEnum.ALLOW_SKU_TAX, buyerId);
        SOAddon addOn = new SOAddon();
        SkuTaxCalculation skuTaxCalculation = new SkuTaxCalculation();
        String taxServiceUrl = getTaxServiceUrl(OrderfulfillmentConstants.TAX_SERVICE_KEY);
         taxPercentage = skuTaxCalculation.getTaxRateForSku(buyerOrderSku.getSku(), zipCode, state,taxServiceUrl).doubleValue();
        if (allowTaxFlag ){
        	 addOn.setTaxPercentage(taxPercentage);
        	  addOn.setRetailPrice(new BigDecimal(MoneyUtil.add(buyerOrderSku.getRetailPrice().doubleValue(),taxPercentage.doubleValue()*(buyerOrderSku.getRetailPrice().doubleValue()))));
			  
        }else{
        	 addOn.setRetailPrice(buyerOrderSku.getRetailPrice());
        }
      
    	
        addOn.setQuantity(0);
        addOn.setDescription(buyerOrderSku.getSkuDescription());
        addOn.setMargin(buyerOrderSku.getBidMargin().doubleValue());
        addOn.setMiscInd(buyerOrderSku.getPriceType() == PriceType.VARIABLE);
       
        addOn.setSku(buyerOrderSku.getSku());
        addOn.setScopeOfWork(getScopeOfWork(buyerOrderSku));
        addOn.setServiceFee(BigDecimal.ZERO);
        return addOn;
    }

    private boolean hasAttribute(BuyerOrderSku buyerOrderSku, BuyerSkuAttributeType buyerSkuAttributeType) {
        for (BuyerOrderSkuAttribute skuAttr : buyerOrderSku.getBuyerSkuAttributeList()) {
            if (skuAttr.getAttributeId().getSkuAttributeType() == buyerSkuAttributeType) {
                return true;
            }
        }
        return false;
    }

    private String getScopeOfWork(BuyerOrderSku buyerOrderSku) {
        StringBuilder stringBuilder = new StringBuilder("");
        for (BuyerOrderSkuTask orderSkuTask : buyerOrderSku.getBuyerSkuTaskList()) {
            stringBuilder.append(orderSkuTask.getTaskComments()).append("\n");
        }
        return stringBuilder.toString();
    }

	public QuickLookupCollection getQuickLookupCollection() {
		return quickLookupCollection;
	}

	public void setQuickLookupCollection(QuickLookupCollection quickLookupCollection) {
		this.quickLookupCollection = quickLookupCollection;
	}
    
	private String getTaxServiceUrl(String applicatioKey) {
        return quickLookupCollection.getApplicationPropertyLookup().getPropertyValue(applicatioKey);
    }

	public IServiceOrderDao getServiceOrderDao() {
		return serviceOrderDao;
	}

	public void setServiceOrderDao(IServiceOrderDao serviceOrderDao) {
		this.serviceOrderDao = serviceOrderDao;
	}
	
}
