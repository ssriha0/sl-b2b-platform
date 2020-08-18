package com.newco.marketplace.web.delegatesImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.business.businessImpl.inhomeOutBoundNotification.NotificationServiceImpl;
import com.newco.marketplace.business.iBusiness.financeManager.IFinanceManagerBO;
import com.newco.marketplace.business.iBusiness.ledger.ICreditCardBO;
import com.newco.marketplace.dto.vo.PIIThresholdVO;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.remoteclient.WalletClient;
import com.newco.marketplace.vo.provider.FinancialProfileVO;
import com.newco.marketplace.web.delegates.IFMPersistDelegate;
import com.newco.marketplace.web.dto.AccountDTO;
import com.newco.marketplace.web.dto.FMFinancialProfileTabDTO;
import com.newco.marketplace.web.dto.FMManageFundsTabDTO;
import com.newco.marketplace.web.utils.ObjectMapper;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.servicelive.common.CommonConstants;
import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.common.vo.SLCreditCardVO;
import com.servicelive.lookup.ILookupBO;
import com.servicelive.lookup.vo.BuyerLookupVO;
import com.servicelive.lookup.vo.ProviderLookupVO;
import com.servicelive.wallet.serviceinterface.IWalletBO;
import com.servicelive.wallet.serviceinterface.requestbuilder.WalletRequestBuilder;
import com.servicelive.wallet.serviceinterface.vo.WalletResponseVO;
import com.servicelive.wallet.serviceinterface.vo.WalletVO;

public class FMPersistDelegateImpl implements IFMPersistDelegate {
	private static final Logger logger = Logger.getLogger(FMPersistDelegateImpl.class);

	private IFinanceManagerBO financeManagerBO;
	private IWalletBO walletBO;
	private WalletRequestBuilder walletRequestBuilder = new WalletRequestBuilder();

	private ILookupBO lookup;

	private static FMPersistDelegateImpl fmPersistor = new FMPersistDelegateImpl();

	// SL-20987 --starts here
	private WalletClient walletClient;
	private NotificationServiceImpl notificationService; 
	//SL-20987 --ends here
	public static FMPersistDelegateImpl getInstance(){
		if(fmPersistor == null)
		{
			fmPersistor = new FMPersistDelegateImpl();
		}
		return fmPersistor;
	}

	public void saveFinancialProfile(
			FMFinancialProfileTabDTO financialProfileTabDTO, Integer vendorId, boolean buyerFlag)
					throws BusinessServiceException, DBException {
		FinancialProfileVO financialProfileVO = ObjectMapper
				.convertFinancialProfileDTOToVO(financialProfileTabDTO);
		financeManagerBO.saveFinancialProfile(financialProfileVO, vendorId, buyerFlag);

	}

	private ICreditCardBO creditCardBO;

	public ICreditCardBO getCreditCardBO() {
		return creditCardBO;
	}

	public IFinanceManagerBO getFinanceManagerBO() {
		return financeManagerBO;
	}

	public void setCreditCardBO(ICreditCardBO creditCardBO) {
		this.creditCardBO = creditCardBO;
	}


	public void setFinanceManagerBO(IFinanceManagerBO financeManagerBO) {
		this.financeManagerBO = financeManagerBO;
	}


	//SL-21117: Revenue Pull Code change starts

	public List <String> getPermittedUsers() throws BusinessServiceException{

		return financeManagerBO.getPermittedUsers();		
	}

	public double getAvailableBalanceForRevenuePull() throws BusinessServiceException{
		double availableBalance=0.0;

		availableBalance = financeManagerBO.getAvailableBalanceForRevenuePull();		

		return availableBalance;
	}

	public boolean getAvailableDateCheckForRevenuePull(Date calendarOnDate) throws BusinessServiceException{
		boolean dbRevenuePullDateCheck=false;

		dbRevenuePullDateCheck = financeManagerBO.getAvailableDateCheckForRevenuePull(calendarOnDate);		

		return dbRevenuePullDateCheck;
	}

