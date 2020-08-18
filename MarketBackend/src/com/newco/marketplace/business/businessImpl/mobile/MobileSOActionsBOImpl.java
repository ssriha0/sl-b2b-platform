package com.newco.marketplace.business.businessImpl.mobile;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.mobile.beans.sodetails.PermitAddon;
import com.newco.marketplace.api.mobile.beans.sodetails.PermitAddons;
import com.newco.marketplace.api.mobile.beans.sodetails.PermitTask;
import com.newco.marketplace.api.mobile.beans.sodetails.PermitTasks;
import com.newco.marketplace.api.mobile.beans.sodetails.Permits;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.AddOn;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.AddOnPaymentDetails;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.AddOnPaymentHistoryDetails;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.HSCreditCardResponse;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.Part;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.PartTracking;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.Pricing;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.Signature;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.Task;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.UpdateSOCompletionRequest;
import com.newco.marketplace.business.iBusiness.mobile.IMobileSOActionsBO;
import com.newco.marketplace.dto.vo.CryptographyVO;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.buyer.BuyerReferenceVO;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderNote;
import com.newco.marketplace.dto.vo.serviceorder.SoTripVo;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.inhomeoutboundnotification.service.INotificationService;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.persistence.iDao.ledger.ICreditCardDao;
import com.newco.marketplace.persistence.iDao.mobile.IMobileSOAccountActionsDao;
import com.newco.marketplace.persistence.iDao.mobile.IMobileSOActionsDao;
import com.newco.marketplace.persistence.iDao.security.SecurityDAO;
import com.newco.marketplace.tokenize.HSTokenizeServiceCreditCardBO;
import com.newco.marketplace.tokenize.TokenizeResponse;
import com.newco.marketplace.util.Cryptography;
import com.newco.marketplace.util.Cryptography128;
import com.newco.marketplace.util.EsapiUtility;
import com.newco.marketplace.utils.MoneyUtil;
import com.newco.marketplace.vo.mobile.CustomReferenceVO;
import com.newco.marketplace.vo.mobile.InvoicePartsVO;
import com.newco.marketplace.vo.mobile.PartsVO;
import com.newco.marketplace.vo.mobile.ProviderHistoryVO;
import com.newco.marketplace.vo.mobile.SOPartLaborPriceReasonVO;
import com.newco.marketplace.vo.mobile.TaskVO;
import com.newco.marketplace.vo.mobile.UpdateApptTimeVO;
import com.newco.marketplace.vo.mobile.v2_0.LpnVO;
import com.newco.marketplace.vo.mobile.v2_0.SORevisitNeededVO;
import com.newco.marketplace.vo.mobile.v2_0.SOTripDetailsVO;
import com.newco.marketplace.vo.mobile.v2_0.SupplierVO;
import com.servicelive.common.CommonConstants;


public class MobileSOActionsBOImpl implements IMobileSOActionsBO{
	private Logger logger = Logger.getLogger(MobileSOActionsBOImpl.class);
	private IMobileSOActionsDao mobileSOActionsDao;
	private IMobileSOAccountActionsDao accountActionsDao;
	private SecurityDAO securityDao;
	private Cryptography cryptography;
	private Cryptography128 cryptography128;
	//PCI II
	private HSTokenizeServiceCreditCardBO hsTokenServiceCreditCardBo;
	//SL 20853 Added to provide encryption for additional payment based on flag
	private ICreditCardDao creditCardDao;
	
	
	
	//R12.0 SLM-5 :Notification service for entry in NPS 
	protected INotificationService notificationService;
	
	
	public INotificationService getNotificationService() {
		return notificationService;
	}

	public void setNotificationService(INotificationService notificationService) {
		this.notificationService = notificationService;
	}

	public Cryptography128 getCryptography128() {
		return cryptography128;
	}

	public void setCryptography128(Cryptography128 cryptography128) {
		this.cryptography128 = cryptography128;
	}

	public Cryptography getCryptography() {
		return cryptography;
	}

	public void setCryptography(Cryptography cryptography) {
		this.cryptography = cryptography;
	}

	//To validate resourceId
	public Integer validateResourceId(Integer resourceId) throws BusinessServiceException {
		Integer resId = 0;
		try {
			resId = mobileSOActionsDao.validateResourceId(resourceId);
		} catch (Exception ex) {
			logger.error("Exception Occured in MobileSOActionsBOImpl-->validateResourceId()");
			throw new BusinessServiceException(ex.getMessage());
		}
		return resId;
	}

	//To validate soId
	public ServiceOrder getServiceOrder(String soId) throws BusinessServiceException {
		ServiceOrder so= new ServiceOrder();
		try {
			so = mobileSOActionsDao.getServiceOrder(soId);
		} catch (Exception ex) {
			logger.error("Exception Occured in MobileSOActionsBOImpl-->getServiceOrder()");
			throw new BusinessServiceException(ex.getMessage());
		}
		return so;
	}

	//To validate the association of soId and providerId
	public String toValidateSoIdForProvider(Map<String, String> validateMap) throws BusinessServiceException {
		String soId="";
		try {
			soId = mobileSOActionsDao.toValidateSoIdForProvider(validateMap);
		} catch (Exception ex) {
			logger.error("Exception Occured in MobileSOActionsBOImpl-->getServiceOrder()");
			throw new BusinessServiceException(ex.getMessage());
		}
		return soId;
	}

	//To insert Note into so_notes 
	public void insertAddNote(Integer resourceId, Integer roleId, String soId,
			String subject, String message, Integer noteTypeId,
			String createdByName, String modifiedBy, Integer entityId,
			String privateInd) throws BusinessServiceException {
		//Added Code for ESAPI encoding
		subject = EsapiUtility.getEncodedString(subject);
		message = EsapiUtility.getEncodedString(message);
		
		ServiceOrderNote note = new ServiceOrderNote();
		note.setSoId(soId);
		note.setNoteTypeId(noteTypeId);
		note.setSubject(subject);
		note.setNote(message);
		note.setRoleId(roleId);
		if (StringUtils.isEmpty(privateInd) || !StringUtils.isNumeric(privateInd)) {
			privateInd = "0";
		}
		note.setPrivateId(Integer.parseInt(privateInd));
		note.setCreatedByName(createdByName);
		note.setModifiedBy(modifiedBy);
		note.setEntityId(entityId);
		Date d=new Date(System.currentTimeMillis());
		Timestamp createdDate = new Timestamp(d.getTime());
		note.setCreatedDate(createdDate);
		note.setModifiedDate(createdDate);
		try {
			mobileSOActionsDao.insertAddNote(note);
		} catch (Exception ex) {
			logger.error("Exception Occured in MobileSOActionsBOImpl-->insertAddNote()");
			throw new BusinessServiceException(ex.getMessage());
		}
		
	}
	public void historyLogging(ProviderHistoryVO hisVO) throws BusinessServiceException{
		try {
			
			mobileSOActionsDao.insertAddNoteHistory(hisVO);
		} catch (Exception ex) {
			logger.error("Exception Occured in MobileSOActionsBOImpl-->insertAddNoteHistory()");
			throw new BusinessServiceException(ex.getMessage());
		}
	}


