package com.servicelive.marketplatform.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.servicelive.domain.buyer.Buyer;
import com.servicelive.domain.buyer.BuyerHoldTime;
import com.servicelive.domain.buyer.SimpleBuyerFeature;
import com.servicelive.domain.so.BuyerReferenceType;


public class BuyerDao extends BaseEntityDao implements IBuyerDao {
	 protected final Logger logger = Logger.getLogger(getClass());
    public Buyer findById(long id) {
    	Buyer b = new Buyer();
    	b= entityMgr.find(Buyer.class, (int)id);
    	if(null==b){
    		logger.info("buyer object is null");
    	}
        return b;
    }

    public BigDecimal getTripCharge(long buyerId, int skillCategoryId) {
        Query query = entityMgr.createNativeQuery("SELECT trip_charge FROM buyer_trip_charge_by_skill WHERE buyer_id = ? AND node_id = ?");
        query.setParameter(1, buyerId);
        query.setParameter(2, skillCategoryId);

        try{
            BigDecimal result = (BigDecimal)query.getSingleResult();
            return result;
        }catch(NoResultException nre){
            return new BigDecimal("0.00");
        }
    }

	@SuppressWarnings("unchecked")
    public List<BuyerReferenceType> getBuyerReferenceTypes(long buyerId) {
        Query q = entityMgr.createQuery("SELECT reftyp FROM BuyerReferenceType reftyp WHERE reftyp.buyer.buyerId = :buyerId AND reftyp.activeInd = 1 Order By reftyp.sortOrder");
        q.setParameter("buyerId", (int)buyerId);
        return (List<BuyerReferenceType>) q.getResultList();
    }

	@SuppressWarnings("unchecked")
    public List<SimpleBuyerFeature> getAllSimpleBuyerFeatures() {
        Query q = entityMgr.createQuery("SELECT features FROM SimpleBuyerFeature features");
        return (List<SimpleBuyerFeature>) q.getResultList();
    }

	@SuppressWarnings("unchecked")
	public List<BuyerHoldTime> getAllBuyerHoldTime() {
        Query q = entityMgr.createQuery("SELECT holdTime FROM BuyerHoldTime holdTime");
        return (List<BuyerHoldTime>) q.getResultList();
	}
}
