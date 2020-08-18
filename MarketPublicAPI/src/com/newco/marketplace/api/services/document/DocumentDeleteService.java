/*
 *	Date       		Project    	Author      Version
 * ----------- 	   ---------   -----------  ---------
 * 05-October-2009  KMSTRSUP   Infosys		1.0
 * 
 * 
 */
package com.newco.marketplace.api.services.document;

import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.addSODoc.AddSODocResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.document.DocumentDeleteMapper;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.document.IDocumentBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;

/**
 * This class is a service class for deleting the Documents from a buyer.
 * 
 * @author Infosys
 * @version 1.0
 */
public class DocumentDeleteService extends BaseService {
	private Logger logger = Logger.getLogger(DocumentDeleteService.class);
	private IDocumentBO documentBO;
	private DocumentDeleteMapper documentDeleteMapper;

	/**
	 * Constructor
	 */

	public DocumentDeleteService() {
		super(null, PublicAPIConstant.DOCUMENT_RESPONSE_XSD,
				PublicAPIConstant.DOC_DELETE_RESPONSE_NAMESPACE,
				PublicAPIConstant.DOCUMENT_SCHEMAS_PATH,
				PublicAPIConstant.SORESPONSE_SCHEMALOCATION, null,
				AddSODocResponse.class);
	}

	/**
	 * This method is for deleting buyer documents
	 * 
	 * @param apiVO
	 *            APIRequestVO
	 * @return AddSODocResponse
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	public IAPIResponse execute(APIRequestVO apiVO) {
		logger.info("Entering execute() method of DocumentDeleteService");
		boolean resultString = true;
		AddSODocResponse deleteDocResponse= new AddSODocResponse();		
		deleteDocResponse.setNamespace(
				PublicAPIConstant.DOC_DELETE_RESPONSE_NAMESPACE);
		deleteDocResponse.setSchemaInstance(PublicAPIConstant.SCHEMA_INSTANCE);
		deleteDocResponse.setVersion(PublicAPIConstant.SORESPONSE_VERSION);
		Map<String, String> requestMap = apiVO.getRequestFromGetDelete();
		Integer buyerId = apiVO.getBuyerIdInteger();
		String fileName = (String)apiVO.getProperty(APIRequestVO.FILENAME);
			// Populating the security context with the buyer Resource ID
		SecurityContext securityContext = null;
		Results results = null;
		
		securityContext = getSecurityContextForBuyerAdmin(buyerId);
		// Getting the documentId with File Name and Entity Id
		try {
				DocumentVO documentVO = documentBO
						.retrieveDocumentByFileNameAndEntityID(
								Constants.DocumentTypes.BUYER, fileName,
								securityContext.getCompanyId());
				if (null != documentVO) {
					ProcessResponse response = documentBO
							.deleteBuyerDocument(new Integer(documentVO
									.getDocumentId()),securityContext.getCompanyId(),null);
					if (null != response && !response.isSuccess()) {
						results = Results
							.getError(
									ResultsCode.
									DOCUMENT_DELETE_INPUT_ERROR.getMessage(),
									ResultsCode.
									DOCUMENT_DELETE_INPUT_ERROR.getCode());

						deleteDocResponse.setResults(results);
						return deleteDocResponse;
					} else
						resultString = true;

				} else {
					results = Results.getError(
							ResultsCode.DOCUMENT_MAPPED_FAILED.getMessage(),
							ResultsCode.DOCUMENT_MAPPED_FAILED.getCode());
					deleteDocResponse.setResults(results);
					return deleteDocResponse;
				}
				} catch (Exception ex) {
					logger.error("DocumentDeleteService-->execute()-->" +
							" Exception-->"
							+ ex.getMessage(), ex);
					results = Results.getError(ex.getMessage(),
							ResultsCode.GENERIC_ERROR.getCode());
					deleteDocResponse.setResults(results);
				}
		deleteDocResponse = documentDeleteMapper.mapDeleteDocResponse(
				resultString);						
		
		logger.info("Leaving execute() method of DocumentDeleteService");
		return deleteDocResponse;
	}

	public void setDocumentBO(IDocumentBO documentBO) {
		this.documentBO = documentBO;
	}

	public void setDocumentDeleteMapper(
			DocumentDeleteMapper documentDeleteMapper) {
		this.documentDeleteMapper = documentDeleteMapper;
	}

}
