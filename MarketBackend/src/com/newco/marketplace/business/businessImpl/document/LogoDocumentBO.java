package com.newco.marketplace.business.businessImpl.document;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.document.ILogoDocumentBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.exception.core.BusinessServiceException;

public class LogoDocumentBO implements ILogoDocumentBO{
	private static final Logger logger = Logger.getLogger(LogoDocumentBO.class);
	private DocumentBO documentBO;
	
	/**
	 * if no logo exists , returns Servicelive logo as default
	 */
	public DocumentVO getLogoForBuyer(Integer buyerId)
			throws BusinessServiceException {
		Integer logoDocId = null;
		DocumentVO logoDoc = null;
		logoDocId = documentBO.retrieveLogoForBuyer(buyerId, Integer.valueOf(Constants.DocumentTypes.CATEGORY.LOGO));
		if(logoDocId != null){
			logoDoc = documentBO.retrieveBuyerDocumentByDocumentId(logoDocId);
			if(logoDoc.getBlobBytes() == null) {
				logoDoc = getServiceLiveLogo();
			}
		}else{
			logoDoc = getServiceLiveLogo();
		}
		return logoDoc;
	}

	public DocumentVO getServiceLiveLogo() throws BusinessServiceException {
		String fileName = "resources/images/service_live_logo.gif"; 
		URL resource = this.getClass().getClassLoader().getResource(fileName);
		DocumentVO logoDoc = new DocumentVO();
		
		try {
			URLConnection urlConnection = resource.openConnection();
			
			InputStream is = urlConnection.getInputStream();
			byte[] logoBytes = new byte[urlConnection.getContentLength()];
			is.read(logoBytes);
			logoDoc.setBlobBytes(logoBytes);

			String fileNameShort = org.springframework.util.StringUtils.getFilename(fileName);
			logoDoc.setFormat(urlConnection.getContentType());
			logoDoc.setFileName(fileNameShort);
		} catch (IOException e) {
			logger.error("Error occurred in  getServiceLiveLogo() due to "+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		
		return logoDoc;
		
	}

	public DocumentBO getDocumentBO() {
		return documentBO;
	}

	public void setDocumentBO(DocumentBO documentBO) {
		this.documentBO = documentBO;
	}

}
