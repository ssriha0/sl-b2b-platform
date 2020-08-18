package com.servicelive.orderfulfillment.orderprep.pricing;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.servicelive.orderfulfillment.orderprep.buyersku.BuyerSkuPricingItem;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.servicelive.domain.so.BuyerOrderMarketAdjustment;
import com.servicelive.domain.so.BuyerOrderRetailPrice;
import com.servicelive.orderfulfillment.dao.IOrderBuyerDao;
import com.servicelive.orderfulfillment.orderprep.buyersku.BuyerSkuMap;

public class MarketAdjustedBuyerPricingScheme extends CommonPricingScheme {
    protected final Logger logger = Logger.getLogger(getClass());

    final Long buyerId;
    final BuyerSkuMap buyerSkuMap;
    IOrderBuyerDao orderBuyerDao;
    String defaultStoreNo;

    private static final BigDecimal ONE_SCALED_WITH_4 = new BigDecimal("1.0000");
    public static final String DFLT_STORE_NUMBER = "09300";

    public MarketAdjustedBuyerPricingScheme(Long buyerId, BuyerSkuMap buyerSkuMap) {
    	super(buyerId,buyerSkuMap);
        this.buyerId = buyerId;
        this.buyerSkuMap = buyerSkuMap;
        if (defaultStoreNo ==  null) defaultStoreNo = DFLT_STORE_NUMBER;
    }

    @Transactional(readOnly = true)
    public void priceSkuItem(BuyerSkuPricingItem buyerSkuPricingItem, StringBuilder note, String taskName) {
        String storeNo = buyerSkuPricingItem.getStoreNo();
        BuyerOrderRetailPrice orderRetailPrice = null;
        Exception error = null;
        try {
            orderRetailPrice = orderBuyerDao.getBuyerOrderRetailPrice(buyerId, buyerSkuPricingItem.getSku(), storeNo, defaultStoreNo);
        } catch (Exception e) {
            error = e;
        }
        
        if (orderRetailPrice == null) {
            String msg = String.format("Unable to get retail price for buyerid, sku, storeNo, defaultStoreNo - %d, %s, %s, %s", buyerId, buyerSkuPricingItem.getSku(), storeNo, defaultStoreNo);
            if (error != null) {
                logger.warn(msg, error);
            } else {
                logger.warn(msg);
            }
        } else {
            BigDecimal adjustedPrice = applyAdjustmentToPrice(orderRetailPrice.getRetailPrice(), buyerSkuPricingItem.getMargin(), buyerSkuPricingItem.getServiceLocationZip());
            if(null != orderRetailPrice.getRetailPrice()){
            	int start = buyerSkuPricingItem.getSku().length()+1;
        		if(taskName.contains(buyerSkuPricingItem.getSku())){
        			taskName = taskName.substring(start);
        		}
            	BigDecimal oldPrice = ONE_SCALED_WITH_4.subtract(buyerSkuPricingItem.getMargin()).multiply(orderRetailPrice.getRetailPrice());
				note.append(java.text.NumberFormat.getCurrencyInstance().format(oldPrice.setScale(2, RoundingMode.HALF_DOWN))+" to "+
						java.text.NumberFormat.getCurrencyInstance().format(adjustedPrice.setScale(2, RoundingMode.HALF_DOWN))+" - "+buyerSkuPricingItem.getSku()+" : "+taskName+"<br>");
            }            
            buyerSkuPricingItem.setRetailPrice(adjustedPrice.setScale(2, RoundingMode.HALF_DOWN));
        }
    }

    private BigDecimal applyAdjustmentToPrice(BigDecimal retailPrice, BigDecimal marginRate, String serviceLocationZip) {
        BuyerOrderMarketAdjustment marketAdjustment = orderBuyerDao.getBuyerOrderMarketAdjustmentWithZip(buyerId, serviceLocationZip);
        if (marketAdjustment == null) return retailPrice;
        // (1 - marginRate) * marketAdjustment * retailPrice
        return ONE_SCALED_WITH_4.subtract(marginRate).multiply(marketAdjustment.getAdjustment()).multiply(retailPrice);
    }

    ////////////////////////////////////////////////////////////////////////////
    // SETTERS FOR SPRING INJECTION
    ////////////////////////////////////////////////////////////////////////////

    public void setOrderBuyerDao(IOrderBuyerDao orderBuyerDao) {
    	super.setOrderBuyerDao(orderBuyerDao);
        this.orderBuyerDao = orderBuyerDao;
    }

    public void setDefaultStoreNo(String defaultStoreNo) {
        this.defaultStoreNo = defaultStoreNo;
    }
}
