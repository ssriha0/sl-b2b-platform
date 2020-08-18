package com.servicelive.orderfulfillment.orderprep.buyer;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.servicelive.domain.so.BuyerOrderSku;
import com.servicelive.orderfulfillment.dao.IOrderBuyerDao;
import com.servicelive.orderfulfillment.orderprep.buyersku.BuyerSkuMap;

public class BuyerSkuLoader {
    IOrderBuyerDao orderBuyerDao;

    /**
     * topLevelSkuMap contains all skus for buyer
     * secondarySkuMap is created for each category under category-to-sku-map
     * @param buyerId
     */
    public BuyerSkuMap getBuyerSkuMap(long buyerId) {
        Map<String, Map<String, BuyerOrderSku>> catToSkuMap = new TreeMap<String, Map<String, BuyerOrderSku>>();
        Map<String, BuyerOrderSku> topLevelSkuMap = new TreeMap<String, BuyerOrderSku>();

        List<BuyerOrderSku> buyerSkus = orderBuyerDao.getBuyerSkus(buyerId);
        for (BuyerOrderSku buyerSku : buyerSkus) {
            buyerSku.setBuyerSkuAttributeList(orderBuyerDao.getBuyerSkuAttributes(buyerSku.getSkuId()));
            if(buyerSku.getBuyerSkuCategory()!=null){
	            String catNm = buyerSku.getBuyerSkuCategory().getCategoryName();
	            Map<String, BuyerOrderSku> secondarySkuMap;
	            if (!catToSkuMap.containsKey(catNm)) {
	                secondarySkuMap = new TreeMap<String, BuyerOrderSku>();
	                catToSkuMap.put(catNm, secondarySkuMap);
	            } else {
	                secondarySkuMap = catToSkuMap.get(catNm);
	            }
	            secondarySkuMap.put(buyerSku.getSku(), buyerSku);
            }
            topLevelSkuMap.put(buyerSku.getSku(), buyerSku);
        }

        return new BuyerSkuMap(catToSkuMap, topLevelSkuMap);
    }

    ////////////////////////////////////////////////////////////////////////////
    // SETTERS FOR SPRING INJECTION
    ////////////////////////////////////////////////////////////////////////////

    public void setOrderBuyerDao(IOrderBuyerDao orderBuyerDao) {
        this.orderBuyerDao = orderBuyerDao;
    }
}
