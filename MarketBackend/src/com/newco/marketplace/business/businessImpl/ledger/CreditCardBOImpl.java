/**
 * 
 */
package com.newco.marketplace.business.businessImpl.ledger;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.SecretKey;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.financeManager.IFinanceManagerBO;
import com.newco.marketplace.business.iBusiness.ledger.ICreditCardBO;
import com.newco.marketplace.dto.vo.CryptographyVO;
import com.newco.marketplace.dto.vo.ledger.CreditCardVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.CreditCardConstants;
import com.newco.marketplace.interfaces.MPConstants;
import com.newco.marketplace.persistence.iDao.ledger.ICreditCardDao;
import com.newco.marketplace.persistence.iDao.provider.ILocationDao;
import com.newco.marketplace.util.Cryptography;
import com.newco.marketplace.util.Cryptography128;
import com.newco.marketplace.utils.RandomGUID;
import com.newco.marketplace.utils.ServiceLiveStringUtils;
import com.newco.marketplace.vo.provider.Location;
import com.sears.os.business.ABaseBO;
import com.servicelive.common.CommonConstants;
import com.servicelive.common.vo.SLCreditCardVO;
import com.servicelive.lookup.ILookupBO;
import com.servicelive.lookup.vo.BuyerLookupVO;
import com.servicelive.wallet.serviceinterface.IWalletBO;
import com.servicelive.wallet.serviceinterface.requestbuilder.IWalletRequestBuilder;
import com.servicelive.wallet.serviceinterface.requestbuilder.WalletRequestBuilder;
import com.servicelive.wallet.serviceinterface.vo.WalletResponseVO;
import com.servicelive.wallet.serviceinterface.vo.WalletVO;


/**
 * @author schavda
 *
 */
public class CreditCardBOImpl  extends ABaseBO implements ICreditCardBO, MPConstants, CreditCardConstants {
	
	private static final Logger logger = Logger.getLogger(CreditCardBOImpl.class);
	private Cryptography cryptography;
	private ICreditCardDao creditCardDao;
	private ILocationDao locationDao;
	private IFinanceManagerBO financeManagerBO;
	private AccountingTransactionMananagementBO accountingTransactionBO;
	
	private IWalletBO walletBO;
	private IWalletRequestBuilder walletRequestBuilder = new WalletRequestBuilder();
	private ILookupBO lookupBO;
	
	private Cryptography128 cryptography128;

	public CreditCardVO getCardDetails(CreditCardVO creditCardVO) throws BusinessServiceException {
		logger.debug("CreditCardBOImpl-->getCardDetails()");
		
		try{
			if(creditCardVO.isActive_ind()) {
				creditCardVO = creditCardDao.getCardDetails(creditCardVO);
			} else {
				creditCardVO = creditCardDao.getCardDetailsAll(creditCardVO);
			}
			
			CryptographyVO cryptographyVO = new CryptographyVO();
			cryptographyVO.setInput(creditCardVO.getEncCardNo());
				
			//Commenting the code for SL-18789
			//cryptographyVO.setKAlias(ENCRYPTION_KEY);
			//cryptographyVO = cryptography.decryptKey(cryptographyVO);
			cryptographyVO.setKAlias(CC_ENCRYPTION_KEY);
			cryptographyVO = cryptography128.decryptKey128Bit(cryptographyVO);
			
			logger.debug("CreditCardBOImpl-->getCardDetails()-->DecypteddNo="+cryptographyVO.getResponse());
			creditCardVO.setCardNo(cryptographyVO.getResponse());
			
		}
		catch (Throwable t) {
			logger.error("CreditCardBOImpl-->getCardDetails()-->Exception-->" + t.getMessage(), t);
			throw new BusinessServiceException("Exception occured in CreditCardBOImpl-->getCardDetails() due to "+t.getMessage());
		}
		return creditCardVO;
	}

	public void setWalletBO(IWalletBO walletBO) {
		this.walletBO = walletBO;
	}

	public void setLookupBO(ILookupBO lookupBO) {
		this.lookupBO = lookupBO;
	}

