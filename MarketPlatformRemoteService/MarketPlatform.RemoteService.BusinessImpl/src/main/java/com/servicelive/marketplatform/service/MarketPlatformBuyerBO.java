package com.servicelive.marketplatform.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.servicelive.common.util.SerializationHelper;
import com.servicelive.domain.common.Location;
import com.servicelive.domain.lookup.LookupStates;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.newco.marketplace.business.buyerCallBackEvent.BuyerCallbackEventsCache;
import com.newco.marketplace.business.iBusiness.buyerCallbackEvent.IBuyerCallbackEventBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.buyerCallbackEvent.BuyerCallbackEvent;
import com.servicelive.domain.buyer.Buyer;
import com.servicelive.domain.buyer.BuyerHoldTime;
import com.servicelive.domain.buyer.BuyerResource;
import com.servicelive.domain.buyer.SimpleBuyerFeature;
import com.servicelive.domain.common.Contact;
import com.servicelive.domain.so.BuyerReferenceType;
import com.servicelive.marketplatform.common.exception.MarketPlatformException;
import com.servicelive.marketplatform.common.vo.BuyerCallbackEventVO;
import com.servicelive.marketplatform.dao.IBuyerDao;
import com.servicelive.marketplatform.dao.IBuyerResourceDao;
import com.servicelive.marketplatform.serviceinterface.IMarketPlatformBuyerBO;
import com.servicelive.marketplatform.serviceinterface.IMarketPlatformDocumentBO;

public class MarketPlatformBuyerBO implements IMarketPlatformBuyerBO {
    protected final Logger logger = Logger.getLogger(getClass());

    IBuyerDao buyerDao;
    IBuyerResourceDao buyerResourceDao;
    IMarketPlatformDocumentBO marketPlatformDocumentBO;
 //   IBuyerCallbackEventBO buyerCallbackEventBO;
    BuyerCallbackEventsCache buyerCallbackEventsCache;

    public Contact retrieveBuyerContactInfo(Long buyerId) {
        Buyer buyer = buyerDao.findById(buyerId);
        if (buyer == null) return null;

        return buyer.getContact();
    }

    public List<Contact> retrieveBuyerContactInfoList(List<Long> buyerIdList) {
        List<Contact> buyerContactList = new ArrayList<Contact>();
        for (Long buyerId : buyerIdList) {
            buyerContactList.add(retrieveBuyerContactInfo(buyerId));
        }
        return buyerContactList;
    }

    public Contact retrieveBuyerResourceContactInfo(Long buyerRsrcId) {
        BuyerResource buyerResource = buyerResourceDao.findById(buyerRsrcId);
        if (buyerResource == null) return null;

        return buyerResource.getContact();
    }

    @Transactional(readOnly = true)
    public Integer findBuyerResourceIdUsingContactId(Long buyerId, Integer contactId) {
        BuyerResource buyerResource = buyerResourceDao.findBuyerResourceIdUsingContactId(buyerId, contactId);
        if (buyerResource != null) {
            return buyerResource.getResourceId();
        }
        return null;
    }

    public List<Contact> retrieveBuyerResourceContactInfoList(List<Long> buyerRsrcIdList) {
        List<Contact> buyerContactList = new ArrayList<Contact>();
        for (Long buyerId : buyerRsrcIdList) {
            buyerContactList.add(retrieveBuyerResourceContactInfo(buyerId));
        }
        return buyerContactList;
    }

    @Transactional(readOnly = true)
    public List<BuyerReferenceType> getBuyerReferenceTypes(long buyerId) {
        return buyerDao.getBuyerReferenceTypes(buyerId);
    }

    @Transactional(readOnly = true)
	public Buyer retrieveBuyer(Long buyerId) {
        Buyer buyer = buyerDao.findById(buyerId);
        if(null == buyer) throw new MarketPlatformException("Buyer not found for buyer id " + buyerId);
        try {
            SerializationHelper.forceOrmLoadUsingJAXBSerialization(buyer);
        } catch (JAXBException e) {
            logger.error(e);
            throw new MarketPlatformException(e);
        }

        loadBuyerPrimaryLocation(buyer);

        return buyer;
    }

    private void loadBuyerPrimaryLocation(Buyer buyer) {
        Location loc = buyer.getLocationByPriLocnId();
        Location newLoc = new Location();
        newLoc.setZip(loc.getZip());

        LookupStates locState = loc.getLookupStates();
        LookupStates lookupState = new LookupStates();
        lookupState.setId(locState.getId());

        newLoc.setLookupStates(lookupState);
        buyer.setLocationByPriLocnId(newLoc);
    }

    @Transactional(readOnly = true)
    public List<SimpleBuyerFeature> retrieveAllSimpleBuyerFeatures() {
        return buyerDao.getAllSimpleBuyerFeatures();
    }
    
    @Transactional(readOnly = true)
    public List<BuyerHoldTime> retrieveAllBuyerHoldTime(){
    	return buyerDao.getAllBuyerHoldTime();
    }

    @Transactional(readOnly = true)
    public BigDecimal getTripChargeByBuyerAndSkillCategory(Long buyerId, Integer skillCategoryId){
    	return buyerDao.getTripCharge(buyerId, skillCategoryId);
    }

