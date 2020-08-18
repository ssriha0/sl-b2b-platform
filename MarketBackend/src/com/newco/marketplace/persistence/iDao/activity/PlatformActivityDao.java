package com.newco.marketplace.persistence.iDao.activity;

import java.util.List;

import com.newco.marketplace.vo.activity.DailyFulfillmentReportVO;
import com.newco.marketplace.vo.activity.SpBalanceReportVO;
import com.newco.marketplace.vo.activity.WalletBalanceVO;

/**
 * @author nsanzer
 */
public interface PlatformActivityDao {
	public List<WalletBalanceVO> getAvailableBalanceVerification (WalletBalanceVO spParams);
	public List<SpBalanceReportVO> getBalanceReportVOs();
	public Integer getEntityType (Integer entityId);
	public List<DailyFulfillmentReportVO> getFulfillmentReportVOs();
	public List<WalletBalanceVO> getProjectBalanceVerification (WalletBalanceVO spParams);
	public List<Integer> getUniqueEntityIds (WalletBalanceVO spParams);
	public void populateFulfillmentRpt();
}
