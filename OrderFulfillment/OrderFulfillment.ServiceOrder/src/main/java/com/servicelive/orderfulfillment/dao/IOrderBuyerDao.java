package com.servicelive.orderfulfillment.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import com.servicelive.domain.so.BuyerOrderMarketAdjustment;
import com.servicelive.domain.so.BuyerOrderRetailPrice;
import com.servicelive.domain.so.BuyerOrderSku;
import com.servicelive.domain.so.BuyerOrderSkuAttribute;
import com.servicelive.domain.so.BuyerOrderTemplateRecord;

public interface IOrderBuyerDao {
    void setEntityManager(EntityManager em);

    public BuyerOrderTemplateRecord getBuyerTemplateRecord(Integer buyerTemplateId);
    List<BuyerOrderTemplateRecord> getBuyerTemplateRecords(long buyerId);
    List<BuyerOrderSku> getBuyerSkus(long buyerId);
    List<BuyerOrderSkuAttribute> getBuyerSkuAttributes(Long skuId);
    BuyerOrderRetailPrice getBuyerOrderRetailPrice(long buyerId, String sku, String storeNumber, String defaultStoreNumber);
    BuyerOrderMarketAdjustment getBuyerOrderMarketAdjustmentWithZip(long buyerId, String zip);
    //check whether marketAdjustmentRate feature is on for a buyer
	boolean isMARfeatureOn(Long buyerId);

	BigDecimal getServiceOfferingPrice(long buyerId,String sku,String zip,String firmId);
}
