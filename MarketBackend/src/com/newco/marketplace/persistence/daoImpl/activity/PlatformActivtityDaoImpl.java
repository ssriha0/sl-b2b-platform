package com.newco.marketplace.persistence.daoImpl.activity;

import java.sql.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.dao.impl.MPBaseDaoImpl;
import com.newco.marketplace.persistence.iDao.activity.PlatformActivityDao;
import com.newco.marketplace.vo.activity.DailyFulfillmentReportVO;
import com.newco.marketplace.vo.activity.SpBalanceReportVO;
import com.newco.marketplace.vo.activity.WalletBalanceVO;

/**
 * @author nsanzer
 * Description: Methods responsible for returning data for 
 * /home/jboss/slive_data/node4/reconciliation/out/ach/daily_platform_activity_<todays_date>.xls
 */
public class PlatformActivtityDaoImpl  extends MPBaseDaoImpl implements PlatformActivityDao {

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

	@SuppressWarnings("unchecked")
	public List<SpBalanceReportVO> getBalanceReportVOs() {
		Calendar cal = Calendar.getInstance();
		Map<String, Date> m = new HashMap<String, Date>();
		m.put("rptDate", new Date(cal.getTimeInMillis()));
        List<SpBalanceReportVO> spBalanceReportVOs = (List<SpBalanceReportVO>) queryForList("spBalanceReport", m);
		return spBalanceReportVOs;
	}

	@SuppressWarnings("unchecked")
	public List<DailyFulfillmentReportVO> getFulfillmentReportVOs() {
        List<DailyFulfillmentReportVO> spBalanceReportVOs = (List<DailyFulfillmentReportVO>) queryForList("rpt_fulfillment_daily.select", null);
		return spBalanceReportVOs;
	}

	@SuppressWarnings("unchecked")
	public List<WalletBalanceVO> getProjectBalanceVerification(WalletBalanceVO spParams) {
		Map m = new HashMap();
		m.put("startDate", spParams.getStartDate());
		m.put("endDate", spParams.getEndDate());
		m.put("entityId", spParams.getEntityId());
		
        List<WalletBalanceVO> spWalletVOs = (List<WalletBalanceVO>) queryForList("spProjectBalanceVerification", m);
		return spWalletVOs;
	}

	public List<Integer> getUniqueEntityIds(WalletBalanceVO spParams) {
        List<Integer> entityIds = (List<Integer>) queryForList("uniqueEntity.select", spParams);
		return entityIds;
	}


	public Integer getEntityType(Integer entityId) {
		Integer entityTypeId = (Integer) queryForObject("entityType.select", entityId);
		return entityTypeId;
	}

	public void populateFulfillmentRpt() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);
		Map<String, Date> m = new HashMap<String, Date>();
		m.put("rptDate", new Date(cal.getTimeInMillis()));
		queryForObject("spFulfillmentHealthReport", m);
	}
}
