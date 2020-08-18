package com.servicelive.orderfulfillment.orderprep.processor.common;

import java.util.ArrayList;
import java.util.List;

import com.servicelive.domain.so.BuyerOrderSku;
import com.servicelive.domain.so.BuyerOrderSkuTask;
import com.servicelive.orderfulfillment.domain.SOTask;
import com.servicelive.orderfulfillment.domain.ValidationHolder;
import com.servicelive.orderfulfillment.domain.type.ProblemType;
import com.servicelive.orderfulfillment.orderprep.buyer.IOrderBuyer;
import com.servicelive.orderfulfillment.orderprep.buyersku.BuyerSkuMap;

public class TopLevelSKUTaskEnhancer extends CommonOrderTaskEnhancer {
    @Override
    protected List<BuyerOrderSkuTask> getBuyerSkuTaskList(SOTask inputTask, IOrderBuyer orderBuyer, ValidationHolder validationHolder) {
        BuyerSkuMap buyerSkuMap = orderBuyer.getBuyerSkuMap();
        BuyerOrderSku buyerOrderSku = buyerSkuMap.getBuyerSku(inputTask.getExternalSku());
        validationUtil.addWarningsIfNull(buyerOrderSku, validationHolder, ProblemType.PrimarySkuNotConfigured);
        return (buyerOrderSku!=null) ? buyerOrderSku.getBuyerSkuTaskList() : new ArrayList<BuyerOrderSkuTask>();
    }
}