	public Long saveCardDetails(CreditCardVO creditCardVO) throws BusinessServiceException {
		logger.debug("CreditCardBOImpl-->saveCardDetails()");
		CreditCardVO oldCreditVO = new CreditCardVO();
		Long cardId = null;

		try{
			cardId = setCardDetails(creditCardVO);
			WalletResponseVO response = doDollarAuth(creditCardVO);
			if (response.getCreditCard().isAuthorized()){
				if(creditCardVO != null && creditCardVO.getCardId() != null
						&& creditCardVO.isActive_ind()){
					oldCreditVO.setCardId(creditCardVO.getCardId());
					oldCreditVO.setActive_ind(false);
					oldCreditVO.setEntityId(creditCardVO.getEntityId());
					//oldCreditVO.setEnabled_ind(true);
					creditCardDao.updateDeactivateCreditCardAccountInfo(oldCreditVO);
				}
		        saveLocation(creditCardVO);
		        if(null!=response && response.getCreditCard()!=null){
		        	//setting masked account num and ajb key to creditCardVO to persist in account_hdr
		        creditCardVO.setMaskedAccount(response.getCreditCard().getMaskedAccount());
		        creditCardVO.setToken(response.getCreditCard().getToken());
	        	creditCardDao.saveCardDetails(creditCardVO);
	        	logger.info("CreditCardBOImpl-->saveCardDetails() --masked_acc_num"+creditCardVO.getMaskedAccount());
	        	logger.info("CreditCardBOImpl-->saveCardDetails() --ajb_key"+creditCardVO.getAjbKey());
		        }
			}
	    }
		catch (Throwable t) {
			logger.error("CreditCardBOImpl-->saveCardDetails()-->Exception-->" + t.getMessage(), t);
			throw new BusinessServiceException("Exception occured in CreditCardBOImpl-->saveCardDetails() due to "+t.getMessage());
		}
		return cardId;
	}

	/**
	 * Description:  Builds request and does auth for the card for 1 dollar and CVV number
	 * @param creditCardVO
	 * @return
	 * @throws BusinessServiceException
	 */
	private WalletResponseVO doDollarAuth(CreditCardVO creditCardVO) throws BusinessServiceException {
		WalletVO request = walletRequestBuilder.authCCForDollarNoCVV(201.00, creditCardVO.getCardVerificationNo(), 
				creditCardVO.getCardTypeId(), creditCardVO.getExpireDate(), creditCardVO.getCardNo(), 
				creditCardVO.getZipcode(), creditCardVO.getBillingAddress1(), creditCardVO.getBillingAddress2());
		request.setCreditCard(SLCreditCardVO.copy(creditCardVO));
		WalletResponseVO response = walletBO.authCCForDollarNoCVV(request);
		return response;
	}

	
	/* 
	 * B2C : Saves the card as a default card.It reset the previous default card before setting a new one.
	 * This is introduced for public APIs.
	 */
	public Long saveAsDefaultCard(CreditCardVO creditCardVO) throws BusinessServiceException {
		logger.debug("CreditCardBOImpl-->saveCardDetails()");
		CreditCardVO oldCreditVO = new CreditCardVO();
		Long cardId = null;

		try{
			cardId = setCardDetails(creditCardVO);
			WalletResponseVO response = doDollarAuth(creditCardVO);
			
			if (response.getCreditCard().isAuthorized()){
				if(creditCardVO != null && creditCardVO.isActive_ind()){
					oldCreditVO = creditCardDao.getActiveDefaultCardForBuyer(new Integer(creditCardVO.getEntityId()));
					if (oldCreditVO != null){
						oldCreditVO.setDefault_ind(false);
						creditCardDao.resetDefaultCreditCard(oldCreditVO);
					}
				}
		        saveLocation(creditCardVO);
	        	creditCardDao.saveCardDetails(creditCardVO);
			}else{
				throw new BusinessServiceException("CreditCardBOImpl-->saveCardDetails()--> Could not save card due to failed authorization.-->");
			}
	    }
		catch (Throwable t) {
			logger.error("CreditCardBOImpl-->saveCardDetails()-->Exception-->" + t.getMessage(), t);
			throw new BusinessServiceException("Exception occured in CreditCardBOImpl-->saveCardDetails() due to "+t.getMessage());
		}
		return cardId;
	}

