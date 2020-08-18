package com.servicelive.wallet.batch.activity.dao;

import java.sql.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.servicelive.common.ABaseDao;
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
 * Description: Methods responsible for returning data for
 * /home/jboss/slive_data/node4/reconciliation/out/ach/daily_platform_activity_<todays_date>.xls
 */

public class PlatformActivityDao extends ABaseDao implements IPlatformActivityDao {

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.activity.dao.IPlatformActivityDao#getAvailableBalanceVerification(com.servicelive.wallet.batch.activity.vo.WalletBalanceVO)
	 */
	@SuppressWarnings("unchecked")
	public List<WalletBalanceVO> getAvailableBalanceVerification(WalletBalanceVO spParams) {

		Map m = new HashMap();
		m.put("startDate", spParams.getStartDate());
		m.put("endDate", spParams.getEndDate());
		m.put("entityType", spParams.getEntityType());
		m.put("entityId", spParams.getEntityId());

		List<WalletBalanceVO> spWalletVOs = (List<WalletBalanceVO>) queryForList("spAvailableBalanceVerification", m);
		return spWalletVOs;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.activity.dao.IPlatformActivityDao#getBalanceReportVOs()
	 */
	@SuppressWarnings("unchecked")
	public List<SpBalanceReportVO> getBalanceReportVOs() {

		Calendar cal = Calendar.getInstance();
		Map<String, Date> m = new HashMap<String, Date>();
		m.put("rptDate", new Date(cal.getTimeInMillis()));
		List<SpBalanceReportVO> spBalanceReportVOs = (List<SpBalanceReportVO>) queryForList("spBalanceReport", m);
		return spBalanceReportVOs;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.activity.dao.IPlatformActivityDao#getEntityType(java.lang.Integer)
	 */
	public Integer getEntityType(Integer entityId) {

		Integer entityTypeId = (Integer) queryForObject("entityType.select", entityId);
		return entityTypeId;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.activity.dao.IPlatformActivityDao#getFulfillmentReportVOs()
	 */
	@SuppressWarnings("unchecked")
	public List<DailyFulfillmentReportVO> getFulfillmentReportVOs() {

		List<DailyFulfillmentReportVO> spBalanceReportVOs = (List<DailyFulfillmentReportVO>) queryForList("rpt_fulfillment_daily.select", null);
		return spBalanceReportVOs;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.activity.dao.IPlatformActivityDao#getProjectBalanceVerification(com.servicelive.wallet.batch.activity.vo.WalletBalanceVO)
	 */
	@SuppressWarnings("unchecked")
	public List<WalletBalanceVO> getProjectBalanceVerification(WalletBalanceVO spParams) {

		Map m = new HashMap();
		m.put("startDate", spParams.getStartDate());
		m.put("endDate", spParams.getEndDate());
		m.put("entityId", spParams.getEntityId());

		List<WalletBalanceVO> spWalletVOs = (List<WalletBalanceVO>) queryForList("spProjectBalanceVerification", m);
		return spWalletVOs;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.activity.dao.IPlatformActivityDao#getUniqueEntityIds(com.servicelive.wallet.batch.activity.vo.WalletBalanceVO)
	 */
	@SuppressWarnings("unchecked")
	public List<Integer> getUniqueEntityIds(WalletBalanceVO spParams) {

		List<Integer> entityIds = (List<Integer>) queryForList("uniqueEntity.select", spParams);
		return entityIds;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.activity.dao.IPlatformActivityDao#populateFulfillmentRpt()
	 */
	public void populateFulfillmentRpt() {

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);
		Map<String, Date> m = new HashMap<String, Date>();
		m.put("rptDate", new Date(cal.getTimeInMillis()));
		queryForObject("spFulfillmentHealthReport", m);
	}
	

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.activity.dao.IPlatformActivityDao#getNachaProcessViewForConsolidatedAutoAchEntry()
	 */
	public ReconciliationViewVO getNachaProcessViewForConsolidatedAutoAchEntry() throws DataServiceException {

		ReconciliationViewVO reconciliationViewVO = new ReconciliationViewVO();

		try {
			reconciliationViewVO = (ReconciliationViewVO) queryForObject("dailyReconciliation.AutoAchConsolidated.Nacha.view", null);

		} catch (Exception e) {
			throw new DataServiceException("Exception occurred in PlatformActivityDao.getNachaProcessViewForConsolidatedAutoAchEntry()", e);
		}

		return reconciliationViewVO;

	}
	

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.activity.dao.IPlatformActivityDao#getNachaProcessLastRanDate(java.util.List)
	 */
	public Date getNachaProcessLastRanDate(Integer processStatusId) throws DataServiceException {

		Date nachaProcessRanDate = null;

		try {

			nachaProcessRanDate = (java.sql.Date) queryForObject("dailyReconciliation.Nacha.status.lastRan", processStatusId);

		} catch (Exception e) {
			throw new DataServiceException("Exception occurred in PlatformActivityDao.getNachaProcessLastRanDate()", e);
		}

		return nachaProcessRanDate;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.activity.dao.IPlatformActivityDao#getNachaProcessView(int, int)
	 */
	public ReconciliationViewVO getNachaProcessView(int transactionTypeId, int entityTypeId) throws DataServiceException {

		ReconciliationViewVO reconciliationViewVO = new ReconciliationViewVO();

		try {
			Map<String, Integer> paramMap = new HashMap<String, Integer>();
			paramMap.put("transactionTypeId", transactionTypeId);
			paramMap.put("entityTypeId", entityTypeId);
			reconciliationViewVO = (ReconciliationViewVO) queryForObject("dailyReconciliation.Nacha.view", paramMap);

		} catch (Exception e) {
			throw new DataServiceException("Exception occurred in PlatformActivityDao.getNachaProcessView()", e);
		}

		return reconciliationViewVO;

	}
	

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.activity.dao.IPlatformActivityDao#getPTDProcessLastRanDate()
	 */
	public Date getPTDProcessLastRanDate() throws DataServiceException {

		Date ptdProcessRanDate = null;

		try {

			ptdProcessRanDate = (java.sql.Date) queryForObject("dailyReconciliation.ptd.lastRan", null);

		} catch (Exception e) {
			throw new DataServiceException("Exception occurred in PlatformActivityDao.getPTDProcessLastRanDate()", e);
		}

		return ptdProcessRanDate;
	}


	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.activity.dao.IPlatformActivityDao#getTransProcessView(int)
	 */
	public ReconciliationViewVO getTransProcessView(int transactionTypeId) throws DataServiceException {

		ReconciliationViewVO reconciliationViewVO = new ReconciliationViewVO();

		try {

			reconciliationViewVO = (ReconciliationViewVO) queryForObject("dailyReconciliation.transfile.view", transactionTypeId);

		} catch (Exception e) {
			throw new DataServiceException("Exception occurred in PlatformActivityDao.getTransProcessView()", e);
		}

		return reconciliationViewVO;
	}


	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.activity.dao.IPlatformActivityDao#getTransProcessLastRanDate()
	 */
	public Date getTransProcessLastRanDate() throws DataServiceException {

		Date transProcessLastRanDate = null;

		try {

			transProcessLastRanDate = (java.sql.Date) queryForObject("dailyReconciliation.Tran.lastRan", null);

		} catch (Exception e) {
			throw new DataServiceException("Exception occurred in PlatformActivityDao.getTransProcessLastRanDate()", e);
		}

		return transProcessLastRanDate;

	}
	

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.activity.dao.IPlatformActivityDao#getGeneralLedgerProcessLastRanDate()
	 */
	public Date getGeneralLedgerProcessLastRanDate() throws DataServiceException {

		Date glProcessRanDate = null;

		try {

			glProcessRanDate = (java.sql.Date) queryForObject("dailyReconciliation.GeneralLedger.lastRan", null);

		} catch (Exception e) {
			throw new DataServiceException("Exception occurred in PlatformActivityDao.getGeneralLedgerProcessLastRanDate()", e);
		}

		return glProcessRanDate;

	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.activity.dao.IPlatformActivityDao#getNachaProcessLastRanDate()
	 */
	public Date getNachaProcessLastRanDate() throws DataServiceException {

		Date nachaProcessRanDate = null;
		try {

			nachaProcessRanDate = (java.sql.Date) queryForObject("dailyReconciliation.Nacha.lastRan", null);
		} catch (Exception e) {
			throw new DataServiceException("Exception occurred in PlatformActivityDao.getNachaProcessLastRanDate()", e);
		}

		return nachaProcessRanDate;
	}

}
