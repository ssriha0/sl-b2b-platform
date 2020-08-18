package com.newco.marketplace.persistence.match.rank.performancemetric.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sears.os.dao.impl.ABaseImplDao;

public class MatchingCriteriaDaoImpl extends ABaseImplDao implements IMatchingCriteriaDao {

	public List<String> fetchProviderResourceListByProviderId(Integer firmId) {
		Map<String, Object> paramData = new HashMap<String, Object>();
		paramData.put("firmId", firmId);
		return ((ArrayList<String>) queryForList("fetch.providerResourceListByProviderId", paramData));
	}

	public void saveOrUpdateMatchingCriteria(String buyerID, Integer firmID, Integer score) {
		HashMap<String, Object> paramData = new HashMap<String, Object>();
		paramData.put("vendorID", firmID);
		paramData.put("score", score);
		paramData.put("buyerID", buyerID);
		
		Integer vendorMatchingScoreId = (Integer) getSqlMapClientTemplate().queryForObject("fetch.id.VendorMatchingScoreId", paramData);
		
		if (null == vendorMatchingScoreId) {
			// insert
			getSqlMapClientTemplate().insert("save.VendorMatchingScoreId", paramData);
		} else {
			
			paramData.put("vendorMatchingScoreId", vendorMatchingScoreId);
			// update
			getSqlMapClientTemplate().update("update.VendorMatchingScoreId", paramData);
		}
		
	}


}