	/**
	 * 
	 * to get the buyer id and wfStateId of service order
	 * 
	 * @param soId
	 * @return
	 * @throws BusinessServiceException
	 */
	//R12.0 SLM-5:method updated for getting wfStateId for NPS notification
	public HashMap<String, Object> getBuyerAndWfStateForSO(String soId)
			throws BusinessServiceException {
		// TODO Auto-generated method stub
		HashMap<String, Object>map = new HashMap<String, Object>();
		try{
			
			map = mobileSOActionsDao.getBuyerAndWfStateForSO(soId);
			
		}
		catch(DataServiceException e){
			logger.error("Exception occured in getBuyerForServiceOrder() due to "+e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return map;
	}

	/**
	 * 
	 * to check whether the SO is assigned at firm level
	 * 
	 * @param soId
	 * @return
	 */public boolean checkIfAssignedToFirm(String soId) throws BusinessServiceException {
			// TODO Auto-generated method stub
		 	boolean assignedToFirmInd =  Boolean.FALSE;
			try{
				
				assignedToFirmInd = mobileSOActionsDao.checkIfAssignedToFirm(soId);
				
			}
			catch(DataServiceException e){
				logger.error("Exception occured in checkIfAssignedToFirm() due to "+e.getMessage());
				throw new BusinessServiceException(e.getMessage());
			}
			return assignedToFirmInd;
		}


	 /**
		 * Get credit card type id
		 * @param ccType
		 * @return
		 * @throws BusinessServiceException
		 */
	 public Integer getCardType(String ccType) throws BusinessServiceException {
			// TODO Auto-generated method stub
			Integer creditCardTypeId =  null;
			try{
				
				creditCardTypeId = mobileSOActionsDao.getCardType(ccType);
				
			}
			catch(DataServiceException e){
				logger.error("Exception occured in getCardType() due to "+e.getMessage());
				throw new BusinessServiceException(e.getMessage());
			}
			return creditCardTypeId;
		}
	 
	 
	public IMobileSOActionsDao getMobileSOActionsDao() {
		return mobileSOActionsDao;
	}

	public void setMobileSOActionsDao(IMobileSOActionsDao mobileSOActionsDao) {
		this.mobileSOActionsDao = mobileSOActionsDao;
	}

	public IMobileSOAccountActionsDao getAccountActionsDao() {
		return accountActionsDao;
	}

	public void setAccountActionsDao(IMobileSOAccountActionsDao accountActionsDao) {
		this.accountActionsDao = accountActionsDao;
	}


	/**
	 * @return the securityDao
	 */
	public SecurityDAO getSecurityDao() {
		return securityDao;
	}

	/**
	 * @param securityDao the securityDao to set
	 */
	public void setSecurityDao(SecurityDAO securityDao) {
		this.securityDao = securityDao;
	}

	/**
	 * 
	 * to get mandatory custom references
	 * 
	 * @param soId
	 * 
	 * 
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<BuyerReferenceVO> getRequiredBuyerReferences(String soId, boolean mandatoryInd)
			throws BusinessServiceException {
		// TODO Auto-generated method stub
		List<BuyerReferenceVO> mandatoryReferences =  null;
		try{
			
			mandatoryReferences = mobileSOActionsDao.getRequiredBuyerReferences(soId,mandatoryInd);
			
		}
		catch(DataServiceException e){
			logger.error("Exception occured in getRequiredBuyerReferences() due to "+e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return mandatoryReferences;
	}

	/**
	 * @param updateSOCompletionRequest
	 * @param serviceOrder 
	 * @return
	 */
	public UpdateSOCompletionRequest preProcessUpdateSOCompletion(
			UpdateSOCompletionRequest updateSOCompletionRequest) {
		// TODO Auto-generated method stub
		 String autoClose="";

	        if(MPConstants.SERVICE_PARTIAL.equals(updateSOCompletionRequest.getCompletionStatus()))
	        {
	        	 updateSOCompletionRequest.setAddOnPaymentDetails(null);
	        	//serviceOrder.setAdditionalPayment(null);
	        	if(updateSOCompletionRequest.getAddOns()!=null && updateSOCompletionRequest.getAddOns().getAddOn()!=null && updateSOCompletionRequest.getAddOns().getAddOn().size()>0)
	        	{
	        		for (AddOn addOn:updateSOCompletionRequest.getAddOns().getAddOn()) {

	        			addOn.setQty(0);
	        		}
	        	}
	        }
	        return updateSOCompletionRequest;
	        
	        
	}

	/**
	 * @param partCoverage
	 * @param partSource
	 * @return
	 * @throws BusinessServiceException 
	 */
	public HashMap<String, Double> getReimbursementRate(String partCoverage, String partSource) throws BusinessServiceException {
		// TODO Auto-generated method stub
		HashMap<String, Double> buyerPriceCalcValues = new HashMap<String, Double>();
		try{
			
			buyerPriceCalcValues = mobileSOActionsDao.getReimbursementRate(partCoverage,partSource);
			
		}
		catch(DataServiceException e){
			logger.error("Exception occured in getReimbursementRate() due to "+e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return buyerPriceCalcValues;
	}
	
	public void updateSoSubStatus(String soId, Integer subStatus) throws BusinessServiceException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("soid", soId);
		map.put("subStatusid", subStatus);
		try{
			mobileSOActionsDao.updateSoSubStatus(map);
		}
		catch(DataServiceException e){
			logger.error("Exception occured in getReimbursementRate() due to "+e);
			throw new BusinessServiceException(e.getMessage());
		}
	}
   
	/* 
	 * R12.0 SLM-5 : method modified for inserting entry in NPS notification table
	 */
	public void updateSoCompletion(String soId, Integer subStatus,
			String comments,Integer buyerId,Integer wfStateId) throws BusinessServiceException {
		try {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("soid", soId);
			map.put("subStatusid", subStatus);

			mobileSOActionsDao.updateSoSubStatus(map);
			boolean isEligibleForNPSNotification=false;
			
	   			//call validation method for NPS notification
				try {
					isEligibleForNPSNotification = notificationService.validateNPSNotificationEligibility(buyerId,soId);
				} 
				catch (BusinessServiceException e) {
					logger.info("Exception in validatiing nps notification eligibility"+ e.getMessage());
				}
				if(isEligibleForNPSNotification){
					//Call Insertion method for NPS notification
					try {
						notificationService.insertNotification(soId,wfStateId,subStatus,null);
					 }catch (BusinessServiceException e){
						logger.info("Caught Exception while insertNotification",e);				
						}
				}
			//Added code for ESAPI encoding
			comments = EsapiUtility.getEncodedString(comments);

			if (null != comments && StringUtils.isNotBlank(comments)) {
				map.put("comments", comments);

				mobileSOActionsDao.updateSoResolutionComments(map);

			}
		} catch (Exception ex) {
			logger.error("Exception Occured in MobileSOActionsBOImpl-->updateSoSubStatus()");
			throw new BusinessServiceException(ex.getMessage());
		}
	}
  
   /**
         * @param addOnList
         * @return
         */
 public void updateAddons(List<AddOn> addOnList,String soId,  String userName)throws BusinessServiceException{
	 Integer addOnCount = null;
     try{   
    	 List<AddOn> addOnMiscInds = new ArrayList<AddOn>();
    			
    			addOnMiscInds = mobileSOActionsDao.getAddOnMiscInds(addOnList);
    			
         for(AddOn addon :addOnList){
        
             if(null!=addon&&StringUtils.isNotBlank(addon.getAddOnSKU())){

            	 if(addOnMiscInds!=null && !addOnMiscInds.isEmpty()){
            		for(AddOn addOn2 : addOnMiscInds){
            			if(addOn2!=null && addOn2.getMiscellInd()!=null && addOn2.getSoAddonId()!=null){
            				if(addon.getSoAddonId()!=null && addon.getSoAddonId().intValue() == addOn2.getSoAddonId().intValue()){
                				if(addOn2.getMiscellInd()){
                					addon.setMiscInd("Y");
                				}
                				else{
                					addon.setMiscInd("N");
                				}
                				break;
                			}
            			}
            		
            		}
            	 }
            	 addon.setSoId(soId);
                 addon.setCreatedDate(new java.util.Date());
                 addon.setModifiedDate(new java.util.Date());
                 addon.setModifiedBy(userName);
                 mobileSOActionsDao.updateAddon(addon);
             }                       
         }
         //SL-20652: Code change to delete add on payment details when add on quantity is 0
         addOnCount= mobileSOActionsDao.getAddOnForSO(soId);
         if(null== addOnCount || (null!= addOnCount && addOnCount.intValue() == 0)){
        	 mobileSOActionsDao.deleteAdditionalPaymentDetails(soId);
         }
     }
     catch(DataServiceException e){
         logger.error("Exception occured in updateAddons() due to "+e.getMessage());
         throw new BusinessServiceException(e.getMessage());
     }       
 } 
 /**
  * @param paymentDetails
  * @return
  */
public boolean mapPaymentDetails(AddOnPaymentDetails paymentDetails,String userName)throws BusinessServiceException{

	boolean isTokenizeResponseValid = false;
	try {		
	/*	boolean isPresent=Boolean.FALSE;
		isPresent=mobileSOActionsDao.isAdditionalPaymentPresent(paymentDetails.getSoId());*/
		TokenizeResponse tokenizeResponse = null;
		String encryptFlag = null;
    	
		if(null!=paymentDetails){
			if(MPConstants.CREDIT_CARD.equalsIgnoreCase(paymentDetails.getPaymentType())){
			encryptFlag = creditCardDao.getEncryptFlag(CommonConstants.ADDITIONAL_PAYMENT_ENCRYPTION_FLAG);//checking if the paymenttype is credit card
        	try{
			tokenizeResponse = hsTokenServiceCreditCardBo.tokenizeHSSTransaction(paymentDetails.getCcNumber(),userName);
        	}catch (Exception e) {
				logger.info("Exception caugth in mobile for tokenization and ignoring");
				e.printStackTrace();
			}
        	if(null!=tokenizeResponse && null!= tokenizeResponse.getMaskedAccount() && null!= tokenizeResponse.getToken()){//returning error if tokenization response is invalid
        		isTokenizeResponseValid = true;
        	}else{
        		return isTokenizeResponseValid;
        	}
		}else{
			isTokenizeResponseValid = true; //Tokenization response not needed in case of cheque
		}
        	AddOnPaymentDetails addonpaymentDetails=new AddOnPaymentDetails();
		    //to insert history details
		    AddOnPaymentHistoryDetails addOnPaymentHistoryDetails = new AddOnPaymentHistoryDetails();
		    addonpaymentDetails.setAmountAuthorized(paymentDetails.getAmountAuthorized());
		    addonpaymentDetails.setSoId(paymentDetails.getSoId());
            /*if (paymentDetails.getPaymentType().equalsIgnoreCase(
				MPConstants.CREDIT_CARD)) {*/
			if(MPConstants.CREDIT_CARD.equalsIgnoreCase(paymentDetails.getPaymentType())){
			// to update as payment type
			if (StringUtils.isNotBlank(paymentDetails.getCcType())) {
				String paymentTypeAlias = mobileSOActionsDao
						.getCreditCardAlias(paymentDetails.getCcType());
				// this is to set as payment type in db
				addonpaymentDetails.setPaymentType(paymentTypeAlias);
				
				// to update as card type
			}
			if (StringUtils.isNotBlank(paymentDetails.getCcType())) {
				String cardType = mobileSOActionsDao.getCreditCardID(
						paymentDetails.getCcType()).toString();
				addonpaymentDetails.setCcType(cardType);
			
			}
			if (StringUtils.isNotBlank(paymentDetails.getCcNumber())) {
				//SL 20853 encrypting additional payment based on flag
				
				if(StringUtils.isNotBlank(encryptFlag) && encryptFlag.equalsIgnoreCase(CommonConstants.CC_ENCRYPTION_ON)){
					addonpaymentDetails	.setCcNumber(encryptCreditCardInfo(paymentDetails.getCcNumber()));
				}
				else {
					if(null!= tokenizeResponse && StringUtils.isBlank(tokenizeResponse.getToken())){
						addonpaymentDetails	.setCcNumber(encryptCreditCardInfo(paymentDetails.getCcNumber()));
					}else{
					  addonpaymentDetails.setCcNumber(null);
					}
				}

			}
			if (StringUtils.isNotBlank(paymentDetails.getExpirationDate())) { 
				String[] array = paymentDetails.getExpirationDate().split(
						"/");
				addonpaymentDetails.setMonth(new Integer(array[0]));
				addonpaymentDetails.setYear(new Integer(array[1]));
			}
			addonpaymentDetails.setPreAuthNumber(paymentDetails.getPreAuthNumber());
			//PCI-Vault II
			if(null!=tokenizeResponse){
				addonpaymentDetails.setMaskedAccNum(tokenizeResponse.getMaskedAccount());
				addonpaymentDetails.setToken(tokenizeResponse.getToken());
			}
			
			//insertion to history table
			
			addOnPaymentHistoryDetails.setAmountAuthorized(paymentDetails.getAmountAuthorized());
			addOnPaymentHistoryDetails.setSoId(paymentDetails.getSoId());
			
			if (StringUtils.isNotBlank(paymentDetails.getCcType())) {
				String paymentTypeAlias = mobileSOActionsDao
						.getCreditCardAlias(paymentDetails.getCcType());
				// this is to set as payment type in db
				addOnPaymentHistoryDetails.setPaymentType(paymentTypeAlias);
				
				// to update as card type
			}
			if (StringUtils.isNotBlank(paymentDetails.getCcType())) {
				String cardType = mobileSOActionsDao.getCreditCardID(
						paymentDetails.getCcType()).toString();
				addOnPaymentHistoryDetails.setCcType(cardType);
			
			}
			if (StringUtils.isNotBlank(paymentDetails.getCcNumber())) {
				//SL 20853 encrypting additional payment based on flag
				if(StringUtils.isNotBlank(encryptFlag) && encryptFlag.equalsIgnoreCase(CommonConstants.CC_ENCRYPTION_ON)){
				addOnPaymentHistoryDetails.setCcNumber(encryptCreditCardInfo(paymentDetails.getCcNumber()));
				}
				else {
					if(null!= tokenizeResponse && StringUtils.isBlank(tokenizeResponse.getToken())){
						addOnPaymentHistoryDetails	.setCcNumber(encryptCreditCardInfo(paymentDetails.getCcNumber()));
					}else{
						addOnPaymentHistoryDetails.setCcNumber(null);
					}
					
				}
						
			}	
			if (StringUtils.isNotBlank(paymentDetails.getExpirationDate())) {
				String[] array = paymentDetails.getExpirationDate().split(
						"/");
				addOnPaymentHistoryDetails.setMonth(new Integer(array[0]));
				addOnPaymentHistoryDetails.setYear(new Integer(array[1]));
			}
			addOnPaymentHistoryDetails.setPreAuthNumber(paymentDetails.getPreAuthNumber());
			if(null!=tokenizeResponse){
				addOnPaymentHistoryDetails.setMaskedAccNum(tokenizeResponse.getMaskedAccount());
				addOnPaymentHistoryDetails.setToken(tokenizeResponse.getToken());
				addOnPaymentHistoryDetails.setResponse(tokenizeResponse.getResponseXML());
			}
		} else {
			addonpaymentDetails.setCheckNumber(paymentDetails.getCheckNumber());
			addonpaymentDetails.setPaymentType(MPConstants.PAYMENT_TYPE_CHECK);
			//Updating History Details
			addOnPaymentHistoryDetails = new AddOnPaymentHistoryDetails();
			addOnPaymentHistoryDetails.setSoId(paymentDetails.getSoId());
			addOnPaymentHistoryDetails.setCheckNumber(paymentDetails.getCheckNumber());
			addOnPaymentHistoryDetails.setAmountAuthorized(paymentDetails.getAmountAuthorized());
			addOnPaymentHistoryDetails.setPaymentType(MPConstants.PAYMENT_TYPE_CHECK);
			
		}		
		
		//delete and new insert is done
		mobileSOActionsDao.deleteAdditionalPaymentDetails(addonpaymentDetails.getSoId());
		mobileSOActionsDao.insertPaymentDetails(addonpaymentDetails);
		//insert into history table
		if(null!=addOnPaymentHistoryDetails){
			try{
			mobileSOActionsDao.insertPaymentHistoryDetails(addOnPaymentHistoryDetails);
			}catch (Exception e) {
				logger.info("Caught Exception in persisting additional Payment History and ignoring");
				e.printStackTrace();
			}
		}	
	}
		
		} catch (DataServiceException e) {
			logger.error("Exception occured in mapPaymentDetails() due to "
					+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
	return isTokenizeResponseValid;
}

 //method for encrypting credit card number
  private String encryptCreditCardInfo(String ccNumber) {
		
		
		if(StringUtils.isNotBlank(ccNumber)) {
			CryptographyVO cryptographyVO = new CryptographyVO();
			cryptographyVO.setInput(ccNumber);
			
			//Commenting the code for SL-18789
			//cryptographyVO.setKAlias(MPConstants.ENCRYPTION_KEY);
			//cryptographyVO =  cryptography.encryptKey(cryptographyVO);
			cryptographyVO.setKAlias(MPConstants.CC_ENCRYPTION_KEY);
			cryptographyVO =  cryptography128.encryptKey128Bit(cryptographyVO);
			
			ccNumber=cryptographyVO.getResponse();
			}
		return ccNumber;
		
	}
 /**
     * @param pricing
     * @return
     */
 public void updatePrisingDetails(Pricing pricing)throws BusinessServiceException{
    
       try{   
           mobileSOActionsDao.updatePrisingDetails(pricing);                     
       }
       catch(DataServiceException e){
           logger.error("Exception occured in updatePrisingDetails() due to "+e.getMessage());
           throw new BusinessServiceException(e.getMessage());
       }       
   }
	
	/**
	 * @param updateSOCompletionRequest
	 * @param soId
	 * @return
	 * @throws BusinessServiceException 
	 */
	public void updateTaskDetails(UpdateSOCompletionRequest updateSOCompletionRequest,String soId) throws BusinessServiceException {
		if(updateSOCompletionRequest.getTasks()!=null && updateSOCompletionRequest.getTasks().getTask()!=null &&  updateSOCompletionRequest.getTasks().getTask().size() >0){
			for(Task ts:updateSOCompletionRequest.getTasks().getTask()){
				TaskVO vo=new TaskVO();
				vo.setSoId(soId);
				vo.setTaskId(ts.getTaskId());
				vo.setTaskStatus(ts.getTaskStatus());
				try{
				mobileSOActionsDao.updateTaskDetails(vo);
				}
				catch (Exception ex) {
					logger.error("Exception Occured in MobileSOActionsBOImpl-->updateTaskDetails()");
					throw new BusinessServiceException(ex.getMessage());
				}
			}
		}
		
	}
	/**
	 * @param resolutionComments
	 * @param soId
	 * @return
	 * @throws BusinessServiceException 
	 */
	public void updateResolutionComments(String resolutionComments, String soId) throws BusinessServiceException {
		//Added code for ESAPI encoding
		resolutionComments = EsapiUtility.getEncodedString(resolutionComments);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("soid", soId);
		map.put("comments", resolutionComments);
		try{
			mobileSOActionsDao.updateSoResolutionComments(map);
		}
		catch (Exception ex) {
			logger.error("Exception Occured in MobileSOActionsBOImpl-->updateResolutionComments()");
			throw new BusinessServiceException(ex.getMessage());
		}
	}
	/**
	 * @param updateSOCompletionRequest
	 * @param soId
	 * @return
	 * @throws BusinessServiceException 
	 */
	public void updatePartsDetails(UpdateSOCompletionRequest updateSOCompletionRequest, String soId) throws BusinessServiceException {
		try{
		if(updateSOCompletionRequest.getPartsTracking()!=null && updateSOCompletionRequest.getPartsTracking().getPart()!=null && updateSOCompletionRequest.getPartsTracking().getPart().size()>0){
			for(PartTracking pt:updateSOCompletionRequest.getPartsTracking().getPart()){
				Integer carrierId = null;
				String trackNo = null;
				if(null!=pt.getCarrier() && !StringUtils.isWhitespace(pt.getCarrier())){
					carrierId=getCarrierId(pt.getCarrier());
				}	
				if(null!= pt.getTrackingNumber() && !StringUtils.isWhitespace(pt.getTrackingNumber())){
					trackNo = pt.getTrackingNumber();
				}
				
				PartsVO pVO=new PartsVO();
				pVO.setSoId(soId);
				pVO.setPartId(pt.getPartId());
				//Added code for ESAPI encoding
				String partName = EsapiUtility.getEncodedString(pt.getPartName());
				pVO.setPartName(partName);
				
				pVO.setCoreCarrierId(carrierId);
				pVO.setCarrierId(carrierId);
				pVO.setTrackingNumber(trackNo);
				pVO.setCoreTrackingNumber(trackNo);
				mobileSOActionsDao.updatePartsDetails(pVO);
			}
		}
		}
		catch (Exception ex) {
		logger.error("Exception Occured in MobileSOActionsBOImpl-->updatePartsDetails()");
		throw new BusinessServiceException(ex);
		}
	}
	/**
	 *
	 * @param carrier
	 * @return carrierId
	 * @throws BusinessServiceException 
	 */
	private Integer getCarrierId(String carrier) throws BusinessServiceException{
		Integer carrierId=null;
		try{
			carrierId =mobileSOActionsDao.getCarrierId(carrier);
		}
		catch (Exception ex) {
			logger.error("Exception Occured in MobileSOActionsBOImpl-->getCarrierId()");
			throw new BusinessServiceException(ex.getMessage());
			}
		return carrierId;
	}
	
	public void deleteInvoicePartsDetails(String soId)throws BusinessServiceException{
		try{
			mobileSOActionsDao.deleteInvoicePartsDetails(soId);
		}
		catch (Exception ex) {
			logger.error("Exception Occured in MobileSOActionsBOImpl-->deleteInvoicePartsDetails()");
			throw new BusinessServiceException(ex.getMessage());
			}
	}
	
	
	/**
	 * @param updateSOCompletionRequest
	 * @param soId
	 * @return
	 * @throws BusinessServiceException 
	 */
	public void insertInvoicePartsDetails(UpdateSOCompletionRequest updateSOCompletionRequest, String soId, String userName) throws BusinessServiceException {
		
		try{
			mobileSOActionsDao.deleteInvoicePartsDetails(soId);
			if(updateSOCompletionRequest.getParts()!=null && updateSOCompletionRequest.getParts().getPart()!=null && updateSOCompletionRequest.getParts().getPart().size()>0){
				
				//Boolean autoAdjudicationInd= mobileSOActionsDao.getAutoAdjudicationInd();

				String  pricingModel= mobileSOActionsDao.getInvoicePartsPricingModel(soId);

				for(Part pt:updateSOCompletionRequest.getParts().getPart()){
					InvoicePartsVO invVO=new InvoicePartsVO();
					
					invVO.setRetailPrice(pt.getRetailPrice());
					invVO.setPartCoverage(pt.getPartCoverage());
					invVO.setPartSource(pt.getPartSource());
					invVO.setQty(pt.getQty());  
					invVO.setUnitCost(pt.getUnitCost());
						
					if( "COST_PLUS".equals(pricingModel)){	
						
						invVO= calculatePriceofInvoicePartsAdjudication(invVO);
						invVO.setAutoAdjudicationInd(true);
					}else{		
						invVO=	calculatePriceofInvoiceParts(invVO);
						invVO.setAutoAdjudicationInd(false);

					}
					
					pt.setPartInvoiceSource("MOBILE"); 
					
					invVO.setSoId(soId);
					invVO.setPartCoverage(pt.getPartCoverage());
					if((MPConstants.NON_SEARS).equals(pt.getPartSource())){
						invVO.setPartSource(MPConstants.NON_SEARS);
						invVO.setNonSearsSource(pt.getNonSearsSource());
					}
					else{
						invVO.setPartSource(pt.getPartSource());
						invVO.setNonSearsSource("");
					}
					//Added code for ESAPI encoding
					String partDesc = EsapiUtility.getEncodedString(pt.getPartDescription());
					
					invVO.setPartDescription(partDesc);
					invVO.setPartNumber(pt.getPartNumber());
					invVO.setSourceNumber(pt.getSourceNumber());
					invVO.setDivisionNumber(pt.getDivisionNumber());
					invVO.setInvoiceNumber(pt.getInvoiceNumber());
					invVO.setUnitCost(pt.getUnitCost());
					//invVO.setRetailPrice(pt.getRetailPrice());
					// setting correct retail price.
					invVO.setQty(pt.getQty());
					invVO.setCreatedDate(new java.util.Date());
					invVO.setModifiedDate(new java.util.Date());
					invVO.setModifiedBy(userName);
					//R12.0 update parts status as Installed for an invoice part added for HSR BUYER through UpdateSOCompletion v1.0 API
					invVO.setPartStatus(MPConstants.PARTS_STATUS_INSTALLED);
					mobileSOActionsDao.insertInvoicePartsDetails(invVO);
				}
			}
		}
			catch (Exception ex) {
			logger.error("Exception Occured in MobileSOActionsBOImpl-->insertInvoicePartsDetails()");
			throw new BusinessServiceException(ex.getMessage());
			}
	
	}
		

	
	
public void insertInvoicePartsDetails1(UpdateSOCompletionRequest updateSOCompletionRequest, String soId, String userName) throws BusinessServiceException {
		
		try{
			mobileSOActionsDao.deleteInvoicePartsDetails(soId);
			if(updateSOCompletionRequest.getParts()!=null && updateSOCompletionRequest.getParts().getPart()!=null && updateSOCompletionRequest.getParts().getPart().size()>0){
				for(Part pt:updateSOCompletionRequest.getParts().getPart()){
					InvoicePartsVO invVO=new InvoicePartsVO();
					double costToInventory = MPConstants.HSR_COST_TO_INVENTORY;
					double percentage = 10.0;
					double retailPrice= Double.parseDouble(pt.getRetailPrice());
					Double reimbursementRate =0.0;
					Double slGrossUpValue = 0.0;
					String partCoverage = pt.getPartCoverage();
					String partSource = pt.getPartSource();
					HashMap<String, Double> buyerPriceCalcValues = mobileSOActionsDao.getReimbursementRate(partCoverage,partSource);
					if (null != buyerPriceCalcValues && !buyerPriceCalcValues.isEmpty()) {
						String reImbRate = buyerPriceCalcValues.get("reImbursementRate").toString();
						String grossValue = buyerPriceCalcValues.get("grossupVal").toString();
						if (StringUtils.isNotEmpty(reImbRate)&& StringUtils.isNotEmpty(grossValue)){
							reimbursementRate = Double.parseDouble(reImbRate);
							slGrossUpValue = Double.parseDouble(grossValue);
						}
					}
					double netPayment = MoneyUtil.getRoundedMoney(retailPrice*(reimbursementRate/100)*100)/100;
					       netPayment = netPayment*pt.getQty();
					double retailCostToInventory = MoneyUtil.getRoundedMoney(retailPrice * (costToInventory / 100.0));
					double retailReimbursement = MoneyUtil.getRoundedMoney((retailPrice * (reimbursementRate / 100.0)) - retailCostToInventory);
					double retailPriceSLGrossUp = MoneyUtil.getRoundedMoney(retailPrice * slGrossUpValue * (percentage / 100.0));
					double retailSLGrossUp = MoneyUtil.getRoundedMoney(retailPrice * slGrossUpValue);
					double finalPayment=MoneyUtil.getRoundedMoney(retailPrice*slGrossUpValue*100)/100;
					finalPayment=finalPayment*pt.getQty();
					
					invVO.setSoId(soId);
					invVO.setPartCoverage(pt.getPartCoverage());
					if((MPConstants.NON_SEARS_INPUT).equals(pt.getPartSource())){
						invVO.setPartSource(MPConstants.NON_SEARS);
						invVO.setNonSearsSource(pt.getNonSearsSource());
					}
					else{
						invVO.setPartSource(MPConstants.SEARS_SOURCE);
						invVO.setNonSearsSource("");
					}
					//Added code for ESAPI encoding
					String partDesc = EsapiUtility.getEncodedString(pt.getPartDescription());
					
					invVO.setPartDescription(partDesc);
					invVO.setPartNumber(pt.getPartNumber());
					invVO.setSourceNumber(pt.getSourceNumber());
					invVO.setDivisionNumber(pt.getDivisionNumber());
					invVO.setInvoiceNumber(pt.getInvoiceNumber());
					invVO.setUnitCost(pt.getUnitCost());
					invVO.setRetailPrice(pt.getRetailPrice());
					invVO.setQty(pt.getQty());
					invVO.setFinalPayment(String.valueOf(finalPayment));
					invVO.setRetailCostToInventory(String.valueOf(retailCostToInventory));
					invVO.setRetailReimbursement(String.valueOf(retailReimbursement));
					invVO.setRetailPriceSLGrossUp(String.valueOf(retailPriceSLGrossUp));
					invVO.setRetailSLGrossUp(String.valueOf(retailSLGrossUp));
					invVO.setCreatedDate(new java.util.Date());
					invVO.setModifiedDate(new java.util.Date());
					invVO.setModifiedBy(userName);
					invVO.setNetPayment(String.valueOf(netPayment));
					mobileSOActionsDao.insertInvoicePartsDetails(invVO);
				}
			}
		}
			catch (Exception ex) {
			logger.error("Exception Occured in MobileSOActionsBOImpl-->insertInvoicePartsDetails()");
			throw new BusinessServiceException(ex.getMessage());
			}
	
	}


	
	
	
	
	/**
	 * @param references
	 * @param soId
	 */
	public void updateBuyerReferences(List<CustomReferenceVO> customReferenceVOs, String soId)
			throws BusinessServiceException {
		
		try{
			boolean found =false;
			List<CustomReferenceVO> soCustomReferenceVOs = getCustomReferncesForSO(soId);
			List<CustomReferenceVO> notExistingCustomReferenceVOs = new ArrayList<CustomReferenceVO>();

			if(soCustomReferenceVOs!=null && !soCustomReferenceVOs.isEmpty()){
				for(CustomReferenceVO customReferenceVO : customReferenceVOs){
					found = false;
					for(CustomReferenceVO referenceVO :soCustomReferenceVOs){
						//update if already exists
						if(referenceVO.getBuyerRefTypeId().intValue() == customReferenceVO.getBuyerRefTypeId().intValue()){
							customReferenceVO.setCustRefId(referenceVO.getCustRefId());
							mobileSOActionsDao.updateCustomRefValue(customReferenceVO);
							found =true;
							break;
						}
					}
					if(!found){
						notExistingCustomReferenceVOs.add(customReferenceVO);
					}
				}
				if(!notExistingCustomReferenceVOs.isEmpty()){
					mobileSOActionsDao.addCustomeReferences(notExistingCustomReferenceVOs);
				}
				
			}
			else if(customReferenceVOs!=null && !customReferenceVOs.isEmpty()){
				mobileSOActionsDao.addCustomeReferences(customReferenceVOs);
			}
		}
		catch (Exception ex) {
			logger.error("Exception Occured in MobileSOActionsBOImpl-->updateBuyerReferences()");
			throw new BusinessServiceException(ex.getMessage());
		}
	}

	/**
	 * @param soId
	 */
	private List<CustomReferenceVO> getCustomReferncesForSO(String soId) throws BusinessServiceException  {
		
		
		try{
			List<CustomReferenceVO> customReferenceVOs = mobileSOActionsDao.getCustomReferncesForSO(soId);
			return customReferenceVOs;
		}
		catch (Exception ex) {
			logger.error("Exception Occured in MobileSOActionsBOImpl-->getCustomReferncesForSO()");
			throw new BusinessServiceException(ex.getMessage());
		}
		
	}
	 
	
		public static double formatAsMoney(double mnt)
	    {
	       mnt -= 0;
	       mnt = (Math.round(mnt*100))/100;
	       return (mnt == Math.floor(mnt)) ? mnt + .00  : ( (mnt*10 == Math.floor(mnt*10)) ?  mnt + 0 : mnt);
	    }
	    

		/**
		 * @param intValue
		 * @return
		 */
		public Contact getVendorResourceContact(int resourceId)
				throws BusinessServiceException {
		    try{
		        return mobileSOActionsDao.getVendorResourceContact(resourceId);
		    }catch(Exception e){
		        String message = String.format("Unknown Exception when trying to read Contact for VendorResource (%1)",resourceId);
		        throw new BusinessServiceException(message, e);
		    }
		}

		/**
		 * @param soId
		 * @return
		 */
		public boolean checkIfSOIsActive(String soId) throws BusinessServiceException{
			// TODO Auto-generated method stub
			 try{
			        return mobileSOActionsDao.checkIfSOIsActive(soId);
			    }catch(Exception e){
			        String message = String.format("Unknown Exception when trying to checkIfSOIsActive",soId);
			        throw new BusinessServiceException(message, e);
			    }
		}


		/**
		 * @param reasonVOs
		 */
		public void updateLabourPartPriceReasons(List<SOPartLaborPriceReasonVO> reasonVOs) throws BusinessServiceException{
			// TODO Auto-generated method stub
			 try{
			         mobileSOActionsDao.updateLabourPartPriceReasons(reasonVOs);
			    }catch(Exception e){
					logger.error("Exception Occured in MobileSOActionsBOImpl-->updateLabourPartPriceReasons()");
			        throw new BusinessServiceException(e.getMessage());
			    }
		}
		
		public void insertSignatureDetails(UpdateSOCompletionRequest request,String soId) throws BusinessServiceException{
			 try{
				 if(null!=request.getProviderSignature()){
					Integer id = mobileSOActionsDao.fetchSignatureDetails(soId, MPConstants.PROVIDER) ;
					if(null==id){
						mobileSOActionsDao.insertSignatureDetails(request.getProviderSignature(),soId,MPConstants.PROVIDER);
					}else{
						mobileSOActionsDao.updateSignatureDetails(request.getProviderSignature(),soId,MPConstants.PROVIDER);
						if(id.intValue() != request.getProviderSignature().getDocumentId().intValue()){
							if((null!=request.getCustomerSignature() && id.intValue()!= request.getCustomerSignature().getDocumentId().intValue())|| null== request.getCustomerSignature()){
								mobileSOActionsDao.updateDocumentDetails(id);
							}
						}
					}
				
				 }
				 if(null!=request.getCustomerSignature()){
				   Integer id = mobileSOActionsDao.fetchSignatureDetails(soId, MPConstants.CUSTOMER) ;
				   if(null==id){
					   mobileSOActionsDao.insertSignatureDetails(request.getCustomerSignature(),soId,MPConstants.CUSTOMER);
				   }else{
					   mobileSOActionsDao.updateSignatureDetails(request.getCustomerSignature(),soId,MPConstants.CUSTOMER);
					   if(id.intValue() != request.getCustomerSignature().getDocumentId().intValue()){
							if((null!=request.getProviderSignature() && id.intValue()!= request.getProviderSignature().getDocumentId().intValue())|| null== request.getProviderSignature()){
								mobileSOActionsDao.updateDocumentDetails(id);
							}
						}
				   }
					
				 }
				 // SLM-117 : code change to update email send ind for boith customer signature and provider signature as 0 if there occurs any update in any of the signatures
				 if(null!=request.getProviderSignature() || null!=request.getCustomerSignature()){
					 Signature signature = new Signature();
					 signature.setEmailSentInd(0);
					 signature.setSoId(soId);
					 mobileSOActionsDao.updateSignatureEmailSent(signature);
				 }
			    }catch(Exception e){
			        String message = String.format("Exception when trying to updateSignatureDetails",soId);
			        throw new BusinessServiceException(message, e);
			    }
	    
		}
	 
		public void updateAddonPermits(Permits permits, String soId)
				throws BusinessServiceException {
			PermitAddons permitAddons = permits.getPermitAddons();
			Integer addOnCount=null;
			try{

				if(null != permitAddons.getPermitAddon()){
					for(PermitAddon permit : permitAddons.getPermitAddon()){
						if(null != permit){//update the permit addon - for the one displayed in screen
							permit.setSoId(soId);
							if(StringUtils.isNotBlank(permit.getSoAddonId())){
								mobileSOActionsDao.updatePermitAddon(permit);
							}
							else{//insert a new entry for extra permit addons - newly added permit add ons
								permit.setMisc(1);
								permit.setScopeOfWork(MPConstants.PERMIT_SCOPE);
								permit.setCoverageType(MPConstants.COVERAGE_TYPE_PT);
								permit.setAutogenAddon(false);
								mobileSOActionsDao.insertPermitAddon(permit);
							}
						}
					}
					//SL-20652:code change to delete payment details when permit add on quantity is zero
					addOnCount = mobileSOActionsDao.getAddOnForSO(soId);
			         if(null== addOnCount || (null!= addOnCount && addOnCount.intValue() == 0)){
			        	 mobileSOActionsDao.deleteAdditionalPaymentDetails(soId);
			         }
				}
				
			}catch(DataServiceException e){
				logger.error("Exception Occured in MobileSOActionsBOImpl-->updateAddonPermits()");
		         throw new BusinessServiceException(e.getMessage());
			}	
		}
		
		public void updatePermitTasks(PermitTasks permitTasks, String soId)
				throws BusinessServiceException {
			try{
				if(null != permitTasks.getPermitTask()){

					mobileSOActionsDao.updateExistingPermitAddOnQty(soId,true);

					for(PermitTask permit : permitTasks.getPermitTask()){
						if(null != permit){
							permit.setSoId(soId);
							permit.setPermitTypeId(mapPermitType(permit.getPermitType()));
							//permit.setPrice(permit.getFinalPermitPriceByProvider() - permit.getCustPrePaidAmount());
							mobileSOActionsDao.updatePermitTask(permit);
							//if provider has given a higher price for prepaid permit task(final price>selling price), -
							//add the extra amount as permit addon with auto_gen_ind = true and coverage = "CC"
							
							if(permit.getFinalPermitPriceByProvider()>permit.getCustPrePaidAmount()){								
								PermitAddon permitAddon = new PermitAddon();
								Double customerCharge = permit.getFinalPermitPriceByProvider() - permit.getCustPrePaidAmount();
								permitAddon.setCustomerCharge(customerCharge);
								permitAddon.setSoId(soId);
								permitAddon.setAutogenAddon(true);
								permitAddon.setCoverageType(MPConstants.COVERAGE_TYPE_CC);
								permitAddon.setAddonSKU(MPConstants.PERMIT_SKU);
								permitAddon.setQty(MPConstants.DEFAULT_QTY);
								permitAddon.setPermitType(permit.getPermitType());
								permitAddon.setMisc(MPConstants.PERMIT_TASK_MISC);
								permitAddon.setScopeOfWork(MPConstants.PERMIT_SCOPE);
								mobileSOActionsDao.insertPermitAddon(permitAddon);
							}
						}
					}
				}
			}catch(DataServiceException e){
				logger.error("Exception Occured in MobileSOActionsBOImpl-->updatePermitTasks()");
		         throw new BusinessServiceException(e.getMessage());
			}
					
		}
		
	  private Integer mapPermitType(String permitType){
	      Integer partType = 0;
	         if(permitType.equalsIgnoreCase(MPConstants.BUILDING)){
	             partType = MPConstants.Building_TYPE;
	         }
	         else if(permitType.equalsIgnoreCase(MPConstants.ELECTICAL)){
	             partType = MPConstants.Electrical_TYPE;
	         }
	         else if(permitType.equalsIgnoreCase(MPConstants.PLUMBING)){
	             partType = MPConstants.Plumbing_TYPE;
	         }
	         return partType;
	        
	  }

	
	  /**
	   * @param soId
	   * @return
	   */
	  public List<DocumentVO> getServiceOrderDocuments(String soId) throws BusinessServiceException{
			List<DocumentVO> documentVoList = new ArrayList<DocumentVO>();	 
			try{
				documentVoList = mobileSOActionsDao.getServiceOrderDocuments(soId);
			}
			catch(DataServiceException e){
				logger.error("Exception Occured in MobileSOActionsBOImpl-->getServiceOrderDocuments()");
		         throw new BusinessServiceException(e.getMessage());
			}
			return documentVoList;
	  }

	/**
	 * @param soId
	 * @return
	 */
	  public List<Signature> getServiceOrderSignature(String soId)
			throws BusinessServiceException {
		  List<Signature> signatureList = new ArrayList<Signature>();
			try{					
				signatureList =  mobileSOActionsDao.getServiceOrderSignature(soId);			
			}
			catch(DataServiceException e){
				logger.error("Exception occured in getServiceOrderSignature() due to "+e.getMessage());
				throw new BusinessServiceException(e.getMessage());
			}
			 return signatureList;
	}


	/**
	 * @param soId
	 * @return
	 * @throws DataServiceException
	 */
	  public List<Integer> getSOTasks(String soId) throws BusinessServiceException {
		  
		// TODO Auto-generated method stub
		List<Integer> taskIds= null;
		try{					
			taskIds =  mobileSOActionsDao.getSOTasks(soId);			
		}
		catch(DataServiceException e){
			logger.error("Exception occured in getSOTasks() due to "+e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		
		return taskIds;
	}

	  /**
		 * @param soId
		 * @return
		 * @throws BusinessServiceException
		 */
	public List<Integer> getSOPermitTaks(String soId)
			throws BusinessServiceException {
		List<Integer> taskIds= null;
		try{					
			taskIds =  mobileSOActionsDao.getSOPermitTaks(soId);			
		}
		catch(DataServiceException e){
			logger.error("Exception occured in getSOPermitTaks() due to "+e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		
		return taskIds;
	}

	public List<AddOn> getSOAddonDetails(String soId)
			throws BusinessServiceException {
		List<AddOn> addonList = new ArrayList<AddOn>();
		try{
			addonList = mobileSOActionsDao.getSOAddonDetails(soId);
		}catch(DataServiceException e){
			logger.error("Exception occured in getSOAddonDetails() due to "+e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return addonList;
	}

	/**
	 * TODO: R12_0 Delete this after review as it is no longer in use. Currently using newly created method
	 
	public void insertInvoicePartsDetails(
			com.newco.marketplace.api.mobile.beans.updateSoCompletion.v2_0.UpdateSOCompletionRequest updateSOCompletionRequest,
			String soId, String userName) throws BusinessServiceException {
		try{
			mobileSOActionsDao.deleteInvoicePartsDetails(soId);
			if(updateSOCompletionRequest.getParts()!=null && updateSOCompletionRequest.getParts().getPart()!=null && updateSOCompletionRequest.getParts().getPart().size()>0){
				for(Part pt:updateSOCompletionRequest.getParts().getPart()){
					InvoicePartsVO invVO=new InvoicePartsVO();
					double costToInventory = MPConstants.HSR_COST_TO_INVENTORY;
					double percentage = 10.0;
					double retailPrice= Double.parseDouble(pt.getRetailPrice());
					Double reimbursementRate =0.0;
					Double slGrossUpValue = 0.0;
					String partCoverage = pt.getPartCoverage();
					String partSource = pt.getPartSource();
					HashMap<String, Double> buyerPriceCalcValues = mobileSOActionsDao.getReimbursementRate(partCoverage,partSource);
					if (null != buyerPriceCalcValues && !buyerPriceCalcValues.isEmpty()) {
						String reImbRate = buyerPriceCalcValues.get("reImbursementRate").toString();
						String grossValue = buyerPriceCalcValues.get("grossupVal").toString();
						if (StringUtils.isNotEmpty(reImbRate)&& StringUtils.isNotEmpty(grossValue)){
							reimbursementRate = Double.parseDouble(reImbRate);
							slGrossUpValue = Double.parseDouble(grossValue);
						}
					}
					double netPayment = MoneyUtil.getRoundedMoney(retailPrice*(reimbursementRate/100)*100)/100;
					       netPayment = netPayment*pt.getQty();
					double retailCostToInventory = MoneyUtil.getRoundedMoney(retailPrice * (costToInventory / 100.0));
					double retailReimbursement = MoneyUtil.getRoundedMoney((retailPrice * (reimbursementRate / 100.0)) - retailCostToInventory);
					double retailPriceSLGrossUp = MoneyUtil.getRoundedMoney(retailPrice * slGrossUpValue * (percentage / 100.0));
					double retailSLGrossUp = MoneyUtil.getRoundedMoney(retailPrice * slGrossUpValue);
					double finalPayment=MoneyUtil.getRoundedMoney(retailPrice*slGrossUpValue*100)/100;
					finalPayment=finalPayment*pt.getQty();
					
					invVO.setSoId(soId);
					invVO.setPartCoverage(pt.getPartCoverage());
					if((MPConstants.NON_SEARS_INPUT).equals(pt.getPartSource())){
						invVO.setPartSource(MPConstants.NON_SEARS);
						invVO.setNonSearsSource(pt.getNonSearsSource());
					}
					else{
						invVO.setPartSource(MPConstants.SEARS_SOURCE);
						invVO.setNonSearsSource("");
					}
					//Added code for ESAPI encoding
					String partDesc = EsapiUtility.getEncodedString(pt.getPartDescription());
					
					invVO.setPartDescription(partDesc);
					invVO.setPartNumber(pt.getPartNumber());
					invVO.setSourceNumber(pt.getSourceNumber());
					invVO.setDivisionNumber(pt.getDivisionNumber());
					invVO.setInvoiceNumber(pt.getInvoiceNumber());
					invVO.setUnitCost(pt.getUnitCost());
					invVO.setRetailPrice(pt.getRetailPrice());
					invVO.setQty(pt.getQty());
					invVO.setFinalPayment(String.valueOf(finalPayment));
					invVO.setRetailCostToInventory(String.valueOf(retailCostToInventory));
					invVO.setRetailReimbursement(String.valueOf(retailReimbursement));
					invVO.setRetailPriceSLGrossUp(String.valueOf(retailPriceSLGrossUp));
					invVO.setRetailSLGrossUp(String.valueOf(retailSLGrossUp));
					invVO.setCreatedDate(new java.util.Date());
					invVO.setModifiedDate(new java.util.Date());
					invVO.setModifiedBy(userName);
					invVO.setNetPayment(String.valueOf(netPayment));
					mobileSOActionsDao.insertInvoicePartsDetails(invVO);
				}
			}
		}
			catch (Exception ex) {
			logger.error("Exception Occured in MobileSOActionsBOImpl-->insertInvoicePartsDetails()");
			throw new BusinessServiceException(ex.getMessage());
			}
		
	}
	
	*/
	
	

	//SLt-4658
	/**
	 * @param v3_0.UpdateSOCompletionRequest
	 * @param soId
	 */
	public void insertSignatureDetails(
			com.newco.marketplace.api.mobile.beans.updateSoCompletion.v3_0.UpdateSOCompletionRequest request,
			String soId) throws BusinessServiceException {
		try{
			 if(null!=request.getProviderSignature()){
				Integer id = mobileSOActionsDao.fetchSignatureDetails(soId, MPConstants.PROVIDER) ;
				if(null==id){
					mobileSOActionsDao.insertSignatureDetails(request.getProviderSignature(),soId,MPConstants.PROVIDER);
				}else{
					mobileSOActionsDao.updateSignatureDetails(request.getProviderSignature(),soId,MPConstants.PROVIDER);
					if(id.intValue() != request.getProviderSignature().getDocumentId().intValue()){
						if((null!=request.getCustomerSignature() && id.intValue()!= request.getCustomerSignature().getDocumentId().intValue())|| null== request.getCustomerSignature()){
							mobileSOActionsDao.updateDocumentDetails(id);
						}
					}
				}
			 }
			 if(null!=request.getCustomerSignature()){
			   Integer id = mobileSOActionsDao.fetchSignatureDetails(soId, MPConstants.CUSTOMER) ;
			   if(null==id){
				   mobileSOActionsDao.insertSignatureDetails(request.getCustomerSignature(),soId,MPConstants.CUSTOMER);
			   }else{
				   mobileSOActionsDao.updateSignatureDetails(request.getCustomerSignature(),soId,MPConstants.CUSTOMER);
				   if(id.intValue() != request.getCustomerSignature().getDocumentId().intValue()){
						if((null!=request.getProviderSignature() && id.intValue()!= request.getProviderSignature().getDocumentId().intValue())|| null== request.getProviderSignature()){
							mobileSOActionsDao.updateDocumentDetails(id);
						}
					}
			   }
			 }
			 // SLM-117 : code change to update email send ind for boith customer signature and provider signature as 0 if there occurs any update in any of the signatures
			 if(null!=request.getProviderSignature() || null!=request.getCustomerSignature()){
				 Signature signature = new Signature();
				 signature.setEmailSentInd(0);
				 signature.setSoId(soId);
				 mobileSOActionsDao.updateSignatureEmailSent(signature);
			 }
		    }catch(Exception e){
		        String message = String.format("Exception when trying to updateSignatureDetails",soId);
		        throw new BusinessServiceException(message, e);
		    }
   
		
	}

	
	
	//SLT-4658
	/**
	 * @param v3_0.UpdateSOCompletionRequest
	 * @return v3_0.UpdateSOCompletionRequest
	 */
	public com.newco.marketplace.api.mobile.beans.updateSoCompletion.v3_0.UpdateSOCompletionRequest preProcessUpdateSOCompletion(
			com.newco.marketplace.api.mobile.beans.updateSoCompletion.v3_0.UpdateSOCompletionRequest updateSOCompletionRequest) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		 String autoClose="";

	        if(MPConstants.SERVICE_PARTIAL.equals(updateSOCompletionRequest.getCompletionStatus()))
	        {
	        	 updateSOCompletionRequest.setAddOnPaymentDetails(null);
	        	//serviceOrder.setAdditionalPayment(null);
	        	if(updateSOCompletionRequest.getAddOns()!=null && updateSOCompletionRequest.getAddOns().getAddOn()!=null && updateSOCompletionRequest.getAddOns().getAddOn().size()>0)
	        	{
	        		for (AddOn addOn:updateSOCompletionRequest.getAddOns().getAddOn()) {

	        			addOn.setQty(0);
	        		}
	        	}
	        }
	        return updateSOCompletionRequest;
	}

	
	
	//SLT-4658
	
	/**
	 * @param v3_0.UpdateSOCompletionRequest
	 * @param soId
	 */
	public void updatePartsDetails(
			com.newco.marketplace.api.mobile.beans.updateSoCompletion.v3_0.UpdateSOCompletionRequest updateSOCompletionRequest,
			String soId) throws BusinessServiceException {
		try{
			if(updateSOCompletionRequest.getPartsTracking()!=null && updateSOCompletionRequest.getPartsTracking().getPart()!=null && updateSOCompletionRequest.getPartsTracking().getPart().size()>0){
				for(PartTracking pt:updateSOCompletionRequest.getPartsTracking().getPart()){
					Integer carrierId = null;
					String trackNo = null;
					if(null!=pt.getCarrier() && !StringUtils.isWhitespace(pt.getCarrier())){
						carrierId=getCarrierId(pt.getCarrier());
					}	
					if(null!= pt.getTrackingNumber() && !StringUtils.isWhitespace(pt.getTrackingNumber())){
						trackNo = pt.getTrackingNumber();
					}
					
					PartsVO pVO=new PartsVO();
					pVO.setSoId(soId);
					pVO.setPartId(pt.getPartId());
					//Added code for ESAPI encoding
					String partName = EsapiUtility.getEncodedString(pt.getPartName());
					pVO.setPartName(partName);
					
					pVO.setCoreCarrierId(carrierId);
					pVO.setCarrierId(carrierId);
					pVO.setTrackingNumber(trackNo);
					pVO.setCoreTrackingNumber(trackNo);
					mobileSOActionsDao.updatePartsDetails(pVO);
				}
			}
			}
			catch (Exception ex) {
			logger.error("Exception Occured in MobileSOActionsBOImpl-->updatePartsDetails()");
			throw new BusinessServiceException(ex);
			}
		
	}
	
	
	/**
	 * R12.0 SPRINT 2
	 * @param curTripNo
	 * @param soId
	 * @return
	 * @throws BusinessServiceException
	 */
	public Integer validateTrip(Integer curTripNo,String soId) throws BusinessServiceException{
		
		try {
			return mobileSOActionsDao.validateTrip(curTripNo,soId);
		}catch (Exception e) {
			e.printStackTrace();
			throw new BusinessServiceException(e.getMessage(), e);			
		}
	}
	
	/**
	 * R12.0 SPRINT 2 - Add the trip details
	 * 
	 * @param currentSOTripId
	 * @param changeType
	 * @param changeCOmment
	 * @return
	 * @throws BusinessServiceException
	 */
	
	public Integer addTripDetails(Integer currentSOTripId,
			String changeType, String changeComment,String createdBy)throws BusinessServiceException{

		try {
			SOTripDetailsVO soTripDetailsVO = new SOTripDetailsVO();
			soTripDetailsVO.setSoTripId(currentSOTripId);
			soTripDetailsVO.setChangeType(changeType);
			soTripDetailsVO.setChangeComment(changeComment);
			soTripDetailsVO.setCreatedBy(createdBy);
			return mobileSOActionsDao.addTripDetails(soTripDetailsVO);
		}catch (Exception e) {
			e.printStackTrace();
			throw new BusinessServiceException(e.getMessage(), e);			
		}
	}
	
	/**
	 * R12.0 SPRINT 2 - update revisit needed data to so_trip table.
	 * 
	 * @param revisitNeededVO
	 * @return
	 * @throws BusinessServiceException
	 */
	public void updateRevisitNeeded(SORevisitNeededVO revisitNeededVO) throws BusinessServiceException{
		try{
			mobileSOActionsDao.updateRevisitNeeded(revisitNeededVO);
		}catch (Exception ex) {
			logger.error("Exception Occured in MobileSOActionsBOImpl-->updateRevisitNeeded()");
			throw new BusinessServiceException(ex);
		}
	}
	
	/**
	 * R12.0 SPRINT 2 - Method to update schedule details in so_schedule and so_schedule_history 
	 * 
	 * @param revisitNeededVO
	 * @throws BusinessServiceException
	 */
	public void updateSOScheduleForRevisit(SORevisitNeededVO revisitNeededVO) throws BusinessServiceException{
		try{
			mobileSOActionsDao.updateSOScheduleForRevisit(revisitNeededVO);
		}catch (Exception ex) {
			logger.error("Exception Occured in MobileSOActionsBOImpl-->updateSOScheduleForRevisit()");
			throw new BusinessServiceException(ex);
		}
	}

	
	/**
	 * R12.0 SPRINT 2 - update revisit needed appointment date in so_hdr
	 * 
	 * @param revisitNeededVO
	 * @throws BusinessServiceException
	 */
	public void updateAppointmentDateTime(SORevisitNeededVO revisitNeededVO) throws BusinessServiceException{
		try{
			mobileSOActionsDao.updateAppointmentDateTime(revisitNeededVO);
		}catch (Exception ex) {
			logger.error("Exception Occured in MobileSOActionsBOImpl-->updateAppointmentDateTime()");
			throw new BusinessServiceException(ex);
		}
	}
	
	/**
	 * @param InvoicePartsVO
	 * @param soId
	 * @return InvoicePartsVO
	 * @throws BusinessServiceException 
	 */
	public void addInvoicePartsInfo(List<InvoicePartsVO> invoicePartVOlist, String soId, String userName) throws BusinessServiceException {

		try{
			
			if(invoicePartVOlist!=null){
				String coverage = mapPartCoverage(soId);

				String pricingModel=getInvoicePartsPricingModel(soId);

				
				for(InvoicePartsVO invoicePart:invoicePartVOlist){
					
					invoicePart.setPartCoverage(coverage);

					calculatePriceofParts(invoicePart,pricingModel);
					
					invoicePart.setPartInvoiceSource("MOBILE");
					 
					if((MPConstants.NON_SEARS).equals(invoicePart.getPartSource())){
						invoicePart.setPartSource(MPConstants.NON_SEARS);
						invoicePart.setNonSearsSource(invoicePart.getNonSearsSource());
					} 
					else if((MPConstants.SEARS_SOURCE).equals(invoicePart.getPartSource())){
						invoicePart.setPartSource(MPConstants.SEARS_SOURCE);
						invoicePart.setNonSearsSource("");
					}
		
									
					invoicePart.setModifiedDate(new java.util.Date());
					invoicePart.setModifiedBy(userName);
					mobileSOActionsDao.updateInvoicePartsDetails(invoicePart);
					// Save invoice documents
					mobileSOActionsDao.saveInvoicePartDocument(invoicePart);
				 }
				
			}
		}
			catch (Exception ex) {
			logger.error("Exception Occured in MobileSOActionsBOImpl-->updateInvoicePartsDetails()");
			throw new BusinessServiceException(ex.getMessage());
			}
		
	}
	
	/**
	 * @param invoicePartsVO
	 * @return
	 * 
	 * to map part coverage according to custom reference
	 * @throws DataServiceException 
	 * 
	 */
	public String mapPartCoverage(String soId) throws BusinessServiceException{
		
		String coverage = null;
		try{
			String customRefValue = mobileSOActionsDao.
					getCustomReference(soId, MPConstants.COVERAGE_TYPE_LABOR);	
			
			
			if (null != customRefValue) {
				if ((MPConstants.COVERAGE_TYPE_LABOR_PA
						.equalsIgnoreCase(customRefValue) || (MPConstants.COVERAGE_TYPE_LABOR_SP
						.equalsIgnoreCase(customRefValue)))) {

					coverage = MPConstants.PROTECTION_AGREEMENT;

				} else if ((MPConstants.COVERAGE_TYPE_LABOR_IW
						.equalsIgnoreCase(customRefValue))) {

					coverage = MPConstants.IN_WARRANTY;

				} else if ((MPConstants.COVERAGE_TYPE_LABOR_PT
						.equalsIgnoreCase(customRefValue))) {

					coverage = MPConstants.PAY_ROLL_TRANSFER;

				} else if ((MPConstants.COVERAGE_TYPE_LABOR_CC
						.equalsIgnoreCase(customRefValue))) {

					coverage = MPConstants.CUSTOMER_COLLECT;

				}
				
			}
		}
		catch(Exception e){
			logger.info(" calculate Price of InvoiceParts "+ e);
			
		}
	
		return coverage;
	}
	
	
	
	public InvoicePartsVO calculatePriceofParts (InvoicePartsVO invoicePartsVO,String pricingModel) {
		
		if( "COST_PLUS".equals(pricingModel)){
			invoicePartsVO= calculatePriceofInvoicePartsAdjudication(invoicePartsVO);	
		}else{
			invoicePartsVO= calculatePriceofInvoiceParts(invoicePartsVO);	

		}
        return invoicePartsVO;
        
	}
	
	
	public InvoicePartsVO calculatePriceofInvoiceParts (InvoicePartsVO invoicePartsVO) {
		try{
		double costToInventory = MPConstants.HSR_COST_TO_INVENTORY;
		double percentage = 10.0;
		double retailPrice= Double.parseDouble(invoicePartsVO.getRetailPrice());
		Double reimbursementRate =0.0;
		Double slGrossUpValue = 0.0;
		
		String partCoverage = invoicePartsVO.getPartCoverage();
		String partSource = invoicePartsVO.getPartSource();
		
		if(("Truck Stock").equals(partSource)){
			partSource="Non Sears";	
		}
		HashMap<String, Double> buyerPriceCalcValues = mobileSOActionsDao.getReimbursementRate(partCoverage,partSource);
		if (null != buyerPriceCalcValues && !buyerPriceCalcValues.isEmpty()) {
			String reImbRate = buyerPriceCalcValues.get("reImbursementRate").toString();
			String grossValue = buyerPriceCalcValues.get("grossupVal").toString();
			if (StringUtils.isNotEmpty(reImbRate)&& StringUtils.isNotEmpty(grossValue)){
				reimbursementRate = Double.parseDouble(reImbRate);
				slGrossUpValue = Double.parseDouble(grossValue);
			}
		}
		 
		
		
		double netPayment = MoneyUtil.getRoundedMoney((retailPrice*(reimbursementRate/100.00)*100)/100);
		netPayment = netPayment*invoicePartsVO.getQty();
		double retailCostToInventory = MoneyUtil.getRoundedMoney(retailPrice * (costToInventory / 100.0));
		double retailReimbursement = MoneyUtil.getRoundedMoney((retailPrice * (reimbursementRate / 100.0)) - retailCostToInventory);
		double retailPriceSLGrossUp = MoneyUtil.getRoundedMoney(retailPrice * slGrossUpValue * (percentage / 100.0));
		double retailSLGrossUp = MoneyUtil.getRoundedMoney(retailPrice * slGrossUpValue);
		double finalPayment=MoneyUtil.getRoundedMoney((retailPrice*slGrossUpValue*100)/100);
		finalPayment=finalPayment*invoicePartsVO.getQty();
		
		
		invoicePartsVO.setFinalPayment(String.valueOf(finalPayment));
		invoicePartsVO.setRetailCostToInventory(String.valueOf(retailCostToInventory));
		invoicePartsVO.setRetailReimbursement(String.valueOf(retailReimbursement));
		invoicePartsVO.setRetailPriceSLGrossUp(String.valueOf(retailPriceSLGrossUp));
		invoicePartsVO.setRetailSLGrossUp(String.valueOf(retailSLGrossUp));
		invoicePartsVO.setNetPayment(String.valueOf(netPayment));		
		invoicePartsVO.setReimbursementRate(String.valueOf(reimbursementRate));		
		} catch(Exception e){
			logger.info(" calculate Price of InvoiceParts "+ e);
			
		}
								
		return invoicePartsVO;
		
	}
	
	
	
	public InvoicePartsVO calculatePriceofInvoicePartsAdjudication (InvoicePartsVO invoicePartsVO) {
		
		try{
		
		// SLM-58 Parts pricing -Parts reimbursement Cost Plus Model

		double retailPrice= Double.parseDouble(invoicePartsVO.getRetailPrice()); 
		double retailPriceOrginal= Double.parseDouble(invoicePartsVO.getRetailPrice());

		// unit Cost
		double unitCost= Double.parseDouble(invoicePartsVO.getUnitCost());		
		Double reimbursementRate =0.0;
		Double slGrossUpValue = 0.0;
		// for adjudication Tolerance
		Double adjTolerance = 0.0;
		Double adjCommercialPriceConstant = 0.0;
		Double firmLevelReimbursementRate=0.0;
		
		//If auto adjudication then the  reimbursement rate, sl gross up values need to fetched from the application_constant table
		
		HashMap<String, String> autoAdjudicationValuesMap=	mobileSOActionsDao.getAutoAdjudicationValues();
		
			String reimbursementRateValue=autoAdjudicationValuesMap.get("auto_adjudication_default_reimbursement_rate");
			String slGrossUp=autoAdjudicationValuesMap.get("auto_adjudication_grossup_value");
		    String adjToleranceValue=autoAdjudicationValuesMap.get("adjudication_tolerance");
		    String adjCommercialPricePercentage=autoAdjudicationValuesMap.get("adjudication_commercial_discount_percentage"); 
	    		
			if (StringUtils.isNotEmpty(reimbursementRateValue)){
				reimbursementRate = Double.parseDouble(reimbursementRateValue);
			}
			if (StringUtils.isNotEmpty(slGrossUp)){
				slGrossUpValue = Double.parseDouble(slGrossUp);
			}
			if (StringUtils.isNotEmpty(adjToleranceValue)){
				adjTolerance= Double.parseDouble(adjToleranceValue);
			}
			if (StringUtils.isNotEmpty(adjCommercialPricePercentage)){
				adjCommercialPriceConstant= Double.parseDouble(adjCommercialPricePercentage);
			}
			

			//R12_1: Auto Adjudication Phase II: if there is a firm level reimbursement rate set for a vendor,use that. 
			//Else use the common reimbursement rate.
			firmLevelReimbursementRate=mobileSOActionsDao.getFirmLevelReimbursementRate(invoicePartsVO.getSoId());
			if(null != firmLevelReimbursementRate){
			 reimbursementRate=firmLevelReimbursementRate;
			}
			//Sprint 5 SLM 102: Commenting this as the conversion is now done during AddPart API.       
			// retail price =75% of the SPD National Sell Retail Price.
			retailPrice=MoneyUtil.getRoundedMoney(retailPrice - (retailPrice*(adjCommercialPriceConstant/100.00)));												
			// provider cost= unit cost * quantity
			double providerCost=MoneyUtil.getRoundedMoney(unitCost*invoicePartsVO.getQty());				
			// Est. Provider Payment = providerCost + (providerCost * Reimbursement Rate/100) 
			double netPayment = MoneyUtil.getRoundedMoney(providerCost + (( (providerCost*(reimbursementRate/100.00)*100)/100))) ;				  		
			//Final price= 	Est. Provider Payment  * 1.1111	
			double constantValue = 100.00/90.00 ;
			double finalPayment=MoneyUtil.getRoundedMoney(netPayment * constantValue) ;					
			// retail cost to iventory
			double retailCostToInventory = MoneyUtil.getRoundedMoney(netPayment * (70.00 / 100.0));
			// retail reimbursement
			double retailReimbursement = MoneyUtil.getRoundedMoney(netPayment * (30.00 / 100.0));
			// retail price sl gross up
			double retailPriceSLGrossUp = MoneyUtil.getRoundedMoney(finalPayment * (10.00 / 100.0));
			// retail sl gross up
			double retailSLGrossUp = MoneyUtil.getRoundedMoney(finalPayment);						
			// provider Margin= final price - provider cost
			double providerMargin=MoneyUtil.getRoundedMoney(netPayment-providerCost);				
			//Reimbursement Max = Retail price + (Retail Price * (Reimbursement Rate % + Adjudication Tolerance %))
			double reimbursementMax = MoneyUtil.getRoundedMoney((retailPrice +(retailPrice * ( (reimbursementRate/100.00) + (adjTolerance/100.00))))* invoicePartsVO.getQty());
					
			//If Estimated Net Payment is less than or equal to Reimbursement Max then, the claim status = Eligible else Not Eligible
			String claimStatus=""; 
			 if(!("MANUAL".equalsIgnoreCase(invoicePartsVO.getPartNoSource()))  &&  netPayment<=reimbursementMax){
					claimStatus="Approved";
				}else{
						claimStatus="Pending";
				}
			invoicePartsVO.setClaimStatus(claimStatus);
			
			// setting correct retail price.
			invoicePartsVO.setRetailPrice(String.valueOf(retailPriceOrginal));		 
			invoicePartsVO.setFinalPayment(String.valueOf(finalPayment));
			invoicePartsVO.setRetailCostToInventory(String.valueOf(retailCostToInventory));
			invoicePartsVO.setRetailReimbursement(String.valueOf(retailReimbursement));
			invoicePartsVO.setRetailPriceSLGrossUp(String.valueOf(retailPriceSLGrossUp));
			invoicePartsVO.setRetailSLGrossUp(String.valueOf(retailSLGrossUp));
			invoicePartsVO.setNetPayment(String.valueOf(netPayment));			
			// set reimbursement max and provider margin
			invoicePartsVO.setReimbursementMax(String.valueOf(reimbursementMax));	
			invoicePartsVO.setProviderMargin(String.valueOf(providerMargin)); 
			invoicePartsVO.setReimbursementRate(String.valueOf(reimbursementRate));
		}
		catch(Exception e){
			logger.info("Exception in calculate Price of InvoiceParts Adjudication " +e);
			
		}
		return invoicePartsVO;
	}

	
	
	/**
	 *
	 * @param soId
	 * @return InvoicePartsVO
	 * @throws BusinessServiceException 
	 */
	public List<InvoicePartsVO>  checkIfPartExists(String soId) throws BusinessServiceException {
		 List<InvoicePartsVO>  invoicePartsVOList=null;
		try{
			
			invoicePartsVOList=mobileSOActionsDao.checkIfPartExists(soId);
		}catch (Exception ex) {
			logger.error("Exception Occured in MobileSOActionsBOImpl-->checkIfPartExists()");
			throw new BusinessServiceException(ex);
		}	
		return invoicePartsVOList;
		
	}
	public List<Integer> getAvailableInvoicePartIdList(String soId) throws BusinessServiceException {
		List<Integer> invoicePartIdList = null;
		try{
			invoicePartIdList = mobileSOActionsDao.getAvailableInvoicePartIdList(soId);
		}catch (Exception e) {
			logger.error("Exception Occured in MobileSOActionsBOImpl-->getAvailableInvoicePartIdList()" + e.getMessage());
			throw new BusinessServiceException(e);
		}
		return invoicePartIdList;
	}
	
	
	public void deleteInvoicePartDetail(List<Integer> partIdList, String soId)throws BusinessServiceException{
		try{
//			for(Integer partId:partIdList){				
//			mobileSOActionsDao.deleteInvoicePartDetail(partId);
//			}
			//SLM-88 
			//1.Get the part name and part number for the invoice id
			List<InvoicePartsVO> partDetailsList = new ArrayList<InvoicePartsVO>();
			partDetailsList = mobileSOActionsDao.getPartDeatilsForPartId(partIdList);
			
			mobileSOActionsDao.deleteInvoicePartDetail(partIdList);
			
			//2.Call the NPS for sending the message.
			deletePartNPSCall(partDetailsList,soId);
		
			}
		catch (Exception ex) {
			logger.error("Exception Occured in MobileSOActionsBOImpl-->deleteInvoicePartsDetail()");
			throw new BusinessServiceException(ex.getMessage());
			}
	}
	//SLM-88 deletePartNPSCall
	public void deletePartNPSCall(List<InvoicePartsVO> partDetailsList,String soId) throws BusinessServiceException{
		
		try{
			if(null!= partDetailsList && !(partDetailsList.isEmpty())){
				for(InvoicePartsVO partDetail:partDetailsList){
						updateNPSForDeletingPart(partDetail,soId);
						}
			}
		
		}
		catch (Exception ex) {
			logger.error("Exception Occured in MobileSOActionsBOImpl-->deletePartNPSCall()");
			throw new BusinessServiceException(ex.getMessage());
			}
	}

	//SLM-88 Calling NPS while deleting parts
	private void updateNPSForDeletingPart(InvoicePartsVO partDetail, String soId) {
		boolean isEligibleForNPSNotification = false;
		String partMessage = null;
		
		try{
    		//call validation method for NPS notification
			if(StringUtils.isNotEmpty(soId)){
				isEligibleForNPSNotification = notificationService.validateNPSNotificationEligibility(MPConstants.INHOME_BUYER,soId);
			}
			if(isEligibleForNPSNotification){
	    		//Creating message for NPS update
				partMessage= partDetail.getPartNumber()+MPConstants.WHITE_SPACE+MPConstants.HYPHEN+MPConstants.WHITE_SPACE+partDetail.getPartDescription()+MPConstants.PARTS_DELETE_MESSAGE;
				//Call Insertion method for NPS notification
				notificationService.insertNotification(soId,partMessage);
			}
    	}catch(Exception e){
			logger.info("Caught Exception while insertNotification"+e);
		}
		
	}

	public void insertInhomePartDetails(List<InvoicePartsVO> partlist,String soId,String userName) throws BusinessServiceException{
		 try{
			 
			 if(null!=partlist&&partlist.size()>0){				 
		      for(InvoicePartsVO partVO:partlist){
		    	 if(null!=partVO){
				 partVO.setSoId(soId);
				 partVO.setCreatedDate(new java.util.Date());
				 partVO.setModifiedDate(new java.util.Date());
				 partVO.setModifiedBy(userName);}								
			 }	
		      mobileSOActionsDao.insertInhomePartDetails(partlist);	
	 }	
			 
			 
			 
		    }catch(Exception e){
		    	logger.error("Exception when trying to insertInhomePartDetails");
		        throw new BusinessServiceException(e.getMessage());
		    }
    
	}
	
	public void updateInhomePartDetails(List<SupplierVO> supplierPartList,String soId,String userName) throws BusinessServiceException{
		 try{
			 List<Integer> invoiceIdList= new ArrayList<Integer>();
			 List<Integer> invoiceIdListIdswithLocation= new ArrayList<Integer>();
			 List<SupplierVO> invoiceIdListwithLocation= new ArrayList<SupplierVO>();
			 if(null!=supplierPartList && supplierPartList.size()>0){				 
				 for(SupplierVO supplierVO:supplierPartList){
					 if(null!=supplierVO){
						 mobileSOActionsDao.updateInhomePartDetails(supplierVO);	
						 invoiceIdList.add(supplierVO.getInvoiceId());
						 if(supplierVO.isLocationDetailPresent()){
							 invoiceIdListIdswithLocation.add(supplierVO.getInvoiceId());
							 invoiceIdListwithLocation.add(supplierVO);
						 }					 
				     }
				}
				 if(null!=invoiceIdListIdswithLocation && invoiceIdListIdswithLocation.size()>0){
					 mobileSOActionsDao.deletePartLocationDetails(invoiceIdListIdswithLocation);
				     mobileSOActionsDao.insertPartLocationDetails(invoiceIdListwithLocation);
				 }
				     
			 }			
		    }catch(Exception e){
		    	logger.error("Exception when trying to updateInhomePartDetails");
		        throw new BusinessServiceException(e.getMessage());
		    }
   
	}

	public HashMap<String, String> fetchApiDetails(String lpnServiceId) throws BusinessServiceException {
		HashMap<String, String> apiDetails = null;
		try{
			apiDetails = mobileSOActionsDao.fetchApiDetails(lpnServiceId);
		}catch (Exception e) {
			logger.error("Exception when trying to retrieve LPN Web service Details:"+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return apiDetails;
	}

	public Map<String, String> fetchLpnClientUrlHeader()throws BusinessServiceException {
		Map<String,String> resultMap = new HashMap<String,String>();
		List<LpnVO> resultList =null;
		try{
			resultList = mobileSOActionsDao.fetchLpnClientUrlHeader();
			   if(null!= resultList && !(resultList.isEmpty())){
				  for(LpnVO result :resultList){
					  if(null!= result && MPConstants.LPN_URL.equals(result.getAppKey())){
						  resultMap.put(MPConstants.LPN_URL,result.getAppValue());
					  }else if(null!= result && MPConstants.LPN_HEADER.equals(result.getAppKey())){
						  resultMap.put(MPConstants.LPN_HEADER, result.getAppValue());
					  }
				  }
			    }
		}catch (Exception e) {
			logger.error("Exception when trying to retrieve LPN Web service Details:"+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return resultMap;
	}

	public int getDocumentIdList(List<Integer> documentIdList)throws BusinessServiceException {
		int docCount = 0;
		List<Integer> documentId = null;
		try{
			documentId = mobileSOActionsDao.getDocumentIdList(documentIdList);
		}catch (Exception e) {
			logger.error("Exception in validatiing document Ids "+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		if(null != documentId && !(documentId.isEmpty()) ){
			docCount = documentId.size();
		}
		return docCount;
	}
	//validating if document are associated with so.
	public int getSoDocumentIdList(List<Integer> documentIdList,String soId)throws BusinessServiceException {
		int docCount = 0;
		List<Integer> documentList = null;
		try{
			documentList = mobileSOActionsDao.getSoDocumentIdList(documentIdList,soId);
		}catch (Exception e) {
			logger.error("Exception in validatiing document Ids "+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		if(null != documentList && !(documentList.isEmpty()) ){
			docCount = documentList.size();
		}
		return docCount;
	}
	public Integer validateLatestOpenTrip(Integer curTripNo, String soId)throws BusinessServiceException {
		SoTripVo latestOpenTripVo=null;
		Integer returnTripId = null;
		try{
			latestOpenTripVo = mobileSOActionsDao.getLatestOpenTrip(soId);
		}catch (Exception e) {
			logger.error("Exception in validating Trip Number"+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		if(null!= latestOpenTripVo && null!= latestOpenTripVo.getTripNo()
				&& latestOpenTripVo.getTripNo().equals(curTripNo)){
			returnTripId = latestOpenTripVo.getTripId();
		}
		return returnTripId;
	}
	
	public void insertPartsRequiredInd(String soId,String requiredType)throws BusinessServiceException {
		try{
			 mobileSOActionsDao.insertPartsRequiredInd(soId,requiredType);
		}catch (Exception e) {
			logger.error("Exception inserting parts required indicator"+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}	
	}
	public List<Integer> getAssociatedDocuments(List<Integer> inputList, boolean afterDeletion) throws BusinessServiceException{
		try{
			 return mobileSOActionsDao.getAssociatedDocuments(inputList, afterDeletion);
		}catch (Exception e) {
			logger.error("Exception getAssociatedDocumentsr"+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}	
	}
	public void deleteDocumentFromParentTables(List<Integer> documentIdList)throws BusinessServiceException{
		try{
			mobileSOActionsDao.deleteDocumentFromParentTables(documentIdList);
		}
		catch (Exception ex) {
			logger.error("Exception Occured in MobileSOActionsBOImpl-->deleteDocumentFromParentTables()");
			throw new BusinessServiceException(ex.getMessage());
			}
	}
	
	
	public Boolean  getAutoAdjudicationInd() throws BusinessServiceException{
		try{
		Boolean autoAdjudicationInd= mobileSOActionsDao.getAutoAdjudicationInd();
		return autoAdjudicationInd;
		}
		catch(Exception e){
			logger.error("Exception Occured in MobileSOActionsBOImpl-->getAutoAdjudicationInd()");
			throw new BusinessServiceException(e.getMessage());
		}
	}
	
	public String   getInvoicePartInd(String soId)  throws BusinessServiceException{
		try{
			String  invoicePartInd= mobileSOActionsDao.getInvoicePartInd(soId);
			return invoicePartInd;
			}
			catch(Exception e){
				logger.error("Exception Occured in MobileSOActionsBOImpl-->getInvoicePartInd()");
				throw new BusinessServiceException(e.getMessage());
			}
	}

	
	//Method to fetch existing part details for UpdateSOProviderPart API
	public List<SupplierVO> getExistingInvoicePartStatusList(String soId)
			throws BusinessServiceException {
		List<SupplierVO> invoicePartStatusList = new ArrayList<SupplierVO>();
		try{
			invoicePartStatusList = mobileSOActionsDao.getExistingInvoicePartStatusList(soId);
		}catch (Exception e) {
			logger.error("Exception Occured in MobileSOActionsBOImpl-->getExistingInvoicePartStatusList()" + e.getMessage());
			throw new BusinessServiceException(e);
		}
		return invoicePartStatusList;
	}
	//Method to fetch existing part details for addInvoiceSOProviderPart API
	public List<InvoicePartsVO> getExistingPartStatusList(String soId)
			throws BusinessServiceException {
		List<InvoicePartsVO> invoicePartStatusList = new ArrayList<InvoicePartsVO>();
		try{
			invoicePartStatusList = mobileSOActionsDao.getExistingPartStatusList(soId);
		}catch (Exception e) {
			logger.error("Exception Occured in MobileSOActionsBOImpl-->getExistingPartStatusList()" + e.getMessage());
			throw new BusinessServiceException(e);
		}
		return invoicePartStatusList;
	}
	/**
	 * @param soId
	 * @return
	 * 
	 * method to fetch the min and max window for the buyer
	 * 
	 */
	public UpdateApptTimeVO getBuyerMinAndMaxTimeWindow(String soId) throws BusinessServiceException{
		UpdateApptTimeVO timeWindowVO=null;
		try{
			timeWindowVO = mobileSOActionsDao.getBuyerMinAndMaxTimeWindow(soId);
		}catch (Exception e) {
			logger.error("Exception in getBuyerMinAndMaxTimeWindow"+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		
		return timeWindowVO;
	}
	/**
	 * R12.0 updates parts indicator in so_workflow_controls for UpdateSoCompletion v1.0 flow
	 * On Delete invoice details, update the indicator to NO_PARTS_ADDED
	 * On Parts added, update the indicator to PARTS_ADDED
	 * @param soId
	 * @param invoicepartsInd
	 * @throws BusinessServiceException
	 */
	public void updateInvoicePartsInd(String soId, String invoicepartsInd) throws BusinessServiceException{
		try{
			mobileSOActionsDao.updateInvoicePartsInd(soId,invoicepartsInd);
		}catch (DataServiceException dataEx) {
			logger.error("DataServiceException in MobileSOActionsBOImpl.updateInvoicePartsInd"+ dataEx.getMessage());
			throw new BusinessServiceException(dataEx.getMessage());
		}catch (Exception e) {
			logger.error("Exception in MobileSOActionsBOImpl.updateInvoicePartsInd"+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
	}

	public void associatingInvoicePartDocument(String invoiceId,List<Integer> docIdList) throws BusinessServiceException {
	try{
		 mobileSOActionsDao.associatingInvoicePartDocument(invoiceId,docIdList);
	}catch (Exception e) {;
		logger.error("Exception in associating invoice documents"+ e.getMessage());
		throw new BusinessServiceException(e.getMessage());
	  }
	}
	
	public String getConstantValueFromDB(String appkey) throws BusinessServiceException{
		try{
			return mobileSOActionsDao.getConstantValueFromDB(appkey);
		}catch (DataServiceException dataEx) {
			logger.error("DataServiceException in MobileSOActionsBOImpl.getCommercialPricePercentageFromDB"+ dataEx.getMessage());
			throw new BusinessServiceException(dataEx.getMessage());
		}catch (Exception e) {
			logger.error("Exception in MobileSOActionsBOImpl.getCommercialPricePercentageFromDB"+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
	}
	
	public void saveInvoicePartPricingModel(String soID, String pricingModel) throws BusinessServiceException{
		try{	
			mobileSOActionsDao.saveInvoicePartPricingModel(soID,pricingModel);
		}catch (Exception e) {
			logger.error("Caught Exception while saveInvoicePartPricingModel"+e);
			throw new BusinessServiceException(e.getMessage());
		}	
	}
	
	public String getInvoicePartsPricingModel(String soId) throws BusinessServiceException{
		try{
			String  invoicePricingModel= mobileSOActionsDao.getInvoicePartsPricingModel(soId);
			return invoicePricingModel;
			}
			catch(Exception e){
				logger.error("Exception Occured in MobileSOActionsBOImpl-->getInvoicePartsPricingModel()");
				throw new BusinessServiceException(e.getMessage());
			}
	}
	/**
	 * @param soId
	 * @param curTripNo
	 * @return
	 * 
	 * method to update trip status
	 * 
	 */
	public void updateTripStatus(Integer curTripId,String tripStatus) throws BusinessServiceException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("tripId", curTripId);
		map.put("status",tripStatus);
		try{
			mobileSOActionsDao.updateTripStatus(map);
		}
		catch(DataServiceException e){
			logger.error("Exception occured in updateTripStatus() due to "+e);
			throw new BusinessServiceException(e.getMessage());
		}
	}
	/**
	 * @param soId
	 * @param curTripNo
	 * @return
	 * 
	 * method to obtain trip id
	 * 
	 */	
	public Integer getTripId(Integer curTripNo, String soId)throws BusinessServiceException {
		Integer returnTripId = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("soId", soId);
		map.put("curTripNo", curTripNo);
		try{
			returnTripId = mobileSOActionsDao.getTripId(map);
		}catch (Exception e) {
			logger.error("Exception in obtaining Trip ID"+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return returnTripId;
	}

	 /**
	  * @param paymentDetails
	  * @return
	  */
	public boolean mapPaymentDetails(AddOnPaymentDetails paymentDetails,HSCreditCardResponse hSCreditCardResponse,String userName)throws BusinessServiceException{

		boolean isTokenizeResponseValid = false;
		String hsResponseData =null;
		try {		
		
			String encryptFlag = null;
	    	
			if(null!=paymentDetails){
				if(MPConstants.CREDIT_CARD.equalsIgnoreCase(paymentDetails.getPaymentType())){
				encryptFlag = creditCardDao.getEncryptFlag(CommonConstants.ADDITIONAL_PAYMENT_ENCRYPTION_FLAG);//checking if the paymenttype is credit card
	        	
	        	if(null!=hSCreditCardResponse && null!= hSCreditCardResponse.getMaskedAccount() && null!= hSCreditCardResponse.getToken()){//returning error if tokenization response is invalid
	        		isTokenizeResponseValid = true;
	        	}else{
	        		return isTokenizeResponseValid;
	        	}
			}else{
				isTokenizeResponseValid = true; //Tokenization response not needed in case of cheque
			}
	        	AddOnPaymentDetails addonpaymentDetails=new AddOnPaymentDetails();
			    //to insert history details
			    AddOnPaymentHistoryDetails addOnPaymentHistoryDetails = new AddOnPaymentHistoryDetails();
			    addonpaymentDetails.setAmountAuthorized(paymentDetails.getAmountAuthorized());
			    addonpaymentDetails.setSoId(paymentDetails.getSoId());
	            /*if (paymentDetails.getPaymentType().equalsIgnoreCase(
					MPConstants.CREDIT_CARD)) {*/
				if(MPConstants.CREDIT_CARD.equalsIgnoreCase(paymentDetails.getPaymentType())){
				// to update as payment type
				if (StringUtils.isNotBlank(paymentDetails.getCcType())) {
					String paymentTypeAlias = mobileSOActionsDao
							.getCreditCardAlias(paymentDetails.getCcType());
					// this is to set as payment type in db
					addonpaymentDetails.setPaymentType(paymentTypeAlias);
					
					// to update as card type
				}
				if (StringUtils.isNotBlank(paymentDetails.getCcType())) {
					String cardType = mobileSOActionsDao.getCreditCardID(
							paymentDetails.getCcType()).toString();
					addonpaymentDetails.setCcType(cardType);
				
				}
				
				if (StringUtils.isNotBlank(paymentDetails.getExpirationDate())) { 
					String[] array = paymentDetails.getExpirationDate().split(
							"/");
					addonpaymentDetails.setMonth(new Integer(array[0]));
					addonpaymentDetails.setYear(new Integer(array[1]));
				}
				addonpaymentDetails.setPreAuthNumber(paymentDetails.getPreAuthNumber());
				//PCI-Vault II
				if(null!=hSCreditCardResponse){
					addonpaymentDetails.setCcNumber(null);
					addonpaymentDetails.setMaskedAccNum(hSCreditCardResponse.getMaskedAccount());
					addonpaymentDetails.setToken(hSCreditCardResponse.getToken());
				}
				
				//insertion to history table
				
				addOnPaymentHistoryDetails.setAmountAuthorized(paymentDetails.getAmountAuthorized());
				addOnPaymentHistoryDetails.setSoId(paymentDetails.getSoId());
				
				if (StringUtils.isNotBlank(paymentDetails.getCcType())) {
					String paymentTypeAlias = mobileSOActionsDao
							.getCreditCardAlias(paymentDetails.getCcType());
					// this is to set as payment type in db
					addOnPaymentHistoryDetails.setPaymentType(paymentTypeAlias);
					
					// to update as card type
				}
				if (StringUtils.isNotBlank(paymentDetails.getCcType())) {
					String cardType = mobileSOActionsDao.getCreditCardID(
							paymentDetails.getCcType()).toString();
					addOnPaymentHistoryDetails.setCcType(cardType);
				
				}
				if (StringUtils.isNotBlank(paymentDetails.getCcNumber())) {
					//SL 20853 encrypting additional payment based on flag
					if(StringUtils.isNotBlank(encryptFlag) && encryptFlag.equalsIgnoreCase(CommonConstants.CC_ENCRYPTION_ON)){
					addOnPaymentHistoryDetails.setCcNumber(encryptCreditCardInfo(paymentDetails.getCcNumber()));
					}
					else {
						if(null!= hSCreditCardResponse && StringUtils.isBlank(hSCreditCardResponse.getToken())){
							addOnPaymentHistoryDetails	.setCcNumber(encryptCreditCardInfo(paymentDetails.getCcNumber()));
						}else{
							addOnPaymentHistoryDetails.setCcNumber(null);
						}
						
					}
							
				}	
				if (StringUtils.isNotBlank(paymentDetails.getExpirationDate())) {
					String[] array = paymentDetails.getExpirationDate().split(
							"/");
					addOnPaymentHistoryDetails.setMonth(new Integer(array[0]));
					addOnPaymentHistoryDetails.setYear(new Integer(array[1]));
				}
				addOnPaymentHistoryDetails.setPreAuthNumber(paymentDetails.getPreAuthNumber());
				if(null!=hSCreditCardResponse){
					
					addOnPaymentHistoryDetails.setMaskedAccNum(hSCreditCardResponse.getMaskedAccount());
					addOnPaymentHistoryDetails.setToken(hSCreditCardResponse.getToken());
					hsResponseData ="CorrelationId : " + hSCreditCardResponse.getCorrelationId() + " , ResponseCode :" 
					+ hSCreditCardResponse.getResponseCode() + " , ResponseMessage : " + hSCreditCardResponse.getResponseMessage() + " , addlResponseData : " 
					+ hSCreditCardResponse.getAddlResponseData() + " , transAmt : " +hSCreditCardResponse.getTransAmt() + " , token : " +hSCreditCardResponse.getToken()
					+ " , ajbKey :" + hSCreditCardResponse.getAjbKey()	; 
						
					addOnPaymentHistoryDetails.setResponse(hsResponseData);
				}
			} else {
				addonpaymentDetails.setCheckNumber(paymentDetails.getCheckNumber());
				addonpaymentDetails.setPaymentType(MPConstants.PAYMENT_TYPE_CHECK);
				//Updating History Details
				addOnPaymentHistoryDetails = new AddOnPaymentHistoryDetails();
				addOnPaymentHistoryDetails.setSoId(paymentDetails.getSoId());
				addOnPaymentHistoryDetails.setCheckNumber(paymentDetails.getCheckNumber());
				addOnPaymentHistoryDetails.setAmountAuthorized(paymentDetails.getAmountAuthorized());
				addOnPaymentHistoryDetails.setPaymentType(MPConstants.PAYMENT_TYPE_CHECK);
				
			}		
			
			//delete and new insert is done
			mobileSOActionsDao.deleteAdditionalPaymentDetails(addonpaymentDetails.getSoId());
			mobileSOActionsDao.insertPaymentDetails(addonpaymentDetails);
			//insert into history table
			if(null!=addOnPaymentHistoryDetails){
				try{
				mobileSOActionsDao.insertPaymentHistoryDetails(addOnPaymentHistoryDetails);
				}catch (Exception e) {
					logger.info("Caught Exception in persisting additional Payment History and ignoring");
					e.printStackTrace();
				}
			}	
		}
			
			} catch (DataServiceException e) {
				logger.error("Exception occured in mapPaymentDetails() due to "
						+ e.getMessage());
				throw new BusinessServiceException(e.getMessage());
			}
		return isTokenizeResponseValid;
	}
	
	
	
	public HSTokenizeServiceCreditCardBO getHsTokenServiceCreditCardBo() {
		return hsTokenServiceCreditCardBo;
	}

	public void setHsTokenServiceCreditCardBo(
			HSTokenizeServiceCreditCardBO hsTokenServiceCreditCardBo) {
		this.hsTokenServiceCreditCardBo = hsTokenServiceCreditCardBo;
	}
	
	

	public ICreditCardDao getCreditCardDao() {
		return creditCardDao;
	}

	public void setCreditCardDao(ICreditCardDao creditCardDao) {
		this.creditCardDao = creditCardDao;
	}

	public String getVendorBNameList(List<Integer> vendorIds) {
		return mobileSOActionsDao.getVendorBNameList(vendorIds);
	}
}
		
