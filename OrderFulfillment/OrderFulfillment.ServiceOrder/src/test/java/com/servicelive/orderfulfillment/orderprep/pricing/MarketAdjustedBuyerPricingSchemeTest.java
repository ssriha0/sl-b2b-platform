package com.servicelive.orderfulfillment.orderprep.pricing;



import static org.mockito.Mockito.*;

import java.math.BigDecimal;

import org.junit.Test;

import com.servicelive.domain.so.BuyerOrderRetailPrice;
import com.servicelive.orderfulfillment.dao.IOrderBuyerDao;
import com.servicelive.orderfulfillment.dao.OrderBuyerDao;
import com.servicelive.orderfulfillment.orderprep.buyersku.BuyerSkuPricingItem;

public class MarketAdjustedBuyerPricingSchemeTest{

	private MarketAdjustedBuyerPricingScheme marketAdjust;
	private OrderBuyerDao orderBuyerDao;
	public static final String DFLT_STORE_NUMBER = "09300";
	
    @Test
    public void testPriceSkuItem() {
        
    	marketAdjust = new MarketAdjustedBuyerPricingScheme(new Long(1000), null);
    	
    	BuyerSkuPricingItem skuPricingItem = new BuyerSkuPricingItem("GARAGEOPEN", "2356", "09400", "60913", 0.003);

    	
    	BuyerOrderRetailPrice orderRetailPrice = new BuyerOrderRetailPrice();
    	orderRetailPrice.setRetailPrice(new BigDecimal(65.00));
    	
    	orderBuyerDao = mock(OrderBuyerDao.class);
    	marketAdjust.setOrderBuyerDao(orderBuyerDao);
    	marketAdjust.setBuyerId(Long.parseLong("1000"));
    	
    	//Long buyerId = (long) 1000;
    	StringBuilder note= new StringBuilder();
    	String taskName = "09400-Task Name1";
    	
    	when(orderBuyerDao.getBuyerOrderRetailPrice(marketAdjust.getBuyerId(), "09400", "2356", DFLT_STORE_NUMBER)).thenReturn(orderRetailPrice);
    	
    	marketAdjust.priceSkuItem(skuPricingItem,note,taskName);
  
    }
}
