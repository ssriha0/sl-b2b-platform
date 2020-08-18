package com.servicelive.orderfulfillment.orderprep.processor.hsr;

import com.servicelive.domain.so.BuyerOrderSku;
import com.servicelive.domain.so.BuyerOrderTemplate;
import com.servicelive.orderfulfillment.orderprep.buyer.IOrderBuyer;
import com.servicelive.orderfulfillment.orderprep.processor.common.CommonOrderCustomRefSkillNodeEnhancer;

public class HsrCustomRefEnhancer extends CommonOrderCustomRefSkillNodeEnhancer {
    @Override
    protected String getTemplateName(String sku, String specialtyCode, IOrderBuyer orderBuyer, String processId) {
        BuyerOrderSku buyerSku = orderBuyer.getBuyerSkuMap().getBuyerSku(sku);
        if (buyerSku != null) {
            Long buyerSOTemplateId = buyerSku.getTemplateId();
            BuyerOrderTemplate buyerOrderTemplate = orderBuyer.getTemplateMap().getTemplate(buyerSOTemplateId.intValue());
            return buyerOrderTemplate.getTitle();
        }
        return null;
    }
    
}
