package com.servicelive.orderfulfillment.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.exception.core.DataServiceException;
import com.servicelive.domain.so.BuyerOrderMarketAdjustment;
import com.servicelive.domain.so.BuyerOrderRetailPrice;
import com.servicelive.domain.so.BuyerOrderSku;
import com.servicelive.domain.so.BuyerOrderSkuAttribute;
import com.servicelive.domain.so.BuyerOrderTemplateRecord;
import org.apache.log4j.Logger;

public class OrderBuyerDao implements IOrderBuyerDao {

    protected final Logger logger = Logger.getLogger(getClass());

    @PersistenceContext()
    private EntityManager em;

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    public BuyerOrderTemplateRecord getBuyerTemplateRecord(Integer buyerTemplateRecordId){
    	return em.find(BuyerOrderTemplateRecord.class, buyerTemplateRecordId);
    }
    
    public List<BuyerOrderTemplateRecord> getBuyerTemplateRecords(long buyerId) {
        Query q = em.createQuery("SELECT tmpltRec FROM BuyerOrderTemplateRecord tmpltRec WHERE tmpltRec.buyerID = :buyerId");
        q.setParameter("buyerId", buyerId);
        @SuppressWarnings("unchecked")
        List<BuyerOrderTemplateRecord> resultList =  (List<BuyerOrderTemplateRecord>) q.getResultList();
        return resultList;
    }

    public List<BuyerOrderSku> getBuyerSkus(long buyerId) {
        Query q = em.createQuery("SELECT buyerSku FROM BuyerOrderSku buyerSku WHERE buyerSku.buyerId = :buyerId");
        q.setParameter("buyerId", buyerId);
        @SuppressWarnings("unchecked") List<BuyerOrderSku> resultList = (List<BuyerOrderSku>) q.getResultList(); 
        return resultList;
    }

    public List<BuyerOrderSkuAttribute> getBuyerSkuAttributes(Long skuId) {
        Query q = em.createQuery("SELECT buyerSkuAttr FROM BuyerOrderSkuAttribute buyerSkuAttr WHERE buyerSkuAttr.attributeId.skuId = :skuId");
        q.setParameter("skuId", skuId);
        @SuppressWarnings("unchecked")
        List<BuyerOrderSkuAttribute> resultList = (List<BuyerOrderSkuAttribute>) q.getResultList();
        return resultList;
    }

	public BuyerOrderRetailPrice getBuyerOrderRetailPrice(long buyerId, String sku, String storeNumber, String defaultStoreNumber) {
        //Since there is no evidence that the store number passed in is not going to have all numbers required to match we will not do like
        final String queryString =
                "SELECT buyerRetailPrice FROM BuyerOrderRetailPrice buyerRetailPrice"
                + " WHERE buyerRetailPrice.buyerId = :buyerId AND buyerRetailPrice.sku = :sku"
                //+ " AND (buyerRetailPrice.storeNumberString LIKE :storeNumber OR buyerRetailPrice.storeNumberString = :defaultStoreNumber)";
                + " AND buyerRetailPrice.storeNumberString in (:storeNumber,  :defaultStoreNumber)";
        Query q = em.createQuery(queryString);
        q.setParameter("buyerId", buyerId);
        q.setParameter("sku", sku);
        //q.setParameter("storeNumber", "%" + storeNumber);
        q.setParameter("storeNumber", storeNumber);
        q.setParameter("defaultStoreNumber", defaultStoreNumber);
        @SuppressWarnings("unchecked")
        List result = q.getResultList();
        if (result != null) {
            if(result.size() > 1){
                //make sure that we return the record with the store number rather than default store number
                for (Object o : result){
                    //logger.info(String.format("Store number in the result %1$s and the passed in store number is %2$s", ((BuyerOrderRetailPrice)o).getStoreNumberString(), storeNumber ));
                    if(((BuyerOrderRetailPrice)o).getStoreNumberString().equals(storeNumber))
                        return (BuyerOrderRetailPrice) o;
                }
            }else if(result.size() == 1){
            return (BuyerOrderRetailPrice) result.get(0);
            }
        }
        //by default return null;
            return null;
        }

    public BuyerOrderMarketAdjustment getBuyerOrderMarketAdjustmentWithZip(long buyerId, String zip) {
        try{
            final String queryString =
                    "select boma from BuyerOrderMarketAdjustment boma, LookupZipMarketPair luZip "
                    + " where boma.marketId = luZip.marketId AND boma.buyerId = :buyerId AND luZip.zip = :zip";
            Query q = em.createQuery(queryString);
            q.setParameter("buyerId", buyerId);
            q.setParameter("zip", zip);
            return (BuyerOrderMarketAdjustment) q.getSingleResult();
        }catch(Exception e){
        	e.printStackTrace();
        }
        return null;
    }
    
    //method to check whether market adjustment rate is on for a specific buyer
    public boolean isMARfeatureOn(Long buyerId){
    	boolean isMARfeatureOn = false;
    	try{   
    		final String queryString = 
    					"select count(active_ind) from buyer_feature_set " +
    					"where buyer_id = :buyerId and feature = :feature and active_ind = :ind";
    		Query q = em.createNativeQuery(queryString);
    		q.setParameter("buyerId", buyerId.intValue());
    		q.setParameter("feature", "MARKET_ADJUSTMENT_RATE");
    		q.setParameter("ind", 1);
    		BigInteger result = (BigInteger)q.getSingleResult();
    		if(1 == result.intValue()){
    			isMARfeatureOn = true;
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    		return isMARfeatureOn;
    	}    	
    	return isMARfeatureOn;
    }
    
    public BigDecimal getServiceOfferingPrice(long buyerId, String sku, String zip,String firmId) {
    	
		String hql = "SELECT sop.list_price FROM service_offerings so JOIN service_offerings_price sop ON " +
				"(so.service_offerings_id=sop.service_offering_id) " +
				"JOIN buyer_sku bs ON (bs.sku_id=so.sku_id) " +
				"WHERE so.vendor_id= :firmId AND bs.buyer_id= :buyerId AND bs.sku= :sku AND sop.zipcode= :zip AND sop.delete_ind= :ind";
		try {
			logger.info("getServiceOfferingPrice hql:"+hql);
			Query q = em.createNativeQuery(hql);
		    q.setParameter("firmId", firmId);
			q.setParameter("buyerId", buyerId);
			q.setParameter("sku", sku);
            q.setParameter("zip", zip);
            q.setParameter("ind", 0);
            BigDecimal price = (BigDecimal)q.getSingleResult();
			return price;
		} catch (Exception e) {
			logger.error("Exception in getServiceOfferingPrice" + e);
		}
		return null;
    }
}
