package com.servicelive.marketplatform.serviceinterface;

import com.newco.marketplace.dto.vo.DocumentVO;
import com.servicelive.marketplatform.common.exception.MarketPlatformException;

public interface IMarketPlatformDocumentBO {
    Integer retrieveDocumentIdByTitleAndOwnerID (String title, Integer ownerID, Integer documentOwnerType);
    DocumentVO retrieveDocumentByTitleAndOwnerID(String title, Integer ownerID, Integer documentOwnerType) throws MarketPlatformException;
    void insertServiceOrderDocument(DocumentVO serviceOrderDocument) throws MarketPlatformException;
}
