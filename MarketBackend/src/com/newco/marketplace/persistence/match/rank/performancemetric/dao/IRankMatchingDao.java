package com.newco.marketplace.persistence.match.rank.performancemetric.dao;

import java.util.List;

import com.newco.marketplace.beans.d2c.d2cproviderportal.D2CProviderAPIVO;
import com.newco.marketplace.dto.vo.performancematric.VendorPerformanceMatricVo;
import com.newco.marketplace.dto.vo.performancematric.VendorRankingBuyerVo;
import com.newco.marketplace.dto.vo.rank.VendorRankingWeightedScoreVo;
import com.newco.marketplace.dto.vo.rank.VendorSoRankingVo;

public interface IRankMatchingDao {

	List<VendorRankingBuyerVo> getVendorRankingBuyerVoRankAttributesAtFirmLevel(Integer buyerId);

	List<VendorPerformanceMatricVo> getVendorPerformanceMatricByFirmAndBuyerId(Integer buyerId, List<D2CProviderAPIVO> firmDetailsListVo);

	List<VendorPerformanceMatricVo> getVendorBonusScoreMapList(Integer buyerId, List<D2CProviderAPIVO> firmDetailsListVo);

	void saveOrUpdateVendorSoRankingVo(VendorSoRankingVo vendorSoRankingVo);

	String generateAndGetCorelationId();

	void saveOrUpdateVendorRankingWeightedScoreVo(VendorRankingWeightedScoreVo vendorRankingWeightedScoreVo);

	List<VendorPerformanceMatricVo> getVendorMatchingByFirmAndBuyerIdList(Integer buyerId, List<D2CProviderAPIVO> firmDetailsListVo);

}
