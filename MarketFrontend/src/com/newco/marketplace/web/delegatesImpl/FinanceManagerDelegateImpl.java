package com.newco.marketplace.web.delegatesImpl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jxl.write.WriteException;


import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.financeManager.IFinanceManagerBO;
import com.newco.marketplace.business.iBusiness.ledger.ICreditCardBO;
import com.newco.marketplace.criteria.PagingCriteria;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.ajax.AjaxCacheVO;
import com.newco.marketplace.dto.vo.financemanger.BuyerSOReportVO;
import com.newco.marketplace.dto.vo.financemanger.ExportStatusVO;
import com.newco.marketplace.dto.vo.financemanger.FMReportVO;
import com.newco.marketplace.dto.vo.financemanger.ProviderSOReportVO;
import com.newco.marketplace.dto.vo.ledger.Account;
import com.newco.marketplace.dto.vo.ledger.AccountHistoryResultVO;
import com.newco.marketplace.dto.vo.ledger.AccountHistoryVO;
import com.newco.marketplace.dto.vo.ledger.CreditCardVO;
import com.newco.marketplace.dto.vo.ledger.FMTransferFundsVO;
import com.newco.marketplace.dto.vo.ledger.ListAfterSearchVO;
import com.newco.marketplace.dto.vo.financemanger.ReportContentVO;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.vo.provider.FinancialProfileVO;
import com.newco.marketplace.web.delegates.IFinanceManagerDelegate;
import com.newco.marketplace.web.delegates.ILookupDelegate;
import com.newco.marketplace.web.dto.FMFinancialProfileTabDTO;
import com.newco.marketplace.web.dto.FMManageAccountsTabDTO;
import com.newco.marketplace.web.dto.FMOverviewHistoryTabDTO;
import com.newco.marketplace.web.dto.FMReportTabDTO;
import com.newco.marketplace.web.dto.FMTransferFundsDTO;
import com.newco.marketplace.web.dto.TransactionDTO;
import com.newco.marketplace.web.utils.FMTransferFundsMapper;
import com.newco.marketplace.web.utils.ObjectMapper;
import com.newco.marketplace.webservices.base.response.ProcessResponse;


/**
 * $Revision: 1.15 $ $Author: kvuppal $ $Date: 2008/06/01 03:13:51 $
 * 
 */
public class FinanceManagerDelegateImpl implements IFinanceManagerDelegate {

	private static final Logger logger = Logger
			.getLogger("FMFetchDelegateImpl");
	private IFinanceManagerBO financeManagerBO;
	private ICreditCardBO creditCardBO;
	private ILookupDelegate luDelegate;

	public FMFinancialProfileTabDTO getVendorDetails(String vendorId,
			boolean buyerFlag) throws BusinessServiceException, DBException {
		FMFinancialProfileTabDTO fmFinancialProfileTabDTO = null;
		try {
			FinancialProfileVO financialProfileVO = financeManagerBO
					.getVendorDetails(vendorId, buyerFlag);
			fmFinancialProfileTabDTO = ObjectMapper
					.convertFinancialProfileVOToDTO(financialProfileVO);
		} catch (BusinessServiceException bse) {
			logger.error("getVendorDetails()-->EXCEPTION-->", bse);
		}
		return fmFinancialProfileTabDTO;
	}

	public void saveFinancialProfile(
			FMFinancialProfileTabDTO financialProfileTabDTO, Integer vendorId,
			boolean buyerFlag) throws BusinessServiceException, DBException {
		FinancialProfileVO financialProfileVO = ObjectMapper
				.convertFinancialProfileDTOToVO(financialProfileTabDTO);
		financeManagerBO.saveFinancialProfile(financialProfileVO, vendorId,
				buyerFlag);

	}