	/**
	 * Description: Set encrypted card No in VO for persisting in the DB.  Set modified and created date.
	 * set randomly generated cardId (should be replaced by auto-numbered field).
	 * 
	 * @param creditCardVO
	 * @return <code>Long</code>  
	 * @throws Exception
	 */
	private Long setCardDetails(CreditCardVO creditCardVO) throws Exception {
		Long cardId;
		CryptographyVO cryptographyVO = new CryptographyVO();
		try{
			cryptographyVO.setInput(creditCardVO.getCardNo());
			
			//Commenting the code for SL-18789
			//cryptographyVO.setKAlias(ENCRYPTION_KEY);
			//cryptographyVO = cryptography.encryptKey(cryptographyVO);
			
			//SL20853 to do encryption based on credit card encryption flag
			String ccEncryptFlag = creditCardDao.getEncryptFlag(CommonConstants.CC_ENCRYPTION_FLAG);
			if (StringUtils.isNotBlank(ccEncryptFlag) &&  ccEncryptFlag.equalsIgnoreCase(CommonConstants.CC_ENCRYPTION_ON))
			{
				cryptographyVO.setKAlias(CC_ENCRYPTION_KEY);
				cryptographyVO = cryptography128.encryptKey128Bit(cryptographyVO);
				logger.info("CreditCardBOImpl-->saveCardDetails()-->EncrypteddNo="+cryptographyVO.getResponse());
				creditCardVO.setEncCardNo(cryptographyVO.getResponse());
			}
			else {
				creditCardVO.setEncCardNo(null);
			}
		
			RandomGUID randomGUID = new RandomGUID();
			cardId = randomGUID.generateGUID();
			creditCardVO.setCardId(cardId);

			java.util.Date today = new java.util.Date();
			Date now = new Date(today.getTime());
			creditCardVO.setCreatedDate(now);
			creditCardVO.setModifiedDate(now);
			return cardId;
		}
		catch (BusinessServiceException t) {
			logger.error("CreditCardBOImpl-->setCardDetails()-->Exception-->" + t.getMessage(), t);
			throw new BusinessServiceException("Exception occured in CreditCardBOImpl-->setCardDetails() due to "+t.getMessage());
		}
	}

	/**
 	 * Description: Saves location associated with CC.
	 * 
	 * @param creditCardVO
	 * @throws BusinessServiceException
	 */
	private void saveLocation(CreditCardVO creditCardVO) throws BusinessServiceException {
		try{
			
			Location location = new Location();
			location.setStreet1(creditCardVO.getBillingAddress1());
			location.setStreet2(creditCardVO.getBillingAddress2());
			location.setAptNo(creditCardVO.getBillingAptNo());
			location.setCity(creditCardVO.getBillingCity());
			location.setStateCd(creditCardVO.getBillingState());
			location.setZip(creditCardVO.getZipcode());
			location.setLocnTypeId(creditCardVO.getLocationTypeId());
			location.setLocnName(creditCardVO.getBillingLocationName());
			Location returnlocation= locationDao.insert(location);
			creditCardVO.setLocationId(returnlocation.getLocnId());
		}
		catch (Throwable t) {
			logger.error("CreditCardBOImpl-->insertCardLocationDetails()-->Exception-->" + t.getMessage(), t);
			throw new BusinessServiceException("Exception occured in CreditCardBOImpl-->saveCardDetails() due to "+t.getMessage());
		}
	}
	
	public boolean updateDeactivateCreditCardAccountInfo(CreditCardVO creditCardVO) throws BusinessServiceException {
		boolean isDeactivated;
		try {
			isDeactivated = creditCardDao.updateDeactivateCreditCardAccountInfo(creditCardVO);
        }
        catch (Throwable t) {
			logger.error("CreditCardBOImpl-->updateDeactivateCreditCardAccountInfo()-->Exception-->" + t.getMessage(), t);
			throw new BusinessServiceException("Exception occured in CreditCardBOImpl-->updateDeactivateCreditCardAccountInfo() due to "+t.getMessage());
        }
        
        return isDeactivated;
	}