	public void insertEntryForRevenuePull(double amount,Date revenuePullDate,String note,String user) throws BusinessServiceException{

		financeManagerBO.insertEntryForRevenuePull(amount,revenuePullDate,note,user);

	}

	public void sendRevenuePullConfirmationEmail(String firstName,String lastName,double amount,String revenuePullDate) throws BusinessServiceException{

		financeManagerBO.sendRevenuePullConfirmationEmail(firstName,lastName,amount,revenuePullDate);

	}

	//Code change ends

	public ProcessResponse withdrawAmount(FMManageFundsTabDTO manageFundsTabDTO,
			Integer companyId, String roleType,String user) throws BusinessServiceException {

		ProcessResponse pr = new ProcessResponse();
		double amount = Double.parseDouble(manageFundsTabDTO.getWithdrawAmount());

		ProviderLookupVO provider = lookup.lookupProvider((long)companyId); 

		WalletVO request = walletRequestBuilder.withdrawProviderFunds(user, manageFundsTabDTO.getAccountId(), (long)companyId, provider.getProviderState(),
				provider.getProviderV1AccountNumber(), amount, manageFundsTabDTO.getProviderMaxWithdrawalNo(), manageFundsTabDTO.getProviderMaxWithdrawalLimit());
		request.getLedger().setNonce(manageFundsTabDTO.getNonce());

		//SL-20987-Wallet Dirty Read Fix  --starts here
		//The code will be executed based on a switch.If the switch is ON,new code i.e through web service the withdraw will be called otherwise the old code
		WalletResponseVO response =null;
		String walletWithdrawSwitch=null;
		try{
			walletWithdrawSwitch=notificationService.getOutBoundFlag(CommonConstants.WALLET_WITHDRAW_SWITCH);
		}
		catch (BusinessServiceException e) {
			logger.error("Exception in fetching the walletWithdrawSwitch" + e);
		}
		if(StringUtils.isNotBlank(walletWithdrawSwitch) && CommonConstants.WALLET_SWITCH_ON.equalsIgnoreCase(walletWithdrawSwitch)){
			try{ 
				response = walletClient.withdrawProviderFunds(request);
			}
			catch (SLBusinessServiceException e) {
				pr.appendMessages(e.getErrors());
			}
		}
		else{
			response = walletBO.withdrawProviderFunds(request);
		}

		//SL-20987 --ends here

		logger.info("withdrawAmount ---> before error check-> " + response.getErrorMessages());
		if(null !=response && !response.isError()){
			logger.info("withdrawAmount ---> before sendWithdrawConfirmationEmail-> companyId: " +companyId + " amount: " +amount + " Txn:" + response.getTransactionId().intValue());
			financeManagerBO.sendWithdrawProviderConfirmationEmail(companyId, amount, response.getTransactionId().intValue());
		}

		pr.appendMessages(response.getErrorMessages());

		return pr;
	}

	public List<AccountDTO> accountDetailsIncludeCC(Integer entityId)
			throws BusinessServiceException {
		return ObjectMapper.convertAccountListtoAccountDTOList(financeManagerBO
				.accountDetailsIncludeCC(entityId));
	}

	/*private CreditCardVO convertWalletCCToMarketPlaceCC(CreditCardVO walletCreditCard ) throws BusinessServiceException {
		CreditCardVO marketCreditCard = new CreditCardVO();		
		try {
			BeanUtils.copyProperties(walletCreditCard, marketCreditCard);
		} catch (IllegalAccessException e) {
			throw new BusinessServiceException(e.getMessage(),e);
		} catch (InvocationTargetException e) {
			throw new BusinessServiceException(e.getMessage(),e);
		}		
		return marketCreditCard;
	}*/

