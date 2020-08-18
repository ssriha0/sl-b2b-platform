/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 23-September-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.utils.mappers.document;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.ErrorResult;
import com.newco.marketplace.api.beans.Result;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.document.DocUploadResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.sears.os.service.ServiceConstants;

/**
 * This class would act as a Mapper class for Mapping DocUploadRequest Object to
 * DocumentVO Object.
 * 
 * @author Infosys
 * @version 1.0
 */
public class DocumentUploadMapper {
	private Logger logger = Logger.getLogger(DocumentUploadMapper.class);
	
	/**
	 * This method is for mapping Document Details.
	 * 
	 * @param fileName String
	 * @param title String
	 * @param blobBytes byte[]
	 * @param securityContext SecurityContext
	 * @return documentVO
	 */
	public DocumentVO mapDocument(String fileName, String description, 
			byte[] blobBytes, SecurityContext securityContext) {
		logger.info("Entering DocumentUploadMapper.mapDocument()");
		DocumentVO documentVO = new DocumentVO();		
		documentVO.setBlobBytes(blobBytes);
		documentVO.setTitle(fileName);
		documentVO.setDescription(description);
		documentVO.setFileName(fileName);		
		Long size = new Long(blobBytes.length);
		
		logger.info("size::"+size);
		
		documentVO.setDocSize(size);
		documentVO.setBuyerId(securityContext.getCompanyId());
		documentVO.setRoleId(securityContext.getRoleId());
		documentVO.setDocCategoryId(Constants.BuyerAdmin.DOC_CATEGORY_ID);
		documentVO.setEntityId(securityContext.getVendBuyerResId());
		documentVO.setCompanyId(securityContext.getCompanyId());
		logger.info("Leaving DocumentUploadMapper.mapDocument()");
		return documentVO;
	}
	/**
	 * This method is for mapping Response for Document Details.
	 * 
	 * @param fileName String	 
	 * @return DocUploadResponse
	 */
	public DocUploadResponse mapResponseDocument(String fileName){
		logger.info("Entering DocumentUploadMapper.mapResponseDocument()");
		Results results = new Results();		
		Result result = new Result();
		ErrorResult errorResult = new ErrorResult();		
		DocUploadResponse docUploadResponse = new DocUploadResponse();
		if(StringUtils.isNotEmpty(fileName)){
			docUploadResponse.setFileName(fileName);			
			results = Results.getSuccess(CommonUtility
					.getMessage(PublicAPIConstant.DOCUMENT_CREATED_RESULT_CODE));
		}
		else {
			result.setMessage(CommonUtility
					.getMessage(
							PublicAPIConstant.DOCUMENT_NOTCREATED_ERROR_CODE));
			errorResult.setCode(ServiceConstants.SYSTEM_ERROR_RC);
			errorResult.setMessage("");
			results = Results.getError(
					ResultsCode.DOCUMENT_CREATED_FAILED.getMessage(),
					ResultsCode.DOCUMENT_CREATED_FAILED.getCode());
		}		
		docUploadResponse.setResults(results);
		docUploadResponse.setVersion(PublicAPIConstant.SORESPONSE_VERSION);
		docUploadResponse
				.setSchemaLocation(
						PublicAPIConstant.DOCRESPONSE_SCHEMALOCATION);
		docUploadResponse.setNamespace(PublicAPIConstant.DOCRESPONSE_NAMESPACE);
		docUploadResponse.setSchemaInstance(PublicAPIConstant.SCHEMA_INSTANCE);
		logger.info("Leaving DocumentUploadMapper.mapResponseDocument()");
		return docUploadResponse;
	}
	
	
	public DocumentVO mapSODocument(String fileName, String description, 
			byte[] blobBytes, SecurityContext securityContext,String soId) {
		logger.info("Entering DocumentUploadMapper.mapDocument()");
		DocumentVO documentVO = new DocumentVO();		
		documentVO.setBlobBytes(blobBytes);
		documentVO.setTitle(fileName);
		documentVO.setSoId(soId);
		documentVO.setDescription(description);
		documentVO.setFileName(fileName);		
		Long size = new Long(blobBytes.length);
		logger.info("size::"+size);
		
		documentVO.setDocSize(size);
		documentVO.setBuyerId(securityContext.getCompanyId());
		documentVO.setRoleId(securityContext.getRoleId());
		documentVO.setDocCategoryId(Constants.BuyerAdmin.DOC_CATEGORY_ID);
		documentVO.setEntityId(securityContext.getVendBuyerResId());
		documentVO.setCompanyId(securityContext.getCompanyId());
		logger.info("Leaving DocumentUploadMapper.mapDocument()");
		return documentVO;		
	}
	

}
