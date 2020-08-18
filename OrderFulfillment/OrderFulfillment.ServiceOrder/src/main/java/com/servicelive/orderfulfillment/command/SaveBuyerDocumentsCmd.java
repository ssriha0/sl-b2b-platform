package com.servicelive.orderfulfillment.command;

import java.util.List;
import java.util.Map;

import com.servicelive.domain.so.BuyerOrderTemplate;
import com.servicelive.marketplatform.serviceinterface.IMarketPlatformBuyerBO;
import com.servicelive.orderfulfillment.domain.SOCustomReference;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.orderprep.buyer.IOrderBuyer;
import com.servicelive.orderfulfillment.orderprep.buyer.OrderBuyerCollection;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;

public class SaveBuyerDocumentsCmd extends SOCommand {
    OrderBuyerCollection orderBuyerCollection;
    IMarketPlatformBuyerBO marketPlatformBuyerBO;

    @SuppressWarnings("unchecked")
	@Override
    public void execute(Map<String, Object> processVariables) {
        ServiceOrder serviceOrder = getServiceOrder(processVariables);
        //if document titles are passed in create those document titles
        List<String> docTitles = (List<String>) processVariables.get(OrderfulfillmentConstants.PVKEY_DOCUMENT_TITLES);

        if (docTitles == null || (docTitles!=null && docTitles.size()==0)) {
	        //else check if orderBuyer exists for the buyer (contains buyer template with document titles)
            IOrderBuyer orderBuyer = orderBuyerCollection.getOrderBuyer(serviceOrder.getBuyerId());
            if (orderBuyer!=null) {
                String soTemplateName = serviceOrder.getCustomRefValue(SOCustomReference.CREF_TEMPLATE_NAME);
                if(null != soTemplateName){
                    BuyerOrderTemplate buyerOrderTemplate = orderBuyer.getTemplateMap().getTemplate(soTemplateName);
                    docTitles = buyerOrderTemplate.getDocumentTitles();
                }
            }
        }

        if (docTitles!=null && docTitles.size()>0) {
            marketPlatformBuyerBO.insertServiceOrderBuyerDocuments(serviceOrder.getSoId(), serviceOrder.getBuyerId(), docTitles);
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    // SETTERS FOR SPRING INJECTION
    ////////////////////////////////////////////////////////////////////////////

    public void setOrderBuyerCollection(OrderBuyerCollection orderBuyerCollection) {
        this.orderBuyerCollection = orderBuyerCollection;
    }

    public void setMarketPlatformBuyerBO(IMarketPlatformBuyerBO marketPlatformBuyerBO) {
        this.marketPlatformBuyerBO = marketPlatformBuyerBO;
    }
}
