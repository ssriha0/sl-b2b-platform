package com.newco.marketplace.persistence.match.rank.performancemetric.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.axis.utils.StringUtils;

import com.newco.marketplace.beans.d2c.d2cproviderportal.D2CProviderAPIVO;
import com.newco.marketplace.dto.vo.performancematric.VendorPerformanceMatricVo;
import com.newco.marketplace.dto.vo.performancematric.VendorRankingBuyerVo;
import com.newco.marketplace.dto.vo.rank.VendorRankingWeightedScoreVo;
import com.newco.marketplace.dto.vo.rank.VendorSoRankingVo;
import com.newco.marketplace.interfaces.SearchMatchRankConstants;
import com.sears.os.dao.impl.ABaseImplDao;

public class RankMatchingDaoImpl extends ABaseImplDao implements IRankMatchingDao {

	public List<VendorRankingBuyerVo> getVendorRankingBuyerVoRankAttributesAtFirmLevel(Integer buyerId) {
		HashMap<String, Object> paramData = new HashMap<String, Object>();
		paramData.put("attributeType", SearchMatchRankConstants.ATTRIBUTE_TYPE_RANK);
		paramData.put("level", SearchMatchRankConstants.LEVEL_FIRM);
		paramData.put("buyerId", buyerId);
		
		return  ((List<VendorRankingBuyerVo>) getSqlMapClientTemplate().queryForList("fetch.vendorRankingBuyerVo", paramData));
	}

	public List<VendorPerformanceMatricVo> getVendorPerformanceMatricByFirmAndBuyerId(
			Integer buyerId, List<D2CProviderAPIVO> firmDetailsListVo) {
		
		HashMap<String, Object> paramData = new HashMap<String, Object>();
		paramData.put("firmDetailsListVo", firmDetailsListVo);
		paramData.put("buyerId", buyerId);
		
		return  ((List<VendorPerformanceMatricVo>) getSqlMapClientTemplate().queryForList("fetch.VendorPerformanceMatricByFirmAndBuyerId", paramData));
	}

	public List<VendorPerformanceMatricVo> getVendorBonusScoreMapList(
			Integer buyerId, List<D2CProviderAPIVO> firmDetailsListVo) {
		
		HashMap<String, Object> paramData = new HashMap<String, Object>();
		paramData.put("firmDetailsListVo", firmDetailsListVo);
		paramData.put("buyerId", buyerId);
		
		return  ((List<VendorPerformanceMatricVo>) getSqlMapClientTemplate().queryForList("fetch.VendorBonusScoreMapList", paramData));
	}

	public void saveOrUpdateVendorSoRankingVo(VendorSoRankingVo vendorSoRankingVo) {
		getSqlMapClientTemplate().insert("save.VendorSoRankingVo", vendorSoRankingVo);
	}

	public String generateAndGetCorelationId() {
		return ((String) getSqlMapClientTemplate().insert("save.generateAndGetCorelationId"));
	}

	public void saveOrUpdateVendorRankingWeightedScoreVo(VendorRankingWeightedScoreVo vendorRankingWeightedScoreVo) {
		// attributeId(vendorRankingBuyerId) = [PERFORMANCE_METRIC || NEW_SIGNUP || MATCHING_PER;] 
		// setVendorId = 67225
		// WeightedScore = 23.46
		// corelationId = 1
		getSqlMapClientTemplate().insert("save.VendorRankingWeightedScoreVo", vendorRankingWeightedScoreVo);
	}

	public List<VendorPerformanceMatricVo> getVendorMatchingByFirmAndBuyerIdList(
			Integer buyerId, List<D2CProviderAPIVO> firmDetailsListVo) {
		
		HashMap<String, Object> paramData = new HashMap<String, Object>();
		paramData.put("firmDetailsListVo", firmDetailsListVo);
		paramData.put("buyerId", buyerId);
		
		return  ((List<VendorPerformanceMatricVo>) getSqlMapClientTemplate().queryForList("fetch.VendorMatchingList", paramData));
	}

}