	public ArrayList<CreditCardVO> getCardsForEntity(CreditCardVO creditCardVO) throws BusinessServiceException{
		logger.debug("CreditCardBOImpl-->getCardDetails()");
		ArrayList alCards = null;
		CreditCardVO cardVO = null;
		try{
			alCards = creditCardDao.getCardsForEntity(creditCardVO);
			int iSize = alCards.size();
			
            CryptographyVO cryptographyVO = new CryptographyVO();
    	    cryptographyVO.setKAlias(MPConstants.CC_ENCRYPTION_KEY);        
        	SecretKey secret = cryptography128.generateSecretKey(cryptographyVO);
        	
			for(int i=0; i<iSize; i++){
				cardVO = (CreditCardVO)alCards.get(i);

				cryptographyVO = new CryptographyVO();
				
				//cryptographyVO.setKAlias(ENCRYPTION_KEY);
				cryptographyVO.setInput(cardVO.getEncCardNo());
				
				//Commenting the code for SL-18789
				//cryptographyVO = cryptography.decryptKey(cryptographyVO);
				//cryptographyVO.setKAlias(CC_ENCRYPTION_KEY);
				cryptographyVO = cryptography128.decryptWithSecretKey(cryptographyVO, secret);
				
				logger.debug("CreditCardBOImpl-->getCardsForEntity()-->DecypteddNo="+cryptographyVO.getResponse());
				cardVO.setCardNo(cryptographyVO.getResponse());
			}
		}
		catch (Throwable t) {
			logger.error("CreditCardBOImpl-->getCardDetails()-->Exception-->" + t.getMessage(), t);
			throw new BusinessServiceException("Exception occured in CreditCardBOImpl-->getCardsForEntity() due to "+t.getMessage());
		}
		return alCards;
	}
		
	private String decrypt( String cypher ) {
		CryptographyVO cryptographyVO = new CryptographyVO();
		
		cryptographyVO.setKAlias(ENCRYPTION_KEY);
		cryptographyVO.setInput(cypher);
		cryptographyVO = cryptography.decryptKey(cryptographyVO);
		return cryptographyVO.getResponse();
	}
	
	public CreditCardVO getActiveCreditCardDetails(Integer entityId) throws BusinessServiceException{
		logger.debug("CreditCardBOImpl-->getCardDetails()");
		CreditCardVO cardVO = null;
		try{
			cardVO = creditCardDao.getActiveCardsForBuyer(entityId);
			if (cardVO != null){
				CryptographyVO cryptographyVO = new CryptographyVO();
				
				//SL-20853
				
				if(StringUtils.isNotBlank(cardVO.getMaskedAccount())){
					logger.info("Masked Account Number is displaying"+cardVO.getMaskedAccount());
					cardVO.setCardNo(ServiceLiveStringUtils.maskStringAlphaNum(cardVO.getMaskedAccount(), 4, "X"));
				}
				else{
					//cryptographyVO.setKAlias(ENCRYPTION_KEY);
					cryptographyVO.setInput(cardVO.getEncCardNo());
				
					//Commenting the code for SL-18789
					//cryptographyVO = cryptography.decryptKey(cryptographyVO);
					cryptographyVO.setKAlias(CC_ENCRYPTION_KEY);
					cryptographyVO = cryptography128.decryptKey128Bit(cryptographyVO);
				
					logger.debug("CreditCardBOImpl-->getCardsForEntity()-->DecypteddNo="+cryptographyVO.getResponse());
					cardVO.setCardNo(cryptographyVO.getResponse());
				}
			}
		}
		catch (Throwable t) {
			logger.error("CreditCardBOImpl-->getCardDetails()-->Exception-->" + t.getMessage(), t);
			throw new BusinessServiceException("Exception occured in CreditCardBOImpl-->getActiveCreditCardDetails() due to "+t.getMessage());
		}
		return cardVO;
	}
	
