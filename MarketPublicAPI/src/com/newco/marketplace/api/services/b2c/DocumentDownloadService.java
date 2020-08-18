/**
 * 
 */
package com.newco.marketplace.api.services.b2c;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.Namespace;
import com.newco.marketplace.api.beans.b2c.DownloadErrorResult;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.business.iBusiness.b2c.IB2CGenericBO;

/**
 * @author Infosys
 * 
 */
@Namespace("http://www.servicelive.com/namespaces/downloadDocument")
public class DocumentDownloadService{

	private static Logger logger = Logger.getLogger(DocumentDownloadService.class);
	
	private B2CRequestValidator b2CRequestValidator;
	private IB2CGenericBO b2CGenericBO;
	private B2CRequestResponseMapper b2CRequestResponseMapper;
	
	public DocumentDownloadService() {
		super();
	}

	public DownloadErrorResult execute(APIRequestVO apiVO) {

		String providerId=apiVO.getProviderId();
		String documentId=apiVO.getDocumentId();
	    String filePath="";
	    DownloadErrorResult validateResult = new DownloadErrorResult();
	    
	    //validateResult = validateParams(soId, providerId, documentId,filePath);
	    
		filePath=validateResult.getFilePath();
		if(null!=validateResult && StringUtils.isNotBlank(filePath)){
			DownloadErrorResult result=new DownloadErrorResult();
	    	result.setCode("0000");
			result.setMessage("Document downloaded successfully");
			result.setFilePath(filePath);
			return result;
		}else{
			DownloadErrorResult result=new DownloadErrorResult();
	    	result.setCode(validateResult.getCode());
			result.setMessage(validateResult.getMessage());
			result.setFilePath(null);
			return result;
		  }
	}

	public B2CRequestValidator getB2CRequestValidator() {
		return b2CRequestValidator;
	}

	public void setB2CRequestValidator(B2CRequestValidator b2cRequestValidator) {
		b2CRequestValidator = b2cRequestValidator;
	}

	public IB2CGenericBO getB2CGenericBO() {
		return b2CGenericBO;
	}

	public void setB2CGenericBO(IB2CGenericBO b2cGenericBO) {
		b2CGenericBO = b2cGenericBO;
	}

	public B2CRequestResponseMapper getB2CRequestResponseMapper() {
		return b2CRequestResponseMapper;
	}

	public void setB2CRequestResponseMapper(
			B2CRequestResponseMapper b2cRequestResponseMapper) {
		b2CRequestResponseMapper = b2cRequestResponseMapper;
	}

}
