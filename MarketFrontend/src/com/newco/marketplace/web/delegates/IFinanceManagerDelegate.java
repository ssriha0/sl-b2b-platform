package com.newco.marketplace.web.delegates;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jxl.write.WriteException;

import com.newco.marketplace.criteria.PagingCriteria;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.ajax.AjaxCacheVO;
import com.newco.marketplace.dto.vo.financemanger.AdminPaymentVO;
import com.newco.marketplace.dto.vo.financemanger.BuyerSOReportVO;
import com.newco.marketplace.dto.vo.financemanger.ExportStatusVO;
import com.newco.marketplace.dto.vo.financemanger.FMReportVO;
import com.newco.marketplace.dto.vo.financemanger.ProviderSOReportVO;
import com.newco.marketplace.dto.vo.ledger.Account;
import com.newco.marketplace.dto.vo.ledger.AccountHistoryResultVO;
import com.newco.marketplace.dto.vo.ledger.AccountHistoryVO;
import com.newco.marketplace.dto.vo.ledger.CreditCardVO;
import com.newco.marketplace.dto.vo.financemanger.ReportContentVO;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.web.dto.FMFinancialProfileTabDTO;
import com.newco.marketplace.web.dto.FMManageAccountsTabDTO;
import com.newco.marketplace.web.dto.FMOverviewHistoryTabDTO;
import com.newco.marketplace.web.dto.FMReportTabDTO;
import com.newco.marketplace.web.dto.FMTransferFundsDTO;
import com.newco.marketplace.web.dto.TransactionDTO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;

public interface IFinanceManagerDelegate {
	
	public FMFinancialProfileTabDTO getVendorDetails(String vendorId, boolean buyerFlag) throws BusinessServiceException,DBException;
	
	public void saveFinancialProfile(FMFinancialProfileTabDTO financialProfileTabDTO, Integer vendorId, boolean buyerFlag) throws BusinessServiceException,DBException;
	
	public void saveFinancialAccounts(FMManageAccountsTabDTO manageAccountsTabDTO, Integer vendorId)throws DataServiceException;
	
	public void saveEscheatFinancialAccounts(FMManageAccountsTabDTO manageAccountsTabDTO, Integer vendorId)throws DataServiceException;

	public CreditCardVO saveCreditCardInfo(FMManageAccountsTabDTO manageAccountsTabDTO, Integer vendorId, String userName)throws DataServiceException;
	
	public ArrayList<LookupVO> getAccountTypeList() throws DataServiceException;
	
	public ArrayList<LookupVO> getCreditCardTypeList() throws DataServiceException;
	
	public ArrayList<LookupVO> getPercentOwnedList() throws DataServiceException;
	
	public FMManageAccountsTabDTO getAccountDetails(Integer entityId) throws BusinessServiceException;
	
	//public CreditCardDTO getCardDetails(Integer entityId) throws BusinessServiceException;
	
	public FMManageAccountsTabDTO getActiveAccountDetails(Integer entityId) throws BusinessServiceException;
	
	public FMManageAccountsTabDTO getActiveCreditCardDetails(Integer companyId) throws BusinessServiceException;
	
	public ByteArrayOutputStream getExportToExcel(FMOverviewHistoryTabDTO listAfterSearchDTO,ByteArrayOutputStream outFinal) throws BusinessServiceException, IOException, WriteException;
	
	public double getAvailableBalance(AjaxCacheVO avo);

	public double getCurrentBalance(AjaxCacheVO avo);

	// E-Wallet Enhancement
	public double getWalletHoldBalance(Integer entityId);

	//E-Wallet Enhancement
	public String getStateRegulationNote(Integer reasonCodeId);
	public String getIRSlevyNote(Integer reasonCodeId);
	public String getLegalHoldNote(Integer reasonCodeId);
	
	//public Integer returnAccountCount(Long accountId, int reconciledInd) throws BusinessServiceException;
	
	public Integer returnAccountCount(Long accountId) throws BusinessServiceException;

	public List<TransactionDTO> getAccountOverviewHistory (FMOverviewHistoryTabDTO fmOverviewDTO) throws BusinessServiceException;
	public AccountHistoryResultVO getAccountOverviewHistoryFMOverview(FMOverviewHistoryTabDTO fmDTO,PagingCriteria pagingCriteria) throws BusinessServiceException;
	public List<AccountHistoryVO> getExportResultSet(FMOverviewHistoryTabDTO fmDTO) throws BusinessServiceException;
	public ArrayList<LookupVO> getTransferSLBucksReasonCodes() throws BusinessServiceException;
	public ProcessResponse transferSLBucks(FMTransferFundsDTO transferDTO);
	public Integer getWalletControlId(Integer entityId);
	public String getWalletControlBanner(Integer walletControlId);
	
	public List<FMManageAccountsTabDTO> getRefundAccountDetails(Integer entityId) throws BusinessServiceException;
	
	public void sendBuyerPostingFeeEmail(Integer companyId, String soId);
	
	public void sendBuyerCancellationEmail(Integer buyerId, Integer providerId, String soId);
	
	public void sendProviderSOCancelledEmail(Integer buyerId, Integer providerId, String soId);
	
	public void sendBuyerSOClosedEmail(Integer buyerId, String soId);
	public void sendProviderSOClosedEmail(Integer buyerId, String soId);
	
	public Account getAutoFundingIndicator(Integer vendorId)throws BusinessServiceException;
	public void saveAutoACHInfo(FMManageAccountsTabDTO manageAccountsTabDTO, Integer vendorId)throws DataServiceException;

	// Added for jira_SL17625 for Reports generation
	public List<ProviderSOReportVO> getProviderPaymentByServiceOrder(FMReportTabDTO fmReportTabDTO)  throws DelegateException;
	public  List<String> validateBuyerIDs(List<String> userIds) throws DelegateException;
	public  List<String> validateProviderIDs(List<String> userIds) throws DelegateException;

	public List<BuyerSOReportVO> getBuyerPaymentByServiceOrder(
			FMReportTabDTO reportTabDTO) throws DelegateException;


	public List<BuyerSOReportVO> getBuyerReportByTaxPayerID(
			FMReportTabDTO reportTabDTO) throws DelegateException;

	public List<ProviderSOReportVO> getProviderNetSummaryReport(
			FMReportTabDTO reportTabDTO)throws DelegateException;
	
	public void saveInputsForReportsScheduler(FMReportTabDTO reportTabDTO);

	public List<ExportStatusVO> getReportStatus(int SlId) throws DelegateException;

	public FMReportTabDTO getReportCritirea(FMReportTabDTO reportTabDTO) throws DelegateException, Exception;
	public boolean deleteReport(FMReportTabDTO reportTabDTO) throws DelegateException, IOException, Exception;

	public ByteArrayOutputStream getFileContent(String filePath)throws BusinessServiceException;
}
