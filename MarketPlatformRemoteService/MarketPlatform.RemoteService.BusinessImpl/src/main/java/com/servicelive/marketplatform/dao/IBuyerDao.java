package com.servicelive.marketplatform.dao;

import java.math.BigDecimal;
import java.util.List;

import com.servicelive.domain.buyer.Buyer;
import com.servicelive.domain.buyer.BuyerHoldTime;
import com.servicelive.domain.buyer.SimpleBuyerFeature;
import com.servicelive.domain.so.BuyerReferenceType;


public interface IBuyerDao {
    public Buyer findById(long id);
    public BigDecimal getTripCharge(long buyerId, int skillCategoryId);
    public List<BuyerReferenceType> getBuyerReferenceTypes(long buyerId);
    public List<SimpleBuyerFeature> getAllSimpleBuyerFeatures();
	public List<BuyerHoldTime> getAllBuyerHoldTime();
}