	public ProcessResponse issueRefunds(FMManageFundsTabDTO manageFundsTabDTO,
			Integer companyId, String roleType, String userName) throws BusinessServiceException {
		ProcessResponse pr = new ProcessResponse();
		/*if(manageFundsTabDTO.getRefundNote() != null){
			manageFundsTabDTO.setRefundNote(manageFundsTabDTO.getRefundNote().trim());
		}
		if(StringUtils.isEmpty(manageFundsTabDTO.getRefundNote())){
			manageFundsTabDTO.getErrors().add(new SOWError("refundNote", "Note Required for Refund",OrderConstants.SOW_TAB_ERROR));
			pr.setCode(ServiceConstants.USER_ERROR_RC);
			pr.setSubCode(ServiceConstants.USER_ERROR_RC);
			return (pr);
		}*/
		double amount = Double.parseDouble(manageFundsTabDTO.getWithdrawAmount());
		pr = financeManagerBO.issueRefunds(companyId, roleType, amount, manageFundsTabDTO.getAccountId(), userName, manageFundsTabDTO.getRefundNote());
		return pr;
	}

	public ProcessResponse depositAmount(FMManageFundsTabDTO manageFundsTabDTO,
			Integer companyId, String roleType,String user) throws BusinessServiceException {

		ProcessResponse pr = new ProcessResponse();
		WalletResponseVO response = null;

		double amount = Double.parseDouble(manageFundsTabDTO.getDepositAmount());


		BuyerLookupVO buyer = lookup.lookupBuyer(Long.valueOf((long)companyId.intValue()));

		// auto ach deposit
		if( manageFundsTabDTO.getAutoACHInd().equals("1") ) {

			WalletVO request = walletRequestBuilder.depositBuyerFundsWithInstantACH(
					user, manageFundsTabDTO.getAccountId(), (long)companyId, buyer.getBuyerState(), buyer.getBuyerV1AccountNumber(), buyer.getBuyerV2AccountNumber(),
					manageFundsTabDTO.getSoId(), null, amount);

			response = walletBO.depositBuyerFundsWithInstantACH(request);

			financeManagerBO.sendDepositConfirmationEmail(companyId, amount, response.getTransactionId().intValue(),roleType );

			// credit card deposit
		} else if( manageFundsTabDTO.getAccountList().split(",")[1].equals("30")) {

			SLCreditCardVO creditCard = lookup.lookupCreditCard(manageFundsTabDTO.getAccountId());				

			WalletVO request = walletRequestBuilder.depositBuyerFundsWithCreditCard(user, manageFundsTabDTO.getAccountId(), 
					(long)companyId, buyer.getBuyerState(), buyer.getBuyerV1AccountNumber(), buyer.getBuyerV2AccountNumber(),
					manageFundsTabDTO.getSoId(), "", amount);

			creditCard.setTransactionAmount(amount);
			creditCard.setCardVerificationNo(manageFundsTabDTO.getCvvCode());
			creditCard.setEntityId(companyId);
			//Setting username of adopted user in case of admin flow
			if(null!=(manageFundsTabDTO.getUserName())){
				creditCard.setUserName(manageFundsTabDTO.getUserName());
			}else{
				creditCard.setUserName(user);
			}
			request.setCreditCard(creditCard);
			//Removing the flag to switch between RTCA and HS since HS web service should be always invoked--START
			request.setHsWebFlag(true);
			response = walletBO.depositBuyerFundsWithCreditCard(request);
			response.validateHSCreditCardResponse();

			//SL-20852 : switch used to differentiate RTCA and HS web service invokation
			/*String HSSWitch = null;	
			try{
				HSSWitch = walletBO.getApplicationFlagForHSWebService(CommonConstants.HS_WEBSERVICE_APP_KEY);
				if(StringUtils.isNotBlank(HSSWitch) && StringUtils.equals(HSSWitch, CommonConstants.HS_WEBSERVICE_FLAG_ON)){
					request.setHsWebFlag(true);
				}else{
					request.setHsWebFlag(false);
				}
			}catch (Exception e) {
				logger.error("Exception in fetching switch from application flag"+ e.getMessage());
			}*/
			//response = walletBO.depositBuyerFundsWithCreditCard(request);
			//SL-20852 : Validating response and set error response to display in FE
			/*if(StringUtils.equalsIgnoreCase(HSSWitch, CommonConstants.HS_WEBSERVICE_FLAG_ON)){
				response.validateHSCreditCardResponse();
			}else{
				response.validateCreditCardResponse();
			}*/
			//Removing the flag to switch between RTCA and HS --END
			if( response.getCreditCard().isAuthorized() ) {

				financeManagerBO.sendCCDepositConfirmationEmail(
						creditCard, amount, response.getTransactionId().intValue(),roleType);
			}

			// cash deposit
		} else {	
			WalletVO request = walletRequestBuilder.depositBuyerFundsWithCash(
					user, manageFundsTabDTO.getAccountId(), (long)companyId, buyer.getBuyerState(), buyer.getBuyerV1AccountNumber(),
					buyer.getBuyerV2AccountNumber(), manageFundsTabDTO.getSoId(), null, amount);

			response = walletBO.depositBuyerFundsWithCash(request);
			if (!response.isError()) { // Saeid added this check
				financeManagerBO.sendDepositConfirmationEmail(companyId, amount, response.getTransactionId().intValue(),roleType );
			}
		}

		// append any error messages to the process response object
		pr.appendMessages(response.getErrorMessages() );

		return pr;
	}

