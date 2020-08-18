package com.newco.marketplace.business.iBusiness.financeManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import jxl.write.WriteException;

import com.newco.marketplace.criteria.PagingCriteria;
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
import com.newco.marketplace.dto.vo.serviceorder.FundingVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderCustomRefVO;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.vo.provider.FinancialProfileVO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.servicelive.common.vo.SLAccountVO;
// import com.newco.marketplace.dto.vo.IdnInformationVO;
import com.newco.marketplace.dto.vo.PIIThresholdVO;


public interface IFinanceManagerBO {

	public void saveFinancialProfile(FinancialProfileVO financialProfileVO, Integer vendorId, boolean buyerFlag) throws BusinessServiceException,DBException;
	public FinancialProfileVO getVendorDetails(String vendorId, boolean buyerFlag) throws BusinessServiceException, DBException;

	public Account getAccountDetails(Integer entityId);
	public Account getEscheatAccountDetails(Integer entityId);
	public List<Account> accountDetails(int entityId);
	public ProcessResponse depositfunds(Integer companyId, String roleType, FundingVO fundVo, long accountId,String user, String autoACHInd);
	public void saveAccountDetails(Account account) throws DataServiceException ;
	public double getavailableBalance(AjaxCacheVO avo);
	
	// E-Wallet Enhancement
	public double getWalletHoldBalance(Integer entityId);

	public void updateEntityWalletRemainingBalance(double remainingHoldBalance, Integer entityId, Integer walletControlId);
	
	//SL-21117: Revenue Pull Code change starts

	public List <String> getPermittedUsers() throws BusinessServiceException;

	public double getAvailableBalanceForRevenuePull();

	public boolean getAvailableDateCheckForRevenuePull(Date calendarOnDate);

	public void insertEntryForRevenuePull(double amount,Date revenuePullDate,String note,String user);

	public void sendRevenuePullConfirmationEmail(String firstName,String lastName,double amount,String revenuePullDate) throws BusinessServiceException;

	//Code change ends

	public double getcurrentBalance(AjaxCacheVO avo);

	public Integer returnAccountCount(Long accountId) throws BusinessServiceException;

	public ByteArrayOutputStream getExportToExcel(List<ListAfterSearchVO> historyVO,ByteArrayOutputStream outFinal) throws BusinessServiceException, IOException, WriteException;

	public boolean updateAccoutEnableInd(Long accountId, boolean enabledInd) throws BusinessServiceException;
	public List<AccountHistoryVO>getAccountOverviewHistory(AccountHistoryVO ahVO) throws BusinessServiceException;
	public AccountHistoryResultVO getAccountOverviewHistoryFMOverview(AccountHistoryVO ahVO,PagingCriteria pagingCriteria) throws BusinessServiceException;
	public List<AccountHistoryVO> getExportRecords(AccountHistoryVO ahVO);
	public ProcessResponse transferSLBucks(FMTransferFundsVO transferFundsVO) throws BusinessServiceException;
	public Integer getWalletControlId(Integer entityId);
	public String getWalletControlBanner(Integer walletControlId);
	public ProcessResponse issueRefunds(Integer companyId, String roleType, double amount, long accountId, String userName, String note);
	public List<Account> accountDetailsIncludeCC(int entityId);	
	public Account accountDetailsForSo(int entityId, String cardNumber);
	public void sendDepositConfirmationEmail(Integer companyId, double amount, Integer ledgerId, String roleType);
	public void sendCCDepositConfirmationEmail(CreditCardVO ccVo, double amount, Integer ledgerId, String roleType);

	public ProcessResponse withdrawfundsSLAOperations(Integer companyId, String roleType, double amount,long accountId,String user);

	public HashMap<String,String> getBuyerTotalDeposit(Integer buyerId);

	// Fetch buyer bitFlag and threshold limit
	public PIIThresholdVO getBuyerThresholdMap(String role);

	public ProcessResponse depositfundsSLAOperations(Integer companyId, String roleType, double amount, long accountId,String user);

	public void sendBuyerPostingFeeEmail(Integer companyId, String soId);

	public void sendBuyerSOCancelEmail(Integer buyerId, Integer providerId, String soId,String roleType);	
	public void sendProviderSOCancelEmail(Integer buyerId, Integer providerId, String soId);

	public void sendBuyerSOClosedEmail(Integer buyerId, String soId);
	public void sendProviderSOClosedEmail(Integer buyerId, String soId);

	public Account getAutoFundingIndicator(Integer vendorId) throws BusinessServiceException ;
	public void saveAutoFundingDetails(Account account) throws BusinessServiceException;

	public ServiceOrderCustomRefVO getCustomReferenceObject(String customRefId, String soId);

	public void sendWithdrawProviderConfirmationEmail(Integer companyId, double amount, Integer ledgerId);

	/**
	 * Description: Consumer funded orders are paid for acceptance.  If the credit card auth fails or 
	 * there is no card on record for the order, we csend a faileed to accept email to the buyer and provider.
	 * @param so
	 */
	public void sendFailedToAcceptSOEmails(ServiceOrder so);

	/**
	 * Description: Return first active account, limit 1
	 * @param entityId
	 * @return
	 * @throws BusinessServiceException
	 */
	public SLAccountVO getActiveAccountDetails(long entityId) throws BusinessServiceException;

	public List<ProviderSOReportVO> getProviderPaymentByServiceOrder(FMReportVO reportVO) 	throws BusinessServiceException;
	public List<String> validateBuyerIDs(List<String> buyerIds) throws BusinessServiceException;
	public List<String> validateProviderIDs(List<String> buyerIds) throws BusinessServiceException;
	public List<BuyerSOReportVO> getBuyerPaymentByServiceOrder(FMReportVO reportVO) throws BusinessServiceException;

	public List<BuyerSOReportVO> getBuyerReportByTaxPayerID(FMReportVO reportVO) throws BusinessServiceException;

	public List<ProviderSOReportVO> getProviderNetSummaryReport(
			FMReportVO reportVO) throws BusinessServiceException;
	public void saveInputsForReportsScheduler(FMReportVO fmReportVO);
	public List<ExportStatusVO> getReportStatus(int slId) throws BusinessServiceException;
	public List<FMReportVO> getCompletedAndFailedRecords()  throws BusinessServiceException;
	public void updateDeletedStatus(List<Integer> reportIds) throws BusinessServiceException;
	public List<FMReportVO> getQueuedRecords(Integer numberOfReportsToBeProcessed)  throws BusinessServiceException;
	public  FMReportVO getReportCritirea(Integer reportId)  throws BusinessServiceException, Exception;
	public boolean deleteReport(FMReportVO fmReportVO)  throws BusinessServiceException, IOException, Exception;
	public ByteArrayOutputStream getFileContent(String filePath)throws BusinessServiceException;
	public void updateAsInProcess(List<FMReportVO> listReportVO)throws BusinessServiceException;
	public List<BuyerSOReportVO> getBuyerReportByTaxPayerIDForFromCsv(
			FMReportVO reportVO) throws BusinessServiceException;
	public void checkAndUpdateInProcessReport(int maxTimeIntervalForBatchSec)throws BusinessServiceException;
	//SLT-2232
	public void sendTransferSLBucksEscheatmentEmail(FMTransferFundsVO transferFundsVO) throws BusinessServiceException;
}
