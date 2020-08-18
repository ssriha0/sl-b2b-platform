/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 04-Apr-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.mobile.services;

import java.io.InputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import com.newco.marketplace.api.mobile.beans.uploadDocument.ProviderDocument;
import com.newco.marketplace.api.mobile.beans.uploadDocument.ProviderUploadDocumentResponse;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.business.iBusiness.document.IDocumentBO;
import com.newco.marketplace.business.iBusiness.mobile.IMobileSOManagementBO;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.util.EsapiUtility;
import com.newco.marketplace.utils.DateUtils;
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
	private IDocumentBO documentBO;
	ProcessResponse processResponse = new ProcessResponse();
	ProviderUploadDocumentResponse providerUploadDocumentResponse = new ProviderUploadDocumentResponse();
	
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

	/**
	 * Implement your logic here.
	 */
	public ProviderUploadDocumentResponse execute(MultipartBody request,
			String soId, String providerId, byte[] fileByte) {
		logger.info("Inside ProviderUploadDocumentService.execute()");
		try {

			boolean validProviderFirm = mobileSOManagementBO
					.isValidProviderResource(providerId);
			if (validProviderFirm) {

				boolean validServiceOrder = mobileSOManagementBO
						.isValidServiceOrder(soId);
				if (validServiceOrder) {

					boolean validServiceOrderProviderFirmAssoc = mobileSOManagementBO
							.isAuthorizedInViewSODetails(soId, providerId);

					if (validServiceOrderProviderFirmAssoc) {
						// Get API input data
						String documentName = "";
						String mediaType = "";
						String documentDescription = "";
						String documentType = "";
						String documentCategory = "";
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

						processResponse = documentBO
								.insertServiceOrderDocument(documentVO);
						if (null != processResponse) {

							if (ServiceConstants.VALID_RC
									.equals(processResponse.getCode())) {
								getDocumentDetails(soId, documentName);
								providerUploadDocumentResponse.setSoId(soId);
								providerUploadDocumentResponse
										.setResults(Results.getSuccess());
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

						}

					} else {
						// invalid service order and provider firm association
						providerUploadDocumentResponse
								.setResults(Results
										.getError(
												ResultsCode.INVALID_SO_PROVIDER_ASSOCIATION
														.getMessage(),
												ResultsCode.INVALID_SO_PROVIDER_ASSOCIATION
														.getCode()));
						providerUploadDocumentResponse.setSoId(soId);
						providerUploadDocumentResponse.setDocument(null);
						return providerUploadDocumentResponse;
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
			logger.info("Exception in ProviderUploadDocumentService.execute()"
					+ e.getMessage());
			e.printStackTrace();
		}

		return providerUploadDocumentResponse;
	}

	public static String getStringFromInputStream(InputStream in)
			throws Exception {
		CachedOutputStream bos = new CachedOutputStream();
		IOUtils.copy(in, bos);
		in.close();
		bos.close();
		return bos.getOut().toString();
	}

	private void getDocumentDetails(String soId, String documentName) {

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

			providerUploadDocumentResponse.setSoId(soId);
			providerUploadDocumentResponse.setDocument(document);

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

}