	/* 
	 * Used by B2C API : This method returns all the active cards.Active cards are :
	 * 1. Active_ind = true and enabled_ind = false , this means card is not authorized by thirdparty yet but passed mod10 
	 * check on the client.
	 * 2. Active_ind= true and enabled_ind = true and response code is one of the below.  
	 * 
	 */
	public List<CreditCardVO> getActiveCardListForBuyer(Integer entityId) throws BusinessServiceException{
		logger.debug("CreditCardBOImpl-->getCardDetails()");
		//List<CreditCardVO> listEnabledCardVO = new ArrayList<CreditCardVO>();
		List<CreditCardVO> listAuthorizedCardVO = new ArrayList<CreditCardVO>();
		List<CreditCardVO> listAllValidCardVO = new ArrayList<CreditCardVO>();
		try{
			//listEnabledCardVO = creditCardDao.getAllEnabledCards(entityId);
			listAuthorizedCardVO = creditCardDao.getAllActiveAndAuthorizedCards(entityId);
			
			//Filter the authorized list to remove all the unauthorized cards based on the response.
			//This doesn't rely on authorized_ind from account_auth_resp as B2C requirement is different. 
			/*for(CreditCardVO cleanVO: listAuthorizedCardVO){
				if(!(cleanVO.getAnsiResponseCode().equalsIgnoreCase("00") || 
						cleanVO.getAnsiResponseCode().equalsIgnoreCase("10") ||
						cleanVO.getCidResponseCode().equalsIgnoreCase("M") ||
						cleanVO.getCidResponseCode().equalsIgnoreCase("P") ||
						cleanVO.getCidResponseCode().equalsIgnoreCase("S") ||
						cleanVO.getCidResponseCode().equalsIgnoreCase("U"))){
					listAuthorizedCardVO.remove(cleanVO);
				}
			}*/
			/* The above if fails and throws incase of ansicode or cid code is null 
			 * The following checks for the null before it compares.
			 * */
			for(CreditCardVO cleanVO: listAuthorizedCardVO){
				if((cleanVO.getAnsiResponseCode()!=null && 
						!(cleanVO.getAnsiResponseCode().equalsIgnoreCase("00") || 
								cleanVO.getAnsiResponseCode().equalsIgnoreCase("10"))) || 
				   (cleanVO.getCidResponseCode()!=null  && 
				   		!(cleanVO.getCidResponseCode().equalsIgnoreCase("M")   || 
				   				cleanVO.getCidResponseCode().equalsIgnoreCase("P")  ||
				   				cleanVO.getCidResponseCode().equalsIgnoreCase("S")  || 
				   				cleanVO.getCidResponseCode().equalsIgnoreCase("U")))){
						listAuthorizedCardVO.remove(cleanVO);
				}
			}
			
			//listAllValidCardVO.addAll(listEnabledCardVO);
			listAllValidCardVO.addAll(listAuthorizedCardVO);
			
            CryptographyVO cryptographyVO = new CryptographyVO();
    	    cryptographyVO.setKAlias(CC_ENCRYPTION_KEY);
        	SecretKey secret = cryptography128.generateSecretKey(cryptographyVO);

        	for(CreditCardVO ccVO: listAllValidCardVO){
				
				cryptographyVO = new CryptographyVO();
				
				//cryptographyVO.setKAlias(ENCRYPTION_KEY);
				cryptographyVO.setInput(ccVO.getEncCardNo());
				
				//Commenting the code for SL-18789
				//cryptographyVO = cryptography.decryptKey(cryptographyVO);
				//cryptographyVO.setKAlias(CC_ENCRYPTION_KEY);
				cryptographyVO = cryptography128.decryptWithSecretKey(cryptographyVO, secret);
				
				logger.debug("CreditCardBOImpl-->getCardsForEntity()-->DecypteddNo="+cryptographyVO.getResponse());
				ccVO.setCardNo(cryptographyVO.getResponse());
			}
		}
		catch (Throwable t) {
			logger.error("CreditCardBOImpl-->getCardDetails()-->Exception-->" + t.getMessage(), t);
			throw new BusinessServiceException("Exception occured in CreditCardBOImpl-->getActiveCreditCardDetails() due to "+t.getMessage());
		}
		return listAllValidCardVO;
	}
	
	public CreditCardVO saveAndAuthorizeCardTransaction(CreditCardVO creditCardVO, String soId, String roleType) throws BusinessServiceException{
		logger.debug("CreditCardBOImpl-->saveAndAuthorizeCardTransaction()");
		Long cardId = creditCardVO.getCardId();
		try{
			cardId = saveCardDetails(creditCardVO);
			
			creditCardVO.setCardId(cardId);
			
			creditCardVO = authorizeCardTransaction(creditCardVO, soId, roleType);
			
		}catch (Exception e) {
			logger.error("CreditCardBOImpl-->saveAndAuthorizeCardTransaction()-->Exception-->" + e.getMessage(), e);
			
			throw new BusinessServiceException("Exception occured in CreditCardBOImpl-->saveAndAuthorizeCardTransaction() due to "+e.getMessage());
		}

		return creditCardVO;
	}

