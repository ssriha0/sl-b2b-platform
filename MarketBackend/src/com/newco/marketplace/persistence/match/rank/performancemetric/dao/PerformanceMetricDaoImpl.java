package com.newco.marketplace.persistence.match.rank.performancemetric.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.axis.utils.StringUtils;

import com.newco.marketplace.dto.BuyerSOTemplateDTO;
import com.newco.marketplace.dto.vo.performancematric.VendorPerformanceMatricVo;
import com.newco.marketplace.dto.vo.performancematric.VendorRankingBuyerVo;
import com.newco.marketplace.dto.vo.performancematric.VendorWeightedScoreVo;
import com.newco.marketplace.dto.vo.serviceorder.BuyerSOTemplateVO;
import com.newco.marketplace.interfaces.SPNConstants;
import com.newco.marketplace.interfaces.SearchMatchRankConstants;
import com.sears.os.dao.impl.ABaseImplDao;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class PerformanceMetricDaoImpl extends ABaseImplDao implements IPerformanceMetricDao {

	// ----------------------------------------------------------------------------------//
	public void test() {
		HashMap<String, Object> paramData = new HashMap<String, Object>();
		paramData.put("zipCode", "60086");
		paramData.put("buyerId", "3333");
		Double buyerMarketIndex = (Double) getSqlMapClientTemplate().queryForObject("test.firm.getMarketIndex", paramData);
		logger.info("buyerMarketIndex = " + buyerMarketIndex);
	}
	
	private BuyerSOTemplateDTO getBuyerSOTemplateXMLAsDTO(String xml) {
		XStream xstream = new XStream(new DomDriver());
		BuyerSOTemplateDTO dto = null;
		try {
			xstream.alias("buyerTemplate", BuyerSOTemplateDTO.class);
			dto = (BuyerSOTemplateDTO) xstream.fromXML(xml);
		} catch (Exception e) {
			logger.error("Exception loading alternate buyer contact");
		}
		return dto;
	}
	
	public List<Integer> fetchSpnIdFromTemplateSkuForBuyerSpecific(Integer buyerId) {
		
		HashMap<String, Object> paramData = new HashMap<String, Object>();
		paramData.put("format", "<isNewSpn>true</isNewSpn>");
		paramData.put("buyerId", buyerId);
		
		List<BuyerSOTemplateVO> buyerSOTemplateVOList = getSqlMapClientTemplate()
				.queryForList("fetch.getBuyerSoTemplateBySkuBuyerId.forSpnId",	paramData);
		List<Integer> spnIdList = null;
		
		if (null != buyerSOTemplateVOList) {
			if (buyerSOTemplateVOList.size() > 0) {
				
				spnIdList = new ArrayList<Integer>();
				
				for (BuyerSOTemplateVO vo : buyerSOTemplateVOList) {
					if (null != vo) {
						BuyerSOTemplateDTO dto = getBuyerSOTemplateXMLAsDTO(vo.getTemplateData());
						if (null != dto) {
							// spn should be new(true)... i.e: from spnet_hdr table
							if(dto.getIsNewSpn()) {
								// distinct spn_id
								if (! (spnIdList.contains(dto.getSpnId()))) {
									spnIdList.add(dto.getSpnId());
								}
							}
						}
					}
				} // END: for loop
				
			}
		}
		
		return spnIdList;
	}
	
	public List<Integer> fetchDistinctFirmIdsFromSpnIdList(Integer buyerId, List<Integer> spnIdList) {
		HashMap<String, Object> paramData = new HashMap<String, Object>();
		paramData.put("buyerId", buyerId);
		paramData.put("providerWfState", SPNConstants.SPN_MEMBER);
		paramData.put("spnIdList", spnIdList);
		
		List<VendorPerformanceMatricVo> firmIdListVO = getSqlMapClientTemplate()
				.queryForList("fetch.firm.spnMembers.fromSpnIdList", paramData);
		
		List<Integer> firmIdList = null;
		
		if (null != firmIdListVO) {
			if (firmIdListVO.size() > 0) {
				firmIdList = new ArrayList<Integer>();
			}
		}
		
		for (VendorPerformanceMatricVo vo : firmIdListVO) {
			firmIdList.add(vo.getVendorId());
		}
		
		return firmIdList;	
		
	}
	
	public List<VendorRankingBuyerVo> getPerformanceAttributesAtFirmLevel(Integer buyerId) {
		HashMap<String, Object> paramData = new HashMap<String, Object>();
		paramData.put("attributeType", SearchMatchRankConstants.ATTRIBUTE_TYPE_PERFORMANCE);
		paramData.put("level", SearchMatchRankConstants.LEVEL_FIRM);
		paramData.put("buyerId", buyerId);
		
		return getSqlMapClientTemplate().queryForList("fetch.vendorRankingBuyerVo", paramData);
	}

	public List<VendorPerformanceMatricVo> fetchAppointmentCommitmentRescheduleByPro(Integer buyerId) {
		HashMap<String, Object> paramData = new HashMap<String, Object>();
		
		// Reschedule Service Order
		paramData.put("actionId", 36);
		
		// 1 = provider
		paramData.put("roleId", 1);
		
		paramData.put("buyerId", buyerId);
		return getSqlMapClientTemplate().queryForList("fetch.appointmentCommitment", paramData);
	}

	public List<VendorPerformanceMatricVo> fetchTotalClosedOrders(List<VendorPerformanceMatricVo> vendorPerfMVoRescheduleByProList) {
		
		if(null != vendorPerfMVoRescheduleByProList && vendorPerfMVoRescheduleByProList.size() > 0) {
			
			HashMap<String, Object> paramData = new HashMap<String, Object>();

			// uniform buyer_id
			Integer buyerId = vendorPerfMVoRescheduleByProList.get(0).getBuyerId();
			paramData.put("buyerId", buyerId);
			
			// closed SO_state = 180
			paramData.put("wfStateId", 180);
			paramData.put("vendorPerfMVoRescheduleByProList", vendorPerfMVoRescheduleByProList);
			
			return getSqlMapClientTemplate().queryForList("fetch.totalClosedOrdersByProviderForParticularBuyer", paramData);
		}
		return null;
	}
	
	public List<VendorPerformanceMatricVo> fetchCustomerRatingCsatAvg(Integer buyerId) {
		HashMap<String, Object> paramData = new HashMap<String, Object>();
		paramData.put("entityTypeId", SearchMatchRankConstants.ENTITY_TYPE_BUYER_RATE_PROVIDER);
		paramData.put("buyerId", buyerId);
		return getSqlMapClientTemplate().queryForList("fetch.CustomerRatingCsatAvg", paramData);
	}

	public List<VendorPerformanceMatricVo> fetchTimeOnSiteCountSum(Integer buyerId) {
		HashMap<String, Object> paramData = new HashMap<String, Object>();
		paramData.put("wfStateId", 180);
		paramData.put("buyerId", buyerId);
		return getSqlMapClientTemplate().queryForList("fetch.timeOnSiteCountSum", paramData);
	}

	public Integer saveOrUpdateVendorPerformanceMetrics(Integer buyerId,
			Integer vendorId, Double consolidatedWeightedScore, String modifiedBy) {
		
		HashMap<String, Object> paramData = new HashMap<String, Object>();
		paramData.put("buyerId", buyerId);
		paramData.put("vendorId", vendorId);
		paramData.put("consolidatedWeightedScore", consolidatedWeightedScore);

		paramData.put("modifiedBy", modifiedBy);
		
		Integer vendorPerformanceMetricId = (Integer) getSqlMapClientTemplate().queryForObject("fetch.id.vendorPerformanceMetric", paramData);
		
		if (null == vendorPerformanceMetricId) {
			// insert
			vendorPerformanceMetricId = (Integer) getSqlMapClientTemplate().insert("save.VendorPerformanceMetrics", paramData);
		} else {
			
			paramData.put("vendorPerformanceMetricId", vendorPerformanceMetricId);
			
			// BACKUP code
			backupVendorPerformanceMetricsByIdBeforeUpdate(paramData);
			
			// update
			getSqlMapClientTemplate().update("update.VendorPerformanceMetrics", paramData);
		}
		
		return vendorPerformanceMetricId;
	}
	
	private void backupVendorPerformanceMetricsByIdBeforeUpdate(HashMap<String,Object> paramData) {
		getSqlMapClientTemplate().insert("backup.beforeUpdate.VendorPerformanceMetrics", paramData);
	}
	
	public void saveUpdateBackupVendorPerformanceWeightedScore(VendorWeightedScoreVo vendorWeightedScoreVo, String modifiedBy) {
		
		HashMap<String, Object> paramData = new HashMap<String, Object>();
		
		paramData.put("vendorRankingBuyerId", vendorWeightedScoreVo.getVendorRankingBuyerId());
		paramData.put("vendorPerformanceMatricId", vendorWeightedScoreVo.getVendorPerformanceMatricId());
		paramData.put("vendorId", vendorWeightedScoreVo.getVendorId());
		paramData.put("weightedScore", vendorWeightedScoreVo.getWeightedScore());

		paramData.put("modifiedBy", modifiedBy);
		// -----------------------------------------------------------------
		// UNIQUE: vendorRankingBuyerId, vendorPerformanceMatricId, vendorId
		// -----------------------------------------------------------------
		Integer vendorPerfWeightedScoreId = (Integer) getSqlMapClientTemplate()
				.queryForObject("fetch.id.vendorPerformanceWeightedScore", paramData);
		
		if (null == vendorPerfWeightedScoreId) {
			// insert
			getSqlMapClientTemplate().insert("save.vendorPerformanceWeightedScore", paramData);
		} else {
			
			paramData.put("vendorPerfWeightedScoreId", vendorPerfWeightedScoreId);
			
			// BACKUP code
			backupVendorPerformanceWeightedScoreByIdBeforeUpdate(paramData);
			
			// update
			getSqlMapClientTemplate().update("update.vendorPerformanceWeightedScore", paramData);
		}
		
		
	}

	private void backupVendorPerformanceWeightedScoreByIdBeforeUpdate(HashMap<String,Object> paramData) {
		getSqlMapClientTemplate().insert("backup.beforeUpdate.vendorPerformanceWeightedScore", paramData);
	}

	
	
}