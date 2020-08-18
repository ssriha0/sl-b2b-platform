package com.newco.batch.MobileSoPdfGenerationBatch;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.batch.ABatchProcess;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.Signature;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.vo.mobile.BuyerTemplateMapperVO;
import com.newco.marketplace.vo.mobile.TemplateDetailsVO;
import com.servicelive.mobileSoPdfGenerationBatch.services.IMobileSoPdfGenerationService;

public class MobileSoPdfGenerationProcessor extends ABatchProcess {

	private static final Logger logger = Logger
			.getLogger(MobileSoPdfGenerationProcessor.class);
	private IMobileSoPdfGenerationService mobileSoPdfGenerationService;

	Signature signature = new Signature();
	List<TemplateDetailsVO> templates;
	HashMap<Integer, BuyerTemplateMapperVO> buyerTemplates;
	String TRUE = "true";

	@Override
	public int execute() {
		try {
			logger.info("Entered in execute() method of MobileSoPdfGenerationProcessor");
			process();
			logger.info("Leaving execute() method of MobileSoPdfGenerationProcessor");
		} catch (Exception ex) {
			logger.error(
					"Unexpected exception occurred IN MobileSoPdfGenerationProcessor!",
					ex);
		}
		return 0;
	}

	public void process() {
		logger.info("Entered process()method of MobileSoPdfGenerationProcessor");
		List<Signature> signatureList = new ArrayList<Signature>();
		List<Signature> orderSignatureList = new ArrayList<Signature>();
		String limitOfOrdersFromDB = null;
		String retryCountFromDB = null;
		String emailSwitch = null;
		
		try {
			
			limitOfOrdersFromDB = mobileSoPdfGenerationService.getPropertyFromDB(MPConstants.SIGNATURE_NO_OF_ORDERS);
			retryCountFromDB = mobileSoPdfGenerationService.getPropertyFromDB(MPConstants.SIGNATURE_MAX_NO_OF_RETRY);
			emailSwitch = mobileSoPdfGenerationService.getPropertyFromDB(MPConstants.DIGITAL_SIGNATURE_EMAIL_SWITCH);
			if(StringUtils.isBlank(limitOfOrdersFromDB)){
				limitOfOrdersFromDB = MPConstants.SIGNATURE_NO_OF_ORDERS_DEFAULT; // Default
			}
			if(StringUtils.isBlank(retryCountFromDB)){
				retryCountFromDB = MPConstants.SIGNATURE_MAX_NO_OF_RETRY_DEFAULT; // Default
			}
			
			// getting customer and provider signature associated with orders
			signatureList = mobileSoPdfGenerationService.getSignatureDetails(limitOfOrdersFromDB);
			// variable for comparing two so's to check that if it is already
			// processed
			String compareSo = "";
			if (null != signatureList && signatureList.size() != 0) {
				
				// Procedure to add a new buyer template.
				// 1. Insert the template in the template table.
				// 2. Map the new template_id with the buyer_id in the buyer_email_template_mapping table.
				//	  From email id for the new buyer should also be inserted in this table.
				List<BuyerTemplateMapperVO> buyerTemplateMapper = mobileSoPdfGenerationService.getBuyerTemplatesMap();
				buyerTemplates = new HashMap<Integer, BuyerTemplateMapperVO>();
				HashMap<Integer, Integer> buyerTemplateMap = new HashMap<Integer, Integer>();
				if(null != buyerTemplateMapper && !buyerTemplateMapper.isEmpty()){
					for(BuyerTemplateMapperVO buyerTemplate : buyerTemplateMapper){
						buyerTemplates.put(buyerTemplate.getBuyerId(), buyerTemplate);
						buyerTemplateMap.put(buyerTemplate.getBuyerId(), buyerTemplate.getTemplateId());
					}
				}
				List<Integer> templateIds = new ArrayList<Integer>(buyerTemplateMap.values());
				templates = mobileSoPdfGenerationService.getAllTemplates(templateIds);
				// loop for getting the first signature
				for (Signature sign1 : signatureList) {
					/*
					 * setting signature obtained into global variable for
					 * accessing from every where in the class
					 */
					setSignature(sign1);
					// if order is already processed inside the logic continue
					// with next order
					if (compareSo.equals(sign1.getSoId())) {
						continue;
					}
					/*
					 * checking that if order is completed or not, if it is not
					 * completed then continue with next order
					 */
					/*
					 * Removing the check since there could be cases where the
					 * provider got both the signatures but not yet completed
					 * the order. So for these orders if the provider tries to
					 * complete through the front end, they need to attach
					 * another document to complete it.
					 */
					/*if (!mobileSoPdfGenerationService.isOrderCompleted(sign1
							.getSoId())) {
						logger.info(" not completed" + sign1.getSoId());
						compareSo = sign1.getSoId();
						// if it is not completed yet setting the status as
						// error and logging the message
						updateError(sign1.getSoId(), sign1.getRetryCount(),
								MPConstants.ORDER_NOT_COMPLETED);
						continue;
					}*/
					// Variable to check that if an order has both provider and
					// customer signature
					int count = 0;
					orderSignatureList.clear();
					/*
					 * setSignature(sign1); loop for getting the second
					 * signature
					 */
					for (Signature sign2 : signatureList) {
						if (StringUtils
								.equals(sign1.getSoId(), sign2.getSoId())) {
							compareSo = sign2.getSoId();
							/*
							 * setSignature(sign2); setting the signature object
							 * into new list
							 */
							orderSignatureList.add(sign2);
							count = count + 1;
							/*
							 * if both the signatures are present for the order
							 * then validating it and calling generate pdf
							 * method
							 */
							if (count == 2) {
								if (validate(orderSignatureList,retryCountFromDB)) {
									try{
										generatePdfForSO(orderSignatureList, emailSwitch);
									}catch (BusinessServiceException e) {
											logger.error("Exception in Processs() method of MobileSoPdfGenerationProcessor :"
													+ e);
											// updating the status of service order as error if any exception is
											// caught
											if (null != signatureList && signatureList.size() != 0) {
												updateError(orderSignatureList.get(0).getSoId(), orderSignatureList.get(0)
														.getRetryCount(), e.toString(),retryCountFromDB);
											}
									}
								}

								break;
							}
						}

					}
					// if processed order only has one entry then updating the
					// status as error
					if (count == 1) {
						updateError(
								sign1.getSoId(),
								sign1.getRetryCount(),
								"Service order only have "
										+ sign1.getResourceInd()
										+ " Signature.",retryCountFromDB);
					}

				}
			}

		} catch (Exception e) {
			logger.error("Exception in Processs() method of MobileSoPdfGenerationProcessor :"
					+ e);
			// updating the status of service order as error if any exception is
			// caught
			/*if (null != signatureList && signatureList.size() != 0) {
				updateError(getSignature().getSoId(), getSignature()
						.getRetryCount(), e.toString(),retryCountFromDB);
			}*/

		}
	}

