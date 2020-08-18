/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 23-September-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.services.document;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.document.MultiDocUploadRequest;
import com.newco.marketplace.api.beans.document.MultiDocUploadResponse;
import com.newco.marketplace.api.beans.document.RequestFile;
import com.newco.marketplace.api.beans.document.RequestFiles;
import com.newco.marketplace.api.beans.document.ResponseFile;
import com.newco.marketplace.api.beans.document.ResponseFiles;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.document.DocumentUploadMapper;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.document.IDocumentBO;
import com.newco.marketplace.business.iBusiness.downloadsignedcopy.IDownloadSignedCopyBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.utils.TimeUtils;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.service.ServiceConstants;
/**
 * This class is a service class for uploading a buyer document.
 * 
 * @author Infosys
 * @version 1.0
 */
public class BuyerDocumentUploadService extends BaseService{
	private static final String PUBLIC_API = "PublicAPI";

	private Logger logger = Logger.getLogger(BuyerDocumentUploadService.class);
	
	private static final String TIME_FORMAT = "MMM dd, yyyy h:mm a";
	public static final String POSTED_SERVICE_ORDER_STATUS = "110";
	public static final String PENDING_CANCEL_SERVICE_ORDER_STATUS = "165";
	public static final String DOCUMENT_TYPE = "docType";
	public static final String DOCUMENT_TYPE_ID = "documentTypeId";
	public static final String DOCUMENT_ID = "documentId";
	public static final String UPLOADED_TIME = "uploadedTime";
	public static final String DB_TIME_ZOME = "dbTimeZone";
	public static final String SO_TIME_ZOME = "soTimeZone";
	public static final String DEFAULT_TIME_ZOME = "CST6CDT";
	public static final String DOCUMENT_NAME = "fileName";
	public static final String MEDIA_TYPE = "mediaType";
	public static final String DOCUMENT_CATEGORY = "docCategory";
	public static final String DOCUMENT_DESC = "docDescription";
	public static final String FILE_NAME = "filename";
	public static final String NAME = "name";
	public static final String FILE = "file";
	public static final String PDF = ".pdf";

	
	private IDocumentBO documentBO;
	private IDownloadSignedCopyBO downloadsignedCopyBO;
	private IServiceOrderBO serviceOrderBO;
	ProcessResponse processResponse = new ProcessResponse();
	
	private DocumentUploadMapper uploadMapper;
	
	public BuyerDocumentUploadService () {
		super (PublicAPIConstant.MULTI_DOC_UPLOAD_REQUEST_XSD,
				PublicAPIConstant.MULTI_DOC_UPLOAD_RESPONSE_XSD, 
				PublicAPIConstant.DOCRESPONSE_NAMESPACE, 
				PublicAPIConstant.DOC_RESOURCES_SCHEMAS,
				PublicAPIConstant.DOCRESPONSE_SCHEMALOCATION,	
				MultiDocUploadRequest.class,
				MultiDocUploadResponse.class);
	}
	
