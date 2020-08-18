/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 23-September-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.services.document;


import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.document.DocUploadRequest;
import com.newco.marketplace.api.beans.document.DocUploadResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;

import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.document.DocumentUploadMapper;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.document.IDocumentBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.webservices.base.response.ProcessResponse;

/**
 * This class is a service class for uploading a buyer document.
 * 
 * @author Infosys
 * @version 1.0
 */
public class DocumentUploadService extends BaseService{
	private Logger logger = Logger.getLogger(DocumentUploadService.class);
	
	private DocumentUploadMapper uploadMapper;
	private IDocumentBO documentBO;	
	
	public DocumentUploadService () {
		super (PublicAPIConstant.DOC_UPLOAD_REQUEST_XSD,
				PublicAPIConstant.DOC_UPLOAD_RESPONSE_XSD, 
				PublicAPIConstant.DOCRESPONSE_NAMESPACE, 
				PublicAPIConstant.DOC_RESOURCES_SCHEMAS,
				PublicAPIConstant.DOCRESPONSE_SCHEMALOCATION,	
				DocUploadRequest.class,
				DocUploadResponse.class);
	}
	/**
	 * This method is for uploading buyer document.
	 * 
	 * @param apiVO APIRequestVO	
	 * @return Object
	 */
	public IAPIResponse execute(APIRequestVO apiVO)  {
		logger.info("Entering DocumentUploadService.execute()");
	
		DocUploadResponse docUploadResponse = null;
		String filename = "";
		DocumentVO documentVO = null;			 
		DocUploadRequest docRequest = (DocUploadRequest)
			apiVO.getRequestFromPostPut();
		Integer buyerId = apiVO.getBuyerIdInteger();	
		SecurityContext securityContext = getSecurityContextForBuyerAdmin(
				(buyerId));					
		//map the request to DocumentVO
		documentVO = uploadMapper.mapDocument(docRequest.getFileName(),
					docRequest.getDescription(), docRequest.getBlobBytes()
					, securityContext);				
		if(null == documentVO){				
				logger.info("Document details not mapped correctly");
				Results results = Results.getError(
						ResultsCode.DOCUMENT_MAPPED_FAILED.getMessage(),
						ResultsCode.DOCUMENT_MAPPED_FAILED.getCode());
				docUploadResponse = new DocUploadResponse();
				docUploadResponse.setResults(results);
				docUploadResponse.setVersion(
						PublicAPIConstant.SORESPONSE_VERSION);
				docUploadResponse.setNamespace(
						PublicAPIConstant.DOCRESPONSE_NAMESPACE);
				docUploadResponse.setSchemaInstance(
						PublicAPIConstant.SCHEMA_INSTANCE);				
				return docUploadResponse;
		}
		logger.info("Mapping Completed. Now calling DocumentBO"
							+ "for actual File Upload");
		ProcessResponse processResponse = new ProcessResponse();
		try {
				processResponse = documentBO.insertBuyerDocument(
						documentVO);			
				// check for unique title and filename,if not rename it		
				if(Constants.BuyerAdmin.DOC_WITH_TITLE_EXISTS.equals(
						processResponse.getCode())){
					String newTitle = getUniqueName(documentVO.getTitle());
					documentVO.setTitle(newTitle);				
					processResponse = documentBO.insertBuyerDocument(documentVO);
					
				}else if(Constants.BuyerAdmin.DOC_WITH_FILENAME_EXISTS.equals(
						processResponse.getCode())){
					documentVO.setFileName(getNewFileName(
							documentVO.getFileName()));
					documentVO.setTitle(documentVO.getFileName());
					processResponse = documentBO.insertBuyerDocument(documentVO);								
				}
				// get filename passing doc id obtained from processResponse
				
				DocumentVO newDoc = new DocumentVO();
				if(null != processResponse.getObj()){
					Integer documentID = (Integer)processResponse.getObj();				
					newDoc =  documentBO.retrieveBuyerDocumentByDocumentId(
								documentID);
					if(null != newDoc){					
						filename = newDoc.getFileName();
					}
				}
				else{					
					Results results = Results.getError(
							ResultsCode.DOCUMENT_CREATED_FAILED.getMessage(),
							ResultsCode.DOCUMENT_CREATED_FAILED.getCode());
					docUploadResponse = new DocUploadResponse();
					docUploadResponse.setResults(results);
					docUploadResponse.setVersion(
							PublicAPIConstant.SORESPONSE_VERSION);
					docUploadResponse.setNamespace(
							PublicAPIConstant.DOCRESPONSE_NAMESPACE);
					docUploadResponse.setSchemaInstance(
							PublicAPIConstant.SCHEMA_INSTANCE);
					return docUploadResponse;
				}
			}catch (
				com.newco.marketplace.exception.core.BusinessServiceException 
				bse) {
				logger.error("DocumentUploadService.execute()-->" +
						"documentBO->retrieveBuyerDocumentByDocumentId()" +
						"-->Exception-->" + bse.getMessage(), bse);
				Results results = Results.getError(bse.getMessage(),
						ResultsCode.DOCUMENT_CREATED_FAILED.getCode());
				docUploadResponse = new DocUploadResponse();
				docUploadResponse.setResults(results);
			
			}catch(DataServiceException dse){
				logger.error("DocumentUploadService.execute()-->" +
						"documentBO-->insertBuyerDocument()-->Exception-->"
						+ dse.getMessage(), dse);
				Results results = Results.getError(dse.getMessage(),
						ResultsCode.DOCUMENT_CREATED_FAILED.getCode());
				docUploadResponse = new DocUploadResponse();
				docUploadResponse.setResults(results);
			} catch(Exception e){
				logger.error("DocumentUploadService.execute()-->" +
						"Exception-->"
						+ e.getMessage(), e);
				Results results = Results.getError(e.getMessage(),
						ResultsCode.DOCUMENT_CREATED_FAILED.getCode());
				docUploadResponse = new DocUploadResponse();
				docUploadResponse.setResults(results);
			} 
			docUploadResponse = uploadMapper.mapResponseDocument(filename);
		
		if (docUploadResponse == null) {
			logger.info("docUploadResponse came as null");
			Results results = Results.getError(
					ResultsCode.DOCUMENT_CREATED_FAILED.getMessage(),
					ResultsCode.DOCUMENT_CREATED_FAILED.getCode());
			docUploadResponse = new DocUploadResponse();
			docUploadResponse.setResults(results);
			docUploadResponse.setVersion(PublicAPIConstant.SORESPONSE_VERSION);
			docUploadResponse.setNamespace(
					PublicAPIConstant.DOCRESPONSE_NAMESPACE);
			docUploadResponse.setSchemaInstance(
					PublicAPIConstant.SCHEMA_INSTANCE);
			return docUploadResponse;			
		}
		logger.info("Leaving DocumentUploadService.execute()");
		return docUploadResponse;
	}
	
	/**
     * This method appends a random number to the given string
     * @param name
     * @return String
     */
	private String getUniqueName(String name){
		StringBuilder unique = new StringBuilder();
		int randomNo = (int)(Math.random()*1000);
		unique.append(name);
		unique.append(PublicAPIConstant.UNDERSCORE);
		unique.append(randomNo);
		return unique.toString();
	}
	/**
     * This method appends a random number to the given filename
     * @param name
     * @return String
     */
	private String getNewFileName(String oldFileName){
		
		int extStart = oldFileName.lastIndexOf(PublicAPIConstant.DOT);
		String fileExtn = oldFileName.substring(extStart+1);
		String fileName = oldFileName.substring(0, extStart);
		StringBuilder newFileName = new StringBuilder();
		newFileName.append(getUniqueName(fileName));
		newFileName.append(PublicAPIConstant.DOT);
		newFileName.append(fileExtn);
		return newFileName.toString();
	}	
	
	public void setUploadMapper(DocumentUploadMapper uploadMapper) {
		this.uploadMapper = uploadMapper;
	}

	public void setDocumentBO(IDocumentBO documentBO) {
		this.documentBO = documentBO;
	}	
}
