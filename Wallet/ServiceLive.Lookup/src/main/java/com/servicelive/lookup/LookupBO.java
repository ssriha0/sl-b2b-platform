package com.servicelive.lookup;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.servicelive.common.BusinessServiceException;
import com.servicelive.common.CommonConstants;
import com.servicelive.common.exception.DataServiceException;
import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.common.util.Cryptography;
import com.servicelive.common.util.Cryptography128;
import com.servicelive.common.vo.SLAccountVO;
import com.servicelive.common.vo.SLCreditCardVO;
import com.servicelive.lookup.dao.IAccountDao;
import com.servicelive.lookup.dao.ILookupSupplierDao;
import com.servicelive.lookup.vo.AccountVO;
import com.servicelive.lookup.vo.AdminLookupVO;
import com.servicelive.lookup.vo.BuyerLookupVO;
import com.servicelive.lookup.vo.EntityLookupVO;
import com.servicelive.lookup.vo.ProviderLookupVO;
import com.servicelive.lookup.vo.ValueLinkAccountsVO;


/**
 * Class LookupBO.
 */
public class LookupBO implements ILookupBO {

	/** logger. */
//	private static final Logger logger = Logger.getLogger(LookupBO.class);
		
	/** lookupFinanceDao. */
	private IAccountDao accountDao;
	
	/** lookupSupplierDao. */
	private ILookupSupplierDao lookupSupplierDao;

	private Cryptography cryptography;
	
	private Cryptography128 cryptography128;
	
	/* (non-Javadoc)
	 * @see com.servicelive.lookup.ILookupBO#lookupAdmin()
	 */
	public AdminLookupVO lookupAdmin() throws SLBusinessServiceException {
		AdminLookupVO adminLookup = new AdminLookupVO();
		adminLookup.setSl1AccountNumber( this.getVLAccountsForSLRevenue().getV1AccountNo() );
		adminLookup.setSl3AccountNumber( this.getVLAccountsForSLOperations().getV1AccountNo() );
		return adminLookup;
	}


	/* (non-Javadoc)
	 * @see com.servicelive.lookup.ILookupBO#lookupBuyer(java.lang.Long)
	 */
	public BuyerLookupVO lookupBuyer(Long buyerId) throws SLBusinessServiceException {

		try {
			BuyerLookupVO buyerLookup = new BuyerLookupVO();
			
			EntityLookupVO buyer = lookupSupplierDao.getBuyerInfo(buyerId);
			
			ValueLinkAccountsVO accounts = getVLAccountsForBuyer(buyerId);

			buyerLookup.setBuyerState( buyer.getState() );
			buyerLookup.setBuyerFundingTypeId( buyer.getFundingTypeId() );
			if(accounts != null){
				buyerLookup.setBuyerV1AccountNumber( accounts.getV1AccountNo() );
				buyerLookup.setBuyerV2AccountNumber( accounts.getV2AccountNo() );
			}
			
			return buyerLookup;
		} catch (DataServiceException e) {
			throw new SLBusinessServiceException(e);
		}
		
	}
	
	

	/* (non-Javadoc)
	 * @see com.servicelive.lookup.ILookupBO#lookupCreditCard(java.lang.Long)
	 */
	public SLCreditCardVO lookupCreditCard(Long cardId) throws SLBusinessServiceException {
		try {
			SLCreditCardVO creditCardAccount = this.accountDao.getCreditCardById(cardId);
			if (creditCardAccount != null) {
				
				//Commenting Code for SL-18789
				//String cardNo = cryptography.decryptKey(creditCardAccount.getEncCardNo());
				// String cardNo = cryptography128.decryptKey128Bit(creditCardAccount.getEncCardNo());
               // creditCardAccount.setCardNo(cardNo);
			}
			return creditCardAccount;
		} catch (DataServiceException e) {
			throw new SLBusinessServiceException(e);
		}
	}


