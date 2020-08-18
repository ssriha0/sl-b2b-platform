package com.servicelive.marketplatform.dao;

import com.servicelive.domain.buyer.BuyerResource;

public interface IBuyerResourceDao {
    public BuyerResource findById(long id);
    public BuyerResource findBuyerResourceIdUsingContactId(long buyerId, int contactId);
}
