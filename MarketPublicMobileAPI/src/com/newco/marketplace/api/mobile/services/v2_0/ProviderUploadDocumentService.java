/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 04-Apr-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.mobile.services.v2_0;

import java.io.InputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.activation.DataHandler;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.soDetails.vo.SoDetailsVO;
import com.newco.marketplace.api.mobile.beans.uploadDocument.ProviderDocument;
import com.newco.marketplace.api.mobile.beans.uploadDocument.ProviderUploadDocumentResponse;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.constants.PublicMobileAPIConstant;
import com.newco.marketplace.api.mobile.service.BaseService;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.document.IDocumentBO;
import com.newco.marketplace.business.iBusiness.mobile.IAuthenticateUserBO;
import com.newco.marketplace.business.iBusiness.mobile.IMobileGenericBO;
import com.newco.marketplace.business.iBusiness.mobile.IMobileSOActionsBO;
import com.newco.marketplace.business.iBusiness.mobile.IMobileSOManagementBO;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.util.EsapiUtility;
import com.newco.marketplace.utils.TimeUtils;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.service.ServiceConstants;

/**
 * This class would act as a Upload document service class
 * 
 * @author Infosys
 * @version 1.0
 */
public class ProviderUploadDocumentService {
	private Logger logger = Logger
			.getLogger(ProviderUploadDocumentService.class);

	private IMobileSOManagementBO mobileSOManagementBO;
	private IMobileSOActionsBO mobileSOActionsBO;
	private IDocumentBO documentBO;
	ProcessResponse processResponse = new ProcessResponse();
	private IAuthenticateUserBO authenticateUserBO;
	private IMobileGenericBO mobileGenericBO;
	
	public IAuthenticateUserBO getAuthenticateUserBO() {
		return authenticateUserBO;
	}

	public void setAuthenticateUserBO(IAuthenticateUserBO authenticateUserBO) {
		this.authenticateUserBO = authenticateUserBO;
	}

	public IMobileGenericBO getMobileGenericBO() {
		return mobileGenericBO;
	}

	public void setMobileGenericBO(IMobileGenericBO mobileGenericBO) {
		this.mobileGenericBO = mobileGenericBO;
	}

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
	public static final String TRIP_NO = "tripNo";
	public static final String TRIP_STATUS = "tripStatus";

