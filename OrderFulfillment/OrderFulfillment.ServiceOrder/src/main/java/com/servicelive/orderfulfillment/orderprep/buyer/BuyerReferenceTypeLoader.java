package com.servicelive.orderfulfillment.orderprep.buyer;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.servicelive.domain.so.BuyerReferenceType;
import com.servicelive.marketplatform.serviceinterface.IMarketPlatformBuyerBO;

public class BuyerReferenceTypeLoader implements IBuyerReferenceTypeLoader {
    IMarketPlatformBuyerBO marketPlatformBuyerBO;

    public Map<String, Integer> getBuyerReferenceNameToIdMap(long buyerId) {
        Map<String, Integer> refMap = new TreeMap<String, Integer>();
        List<BuyerReferenceType> buyerRefTypes = marketPlatformBuyerBO.getBuyerReferenceTypes(buyerId);

        if (buyerRefTypes != null) {
            for (BuyerReferenceType buyerReferenceType : buyerRefTypes) {
                refMap.put(buyerReferenceType.getRefType().toUpperCase(), buyerReferenceType.getBuyerRefTypeId());
            }
        }

        return refMap;
    }

    ////////////////////////////////////////////////////////////////////////////
    // SETTERS FOR SPRING INJECTION
    ////////////////////////////////////////////////////////////////////////////

    public void setMarketPlatformBuyerBO(IMarketPlatformBuyerBO marketPlatformBuyerBO) {
        this.marketPlatformBuyerBO = marketPlatformBuyerBO;
    }
}