	public ArrayList<LookupVO> getAccountTypeList() throws DataServiceException {
		ArrayList<LookupVO> percentageList = null;
		try {
			percentageList = (ArrayList) luDelegate.getAccountTypeList();
		} catch (BusinessServiceException e) {
			logger.info("Caught Exception and ignoring", e);
		}
		return percentageList;
	}

	public ArrayList<LookupVO> getCreditCardTypeList()
			throws DataServiceException {
		ArrayList<LookupVO> percentageList = null;
		try {
			percentageList = (ArrayList) luDelegate.getCreditCardTypeList();
		} catch (BusinessServiceException e) {
			logger.info("Caught Exception and ignoring", e);
		}
		return percentageList;
	}

	public double getAvailableBalance(AjaxCacheVO avo) {

		double availableBalance = financeManagerBO.getavailableBalance(avo);

		return availableBalance;
	}

	public double getCurrentBalance(AjaxCacheVO avo) {

		double currentBalance = financeManagerBO.getcurrentBalance(avo);

		return currentBalance;
	}
	
	public double getWalletHoldBalance(Integer entityId) {
		double walletHoldBalance = 0.0;
		walletHoldBalance = financeManagerBO.getWalletHoldBalance(entityId);
		return walletHoldBalance;
	}

	public String getStateRegulationNote(Integer reasonCodeId) {
		return luDelegate.getStateRegulationNote(reasonCodeId);
	}

	public String getIRSlevyNote(Integer reasonCodeId) {
		return luDelegate.getIRSlevyNote(reasonCodeId);
	}

	public String getLegalHoldNote(Integer reasonCodeId) {
		return luDelegate.getLegalHoldNote(reasonCodeId);
	}

	public ArrayList<LookupVO> getPercentOwnedList()
			throws DataServiceException {
		ArrayList<LookupVO> percentageList = null;
		try {
			percentageList = (ArrayList) luDelegate.getPercentOwnedList();
		} catch (BusinessServiceException e) {
			logger.info("Caught Exception and ignoring", e);
		}
		return percentageList;
	}

	public FMManageAccountsTabDTO getAccountDetails(Integer entityId)
			throws BusinessServiceException {
		List<Account> list = new ArrayList<Account>();
		Account account = new Account();
		list = financeManagerBO.accountDetails(entityId.intValue());
		for (Account accountList : list) {
			account = accountList;
		}
		FMManageAccountsTabDTO manageAccountsTabDTO = ObjectMapper
				.convertFinancialAccountsVOToDTO(account);
		return manageAccountsTabDTO;
	}

	public List<AccountHistoryVO> getExportResultSet(
			FMOverviewHistoryTabDTO fmDTO) throws BusinessServiceException {
		AccountHistoryVO ahVO = ObjectMapper
				.convertOverviewHistoryDTOtoVO(fmDTO);
		List<AccountHistoryVO> accountHistoryVO = financeManagerBO
				.getExportRecords(ahVO);
		return accountHistoryVO;
	}

	public AccountHistoryResultVO getAccountOverviewHistoryFMOverview(
			FMOverviewHistoryTabDTO fmDTO, PagingCriteria pagingCriteria)
			throws BusinessServiceException {
		AccountHistoryVO ahVO = ObjectMapper
				.convertOverviewHistoryDTOtoVO(fmDTO);
		AccountHistoryResultVO accountHistoryResultVO = financeManagerBO
				.getAccountOverviewHistoryFMOverview(ahVO, pagingCriteria);
		return accountHistoryResultVO;
	}

	public List<TransactionDTO> getAccountOverviewHistory(
			FMOverviewHistoryTabDTO fmDTO) throws BusinessServiceException {
		List<AccountHistoryVO> list = new ArrayList<AccountHistoryVO>();
		AccountHistoryVO ahVO = ObjectMapper
				.convertOverviewHistoryDTOtoVO(fmDTO);
		list = financeManagerBO.getAccountOverviewHistory(ahVO);
		List<TransactionDTO> overviewHistoryDTO = ObjectMapper
				.convertOverviewHistoryVOtoDTO(list);
		return overviewHistoryDTO;

	}

