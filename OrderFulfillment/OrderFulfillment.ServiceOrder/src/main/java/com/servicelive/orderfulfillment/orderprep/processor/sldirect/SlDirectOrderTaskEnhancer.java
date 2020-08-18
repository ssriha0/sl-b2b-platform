package com.servicelive.orderfulfillment.orderprep.processor.sldirect;

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

/**
 * 
 * $Revision: 1.0 $ $Author: Infosys $ $Date: 2015/05/20
 * This class is the task enhancer for SL Direct buyer
 * 
 */
public class SlDirectOrderTaskEnhancer extends CommonOrderTaskEnhancer {

    @Override
    protected List<BuyerOrderSkuTask> getBuyerSkuTaskList(SOTask inputTask, 
    		IOrderBuyer orderBuyer, ValidationHolder validationHolder) {
    	
        BuyerSkuMap buyerSkuMap = null;
        String sku = null;
        BuyerOrderSku buyerOrderSku = null;
        
        if(null != orderBuyer){
        	buyerSkuMap = orderBuyer.getBuyerSkuMap();
        }
        
        if(null != inputTask){
        	sku = inputTask.getExternalSku();
        }
        
        if(!StringUtils.isEmpty(sku)){
        	//SKU is not empty
        	if(null != buyerSkuMap){
        		buyerOrderSku = buyerSkuMap.getBuyerSku(sku);
                validationUtil.addWarningsIfNull(buyerOrderSku, validationHolder, ProblemType.PrimarySkuNotConfigured);
        	}
        }else{
        	logger.info("SKU is empty");
        }
        
        return (buyerOrderSku!=null) ? buyerOrderSku.getBuyerSkuTaskList() : new ArrayList<BuyerOrderSkuTask>();
    }
}