	/**
	 * Method for generating pdf and inserting it as document and sending
	 * attached mail to customer
	 * 
	 * */
	public void generatePdfForSO(List<Signature> orderSignatureList, String emailSwitch)
			throws BusinessServiceException {
		boolean customerCopyOnlyInd = false;
		Signature sign = new Signature();
		ByteArrayOutputStream custProvCopy = new ByteArrayOutputStream();
		ByteArrayOutputStream custCopy = new ByteArrayOutputStream();
		try {
			for (Signature signature : orderSignatureList) {
				if (MPConstants.CUSTOMER.equals(signature.getResourceInd())) {
					sign = signature;
					break;
				}
			}
			// setting status as in progress
			mobileSoPdfGenerationService.updateSignature(sign,
					MPConstants.PDF_STATUS_INPROGRESS);
			// getting the byte array of generated service order pdf.
			logger.info("after setPdfStatus in process");
			custProvCopy = mobileSoPdfGenerationService
					.generatePdf(orderSignatureList, customerCopyOnlyInd);
			/*
			 * inserting generated service order pdf into document table and
			 * associating it with so
			 */		
			mobileSoPdfGenerationService
					.insertServiceOrderDocument(custProvCopy, sign);
			// sending the pdf as attached to the customer only if
			// 1. Customer email present
			// 2. Email switch is ON
			// 3. Email sent ind is 0 (SLM-117)
			
			if (null != sign.getCustomerEmail()
					&& StringUtils.isNotBlank(sign.getCustomerEmail()) && 
					StringUtils.equalsIgnoreCase(TRUE, emailSwitch) &&
					(null != sign.getEmailSentInd() && 0 == sign.getEmailSentInd().intValue())) {
				
				// TODO SLM-118 : Add new method to generate the receipt				
				customerCopyOnlyInd = true;
				// Separate pdf need to be generated consist of only customer copy to send mail to customer. 
				custCopy = mobileSoPdfGenerationService
						.generatePdf(orderSignatureList, customerCopyOnlyInd);				
				
				// TODO - SLM-118 No need to delete the attachment. We need to keep the attachment for 
				// legal purposes
				mobileSoPdfGenerationService.sendAttachmentMail(custCopy, sign, buyerTemplates, templates);
				
				// SLM-117 : Update the email_sent_ind to 1
				sign.setEmailSentInd(1);
				mobileSoPdfGenerationService.updateSignatureEmailSent(sign);				
				logger.info("AFTER MAIL SEND");
			}
			// setting status as completed so that job will not catch it again
			sign.setErrorReason("");
			mobileSoPdfGenerationService.updateSignature(sign,
					MPConstants.PDF_STATUS_COMPLETED);

		} catch (BusinessServiceException e) {
			logger.error("Exception in generatePdfForSO() method of MobileSoPdfGenerationProcessor :"
					+ e);
			throw new BusinessServiceException(e);
		}

	}