	public List<AccountDTO> getAccounts(Integer entityId)
			throws BusinessServiceException {
		return ObjectMapper.convertAccountListToAccountDTO(financeManagerBO
				.accountDetails(entityId));
	}


	public ProcessResponse withdrawAmountSLAOperations(FMManageFundsTabDTO manageFundsTabDTO,
			Integer companyId, String roleType,String user) throws BusinessServiceException {
		ProcessResponse pr = new ProcessResponse();
		double amount = Double.parseDouble(manageFundsTabDTO.getWithdrawAmount());
		pr = financeManagerBO.withdrawfundsSLAOperations(companyId, roleType, amount, manageFundsTabDTO.getAccountId(),user);
		return pr;
	}

	public ProcessResponse depositAmountSLAOperations(FMManageFundsTabDTO manageFundsTabDTO,
			Integer companyId, String roleType,String user) throws BusinessServiceException {
		ProcessResponse pr = new ProcessResponse();
		double amount = Double.parseDouble(manageFundsTabDTO.getDepositAmount());
		pr = financeManagerBO.depositfundsSLAOperations(companyId, roleType, amount, manageFundsTabDTO.getAccountId(),user);
		return pr;
	}
	public HashMap<String,String> getBuyerTotalDeposit(Integer buyerId) throws BusinessServiceException
	{
		HashMap einMap = financeManagerBO.getBuyerTotalDeposit(buyerId);
		return einMap;
	}

	// Fetch buyer bitFlag and threshold limit
	public PIIThresholdVO getBuyerThresholdMap(String role) throws BusinessServiceException
	{	
		PIIThresholdVO pIIThresholdVO = new PIIThresholdVO();
		pIIThresholdVO = financeManagerBO.getBuyerThresholdMap(role);
		return pIIThresholdVO;
	}

	public String getLedgerEntryProviderWithdrawNonce() throws BusinessServiceException
	{
		String nonce = walletBO.getLedgerEntryNonce(20);
		return nonce;
	}


	public void setLookup(ILookupBO lookup) {
		this.lookup = lookup;
	}

	/**
	 * Sets the wallet client bo.
	 * 
	 * @param walletClientBO the new wallet client bo
	 */
	public void setWalletBO(IWalletBO walletBO) {

		this.walletBO = walletBO;
	}

	//SL-20987 -- starts here

	public NotificationServiceImpl getNotificationService() {
		return notificationService;
	}

	public void setNotificationService(NotificationServiceImpl notificationService) {
		this.notificationService = notificationService;
	}


	public WalletClient getWalletClient() {
		return walletClient;
	}

	public void setWalletClient(WalletClient walletClient) {
		this.walletClient = walletClient;
	}
	//SL-20987 -- ends here
}

