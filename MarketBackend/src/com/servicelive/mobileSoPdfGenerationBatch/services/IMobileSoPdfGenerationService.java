package com.servicelive.mobileSoPdfGenerationBatch.services;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;

import com.newco.marketplace.api.mobile.beans.updateSoCompletion.Signature;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.vo.mobile.BuyerTemplateMapperVO;
import com.newco.marketplace.vo.mobile.TemplateDetailsVO;


public interface IMobileSoPdfGenerationService{
	
	/**
	 * 
	 * to get the signature details of 10 service orders
	 *
	 * @throws BusinessServiceException
	 */
	public List<Signature> getSignatureDetails(String limit) throws BusinessServiceException;
	/**
	 * 
	 * to update the signature pdf generation status of service order
	 *
	 * @throws BusinessServiceException
	 */
	public void updateSignature(Signature signature,String updateStatus) throws BusinessServiceException;
	/**	  
	 * to check if order is completed
	 * @param soId
	 * @throws BusinessServiceException
	 */
	public boolean isOrderCompleted(String soId) throws BusinessServiceException;
	/**	  
	 * to generate service order as pdf with uploaded signatures
	 * @param signature
	 * @throws BusinessServiceException
	 */
	public ByteArrayOutputStream generatePdf(List<Signature>signatureList, boolean customerCopyOnlyInd) throws BusinessServiceException;

	/**
	 * Method for sending mail to customer with generated so pdf as attachment
	 * 
	 * @param byteArrayOutputStream
	 * @param templates 
	 * @param buyerTemplates 
	 * @throws BusinessServiceException
	 */
	public void sendAttachmentMail(ByteArrayOutputStream byteArrayOutputStream,
			Signature signature, HashMap<Integer, BuyerTemplateMapperVO> buyerTemplates, List<TemplateDetailsVO> templates) throws BusinessServiceException;
	/**
	 * Method for inserting generated soPdf as service order associate document.
	 * 
	 * @param byteArrayOutputStream,signature
	 * @throws BusinessServiceException
	 */
	public void insertServiceOrderDocument(ByteArrayOutputStream byteArrayOutputStream,
			Signature signature) throws BusinessServiceException ;
	
	
	/**
	 * @param appKey
	 * @return
	 * @throws BusinessServiceException
	 */
	public String getPropertyFromDB(String appKey)	throws BusinessServiceException;
	
	/**
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<BuyerTemplateMapperVO> getBuyerTemplatesMap() throws BusinessServiceException;
	
	/**
	 * @param templateIds
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<TemplateDetailsVO> getAllTemplates(List<Integer> templateIds) throws BusinessServiceException;
	
	/**
	 * to update the email_sent_ind 
	 * @throws BusinessServiceException
	 */
	public void updateSignatureEmailSent(Signature signature) throws BusinessServiceException;
	
}
