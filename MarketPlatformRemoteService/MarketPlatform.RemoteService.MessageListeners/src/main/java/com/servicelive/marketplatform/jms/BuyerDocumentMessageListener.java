package com.servicelive.marketplatform.jms;

import com.servicelive.marketplatform.common.vo.ServiceOrderBuyerDocumentsVO;
import com.servicelive.marketplatform.serviceinterface.IMarketPlatformBuyerBO;
import org.apache.log4j.Logger;

import javax.ejb.EJBException;
import javax.jms.JMSException;
import javax.jms.Message;
import java.util.List;

public class BuyerDocumentMessageListener extends AbstractMessageListener {
    private static final Logger logger = Logger.getLogger(BuyerDocumentMessageListener.class);
    IMarketPlatformBuyerBO marketPlatformBuyerBO;
    
    public void onMessage(Message message) {
        logger.debug("BuyerDocumentMessageListener received a message");
        ServiceOrderBuyerDocumentsVO buyerDocumentVO = extractBuyerDocumentVO(message);
        List<String> errors = marketPlatformBuyerBO.insertServiceOrderBuyerDocuments(
                buyerDocumentVO.getServiceOrderId(),
                buyerDocumentVO.getBuyerId(),
                buyerDocumentVO.getDocumentTitles());
        if(null != errors && errors.size() > 0)
            logger.warn("Some of the documents were not saved with the service order - " + errors);
        logger.debug("BuyerDocumentMessageListener completed buyer document save.");
    }

    private ServiceOrderBuyerDocumentsVO extractBuyerDocumentVO(Message message) throws EJBException {
        Object obj = extractObjectFromMessage(message);
        assertPayloadIsBuyerDocumentVO(obj);
        return (ServiceOrderBuyerDocumentsVO) obj;
    }

    private void assertPayloadIsBuyerDocumentVO(Object obj) {
        String errMsg = "Unable to extract buyer document info from ObjectMessage.";
        if ( ! (obj instanceof ServiceOrderBuyerDocumentsVO)) {
            logger.error(errMsg);
            throw new EJBException(errMsg);
        }
    }

    public void setMarketPlatformBuyerBO(IMarketPlatformBuyerBO marketPlatformBuyerBO) {
        this.marketPlatformBuyerBO = marketPlatformBuyerBO;
    }
}