	public FMManageAccountsTabDTO getActiveAccountDetails(Integer entityId)
			throws BusinessServiceException {
		Account account = new Account();
		account = financeManagerBO.getAccountDetails(entityId);
		Account escheatAccount = new Account();

		escheatAccount = financeManagerBO.getEscheatAccountDetails(entityId);
		if (escheatAccount != null) {
			FMManageAccountsTabDTO manageAccountsTabDTO = ObjectMapper
					.convertAllFinancialAccountsVOToDTO(account, escheatAccount);
			return manageAccountsTabDTO;
		} else {
			FMManageAccountsTabDTO manageAccountsTabDTO = ObjectMapper
					.convertFinancialAccountsVOToDTO(account);
			return manageAccountsTabDTO;
		}
	}

	public FMManageAccountsTabDTO getActiveCreditCardDetails(Integer companyId)
			throws BusinessServiceException {
		CreditCardVO creditVO = null;
		try {
			creditVO = creditCardBO.getActiveCreditCardDetails(companyId);
		} catch (BusinessServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FMManageAccountsTabDTO manageAccountsTabDTO = ObjectMapper
				.convertCreditCardVOToDTO(creditVO, companyId);
		return manageAccountsTabDTO;
	}

	public void saveFinancialAccounts(
			FMManageAccountsTabDTO manageAccountsTabDTO, Integer vendorId)
			throws DataServiceException {
		Account account = ObjectMapper.convertFinancialAccountsDTOToVO(
				manageAccountsTabDTO, vendorId);
		financeManagerBO.saveAccountDetails(account);
	}

	public void saveEscheatFinancialAccounts(
			FMManageAccountsTabDTO manageAccountsTabDTO, Integer vendorId)
			throws DataServiceException {
		Account account = ObjectMapper.convertEscheatFinancialAccountsDTOToVO(
				manageAccountsTabDTO, vendorId);
		financeManagerBO.saveAccountDetails(account);
	}

	public void saveAutoACHInfo(FMManageAccountsTabDTO manageAccountsTabDTO,
			Integer vendorId) throws DataServiceException {
		Account account = new Account();
		account.setOwner_entity_id(vendorId.longValue());
		account.setOwner_entity_type_id(manageAccountsTabDTO.getEntityTypeId()
				.longValue());
		account.setSaveAutoFundInd(manageAccountsTabDTO.getSaveAutoFundInd());
		if (manageAccountsTabDTO.getAutoACHInd().equalsIgnoreCase("true")) {
			account.setAutoACHInd(1);
		} else {
			account.setAutoACHInd(0);
		}

		if (manageAccountsTabDTO.getOldAccountId() != null) {
			account.setAccount_id(new Long(manageAccountsTabDTO
					.getOldAccountId()));
		}
		try {
			financeManagerBO.saveAutoFundingDetails(account);
		} catch (BusinessServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public CreditCardVO saveCreditCardInfo(
			FMManageAccountsTabDTO manageAccountsTabDTO, Integer vendorId,String userName)
			throws DataServiceException {
		CreditCardVO creditcard = ObjectMapper.convertCreditCardDTOToVO(
				manageAccountsTabDTO, vendorId);
		creditcard.setActive_ind(true);
		creditcard.setEnabled_ind(true);
		creditcard.setUserName(userName);
		try {
			Long cardId = creditCardBO.saveCardDetails(creditcard);
			creditcard.setCardId(cardId);
		} catch (BusinessServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return creditcard;
	}

	public ByteArrayOutputStream getExportToExcel(
			FMOverviewHistoryTabDTO listAfterSearchDTO,
			ByteArrayOutputStream outFinal) throws BusinessServiceException,
			IOException, WriteException {
		List<ListAfterSearchVO> historyVO = ObjectMapper
				.convertlistAfterSearchDTOtoVO(listAfterSearchDTO);
		ByteArrayOutputStream outDelegate = financeManagerBO.getExportToExcel(
				historyVO, outFinal);
		return outDelegate;
	}
	
	/*
	 * public Integer returnAccountCount(Long accountId, int reconciledInd)
	 * throws BusinessServiceException{ Integer accountCount =
	 * financeManagerBO.returnAccountCount(accountId, reconciledInd); return
	 * accountCount; }
	 */

	public Integer returnAccountCount(Long accountId)
			throws BusinessServiceException {
		Integer accountCount = financeManagerBO.returnAccountCount(accountId);
		return accountCount;
	}

	/**
	 * This method calls the business method to retrieve the value of Auto
	 * Funding Indicator
	 * 
	 * @param vendorId
	 * @return int autoFundingInd
	 * @throws BusinessServiceException
	 */
	public Account getAutoFundingIndicator(Integer vendorId)
			throws BusinessServiceException {
		Account acct = financeManagerBO.getAutoFundingIndicator(vendorId);
		return acct;
	}

	public IFinanceManagerBO getFinanceManagerBO() {
		return financeManagerBO;
	}

	public void setFinanceManagerBO(IFinanceManagerBO financeManagerBO) {
		this.financeManagerBO = financeManagerBO;
	}

	public ILookupDelegate getLuDelegate() {
		return luDelegate;
	}

	public void setLuDelegate(ILookupDelegate luDelegate) {
		this.luDelegate = luDelegate;
	}

	public ICreditCardBO getCreditCardBO() {
		return creditCardBO;
	}

	public void setCreditCardBO(ICreditCardBO creditCardBO) {
		this.creditCardBO = creditCardBO;
	}

	public ArrayList<LookupVO> getTransferSLBucksReasonCodes()
			throws BusinessServiceException {
		ArrayList<LookupVO> reasons = new ArrayList<LookupVO>();
		reasons = getLuDelegate().getTransferSLBucksReasonCodes();

		return reasons;
	}
	
	public ProcessResponse transferSLBucks(FMTransferFundsDTO transferDTO) {
		ProcessResponse processResponse = new ProcessResponse();

		FMTransferFundsVO transferFundsVO = new FMTransferFundsVO();
		transferFundsVO = FMTransferFundsMapper.convertDTOtoVO(transferDTO,
				transferFundsVO);

		try {
			processResponse = financeManagerBO.transferSLBucks(transferFundsVO);
		} catch (BusinessServiceException bse) {
			logger.error("[BusinessServiceException] ", bse);
			// throw new BusinessServiceException("Error in increasing the spend
			// limit.", bse);
		} catch (Exception ex) {
			logger.error("[Exception] ", ex);
		}

		return processResponse;
	}
	
	public Integer getWalletControlId(Integer entityId) {
		int walletControlId = 0;
		try {
			walletControlId = financeManagerBO.getWalletControlId(entityId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return walletControlId;
	}
	
	public String getWalletControlBanner(Integer walletControlId){
		String walletBanner = "";
		try {
			walletBanner=financeManagerBO.getWalletControlBanner(walletControlId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return walletBanner;
		
	}

	public List<FMManageAccountsTabDTO> getRefundAccountDetails(Integer entityId)
			throws BusinessServiceException {
		List<Account> list = new ArrayList<Account>();
		List<FMManageAccountsTabDTO> fmManageAccountsList = new ArrayList<FMManageAccountsTabDTO>();
		FMManageAccountsTabDTO manageAccountsTabDTO = null;
		list = financeManagerBO.accountDetailsIncludeCC(entityId.intValue());
		for (Account accountObj : list) {
			manageAccountsTabDTO = new FMManageAccountsTabDTO();
			manageAccountsTabDTO = ObjectMapper
					.convertFinancialAccountsVOToDTO(accountObj);
			fmManageAccountsList.add(manageAccountsTabDTO);
		}
		return fmManageAccountsList;
	}

	public void sendBuyerPostingFeeEmail(Integer companyId, String soId) {
		if (financeManagerBO == null)
			return;
		financeManagerBO.sendBuyerPostingFeeEmail(companyId, soId);
	}

	public void sendBuyerCancellationEmail(Integer buyerId, Integer providerId,
			String soId) {
		String roleType = null;
		financeManagerBO.sendBuyerSOCancelEmail(buyerId, providerId, soId,
				roleType);
	}

	public void sendProviderSOCancelledEmail(Integer buyerId,
			Integer providerId, String soId) {
		financeManagerBO.sendProviderSOCancelEmail(buyerId, providerId, soId);
	}

	public void sendBuyerSOClosedEmail(Integer buyerId, String soId) {
		financeManagerBO.sendBuyerSOClosedEmail(buyerId, soId);
	}

	public void sendProviderSOClosedEmail(Integer buyerId, String soId) {
		financeManagerBO.sendProviderSOClosedEmail(buyerId, soId);
	}

	// Added for jira_SL17625 for Reports generation
	public List<ProviderSOReportVO> getProviderPaymentByServiceOrder(
			FMReportTabDTO fmReportTabDTO) throws DelegateException {
		FMReportVO reportVO = ObjectMapper
				.convertFMReportDTOToVO(fmReportTabDTO);
		List<ProviderSOReportVO> listOfProviderVO = null;
		try {
			listOfProviderVO = financeManagerBO
					.getProviderPaymentByServiceOrder(reportVO);
		} catch (BusinessServiceException e) {
			throw new DelegateException(e);
		}
		return listOfProviderVO;
	}

	public List<String> validateBuyerIDs(List<String> userIds)
			throws DelegateException {
		// FMReportVO
		// reportVO=ObjectMapper.convertFMReportDTOToVO(fmReportTabDTO);
		List<String> invalidIds = null;
		try {
			invalidIds = financeManagerBO.validateBuyerIDs(userIds);
		} catch (BusinessServiceException e) {
			throw new DelegateException(e);
		}
		return invalidIds;

	}

	public List<String> validateProviderIDs(List<String> userIds)
			throws DelegateException {
		// FMReportVO
		// reportVO=ObjectMapper.convertFMReportDTOToVO(fmReportTabDTO);
		// reportVO=ObjectMapper.convertFMReportDTOToVO(fmReportTabDTO);
		List<String> invalidIds = null;
		try {
			invalidIds = financeManagerBO.validateProviderIDs(userIds);
		} catch (BusinessServiceException e) {
			throw new DelegateException(e);
		}
		return invalidIds;
	}

	public List<BuyerSOReportVO> getBuyerPaymentByServiceOrder(
			FMReportTabDTO reportTabDTO) throws DelegateException {
		FMReportVO reportVO = ObjectMapper.convertFMReportDTOToVO(reportTabDTO);
		List<BuyerSOReportVO> listOfBuyerSO = null;
		try {
			listOfBuyerSO = financeManagerBO
					.getBuyerPaymentByServiceOrder(reportVO);
		} catch (BusinessServiceException e) {
			throw new DelegateException(e);
		}
		return listOfBuyerSO;
	}

	/*
	 * TO be removed public int getProviderSOReportCount(FMReportTabDTO
	 * fmReportTabDTO){ FMReportVO
	 * reportVO=ObjectMapper.convertFMReportDTOToVO(fmReportTabDTO); return
	 * financeManagerBO.getProviderSOReportCount(reportVO); }
	 * 
	 * public int getBuyerSOReportCount(FMReportTabDTO reportTabDTO) {
	 * FMReportVO reportVO=ObjectMapper.convertFMReportDTOToVO(reportTabDTO);
	 * return financeManagerBO.getBuyerSOReportCount(reportVO); }
	 */

	public List<BuyerSOReportVO> getBuyerReportByTaxPayerID(
			FMReportTabDTO reportTabDTO) throws DelegateException {
		FMReportVO reportVO = ObjectMapper.convertFMReportDTOToVO(reportTabDTO);
		List<BuyerSOReportVO> listOfBuyerSO = null;
		try {
			//listOfBuyerSO = financeManagerBO
			//		.getBuyerReportByTaxPayerID(reportVO);
			listOfBuyerSO = financeManagerBO.getBuyerReportByTaxPayerIDForFromCsv(reportVO);
		} catch (BusinessServiceException e) {
			throw new DelegateException(e);
		}
		return listOfBuyerSO;

	}

	/*
	 * To be removed public int getBuyerTaxIDReportCount(FMReportTabDTO
	 * reportTabDTO) { FMReportVO
	 * reportVO=ObjectMapper.convertFMReportDTOToVO(reportTabDTO); return
	 * financeManagerBO.getBuyerTaxIDReportCount(reportVO); }
	 * 
	 * public int getProviderNetSummaryReportCount(FMReportTabDTO reportTabDTO) {
	 * FMReportVO reportVO=ObjectMapper.convertFMReportDTOToVO(reportTabDTO);
	 * return financeManagerBO.getProviderNetSummaryReportCount(reportVO); }
	 * 
	 */

	public List<ProviderSOReportVO> getProviderNetSummaryReport(
			FMReportTabDTO reportTabDTO) throws DelegateException {
		FMReportVO reportVO = ObjectMapper.convertFMReportDTOToVO(reportTabDTO);
		List<ProviderSOReportVO> listOfProviderVO = null;
		try {
			listOfProviderVO = financeManagerBO
					.getProviderNetSummaryReport(reportVO);
		} catch (BusinessServiceException e) {
			throw new DelegateException(e);
		}
		return listOfProviderVO;
	}

	public void saveInputsForReportsScheduler(FMReportTabDTO reportTabDTO) {
		FMReportVO fmReportVO = ObjectMapper
				.convertFMReportDTOToVO(reportTabDTO);
		financeManagerBO.saveInputsForReportsScheduler(fmReportVO);

	}

	public List<ExportStatusVO> getReportStatus(int slId) throws DelegateException {
		 List<ExportStatusVO> listOfExportVO=null;
		try{
		 listOfExportVO= financeManagerBO.getReportStatus(slId);
		}catch (BusinessServiceException e) {
			throw new DelegateException(e);
		}
		return listOfExportVO;
	}

	public FMReportTabDTO getReportCritirea(FMReportTabDTO fmReportTabDTO)
			throws DelegateException, IOException, Exception {
		//FMReportVO fmReportVO = ObjectMapper
		//		.convertFMReportDTOToVO(reportTabDTO);
		FMReportVO fmReportVO = null;
		FMReportTabDTO reportTabDTO = null;
		try {
			fmReportVO = financeManagerBO.getReportCritirea(fmReportTabDTO.getReportId());
			reportTabDTO = ObjectMapper.convertFMReportVOToDTO(fmReportVO);
		} catch (BusinessServiceException de) {
			logger.error(de);
			throw new DelegateException(de);
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
		return reportTabDTO;
	}

	public boolean deleteReport(FMReportTabDTO reportTabDTO)
			throws DelegateException, Exception {
		FMReportVO fmReportVO = ObjectMapper
				.convertFMReportDTOToVO(reportTabDTO);
		fmReportVO.setReportStatus(OrderConstants.REPORT_DELETED);
		try {
			return financeManagerBO.deleteReport(fmReportVO);
		} catch (BusinessServiceException e) {
			throw new DelegateException(e);
		}
	}
	public ByteArrayOutputStream getFileContent(String filePath)throws BusinessServiceException{
		return financeManagerBO.getFileContent(filePath);
	}

}
