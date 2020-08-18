package com.servicelive.orderfulfillment.orderprep.pricing;

import com.servicelive.domain.so.BuyerOrderSku;
import com.servicelive.orderfulfillment.dao.IOrderBuyerDao;
import com.servicelive.orderfulfillment.orderprep.buyersku.BuyerSkuMap;
import com.servicelive.orderfulfillment.orderprep.buyersku.BuyerSkuPricingItem;

public class FixedBuyerPricingScheme extends CommonPricingScheme  {
    final Long buyerId;
    final BuyerSkuMap buyerSkuMap;
    IOrderBuyerDao orderBuyerDao;
 
	public FixedBuyerPricingScheme(Long buyerId, BuyerSkuMap buyerSkuMap) {
		super(buyerId,buyerSkuMap);
        this.buyerId = buyerId;
        this.buyerSkuMap = buyerSkuMap;
    }
	
	public IOrderBuyerDao getOrderBuyerDao() {
		return orderBuyerDao;
	}

	public void setOrderBuyerDao(IOrderBuyerDao orderBuyerDao) {
		super.setOrderBuyerDao(orderBuyerDao);
		this.orderBuyerDao = orderBuyerDao;
	}

    public void priceSkuItem(BuyerSkuPricingItem buyerSkuPricingItem) {    	
        BuyerOrderSku buyerSku = buyerSkuMap.getBuyerSku(buyerSkuPricingItem.getSku());
        buyerSkuPricingItem.setRetailPrice(buyerSku.getBidPrice());
    }      

    

}
