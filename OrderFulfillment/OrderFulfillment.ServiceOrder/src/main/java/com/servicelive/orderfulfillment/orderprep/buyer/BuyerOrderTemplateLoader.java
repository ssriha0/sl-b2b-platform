package com.servicelive.orderfulfillment.orderprep.buyer;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

import com.servicelive.domain.so.BuyerOrderTemplate;
import com.servicelive.domain.so.BuyerOrderTemplatePart;
import com.servicelive.domain.so.BuyerOrderTemplateRecord;
import com.servicelive.domain.so.BuyerSOTemplateContact;
import com.servicelive.marketplatform.serviceinterface.IMarketPlatformBuyerBO;
import com.servicelive.orderfulfillment.dao.IOrderBuyerDao;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class BuyerOrderTemplateLoader {
    IOrderBuyerDao orderBuyerDao;
    IMarketPlatformBuyerBO marketPlatformBuyerBO;

    public BuyerTemplateMap getBuyerTemplateMap(long buyerId) {
        List<BuyerOrderTemplateRecord> templateRecords = orderBuyerDao.getBuyerTemplateRecords(buyerId);
        Map<String, BuyerOrderTemplate> buyerOrderTemplateMap = new TreeMap<String, BuyerOrderTemplate>();

        for (BuyerOrderTemplateRecord templateRecord : templateRecords) {
            BuyerOrderTemplate template = extractAndPrepBuyerOrderTemplate(templateRecord);
            buyerOrderTemplateMap.put(templateRecord.getTemplateName(), template);
        }

        return new BuyerTemplateMap(buyerOrderTemplateMap);
    }

    public BuyerOrderTemplate getBuyerOrderTemplate(Integer buyerTemplateId){
    	BuyerOrderTemplateRecord templateRecord = orderBuyerDao.getBuyerTemplateRecord(buyerTemplateId);
    	return extractAndPrepBuyerOrderTemplate(templateRecord);
    }
    
    private BuyerOrderTemplate extractAndPrepBuyerOrderTemplate(BuyerOrderTemplateRecord templateRecord){
        BuyerOrderTemplate template = extractBuyerOrderTemplate(templateRecord);
        setTemplateBuyerResource(templateRecord.getBuyerID(), template);
        setDocumentLogoId(templateRecord.getBuyerID(), template);
        
        template.setTemplateRecordId(templateRecord.getTemplateID());
        return template;
    }
    private BuyerOrderTemplate extractBuyerOrderTemplate(BuyerOrderTemplateRecord templateRecord) {
        XStream xstream = new XStream(new DomDriver());
        BuyerOrderTemplate buyerOrderTemplate;

        xstream.alias("buyerTemplate", BuyerOrderTemplate.class);
        xstream.alias("contact", BuyerSOTemplateContact.class);
        xstream.alias("altBuyerContact", BuyerSOTemplateContact.class);
        xstream.alias("part", BuyerOrderTemplatePart.class);
        buyerOrderTemplate = (BuyerOrderTemplate)xstream.fromXML(templateRecord.getTemplateXmlData());

        return buyerOrderTemplate;
    }

    private void setTemplateBuyerResource(Long buyerId, BuyerOrderTemplate template) {
        if (template.getAltBuyerContactId() != null && template.getAltBuyerContactId() > 0) {
            template.setBuyerResourceId(marketPlatformBuyerBO.findBuyerResourceIdUsingContactId(buyerId, template.getAltBuyerContactId()));
        }
    }

    private void setDocumentLogoId(Long buyerId, BuyerOrderTemplate template) {
        if (!StringUtils.isBlank(template.getDocumentLogo())) {
            template.setDocumentLogoId(marketPlatformBuyerBO.retrieveBuyerDocumentIdByTitle(buyerId, template.getDocumentLogo()));
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    // SETTERS FOR SPRING INJECTION
    ////////////////////////////////////////////////////////////////////////////

    public void setOrderBuyerDao(IOrderBuyerDao orderBuyerDao) {
        this.orderBuyerDao = orderBuyerDao;
    }

    public void setMarketPlatformBuyerBO(IMarketPlatformBuyerBO marketPlatformBuyerBO) {
        this.marketPlatformBuyerBO = marketPlatformBuyerBO;
    }
}