	/**
	 * Implement your logic here.
	 */
	public ProviderUploadDocumentResponse execute(MultipartBody request,
			String soId, String providerId, byte[] fileByte,String createdBy) {
		logger.info("Inside ProviderUploadDocumentService.execute()");
		ProviderUploadDocumentResponse providerUploadDocumentResponse = new ProviderUploadDocumentResponse();
		try {

			boolean validProviderFirm = mobileSOManagementBO
					.isValidProviderResource(providerId);
			if (validProviderFirm) {

				boolean validServiceOrder = mobileSOManagementBO
						.isValidServiceOrder(soId);
				if (validServiceOrder) {

					boolean validServiceOrderProviderAssoc = mobileSOManagementBO
							.isAuthorizedInViewSODetails(soId, providerId);
					Results results =  validateProviderForSO(soId,providerId,validServiceOrderProviderAssoc);
					if(null != results){
						providerUploadDocumentResponse
						.setResults(results);	
						providerUploadDocumentResponse.setSoId(soId);
						providerUploadDocumentResponse.setDocument(null);	
						return providerUploadDocumentResponse;
					}
						// Get API input data
						String documentName = "";
						String mediaType = "";
						String documentDescription = "";
						String documentType = "";
						String documentCategory = "";
						String tripNo = "";
						Integer tripNumber = 0;
						String tripStatus="";
						Integer currentTripId = 0;
						List<Attachment> attachments = request
								.getAllAttachments();

						logger.info("attachment size:::" + attachments.size());
						String exactFileName = "";
						/*
						 * Every multipart entity which is set in the request is
						 * mapped as one ATTACHMENT in cfx MultiPart Iterating
						 * through these attachments, we will get all the fields
						 * passed as request.
						 */

						for (Attachment attachment : attachments) {

							// Develop a DataHandler for the data being passed
							// as an attachment.
							DataHandler attachmentHandler = attachment
									.getDataHandler();
							MultivaluedMap<String, String> formDetailsMap = attachment
									.getHeaders();

							// We use Content-Disposition header, when dealing
							// with the form submission of files.
							String[] formContentDisposition = formDetailsMap
									.getFirst("Content-Disposition").split(";");

							logger.info("fields size:::"
									+ formContentDisposition.length);
							for (String fields : formContentDisposition) {
								/*
								 * This "if" condition to extract the
								 * "File Description" and "Document Category"
								 * which is obtained from the multipart request
								 */
								if (fields.trim().startsWith(NAME)) {
									String[] fieldsArray = fields.split("=");
									if (StringUtils.contains(fieldsArray[1]
											.trim().replaceAll("\"", ""),
											DOCUMENT_DESC)) {
										documentDescription = getStringFromInputStream(attachmentHandler
												.getInputStream());
									} else if (StringUtils.contains(
											fieldsArray[1].trim().replaceAll(
													"\"", ""),
											DOCUMENT_CATEGORY)) {
										documentCategory = getStringFromInputStream(attachmentHandler
												.getInputStream());
									} else if (StringUtils.contains(
											fieldsArray[1].trim().replaceAll(
													"\"", ""), DOCUMENT_TYPE)) {
										documentType = getStringFromInputStream(attachmentHandler
												.getInputStream());
										if (null != documentType
												&& documentType.toLowerCase()
														.contains("provider")) {
											documentType = MPConstants.PROVIDER_SIGNATURE_FOR_UPLOAD;
										}else if (null != documentType
												&& documentType.toLowerCase()
												.contains("waiver of lien")) {
											documentType = MPConstants.SIGNED_CUSTOMER_COPY_INCLUDING_WAIVER_OF_LIEN;
										} else if (null != documentType
												&& documentType.toLowerCase()
														.contains("customer")) {
											documentType = MPConstants.CUSTOMER_SIGNATURE_FOR_UPLOAD;
										} else if (null != documentType
												&& documentType.toLowerCase()
														.contains("permit")) {
											documentType = MPConstants.PROOF_OF_PERMIT_FOR_UPLOAD;
										}
										
									} else if (StringUtils.contains(
											fieldsArray[1].trim().replaceAll(
													"\"", ""), DOCUMENT_NAME)) {
										String docName = getStringFromInputStream(attachmentHandler
												.getInputStream());
										if (StringUtils.isNotBlank(docName)) {
											documentName = docName;
										}						
									}
									else if (StringUtils.contains(
											fieldsArray[1].trim().replaceAll(
													"\"", ""), TRIP_NO)) {
										tripNo =  getStringFromInputStream(attachmentHandler
												.getInputStream());
									}
									else if (StringUtils.contains(
											fieldsArray[1].trim().replaceAll(
													"\"", ""), TRIP_STATUS)) {
										tripStatus =  getStringFromInputStream(attachmentHandler
												.getInputStream());
									}
									
								}
								/*
								 * This "if" condition to extract the File which
								 * is obtained from the multipart request
								 */
								if (fields.trim().startsWith(FILE_NAME)) {
									/*
									 * If fileName is passed as form data, then
									 * the document name is set in the previous
									 * step. Else, filename is extracted from
									 * the file.
									 */
									String[] name = fields.split("=");
									exactFileName = name[1].trim().replaceAll(
											"\"", "");
									// if filename extracted from the file is
									// empty or if the file byte size is 0,
									// then it means that no file is attached.
									if (StringUtils.isBlank(exactFileName)
											|| fileByte.length <= 0) {
										providerUploadDocumentResponse
												.setResults(Results
														.getError(
																ResultsCode.INVALID_FILE
																		.getMessage(),
																ResultsCode.INVALID_FILE
																		.getCode()));
										providerUploadDocumentResponse
												.setDocument(null);
										return providerUploadDocumentResponse;
									}

									if (StringUtils.isBlank(documentName)) {
										documentName = exactFileName;
									}

									String[] media = formDetailsMap.getFirst(
											"Content-Type").split(";");
									mediaType = media[0];
									

									Integer size = fileByte.length
											/ OrderConstants.SIZE_KB;
									if (size > OrderConstants.FIVE_KB) {
										providerUploadDocumentResponse
												.setResults(Results
														.getError(
																ResultsCode.INVALID_FILE_SIZE
																		.getMessage(),
																ResultsCode.INVALID_FILE_SIZE
																		.getCode()));
										providerUploadDocumentResponse
												.setDocument(null);
										return providerUploadDocumentResponse;
									}
								}
							}
						}
						//Issue fix IssueNo:SL-20327
						try{
							tripNumber = Integer.valueOf(tripNo);
						}catch(NumberFormatException e){
							tripNumber = 0;
						}
						// Validate the trip number
						
						// NOTE: Since on Day 1 of implementation, there are
						// orders with no trip (0), we are not making this as a 
						// validation. Assumption - GET Service order API will return 
						// the current trip number as 0 if there is no OPEN trips 
						
						if(0!=tripNumber){
							//currentTripId = mobileSOActionsBO.validateLatestOpenTrip(tripNumber, soId);
							currentTripId = mobileSOActionsBO.getTripId(tripNumber, soId);
							if(null==currentTripId){
								providerUploadDocumentResponse.setResults(Results
										.getError(ResultsCode.TIMEONSITE_INVALID_TRIP.getMessage(),
												ResultsCode.TIMEONSITE_INVALID_TRIP.getCode()));
								providerUploadDocumentResponse.setSoId(soId);
								providerUploadDocumentResponse.setDocument(null);
								return providerUploadDocumentResponse;
							}
						}

						// setting the document data
						//Added code for ESAPI Encoding
						documentName = EsapiUtility.getEncodedString(documentName);
						documentDescription = EsapiUtility.getEncodedString(documentDescription);
						
						DocumentVO documentVO = new DocumentVO();
						documentVO.setTitle(documentType);
						documentVO.setSoId(soId);
						documentVO.setDocCategory(documentCategory);
						documentVO.setFileName(documentName);
						documentVO.setDescription(documentDescription);
						documentVO.setFormat(mediaType);
						documentVO.setEntityId(Integer.parseInt(providerId));
						documentVO.setRoleId(MPConstants.PROVIDER_ROLE);
						documentVO.setBlobBytes(fileByte);
						documentVO.setCompanyId(getFirmId(providerId));
						documentVO.setDocSize(new Long(fileByte.length));


						logger.info("documentType::" + documentType);
						logger.info("documentCategory::" + documentCategory);
						logger.info("documentDescription::" + documentName);
						logger.info("documentDescription::"
								+ documentDescription);
						logger.info("tripNo::"+ tripNo);

						processResponse = documentBO
								.insertServiceOrderDocument(documentVO);
						if (null != processResponse) {

							if (ServiceConstants.VALID_RC
									.equals(processResponse.getCode())) {
								getDocumentDetails(soId, documentName,providerUploadDocumentResponse);
								providerUploadDocumentResponse.setSoId(soId);
								providerUploadDocumentResponse
										.setResults(Results.getSuccess());								
								
								// TODO Update the trip No details in the so_trip_details table
								if(0!=tripNumber && 0!=currentTripId){
									
									// 1. Get the current trip id.
									// 2. Insert an entry in to so_trip_details table for 
									//    change_type 'DOCUMENT/PHOTOS'
									// 3. Change_comment --> 'documentType' added
									String changeComment = MPConstants.UPLOADED+" "+documentType;
									
									Integer tripDetailsId = mobileSOActionsBO.
										addTripDetails(currentTripId,MPConstants.PHOTOS_DOCUMENTS,changeComment,createdBy);
									
									logger.info("tripDetailsId::"+tripDetailsId);
									
								}
								return providerUploadDocumentResponse;

							}

							// if document upload is not allowed in the current
							// SO status
							else if (OrderConstants.SO_DOC_NOT_IN_ALLOWED_STATE_ERROR_RC
									.equals(processResponse.getCode())) {
								providerUploadDocumentResponse
										.setResults(Results
												.getError(
														ResultsCode.INVALID_SERVICE_ORDER_STATUS
																.getMessage(),
														ResultsCode.INVALID_SERVICE_ORDER_STATUS
																.getCode()));
								providerUploadDocumentResponse.setSoId(soId);
								providerUploadDocumentResponse
										.setDocument(null);
								return providerUploadDocumentResponse;
							}

							// invalid file format
							else if (OrderConstants.SO_DOC_INVALID_FORMAT
									.equals(processResponse.getCode())
									|| OrderConstants.SO_DOC_INVALID_FORMAT_SEARS_BUYER
											.equals(processResponse.getCode())) {
								providerUploadDocumentResponse
										.setResults(Results.getError(
												ResultsCode.INVALID_FILE_FORMAT
														.getMessage(),
												ResultsCode.INVALID_FILE_FORMAT
														.getCode()));
								providerUploadDocumentResponse.setSoId(soId);
								providerUploadDocumentResponse
										.setDocument(null);
								return providerUploadDocumentResponse;
							} else if (OrderConstants.SO_DOC_UPLOAD_EXSITS
									.equals(processResponse.getCode())) {
								providerUploadDocumentResponse
										.setResults(Results.getError(
												ResultsCode.DOC_EXISTS
														.getMessage(),
												ResultsCode.DOC_EXISTS
														.getCode()));
								providerUploadDocumentResponse.setSoId(soId);
								providerUploadDocumentResponse
										.setDocument(null);
								
								return providerUploadDocumentResponse;
							}
							//CC-1113 Changes --START
							else if (OrderConstants.DOC_UPLOAD_ERROR_RC
									.equals(processResponse.getCode())) { //check to prevent the doc upload error
								providerUploadDocumentResponse
										.setResults(Results.getError(
												ResultsCode.DOC_UPLOAD_ERROR
														.getMessage(),
												ResultsCode.DOC_UPLOAD_ERROR
														.getCode()));
								providerUploadDocumentResponse.setSoId(soId);
								providerUploadDocumentResponse
										.setDocument(null);
								
								return providerUploadDocumentResponse;
							}
							else if (OrderConstants.DOC_PROCESSING_ERROR_RC
									.equals(processResponse.getCode())) { //check to prevent the doc processing error
								providerUploadDocumentResponse
										.setResults(Results.getError(
												ResultsCode.DOC_PROCESSING_ERROR
														.getMessage(),
												ResultsCode.DOC_PROCESSING_ERROR
														.getCode()));
								providerUploadDocumentResponse.setSoId(soId);
								providerUploadDocumentResponse
								.setDocument(null);
								
								return providerUploadDocumentResponse;
							}
							else if (OrderConstants.DOC_USER_AUTH_ERROR_RC
									.equals(processResponse.getCode())) { //check to prevent the doc auth error
								providerUploadDocumentResponse
										.setResults(Results.getError(
												ResultsCode.DOC_USER_AUTH_ERROR
														.getMessage(),
												ResultsCode.DOC_USER_AUTH_ERROR
														.getCode()));
								providerUploadDocumentResponse.setSoId(soId);
								providerUploadDocumentResponse
										.setDocument(null);
								
								return providerUploadDocumentResponse;
							}
							else if (OrderConstants.SO_DOC_SIZE_EXCEEDED_RC
									.equals(processResponse.getCode())) { //check to prevent the doc size exceeded error
								providerUploadDocumentResponse
										.setResults(Results.getError(
												ResultsCode.SO_DOC_SIZE_EXCEEDED
														.getMessage(),
												ResultsCode.SO_DOC_SIZE_EXCEEDED
														.getCode()));
								providerUploadDocumentResponse.setSoId(soId);
								providerUploadDocumentResponse
										.setDocument(null);
								
								return providerUploadDocumentResponse;
							}//CC-1113 Changes --END

						}

				

				} else {
					// Error message for invalid service order
					providerUploadDocumentResponse.setResults(Results.getError(
							ResultsCode.INVALID_SO_ID.getMessage(),
							ResultsCode.INVALID_SO_ID.getCode()));
					providerUploadDocumentResponse.setSoId(soId);
					providerUploadDocumentResponse.setDocument(null);					
					return providerUploadDocumentResponse;
				}

			} else {
				// Error message for invalid provide firm id
				providerUploadDocumentResponse.setResults(Results.getError(
						ResultsCode.INVALID_RESOURCE_ID.getMessage(),
						ResultsCode.INVALID_RESOURCE_ID.getCode()));
				providerUploadDocumentResponse.setSoId(soId);
				providerUploadDocumentResponse.setDocument(null);				
				return providerUploadDocumentResponse;
			}

		} catch (Exception e) {
			logger.error("Exception in ProviderUploadDocumentService.execute()"
					+ e.getMessage());
			providerUploadDocumentResponse.setResults(Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(),ResultsCode.INTERNAL_SERVER_ERROR.getCode()));
			e.printStackTrace();
		}
	
