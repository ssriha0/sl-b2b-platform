package com.servicelive.orderfulfillment.orderprep.buyer;

import java.util.Map;

public interface IBuyerReferenceTypeLoader {
    Map<String, Integer> getBuyerReferenceNameToIdMap(long buyerId);
}
