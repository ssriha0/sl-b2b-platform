package com.servicelive.marketplatform.service;

import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.document.IDocumentBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.servicelive.marketplatform.common.exception.MarketPlatformException;
import com.servicelive.marketplatform.serviceinterface.IMarketPlatformDocumentBO;
import com.servicelive.marketplatform.serviceinterface.IMarketPlatformDocumentServiceBO;

public class MarketPlatformDocumentBO implements IMarketPlatformDocumentBO, IMarketPlatformDocumentServiceBO {
    protected final Logger logger = Logger.getLogger(getClass());

    IDocumentBO documentBO;

	public Integer retrieveBuyerDocumentIdByTitleAndOwnerId(String title, Integer ownerId) {
		DocumentVO documentVO = retrieveDocumentByTitleAndOwnerID(title, ownerId, Constants.DocumentTypes.BUYER);
		if(documentVO != null) return documentVO.getDocumentId();
		return null;
	}
	
    public Integer retrieveDocumentIdByTitleAndOwnerID (String title, Integer ownerID, Integer documentOwnerType) {
        DocumentVO documentVO;
        try {
            documentVO = documentBO.retrieveDocumentByTitleAndEntityID(documentOwnerType, title, ownerID);
        } catch (BusinessServiceException e) {
            throw new MarketPlatformException(e);
        }
        return documentVO.getDocumentId();
    }

    public DocumentVO retrieveDocumentByTitleAndOwnerID(String title, Integer ownerID, Integer documentOwnerType) throws MarketPlatformException {
        DocumentVO documentVO;
        try {
            documentVO = documentBO.retrieveDocumentByTitleAndEntityID(documentOwnerType, title, ownerID);
        } catch (BusinessServiceException e) {
            logger.error(e);
            throw new MarketPlatformException(e);
        }
        return documentVO;
    }

    public void insertServiceOrderDocument(DocumentVO serviceOrderDocument) throws MarketPlatformException {
        logServiceOrderDocumentInsertion(serviceOrderDocument);
        try {
            ProcessResponse pr = documentBO.insertServiceOrderDocument(serviceOrderDocument);
            if(pr.isError() && pr.getCode() != OrderConstants.SO_DOC_UPLOAD_EXSITS) throw new MarketPlatformException(pr.getMessage());
        } catch (Exception e) {
            logger.error(e);
            throw new MarketPlatformException(e);
        }
    }

    private void logServiceOrderDocumentInsertion(DocumentVO serviceOrderDocument) {
        if (logger.isDebugEnabled()){
        StringBuilder sb = new StringBuilder("\nInserting service order document:");
        sb.append("\nsoID        : ").append(serviceOrderDocument.getSoId());
        sb.append("\ndocumentId  : ").append(serviceOrderDocument.getDocumentId());
        sb.append("\ncompanyId   : ").append(serviceOrderDocument.getCompanyId());
        sb.append("\ntitle       : ").append(serviceOrderDocument.getTitle());
        sb.append("\nvendorId    : ").append(serviceOrderDocument.getVendorId());
            logger.debug(sb.toString());
    }
    }

    ////////////////////////////////////////////////////////////////////////////
    // SETTERS FOR SPRING INJECTION
    ////////////////////////////////////////////////////////////////////////////

    public void setDocumentBO(IDocumentBO documentBO) {
        this.documentBO = documentBO;
    }

}
