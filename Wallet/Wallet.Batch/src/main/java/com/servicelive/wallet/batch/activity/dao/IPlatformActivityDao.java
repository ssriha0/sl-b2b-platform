package com.servicelive.wallet.batch.activity.dao;

import java.sql.Date;
import java.util.List;

import com.servicelive.common.exception.DataServiceException;
import com.servicelive.wallet.batch.activity.vo.DailyFulfillmentReportVO;
import com.servicelive.wallet.batch.activity.vo.ReconciliationViewVO;
import com.servicelive.wallet.batch.activity.vo.SpBalanceReportVO;
import com.servicelive.wallet.batch.activity.vo.WalletBalanceVO;

// TODO: Auto-generated Javadoc
/**
 * 
 * 
 * @author nsanzer
 */
public interface IPlatformActivityDao {

	/**
	 * Gets the available balance verification.
	 * 
	 * @param spParams 
	 * 
	 * @return the available balance verification
	 */
	public List<WalletBalanceVO> getAvailableBalanceVerification(WalletBalanceVO spParams);

	/**
	 * Gets the balance report v os.
	 * 
	 * @return the balance report v os
	 */
	public List<SpBalanceReportVO> getBalanceReportVOs();

	/**
	 * Gets the entity type.
	 * 
	 * @param entityId 
	 * 
	 * @return the entity type
	 */
	public Integer getEntityType(Integer entityId);

	/**
	 * Gets the fulfillment report v os.
	 * 
	 * @return the fulfillment report v os
	 */
	public List<DailyFulfillmentReportVO> getFulfillmentReportVOs();

	/**
	 * Gets the project balance verification.
	 * 
	 * @param spParams 
	 * 
	 * @return the project balance verification
	 */
	public List<WalletBalanceVO> getProjectBalanceVerification(WalletBalanceVO spParams);

	/**
	 * Gets the unique entity ids.
	 * 
	 * @param spParams 
	 * 
	 * @return the unique entity ids
	 */
	public List<Integer> getUniqueEntityIds(WalletBalanceVO spParams);

	/**
	 * Populate fulfillment rpt.
	 */
	public void populateFulfillmentRpt();
	
	/**
	 * Gets the trans process last ran date.
	 * 
	 * @return the trans process last ran date
	 * 
	 * @throws DataServiceException 
	 */
	public Date getTransProcessLastRanDate() throws DataServiceException;

	/**
	 * Gets the general ledger process last ran date.
	 * 
	 * @return the general ledger process last ran date
	 * 
	 * @throws DataServiceException 
	 */
	public Date getGeneralLedgerProcessLastRanDate() throws DataServiceException;

	/**
	 * Gets the nacha process last ran date.
	 * 
	 * @return the nacha process last ran date
	 * 
	 * @throws DataServiceException 
	 */
	public Date getNachaProcessLastRanDate() throws DataServiceException;

	/**
	 * Gets the pTD process last ran date.
	 * 
	 * @return the pTD process last ran date
	 * 
	 * @throws DataServiceException 
	 */
	public Date getPTDProcessLastRanDate() throws DataServiceException;
	
	/**
	 * Gets the trans process view.
	 * 
	 * @param transactionTypeId 
	 * 
	 * @return the trans process view
	 * 
	 * @throws DataServiceException 
	 */
	public ReconciliationViewVO getTransProcessView(int transactionTypeId) throws DataServiceException;

	/**
	 * Gets the nacha process view for consolidated auto ach entry.
	 * 
	 * @return the nacha process view for consolidated auto ach entry
	 * 
	 * @throws DataServiceException 
	 */
	public ReconciliationViewVO getNachaProcessViewForConsolidatedAutoAchEntry() throws DataServiceException;

	/**
	 * Gets the nacha process view.
	 * 
	 * @param transactionTypeId 
	 * @param entityTypeId 
	 * 
	 * @return the nacha process view
	 * 
	 * @throws DataServiceException 
	 */
	public ReconciliationViewVO getNachaProcessView(int transactionTypeId, int entityTypeId) throws DataServiceException;

	/**
	 * Gets the nacha process last ran date.
	 * 
	 * @param processStatusIds 
	 * 
	 * @return the nacha process last ran date
	 * 
	 * @throws DataServiceException 
	 */
	public Date getNachaProcessLastRanDate(Integer processStatusId) throws DataServiceException;

}
