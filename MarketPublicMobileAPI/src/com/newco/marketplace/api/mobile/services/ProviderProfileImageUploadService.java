package com.newco.marketplace.api.mobile.services;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.activation.DataHandler;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.mobile.beans.uploadProviderProfileImage.ProviderProfileImageUploadResponse;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.business.iBusiness.mobile.IMobileSOManagementBO;
import com.newco.marketplace.business.iBusiness.provider.IProviderInfoPagesBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.provider.ProviderDocumentVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.webservices.base.response.ProcessResponse;

public class ProviderProfileImageUploadService {
	private Logger logger = Logger
			.getLogger(ProviderProfileImageUploadService.class);

	private IMobileSOManagementBO mobileSoManagement;
	private IProviderInfoPagesBO providerInfoPagesBO;

	public ProviderProfileImageUploadResponse execute(MultipartBody request,
			String providerId, byte[] fileByte) {
		;
		logger.info("Entering execute method");
		Results results = new Results();
		ProviderDocumentVO providerDocument = new ProviderDocumentVO();
		DocumentVO documentVO = new DocumentVO();
		ProcessResponse uploadImageResponse = null;
		ProviderProfileImageUploadResponse response = new ProviderProfileImageUploadResponse();

		if (StringUtils.isBlank(providerId)) {
			logger.info("resource id blank-->");
			results = Results.getError(
					ResultsCode.INVALID_RESOURCE_ID.getMessage(),
					ResultsCode.INVALID_RESOURCE_ID.getCode());
			response.setResults(results);
			return response;
		} else {
			try {
				if (!mobileSoManagement.isValidProviderResource(providerId)) {
					results = Results.getError(
							ResultsCode.INVALID_RESOURCE_ID.getMessage(),
							ResultsCode.INVALID_RESOURCE_ID.getCode());
					response.setResults(results);
					return response;
				}
			} catch (BusinessServiceException e) {
				logger.error(
						"Error occurred while executing mobileSoManagement.isValidProviderResource...Track back there",
						e);
				results = Results.getError(
						ResultsCode.INVALID_PROVIDER.getMessage(),
						ResultsCode.INVALID_PROVIDER.getCode());
				response.setResults(results);
				return response;
			}
		}
		
		providerDocument.setResourceId(Integer.parseInt(providerId));
		providerDocument.setPrimaryInd(true);
		providerDocument.setCreatedDate(new Date());
		providerDocument.setModifiedDate(new Date());
		providerDocument.setReviewedInd(Boolean.FALSE);
		documentVO.setBlobBytes(fileByte);
		documentVO.setEntityId(Integer.parseInt(providerId));
		
		List<Attachment> attachments = request
				.getAllAttachments();
		String documentName = "";
		String mediaType = "";
		File file = null;
		for (Attachment attachment : attachments) {

			// Develop a DataHandler for the data being passed
			// as an attachment.
			DataHandler attachmentHandler = attachment
					.getDataHandler();
			MultivaluedMap<String, String> formDetailsMap = attachment
					.getHeaders();
			file = (File)attachment.getObject();
			// We use Content-Disposition header, when dealing
			// with the form submission of files.
			String[] formContentDisposition = formDetailsMap
					.getFirst("Content-Disposition").split(";");
			logger.info("fields size:::"
					+ formContentDisposition.length);
			logger.info("Content-Disposition:::" + formDetailsMap.getFirst("Content-Disposition"));
			for (String fields : formContentDisposition) {
				/*
				 * This "if" condition to extract the File which is obtained
				 * from the multipart request
				 */
				if (fields.trim().startsWith("filename")) {
					/*
					 * If fileName is passed as form data, then the document
					 * name is set in the previous step. Else, filename is
					 * extracted from the file.
					 */
					String[] name = fields.split("=");
					String exactFileName = name[1].trim().replaceAll("\"", "");
					// if filename extracted from the file is
					// empty or if the file byte size is 0,
					// then it means that no file is attached.
					if (StringUtils.isBlank(exactFileName)
							|| fileByte.length <= 0) {
						response.setResults(Results.getError(
								ResultsCode.INVALID_FILE.getMessage(),
								ResultsCode.INVALID_FILE.getCode()));
						return response;
					}

					if (StringUtils.isBlank(documentName)) {
						documentName = exactFileName;
					}

					String[] media = formDetailsMap.getFirst("Content-Type")
							.split(";");
					mediaType = media[0];

				}

			}
			
		}
		documentVO.setFileName(documentName);
		documentVO.setDocument(file);
		documentVO.setFormat(mediaType);
		documentVO.setDocSize(Long.valueOf(fileByte.length));
		documentVO.setDocCategoryId(Constants.DocumentTypes.CATEGORY.PROVIDER_PHOTO);
		providerDocument.setDocDetails(documentVO);
		
		try {
			uploadImageResponse = providerInfoPagesBO
					.uploadResourcePicture(providerDocument);
		} catch (BusinessServiceException e) {
			logger.error(
					"Error occurred while uploading Provider profile image...Track back there",
					e);
			results = Results.getError(
					ResultsCode.PROFILE_IMAGE_UPLOAD_API_ERROR.getMessage(),
					ResultsCode.PROFILE_IMAGE_UPLOAD_API_ERROR.getCode());
			response.setResults(results);
			return response;
		}
		if (null == uploadImageResponse) {
			logger.error("Error occurred while uploading Provider profile image");
			results = Results.getError(
					ResultsCode.PROFILE_IMAGE_UPLOAD_API_ERROR.getMessage(),
					ResultsCode.PROFILE_IMAGE_UPLOAD_API_ERROR.getCode());
			response.setResults(results);
			return response;
		} else {
			if (uploadImageResponse.getCode().equalsIgnoreCase(
					OrderConstants.SO_DOC_INVALID_FORMAT)) {
				response.setResults(Results.getError(
						ResultsCode.INVALID_FILE.getMessage(),
						ResultsCode.INVALID_FILE.getCode()));
				response.setDocument(null);
				return response;
			} else if (uploadImageResponse.getCode().equalsIgnoreCase(
					OrderConstants.SO_DOC_SIZE_EXCEEDED_RC)) {
				response.setResults(Results.getError(
						ResultsCode.INVALID_FILE_SIZE.getMessage(),
						ResultsCode.INVALID_FILE_SIZE.getCode()));
				response.setDocument(null);
				return response;
			}
		}
		response.setResults(Results.getSuccess());
		providerDocument = null;
		logger.info("Leaving execute method");
		return response;
	}

	public IMobileSOManagementBO getMobileSoManagement() {
		return mobileSoManagement;
	}

	public void setMobileSoManagement(IMobileSOManagementBO mobileSoManagement) {
		this.mobileSoManagement = mobileSoManagement;
	}

	public IProviderInfoPagesBO getProviderInfoPagesBO() {
		return providerInfoPagesBO;
	}

	public void setProviderInfoPagesBO(IProviderInfoPagesBO providerInfoPagesBO) {
		this.providerInfoPagesBO = providerInfoPagesBO;
	}
}
