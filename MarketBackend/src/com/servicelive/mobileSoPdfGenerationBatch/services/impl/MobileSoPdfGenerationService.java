package com.servicelive.mobileSoPdfGenerationBatch.services.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.newco.marketplace.api.mobile.beans.sodetails.AddonPayment;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.Signature;
import com.newco.marketplace.business.businessImpl.document.DocumentBO;
import com.newco.marketplace.business.iBusiness.mobile.IMobileSOManagementBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.persistence.iDao.mobile.IMobileSOActionsDao;
import com.newco.marketplace.vo.mobile.BuyerTemplateMapperVO;
import com.newco.marketplace.vo.mobile.ProviderHistoryVO;
import com.newco.marketplace.vo.mobile.TemplateDetailsVO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.service.ServiceConstants;
import com.servicelive.mobileSoPdfGenerationBatch.services.IMobileSoPdfGenerationService;

public class MobileSoPdfGenerationService implements
		IMobileSoPdfGenerationService {
	private static final Logger logger = Logger
			.getLogger(MobileSoPdfGenerationService.class);

	private IMobileSOActionsDao mobileSOActionsDao;
	private DocumentBO documentBO;
	private IServiceOrderBO serviceOrderBOTarget;
	private IMobileSOManagementBO mobileSOManagementBO;

	/**
	 * 
	 * to get the signature details of service orders
	 * 
	 * @throws BusinessServiceException
	 */
	public List<Signature> getSignatureDetails(String limit)
			throws BusinessServiceException {
		List<Signature> signatureList = null;
		try {
			logger.debug("inside getSignatureDetails");
			signatureList = mobileSOActionsDao.getSignatureDetails(limit);
			logger.info("after getSignatureDetails");
		} catch (Exception ex) {
			logger.error("Exception Occured in MobileSOActionsBOImpl-->getSignatureDetails()"
					+ ex);
			throw new BusinessServiceException(ex);
		}
		return signatureList;
	}

	public void updateSignature(Signature signature, String updateStatus)
			throws BusinessServiceException {
		try {
			logger.debug("inside updateSignature");
			if (null != updateStatus && StringUtils.isNotBlank(updateStatus)) {
				signature.setPdfStatus(updateStatus);
			}
			mobileSOActionsDao.updateSignature(signature);
			logger.info("after updateSignature");
		} catch (Exception e) {
			logger.error("Exception occured in updateSignature() due to " + e);
			throw new BusinessServiceException(e);
		}
	}
	
	/**
	 * to update the email_sent_ind 
	 */
	public void updateSignatureEmailSent(Signature signature) throws BusinessServiceException {
		try {
			logger.debug("inside updateSignatureEmailSent");
			mobileSOActionsDao.updateSignatureEmailSent(signature);
			logger.info("after updateSignature");
		} catch (Exception e) {
			logger.error("Exception occured in updateSignature() due to " + e);
			throw new BusinessServiceException(e);
		}
	}

	/**
	 * to check if order is completed
	 * 
	 * @param soId
	 * @throws BusinessServiceException
	 */
	public boolean isOrderCompleted(String soId)
			throws BusinessServiceException {
		try {
			logger.info("isOrderCompleted");
			return mobileSOActionsDao.selectSubStatus(soId);

		} catch (Exception e) {
			logger.error("Exception occured in isOrderCompleted() due to " + e);
			throw new BusinessServiceException(e);
		}
	}

	/**
	 * to generate service order as pdf with uploaded signatures
	 * 
	 * @param signature
	 * @throws BusinessServiceException
	 */
	public ByteArrayOutputStream generatePdf(List<Signature> signatureList, boolean customerCopyOnlyInd)
			throws BusinessServiceException {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		String soId = signatureList.get(0).getSoId();
		AddonPayment additional = new AddonPayment();
		try {
			additional = mobileSOActionsDao.getAdditionalPaymentData(soId);
			String cardNumber = "";
			if (null != additional) {
				if (null != additional.getCcNumber()
						&& StringUtils.isNotBlank(additional.getCcNumber())) {
					cardNumber = mobileSOManagementBO
							.decryptCreditCardInfo(additional.getCcNumber());
				}else{
					cardNumber =  additional.getMaskedAccNumber();
					if(cardNumber !=null && StringUtils.isNotBlank(cardNumber)){
						int length = cardNumber.length();
						if(length >= 4){
							cardNumber = cardNumber.substring(length-4, length);
						}
					}
				}
				additional.setCcNumber(cardNumber);
			}

			logger.debug("inside generatePdf");
			bytes = serviceOrderBOTarget.getPDFForMobile(soId, signatureList,
					additional, customerCopyOnlyInd);
			if (null == bytes) {
				throw new BusinessServiceException("PDF not generated.");
			}
			logger.info("after generatePdf");
		} catch (Exception e) {
			logger.error("Exception occured in generatePdf() due to " + e);
			throw new BusinessServiceException(e);
		}
		return bytes;
	}

	/**
	 * Method for sending mail to customer with generated so pdf as attachment
	 * 
	 * @param byteArrayOutputStream
	 * @throws BusinessServiceException
	 */
	public void sendAttachmentMail(ByteArrayOutputStream byteArrayOutputStream,
			Signature signature, HashMap<Integer, BuyerTemplateMapperVO> buyerTemplates, List<TemplateDetailsVO> templates) throws BusinessServiceException {
		logger.debug("inside send mail");
		StringWriter bodyStringWriter = new StringWriter();
		StringWriter subjectStringWriter = new StringWriter();
		TemplateDetailsVO templateDetailsVO = null;
		List<String> templateInputs = null;
		if (null != signature.getCustomerEmail()
				&& StringUtils.isNotBlank(signature.getCustomerEmail())) {
			logger.info("mailing address" + signature.getCustomerEmail());
			String toRecipient = signature.getCustomerEmail();
			
			// SLM-69 : Modifies email send code in java mail to velocity mail to incorporate multiple templates for multiple buyers.
			Map<String, Object> customerEmailDetails = getCustomerEmailDetails(signature.getName(), signature.getSoId());
			String buyerId = customerEmailDetails.get("buyerId").toString();
			Integer defaultBuyerId = new Integer(0);
			BuyerTemplateMapperVO templateDetails = buyerTemplates.get(Integer.parseInt(buyerId));
			if(null == templateDetails){
				templateDetails = buyerTemplates.get(defaultBuyerId);
			}
			templateDetailsVO = getTemplateDetails(templateDetails, templates);
			templateInputs = splitTemplateInputs(templateDetails.getTemplateInputs());
			if(null != templateDetailsVO){
				try{
					VelocityContext velocityContext =  new VelocityContext();
					// Please follow the below steps while adding a new template input.
					// 1. The new template input should be available in the template_input column of the buyer_email_template_mapping table as comma separated.
					// 2. The new template should be available in the customerEmailDetails map and the same input name should be available as the key.
					if(null != templateInputs && null != customerEmailDetails){
						for(String templateInput : templateInputs){
							velocityContext.put(templateInput, customerEmailDetails.get(templateInput));
						}
					}
					VelocityEngine velocityEngine = new VelocityEngine();
					
					velocityEngine.evaluate(velocityContext, bodyStringWriter, MPConstants.VELOCITY_TEMPLATE, templateDetailsVO.getTemplateSource());
					velocityEngine.evaluate(velocityContext, subjectStringWriter, MPConstants.VELOCITY_TEMPLATE, templateDetails.getSubject());
				}catch (Exception e) {
					logger.error("Exception Occured in MobileSOActionsBOImpl-->sendAttachmentMail()" + e);
				}
				
				JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
				mailSender.setHost(MPConstants.MAIL_HOST);
				MimeMessage mimeMessage = mailSender.createMimeMessage();
				MimeMessageHelper mimeHelper;
				try {
					final byte[] data = byteArrayOutputStream.toByteArray();
					
					mimeHelper = new MimeMessageHelper(mimeMessage, true);
					mimeHelper.setText(bodyStringWriter.toString(), true);
					mimeHelper.setSubject(subjectStringWriter.toString());
					mimeHelper.setTo(toRecipient);
					//mimeHelper.setCc(getEmailAddresses(ccAddress));
					//mimeHelper.setBcc(getEmailAddresses(bccAddress));
					if(StringUtils.isNotBlank(templateDetails.getFromEmail()) && StringUtils.isNotBlank(templateDetails.getAliasName())){
						mimeHelper.setFrom(new InternetAddress(templateDetails.getFromEmail(), templateDetails.getAliasName()));
					}else if(StringUtils.isNotBlank(templateDetails.getFromEmail()) && StringUtils.isBlank(templateDetails.getAliasName())){
						mimeHelper.setFrom(new InternetAddress(templateDetails.getFromEmail()));
					}else if(StringUtils.isBlank(templateDetails.getFromEmail()) && StringUtils.isNotBlank(templateDetails.getAliasName())){
						mimeHelper.setFrom(new InternetAddress("noreply@servicelive.com", templateDetails.getAliasName()));
					}else{
						mimeHelper.setFrom(new InternetAddress("noreply@servicelive.com"));
					}
					if(StringUtils.isNotBlank(templateDetails.getReplyToEmail())){
						mimeHelper.setReplyTo(templateDetails.getReplyToEmail());
					}					
					String docPath = "";
					try {		
						String soId = signature.getSoId();
						String soReplace = soId.replace("-", "");
						String docName = soId +MPConstants.PDF_FORMAT;
						docPath = mobileSOActionsDao.getDocumentPath(
								soId,docName);
						docPath = formatPdf(docPath,soId,buyerId);
						File someFile = new File(docPath);
						FileOutputStream fos = new FileOutputStream(someFile);
						fos.write(data);
						fos.flush();
						fos.close();
						if(MPConstants.INHOME_BUYER.toString().equals(buyerId)){
							mimeHelper.addAttachment(MPConstants.SEARS_REPAIR+soReplace
									+ MPConstants.PDF_FORMAT, someFile);
						}
						else{
							mimeHelper.addAttachment(soId
									+ MPConstants.PDF_FORMAT, someFile);
						}
								
						logger.debug("inside send mail second try data:");
					} catch (Exception e) {
						throw new BusinessServiceException(e);
					}
					mailSender.send(mimeMessage);
					// removing the code to delete file.
					//	delelteFile(docPath);
				} catch (MessagingException e) {
					logger.error("Caught Exception in sendAttachmentMail() :", e);
					throw new BusinessServiceException(e);
				} catch (UnsupportedEncodingException e) {
					logger.error("Caught Exception in sendAttachmentMail() :", e);
					throw new BusinessServiceException(e);
				}
			}
		}
	}
	
	

	private List<String> splitTemplateInputs(String templateInputsString) {
		List<String> templateInputs = null;
		if(StringUtils.isNotBlank(templateInputsString)){
			String[] inputs = templateInputsString.split(",");
			templateInputs = Arrays.asList(inputs);
		}
		return templateInputs;
	}

	private String formatPdf(String docPath,String soId, String buyerId) {
		String extn = soId+MPConstants.PDF_FORMAT;
		Integer index = docPath.indexOf(extn);
		docPath = docPath.substring(0, index);
		docPath = docPath + soId+MPConstants.DIGITAL_SIGNATURE+MPConstants.PDF_FORMAT;
		return docPath;
	}

	private TemplateDetailsVO getTemplateDetails(BuyerTemplateMapperVO templateDetails, List<TemplateDetailsVO> templates) {
		if(null != templateDetails && null != templateDetails.getTemplateId() && null != templates && !templates.isEmpty()){
			for(TemplateDetailsVO detailsVO : templates){
				if(templateDetails.getTemplateId().equals(detailsVO.getTemplateId())){
					return detailsVO;
				}
			}
		}
		return null;
	}

	public List<BuyerTemplateMapperVO> getBuyerTemplatesMap()
			throws BusinessServiceException {
		List<BuyerTemplateMapperVO> buyerTemplateMapper = null;
		try {
			buyerTemplateMapper = mobileSOActionsDao.getBuyerTemplatesMap();
		} catch (Exception ex) {
			logger.error("Exception Occured in MobileSOActionsBOImpl-->getBuyerTemplatesMap()"
					+ ex);
			throw new BusinessServiceException(ex);
		}
		return buyerTemplateMapper;
	}

	public List<TemplateDetailsVO> getAllTemplates(
			List<Integer> templateIds) throws BusinessServiceException {
		List<TemplateDetailsVO> templates = null;
		try {
			templates = mobileSOActionsDao.getAllTemplates(templateIds);
		} catch (Exception ex) {
			logger.error("Exception Occured in MobileSOActionsBOImpl-->getBuyerTemplatesMap()"
					+ ex);
			throw new BusinessServiceException(ex);
		}
		return templates;
	}
	
	private Map<String, Object> getCustomerEmailDetails(String customerName, String soId) {
		Map<String, Object> customerEmailDetails = new HashMap<String, Object>();
		try {
			customerEmailDetails = mobileSOActionsDao.getCustomerEmailDetails(soId);
		} catch (Exception ex) {
			logger.error("Exception Occured in MobileSOActionsBOImpl-->getCustomerEmailDetails()" + ex);
		}
		if(StringUtils.isNotBlank(customerName)){
			customerEmailDetails.put("customerName", customerName);
		}else{
			customerEmailDetails.put("customerName", "Customer");
		}
		customerEmailDetails.put("soId", soId);
		return customerEmailDetails;
	}

	@SuppressWarnings("unused")
	private StringBuilder prepareEmailContent(String customerName, String soId){
		StringBuilder content = new StringBuilder(MPConstants.MOBILE_PDF_EMAIL_CONTENT_TEXT1);
		
		if(StringUtils.isNotBlank(customerName)){
			content.append(customerName);
		} else {
			content.append(MPConstants.MOBILE_PDF_EMAIL_CONTENT_TEXT2);
		}
		content.append(MPConstants.MOBILE_PDF_EMAIL_CONTENT_TEXT3);
		content.append(MPConstants.MOBILE_PDF_EMAIL_CONTENT_TEXT4);
		if(StringUtils.isNotBlank(soId)){
			content.append(" ").append(soId);
		}
		content.append(MPConstants.MOBILE_PDF_EMAIL_CONTENT_TEXT5);
		content.append(MPConstants.MOBILE_PDF_EMAIL_CONTENT_TEXT6);
		
		return content;
	}

	/**
	 * Method for inserting generated soPdf as service order associate document.
	 * 
	 * @param byteArrayOutputStream
	 *            ,signature
	 * @throws BusinessServiceException
	 */
	public void insertServiceOrderDocument(
			ByteArrayOutputStream byteArrayOutputStream, Signature signature)
			throws BusinessServiceException {
		ProcessResponse processResponse = new ProcessResponse();
		try {

			logger.debug("insertServiceOrderDocument");
			ProviderHistoryVO hisVo = new ProviderHistoryVO();
			hisVo = mobileSOActionsDao.getResourceFirmData(signature.getSoId());
			// setting the document data
			DocumentVO documentVO = new DocumentVO();
			documentVO.setTitle(MPConstants.PDF_TITLE);
			documentVO.setSoId(signature.getSoId());
			documentVO.setDocCategory(MPConstants.DOC_CATEGORY_ID);
			String soId = signature.getSoId();
			documentVO
					.setFileName(soId+ MPConstants.PDF_FORMAT);
			documentVO.setDescription(MPConstants.PDF_TITLE);
			documentVO.setFormat(MPConstants.FILE_FORMAT);
			// setting accepted resource id
			documentVO.setEntityId(hisVo.getEnitityId());
			documentVO.setRoleId(MPConstants.PROVIDER_ROLE);
			documentVO.setBlobBytes(byteArrayOutputStream.toByteArray());
			// setting firmID
			documentVO.setCompanyId(hisVo.getRoleId());
			documentVO.setDocSize(new Long(
					byteArrayOutputStream.toByteArray().length));
			logger.debug("befor calling insert");
			processResponse = documentBO.insertServiceOrderDocument(documentVO);

			if (null != processResponse) {
				// if document upload is not allowed in the current SO status
				if (OrderConstants.SO_DOC_NOT_IN_ALLOWED_STATE_ERROR_RC
						.equals(processResponse.getCode())) {
					throw new BusinessServiceException(
							MPConstants.MOBILE_PDF_INVALID_SO_STATUS);
				}
				
				if (!ServiceConstants.VALID_RC
						.equals(processResponse.getCode())) {
					throw new BusinessServiceException(
							MPConstants.MOBILE_PDF_DUPLICATE_ENTRY);
				}
			} else {
				throw new BusinessServiceException(
						MPConstants.MOBILE_PDF_INVALID_FIRM_ASSOC);
			}
		} catch (Exception e) {
			logger.error("Exception occured in insertServiceOrderDocument() due to "
					+ e.getMessage());
			throw new BusinessServiceException(e);
		}

	}
	
	
	public String getPropertyFromDB(String appKey) throws BusinessServiceException {
		try {
			return mobileSOActionsDao.getPropertyFromDB(appKey);
		} catch (DataServiceException dse) {
			logger.error("Exception in fetching data " + dse.getMessage(), dse);
			throw new BusinessServiceException(
					"Exception in getting app key value due to"
							+ dse.getMessage(), dse);
		}	
	}

	public static File createFile(String fileName, String fileDir) {
		File outputFile = null;
		try {
			boolean success = new File(fileDir).mkdir();
			outputFile = new File(fileDir + fileName);
		} catch (Exception e) {
			logger.error("Exception in createFile" + e);
		}

		return outputFile;
	}
	
	private boolean delelteFile(String filePath) throws BusinessServiceException {
		File fileToDelete = null;
		try{
			fileToDelete = new File(filePath);
			if(fileToDelete.exists()){
				return fileToDelete.delete();
			}
		}catch (Exception e) {
			logger.error(e);
			throw new BusinessServiceException(e);
		}		
		return false;
	}

	public IMobileSOActionsDao getMobileSOActionsDao() {
		return mobileSOActionsDao;
	}

	public void setMobileSOActionsDao(IMobileSOActionsDao mobileSOActionsDao) {
		this.mobileSOActionsDao = mobileSOActionsDao;
	}

	public DocumentBO getDocumentBO() {
		return documentBO;
	}

	public void setDocumentBO(DocumentBO documentBO) {
		this.documentBO = documentBO;
	}

	public IServiceOrderBO getServiceOrderBOTarget() {
		return serviceOrderBOTarget;
	}

	public void setServiceOrderBOTarget(IServiceOrderBO serviceOrderBOTarget) {
		this.serviceOrderBOTarget = serviceOrderBOTarget;
	}

	public IMobileSOManagementBO getMobileSOManagementBO() {
		return mobileSOManagementBO;
	}

	public void setMobileSOManagementBO(IMobileSOManagementBO mobileSOManagementBO) {
		this.mobileSOManagementBO = mobileSOManagementBO;
	}

}