	/**
	 * Method for setting status as ERROR and incrementing the no of retries
	 * count so that if count exceeds the limit ,setting the status as Failure
	 * so that job will not try it in the future and logging the reason of
	 * failure or error
	 * 
	 * @param sign
	 * @param exception
	 */
	public void updateError(String soId, Integer retryCount, String exception,String retryCountFromDB) {
		Signature sign = new Signature();
		sign.setSoId(soId);
		if (null == retryCount) {
			retryCount = -1;
		}
		sign.setRetryCount(retryCount);
		sign.setPdfStatus(MPConstants.PDF_STATUS_ERROR);
		sign.setErrorReason(exception);
		Integer maxCount = Integer.parseInt(retryCountFromDB);
		try {
			if (retryCount >= maxCount) {
				mobileSoPdfGenerationService.updateSignature(sign,
						MPConstants.PDF_STATUS_FAILURE);
			} else {
				sign.setRetryCount(retryCount + 1);
				mobileSoPdfGenerationService.updateSignature(sign,
						MPConstants.PDF_STATUS_ERROR);
			}
		} catch (Exception e) {
			logger.error("Exception in updateError() method of MobileSoPdfGenerationProcessor :"
					+ e);
		}
	}

	/**
	 * 
	 * @param orderSignatureList
	 * @return
	 * @throws BusinessServiceException
	 */
	public boolean validate(List<Signature> orderSignatureList,String retryCountFromDB)
			throws BusinessServiceException {
		boolean isValid = true;
		Signature sign1 = null;
		Signature sign2 = null;
		
		try {
			sign1 = orderSignatureList.get(0);
			sign2 = orderSignatureList.get(1);

			if (null != sign1.getResourceInd()
					&& null != sign2.getResourceInd()
					&& StringUtils.isNotBlank(sign1.getResourceInd())
					&& StringUtils.isNotBlank(sign2.getResourceInd())) {
				if (StringUtils.equals(sign1.getResourceInd(),
						sign2.getResourceInd())) {
					isValid = false;
					updateError(
							sign1.getSoId(),
							sign1.getRetryCount(),
							"Service order  have multiple entries of "
									+ sign1.getResourceInd() + " Signature.",retryCountFromDB);
				}
			}
			if (!MPConstants.SIGNATURE_DOC_FORMAT.equals(sign1
					.getDocCategoryId())) {
				isValid = false;
				updateError(sign1.getSoId(), sign1.getRetryCount(),
						sign1.getResourceInd()
								+ MPConstants.SIGNATURE_INVALID_FORMAT,retryCountFromDB);
			} else if (!MPConstants.SIGNATURE_DOC_FORMAT.equals(sign2
					.getDocCategoryId())) {
				isValid = false;
				updateError(sign2.getSoId(), sign2.getRetryCount(),
						sign2.getResourceInd()
								+ MPConstants.SIGNATURE_INVALID_FORMAT,retryCountFromDB);
			}

		} catch (Exception e) {
			logger.error("Exception in validate() method of MobileSoPdfGenerationProcessor :"
					+ e);
			throw new BusinessServiceException(e);
		}

		return isValid;
	}

	public IMobileSoPdfGenerationService getMobileSoPdfGenerationService() {
		return mobileSoPdfGenerationService;
	}

	public void setMobileSoPdfGenerationService(
			IMobileSoPdfGenerationService mobileSoPdfGenerationService) {
		this.mobileSoPdfGenerationService = mobileSoPdfGenerationService;
	}

	public Signature getSignature() {
		return signature;
	}

	public void setSignature(Signature signature) {
		this.signature = signature;
	}
	
	public List<TemplateDetailsVO> getTemplates() {
		return templates;
	}

	public void setTemplates(List<TemplateDetailsVO> templates) {
		this.templates = templates;
	}

	public HashMap<Integer, BuyerTemplateMapperVO> getBuyerTemplates() {
		return buyerTemplates;
	}

	public void setBuyerTemplates(HashMap<Integer, BuyerTemplateMapperVO> buyerTemplates) {
		this.buyerTemplates = buyerTemplates;
	}

	@Override
	public void setArgs(String[] args) {

	}
	

}
