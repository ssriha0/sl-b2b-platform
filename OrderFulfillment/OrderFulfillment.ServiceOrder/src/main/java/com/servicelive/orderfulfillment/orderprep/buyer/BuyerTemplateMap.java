package com.servicelive.orderfulfillment.orderprep.buyer;

import java.util.Map;
import java.util.TreeMap;

import com.servicelive.domain.so.BuyerOrderTemplate;

public class BuyerTemplateMap {
    final Map<String, BuyerOrderTemplate> templateNameToTemplateMap;
    final Map<Integer, BuyerOrderTemplate> orderBuyerTemplateRecIdToNameMap = new TreeMap<Integer, BuyerOrderTemplate>();

    public BuyerTemplateMap(Map<String, BuyerOrderTemplate> templateNameToTemplateMap) {
        this.templateNameToTemplateMap = templateNameToTemplateMap;
        populateOrderBuyerTemplateRecIdToNameMap();
    }

    private void populateOrderBuyerTemplateRecIdToNameMap() {
        for (BuyerOrderTemplate buyerOrderTemplate : templateNameToTemplateMap.values()) {
            orderBuyerTemplateRecIdToNameMap.put(buyerOrderTemplate.getTemplateRecordId(), buyerOrderTemplate);
        }
    }

    public BuyerOrderTemplate getTemplate(String templateName) {
        return templateNameToTemplateMap.get(templateName);
    }

    public BuyerOrderTemplate getTemplate(Integer templateId) {
        return orderBuyerTemplateRecIdToNameMap.get(templateId);
    }
}
