package com.servicelive.orderfulfillment.orderprep.pricing;

import com.servicelive.domain.so.BuyerOrderSku;
import com.servicelive.orderfulfillment.dao.IOrderBuyerDao;
import com.servicelive.orderfulfillment.domain.util.PricingUtil;
import com.servicelive.orderfulfillment.orderprep.buyersku.BuyerSkuMap;
import com.servicelive.orderfulfillment.orderprep.buyersku.BuyerSkuPricingItem;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class HsrMarketAdjustedBuyerPricingScheme extends CommonPricingScheme {
    protected final Logger logger = Logger.getLogger(getClass());

    final Long buyerId;
    final BuyerSkuMap buyerSkuMap;
    IOrderBuyerDao orderBuyerDao;

	public HsrMarketAdjustedBuyerPricingScheme(Long buyerId, BuyerSkuMap buyerSkuMap) {
    	super(buyerId,buyerSkuMap);
        this.buyerId = buyerId;
        this.buyerSkuMap = buyerSkuMap;
    }

    public void priceSkuItem(BuyerSkuPricingItem buyerSkuPricingItem) {
        BigDecimal calculatedRetailPrice;

        BuyerOrderSku buyerOrderSku = buyerSkuMap.getBuyerSku(buyerSkuPricingItem.getSku());
        switch (buyerSkuPricingItem.getBidPriceSchema()) {
            case DEFAULT:
                calculatedRetailPrice = buyerOrderSku.getBidPrice();
                break;
            case MARGIN_ADJUST:
                BigDecimal skuBidMargin = buyerOrderSku.getBidMargin();
                BigDecimal skuRetailPrice = buyerOrderSku.getRetailPrice();
                calculatedRetailPrice = PricingUtil.ONE_SCALED_WITH_4.subtract(skuBidMargin).multiply(skuRetailPrice);
                break;
            default:
                calculatedRetailPrice = PricingUtil.ZERO;
        }

        buyerSkuPricingItem.setRetailPrice(calculatedRetailPrice.setScale(2, RoundingMode.HALF_DOWN));
    }
    
    public IOrderBuyerDao getOrderBuyerDao() {
		return orderBuyerDao;
	}

	public void setOrderBuyerDao(IOrderBuyerDao orderBuyerDao) {
		super.setOrderBuyerDao(orderBuyerDao);
		this.orderBuyerDao = orderBuyerDao;
	}

	
}