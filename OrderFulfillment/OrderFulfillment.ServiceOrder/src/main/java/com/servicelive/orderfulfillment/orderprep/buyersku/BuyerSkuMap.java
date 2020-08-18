package com.servicelive.orderfulfillment.orderprep.buyersku;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.servicelive.domain.so.BuyerOrderSku;
import com.servicelive.domain.so.BuyerOrderSkuTask;
import com.servicelive.orderfulfillment.common.ServiceOrderException;
import org.apache.log4j.Logger;
/**
 * topLevelSkuMap contains all skus for buyer.
 * categoryToSkuMap maps catetory(specialty) to available skus
 */
public class BuyerSkuMap {
    final Map<String, Map<String, BuyerOrderSku>> categoryToSkuMap;
    Map<String, BuyerOrderSku> topLevelSkuMap;
   Logger logger = Logger.getLogger(getClass());
    
    public BuyerSkuMap(Map<String, Map<String, BuyerOrderSku>> categoryToSkuMap, Map<String, BuyerOrderSku> topLevelSkuMap) {
        this.categoryToSkuMap = categoryToSkuMap;
        this.topLevelSkuMap = topLevelSkuMap;
    }

    public BuyerOrderSku getBuyerSku(String specialtyCode, String sku) {
    	logger.info("**inside getBuyerSku**");
    	BuyerOrderSku buyerOrder = getBuyerSku(sku);
    	logger.info("**after calling getBuyerSku**");
    	if(null !=buyerOrder){
    		logger.info("**going to fetch sku from buyerOrder**");
    		if(null != topLevelSkuMap){
    			logger.info("topLevelSkuMap keys ::"+topLevelSkuMap.keySet());
    		}
    		String sampleSku = buyerOrder.getSku();
    		logger.info("sku from topLevelSkuMap :"+sampleSku);
    		
    	}
    	
        Map<String, BuyerOrderSku> skuMap = categoryToSkuMap.get(specialtyCode);
        if (skuMap==null) {
            throw new ServiceOrderException("skuMap is not available for specialtyCode - " + specialtyCode);
        }
        return skuMap.get(sku);
    }

    public Collection<BuyerOrderSku> getBuyerSkusForCategory(String specialtyCode) {
        Map<String, BuyerOrderSku> skuMap = categoryToSkuMap.get(specialtyCode);
        return skuMap!=null ? skuMap.values() : null;
    }

    public BuyerOrderSku getBuyerSku(String sku) {
        return topLevelSkuMap.get(sku);
    }

    public List<BuyerOrderSkuTask> getBuyerSkuTasks(String specialtyCode, String sku) {
    	logger.info("**inside getBuyerSkuTasks of buyerskumap**");
    	BuyerOrderSku buyerSku = getBuyerSku(specialtyCode, sku);
    	if (buyerSku==null) {
            throw new ServiceOrderException("skuMap is not available for sku - " + sku);
    }
        return buyerSku.getBuyerSkuTaskList();
}
}