	/**
	 * This method is for uploading buyer document.
	 * 
	 * @param apiVO APIRequestVO	
	 * @return Object
	 */
	public IAPIResponse execute(APIRequestVO apiVO)  {
		logger.info("Inside BuyerDocumentUploadService.execute()");
		
		MultiDocUploadResponse docUploadResponse = new MultiDocUploadResponse();
		docUploadResponse.setVersion(
				PublicAPIConstant.SORESPONSE_VERSION);
		docUploadResponse.setNamespace(
				PublicAPIConstant.DOCRESPONSE_NAMESPACE);
		docUploadResponse.setSchemaInstance(
				PublicAPIConstant.SCHEMA_INSTANCE);
		
		
		String filename = "";
		List<DocumentVO> documentVOList = null;	
		List<String> invalidDocumentList = new ArrayList<String>();

		MultiDocUploadRequest docRequest = (MultiDocUploadRequest)apiVO.getRequestFromPostPut();
		Integer buyerId = apiVO.getBuyerIdInteger();	
		SecurityContext securityContext = getSecurityContextForBuyerAdmin((buyerId));		
		String soId = apiVO.getSOId();
		
		try {
			// TODO Validate the buyer
			boolean isValidBuyer  = downloadsignedCopyBO.validateBuyer(buyerId);
			
			if(!isValidBuyer){
				docUploadResponse.setResults(Results.getError(
						com.newco.marketplace.api.common.ResultsCode.INVALID_BUYER_ID_NEW.getMessage(),
						com.newco.marketplace.api.common.ResultsCode.INVALID_BUYER_ID_NEW.getCode()));
				docUploadResponse.setSoId(soId);
				return docUploadResponse;
				
				
			}
			
			// TODO Validate the service order
			boolean isValidServiceOrder = downloadsignedCopyBO.validateServiceOrder(soId);;
			
			if(!isValidServiceOrder){
				// Error message for invalid service order
				docUploadResponse.setResults(Results.getError(
						com.newco.marketplace.api.common.ResultsCode.INVALID_SERVICE_ORDER.getMessage(),
						com.newco.marketplace.api.common.ResultsCode.INVALID_SERVICE_ORDER.getCode()));
				docUploadResponse.setSoId(soId);
				return docUploadResponse;
				
				
			}
			
			// TODO validate the buyer - service order combo
			
			boolean validServiceOrderBuyerAssoc = downloadsignedCopyBO.validateSoIdAssociation(soId, buyerId);
			
			if(!validServiceOrderBuyerAssoc){
				docUploadResponse.setResults(Results.getError(
						com.newco.marketplace.api.common.ResultsCode.SERVICE_ORDER_NOT_ASSOCIATED.getMessage(),
						com.newco.marketplace.api.common.ResultsCode.SERVICE_ORDER_NOT_ASSOCIATED.getCode()));
				docUploadResponse.setSoId(soId);
				return docUploadResponse;
				
			}
			
			
			int fileLimit=0;
			String noOfFiles="";		
			noOfFiles=documentBO.getConstantValueFromDB("buyer_document_upload_no_of_files");
			if(StringUtils.isNotBlank(noOfFiles)){
				fileLimit=Integer.parseInt(noOfFiles);
			}
			
			
			RequestFiles fileList=docRequest.getFiles();
			ResponseFiles responseFileList=new ResponseFiles();

			List<RequestFile> files=new ArrayList<RequestFile>(); 
			List<ResponseFile> responseFiles=new ArrayList<ResponseFile>(); 
            boolean documentUpload=false;
			files=	fileList.getFile();
			
			if(null!=files && files.size()>fileLimit){
				docUploadResponse.setResults(Results.getError(
						com.newco.marketplace.api.common.ResultsCode.FILE_LIMIT_EXCEEDED.getMessage(),
						com.newco.marketplace.api.common.ResultsCode.FILE_LIMIT_EXCEEDED.getCode()));
				docUploadResponse.setSoId(soId);
				return docUploadResponse;
			}
			
			
			if(null!=files && files.size()>0){
				
				for(RequestFile file:files){
					
				ResponseFile responseFile=new ResponseFile();
				DocumentVO documentVO=	uploadMapper.mapSODocument(file.getFileName(),
						file.getDescription(), file.getBlobBytes(), securityContext,soId);	
				
				if(null == documentVO){				
					logger.info("Document details not mapped correctly");
					/*docUploadResponse.setResults(Results.getError(
							com.newco.marketplace.api.common.ResultsCode.DOCUMENT_MAPPED_FAILED.getMessage(),
							com.newco.marketplace.api.common.ResultsCode.DOCUMENT_MAPPED_FAILED.getCode()));*/
					
					responseFile.setFileName(file.getFileName());
					responseFile.setMessage(com.newco.marketplace.api.common.ResultsCode.DOCUMENT_MAPPED_FAILED.getMessage());
					responseFiles.add(responseFile);
					invalidDocumentList.add(file.getFileName());
                     continue;
					
					//return docUploadResponse;
			}
				
				
				logger.info("Mapping Completed. Now calling DocumentBO"
						+ "for actual File Upload");
				documentVO.setPlatformIndicatior(PUBLIC_API);
				ProcessResponse processResponse = documentBO.insertServiceOrderDocument(documentVO);
				
				if (null != processResponse) {

					if (ServiceConstants.VALID_RC
							.equals(processResponse.getCode())) {
						
						getDocumentDetails(soId, file.getFileName(),responseFile);
					//	docUploadResponse.setResults(Results.getSuccess());
					//	docUploadResponse.setSoId(soId);
					//	return docUploadResponse;
						responseFile.setFileName(file.getFileName());
						responseFile.setMessage("Document uploaded sucessfully");
						responseFiles.add(responseFile);
						documentUpload=true;
					} 

					// if document upload is not allowed in the current
					// SO status
					else if (OrderConstants.SO_DOC_NOT_IN_ALLOWED_STATE_ERROR_RC
							.equals(processResponse.getCode())) {
					/*	docUploadResponse.setResults(Results.getError(
								com.newco.marketplace.api.common.ResultsCode.INVALID_SERVICE_ORDER_STATUS.getMessage(),
								com.newco.marketplace.api.common.ResultsCode.INVALID_SERVICE_ORDER_STATUS.getCode()));
						docUploadResponse.setSoId(soId);
						return docUploadResponse;*/
						
						responseFile.setFileName(file.getFileName());
						responseFile.setMessage(com.newco.marketplace.api.common.ResultsCode.INVALID_SERVICE_ORDER_STATUS.getMessage());
						responseFiles.add(responseFile);
						invalidDocumentList.add(file.getFileName());

					}

					// invalid file format
					else if (OrderConstants.SO_DOC_INVALID_FORMAT
							.equals(processResponse.getCode())
							|| OrderConstants.SO_DOC_INVALID_FORMAT_SEARS_BUYER
									.equals(processResponse.getCode())) {
					/*	docUploadResponse.setResults(Results.getError(
								ResultsCode.INVALID_FILE_FORMAT.getMessage(),
								ResultsCode.INVALID_FILE_FORMAT.getCode()));*/
						
						responseFile.setFileName(file.getFileName());
						responseFile.setMessage(ResultsCode.INVALID_FILE_FORMAT.getMessage());
						responseFiles.add(responseFile);
						invalidDocumentList.add(file.getFileName());

						
						//return docUploadResponse;
					} else if (OrderConstants.SO_DOC_UPLOAD_EXSITS
							.equals(processResponse.getCode())) {
						/*docUploadResponse.setResults(Results.getError(
								ResultsCode.DOC_EXISTS.getMessage(),
								ResultsCode.DOC_EXISTS.getCode()));*/
						
						responseFile.setFileName(file.getFileName());
						responseFile.setMessage(ResultsCode.DOC_EXISTS.getMessage());
						responseFiles.add(responseFile);
						invalidDocumentList.add(file.getFileName());


						
						
						/*docUploadResponse.setSoId(soId);
						return docUploadResponse;*/
					}
					//CC-1113 Changes --START
					else if (OrderConstants.DOC_UPLOAD_ERROR_RC
							.equals(processResponse.getCode())) { //check to prevent the doc upload error
						
						/*docUploadResponse.setResults(Results.getError(
								ResultsCode.DOC_UPLOAD_ERROR.getMessage(),
								ResultsCode.DOC_UPLOAD_ERROR.getCode()));*/
						
						
						
						responseFile.setFileName(file.getFileName());
						responseFile.setMessage(ResultsCode.DOC_UPLOAD_ERROR.getMessage());
						responseFiles.add(responseFile);
						invalidDocumentList.add(file.getFileName());

						/*docUploadResponse.setSoId(soId);
						return docUploadResponse;*/
					}
					else if (OrderConstants.DOC_PROCESSING_ERROR_RC
							.equals(processResponse.getCode())) { //check to prevent the doc processing error
						
						/*docUploadResponse.setResults(Results.getError(
								ResultsCode.DOC_PROCESSING_ERROR.getMessage(),
								ResultsCode.DOC_PROCESSING_ERROR.getCode()));*/
						
						
						responseFile.setFileName(file.getFileName());
						responseFile.setMessage(ResultsCode.DOC_PROCESSING_ERROR.getMessage());
						responseFiles.add(responseFile);
						invalidDocumentList.add(file.getFileName());

						
						/*docUploadResponse.setSoId(soId);
						return docUploadResponse;*/
					}
					else if (OrderConstants.DOC_USER_AUTH_ERROR_RC
							.equals(processResponse.getCode())) { //check to prevent the doc auth error
						
						
						/*docUploadResponse.setResults(Results.getError(
								ResultsCode.DOC_USER_AUTH_ERROR.getMessage(),
								ResultsCode.DOC_USER_AUTH_ERROR.getCode()));*/
						
						
						responseFile.setFileName(file.getFileName());
						responseFile.setMessage(ResultsCode.DOC_USER_AUTH_ERROR.getMessage());
						responseFiles.add(responseFile);
						invalidDocumentList.add(file.getFileName());

						
						
						/*docUploadResponse.setSoId(soId);
						return docUploadResponse;*/
					}
					else if (OrderConstants.SO_DOC_SIZE_EXCEEDED_RC
							.equals(processResponse.getCode())) { //check to prevent the doc size exceeded error
						/*docUploadResponse.setResults(Results.getError(
								ResultsCode.SO_DOC_SIZE_EXCEEDED.getMessage(),
								ResultsCode.SO_DOC_SIZE_EXCEEDED.getCode()));*/
						
						responseFile.setFileName(file.getFileName());
						responseFile.setMessage(ResultsCode.SO_DOC_SIZE_EXCEEDED.getMessage());
						responseFiles.add(responseFile);
						invalidDocumentList.add(file.getFileName());

						/*docUploadResponse.setSoId(soId);
						return docUploadResponse;*/
					}
				}
				
				}
				docUploadResponse.setSoId(soId);
				responseFileList.setFile(responseFiles);
				docUploadResponse.setFiles(responseFileList);
			}			

			Results results=new Results();
			if (!invalidDocumentList.isEmpty()) {
				logger.info("Mapping the Invalid Documents with error List");
				String errorMessage = CommonUtility
						.getMessage(PublicAPIConstant.DOCUMENT_ADD_ERROR_CODE);
				int length=0;
				for (String fileName : invalidDocumentList) {
					length=length+1;
					if(length==invalidDocumentList.size()){
					errorMessage = errorMessage + fileName;
					}
					else{
						errorMessage = errorMessage + fileName + "  ::  ";
	
					}
				}

				results = Results.getError(errorMessage,
						ResultsCode.FAILURE.getCode());	
			} else {
				results = Results.getSuccess(
						com.newco.marketplace.api.common.ResultsCode.DOCUMENT_ADDED_TO_SO.getMessage());	
			}	
			docUploadResponse.setResults(results);
			
		}catch (Exception e) {
			logger.error("Exception in ProviderUploadDocumentService.execute()"
					+ e.getMessage());
			docUploadResponse.setResults(Results.getError(
					com.newco.marketplace.api.common.ResultsCode.INTERNAL_SERVER_ERROR.getMessage(),
					com.newco.marketplace.api.common.ResultsCode.INTERNAL_SERVER_ERROR.getCode()));
			docUploadResponse.setSoId(soId);
			e.printStackTrace();
		}
		return docUploadResponse;
	}
		
	
	private void getDocumentDetails(String soId, String documentName,ResponseFile responseFile) {

		HashMap<String, Object> docDetails = new HashMap<String, Object>();
		try {
			
			// TODO Get document details
			docDetails = serviceOrderBO.getDocumentDetails(soId,documentName);
			
			String docId = docDetails.get(DOCUMENT_ID).toString();
			String dbTimeZone = (docDetails.get(DB_TIME_ZOME) != null) ? docDetails
					.get(DB_TIME_ZOME).toString() : DEFAULT_TIME_ZOME;
			String soTimeZone = (docDetails.get(SO_TIME_ZOME) != null) ? docDetails
					.get(SO_TIME_ZOME).toString() : DEFAULT_TIME_ZOME;
			Date uploadedDate = (Date) docDetails.get(UPLOADED_TIME);
			Timestamp uploadedTimeStamp = new Timestamp(uploadedDate.getTime());

			// convert DB date to GMT
			Timestamp gmtTimeStamp = TimeUtils.convertToGMT(uploadedTimeStamp,
					dbTimeZone);

			// convert GMT date to service location time zone
			SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT);
			String uploadedDateString = TimeUtils.convertGMTtoTimezone(gmtTimeStamp, soTimeZone, sdf);
			if(null!=responseFile){
				responseFile.setUploadedTime(uploadedDateString);
				responseFile.setDocumentId(docId);
				responseFile.setFileName(documentName);

			}
		} catch (Exception e) {
			logger.info("Exception in ProviderUploadDocumentService.getDocumentDetails() "
					+ e.getMessage());
			e.printStackTrace();
		}

	}
	
	
	public void setProcessResponse(ProcessResponse processResponse) {
		this.processResponse = processResponse;
	}

	public IDocumentBO getDocumentBO() {
		return documentBO;
	}

	public void setDocumentBO(IDocumentBO documentBO) {
		this.documentBO = documentBO;
	}
	
	public IDownloadSignedCopyBO getDownloadsignedCopyBO() {
		return downloadsignedCopyBO;
	}

	public void setDownloadsignedCopyBO(
			IDownloadSignedCopyBO downloadsignedCopyBO) {
		this.downloadsignedCopyBO = downloadsignedCopyBO;
	}	
	
	public IServiceOrderBO getServiceOrderBO() {
		return serviceOrderBO;
	}
	
	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}
	
	public void setUploadMapper(DocumentUploadMapper uploadMapper) {
		this.uploadMapper = uploadMapper;
	}
}
