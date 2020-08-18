package com.servicelive.orderfulfillment.orderprep.processor.att;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.servicelive.domain.so.BuyerOrderSku;
import com.servicelive.domain.so.BuyerOrderSkuTask;
import com.servicelive.orderfulfillment.domain.SOTask;
import com.servicelive.orderfulfillment.domain.ValidationHolder;
import com.servicelive.orderfulfillment.domain.type.ProblemType;
import com.servicelive.orderfulfillment.orderprep.buyer.IOrderBuyer;
import com.servicelive.orderfulfillment.orderprep.buyersku.BuyerSkuMap;
import com.servicelive.orderfulfillment.orderprep.processor.common.CommonOrderTaskEnhancer;

public class AttOrderTaskEnhancer extends CommonOrderTaskEnhancer {

    @Override
    protected List<BuyerOrderSkuTask> getBuyerSkuTaskList(SOTask inputTask, IOrderBuyer orderBuyer, ValidationHolder validationHolder) {
        BuyerSkuMap buyerSkuMap = orderBuyer.getBuyerSkuMap();
        String sku = null;
        sku = inputTask.getExternalSku();
        BuyerOrderSku buyerOrderSku = null;
        if(!StringUtils.isEmpty(sku)){
        	logger.info("SKU is not empty");
        	buyerOrderSku = buyerSkuMap.getBuyerSku(inputTask.getExternalSku());
            validationUtil.addWarningsIfNull(buyerOrderSku, validationHolder, ProblemType.PrimarySkuNotConfigured);
        }else{
        	logger.info("SKU is empty");
        }
        return (buyerOrderSku!=null) ? buyerOrderSku.getBuyerSkuTaskList() : new ArrayList<BuyerOrderSkuTask>();
    }
}
