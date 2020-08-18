package com.newco.marketplace.persistence.match.rank.performancemetric.dao;

import java.util.List;

import com.newco.marketplace.dto.vo.performancematric.VendorPerformanceMatricVo;
import com.newco.marketplace.dto.vo.performancematric.VendorRankingBuyerVo;
import com.newco.marketplace.dto.vo.performancematric.VendorWeightedScoreVo;

public interface IPerformanceMetricDao {
	
	void test();

	List<VendorRankingBuyerVo> getPerformanceAttributesAtFirmLevel(Integer buyerId);
	
	List<Integer> fetchSpnIdFromTemplateSkuForBuyerSpecific(Integer buyerId);

	List<VendorPerformanceMatricVo> fetchAppointmentCommitmentRescheduleByPro(Integer buyerId);

	List<VendorPerformanceMatricVo> fetchTotalClosedOrders(List<VendorPerformanceMatricVo> vendorPerfMVoRescheduleByPro);

	List<VendorPerformanceMatricVo> fetchCustomerRatingCsatAvg(Integer buyerId);

	List<VendorPerformanceMatricVo> fetchTimeOnSiteCountSum(Integer buyerId);

	List<Integer> fetchDistinctFirmIdsFromSpnIdList(Integer buyerId, List<Integer> spnIdList);

	Integer saveOrUpdateVendorPerformanceMetrics(Integer buyerId,
			Integer vendorId, Double consolidatedWeightedScore, String modifiedBy);

	void saveUpdateBackupVendorPerformanceWeightedScore(
			VendorWeightedScoreVo vendorWeightedScoreVo, String modifiedBy);
	
}
