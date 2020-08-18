package com.newco.marketplace.web.delegates;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.newco.marketplace.dto.vo.PIIThresholdVO;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.web.dto.AccountDTO;
import com.newco.marketplace.web.dto.FMFinancialProfileTabDTO;
import com.newco.marketplace.web.dto.FMManageFundsTabDTO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;

public interface IFMPersistDelegate {

	// public String processCreateDraftSO(ServiceOrder serviceOrder);
	public void saveFinancialProfile(
			FMFinancialProfileTabDTO financialProfileTabDTO, Integer vendorId, boolean buyerFlag)
					throws BusinessServiceException,DBException;

	public List<AccountDTO> getAccounts(Integer entityId)
			throws BusinessServiceException;

	//SL-21117: Revenue Pull Code change starts

	public List <String> getPermittedUsers() throws BusinessServiceException;

	public double getAvailableBalanceForRevenuePull() throws BusinessServiceException;

	public boolean getAvailableDateCheckForRevenuePull(Date calendarOnDate) throws BusinessServiceException;

	public void insertEntryForRevenuePull(double amount,Date revenuePullDate,String note,String user) throws BusinessServiceException;

	public void sendRevenuePullConfirmationEmail(String firstName,String lastName,double amount,String revenuePullDate) throws BusinessServiceException;

	//Code change ends

	public ProcessResponse withdrawAmount(FMManageFundsTabDTO manageFundsTabDTO, Integer companyId, String roleType,String user)
			throws BusinessServiceException;
	public ProcessResponse depositAmount(FMManageFundsTabDTO manageFundsTabDTO, Integer companyId, String roleType,String user)
			throws BusinessServiceException;
	public ProcessResponse issueRefunds(FMManageFundsTabDTO manageFundsTabDTO, Integer companyId, String roleType, String userName)
			throws BusinessServiceException;
	public List<AccountDTO> accountDetailsIncludeCC(Integer entityId) throws BusinessServiceException;

	public ProcessResponse withdrawAmountSLAOperations(FMManageFundsTabDTO manageFundsTabDTO,
			Integer companyId, String roleType,String user) throws BusinessServiceException;

	public ProcessResponse depositAmountSLAOperations(FMManageFundsTabDTO manageFundsTabDTO,
			Integer companyId, String roleType,String user) throws BusinessServiceException;
	public HashMap<String,String> getBuyerTotalDeposit(Integer buyerId) throws BusinessServiceException;
	// Fetch buyer bitFlag and threshold limit
	public PIIThresholdVO getBuyerThresholdMap(String role) throws BusinessServiceException;

	public String getLedgerEntryProviderWithdrawNonce() throws BusinessServiceException;
}