	/* (non-Javadoc)
	 * @see com.servicelive.lookup.ILookupBO#lookupProvider(java.lang.Long)
	 */
	public ProviderLookupVO lookupProvider(Long providerId) throws SLBusinessServiceException {
		try {
			ProviderLookupVO providerLookup = new ProviderLookupVO();
			
			EntityLookupVO provider = lookupSupplierDao.getProviderInfo(providerId);

			providerLookup.setProviderState( provider.getState() );
			providerLookup.setProviderFundingTypeId( CommonConstants.FUNDING_TYPE_NON_FUNDED );
			
			ValueLinkAccountsVO accounts = getVLAccountsForProvider(providerId);
			if( accounts != null ) {
				providerLookup.setProviderV1AccountNumber( accounts.getV1AccountNo() );
			}
			
			return providerLookup;
		} catch (DataServiceException e) {
			throw new SLBusinessServiceException(e.getMessage(), e);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.servicelive.wallet.profile.IProfileBO#getAccountDetails(long)
	 */
	public SLAccountVO lookupAccount(Long accountId) throws SLBusinessServiceException {
		try {
			return accountDao.getAccountDetails(accountId);
		} catch (DataServiceException e) {
			throw new SLBusinessServiceException(e.getMessage(), e);
		}
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.profile.IProfileBO#getActiveAccountDetails(long)
	 */
	public SLAccountVO getActiveAccountDetails(long entityId) throws BusinessServiceException {

		SLAccountVO account = new SLAccountVO();
		try {
			account = accountDao.getActiveAccountDetails(entityId);
			if (account != null && account.getAccountNumber() != null) {
				
				//Commenting code for SL-18789
				//account.setAccountNumber(cryptography.decryptKey(account.getAccountNumber()));
				account.setAccountNumber(cryptography128.decryptKey128Bit(account.getAccountNumber()));

				// also decrypt the routing number
				
				if (account.getRoutingNumber() != null) {  // Fix for JIRA SL-12158
				account.setRoutingNumber(cryptography.decryptKey(account.getRoutingNumber()));
				}
			}
		} catch (Exception e) {
			throw new BusinessServiceException("Error happened while getting active account detail in Ledger", e);
		}

		return account;
	}
	public Long getCreditcardAccountNoForNonAutofundedBuyer(long entityId)throws BusinessServiceException{
		Long creditCardAccountId=null;
		String creditCardAccountIdVal=null;
		try{
		   creditCardAccountId=accountDao.getCreditCardAccountNo(entityId);
		   if(null != creditCardAccountId){
			   //Commentting code for SL-18789
			   //creditCardAccountIdVal=cryptography.decryptKey(creditCardAccountId.toString());
			   creditCardAccountIdVal=cryptography128.decryptKey128Bit(creditCardAccountId.toString());

			   if(StringUtils.isNotBlank(creditCardAccountIdVal)){
				   creditCardAccountId=new Long(creditCardAccountIdVal);
			   }
			   
		   }
		}
		catch (Exception e) {
			throw new BusinessServiceException("Error happend in fetching account no for credit card for non auto funded buyer",e);
		}
		return creditCardAccountId;
		}
	
	/* (non-Javadoc)
	 * @see com.servicelive.wallet.profile.IProfileBO#getSLValueLinkAccounts(java.lang.String)
	 */
	/**
 * getSLValueLinkAccounts.
 * 
 * @param accountName 
 * 
 * @return ValueLinkAccountsVO
 * 
 * @throws SLBusinessServiceException 
 */
private ValueLinkAccountsVO getSLValueLinkAccounts(String accountName) throws SLBusinessServiceException {
		try {
			Map<String, ValueLinkAccountsVO> accounts = accountDao.getSLValueLinkAccounts();
			return accounts.get(accountName);
		} catch (DataServiceException e) {
			throw new SLBusinessServiceException(e.getMessage(), e);
		}
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.profile.IProfileBO#getValueLinkAccounts(long, long)
	 */
	/**
	 * getValueLinkAccounts.
	 * 
	 * @param entityTypeId 
	 * @param ledgerEntityId 
	 * 
	 * @return ValueLinkAccountsVO
	 * 
	 * @throws SLBusinessServiceException 
	 */
	public ValueLinkAccountsVO getValueLinkAccounts(long entityTypeId, long ledgerEntityId) throws SLBusinessServiceException {

		try {
			ValueLinkAccountsVO criteria = new ValueLinkAccountsVO();
			criteria.setEntityTypeId(entityTypeId);
			criteria.setLedgerEntityId(ledgerEntityId);
			return accountDao.getValueLinkAccounts(criteria);
		} catch (DataServiceException e) {
			throw new SLBusinessServiceException(e.getMessage(), e);
		}
	}
	
	/**
	 * getVLAccountsForBuyer.
	 * 
	 * @param buyerId 
	 * 
	 * @return ValueLinkAccountsVO
	 * 
	 * @throws SLBusinessServiceException 
	 */
	private ValueLinkAccountsVO getVLAccountsForBuyer(long buyerId) throws SLBusinessServiceException {
		return getValueLinkAccounts(CommonConstants.LEDGER_ENTITY_TYPE_BUYER, buyerId);
	}

	/**
	 * getVLAccountsForProvider.
	 * 
	 * @param providerId 
	 * 
	 * @return ValueLinkAccountsVO
	 * 
	 * @throws SLBusinessServiceException 
	 */
	private ValueLinkAccountsVO getVLAccountsForProvider(long providerId) throws SLBusinessServiceException {
		return getValueLinkAccounts(CommonConstants.LEDGER_ENTITY_TYPE_PROVIDER, providerId);
	}

	
	/**
	 * getVLAccountsForSLOperations.
	 * 
	 * @return ValueLinkAccountsVO
	 * 
	 * @throws SLBusinessServiceException 
	 */
	private ValueLinkAccountsVO getVLAccountsForSLOperations() throws SLBusinessServiceException {
		return this.getSLValueLinkAccounts(CommonConstants.VL_ACCOUNT_PREFUNDING_ACCOUNT);
	}

	/**
	 * getVLAccountsForSLRevenue.
	 * 
	 * @return ValueLinkAccountsVO
	 * 
	 * @throws SLBusinessServiceException 
	 */
	private ValueLinkAccountsVO getVLAccountsForSLRevenue() throws SLBusinessServiceException {
		return this.getSLValueLinkAccounts(CommonConstants.VL_ACCOUNT_POSTING_FEE);
	}


	/**
	 * setLookupFinanceDao.
	 * 
	 * @param lookupFinanceDao 
	 * 
	 * @return void
	 */
	public void setAccountDao(IAccountDao accountDao) {
		this.accountDao = accountDao;
	}
	
	/**
	 * setLookupSupplierDao.
	 * 
	 * @param lookupSupplierDao 
	 * 
	 * @return void
	 */
	public void setLookupSupplierDao(ILookupSupplierDao lookupSupplierDao) {
		this.lookupSupplierDao = lookupSupplierDao;
	}
	
	/**
	 * setCryptography
	 * 
	 * @param cryptography
	 * 
	 * @return void
	 */
	public void setCryptography(Cryptography cryptography) {
		this.cryptography = cryptography;
	}
	/**
	 * setCryptography128
	 * 
	 * @param cryptography128
	 * 
	 * @return void
	 */

	public void setCryptography128(Cryptography128 cryptography128) {
		this.cryptography128 = cryptography128;
	}
	
	

}
