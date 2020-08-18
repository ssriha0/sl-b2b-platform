package com.servicelive.marketplatform.serviceinterface;

import java.math.BigDecimal;
import java.util.List;



import com.servicelive.domain.buyer.Buyer;
import com.servicelive.domain.buyer.BuyerHoldTime;
import com.servicelive.domain.buyer.BuyerResource;
import com.servicelive.domain.buyer.SimpleBuyerFeature;
import com.servicelive.domain.common.Contact;
import com.servicelive.domain.so.BuyerReferenceType;
import com.servicelive.marketplatform.common.vo.BuyerCallbackEventVO;



public interface IMarketPlatformBuyerBO {
    public Contact retrieveBuyerContactInfo(Long buyerId);
    public List<Contact> retrieveBuyerContactInfoList(List<Long> buyerIdList);
    public Contact retrieveBuyerResourceContactInfo(Long buyerRsrcId);
    public Integer findBuyerResourceIdUsingContactId(Long buyerId, Integer contactId);
    public List<Contact> retrieveBuyerResourceContactInfoList(List<Long> buyerRsrcIdList);
    public Buyer retrieveBuyer(Long buyerId);
    public BuyerResource getBuyerResource(Long buyerResourceId);
    public List<SimpleBuyerFeature> retrieveAllSimpleBuyerFeatures();
	public BigDecimal getTripChargeByBuyerAndSkillCategory(Long buyerId, Integer skillCategoryId);
    public List<BuyerReferenceType> getBuyerReferenceTypes(long buyerId);
    public Integer retrieveBuyerDocumentIdByTitle(Long buyerId, String title);
    public List<String> insertServiceOrderBuyerDocuments(String serviceOrderId, Long buyerId, List<String> documentTitles);
    public List<BuyerHoldTime> retrieveAllBuyerHoldTime();
    
   public BuyerCallbackEventVO fetchBuyerCallbackEvent(String buyerId, String actionId);
}