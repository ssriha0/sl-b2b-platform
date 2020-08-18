package com.servicelive.marketplatform.dao;

import com.servicelive.domain.buyer.BuyerResource;

import javax.persistence.Query;
import java.util.List;


public class BuyerResourceDao extends BaseEntityDao implements IBuyerResourceDao {
    public BuyerResource findById(long id) {
        return entityMgr.find(BuyerResource.class, (int)id);
    }

    public BuyerResource findBuyerResourceIdUsingContactId(long buyerId, int contactId) {
        Query q = entityMgr.createQuery("SELECT brsrc FROM BuyerResource brsrc WHERE buyer.buyerId = :buyerId AND contact.contactId = :contactId");
        q.setParameter("buyerId", (int)buyerId);
        q.setParameter("contactId", contactId);
        List result = q.getResultList();
        if (result.size() > 0) {
            return (BuyerResource) result.get(0);
        }
        return null;
    }
}