	public CreditCardVO authorizeCardTransaction(CreditCardVO creditCardVO, String soId, String roleType) throws BusinessServiceException{
		Long cardId = creditCardVO.getCardId();
		if (cardId == null) {
			logger.error("Invalid Credit Card");
			throw new BusinessServiceException ("Exception occured in CreditCardBOImpl-->saveAndAuthorizeCardTransaction()- Invalid Credit Card");
		}
		
		Long buyerId = creditCardVO.getEntityId().longValue();
		BuyerLookupVO buyerVO = lookupBO.lookupBuyer(buyerId);

		SLCreditCardVO slCreditCardVO = SLCreditCardVO.adapt(lookupBO.lookupCreditCard(creditCardVO.getCardId()));
		slCreditCardVO.setTransactionAmount(creditCardVO.getTransactionAmount());
		slCreditCardVO.setUserName(creditCardVO.getUserName());
		Long accountId = slCreditCardVO.getCardId();
				
		//SLCreditCardVO slCreditCardVO = SLCreditCardVO.adapt(creditCardVO);
						
		WalletVO request = walletRequestBuilder.depositBuyerFundsWithCreditCard(creditCardVO.getUserName(), accountId, 
				buyerId, buyerVO.getBuyerState(), buyerVO.getBuyerV1AccountNumber(), buyerVO.getBuyerV2AccountNumber(),
				soId, "", creditCardVO.getTransactionAmount());

		request.setCreditCard(slCreditCardVO);
		if ("SimpleBuyer".equals(roleType)){
			request.setFundingTypeId(new Long(CommonConstants.CONSUMER_FUNDING_TYPE));
		}else{
			request.setFundingTypeId(new Long(CommonConstants.PRE_FUNDING_TYPE));
		}

		WalletResponseVO response = walletBO.depositBuyerFundsWithCreditCard(request);
		response.validateCreditCardResponse();
		response.getCreditCard().setEntityId(buyerId.intValue());
							
		if( response.getCreditCard().isAuthorized() ) {
			financeManagerBO.sendCCDepositConfirmationEmail(response.getCreditCard(), 
					creditCardVO.getTransactionAmount(), response.getTransactionId().intValue(),roleType);
		}
		
		creditCardVO.setAnsiResponseCode(response.getCreditCard().getAnsiResponseCode());
		creditCardVO.setCidResponseCode(response.getCreditCard().getCidResponseCode());
		creditCardVO.setAuthorizationCode(response.getCreditCard().getAuthorizationCode());
		creditCardVO.setAuthorizationAmount(response.getCreditCard().getAuthorizationAmount());
		creditCardVO.setAuthorized(response.getCreditCard().isAuthorized());
		creditCardVO.setAuthorizerRespCode(response.getCreditCard().getAuthorizerRespCode());
		
		return creditCardVO;
	}

	
	/**
	 * @return the creditCardDao
	 */
	public ICreditCardDao getCreditCardDao() {
		return creditCardDao;
	}

	/**
	 * @param creditCardDao the creditCardDao to set
	 */
	public void setCreditCardDao(ICreditCardDao creditCardDao) {
		this.creditCardDao = creditCardDao;
	}

	/**
	 * @return the cryptography
	 */
	public Cryptography getCryptography() {
		return cryptography;
	}

	/**
	 * @param cryptography the cryptography to set
	 */
	public void setCryptography(Cryptography cryptography) {
		this.cryptography = cryptography;
	}


	/**
	 * @return the accountingTransactionBO
	 */
	public AccountingTransactionMananagementBO getAccountingTransactionBO() {
		return accountingTransactionBO;
	}

	/**
	 * @param accountingTransactionBO the accountingTransactionBO to set
	 */
	public void setAccountingTransactionBO(
			AccountingTransactionMananagementBO accountingTransactionBO) {
		this.accountingTransactionBO = accountingTransactionBO;
	}

	public ILocationDao getLocationDao() {
		return locationDao;
	}

	public void setLocationDao(ILocationDao locationDao) {
		this.locationDao = locationDao;
	}

	public IFinanceManagerBO getFinanceManagerBO() {
		return financeManagerBO;
	}

	public void setFinanceManagerBO(IFinanceManagerBO financeManagerBO) {
		this.financeManagerBO = financeManagerBO;
	}

	public void commit() {
		this.commitWork();
		
	}

	public Cryptography128 getCryptography128() {
		return cryptography128;
	}

	public void setCryptography128(Cryptography128 cryptography128) {
		this.cryptography128 = cryptography128;
	}

}