    public Integer retrieveBuyerDocumentIdByTitle(Long buyerId, String title) {
        return marketPlatformDocumentBO.retrieveDocumentIdByTitleAndOwnerID(title, buyerId.intValue(), Constants.DocumentTypes.BUYER);
    }

    @Transactional
    public List<String> insertServiceOrderBuyerDocuments(String serviceOrderId, Long buyerId, List<String> documentTitles) {
        List<String> errorMsgList = new ArrayList<String>();

        for (String documentTitle : documentTitles) {
            DocumentVO documentVO = null;
            try {
                documentVO = marketPlatformDocumentBO.retrieveDocumentByTitleAndOwnerID(documentTitle, buyerId.intValue(), Constants.DocumentTypes.BUYER);
            } catch (MarketPlatformException ignored) {}

            if (documentVO == null) {
                errorMsgList.add("Unable to retrieve so buyer document with title - " + documentTitle);
            } else {
                documentVO.setSoId(serviceOrderId);
                documentVO.setCompanyId(buyerId.intValue());
                try {
                    marketPlatformDocumentBO.insertServiceOrderDocument(documentVO);
                } catch (MarketPlatformException e) {
                    logger.error("Error happened while trying to save documents");
                    throw e;
                }
            }
        }
        return errorMsgList;
    }

    ////////////////////////////////////////////////////////////////////////////
    // SETTERS FOR SPRING INJECTION
    ////////////////////////////////////////////////////////////////////////////

    public void setBuyerDao(IBuyerDao buyerDao) {
        this.buyerDao = buyerDao;
    }

    public void setBuyerResourceDao(IBuyerResourceDao buyerResourceDao) {
        this.buyerResourceDao = buyerResourceDao;
    }

    public void setMarketPlatformDocumentBO(IMarketPlatformDocumentBO marketPlatformDocumentBO) {
        this.marketPlatformDocumentBO = marketPlatformDocumentBO;
    }

    
   

	public BuyerCallbackEventsCache getBuyerCallbackEventsCache() {
		return buyerCallbackEventsCache;
	}

	public void setBuyerCallbackEventsCache(
			BuyerCallbackEventsCache buyerCallbackEventsCache) {
		this.buyerCallbackEventsCache = buyerCallbackEventsCache;
	}

	@Transactional(readOnly = true)
	public BuyerResource getBuyerResource(Long buyerResourceId) {
		BuyerResource buyerResource = buyerResourceDao.findById(buyerResourceId);
        try {
            SerializationHelper.forceOrmLoadUsingJAXBSerialization(buyerResource);
        } catch (JAXBException e) {
            logger.error(e);
            throw new MarketPlatformException(e);
        }
		return buyerResource;
	}
	
	@Transactional(readOnly = true)
	public BuyerCallbackEventVO fetchBuyerCallbackEvent(String buyerId, String actionId){
		 BuyerCallbackEventVO buyerCallbackEventVO = new BuyerCallbackEventVO();
		  logger.info("Fetching the data from buyerCallbackEventsCache ");
	try{
		BuyerCallbackEvent buyerCallbackEvent=buyerCallbackEventsCache.fetchBuyerCallbackEvent( buyerId,  actionId);
		if (buyerCallbackEvent!=null){
			buyerCallbackEventVO.setActionId(buyerCallbackEvent.getActionId());
			buyerCallbackEventVO.setBuyerId(buyerCallbackEvent.getBuyerId());
			buyerCallbackEventVO.setEventId(buyerCallbackEvent.getEventId());
			buyerCallbackEventVO.setServiceId(buyerCallbackEvent.getServiceId());
		}
		
	 } catch (Exception e) {
            logger.error("FetchBuyerCallbackEvent"+e.getMessage());
            throw new MarketPlatformException(e);
        }
	
	return buyerCallbackEventVO;
	
	
}
	
//	public List<BuyerCallbackEventVO> getBuyerCallbackEventList(){
//		List<BuyerCallbackEvent> buyerCallbackEvents = new ArrayList<BuyerCallbackEvent>();
//		List<BuyerCallbackEventVO> buyerCallbackEventVOs = new ArrayList<BuyerCallbackEventVO>();
//		try{
//			buyerCallbackEvents=buyerCallbackEventBO.getBuyerCallbackEventList();
//			
//			for (BuyerCallbackEvent buyerCallbackEvent:buyerCallbackEvents){
//				BuyerCallbackEventVO buyerCallbackEventVO = new BuyerCallbackEventVO();
//				buyerCallbackEventVO.setActionId(buyerCallbackEvent.getActionId());
//				buyerCallbackEventVO.setBuyerId(buyerCallbackEvent.getBuyerId());
//				buyerCallbackEventVO.setEventId(buyerCallbackEvent.getEventId());
//				buyerCallbackEventVO.setServiceId(buyerCallbackEvent.getServiceId());
//				buyerCallbackEventVOs.add(buyerCallbackEventVO);
//			}
//		 } catch (Exception e) {
//	            logger.error(e.getMessage());
//	            throw new MarketPlatformException(e);
//	        }
//		
//		return buyerCallbackEventVOs;
//		
//		
//	}
}