		return providerUploadDocumentResponse;
	}

	/**
	 * @param soId
	 * @param providerId
	 * @param validServiceOrderProviderAssoc
	 * @return
	 * method to validate provider permission based on role
	 */
	private Results validateProviderForSO(String soId, String providerId,
			boolean validServiceOrderProviderAssoc) {
		
		 
		Results result = null;
		try{
			Integer resourceRoleLevel = authenticateUserBO.getRoleOfResource(null, Integer.parseInt(providerId));
			Integer firmId = mobileSOManagementBO.validateProviderId(providerId); 
			if(null!=firmId && null !=providerId){
				SoDetailsVO detailsVO = new SoDetailsVO();
			 detailsVO.setSoId(soId);
			 detailsVO.setFirmId(firmId.toString());
			 detailsVO.setProviderId(Integer.parseInt(providerId));
			 detailsVO.setRoleId(resourceRoleLevel);
			 boolean authSuccess = mobileGenericBO.isAuthorizedToViewBeyondPosted(detailsVO);
			 if(!authSuccess){
				 result = Results.getError(ResultsCode.PERMISSION_ERROR.getMessage(),
							ResultsCode.PERMISSION_ERROR.getCode());	
			 }
			}
			List<Integer> roleIdValues = Arrays.asList(
					PublicMobileAPIConstant.ROLE_LEVEL_ONE,
					PublicMobileAPIConstant.ROLE_LEVEL_TWO,
					PublicMobileAPIConstant.ROLE_LEVEL_THREE);
			if (null == resourceRoleLevel || !roleIdValues.contains(resourceRoleLevel)) {
				result = Results.getError(ResultsCode.INVALID_ROLE.getMessage(),ResultsCode.INVALID_ROLE.getCode());
			}
		}
		catch(Exception e){
			logger.error("Exception inside validateProviderForSO inside Upload Document");
			e.printStackTrace();
		}
		
		
		return result;
	}

	public static String getStringFromInputStream(InputStream in)
			throws Exception {
		CachedOutputStream bos = new CachedOutputStream();
		IOUtils.copy(in, bos);
		in.close();
		bos.close();
		return bos.getOut().toString();
	}

	private void getDocumentDetails(String soId, String documentName,ProviderUploadDocumentResponse providerUploadDocumentResponse) {

		HashMap<String, Object> docDetails = new HashMap<String, Object>();
		ProviderDocument document = new ProviderDocument();
		try {
			docDetails = mobileSOManagementBO.getDocumentDetails(soId,
					documentName);

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

			document.setDocumentId(docId);
			// for mat the date
			document.setUploadedTime(uploadedDateString);
			if(null!=providerUploadDocumentResponse){
			providerUploadDocumentResponse.setSoId(soId);
			providerUploadDocumentResponse.setDocument(document);
			}
		} catch (Exception e) {
			logger.info("Exception in ProviderUploadDocumentService.getDocumentDetails() "
					+ e.getMessage());
			e.printStackTrace();
		}

	}

	// get the firmId for the providerId
	private Integer getFirmId(String providerId) {
		Integer firmId = null;
		try {
			firmId = mobileSOManagementBO.getFirmId(providerId);

		} catch (Exception e) {
			logger.info("Exception in ProviderUploadDocumentService.getFirmId() "
					+ e.getMessage());
			e.printStackTrace();
		}
		return firmId;
	}

	/**
	 * Format the date
	 * 
	 * @param format
	 * @param date
	 * @return
	 */
	private String formatDate(String format, Date date) {
		DateFormat formatter = new SimpleDateFormat(format);
		String formattedDate = null;
		try {
			formattedDate = formatter.format(date);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return formattedDate;
	}

	public IMobileSOManagementBO getMobileSOManagementBO() {
		return mobileSOManagementBO;
	}

	public void setMobileSOManagementBO(
			IMobileSOManagementBO mobileSOManagementBO) {
		this.mobileSOManagementBO = mobileSOManagementBO;
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

	/**
	 * @return the mobileSOActionsBO
	 */
	public IMobileSOActionsBO getMobileSOActionsBO() {
		return mobileSOActionsBO;
	}

	/**
	 * @param mobileSOActionsBO the mobileSOActionsBO to set
	 */
	public void setMobileSOActionsBO(IMobileSOActionsBO mobileSOActionsBO) {
		this.mobileSOActionsBO = mobileSOActionsBO;
	}
	

}